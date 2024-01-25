package guru.maze.avatar.utils;

import android.text.TextUtils;

import java.math.BigDecimal;


/**
 * @author by xiaok
 * @date 2020/5/13
 */
public class DecimalUtil {

    /**
     * @param a
     * @param b
     * @return true A>B  false A<B
     */
    public static boolean compareTo(String a, String b) {

        if (DigitUtil.parseDouble(a) == 0) {
            a = "0";
        }
        if (DigitUtil.parseDouble(b) == 0) {
            b = "0";
        }
        BigDecimal strA = new BigDecimal(a);
        BigDecimal strB = new BigDecimal(b);

        return strA.subtract(strB).doubleValue() > 0;
    }

    public static boolean compareTo(String a) {
        if (DigitUtil.parseDouble(a) == 0) {
            return compareTo("0", "0");
        }
        return compareTo(a, "0");
    }


    public static boolean compareToEquals(String a) {
        if (DigitUtil.parseDouble(a) == 0) {
            return compareTo("0", "0");
        }
        return compareTo(a, "0");
    }

    public static boolean compareToEquals(String a, String b) {

        if (DigitUtil.parseDouble(a) == 0) {
            a = "0";
        }
        if (DigitUtil.parseDouble(b) == 0) {
            b = "0";
        }
        BigDecimal strA = new BigDecimal(a);
        BigDecimal strB = new BigDecimal(b);

        return strA.subtract(strB).doubleValue() >= 0;
    }


    public static boolean compareIsEquals(String a, String b) {

        if (DigitUtil.parseDouble(a) == 0) {
            a = "0";
        }
        if (DigitUtil.parseDouble(b) == 0) {
            b = "0";
        }
        BigDecimal strA = new BigDecimal(a);
        BigDecimal strB = new BigDecimal(b);

        return strA.subtract(strB).doubleValue() == 0;
    }


    public static boolean compareIsZero(String a) {
        if (DigitUtil.parseDouble(a) == 0) {
            return true;
        }
        return new BigDecimal(a).doubleValue() == 0;
    }


    public static double Dvalue(String base, int progress) {
        BigDecimal strA = new BigDecimal(base).multiply(new BigDecimal(100));
        BigDecimal strB = new BigDecimal(progress);
        return strB.divide(strA, Params.DEFAULT_DECIMAL, BigDecimal.ROUND_DOWN).doubleValue();
    }

    public static double Dvalue(String base, String current) {

        BigDecimal strA = new BigDecimal(base).multiply(new BigDecimal(100));
        BigDecimal strB = new BigDecimal(current).multiply(new BigDecimal(100));
        return strB.subtract(strA).doubleValue();
    }


    public static String add(String a, String b) {
        if (TextUtils.isEmpty(a)||TextUtils.isEmpty(b))return Params.DEFAULT_ZERO;

        return new BigDecimal(a).add(new BigDecimal(b)).toPlainString();
    }

    public static String sub(String a, String b) {
        if (TextUtils.isEmpty(a)||TextUtils.isEmpty(b))return Params.DEFAULT_ZERO;

        return new BigDecimal(a).subtract(new BigDecimal(b)).toPlainString();
    }


    public static String divide(String a, String b) {
        if (TextUtils.isEmpty(a)||TextUtils.isEmpty(b))return Params.DEFAULT_ZERO;

        try {
            return new BigDecimal(a).divide(new BigDecimal(b), 2, BigDecimal.ROUND_DOWN).toPlainString();

        }catch (Exception e){
            return "0";
        }
    }

    public static String multiply(String a, String b) {
        if (TextUtils.isEmpty(a)||TextUtils.isEmpty(b))return Params.DEFAULT_ZERO;

        try {
            return BigDecimal.valueOf(DigitUtil.parseDouble(a)).multiply(BigDecimal.valueOf(DigitUtil.parseDouble(b))).toPlainString();

        }catch (Exception e){
            return "0";
        }
    }

    public static String multiplyNoZero(String a, String b) {
        if (TextUtils.isEmpty(a)||TextUtils.isEmpty(b))return Params.DEFAULT_ZERO;
        return new BigDecimal(a).multiply(new BigDecimal(b)).toBigInteger().toString();
    }



}
