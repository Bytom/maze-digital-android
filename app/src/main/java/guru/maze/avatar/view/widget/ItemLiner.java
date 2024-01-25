package guru.maze.avatar.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import guru.maze.avatar.R;


/**
 * Created by xiaok on 2018/10/23.
 */

public class ItemLiner extends RelativeLayout {


    public TextView mItemLinerSetname;


    private int mImageResource;
    private String mItemName;
    private boolean mIsShowNext;
    private int mTextColor;
    public TextView mMItemLinerName;
    public ImageView mItemLinerNextImageView;


    public ItemLiner(Context context) {
        this(context, null);
    }

    public ItemLiner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemLiner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ItemLiner);
        mImageResource = array.getResourceId(R.styleable.ItemLiner_item_image, 0);
        mItemName = array.getString(R.styleable.ItemLiner_item_name);
        mIsShowNext = array.getBoolean(R.styleable.ItemLiner_item_next, true);
        mTextColor = array.getColor(R.styleable.ItemLiner_item_textColor, ContextCompat.getColor(context, R.color.text_black));
        initLayout(context);
        array.recycle();
    }

    private void initLayout(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.widget_item_liner, this);

        ImageView mItemLinerImageview = inflate.findViewById(R.id.item_liner_imageview);
        mMItemLinerName = inflate.findViewById(R.id.item_liner_name);
        mItemLinerSetname = inflate.findViewById(R.id.item_liner_setname);
        mItemLinerNextImageView = inflate.findViewById(R.id.item_liner_next);


        mMItemLinerName.setTextColor(mTextColor);
        if (mIsShowNext) {
            mItemLinerNextImageView.setVisibility(VISIBLE);
        } else {
            mItemLinerNextImageView.setVisibility(GONE);
        }


        if (mImageResource != 0) {
            mItemLinerImageview.setVisibility(VISIBLE);
            mItemLinerImageview.setImageResource(mImageResource);
        }
        mMItemLinerName.setText(mItemName);

    }


}
