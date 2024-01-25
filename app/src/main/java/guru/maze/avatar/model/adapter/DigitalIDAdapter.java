package guru.maze.avatar.model.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import guru.maze.avatar.base.BaseAdapter;
import guru.maze.avatar.R;
import guru.maze.avatar.utils.GlideUtil;

/**
 * @author by xiaok
 * @date 2023/12/22
 */
public class DigitalIDAdapter extends BaseAdapter<String, BaseViewHolder> {
    public DigitalIDAdapter( List<String> data) {
        super(R.layout.adapter_digital_id, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String item) {

        ImageView img = holder.getView(R.id.img_id);
        GlideUtil.loadAvatar(img.getContext(),item,img);
    }
}
