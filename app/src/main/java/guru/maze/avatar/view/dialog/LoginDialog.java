package guru.maze.avatar.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.SpanUtils;

import androidx.core.content.ContextCompat;
import guru.maze.avatar.R;
import guru.maze.avatar.utils.NetConstant;
import guru.maze.avatar.utils.ToastCustomUtil;

/**
 * @author by xiaok
 * @date 2022/11/3
 */
public class LoginDialog extends BaseDialog {


    public View mLoginGoogle;

    public LoginDialog(Context context) {
        super(context);
        setFullScreenWidth();
        setContentView(initView());
    }


    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    private View initView() {

        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_login, null);


        TextView policy = inflate.findViewById(R.id.privacy_policy);

        policy.setText(new SpanUtils().append(mContext.getString(R.string.login_policy1))
                               .append(mContext.getString(R.string.terms_of_service)).setUnderline().setForegroundColor(ContextCompat.getColor(mContext, R.color.base_juice))
                               .append(mContext.getString(R.string.and))
                               .append(mContext.getString(R.string.privacy_policy)).setUnderline().setForegroundColor(ContextCompat.getColor(mContext, R.color.base_juice))
                               .create());
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTerms = new Intent(Intent.ACTION_VIEW, Uri.parse(NetConstant.MAZE_TERMS_OF_SERVICE));
                mContext.startActivity(intentTerms);
            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.login_chuanyin:
                        ToastCustomUtil.showSuccessToast(mContext,"登陆传音");
                        break;

                    default:
                        break;

                }
            }
        };
        inflate.findViewById(R.id.login_chuanyin).setOnClickListener(onClickListener);
        mLoginGoogle = inflate.findViewById(R.id.login_google);

        return inflate;
    }
}
