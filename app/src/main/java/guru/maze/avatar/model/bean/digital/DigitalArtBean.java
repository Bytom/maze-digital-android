package guru.maze.avatar.model.bean.digital;

import com.google.gson.annotations.SerializedName;

/**
 * @author by xiaok
 * @date 2023/10/23
 */

public class DigitalArtBean {

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
}
