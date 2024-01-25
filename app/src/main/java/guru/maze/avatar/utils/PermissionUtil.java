package guru.maze.avatar.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.UtilsTransActivity;
import com.hjq.permissions.IPermissionInterceptor;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import guru.maze.avatar.base.App;
import guru.maze.avatar.R;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/06
 *     desc  : 权限帮助类
 * </pre>
 */
public class PermissionUtil {

    public static boolean requestPermission(Activity activity, String permission, int requestCode) {
        //检查权限: 检查用户是不是已经授权
        int checkSelfPermission = ContextCompat.checkSelfPermission(App.getIns(), permission);
        //拒绝 : 检查到用户之前拒绝授权
        if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
            //申请权限
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        } else if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
            //已经授权
            return true;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
        return false;
    }


//    public static void requestStorage(final OnPermissionGrantedListener listener) {
//        request(listener, PermissionConstants.STORAGE);
//    }

    public static void requestImage(Context context,final OnPermissionGrantedListener listener) {
        XXPermissions.with(context).permission(Permission.READ_MEDIA_IMAGES)
                .interceptor(new IPermissionInterceptor() {
                    @Override
                    public void deniedPermissionRequest(@NonNull Activity activity, @NonNull List<String> allPermissions, @NonNull List<String> deniedPermissions, boolean doNotAskAgain, @Nullable OnPermissionCallback callback) {
                        ToastCustomUtil.showLocalErrorToast(context, R.string.permission_denied_forever_message);
                    }
                })
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                        if (allGranted) {
                            listener.onPermissionGranted();
                        }else {
                            ToastCustomUtil.showLocalErrorToast(context, R.string.permission_denied_forever_message);

                        }
                    }
                });
    }





    public static void requestPhone(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.PHONE);
    }

    public static void requestPhone(final OnPermissionGrantedListener grantedListener,
                                    final OnPermissionDeniedListener deniedListener) {
        request(grantedListener, deniedListener, PermissionConstants.PHONE);
    }

    public static void requestCAMERA(final OnPermissionGrantedListener listener){
        request(listener, PermissionConstants.CAMERA);
    }

    public static void requestCAMERA(final OnPermissionGrantedListener grantedListener,
                                    final OnPermissionDeniedListener deniedListener) {
        request(grantedListener, deniedListener, PermissionConstants.CAMERA);
    }








    public static void requestSms(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.SMS);
    }

    public static void requestLocation(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.LOCATION);
    }

    private static void request(final OnPermissionGrantedListener grantedListener,
                                final @PermissionConstants.PermissionGroup String... permissions) {
        request(grantedListener, null, permissions);
    }

    private static void request(final OnPermissionGrantedListener grantedListener,
                                final OnPermissionDeniedListener deniedListener,
                                final @PermissionConstants.PermissionGroup String... permissions) {
        PermissionUtils.permission(permissions)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(@NonNull @NotNull UtilsTransActivity activity, @NonNull @NotNull ShouldRequest shouldRequest) {
                        DialogHelper.showRationaleDialog(shouldRequest);
                    }


                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        if (grantedListener != null) {
                            grantedListener.onPermissionGranted();
                        }
                        LogUtils.d(permissionsGranted);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog();
                        }
                        if (deniedListener != null) {
                            deniedListener.onPermissionDenied();
                        }
                        LogUtils.d(permissionsDeniedForever, permissionsDenied);
                    }
                })
                .request();
    }

    public interface OnPermissionGrantedListener {
        void onPermissionGranted();
    }

    public interface OnPermissionDeniedListener {
        void onPermissionDenied();
    }
}
