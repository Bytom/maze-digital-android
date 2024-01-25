package guru.maze.avatar.net.rx;


import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

/**
 * Created by xiaok on 2018/11/27.
 * 自己不处理结果！
 */

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onError(Throwable t) {
//        Log.d("xiaok","错误信息："+t.getMessage());
        //处理常见的几种连接错误
//        EventResponse msgEvent;
//        Resources resources = App.getIns().getApplicationContext().getResources();
        if (t instanceof SocketTimeoutException) {

        } else if (t instanceof ConnectException) {
        } else if (t instanceof UnknownHostException) {
        }else if (t instanceof NullPointerException){
        }

    }


    @Override
    public void onComplete() {

    }
}
