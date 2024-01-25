package guru.maze.avatar.net;


import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import guru.maze.avatar.utils.Constant;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author by xiaok
 * @date 2020/4/8
 */
public class AddResponseInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());


        String authorization = response.header("Authorization");
        if (!TextUtils.isEmpty(authorization)){
            Log.i(Constant.XIAOK,"获取响应头："+authorization);
            SPUtils.getInstance().put(Constant.ACCESS_TOKEN,authorization);
        }

        return  response;
    }


}
