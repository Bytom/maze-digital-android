package guru.maze.avatar.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import guru.maze.avatar.R;


/**
 * @author xiaok
 */
public abstract class BaseDialog extends Dialog {

    protected Context mContext;

    protected LayoutParams mLayoutParams;

    public LayoutParams getLayoutParams() {
        return mLayoutParams;
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    public BaseDialog(Context context) {
        super(context);
        initView(context);

    }


    private void initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        mContext = context;
        Window window = this.getWindow();
        initAnim(window);
        mLayoutParams = window.getAttributes();
//        mLayoutParams.alpha = 1f;
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = setGravity();
        }
        window.setAttributes(mLayoutParams);

    }

    private void initAnim(Window window) {
        window.setWindowAnimations(R.style.dialog_anim);  //添加动画  </strong> </span>
    }

//




    /**
     * 设置宽度match_parent
     */
    public void setFullScreenWidth() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }


    public void setFullScreen() {
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        LayoutParams lp = window.getAttributes();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }


    public abstract int setGravity();

}
