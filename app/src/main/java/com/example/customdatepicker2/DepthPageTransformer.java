package com.example.customdatepicker2;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

public class DepthPageTransformer implements ViewPager2.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {
        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));

        page.setScaleX(scaleFactor);
        page.setScaleY(scaleFactor);

        page.setAlpha(1 - Math.abs(position));
    }
}