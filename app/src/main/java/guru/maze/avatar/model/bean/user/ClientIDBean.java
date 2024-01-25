package guru.maze.avatar.model.bean.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author by xiaok
 * @date 2022/11/9
 */
public class ClientIDBean {


    @SerializedName("client_id")
    public String clientId;

    @SerializedName("token")
    public String token;

    @SerializedName("prompts")
    public List<String> prompts;
    @SerializedName("model_ids")
    public List<Integer> modelIds;
}

