package guru.maze.avatar.model.db.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author by xiaok
 * @date 2022/11/11
 */
@Entity
public class UserDBManager {
    @Id
    private Long id;

    private String userAvatar;
    private String userName;
    private String accessToken;
    private int UserID;


    public UserDBManager(UserDaoBuilder builder) {
        this.userName = builder.userName;
        this.userAvatar = builder.userAvatar;
        this.accessToken = builder.accessToken;
        this.UserID = builder.userID;
    }



    @Generated(hash = 80317314)
    public UserDBManager(Long id, String userAvatar, String userName,
            String accessToken, int UserID) {
        this.id = id;
        this.userAvatar = userAvatar;
        this.userName = userName;
        this.accessToken = accessToken;
        this.UserID = UserID;
    }



    @Generated(hash = 811508789)
    public UserDBManager() {
    }



    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getUserAvatar() {
        return this.userAvatar;
    }



    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }



    public String getUserName() {
        return this.userName;
    }



    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getAccessToken() {
        return this.accessToken;
    }



    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getUserID() {
        return this.UserID;
    }



    public void setUserID(int UserID) {
        this.UserID = UserID;
    }



    public static class UserDaoBuilder{
        private String userName;
        private String userAvatar;
        private String accessToken;
        private int userID;


        public UserDaoBuilder setUserName(String userName){
            this.userName = userName;
            return this;
        }

        public UserDaoBuilder setUserAvatar(String userAvatar){
            this.userAvatar = userAvatar;
            return this;
        }

        public UserDaoBuilder setAccessToken(String accessToken){
            this.accessToken = accessToken;
            return this;
        }

        public UserDaoBuilder setUserID(int userID){
            this.userID = userID;
            return this;
        }

        public UserDBManager build() {
            return new UserDBManager(this);
        }
    }
}
