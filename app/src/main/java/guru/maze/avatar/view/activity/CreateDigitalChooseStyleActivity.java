package guru.maze.avatar.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;
import guru.maze.avatar.base.BaseActivity;
import guru.maze.avatar.R;
import guru.maze.avatar.model.adapter.UserDigitalChooseStyleAdapter;
import guru.maze.avatar.model.bean.digital.DigitalResourceBean;
import guru.maze.avatar.model.bean.user.UserPointBean;
import guru.maze.avatar.model.inter.CommonCallBack;
import guru.maze.avatar.model.inter.CommonInterface;
import guru.maze.avatar.model.user.GlobalImpl;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.ToastCustomUtil;
import guru.maze.avatar.view.widget.SimpleTitle;

/**
 * @author by xiaok
 * @date 2023/10/19
 */
public class CreateDigitalChooseStyleActivity extends BaseActivity implements OnItemClickListener {
    @BindView(R.id.simple_title)
    public SimpleTitle mSimpleTitle;
    @BindView(R.id.list_recy)
    RecyclerView mListRecycler;

    @BindView(R.id.click)
    public TextView mClick;
    @BindView(R.id.bottom_container)
    public ConstraintLayout mBottomContainer;
    @BindView(R.id.click_num)
    TextView mClickNum;
    private DigitalResourceBean mResourceBean;
    private UserDigitalChooseStyleAdapter mUserDigitalChooseStyleAdapter;
    private CommonCallBack<Integer> mLevelCallBack;
    private int  mUserLevel;
    private CommonCallBack<DigitalResourceBean.Children> mCommonCallBack;

    @Override
    public View initView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_base_list, null);

    }

    @Override
    public void initSerialization(Bundle extras) {
        mResourceBean = (DigitalResourceBean) extras.getSerializable(Constant.SERIALIZE_ONE);
    }

    @Override
    protected void initialize() {

        mSimpleTitle.mTvTitle.setText(R.string.i_want_to_be);
        mBottomContainer.setVisibility(View.VISIBLE);
        mClick.setText(R.string.continue_text);

        initAdapter();
        initUserLevel();
    }

    private void initUserLevel() {
        GlobalImpl.getIns().getUserPointAvailable(mContext, new CommonInterface<UserPointBean>() {
            @Override
            public void callBack(UserPointBean userPointBean) {
                mUserLevel = userPointBean.memberLevel;
                mLevelCallBack.data = mUserLevel;
                mUserDigitalChooseStyleAdapter.notifyDataSetChanged();

            }
        });

    }

    private void initAdapter() {


        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mListRecycler.setLayoutManager(manager);
        mCommonCallBack = new CommonCallBack<>();
        mCommonCallBack.data = null;

        mLevelCallBack = new CommonCallBack<>();
        mLevelCallBack.data = 0;
        mUserDigitalChooseStyleAdapter = new UserDigitalChooseStyleAdapter(mResourceBean.children, mCommonCallBack,mLevelCallBack);
        mListRecycler.setAdapter(mUserDigitalChooseStyleAdapter);

        mUserDigitalChooseStyleAdapter.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {


        if (mUserLevel==3){
            DigitalResourceBean.Children children = (DigitalResourceBean.Children) adapter.getData().get(position);

            mCommonCallBack.data = children;
            ((SimpleItemAnimator) adapter.getRecyclerView().getItemAnimator()).setSupportsChangeAnimations(false);
            adapter.notifyItemRangeChanged(0, adapter.getData().size());

        }else {
            //TODO
            GlobalImpl.getIns().showGradeLevelDialog(mContext);
        }

//        if (mCommonCallBack.data!=null){
////            mClick.setText(getString(R.string.continue_text)+"("+mCommonCallBack.data.size()+")");
//            mClickNum.setVisibility(View.VISIBLE);
//            mClickNum.setText("1");
//
//        }else {
//            mClickNum.setVisibility(View.GONE);
//
//        }
    }

    @OnClick(R.id.bottom_container)
    public void onClick() {
        //submit
        if (mCommonCallBack.data != null) {

//            if (mCommonCallBack.data.size()<=4){
//                Intent intent = new Intent(mContext, CreateDigitalHumanActivity.class);
//
//                List<Integer> collect = mCommonCallBack.data.stream().map(item -> item.id).collect(Collectors.toList());
//
//                List<Integer> points = mCommonCallBack.data.stream().map(item -> item.points).collect(Collectors.toList());
//                int totalPoint=0;
//                for (Integer point : points) {
//                    totalPoint+=point;
//                }
//                intent.putExtra(Constant.SERIALIZE_ONE,new ArrayList<>(collect));
//
//
//                intent.putExtra(Constant.SERIALIZE_TWO,totalPoint);
//
//                startActivity(intent);
//            }else {
//                ToastCustomUtil.showLocalErrorToast(mContext, R.string.max_4);
//
//            }
            Intent intent = new Intent(mContext, CreateDigitalHumanActivity.class);
            List<Integer> collect = new ArrayList<>();
            collect.add(mCommonCallBack.data.id);

            intent.putExtra(Constant.SERIALIZE_ONE, new ArrayList<>(collect));
            intent.putExtra(Constant.SERIALIZE_TWO, mCommonCallBack.data.points);

            startActivity(intent);

        } else {
            ToastCustomUtil.showLocalErrorToast(mContext, R.string.choose_at_least_one);
        }
    }
}
