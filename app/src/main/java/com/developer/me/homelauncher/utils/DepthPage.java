package com.developer.me.homelauncher.utils;

import android.view.View;

import com.eftimoff.viewpagertransformers.BaseTransformer;

/**
 * Created by Sanidhya on 9/14/2017.
 */

public class DepthPage extends BaseTransformer {

    public DepthPage() {
    }

    protected void onTransform(View view, float position) {
        if(position <= 0.0F) {
            view.setTranslationX(0.0F);
            view.setScaleX(1.0F);
            view.setScaleY(1.0F);
        } else if(position <= 1.0F) {
            view.setAlpha((1F - position)/2);
            view.setPivotX(0.5F * (float)view.getWidth());
            view.setTranslationY((float)view.getHeight() * -position);
        }

    }

    protected boolean isPagingEnabled() {
        return true;
    }
}
