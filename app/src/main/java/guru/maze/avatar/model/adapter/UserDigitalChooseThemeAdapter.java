package guru.maze.avatar.model.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import guru.maze.avatar.base.BaseAdapter;
import guru.maze.avatar.model.bean.digital.DigitalResourceBean;
import guru.maze.avatar.model.inter.CommonCallBack;
import guru.maze.avatar.R;

/**
 * @author by xiaok
 * @date 2023/10/20
 */
public class UserDigitalChooseThemeAdapter extends BaseAdapter<DigitalResourceBean, BaseViewHolder> {
    private   CommonCallBack<DigitalResourceBean> mIntegerCommonCallBack;
    public UserDigitalChooseThemeAdapter(List<DigitalResourceBean> data, CommonCallBack<DigitalResourceBean> commonCallBack) {
        super(R.layout.adaper_digital_theme, data);
        this.mIntegerCommonCallBack = commonCallBack;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, DigitalResourceBean item) {

        holder.setText(R.id.digital_theme,item.name);

        ImageView imageView = holder.getView(R.id.checkbox);

        if (mIntegerCommonCallBack.data!=null &&mIntegerCommonCallBack.data.id.equals(item.id)){
            imageView.setBackgroundResource(R.drawable.box_check);
        }else {
            imageView.setBackgroundResource(0);

        }
    }
}
