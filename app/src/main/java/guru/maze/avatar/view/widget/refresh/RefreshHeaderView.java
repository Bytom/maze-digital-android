package guru.maze.avatar.view.widget.refresh;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import guru.maze.avatar.R;


/**
 * @author by xiaok
 * @date 2020/4/13
 */
public class RefreshHeaderView extends LinearLayout implements RefreshHeader {

    private ImageView mLogoView;
    private ObjectAnimator mRotationRelease;
//    private ObjectAnimator mRotationStart;

    public RefreshHeaderView(Context context) {
        super(context);
        initView(context);
    }


    public RefreshHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化视图
     *
     * @param context
     */
    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        mLogoView = new ImageView(context);
        mLogoView.setBackgroundResource(R.drawable.smart_refresh);
        mRotationRelease = ObjectAnimator.ofFloat(mLogoView, "rotation", 0, 360);


        mRotationRelease.setInterpolator(new LinearInterpolator());
        mRotationRelease.setDuration(800);
        mRotationRelease.setRepeatCount(1);

        addView(mLogoView, ConvertUtils.dp2px(24), ConvertUtils.dp2px(24));

        setMinimumHeight(ConvertUtils.dp2px(55));
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    /**
     * 开始动画（开始刷新或者开始加载动画）
     *
     * @param refreshLayout RefreshLayout
     * @param height        HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {


    }

    /**
     * 【仅限框架内调用】手指拖动下拉（会连续多次调用，添加isDragging并取代之前的onPulling、onReleasing）
     *
     * @param isDragging    true 手指正在拖动 false 回弹动画
     * @param percent       下拉的百分比 值 = offset/footerHeight (0 - percent - (footerHeight+maxDragHeight) / footerHeight )
     * @param offset        下拉的像素偏移量  0 - offset - (footerHeight+maxDragHeight)
     * @param height        高度 HeaderHeight or FooterHeight (offset 可以超过 height 此时 percent 大于 1)
     * @param maxDragHeight 最大拖动高度 offset 可以超过 height 参数 但是不会超过 maxDragHeight
     */
    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

        if (mRotationRelease != null && mRotationRelease.isRunning()) {
            mRotationRelease.cancel();
        }
        if (isDragging && percent <= 1 && percent >= 0.5) {
            mLogoView.setRotation((float) (-720 * (percent - 0.5)));
        } else {
            mLogoView.setRotation(0);
        }

    }


    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }


    /**
     * 【仅限框架内调用】动画结束
     *
     * @param refreshLayout RefreshLayout
     * @param success       数据是否成功刷新或加载
     * @return 完成动画所需时间 如果返回 Integer.MAX_VALUE 将取消本次完成事件，继续保持原有状态
     */
    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        mRotationRelease.start();
        return 800;//延迟500毫秒之后再弹回
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:

                break;
            case Refreshing:
            case Loading:
                break;
            case RefreshFinish:
            case PullDownCanceled:
                break;

        }
    }
}
