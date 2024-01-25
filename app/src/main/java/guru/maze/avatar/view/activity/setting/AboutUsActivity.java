package guru.maze.avatar.view.activity.setting;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;

import butterknife.BindView;
import butterknife.OnClick;
import guru.maze.avatar.base.BaseActivity;
import guru.maze.avatar.utils.CommonUtil;
import guru.maze.avatar.R;

/**
 * @author by xiaok
 * @date 2022/11/14
 */
public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.updaye_tag)
    View mUpdateTag;

    @Override
    public View initView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_aboutus, null);
    }

    @Override
    protected void initialize() {


//        RetrofitFactory.getRetrofit().create(MazeBusinessServer.class).getAppUpdate().compose(RxUtil.rxSchedulerHelper()).compose(RxUtil.commonResult(mContext)).subscribe(new BaseSubscriber<AppUpdateBean>() {
//            @Override
//            public void onNext(AppUpdateBean updateBean) {
//                String appVersionName = AppUtils.getAppVersionName();
//                if (CommonUtil.compareVersion(appVersionName, updateBean.latestVersion.versionNumber) == -1) {
//                    mUpdateTag.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }


    @OnClick({ R.id.item_update})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.item_update:

                CommonUtil.toUpdateApp(mContext);
                break;

        }
    }
}
