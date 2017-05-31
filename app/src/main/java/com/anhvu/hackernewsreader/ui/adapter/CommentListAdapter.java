package com.anhvu.hackernewsreader.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anhvu.hackernewsreader.R;
import com.anhvu.hackernewsreader.Util;
import com.anhvu.hackernewsreader.model.Comment;
import com.anhvu.hackernewsreader.ui.viewmodel.CommentViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by leanh on 5/29/17.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {

    private Context context;
    private List<CommentViewModel> data;
    private int margin4DpUnit, margin16DpUnit;

    public CommentListAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
        margin4DpUnit = (int) Util.dpToPixels(4, context);
        margin16DpUnit = (int) Util.dpToPixels(16, context);
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment_list, parent, false);
        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        CommentViewModel comment = data.get(position);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        if (position < getItemCount() - 1 && getItem(position + 1).getHierarchy() == 0) {
            lp.setMargins(margin16DpUnit * (comment.getHierarchy() + 1), 0, 0, margin16DpUnit);
        } else {
            lp.setMargins(margin16DpUnit * (comment.getHierarchy() + 1), 0, 0, margin4DpUnit);
        }
        holder.container.setLayoutParams(lp);
        holder.txtTime.setText(comment.getTime() + " - ");
        holder.txtAuthor.setText(comment.getAuthor());
        holder.txtComment.setText(comment.getComment() != null ? Html.fromHtml(comment.getComment()).toString() : "");
    }

    public int getPosition(CommentViewModel c) {
        for (int i = 0; i < getItemCount(); i++) {
            if (data.get(i).equals(c)) {
                return i;
            }
        }
        return -1;
    }

    public CommentViewModel getItem(int position) {
        return data.get(position);
    }

    public void add(CommentViewModel comment) {
        data.add(comment);
    }

    public void add(int position, CommentViewModel comment) {
        data.add(position, comment);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTime, txtAuthor, txtComment;
        private CardView container;

        public CommentViewHolder(View itemView) {
            super(itemView);
            container = (CardView) itemView.findViewById(R.id.container);
            txtTime = (TextView) itemView.findViewById(R.id.txt_time);
            txtAuthor = (TextView) itemView.findViewById(R.id.txt_author);
            txtComment = (TextView) itemView.findViewById(R.id.txt_comment);
        }
    }
}
