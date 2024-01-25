package guru.maze.avatar.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import guru.maze.avatar.R;


/**
 * @author by xiaok
 * @date 2022/11/11
 */
public class CommonUtil {

    public static void showNetPopup(Context context, int code, String msg) {
        switch (code) {
            case 1001:
//                ToastCustomUtil.showNetErrorToast(context, context.getString(R.string.alert_right_phone));
                break;
            case 1003:
                break;
            default:
                ToastCustomUtil.showNetErrorToast(context, msg);
                break;
        }
    }



    /**
     * 版本号比较
     *
     * @param version1
     * @param version2
     * @return 0代表相等，1代表version1大于version2，-1代表version1小于version2
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        if (TextUtils.isEmpty(version1) || TextUtils.isEmpty(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    public static void toUpdateApp(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            //可以接收
            context.startActivity(intent);
        } else {
            //没有应用市场，我们通过浏览器跳转到Google Play
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName()));
            context.startActivity(intent);
        }
    }
    public static void setRightIcon(Context context, TextView view,int res) {

        Drawable drawable = context.getResources().getDrawable(res  );
        view.setCompoundDrawablesWithIntrinsicBounds(null,
                null, drawable, null);
        view.setCompoundDrawablePadding(ConvertUtils.dp2px(3));
    }

    public static void setEmptyAdapter(Context context, BaseMultiItemQuickAdapter adapter) {
        View empty = LayoutInflater.from(context).inflate(R.layout.view_empty_data, null);
//        TextView emptydata = empty.findViewById(R.id.empty_data);
//        emptydata.setText(R.string.empty_data);
        adapter.setHeaderWithEmptyEnable(true);
        adapter.setEmptyView(empty);
    }
}
