package guru.maze.avatar.view.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import guru.maze.avatar.R;
import guru.maze.avatar.base.BaseActivity;
import guru.maze.avatar.model.adapter.UserDigitalChooseThemeAdapter;
import guru.maze.avatar.model.bean.digital.DigitalResourceBean;
import guru.maze.avatar.model.inter.CommonCallBack;
import guru.maze.avatar.net.RetrofitFactory;
import guru.maze.avatar.net.domain.DigitalServer;
import guru.maze.avatar.net.rx.BaseSubscriber;
import guru.maze.avatar.net.rx.RxUtil;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.ToastCustomUtil;
import guru.maze.avatar.view.widget.SimpleTitle;


/**
 * @author by xiaok
 * @date 2023/10/19
 */
public class CreateDigitalChooseThemeActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.simple_title)
    public SimpleTitle mSimpleTitle;
    @BindView(R.id.list_recy)
    RecyclerView mListRecycler;

    @BindView(R.id.bottom_container)
    public ConstraintLayout mBottomContainer;
    @BindView(R.id.click)
    public TextView mClick;

    private UserDigitalChooseThemeAdapter mUserDigitalChooseThemeAdapter;
    private CommonCallBack<DigitalResourceBean> mIntegerCommonCallBack;

    @Override
    public View initView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_base_list,null);

    }
    @Override
    protected void initialize() {

        mSimpleTitle.mTvTitle.setText(R.string.i_am);
        mBottomContainer.setVisibility(View.VISIBLE);
        mClick.setText(R.string.continue_text);

        initAdapter();
        initResourceNet();
    }

    private void initResourceNet() {

        RetrofitFactory.getRetrofit().create(DigitalServer.class).getDigitalResource()
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.commonResult(mContext))
                .subscribe(new BaseSubscriber<List<DigitalResourceBean>>() {
                    @Override
                    public void onNext(List<DigitalResourceBean> o) {
                        mUserDigitalChooseThemeAdapter.setNewInstance(o);

                    }
                });
    }

    private void initAdapter() {

        mIntegerCommonCallBack = new CommonCallBack<>();
        mIntegerCommonCallBack.data = null;
        mListRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mUserDigitalChooseThemeAdapter = new UserDigitalChooseThemeAdapter(null, mIntegerCommonCallBack);

        mListRecycler.setAdapter(mUserDigitalChooseThemeAdapter);
        mUserDigitalChooseThemeAdapter.setOnItemClickListener(this);

    }
    @OnClick(R.id.bottom_container)
    public void onClick() {
        //submit
        if (mIntegerCommonCallBack.data!=null){

            Intent intent = new Intent(mContext, CreateDigitalChooseStyleActivity.class);
            intent.putExtra(Constant.SERIALIZE_ONE,mIntegerCommonCallBack.data);
            startActivity(intent);
        }else {
            ToastCustomUtil.showLocalErrorToast(mContext,"");
        }
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

        DigitalResourceBean  resourceBean = (DigitalResourceBean) adapter.getData().get(position);

        mIntegerCommonCallBack.data =resourceBean;
        adapter.notifyDataSetChanged();;
    }
}
