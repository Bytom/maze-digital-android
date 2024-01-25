package guru.maze.avatar.utils;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

/**
 * Created by xiaok on 2018/11/13.
 * 管理全局activitymanager
 */

public class ActivityManagerUtil {
    private static ActivityManagerUtil ins;

    private static final Stack<Activity> sActivityStack = new Stack<>();


    public static ActivityManagerUtil getIns() {
        if (ins == null) {
            synchronized (ActivityManagerUtil.class) {
                ins = new ActivityManagerUtil();
            }
        }
        return ins;
    }

    /**
     * 添加 Activity 到栈
     *
     * @param activity activity
     */
    public static void addActivity(Activity activity) {
        Log.i(Constant.XIAOK,"addActivity:"+activity.getClass().getName());
        sActivityStack.add(activity);

    }

    /**
     * 删除堆栈中的Activity
     */
    public void removeActivity(Activity activity) {
        if (sActivityStack.isEmpty()) {
            return;
        }
        sActivityStack.remove(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishTargetActivity(Activity activity) {
        if (activity != null) {
            sActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity 在遍历一个列表的时候不能执行删除操作，所有我们先记住要删除的对象，遍历之后才去删除。
     */
    public void finishSingleActivityByClass(Class<?> cls) {
        for (Activity activity : sActivityStack) {
            if (activity.getClass().equals(cls)) {
                finishTargetActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = sActivityStack.size(); i < size; i++) {
            if (null != sActivityStack.get(i)) {
                sActivityStack.get(i).finish();
            }
        }
        sActivityStack.clear();
    }


    /**
     * 重新刷新其他activity
     *
     * @param activity
     */
    public void recreateAllOtherActivity(Activity activity) {
        for (int i = 0, size = sActivityStack.size(); i < size; i++) {
            if (null != sActivityStack.get(i) && sActivityStack.get(i) != activity) {
                sActivityStack.get(i).recreate();
            }
        }
    }

    /**
     * 重新刷新其他activity
     *
     * @param activity
     */
    public void recreateAllActivity(Activity activity) {
        for (int i = 0, size = sActivityStack.size(); i < size; i++) {
            if (null != sActivityStack.get(i) && activity != sActivityStack.get(i)) {
                sActivityStack.get(i).recreate();
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExitBack() {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }


}
