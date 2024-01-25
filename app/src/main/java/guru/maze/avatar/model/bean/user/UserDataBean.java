package guru.maze.avatar.model.bean.user;

import com.google.gson.annotations.SerializedName;

/**
 * @author by xiaok
 * @date 2022/11/10
 */
public class UserDataBean {
    @SerializedName("id")
    public Integer id;
    @SerializedName("avatar")
    public String avatar;
    @SerializedName("name")
    public String name;
    @SerializedName("visible")
    public String visible;
    @SerializedName("token")
    public String accessToken;


}
