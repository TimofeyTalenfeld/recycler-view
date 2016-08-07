package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.animation.LinearInterpolator;

/**
 * Created by root on 7/27/16.
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperAdapter adapter;
    private MarkLastItemDecoration decoration;

    private Paint paint = new Paint();

    public ItemTouchHelperCallback(ItemTouchHelperAdapter adapter, MarkLastItemDecoration decoration) {

        this.adapter = adapter;
        this.decoration = decoration;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;

        return makeMovementFlags(dragFlags, ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        decoration.decorate(viewHolder.getLayoutPosition(), target.getLayoutPosition());
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            decorateBackground(c, viewHolder, dX, viewHolder.itemView.getWidth());
        }

    }

    private void decorateBackground(Canvas c, RecyclerView.ViewHolder viewHolder, float dX, int width) {

        int alpha = (int) (255 * (1 - (1.0f * width - Math.abs(dX)) / width));

        paint.setAlpha(alpha);
        c.drawRect(viewHolder.itemView.getLeft(), viewHolder.itemView.getTop(),
                viewHolder.itemView.getRight(), viewHolder.itemView.getBottom(), paint);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.omItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }


}
