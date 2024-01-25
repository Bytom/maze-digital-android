package guru.maze.avatar.model.bean.digital;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import guru.maze.avatar.model.bean.digital.DigitalSectionEntity;


/**
 * @author by xiaok
 * @date 2023/10/20
 */

public class UserDigitalBean {


    @SerializedName("id")
    public Integer id;
    @SerializedName("completed_num")
    public Integer completedNum;
    @SerializedName("combined_num")
    public Integer combinedNum;
    @SerializedName("timestamp")
    public Integer timestamp;
    @SerializedName("artworks")
    public List<Artworks> artworks;


    public static final int BUILDING = 1;
    public static final int COMPLETE = 0;
    public static final int FAILED = 2;

    public static final int HEADER = 5;


    public static class Artworks extends DigitalSectionEntity implements Serializable {
        @SerializedName("id")
        public Integer id;
        @SerializedName("combine_id")
        public Integer combineId;
        @SerializedName("model_id")
        public Integer modelId;
        @SerializedName("resolution_id")
        public Integer resolutionId;
        @SerializedName("prompt")
        public String prompt;
        @SerializedName("complete_percent")
        public String completePercent;
        @SerializedName("init_image_url")
        public String initImageUrl;
        @SerializedName("init_image_similarity")
        public Integer initImageSimilarity;
        @SerializedName("num")
        public Integer num;
        @SerializedName("timestamp")
        public Integer timestamp;
        @SerializedName("url")
        public String url;
        @SerializedName("prompt_style_parent_name")
        public String prompt_style_parent_name;
        @SerializedName("prompt_style_name")
        public String prompt_style_name;
        @SerializedName("status")
        public int status;


        @SerializedName("header")
        public boolean header;

        @Override
        public boolean isHeader() {
            return header;
        }


        @Override
        public int getDigitalType() {
            return status == 9 ? FAILED : "1".equals(completePercent) ? COMPLETE : BUILDING;
        }
    }

}
