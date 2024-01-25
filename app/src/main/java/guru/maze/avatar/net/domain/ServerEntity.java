package guru.maze.avatar.net.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author by xiaok
 * @date 2023/12/13
 */
public class ServerEntity {
    public static class createDigitalImg {
//"prompt_style_ids": [1,2,3],
//  "init_image_url": "https://abc.com",
//  "client": "discord" //测试数据

        @SerializedName("prompt_style_ids")
        public List<Integer> styleIds;
        @SerializedName("init_image_url")
        public String initImageUrl;
        @SerializedName("client")
        private String client = "app";
    }

    public static class loginGoogle {
        //  "id_token": "",
        //  "google_id": "",
        //  "name": "",
        //  "email": ""
        @SerializedName("id_token")
        public String idToken;
        @SerializedName("google_id")
        public String googleId;
        @SerializedName("name")
        public String name;
        @SerializedName("email")
        public String email;
    }
}
