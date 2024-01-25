package guru.maze.avatar.utils;

import android.text.TextUtils;

/**
 * Created by xiaok on 2018/10/24.
 * 数字资产 的 相关工具类
 */

public class DigitUtil {

    public static int parseInt(String value) {
        int ret = 0;
        if (!TextUtils.isEmpty(value)) {
            try {
                ret = Integer.parseInt(value);
            } catch (Exception e) {
                return 0;
            }
        }
        return ret;
    }

    /**
     * 以亿为单位
     *
     * @param value
     * @return
     */
    public static long parseBTMLong(String value) {
        long ret = 0;
        if (!TextUtils.isEmpty(value)) {
            try {
                ret = Long.parseLong(value);
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public static long parseLong(String value) {
        long ret = 0;
        if (!TextUtils.isEmpty(value)) {
            try {
                ret = Long.parseLong(value);
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public static long parseSeedLong(String value) {
        long ret = -1L;
        if (!TextUtils.isEmpty(value)) {
            try {
                ret = Long.parseLong(value);
            } catch (Exception e) {
                return -1L;
            }
        }
        return ret;
    }

    public static float parseFloat(String value) {
        float ret = 0;
        if (!TextUtils.isEmpty(value)) {
            try {
                ret = Float.parseFloat(value);
            } catch (Exception e) {
            }
        }
        return ret;
    }


    public static double parseDouble(String value) {
        double ret = 0d;
        if (!TextUtils.isEmpty(value)) {
            try {
                ret = Double.parseDouble(value);
            } catch (Exception e) {
                return 0d;
            }
        }

        return ret;
    }


}
