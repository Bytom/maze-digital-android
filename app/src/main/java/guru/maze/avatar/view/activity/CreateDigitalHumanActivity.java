package guru.maze.avatar.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.luck.picture.lib.basic.PictureSelectionCameraModel;
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.language.LanguageConfig;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.picture.lib.utils.SandboxTransformUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import guru.maze.avatar.base.BaseActivity;
import guru.maze.avatar.R;
import guru.maze.avatar.model.bean.digital.DigitalArtBean;
import guru.maze.avatar.model.inter.CommonInterface;
import guru.maze.avatar.net.QiniuyunFactory;
import guru.maze.avatar.net.RetrofitFactory;
import guru.maze.avatar.net.domain.DigitalServer;
import guru.maze.avatar.net.domain.ServerEntity;
import guru.maze.avatar.net.rx.BaseSubscriber;
import guru.maze.avatar.net.rx.RxUtil;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.ProgressBarUtil;
import guru.maze.avatar.utils.ToastCustomUtil;
import guru.maze.avatar.view.dialog.CommonBottomDialog;
import guru.maze.avatar.view.widget.GlideEngine;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;
import top.zibin.luban.OnRenameListener;

/**
 * @author by xiaok
 * @date 2023/10/19
 */
public class CreateDigitalHumanActivity extends BaseActivity {

    @BindView(R.id.cost_text)
    TextView mCostText;


    @BindView(R.id.load_p2t_desc)
    TextView mLoadP2tDesc;
    @BindView(R.id.picture_reference)
    ImageView mPictureReference;
    @BindView(R.id.p2t_delete)
    ImageView mP2tDelete;
    @BindView(R.id.picture_container)
    ConstraintLayout mPictureContainer;

    private CommonBottomDialog mCommonBottomDialog;
    private ArrayList<Integer> mStyleIds;
    private int mPoints;
    private String mLoadUrl;


