package com.kunio.wanandroidclient.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kunio.wanandroidclient.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by zhc on 2017/9/5 0005.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).centerCrop().error(R.drawable.default_banner).into(imageView);
    }
}
