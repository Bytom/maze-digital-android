package guru.maze.avatar.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import guru.maze.avatar.R;
/**
 * @author by xiaok
 * @date 2022/11/15
 */
public class DeleteArtDialog extends BaseDialog {

    public TextView mDeleteNow;
    public TextView mDesc;
    public TextView mNotNow;
    public TextView mTitle;

    public DeleteArtDialog(Context context) {
        super(context);
        setFullScreenWidth();
        setContentView(initView());
    }

    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_delete_art, null);

        mTitle = view.findViewById(R.id.title_tips);
        mDesc = view.findViewById(R.id.title_desc);
        mNotNow = view.findViewById(R.id.not_now);
               mNotNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mDeleteNow = view.findViewById(R.id.delete_now);
        return view;


    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }
}
