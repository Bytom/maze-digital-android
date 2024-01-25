package guru.maze.avatar.view.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import guru.maze.avatar.base.BaseFragment;
import guru.maze.avatar.R;
import guru.maze.avatar.model.adapter.StaggeredGridAdapter;

/**
 * @author by xiaok
 * @date 2023/12/13
 */
public class HomeFragment  extends BaseFragment {
    private Context mContext;
    @BindView(R.id.popular_recycler)
    RecyclerView mRecyclerView;
    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container) {
        mContext = getActivity();
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_home,null);
    }

    @Override
    protected void initView() {


        initAdapter();

    }

    private void initAdapter() {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(manager);

        StaggeredGridAdapter staggeredGridAdapter = new StaggeredGridAdapter(null);
        mRecyclerView.setAdapter(staggeredGridAdapter);

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        staggeredGridAdapter.setNewInstance(list);


    }
}
