package guru.maze.avatar.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import guru.maze.avatar.R;
import guru.maze.avatar.model.inter.EventResponse;
import guru.maze.avatar.utils.ActivityManagerUtil;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.StatusBarUtil;
import guru.maze.avatar.utils.ToastCustomUtil;

/**
 * @author by xiaok
 * @date 2022/11/1
 */
public abstract class BaseActivity extends RxAppCompatActivity {


    //butterknife
    public Unbinder mUnbinder;
    public FrameLayout mBaseContainer;

    public Context mContext;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean darkMode = SPUtils.getInstance().getBoolean(Constant.USER_DARK_MODE);
        if (darkMode) {
            if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
                Log.i(Constant.XIAOK, "导致创建了Main");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        } else {

            if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

//        BarUtils.setStatusBarColor(this,ContextCompat.getColor(this,R.color.base_red));
//        BarUtils.transparentStatusBar(this);
        StatusBarUtil.setWindowStatusBarColor(this, R.color.bg_gray);

        ActivityManagerUtil.addActivity(this);

        EventBus.getDefault().register(this);
        mContext = BaseActivity.this;

        initPreSetContent();
        if (getIntent().getExtras() != null) {
            initSerialization(getIntent().getExtras());
        }
        setContentView(R.layout.activity_base);

        initBaseView();

    }


    /**
     * 创建View之前的操作 比如说进入时状态栏
     */
    public void initPreSetContent() {

    }


    /**
     * 序列化数据操作
     *
     * @param extras
     */
    public void initSerialization(Bundle extras) {

    }

    /**
     * 初始化baseView
     */
    private void initBaseView() {
        mBaseContainer = findViewById(R.id.base_container);

//        BarUtils.addMarginTopEqualStatusBarHeight(mBaseContainer);

        mBaseContainer.addView(initView());
        mUnbinder = ButterKnife.bind(this);
        initialize();

    }


    protected void initialize() {
        //如果使用butterknife请使用此方法来做初始化或者在onStart()方法中

    }

    //接收全局的网络回调！ 主要是错误！
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventCall(EventResponse response) {

        onEventReceive(response);
    }

    public void onEventReceive(EventResponse response) {

    }


    public abstract View initView();

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        mUnbinder = null;
        ActivityManagerUtil.getIns().removeActivity(this);
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }


    @Override
    protected void onPause() {
        if (KeyboardUtils.isSoftInputVisible(this)) {
            KeyboardUtils.hideSoftInput(this);
            mBaseContainer.scrollTo(0, 0);
        }
        ToastCustomUtil.cancelToast();

        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
        ToastCustomUtil.cancelToast();

    }







}
