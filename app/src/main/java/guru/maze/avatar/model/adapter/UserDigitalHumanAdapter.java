package guru.maze.avatar.model.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import guru.maze.avatar.base.BaseResponse;
import guru.maze.avatar.model.bean.digital.DigitalArtBean;
import guru.maze.avatar.model.bean.digital.UserDigitalBean;
import guru.maze.avatar.model.inter.CommonInterface;
import guru.maze.avatar.net.RetrofitFactory;
import guru.maze.avatar.net.domain.DigitalServer;
import guru.maze.avatar.net.rx.BaseSubscriber;
import guru.maze.avatar.net.rx.RxUtil;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.DecimalUtil;
import guru.maze.avatar.utils.DigitUtil;
import guru.maze.avatar.utils.GlideUtil;
import guru.maze.avatar.utils.Params;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import guru.maze.avatar.R;

/**
 * @author by xiaok
 * @date 2023/10/20
 */
public class UserDigitalHumanAdapter extends BaseMultiItemQuickAdapter<UserDigitalBean.Artworks, BaseViewHolder> implements LoadMoreModule {

    private CommonInterface<Boolean> mCommonInterface;


    private Map<Integer, BaseSubscriber<Long>> map = new HashMap<>();


    public UserDigitalHumanAdapter(List<UserDigitalBean.Artworks> data, CommonInterface<Boolean> commonInterface) {
        this.mCommonInterface = commonInterface;
        addItemType(UserDigitalBean.COMPLETE, R.layout.adapter_digital_item);
        addItemType(UserDigitalBean.BUILDING, R.layout.adapter_digital_load_building);
        addItemType(UserDigitalBean.FAILED, R.layout.view_fail_reason);

    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, UserDigitalBean.Artworks item) {
        int itemViewType = holder.getItemViewType();
        switch (itemViewType) {
            case UserDigitalBean.BUILDING:
                setBuildData(holder, item);
//                break;
            case UserDigitalBean.COMPLETE:
                setCompleteData(holder, item);
                break;
            case UserDigitalBean.FAILED:

                break;


        }
    }

    private void setBuildData(BaseViewHolder holder, UserDigitalBean.Artworks item) {

        if (map.get(item.id) == null) {
            BaseSubscriber<Long> baseSubscriber = Flowable.interval(2, TimeUnit.SECONDS).compose(RxUtil.<Long>rxSchedulerHelper()).subscribeWith(new BaseSubscriber<Long>() {
                @SuppressLint("CheckResult")
                @Override
                public void onNext(Long aLong) {
                    RetrofitFactory.getRetrofit().create(DigitalServer.class).getDigitalArt(item.id).
                            subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<BaseResponse<DigitalArtBean>>() {
                                @Override
                                public void accept(BaseResponse<DigitalArtBean> digitalArtBeanBaseResponse) throws Throwable {

                                    if (digitalArtBeanBaseResponse.getCode()==200){
                                        String percent = item.completePercent;
                                        if (percent.equals("1")) {
                                            //加载完成了

                                            if (map.get(item.id) != null) {
                                                Log.i(Constant.XIAOK, "滞空数据");
                                                BaseSubscriber<Long> longBaseSubscriber = map.get(item.id);
                                                longBaseSubscriber.dispose();
                                                longBaseSubscriber = null;
                                                map.put(item.id, null);
                                            }
                                            mCommonInterface.callBack(true);
                                        } else {
                                            TextView tvProgress = holder.getView(R.id.tv_progress);
                                            ProgressBar progressBar = holder.getView(R.id.progress_bar);
                                            String multiply = DecimalUtil.multiplyNoZero(percent, "100");
                                            tvProgress.setText(multiply + Params.PERCENT);
                                            progressBar.setProgress((int) DigitUtil.parseDouble(multiply));
                                        }
                                    }else {
                                        if (map.get(item.id) != null) {
                                            Log.i(Constant.XIAOK, "滞空数据");
                                            BaseSubscriber<Long> longBaseSubscriber = map.get(item.id);
                                            longBaseSubscriber.dispose();
                                            longBaseSubscriber = null;
                                            map.put(item.id, null);
                                        }
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Throwable {
                                    if (map.get(item.id) != null) {
                                        Log.i(Constant.XIAOK, "滞空数据");
                                        BaseSubscriber<Long> longBaseSubscriber = map.get(item.id);
                                        longBaseSubscriber.dispose();
                                        longBaseSubscriber = null;
                                        map.put(item.id, null);
                                    }
                                }
                            });


                }
            });
            map.put(item.id, baseSubscriber);
        }
    }

    private void setCompleteData(BaseViewHolder holder, UserDigitalBean.Artworks item) {

        int itemViewType = holder.getItemViewType();
        if (itemViewType==UserDigitalBean.COMPLETE){
            ImageView view = holder.getView(R.id.img);
                Log.i(Constant.XIAOK,"itemURL:"+item.url);
                GlideUtil.load(view.getContext(), item.url, 12, view);
        }
    }



    @NotNull
    @Override
    public BaseLoadMoreModule addLoadMoreModule(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        return new BaseLoadMoreModule(this);
    }
}
