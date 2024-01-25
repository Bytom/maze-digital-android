package guru.maze.avatar.view.activity.setting;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import guru.maze.avatar.base.BaseActivity;
import guru.maze.avatar.model.inter.EventResponse;
import guru.maze.avatar.net.RetrofitFactory;
import guru.maze.avatar.net.domain.ServerEntity;
import guru.maze.avatar.net.domain.UserAccountServer;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.Params;
import guru.maze.avatar.view.widget.ItemLiner;
import guru.maze.avatar.R;

/**
 * @author by xiaok
 * @date 2023/6/26
 */
public class AccountInfoActivity extends BaseActivity {
    @BindView(R.id.item_chuanyin)
    ItemLiner mItemChuanyin;
    @BindView(R.id.item_google)
    ItemLiner mItemGoogle;

    private boolean bindPhone;
    private boolean bindDiscord;
    private boolean bindGoogle;

    @Override
    public View initView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_account_info, null);
    }

    @Override
    protected void initialize() {
        mItemChuanyin.mItemLinerSetname.setText(R.string.bind);
        mItemGoogle.mItemLinerSetname.setText(R.string.bind);

        initBindInfoNet();
    }

    private void initBindInfoNet() {

    }

    @OnClick({R.id.item_chuanyin, R.id.item_google})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.item_google:
                if (!bindGoogle) {
                    startGoogle();
                }
                break;
        }
    }

    private void startGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(Params.GOOGLE_TOKEN).requestEmail().build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Log.i(Constant.XIAOK, "是否登录过Account:" + new Gson().toJson(account));
        //TODO  判断用户是否已经登录过
        //默认没登录
        if (account == null) {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, Constant.RC_SIGN_IN);
        } else {
            bind2Google(account);
        }


    }

    private void bind2Google(GoogleSignInAccount account) {
        ServerEntity.loginGoogle loginGoogle = new ServerEntity.loginGoogle();
        loginGoogle.googleId = account.getId();
        loginGoogle.idToken = account.getIdToken();
        loginGoogle.email = account.getEmail();
        loginGoogle.name = account.getDisplayName();
        Log.i(Constant.XIAOK, new Gson().toJson(loginGoogle));
//        RetrofitFactory.getRetrofit().create(UserAccountServer.class).bindGoogle(loginGoogle).compose(RxUtil.rxSchedulerHelper()).compose(RxUtil.commonResult(mContext)).subscribe(new BaseSubscriber<Object>() {
//            @Override
//            public void onNext(Object o) {
//            Log.i(Constant.XIAOK,"google绑定成功："+new Gson().toJson(o));
//                initBindInfoNet();
//            }
//        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == Constant.RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.i(Constant.XIAOK, "account:" + new Gson().toJson(account));

            bind2Google(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(Constant.XIAOK, "signInResult:failed code=" + e.getStatusCode());
            Log.w(Constant.XIAOK, "登录失败");
//            updateUI(null);

        }
    }
}
