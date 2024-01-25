package guru.maze.avatar.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import guru.maze.avatar.R;
import guru.maze.avatar.model.adapter.CenterTextAdapter;
import guru.maze.avatar.view.widget.RecycleViewDivider;

/**
 * @author by xiaok
 * @date 2021/9/16
 */
public class CommonBottomDialog extends BaseDialog {

    public TextView mDialogTitle;
    private RecyclerView mDialogRecycler;
    public CenterTextAdapter mCenterTextAdapter;

    public CommonBottomDialog(Context context) {
        super(context);
        setContentView(initView());
        setFullScreenWidth();
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    protected View initView() {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_common_bottom, null);

        mDialogTitle = inflate.findViewById(R.id.tv_title);

        inflate.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mDialogRecycler = inflate.findViewById(R.id.recycler);

        mDialogRecycler.setLayoutManager(new LinearLayoutManager(mContext));

        mDialogRecycler.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.VERTICAL, ConvertUtils.dp2px( 16), mContext.getResources().getColor(R.color.common_line)));

        mCenterTextAdapter = new CenterTextAdapter(null);
        mDialogRecycler.setAdapter(mCenterTextAdapter);

        return inflate;
    }

}
