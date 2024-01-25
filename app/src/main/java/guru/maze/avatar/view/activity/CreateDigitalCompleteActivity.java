package guru.maze.avatar.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import guru.maze.avatar.base.BaseActivity;
import guru.maze.avatar.R;
import guru.maze.avatar.model.bean.digital.DigitalArtBean;
import guru.maze.avatar.model.inter.EventResponse;
import guru.maze.avatar.net.RetrofitFactory;
import guru.maze.avatar.net.domain.DigitalServer;
import guru.maze.avatar.net.rx.BaseSubscriber;
import guru.maze.avatar.net.rx.RxUtil;
import guru.maze.avatar.utils.ActivityManagerUtil;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.view.widget.SimpleTitle;
import io.reactivex.rxjava3.core.Flowable;

/**
 * @author by xiaok
 * @date 2023/10/23
 */
public class CreateDigitalCompleteActivity extends BaseActivity {
    @BindView(R.id.simple_title)
    SimpleTitle mSimpleTitle;
    @BindView(R.id.cost_text)
    TextView mCostText;
    @BindView(R.id.digital_progress)
    ImageView mDigitalProgress;
    @BindView(R.id.progress_text)
    TextView mProgressText;
    private ArrayList<Integer> mArtIDs;
    private BaseSubscriber<Long> mBaseSubscriber;

    private boolean allFinish;
    @Override
    public View initView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_create_digital_complete, null);
    }

    @Override
    public void initSerialization(Bundle extras) {
        mArtIDs = (ArrayList<Integer>) getIntent().getExtras().get(Constant.SERIALIZE_ONE);

    }

    @Override
    protected void initialize() {


        Log.i(Constant.XIAOK, "mArtIDs:" + mArtIDs);
        Glide.with(mContext).load(R.drawable.digital_progress).apply(new RequestOptions().override(ConvertUtils.dp2px(100), ConvertUtils.dp2px(100))).into(mDigitalProgress);
        initNet();


    }

    @OnClick({R.id.bottom_container})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_container:
                if (!allFinish) {
                    //后台运行
                    EventBus.getDefault().post(new EventResponse(Constant.DIGITAL_CREATE, ""));
                    finish();
                } else {
                    //去画廊
                    EventBus.getDefault().post(new EventResponse(Constant.DIGITAL_CREATE, ""));
                    ActivityManagerUtil.getIns().finishSingleActivityByClass(CreateDigitalHumanActivity.class);
                    ActivityManagerUtil.getIns().finishSingleActivityByClass(CreateDigitalChooseStyleActivity.class);
                    ActivityManagerUtil.getIns().finishSingleActivityByClass(CreateDigitalChooseThemeActivity.class);
                    finish();
                }
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void initNet() {

        StringBuilder idsBuilder = new StringBuilder();
        for (int i = 0; i < mArtIDs.size(); i++) {
            if (i == mArtIDs.size() - 1) {
                idsBuilder.append(mArtIDs.get(i));
            } else {
                idsBuilder.append(mArtIDs.get(i)).append(",");
            }
        }
        String ids = idsBuilder.toString();
        Log.i(Constant.XIAOK, "ids:" + ids);
        mBaseSubscriber = Flowable.interval(10, TimeUnit.SECONDS).compose(RxUtil.rxSchedulerHelper()).subscribeWith(new BaseSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {

                    RetrofitFactory.getRetrofit().create(DigitalServer.class).getDigitalListArt(ids).compose(RxUtil.rxSchedulerHelper()).compose(RxUtil.commonResult(mContext)).subscribe(new BaseSubscriber<List<DigitalArtBean>>() {
                        @Override
                        public void onNext(List<DigitalArtBean> artBeanList) {
                            //TODO
                            Log.i(Constant.XIAOK, "轮训作画：" + new Gson().toJson(artBeanList));
                            if (justFinish(artBeanList)){
                                mBaseSubscriber.onComplete();
                                mBaseSubscriber.dispose();
                                mCostText.setText(R.string.go_to_gallrty);
                                mProgressText.setText(R.string.digital_completed);
                                Glide.with(mContext).load(R.drawable.success).apply(new RequestOptions().override(ConvertUtils.dp2px(100), ConvertUtils.dp2px(100))).into(mDigitalProgress);
                                allFinish = true;
                            }
                        }
                    });



            }
        });


    }

    private boolean justFinish(List<DigitalArtBean> artBeanList){
        for (int i = 0; i < artBeanList.size(); i++) {
            if (!"1".equals(artBeanList.get(i).completePercent)){
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (mBaseSubscriber != null) {
            mBaseSubscriber.dispose();
        }
        super.onDestroy();
    }

}
