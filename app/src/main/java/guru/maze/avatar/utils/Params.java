package guru.maze.avatar.utils;

/**
 * @author by xiaok
 * @date 2022/11/2
 */
public class Params {
    public static final int DEFAULT_DECIMAL = 4;
    public static final String DEFAULT_ZERO = "0.00";

    public static final String PRIVATE = "private";
    public static final String PUBLIC = "public";

    public static final String ADD = "+";
    public static final String SUB = "-";
    public static final String SLASH = "/";
    public static final String PERCENT = "%";
    public static final String DOLLAR = "＄";

    public static final String MAZE_SCHEME ="mazeai://";


    public static final String MemberSubscriptionYear=  "MemberSubscriptionYear";
    public static final String MemberSubscriptionHalfYear=  "MemberSubscriptionHalfYear";
    public static final String MemberSubscriptionMonth=  "MemberSubscription";

    public static final String PointsPurchase = "PointsPurchase";
    public static final String MONTH=  "month";
    public static final String YEAR=  "year";
    public static final String HALF_YEAR=  "half_year";


//    public static final String GOOGLE_TOKEN = "962533567909-507f4mbqqubjgootna7bik3ghbvqe2nf.apps.googleusercontent.com";
    public static final String GOOGLE_TOKEN = "695496474639-46knjjq64qlpepg1vb2o7cv3906d6qet.apps.googleusercontent.com";

    public enum ORDER_BY {
        CREATED_AT("created_at"),
        SORT_PRIORITY("recommend_time");






       public String type;
        ORDER_BY(String type) {
            this.type = type;
        }
    }

    public static class GooglePaySub{
        public static final String STANDARD_MONTHLY = "google_member_subscription_level_2";
        public static final String STANDARD_YEARLY = "google_member_subscription_year_level_2";
        public static final String PRO_MONTHLY = "google_member_subscription_level_3";
        public static final String PRO_YEARLY = "google_member_subscription_year_level_3";
        public static final String PRO_HALF_YEAR = "google_subscription_pro_half_year";
        public static final String STANDARD_HALF_YEAR = "google_subscription_standard_half_year";

    }


    public static class GooglePayPurchase{
        public static final String Purchase_3 = "pointspurchase_usd_3";
        public static final String Purchase_5 = "pointspurchase_usd_5";
        public static final String Purchase_10 = "pointspurchase_usd_10";

    }


}
