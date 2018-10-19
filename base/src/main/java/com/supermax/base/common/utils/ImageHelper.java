package com.supermax.base.common.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/*
 * @Author yinzh
 * @Date   2018/10/18 16:59
 * @Description :图片加载帮助类
 */
public class ImageHelper {

    private static ImageHelper helper;

    private ImageHelper() {
    }

    static ImageHelper getInstance() {
        if (helper == null) helper = new ImageHelper();
        return helper;
    }


    public Builder createRequest() {
        return new Builder(QsHelper.getInstance().getApplication());
    }

    public Builder createRequest(Context context) {
        return new Builder(context);
    }

    public Builder createRequest(Activity activity) {
        return new Builder(activity);
    }

    public Builder createRequest(FragmentActivity activity) {
        return new Builder(activity);
    }

    public Builder createRequest(android.support.v4.app.Fragment fragment) {
        return new Builder(fragment);
    }


    public class Builder{
        private boolean enableHolder = true;
        private RequestManager manager;
        private Object               mObject;
        private int                  placeholderId;
        private int                  errorId;
        private Drawable placeholderDrawable;
        private Drawable             errorDrawable;
        private boolean              centerCrop;
        private boolean              fitCenter;
        private boolean              centerInside;
        private int[]                mCorners;
        private int                  mWidth;
        private int                  mHeight;
        private boolean              noMemoryCache;
        private DiskCacheStrategy diskCacheStrategy;
        private BitmapTransformation mTransformation;
        private boolean              mIsCircleCrop;



        Builder(Context context) {
            manager = Glide.with(context);
        }

        Builder(Activity context) {
            manager = Glide.with(context);
        }

        Builder(Fragment context) {
            manager = Glide.with(context);
        }

        Builder(android.support.v4.app.Fragment context) {
            manager = Glide.with(context);
        }

        Builder(FragmentActivity context) {
            manager = Glide.with(context);
        }

        Builder(View context) {
            manager = Glide.with(context);
        }


    }
}
