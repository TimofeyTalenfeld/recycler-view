package ru.yandex.yamblz.ui.fragments;

/**
 * Created by root on 7/27/16.
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int from, int to);
    void omItemDismiss(int position);
}
