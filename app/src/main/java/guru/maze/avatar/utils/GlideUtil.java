package guru.maze.avatar.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import androidx.core.content.ContextCompat;
import guru.maze.avatar.base.BaseActivity;
import guru.maze.avatar.view.widget.GlideRoundTransform;
import guru.maze.avatar.R;
/**
 * @author by xiaok
 * @date 2022/11/3
 */
public class GlideUtil {

    public static void loadBord(Context context, String url, int radius, int bordColor, ImageView imageView) {
        RequestOptions transform = new RequestOptions().transform(new GlideRoundTransform(context, radius, ContextCompat.getColor(context, bordColor), 1));
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .apply(transform)
                    .into(imageView);
        } else {
            Glide.with(context).load(R.drawable.error_glide).apply(transform).into(imageView);
        }
    }


    public static void loadAvatar(Context context, String url, ImageView imageView) {
        if (context instanceof BaseActivity){
            if (((BaseActivity) context).isDestroyed())return;
        }
        if (imageView==null)return;
        Glide.with(context).load(url).apply(new RequestOptions().placeholder(R.drawable.default_avatar).error(R.drawable.default_avatar)).into(imageView);
    }

    public static void load(Context context, String url, int radius, ImageView imageView) {
       if (context instanceof BaseActivity){
           if (((BaseActivity) context).isDestroyed())return;
       }
        RequestOptions transform = new RequestOptions().transform(new GlideRoundTransform(context, radius))
               .placeholder(R.drawable.shape_placeholder).error(R.drawable.error_glide);
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .apply(transform)
                    .into(imageView);
        } else {
            Glide.with(context).load(R.drawable.error_glide).apply(transform).into(imageView);
        }
    }

    public static void load(Context context, String url, int radius,int placeHolder, ImageView imageView) {
        if (context instanceof BaseActivity){
            if (((BaseActivity) context).isDestroyed())return;
        }
        RequestOptions transform = new RequestOptions().transform(new GlideRoundTransform(context, radius))
                                           .placeholder(placeHolder).error(R.drawable.error_glide);
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .apply(transform)
                    .into(imageView);
        } else {
            Glide.with(context).load(R.drawable.error_glide).apply(transform).into(imageView);
        }
    }

}
