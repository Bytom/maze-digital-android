package guru.maze.avatar.model.user;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.CacheDoubleUtils;
import com.google.gson.Gson;

import java.util.List;

import guru.maze.avatar.model.bean.user.UserPointBean;
import guru.maze.avatar.model.inter.CommonInterface;
import guru.maze.avatar.net.RetrofitFactory;
import guru.maze.avatar.net.domain.UserAccountServer;
import guru.maze.avatar.net.rx.BaseSubscriber;
import guru.maze.avatar.net.rx.RxUtil;
import guru.maze.avatar.view.dialog.DeleteArtDialog;
import guru.maze.avatar.R;

/**
 * @author by xiaok
 * @date 2022/11/16
 */
public class GlobalImpl {

    private DeleteArtDialog mGradeLevelDialog;

    private static class Holder {
        private static GlobalImpl INSTANCE = new GlobalImpl();
    }

    public static GlobalImpl getIns() {
        return Holder.INSTANCE;
    }




    public void getUserPointAvailable(Context context, CommonInterface<UserPointBean> commonInterface) {
        RetrofitFactory.getRetrofit().create(UserAccountServer.class).getDrawPointCount().compose(RxUtil.rxSchedulerHelper()).compose(RxUtil.commonResult(context)).subscribe(new BaseSubscriber<UserPointBean>() {
            @Override
            public void onNext(UserPointBean artCountBean) {
                commonInterface.callBack(artCountBean);
            }

            @Override
            public void onError(Throwable t) {
                commonInterface.callBack(null);
            }
        });

    }









    private void initGradeLevelDialog(Context context) {
        if (mGradeLevelDialog == null) {
            mGradeLevelDialog = new DeleteArtDialog(context);
        }
        mGradeLevelDialog.mNotNow.setText(context.getText(R.string.cancel));
        mGradeLevelDialog.mDeleteNow.setText(context.getText(R.string.upgrade));
        mGradeLevelDialog.mDeleteNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGradeLevelDialog.dismiss();
//                context.startActivity(new Intent(context, BuyProfileActivity.class));
            }
        });
        mGradeLevelDialog.mDesc.setText(R.string.upgrade_dialog_desc);
    }

    public void showGradeLevelDialog(Context context) {
        if (mGradeLevelDialog == null) {
            initGradeLevelDialog(context);
        }
        if (context == null) return;
        mGradeLevelDialog.show();
    }

}
