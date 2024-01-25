package guru.maze.avatar.view.activity.setting;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.LanguageUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import guru.maze.avatar.base.BaseActivity;

import guru.maze.avatar.R;
import guru.maze.avatar.model.bean.user.UserDataBean;
import guru.maze.avatar.model.db.user.UserDBHelper;
import guru.maze.avatar.model.db.user.UserDBManager;
import guru.maze.avatar.model.inter.EventResponse;
import guru.maze.avatar.net.RetrofitFactory;
import guru.maze.avatar.net.domain.UserAccountServer;
import guru.maze.avatar.net.rx.BaseSubscriber;
import guru.maze.avatar.net.rx.RxUtil;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.NetConstant;
import guru.maze.avatar.view.activity.NightModeChangeMaskActivity;
import guru.maze.avatar.view.dialog.CommonBottomDialog;
import guru.maze.avatar.view.dialog.DeleteArtDialog;
import guru.maze.avatar.view.widget.ItemLiner;

/**
 * @author by xiaok
 * @date 2022/11/7
 */
public class SettingActivity extends BaseActivity  {
    @BindView(R.id.item_username)
    ItemLiner mItemUsername;
    @BindView(R.id.item_faq)
    ItemLiner mItemFaq;
    @BindView(R.id.item_about_us)
    ItemLiner mItemAboutUs;
    @BindView(R.id.item_dark)
    ItemLiner mItemDark;


    private DeleteArtDialog mDeleteArtDialog;

    private CommonBottomDialog mCommonBottomDialog;


    @Override
    public View initView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_setting, null);

    }

    @Override
    protected void initialize() {
        setUserName();
        boolean darkMode = SPUtils.getInstance().getBoolean(Constant.USER_DARK_MODE);
        mItemDark.mMItemLinerName.setText(darkMode ? R.string.switch_2_day : R.string.switch_2_night);


    }


    private void setUserName() {
        UserDBManager userDBManager = UserDBHelper.getIns().queryUserData();
        mItemUsername.mItemLinerSetname.setText(userDBManager != null ? userDBManager.getUserName() : "");
    }

    @OnClick({R.id.item_username, R.id.item_account_info, R.id.policy, R.id.item_faq, R.id.item_about_us, R.id.item_dark, R.id.item_delete_account, R.id.item_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_username:

//                startActivity(new Intent(mContext, EditActivity.class));
                break;
            case R.id.item_account_info:
                startActivity(new Intent(mContext, AccountInfoActivity.class));

                break;


            case R.id.policy:
                mContext.startActivity(new Intent(mContext, PolicyActivity.class));
                break;
            case R.id.item_faq:
                Intent intentFAQ = new Intent(Intent.ACTION_VIEW, Uri.parse(NetConstant.MAZE_FAQ));
                mContext.startActivity(intentFAQ);
                break;
            case R.id.item_about_us:

                mContext.startActivity(new Intent(mContext, AboutUsActivity.class));
                break;
            case R.id.item_dark:
                boolean darkMode = SPUtils.getInstance().getBoolean(Constant.USER_DARK_MODE);
                SPUtils.getInstance().put(Constant.USER_DARK_MODE, !darkMode);
                startActivity(new Intent(this, NightModeChangeMaskActivity.class));
                overridePendingTransition(R.anim.window_scale_in, R.anim.window_scale_out);
                mItemDark.mMItemLinerName.setText(!darkMode ? R.string.switch_2_day : R.string.switch_2_night);
                recreate();
                break;
            case R.id.item_delete_account:

                if (mDeleteArtDialog == null) {
                    mDeleteArtDialog = new DeleteArtDialog(mContext);
                }
                mDeleteArtDialog.show();
                mDeleteArtDialog.mDeleteNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteAccount();
                    }
                });

                break;
            case R.id.item_login_out:
                Log.i(Constant.XIAOK, "触发了退出");
                setOutAccount();
                break;
        }
    }


    private void deleteAccount() {
//        RetrofitFactory.getRetrofit().create(UserAccountServer.class).deleteUserAccount().compose(RxUtil.rxSchedulerHelper()).compose(RxUtil.commonResult(mContext)).subscribe(new BaseSubscriber<Object>() {
//            @Override
//            public void onNext(Object o) {
//                if (mDeleteArtDialog != null) {
//                    mDeleteArtDialog.dismiss();
//                }
//                setOutAccount();
//            }
//        });
    }

    private void setOutAccount() {
//        String clientId = SPUtils.getInstance().getString(Constant.PUSH_CLIENT_ID, "");
//
//        //退出Google登陆
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(Params.GOOGLE_TOKEN).requestId().requestEmail().build();
//        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(mContext, gso);
//        googleSignInClient.signOut();
//
//        if (!TextUtils.isEmpty(clientId)) {
//            ServerEntity.bindNotify bindNotify = new ServerEntity.bindNotify();
//            bindNotify.clientId = clientId;
//            RetrofitFactory.getRetrofit().create(MazePushServer.class).unbindNotification(bindNotify).compose(RxUtil.rxSchedulerHelper()).compose(RxUtil.commonResult(mContext)).subscribe(new BaseSubscriber<Object>() {
//                @Override
//                public void onNext(Object o) {
//
//                }
//
//                @Override
//                public void onError(Throwable t) {
//                    super.onError(t);
//
//                }
//            });
//        }
//        UserDBHelper.getIns().loginOutAccount();
//        EventBus.getDefault().post(new EventResponse(Constant.USER_DELETE, ""));
//        finish();


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceive(EventResponse response) {
        if (response.code == Constant.USER_SETTING) {
            Log.i(Constant.XIAOK, "收到用户信息更新Setting");
            setUserName();
        }
    }


}