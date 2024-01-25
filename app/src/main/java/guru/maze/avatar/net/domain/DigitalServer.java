package guru.maze.avatar.net.domain;

import java.util.List;

import guru.maze.avatar.BuildConfig;
import guru.maze.avatar.base.BaseResponse;
import guru.maze.avatar.model.bean.digital.DigitalArtBean;
import guru.maze.avatar.model.bean.digital.DigitalResourceBean;
import guru.maze.avatar.model.bean.digital.UserDigitalBean;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author by xiaok
 * @date 2023/10/19
 */
public interface DigitalServer {

    @GET(BuildConfig.Base_Url + "prompt-style/resources")
    Flowable<BaseResponse<List<DigitalResourceBean>>>getDigitalResource();

    @POST(BuildConfig.Base_Url+"prompt-style/create-image")
    Flowable<BaseResponse<List<DigitalArtBean>>> createDigitalImage(@Body ServerEntity.createDigitalImg entity);

    @GET(BuildConfig.Base_Url+"prompt-style/query?limit=20")
    Flowable<BaseResponse<List<UserDigitalBean>>> getMineDigital(@Query("start") int start);

    @GET(BuildConfig.Base_Url+"prompt-style/gets")
    Flowable<BaseResponse<List<DigitalArtBean>>> getDigitalListArt(@Query("ids") String ids);


    @GET(BuildConfig.Base_Url+"prompt-style/get")
    Flowable<BaseResponse<DigitalArtBean>> getDigitalArt(@Query("id") int id);




}