    @Override
    public View initView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_create_digital, null);
    }

    @Override
    public void initSerialization(Bundle extras) {
        mStyleIds = (ArrayList<Integer>) getIntent().getExtras().get(Constant.SERIALIZE_ONE);
        mPoints = extras.getInt(Constant.SERIALIZE_TWO);

    }


    @Override
    protected void initialize() {
        Log.i(Constant.XIAOK, "mStyleIds:" + mStyleIds);
        mCostText.setText(getString(R.string.continue_text) + "(" + mPoints + " " + getString(R.string.points) + ")");
    }

    @OnClick({R.id.bottom_container, R.id.p2t_delete, R.id.picture_container})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_container:

                //
                submit2Server();

                break;
            case R.id.p2t_delete:
                mLoadUrl = "";
                mPictureReference.setImageBitmap(null);
                mLoadP2tDesc.setVisibility(View.VISIBLE);
                mP2tDelete.setVisibility(View.GONE);
                break;
            case R.id.picture_container:
                showAlbumDialog(Constant.CHOOSE_MAZE_REQUEST);
                break;
        }
    }

    private void submit2Server() {

//
        if (TextUtils.isEmpty(mLoadUrl)) {
            ToastCustomUtil.showLocalErrorToast(mContext, R.string.upload_one_photo);
            return;
        }


        ServerEntity.createDigitalImg createDigitalImg = new ServerEntity.createDigitalImg();
        createDigitalImg.initImageUrl = mLoadUrl;
        createDigitalImg.styleIds = mStyleIds;
        RetrofitFactory.getRetrofit().create(DigitalServer.class).createDigitalImage(createDigitalImg).compose(RxUtil.rxSchedulerHelper()).compose(RxUtil.commonResult(mContext)).subscribe(new BaseSubscriber<List<DigitalArtBean>>() {
            @Override
            public void onNext(List<DigitalArtBean> digitalArtBeans) {
                Log.i(Constant.XIAOK, "作画成功" + new Gson().toJson(digitalArtBeans));

                Intent intent = new Intent(mContext, CreateDigitalCompleteActivity.class);

                List<Integer> ids = new ArrayList<>();
                for (DigitalArtBean digitalArtBean : digitalArtBeans) {
                    ids.add(digitalArtBean.id);
                }


                intent.putExtra(Constant.SERIALIZE_ONE, new ArrayList<Integer>(ids));
                startActivity(intent);
            }
        });


    }

    private void showAlbumDialog(int requestCode) {
        List<String> text = new ArrayList<>();

        text.add(getString(R.string.upload_from_local_device));
        text.add(getString(R.string.take_a_photo));
        if (mCommonBottomDialog == null) {
            mCommonBottomDialog = new CommonBottomDialog(mContext);

        }
        mCommonBottomDialog.mCenterTextAdapter.setNewInstance(text);
        mCommonBottomDialog.show();


        mCommonBottomDialog.mDialogTitle.setVisibility(View.GONE);
        mCommonBottomDialog.mCenterTextAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (position == 0) {
                    openAlbum(requestCode);

                } else {
                    openCamera(requestCode);

                }
                mCommonBottomDialog.dismiss();
            }
        });
    }

    private void openAlbum(int requestCode) {

        PictureSelectionModel selectionModel = PictureSelector.create(this).openGallery(SelectMimeType.ofImage())
                                                       //设置语言
                                                       .setLanguage(LanguageConfig.JAPAN).setSelectorUIStyle(new PictureSelectorStyle()).setImageEngine(GlideEngine.createGlideEngine())
                                                       //是否压缩图片
                                                       .setCompressEngine(new ImageFileCompressEngine()).setSandboxFileEngine(new MeSandboxFileEngine()).isPageSyncAlbumCount(true)
                                                       //.setExtendLoaderEngine(getExtendLoaderEngine())
                                                       .setSelectionMode(SelectModeConfig.SINGLE).isOriginalControl(true).isDisplayCamera(false)
                                                       //跳过不需要裁剪的类型
                                                       //预览点击全屏
                                                       .isPreviewFullScreenMode(true)
                                                       //预览缩放效果
                                                       .isPreviewZoomEffect(true).isPreviewImage(true).isPreviewVideo(false).isPreviewAudio(false).isDirectReturnSingle(true).setMaxSelectNum(1).setMaxVideoSelectNum(0).isGif(false);

        selectionModel.forResult(requestCode);

    }

    private void openCamera(int requestCode) {
        PictureSelectionCameraModel pictureSelectionCameraModel = PictureSelector.create(this).openCamera(SelectMimeType.ofImage());
        pictureSelectionCameraModel.forResultActivity(requestCode);

    }

    private static class ImageFileCompressEngine implements CompressFileEngine {
        @Override
        public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {
            Luban.with(context).load(source).ignoreBy(15 * 1024).filter(new CompressionPredicate() {
                @Override
                public boolean apply(String path) {
                    return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                }
            }).setRenameListener(new OnRenameListener() {
                @Override
                public String rename(String filePath) {
                    int indexOf = filePath.lastIndexOf(".");
                    String postfix = indexOf != -1 ? filePath.substring(indexOf) : ".png";
                    return DateUtils.getCreateFileName("CMP_") + postfix;
                }
            }).setCompressListener(new OnNewCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(String source, File compressFile) {
                    if (call != null) {
                        call.onCallback(source, compressFile.getAbsolutePath());
                    }
                }

                @Override
                public void onError(String source, Throwable e) {
                    if (call != null) {
                        call.onCallback(source, null);
                    }
                }
            }).launch();
        }
    }

    /**
     * 自定义沙盒文件处理
     */
    private static class MeSandboxFileEngine implements UriToFileTransformEngine {
        @Override
        public void onUriToFileAsyncTransform(Context context, String srcPath, String mineType, OnKeyValueResultCallbackListener call) {
            if (call != null) {
                call.onCallback(srcPath, SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType));
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.CHOOSE_MAZE_REQUEST) {
                ProgressBarUtil.showProgress(mContext);
                ArrayList<LocalMedia> result = PictureSelector.obtainSelectorList(data);
                LocalMedia localMedia = result.get(0);
                String realPath = localMedia.getRealPath();
                Bitmap bitmap = ImageUtils.getBitmap(realPath);
                mP2tDelete.setVisibility(View.VISIBLE);
                mPictureReference.setImageBitmap(bitmap);
                mLoadP2tDesc.setVisibility(View.GONE);


                load2Qiniuyun(bitmap);
            }
        }
    }

    private void load2Qiniuyun(Bitmap bitmap) {
        byte[] cropBytes = ImageUtils.bitmap2Bytes(bitmap);
        String key = "ai_init_image/" + System.currentTimeMillis() + "_" + new Random().nextInt(1000000);

        Log.i(Constant.XIAOK, "load2qiniuyun:" + key);


        QiniuyunFactory.getIns().upLoad2Qiniuyun( mContext, null, cropBytes, key, new CommonInterface<String>() {
            @Override
            public void callBack(String loadUrl) {
                //底图的上传成功
                //上传到图生文接口
                ProgressBarUtil.dismissProgress(mContext);
                Log.i(Constant.XIAOK, "获取图片地址" + loadUrl);
                mLoadUrl = loadUrl;

            }
        });

    }


}
