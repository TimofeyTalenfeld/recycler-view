package ru.yandex.yamblz.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import ru.yandex.yamblz.R;

public class ContentFragment extends BaseFragment {

    @BindView(R.id.rv) RecyclerView rv;

    private ItemTouchHelper.Callback callback;
    private ItemTouchHelper touchHelper;
    private RecyclerView.Adapter<ContentAdapter.ContentHolder> adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemAnimator animator;
    private FrameItemDecoration frameItemDecoration;
    private MarkLastItemDecoration markLastItemDecoration;

    private int spanCount = 1;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ContentAdapter();
        layoutManager = new GridLayoutManager(getContext(), spanCount);
        animator = new CustomItemAnimator();
        frameItemDecoration = new FrameItemDecoration();
        markLastItemDecoration = new MarkLastItemDecoration();

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        rv.setItemAnimator(animator);
        rv.addItemDecoration(frameItemDecoration);
        rv.addItemDecoration(markLastItemDecoration);

        optimize();

        callback = new ItemTouchHelperCallback((ItemTouchHelperAdapter) adapter, markLastItemDecoration);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rv);

    }

    private void optimize() {
        rv.setHasFixedSize(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    public void updateColumnSpan(int columnSpan) {
        spanCount = columnSpan;
        ((GridLayoutManager) layoutManager).setSpanCount(spanCount);
        layoutManager.requestLayout();
        notifyVisibleItemsChanged();
    }

    public void updateDecorationColors(int color) {
        frameItemDecoration.setColors(color);
        notifyVisibleItemsChanged();
    }

    private void notifyVisibleItemsChanged() {
        adapter.notifyItemRangeChanged(((GridLayoutManager) layoutManager).findFirstVisibleItemPosition(),
                ((GridLayoutManager) layoutManager).findLastVisibleItemPosition() - ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition());
    }

}
