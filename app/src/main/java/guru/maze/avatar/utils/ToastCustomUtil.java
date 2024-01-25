package guru.maze.avatar.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import guru.maze.avatar.R;


/**
 * @author by xiaok
 * @date 2021/9/14
 */
public class ToastCustomUtil extends Toast {

    private static Toast mToast;

    public ToastCustomUtil(Context context) {
        super(context);
    }


    public static void showLocalErrorToast(Context context, String content) {

        //获取系统的LayoutInflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_toast, null);

        TextView tvToast = view.findViewById(R.id.tv_toast);

        tvToast.setText(content);

        Drawable drawableLeft = context.getResources().getDrawable(
                R.drawable.shape_yellow_round);
        tvToast.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);

        //实例化toast
        if (mToast == null) {
            mToast = new Toast(context);
        }

        mToast.setView(view);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public static void showLocalErrorToast(Context context, int content) {
        showLocalErrorToast(context, context.getString(content));
    }


    public static void showSuccessToast(Context context, int content) {
        showSuccessToast(context, context.getString(content));
    }

    public static void showSuccessToast(Context context, String content) {

        //获取系统的LayoutInflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_toast, null);

        TextView tvToast = view.findViewById(R.id.tv_toast);

        tvToast.setText(content);

        Drawable drawableLeft = context.getResources().getDrawable(
                R.drawable.shape_green_round);
        tvToast.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);

        //实例化toast
        if (mToast == null) {
            mToast = new Toast(context);
        }
        mToast.setView(view);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }


    public static void showNetErrorToast(Context context, String content) {

        //获取系统的LayoutInflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_toast, null);

        TextView tvToast = view.findViewById(R.id.tv_toast);

        tvToast.setText(content);

        Drawable drawableLeft = context.getResources().getDrawable(
                R.drawable.shape_red_round);
        tvToast.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);

        //实例化toast
        if (mToast == null) {
            mToast = new Toast(context);
        }
        mToast.setView(view);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }


    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

   public static void showPositionToast(Context context,String content,int mode,int offX,int offY){


       //获取系统的LayoutInflater
       LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View view = inflater.inflate(R.layout.view_pos_toast, null);

       TextView tvToast = view.findViewById(R.id.tv_toast);

       tvToast.setText(content);



       //实例化toast
       if (mToast == null) {
           mToast = new Toast(context);
       }
       mToast.setView(view);
       mToast.setDuration(Toast.LENGTH_SHORT);
       mToast.setGravity(mode, offX, offY);
       mToast.show();
   }

}
