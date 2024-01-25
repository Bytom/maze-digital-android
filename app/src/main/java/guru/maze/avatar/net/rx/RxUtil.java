package guru.maze.avatar.net.rx;

import android.content.Context;
import android.util.Log;

import guru.maze.avatar.base.BaseResponse;
import guru.maze.avatar.utils.CommonUtil;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.base.BaseActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by xiaok on 2018/11/27.
 * 重新封装Rxjava
 */

public class RxUtil {
    /**
     * 简化线程操作
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */

    public static <T> FlowableTransformer<BaseResponse<T>, T> commonResult(Context context) {   //compose判断结果
        return new FlowableTransformer<BaseResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<BaseResponse<T>> commonResponseFlowable) {
                return commonResponseFlowable.flatMap(new Function<BaseResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseResponse<T> t) throws Exception {
                        if (t.getCode() == 200 || t.getCode() == 0) {
                            if (context instanceof BaseActivity) {
                                if (((BaseActivity) context).isDestroyed()) {
                                    return null;
                                }
                            }
                            return createFlowable(t.getResult());
                        } else {
                            Log.i(Constant.XIAOK,"这里失败的网络请求:"+t.getCode()+"---"+t.getMsg());
                            //TODO
                            if (t.getCode() == 1006||t.getCode()==1032) {
                                //登录失效
                                //这里触发了登陆失效？？

//                                UserDBHelper.getIns().loginOutAccount();
//                                ToastCustomUtil.showLocalErrorToast(context,t.getMsg());
                                return null;
                            }
                            CommonUtil.showNetPopup(context, t.getCode(), t.getMsg());
                            return null;
                        }
                    }
                });
            }


        };
    }


    /**
     * 创建Flowable
     */

    private static <T> Flowable<T> createFlowable(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

}
