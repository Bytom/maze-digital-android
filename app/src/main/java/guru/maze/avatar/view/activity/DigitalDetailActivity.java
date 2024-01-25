package guru.maze.avatar.view.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import guru.maze.avatar.base.BaseActivity;
import guru.maze.avatar.model.bean.digital.UserDigitalBean;
import guru.maze.avatar.model.db.user.UserDBHelper;
import guru.maze.avatar.model.db.user.UserDBManager;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.R;
import guru.maze.avatar.utils.GlideUtil;
import guru.maze.avatar.utils.PermissionUtil;
import guru.maze.avatar.utils.ProgressBarUtil;
import guru.maze.avatar.utils.ToastCustomUtil;

/**
 * @author by xiaok
 * @date 2023/10/25
 */
public class DigitalDetailActivity extends BaseActivity {
    @BindView(R.id.detail_img)
    ImageView mDetailImg;
    @BindView(R.id.owner_avatar)
    ImageView mOwnerAvatar;
    @BindView(R.id.owner_name)
    TextView mOwnerName;
    @BindView(R.id.gender_text)
    TextView mGenderText;
    @BindView(R.id.style_text)
    TextView mStyleText;
    private UserDigitalBean.Artworks mArtworks;

    @Override
    public View initView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_digital_detail, null);
    }

    @Override
    public void initSerialization(Bundle extras) {
        mArtworks = (UserDigitalBean.Artworks) extras.getSerializable(Constant.SERIALIZE_ONE);
    }

    @Override
    protected void initialize() {


        GlideUtil.load(mContext, mArtworks.url, 12, mDetailImg);


        UserDBManager userDBManager = UserDBHelper.getIns().queryUserData();

        if (userDBManager != null) {
            GlideUtil.loadAvatar(mContext, userDBManager.getUserAvatar(), mOwnerAvatar);
            mOwnerName.setText(userDBManager.getUserName());
        }
        mGenderText.setText(mArtworks.prompt_style_parent_name);
        mStyleText.setText(mArtworks.prompt_style_name);


    }

    @OnClick({R.id.bottom_container})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_container:

                //
                downLoadPic();
                break;

        }
    }

    private void downLoadPic() {
        if (!TextUtils.isEmpty(mArtworks.url)) {
            PermissionUtil.requestImage(mContext,new PermissionUtil.OnPermissionGrantedListener() {
                @Override
                public void onPermissionGranted() {
                    if (TextUtils.isEmpty(mArtworks.url)) return;
                    ProgressBarUtil.showProgress(mContext);
                    Glide.with(mContext).load(mArtworks.url).transition(new DrawableTransitionOptions().crossFade()).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull @NotNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {
                            ProgressBarUtil.dismissProgress(mContext);
                            ImageUtils.save2Album(ImageUtils.drawable2Bitmap(resource), Bitmap.CompressFormat.JPEG, 100);
                            ToastCustomUtil.showSuccessToast(mContext, R.string.success);
                        }
                    });
                }
            });
        }


    }
}
