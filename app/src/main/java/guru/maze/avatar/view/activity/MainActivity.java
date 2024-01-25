package guru.maze.avatar.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;
import guru.maze.avatar.R;
import guru.maze.avatar.base.BaseActivity;
import guru.maze.avatar.model.inter.EventResponse;
import guru.maze.avatar.model.user.LoginImpl;
import guru.maze.avatar.net.domain.ServerEntity;
import guru.maze.avatar.utils.ActivityManagerUtil;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.Params;
import guru.maze.avatar.utils.ToastCustomUtil;
import guru.maze.avatar.view.fragment.HomeFragment;
import guru.maze.avatar.view.fragment.MineFragment;

public class MainActivity extends BaseActivity {


    @BindView(R.id.rb_mine)
    RadioButton mRbMine;
    @BindView(R.id.rb_discover)
    RadioButton mRbDiscover;
    private Context mContext;
    public List<Fragment> mFragments = null;


    @Override
    public View initView() {
        mContext = MainActivity.this;
        return LayoutInflater.from(mContext).inflate(R.layout.activity_main, null);
    }

    @Override
    protected void initialize() {

        initFragment();

    }

    private void initFragment() {
        mFragments = new ArrayList<>();

        mFragments.add(new HomeFragment());
        mFragments.add(new MineFragment());

        switchFragment(0);


        mRbDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment(0);
            }
        });
        mRbMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLoginOrSwitch();
            }
        });
    }

    public void switchFragment(int pos) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        int length = mFragments.size();
        for (int i = 0; i < length; i++) {
            Fragment fragment = mFragments.get(i);
            if (i == pos) {
                if (fragment.isAdded()) {
                    fragmentTransaction.show(fragment);
                } else {
                    fragmentTransaction.add(R.id.main_frame, fragment, fragment.getTag());
                }
            } else {
                if (fragment.isAdded()) {
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        try {
            fragmentTransaction.commitAllowingStateLoss();
            getSupportFragmentManager().executePendingTransactions();
        } catch (Exception e) {
        }
    }

    @Override
    public void onEventReceive(EventResponse response) {
        if (response.code == Constant.USER_DELETE) {
            Log.i(Constant.XIAOK, "收到用户退出账户");
            switchFragment(0);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }
    private long mPressedTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long nowTime = System.currentTimeMillis();
            if (nowTime - mPressedTime > 2000) {
                ToastCustomUtil.showLocalErrorToast(mContext, R.string.quit_again);
                mPressedTime = nowTime;
            } else {
                ActivityManagerUtil.getIns().AppExit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.rb_create)
    public void onClick() {
        //创建作品
        startActivity(new Intent(mContext, CreateDigitalChooseThemeActivity.class));

    }
    private void setLoginOrSwitch() {
//        boolean b = LoginImpl.getIns().justLogin();
//        if (!b) {
//            LoginImpl.getIns().setGoogleLogin(this);
//
//        } else {
            switchFragment(1);
//        }
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

            String idToken = account.getIdToken();
            Log.i(Constant.XIAOK, "Googletoken:" + idToken);
            Log.i(Constant.XIAOK, "account:" + new Gson().toJson(account));
            ServerEntity.loginGoogle loginGoogle = new ServerEntity.loginGoogle();
            loginGoogle.googleId = account.getId();
            loginGoogle.idToken = account.getIdToken();
            loginGoogle.email = account.getEmail();
            loginGoogle.name = account.getDisplayName();
            Log.i(Constant.XIAOK, new Gson().toJson(loginGoogle));
            LoginImpl.getIns().login2Google(mContext, loginGoogle);
        } catch (ApiException e) {
            Log.w(Constant.XIAOK, "signInResult:failed code=" + e.getStatusCode());
            Log.w(Constant.XIAOK, "signInResult:failed msg=" + e.getMessage());
            Log.w(Constant.XIAOK, "登录失败");


            //退出Google登陆
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(Params.GOOGLE_TOKEN).requestId().requestEmail().build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(mContext, gso);
            googleSignInClient.signOut();


        }
    }

}