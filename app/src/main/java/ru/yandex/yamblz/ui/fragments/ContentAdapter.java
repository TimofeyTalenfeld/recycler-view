package ru.yandex.yamblz.ui.fragments;

import android.animation.Animator;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ru.yandex.yamblz.R;

class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> implements ItemTouchHelperAdapter {

    private final Random rnd = new Random();
    private final List<Integer> colors = new ArrayList<>();

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContentHolder contentHolder = new ContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item, parent, false));

        contentHolder.itemView.setOnClickListener((v) -> {
            if(contentHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                createColorForPosition(contentHolder.getAdapterPosition());
                notifyItemChanged(contentHolder.getAdapterPosition());
            }
        });

        return contentHolder;
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        holder.bind(createColorForPosition(position));
        holder.itemView.animate().setInterpolator(new LinearInterpolator()).setDuration(750)
            .setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    holder.itemView.animate().setInterpolator(new LinearInterpolator()).setDuration(0)
                            .rotationX(0)
                            .start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            })
            .rotationXBy(360)
            .start();
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    private Integer createColorForPosition(int position) {
        if (position >= colors.size()) {
            colors.add(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
        } else {
            colors.set(position, Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
        }
        return colors.get(position);
    }

    @Override
    public void onItemMove(int from, int to) {
        Collections.swap(colors, from, to);
        notifyItemMoved(from, to);
    }

    @Override
    public void omItemDismiss(int position) {
        colors.remove(position);
        notifyItemRemoved(position);
    }

    static class ContentHolder extends RecyclerView.ViewHolder {
        ContentHolder(View itemView) {
            super(itemView);
        }

        void bind(Integer color) {
            itemView.setBackgroundColor(color);
            ((TextView) itemView).setText("#".concat(Integer.toHexString(color).substring(2)));

        }
    }
}
