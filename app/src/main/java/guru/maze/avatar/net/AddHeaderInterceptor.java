package guru.maze.avatar.net;


import com.blankj.utilcode.util.SPUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import guru.maze.avatar.model.db.user.UserDBHelper;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.LocalLanguageUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author by xiaok
 * @date 2020/4/8
 */
public class AddHeaderInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();


        Request.Builder requestBuilder = originalRequest.newBuilder()
                                                 .addHeader("from", "android")
                                                 .addHeader("Authorization", SPUtils.getInstance().getString(Constant.ACCESS_TOKEN))
                                                 .method(originalRequest.method(), originalRequest.body());


        HttpUrl.Builder urlBuilder = originalRequest.url().newBuilder();
        urlBuilder.addQueryParameter("user", UserDBHelper.getIns().queryUserID() + "");


        urlBuilder.addQueryParameter("language", LocalLanguageUtils.getServerLanguage());
        requestBuilder.url(urlBuilder.build());

        Request request = requestBuilder.build();


        //
        return chain.proceed(request);
    }


}
