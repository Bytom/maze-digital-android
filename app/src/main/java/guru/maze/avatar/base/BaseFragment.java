package guru.maze.avatar.base;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.trello.rxlifecycle4.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import guru.maze.avatar.model.inter.EventResponse;
import guru.maze.avatar.utils.ToastCustomUtil;

/**
 * @author by xiaok
 * @date 2022/11/1
 */
public abstract class BaseFragment extends RxFragment {

    public Unbinder mUnbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = setContentView(inflater, container);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BaseFragment.this.getActivity().onTouchEvent(event);
                return false;
            }
        });
        initSerialization();

        mUnbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /**
     * 初始化业务数据
     */
    public void initSerialization() {
    }

    public abstract View setContentView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mUnbinder = null;
    }


    //接收全局的网络回调！ 主要是错误！
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventCall(EventResponse response) {
        onEventReceive(response);
    }

    public void onEventReceive(EventResponse response) {

    }


    @Override
    public void onPause() {
        super.onPause();
        ToastCustomUtil.cancelToast();
    }


    @Override
    public void onStop() {
        super.onStop();
        ToastCustomUtil.cancelToast();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    //关闭软键盘
    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
