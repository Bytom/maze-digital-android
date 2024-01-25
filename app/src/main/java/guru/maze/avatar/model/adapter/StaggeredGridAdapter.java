package guru.maze.avatar.model.adapter;

import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import guru.maze.avatar.base.BaseAdapter;
import guru.maze.avatar.R;
import guru.maze.avatar.utils.GlideUtil;

/**
 * @author by xiaok
 * @date 2022/11/2
 */
public class StaggeredGridAdapter extends BaseAdapter<String, BaseViewHolder> {


    public StaggeredGridAdapter(List<String> data) {
        super(R.layout.adapter_staggerdgrid, data);
    }

    @Override
    protected void convert(@NonNull @NotNull BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.img);

        imageView.setBackgroundResource(R.drawable.demo1);

//        GlideUtil.load(getContext(), item, 12, imageView);


    }



}
