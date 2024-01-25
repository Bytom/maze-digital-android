package guru.maze.avatar.model.adapter;


import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import guru.maze.avatar.base.BaseAdapter;
import guru.maze.avatar.R;

/**
 * @author by xiaok
 * @date 2021/9/16
 */
public class CenterTextAdapter extends BaseAdapter<String, BaseViewHolder> {
    public CenterTextAdapter(@Nullable List<String> data) {
        super(R.layout.view_center_text, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.center_text, item);
    }
}
