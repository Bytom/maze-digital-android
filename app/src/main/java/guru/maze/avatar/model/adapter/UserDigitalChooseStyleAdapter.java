package guru.maze.avatar.model.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import guru.maze.avatar.base.BaseAdapter;
import guru.maze.avatar.model.bean.digital.DigitalResourceBean;
import guru.maze.avatar.R;
import guru.maze.avatar.model.inter.CommonCallBack;
import guru.maze.avatar.utils.GlideUtil;

/**
 * @author by xiaok
 * @date 2022/11/2
 */
public class UserDigitalChooseStyleAdapter extends BaseAdapter<DigitalResourceBean.Children, BaseViewHolder> {


    private CommonCallBack<DigitalResourceBean.Children> mCommonCallBack;
    private CommonCallBack<Integer> userLevel;
    public UserDigitalChooseStyleAdapter(List<DigitalResourceBean.Children> data, CommonCallBack<DigitalResourceBean.Children> commonCallBack, CommonCallBack<Integer> userLevel) {
        super(R.layout.adapter_digital_item, data);
        mCommonCallBack  = commonCallBack;
        this.userLevel  = userLevel;

    }


    @Override
    protected void convert(@NonNull BaseViewHolder holder, DigitalResourceBean.Children item) {

        ImageView view = holder.getView(R.id.img);
        GlideUtil.load(view.getContext(),item.backgroundUrl,12,view);

        holder.setText(R.id.style_title,item.name);

        ImageView checkBox = holder.getView(R.id.checkbox);

        View container = holder.getView(R.id.container);


        if (mCommonCallBack.data != null &&mCommonCallBack.data.id.equals(item.id) ) {
            container.setBackgroundResource(R.drawable.shape_white_corner12_juice_line);
            checkBox.setBackgroundResource(R.drawable.box_check);
        } else {
            container.setBackgroundResource(R.drawable.shape_white_corner12);
            checkBox.setBackgroundResource(0);
        }
        holder.setVisible(R.id.pro_tag, userLevel.data != 3);

//
    }
}
