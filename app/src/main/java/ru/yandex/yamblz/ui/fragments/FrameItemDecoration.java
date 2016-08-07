package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 8/7/16.
 */
public class FrameItemDecoration extends RecyclerView.ItemDecoration {

    private Paint paint = new Paint();

    private static final float STROKE_WIDTH = 15f;

    {
        paint.setColor(Color.TRANSPARENT);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        for (int i = 0; i < parent.getChildCount(); ++i) {
            if(i % 2 == 0) {

                View child = parent.getChildAt(i);

                c.drawRect(child.getLeft() + child.getTranslationX(),
                        child.getTop() + child.getTranslationY(),
                        child.getRight() + child.getTranslationX(),
                        child.getBottom() + child.getTranslationY(), paint);
            }
        }

    }

    public void setColors(int color) {
        paint.setColor(color);
    }

}
