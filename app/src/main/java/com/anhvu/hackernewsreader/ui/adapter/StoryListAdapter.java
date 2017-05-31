package com.anhvu.hackernewsreader.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anhvu.hackernewsreader.ui.listener.OnItemClickListener;
import com.anhvu.hackernewsreader.R;
import com.anhvu.hackernewsreader.ui.viewmodel.StoryViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leanh on 5/29/17.
 */

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.StoryViewHolder> {

    private List<StoryViewModel> data;
    private OnItemClickListener listener;

    public StoryListAdapter(OnItemClickListener listener) {
        this.data = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_story_list, parent, false);
        return new StoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoryViewHolder holder, int position) {
        StoryViewModel story = data.get(position);
        holder.txtIndex.setText(getIndex(position));
        holder.txtScore.setText(story.getScore());
        holder.txtTitle.setText(story.getTitle() != null ? Html.fromHtml(story.getTitle()).toString() : "");
        holder.txtTimeAndAuthor.setText(getTimeAndAuthorText(story.getTime(), story.getAuthor()));
        holder.txtUrl.setText(story.getShortUrl());
        holder.txtCommentCount.setText(story.getCommentCount());

    }

    private String getIndex(int position) {
        return String.valueOf(position + 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private String getTimeAndAuthorText(String time, String author) {
        return time + " - " + author;
    }


    public void add(StoryViewModel story) {
        data.add(story);
    }

    class StoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtIndex, txtScore, txtTitle, txtTimeAndAuthor, txtUrl, txtCommentCount;

        public StoryViewHolder(View itemView) {
            super(itemView);
            txtIndex = (TextView) itemView.findViewById(R.id.txt_index);
            txtScore = (TextView) itemView.findViewById(R.id.txt_score);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtTimeAndAuthor = (TextView) itemView.findViewById(R.id.txt_time_author);
            txtUrl = (TextView) itemView.findViewById(R.id.txt_url);
            txtCommentCount = (TextView) itemView.findViewById(R.id.txt_comment_count);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(data.get(getAdapterPosition()).getId());
        }
    }
}
