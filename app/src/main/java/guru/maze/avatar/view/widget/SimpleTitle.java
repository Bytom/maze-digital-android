package guru.maze.avatar.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;

import androidx.constraintlayout.widget.ConstraintLayout;
import guru.maze.avatar.R;
import guru.maze.avatar.base.BaseActivity;

/**
 * Created by xiaok on 2018/9/25.
 */

public class SimpleTitle extends ConstraintLayout {
    public ImageView mSimpleLeft;
    public TextView mSimpleRight;
    public TextView mTvTitle;
    private Context mContext;
    private int mResRl;
    private int mRlColor;
    private String mRlText;
    private int mTitleColor;

    public SimpleTitle(Context context) {
        this(context, null);
    }

    public SimpleTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleTitle);
        String tvTitle = typedArray.getString(R.styleable.SimpleTitle_title);

        mResRl = typedArray.getResourceId(R.styleable.SimpleTitle_rl_img, 0);
        mRlColor = typedArray.getColor(R.styleable.SimpleTitle_rl_color, 0);
        mTitleColor = typedArray.getColor(R.styleable.SimpleTitle_title_color, 0);
        mRlText = typedArray.getString(R.styleable.SimpleTitle_rl_text);


        initLayout(tvTitle);
        typedArray.recycle();
    }

    private void initLayout(String tvTitle) {
        View inflate = inflate(mContext, R.layout.widget_simple_title, this);


        mSimpleLeft = inflate.findViewById(R.id.nav_back);
        mSimpleRight = inflate.findViewById(R.id.rl_tv);
        mTvTitle = inflate.findViewById(R.id.tv_title);

        if (!TextUtils.isEmpty(tvTitle)) {
            mTvTitle.setText(tvTitle);
        }
        if (mResRl != 0) {
            mSimpleRight.setBackgroundResource(mResRl);
        }
        if (mRlColor != 0) {
            mSimpleRight.setTextColor(mRlColor);
        }
        if (!TextUtils.isEmpty(mRlText)){
            mSimpleRight.setText(mRlText);
        }
        if (mTitleColor!=0){
            mTvTitle.setTextColor(mTitleColor);
        }



        mSimpleLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof BaseActivity) {
                    KeyboardUtils.hideSoftInput(((BaseActivity) mContext));
                    ((BaseActivity) mContext).finish();
                }
            }
        });
    }


}
