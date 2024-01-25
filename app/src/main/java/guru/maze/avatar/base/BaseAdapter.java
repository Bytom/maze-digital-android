package guru.maze.avatar.base;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by xiaok on 2018/9/14.
 * 根据BRVAH 的adapter抽取中间层
 */

public abstract class BaseAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> implements LoadMoreModule {


    public BaseAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);

    }

    public void publicConvert(K helper, T item) {
        convert(helper, item);
    }

    public K createVH(ViewGroup parent, int viewType) {
        return createViewHolder(parent, viewType);
    }


    @NotNull
    @Override
    public BaseLoadMoreModule addLoadMoreModule(@NotNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        return new BaseLoadMoreModule(this);
    }
}
