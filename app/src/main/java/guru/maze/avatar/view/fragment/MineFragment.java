package guru.maze.avatar.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ClipboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;
import guru.maze.avatar.R;
import guru.maze.avatar.base.BaseFragment;
import guru.maze.avatar.model.adapter.DigitalIDAdapter;
import guru.maze.avatar.model.adapter.UserDigitalHumanAdapter;
import guru.maze.avatar.model.bean.digital.UserDigitalBean;
import guru.maze.avatar.model.db.user.UserDBHelper;
import guru.maze.avatar.model.db.user.UserDBManager;
import guru.maze.avatar.model.inter.CommonInterface;
import guru.maze.avatar.net.RetrofitFactory;
import guru.maze.avatar.net.domain.DigitalServer;
import guru.maze.avatar.net.rx.BaseSubscriber;
import guru.maze.avatar.net.rx.RxUtil;
import guru.maze.avatar.utils.CommonUtil;
import guru.maze.avatar.utils.Constant;
import guru.maze.avatar.utils.GlideUtil;
import guru.maze.avatar.view.activity.DigitalDetailActivity;
import guru.maze.avatar.view.activity.setting.SettingActivity;
import guru.maze.avatar.view.widget.CircleImageView;
import guru.maze.avatar.view.widget.refresh.MazeSmartRefreshLayout;

/**
 * @author by xiaok
 * @date 2023/12/13
 */
public class MineFragment extends BaseFragment implements View.OnClickListener, OnItemClickListener, OnLoadMoreListener, OnRefreshListener {

    private Context mContext;
    @BindView(R.id.mine_recycler)
    RecyclerView mMineRecycler;
    @BindView(R.id.swipe)
    MazeSmartRefreshLayout mSwipe;
    private View mHeaderView;
    private View mUserBg;
    private TextView allPoints;
    private UserDigitalHumanAdapter mUserDigitalHumanAdapter;
    private int pageNo;

    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container) {
       mContext = getActivity();
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_mine,null);
    }

    @Override
    protected void initView() {
        initHeader();


        initNet(false);

        initDigitalAdapter();


    }

    private void initDigitalAdapter() {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mMineRecycler.setLayoutManager(manager);

        mUserDigitalHumanAdapter = new UserDigitalHumanAdapter(null, new CommonInterface<Boolean>() {
            @Override
            public void callBack(Boolean aBoolean) {
                if (aBoolean) {
                    pageNo = 0;
                    initNet(false);
                }
            }
        });
        mMineRecycler.setAdapter(mUserDigitalHumanAdapter);
        mUserDigitalHumanAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        CommonUtil.setEmptyAdapter(mContext, mUserDigitalHumanAdapter);
        mUserDigitalHumanAdapter.setOnItemClickListener(this);
        mUserDigitalHumanAdapter.addHeaderView(mHeaderView);
        mSwipe.setOnRefreshListener(this);



    }

    private void initHeader() {
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.view_mine_header, null);


        CircleImageView userAvatar = mHeaderView.findViewById(R.id.user_avatar);

        UserDBManager userDBManager = UserDBHelper.getIns().queryUserData();

        if (userDBManager==null)return;
        GlideUtil.loadAvatar(mContext, userDBManager.getUserAvatar(), userAvatar);



        mHeaderView.findViewById(R.id.manage_id).setOnClickListener(this);
        mHeaderView.findViewById(R.id.manage_digi).setOnClickListener(this);



        userAvatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (userDBManager != null) {
                    ClipboardUtils.copyText(userDBManager.getUserID() + "");
                }
                return false;
            }
        });

        mUserBg = mHeaderView.findViewById(R.id.user_container);
//        permanentPoints = mHeaderView.findViewById(R.id.permanent_points);
//        subPoints = mHeaderView.findViewById(R.id.sub_points);
        allPoints = mHeaderView.findViewById(R.id.all_points);



        RecyclerView myIdRecycler = mHeaderView.findViewById(R.id.my_id_recycler);

        myIdRecycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        DigitalIDAdapter digitalIDAdapter = new DigitalIDAdapter(null);
        myIdRecycler.setAdapter(digitalIDAdapter);
        digitalIDAdapter.setOnItemClickListener(this);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        digitalIDAdapter.setNewInstance(list);

//
    }




    @OnClick({ R.id.setting})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.manage_id:
                //TODO


                break;

            case R.id.manage_digi:
                break;
            case R.id.setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }

    private void initNet(boolean loadMore) {
        RetrofitFactory.getRetrofit().create(DigitalServer.class).getMineDigital(pageNo).compose(RxUtil.rxSchedulerHelper()).compose(RxUtil.commonResult(mContext)).subscribe(new BaseSubscriber<List<UserDigitalBean>>() {
            @Override
            public void onNext(List<UserDigitalBean> userDigitalBean) {
                if (mSwipe == null) return;
                if (loadMore) {
                    if (userDigitalBean != null && userDigitalBean.size() != 0) {
                        List<UserDigitalBean.Artworks> list = new ArrayList<>();
                        for (UserDigitalBean digitalBean : userDigitalBean) {
                            UserDigitalBean.Artworks artworks = new UserDigitalBean.Artworks();
                            artworks.timestamp = digitalBean.timestamp;
                            artworks.header = true;
                            list.add(artworks);
                            list.addAll(digitalBean.artworks);
                        }
                        mUserDigitalHumanAdapter.addData(list);
                        mUserDigitalHumanAdapter.getLoadMoreModule().loadMoreComplete();

                    } else {
                        mUserDigitalHumanAdapter.getLoadMoreModule().loadMoreEnd(true);
                    }
                    if (mSwipe.isRefreshing()) {
                        mSwipe.finishRefresh();
                    }
                } else {
                    List<UserDigitalBean.Artworks> list = new ArrayList<>();
                    for (UserDigitalBean digitalBean : userDigitalBean) {
                        UserDigitalBean.Artworks artworks = new UserDigitalBean.Artworks();
                        artworks.timestamp = digitalBean.timestamp;
                        artworks.header = true;
                        list.add(artworks);
                        list.addAll(digitalBean.artworks);
                    }
                    mUserDigitalHumanAdapter.setNewInstance(list);
                    mUserDigitalHumanAdapter.getLoadMoreModule().loadMoreComplete();

                    if (mSwipe.isRefreshing()) {
                        mSwipe.finishRefresh();
                    }
                }


            }
        });
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (adapter instanceof DigitalIDAdapter){
            //TODO



        }

        if (adapter instanceof UserDigitalHumanAdapter){
            UserDigitalBean.Artworks o = (UserDigitalBean.Artworks) adapter.getData().get(position);
            if (!o.header && "1".equals(o.completePercent)) {
                Intent intent = new Intent(mContext, DigitalDetailActivity.class);
                intent.putExtra(Constant.SERIALIZE_ONE, o);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onLoadMore() {
        pageNo += 10;
        initNet(true);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo=0;
        initNet(false);
    }
}
