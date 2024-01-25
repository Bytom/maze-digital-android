package guru.maze.avatar.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import guru.maze.avatar.R;

/**
 * Created by xiaok on 2018/10/31.
 */

public class CustomProgressDialog extends Dialog {
    protected Context mContext;

    protected WindowManager.LayoutParams mLayoutParams;
    public View mHideProgress;

    public WindowManager.LayoutParams getLayoutParams() {
        return mLayoutParams;
    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
        setFullScreen();
        setContentView(getView());
    }

    public CustomProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
        setFullScreen();
        setContentView(getView());
    }

    public CustomProgressDialog(Context context) {
        super(context);
        initView(context);
        setFullScreen();
        setContentView(getView());
    }

    private View getView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
        ImageView refreshLogo = inflate.findViewById(R.id.refresh_logo);


        RequestOptions options = new RequestOptions()
                                         .fitCenter()
                                         .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext)
                .asGif()
                .load(R.drawable.loading)
                .apply(options)
                .into(refreshLogo);

        mHideProgress = inflate.findViewById(R.id.hide);
        mHideProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return inflate;
    }


    private void initView(Context context) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawableResource(R.color.transparent);
        mContext = context;
        Window window = this.getWindow();

        mLayoutParams = window.getAttributes();
//        mLayoutParams.alpha = 1f;
        if (mLayoutParams != null) {
            mLayoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
            mLayoutParams.gravity = Gravity.CENTER;
        }
        window.setAttributes(mLayoutParams);

    }


//


    public void setFullScreen() {
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }


}
