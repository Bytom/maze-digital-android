package guru.maze.avatar.view.activity.setting;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.OnClick;
import guru.maze.avatar.base.BaseActivity;
import guru.maze.avatar.R;
import guru.maze.avatar.utils.NetConstant;

/**
 * @author by xiaok
 * @date 2023/6/26
 */
public class PolicyActivity extends BaseActivity {

    @Override
    public View initView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_policy, null);
    }

    @OnClick({ R.id.item_privacy_policy,R.id.item_user_policy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_privacy_policy:
                Intent intentPrivacy = new Intent(Intent.ACTION_VIEW, Uri.parse(NetConstant.MAZE_PRIVACY_POLICY));
                mContext.startActivity(intentPrivacy);
                break;
            case R.id.item_user_policy:
                Intent intentTerms = new Intent(Intent.ACTION_VIEW, Uri.parse(NetConstant.MAZE_TERMS_OF_SERVICE));
                mContext.startActivity(intentTerms);
                break;
        }
    }
}
