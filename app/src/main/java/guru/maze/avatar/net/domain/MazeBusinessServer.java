package guru.maze.avatar.net.domain;

import guru.maze.avatar.BuildConfig;
import guru.maze.avatar.base.BaseResponse;
import guru.maze.avatar.model.bean.user.ClientIDBean;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;

/**
 * @author by xiaok
 * @date 2023/12/14
 */
public interface MazeBusinessServer {
    @GET(BuildConfig.Base_Url+"get-init-image-token")
    Flowable<BaseResponse<ClientIDBean>> getReferenceImageToken();
}
