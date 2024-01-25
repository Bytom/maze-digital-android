package guru.maze.avatar.net;

import android.content.Context;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import guru.maze.avatar.model.bean.user.ClientIDBean;
import guru.maze.avatar.model.inter.CommonInterface;
import guru.maze.avatar.net.domain.MazeBusinessServer;
import guru.maze.avatar.net.rx.BaseSubscriber;
import guru.maze.avatar.net.rx.RxUtil;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.NetConstant;

/**
 * @author by xiaok
 * @date 2022/12/16
 */
public class QiniuyunFactory {

    private UploadManager sUploadManager;

    private static class Holder {
        private static QiniuyunFactory INSTANCE = new QiniuyunFactory();
    }

    public static QiniuyunFactory getIns() {
        return Holder.INSTANCE;
    }


    private UploadManager getUploadManager() {
        //产生卡顿的原因应该是Retrofit初始化问题！！
        if (sUploadManager == null) {
            //config配置上传参数
            Configuration configuration = new Configuration.Builder()
                                                  .connectTimeout(10)
                                                  //.zone(zone)
                                                  //.dns(buildDefaultDns())//指定dns服务器
                                                  .responseTimeout(60).build();
            //this.uploadManager = new UploadManager(fileRecorder);
            sUploadManager = new UploadManager(configuration);
        }
        return sUploadManager;
    }


    public void upLoad2Qiniuyun(Context context, UploadOptions options, byte[] upLoadFile, String key, CommonInterface<String> callBack) {
        RetrofitFactory.getRetrofit().create(MazeBusinessServer.class).getReferenceImageToken()
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.commonResult(context))
                .subscribe(new BaseSubscriber<ClientIDBean>() {
                    @Override
                    public void onNext(ClientIDBean clientIDBean) {
                        String token = clientIDBean.token;
                        Log.i(Constant.XIAOK, "token:" + token);

                        getUploadManager().put(upLoadFile, key, token,
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String key, ResponseInfo info, JSONObject res) {
                                        //res 包含 hash、key 等信息，具体字段取决于上传策略的设置
                                        if (info.isOK()) {
                                            Log.i(Constant.XIAOK, "Upload Success");
                                            callBack.callBack(NetConstant.WU_JIE_BAN_TU + key);
                                        } else {
                                            callBack.callBack("");
                                            Log.i(Constant.XIAOK, "Upload Fail");
                                            //如果失败，这里可以把 info 信息上报自己的服务器，便于后面分析上传错误原因
                                        }
                                        Log.i(Constant.XIAOK, key + ",\r\n " + info + ",\r\n " + res);
                                    }
                                }, options);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Log.i(Constant.XIAOK, "onError:Upload Fail");

                        callBack.callBack("");
                    }
                });

    }


}
