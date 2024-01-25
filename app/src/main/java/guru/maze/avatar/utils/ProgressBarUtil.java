package guru.maze.avatar.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import guru.maze.avatar.view.dialog.CustomProgressDialog;

/**
 * Created by KKK on 2017/9/4.
 */

public class ProgressBarUtil {
    public static CustomProgressDialog mProgressDialog = null;

    public static void showProgress(Context context) {
        showProgress(context, "",true);}

    public static void showProgress(Context context,boolean hideProgress) {
        showProgress(context, "",hideProgress);}


    public static void showProgress(Context context,  int text) {
        showProgress(context,  context.getResources().getString(text),true);
    }
    public static CustomProgressDialog getProgress(){
        return mProgressDialog;
    }


    public synchronized static void showProgress(final Context context,  final String text,boolean hideProgress) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null == mProgressDialog || !mProgressDialog.isShowing()) {
                        dismissProgress(context);
                        mProgressDialog = new CustomProgressDialog(context);
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        mProgressDialog.mHideProgress.setVisibility(hideProgress? View.GONE:View.VISIBLE);
                        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface arg0) {
                            }
                        });
                        mProgressDialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public synchronized static void dismissProgress(Context context) {
        if (null != context) {
            if (context instanceof Activity && !((Activity) context).isFinishing()) {
                dismiss();
            } else {
                dismiss();
            }
        }
    }

    private static void dismiss() {
        try {
            if (null != mProgressDialog) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
