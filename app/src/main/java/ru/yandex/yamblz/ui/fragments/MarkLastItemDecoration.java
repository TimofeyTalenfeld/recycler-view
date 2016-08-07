package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by root on 8/7/16.
 */
public class MarkLastItemDecoration extends RecyclerView.ItemDecoration {

    private Paint paint = new Paint();

    private int from;
    private int to;

    private static final int MARK_RADIUS = 40;
    private static final int PADDING_LEFT = 15 + MARK_RADIUS;
    private static final int PADDING_TOP = 15 + MARK_RADIUS;

    {
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        drawMark(c, parent.getChildAt(from));
        drawMark(c, parent.getChildAt(to));

    }

    private void drawMark(Canvas canvas, View view) {

        canvas.drawCircle(view.getLeft() + view.getTranslationX() + PADDING_LEFT,
                view.getTop() + view.getTranslationY() + PADDING_TOP,
                MARK_RADIUS, paint);
    }

    public void decorate(int from, int to) {
        this.from = from;
        this.to = to;
    }

}
