package guru.maze.avatar.net.domain;

import guru.maze.avatar.BuildConfig;
import guru.maze.avatar.base.BaseResponse;
import guru.maze.avatar.model.bean.user.UserDataBean;
import guru.maze.avatar.model.bean.user.UserPointBean;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author by xiaok
 * @date 2023/12/14
 */
public interface UserAccountServer {

    @GET(BuildConfig.Base_Url + "point-info")
    Flowable<BaseResponse<UserPointBean>> getDrawPointCount();

    @POST(BuildConfig.Base_Url + "login-google-by-android")
    Flowable<BaseResponse<UserDataBean>> login2Google(@Body ServerEntity.loginGoogle entity);
}
