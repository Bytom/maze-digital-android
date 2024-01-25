package guru.maze.avatar.model.user;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import guru.maze.avatar.model.bean.user.UserDataBean;
import guru.maze.avatar.model.db.user.UserDBHelper;
import guru.maze.avatar.model.db.user.UserDBManager;
import guru.maze.avatar.model.inter.EventResponse;
import guru.maze.avatar.net.RetrofitFactory;
import guru.maze.avatar.net.domain.ServerEntity;
import guru.maze.avatar.net.domain.UserAccountServer;
import guru.maze.avatar.net.rx.BaseSubscriber;
import guru.maze.avatar.net.rx.RxUtil;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.LocalLanguageUtils;
import guru.maze.avatar.utils.Params;
import guru.maze.avatar.utils.ToastCustomUtil;
import guru.maze.avatar.view.dialog.LoginDialog;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import guru.maze.avatar.R;

/**
 * @author by xiaok
 * @date 2022/11/15
 * 登陆实现类
 */
public class LoginImpl {

    private static class Holder {
        private static LoginImpl INSTANCE = new LoginImpl();
    }

    public static LoginImpl getIns() {
        return Holder.INSTANCE;
    }

    /**
     * 判断是否登陆
     */
    public boolean justLogin() {
        UserDBManager userDBManager = UserDBHelper.getIns().queryUserData();
        if (userDBManager == null) return false;
        return true;
    }

    private LoginDialog showLoginDialog(Context context) {
        LoginDialog loginDialog = new LoginDialog(context);

        loginDialog.show();
        return loginDialog;
    }


    public void setGoogleLogin(Activity activity) {
        LoginDialog loginDialog = showLoginDialog(activity);
        loginDialog.mLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(Params.GOOGLE_TOKEN)
                                                  .requestEmail()
                                                  .build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(activity, gso);
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
                Log.i(Constant.XIAOK, "是否登录过Account:" + new Gson().toJson(account));
                //TODO  判断用户是否已经登录过
                //默认没登录
                if (account == null) {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    activity.startActivityForResult(signInIntent, Constant.RC_SIGN_IN);
                } else {
                    ServerEntity.loginGoogle loginGoogle = new ServerEntity.loginGoogle();
                    loginGoogle.googleId = account.getId();
                    loginGoogle.idToken = account.getIdToken();
                    loginGoogle.email = account.getEmail();
                    loginGoogle.name = account.getDisplayName();
                    Log.i(Constant.XIAOK, "google回执信息：" + new Gson().toJson(loginGoogle));
                    LoginImpl.getIns().login2Google(activity, loginGoogle);
                }
            }
        });


    }


    public void login2Google(Context context, ServerEntity.loginGoogle entity) {
        RetrofitFactory.getRetrofit().create(UserAccountServer.class).login2Google(entity)
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.commonResult(context))
                .subscribe(new BaseSubscriber<UserDataBean>() {
                    @Override
                    public void onNext(UserDataBean userDataBean) {
                        Log.i(Constant.XIAOK, "获取Google登陆数据:" + new Gson().toJson(userDataBean));
                        ToastCustomUtil.showSuccessToast(context, R.string.login_google);

                        SPUtils.getInstance().put(Constant.ACCESS_TOKEN, userDataBean.accessToken);

                        UserDBManager build = new UserDBManager.UserDaoBuilder()
                                                      .setAccessToken(userDataBean.accessToken)
                                                      .setUserName(userDataBean.name)
                                                      .setUserAvatar("")
                                                      .setUserID(userDataBean.id)
                                                      .build();

                        UserDBHelper.getIns().addOrUpdateAsset(build);
//                        String clientId = SPUtils.getInstance().getString(Constant.PUSH_CLIENT_ID, "");

                        EventBus.getDefault().post(new EventResponse(Constant.USER_LOGIN_SUCCESS, ""));
//                        if (!TextUtils.isEmpty(clientId)) {
//                            ServerEntity.bindNotify bindNotify = new ServerEntity.bindNotify();
//                            bindNotify.clientId = clientId;
//                            RetrofitFactory.getRetrofit().create(MazePushServer.class).bindNotification(LocalLanguageUtils.getServerLanguage(), bindNotify)
//                                    .compose(RxUtil.rxSchedulerHelper())
//                                    .compose(RxUtil.commonResult(context))
//                                    .subscribe(new BaseSubscriber<Object>() {
//                                        @Override
//                                        public void onNext(Object o) {
//                                        }
//                                    });
//
//                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }





}
