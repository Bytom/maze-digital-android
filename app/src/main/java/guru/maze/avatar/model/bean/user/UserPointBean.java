package guru.maze.avatar.model.bean.user;

import com.google.gson.annotations.SerializedName;

/**
 * @author by xiaok
 * @date 2022/11/24
 */
public class UserPointBean {
    @SerializedName("member_level")
    public int memberLevel;
    @SerializedName("member_expires")
    public int memberExpires;
    @SerializedName("used_points_today")
    public int usedPointsToday;
    @SerializedName("remaining_trial_points_today")
    public int totalCurrentPoints;
    @SerializedName("fast_points_used_today")
    public int fastPointsUsedToday;
    @SerializedName("used_points_total")
    public int usedPointsTotal;
    @SerializedName("used_fast_points_total")
    public int usedFastPointsTotal;
    @SerializedName("rewarded_points_total")
    public int rewardedPointsTotal;
    @SerializedName("unlimited")
    public boolean unlimited;

    @SerializedName("can_painting")
    public boolean canPainting;
    @SerializedName("permanent_point")
    public int permanentPoint;
    @SerializedName("free_point_today")
    public  int free_point_today;
    @SerializedName("monthly_point")
    public int monthly_point;

    @SerializedName("experience_points")
    public int experiencePoints;
    @SerializedName("level")
    public int level;

    //TODO

    @SerializedName("member_subscription")
    public MemberSubscription memberSubscription;



    public static class MemberSubscription {
        @SerializedName("id")
        public Integer id;
        @SerializedName("user_id")
        public Integer userId;
        @SerializedName("order_id")
        public String orderId;
        @SerializedName("pay_id")
        public String payId;
        @SerializedName("goods_id")
        public Integer goodsId;
        @SerializedName("product_id")
        public String productId;
        @SerializedName("product_token")
        public String productToken;
        @SerializedName("level")
        public Integer level;
        @SerializedName("start")
        public Integer start;
        @SerializedName("expires")
        public int expires;
        @SerializedName("status")
        public int status;
    }


}
