package guru.maze.avatar.model.bean.digital;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author by xiaok
 * @date 2023/10/20
 */

public class DigitalResourceBean implements Serializable{


    @SerializedName("id")
    public Integer id;
    @SerializedName("parent_id")
    public Integer parentId;
    @SerializedName("model_id")
    public Integer modelId;
    @SerializedName("resolution_id")
    public Integer resolutionId;
    @SerializedName("step")
    public Integer step;
    @SerializedName("type")
    public Integer type;
    @SerializedName("name")
    public String name;
    @SerializedName("prompt")
    public String prompt;
    @SerializedName("background_url")
    public String backgroundUrl;
    @SerializedName("sort_num")
    public Integer sortNum;
    @SerializedName("points")
    public Integer points;
    @SerializedName("children")
    public List<Children> children;


    public static class Children implements Serializable {


        @SerializedName("id")
        public Integer id;
        @SerializedName("parent_id")
        public Integer parentId;
        @SerializedName("model_id")
        public Integer modelId;
        @SerializedName("resolution_id")
        public Integer resolutionId;
        @SerializedName("step")
        public Integer step;
        @SerializedName("type")
        public Integer type;
        @SerializedName("name")
        public String name;
        @SerializedName("prompt")
        public String prompt;
        @SerializedName("background_url")
        public String backgroundUrl;
        @SerializedName("sort_num")
        public Integer sortNum;
        @SerializedName("points")
        public Integer points;
        @SerializedName("children")
        public List<Children> children;
    }

}
