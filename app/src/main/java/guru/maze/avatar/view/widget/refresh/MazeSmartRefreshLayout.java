package guru.maze.avatar.view.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;


/**
 * @author by xiaok
 * @date 2020/4/13
 */
public class MazeSmartRefreshLayout extends SmartRefreshLayout {
    public MazeSmartRefreshLayout(Context context) {
        this(context, null);
    }

    public MazeSmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RefreshHeaderView refreshHeaderView = new RefreshHeaderView(context);
        refreshHeaderView.setLayoutParams(layoutParams);
        addView(refreshHeaderView, 0);
    }
}
