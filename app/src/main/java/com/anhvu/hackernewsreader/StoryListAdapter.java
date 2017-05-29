package com.anhvu.hackernewsreader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anhvu.hackernewsreader.model.Story;

import java.util.List;

/**
 * Created by leanh on 5/29/17.
 */

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.StoryViewHolder> {

    private List<Story> data;

    public StoryListAdapter(List<Story> data) {
        this.data = data;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_story_list, parent, false);
        return new StoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoryViewHolder holder, int position) {
        Story story = data.get(position);
        holder.txtIndex.setText(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {

        private TextView txtIndex, txtScore, txtTitle, txtTimeAndAuthor, txtUrl, txtCommentCount;

        public StoryViewHolder(View itemView) {
            super(itemView);
            txtIndex = (TextView) itemView.findViewById(R.id.txt_index);
            txtScore = (TextView) itemView.findViewById(R.id.txt_score);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtTimeAndAuthor = (TextView) itemView.findViewById(R.id.txt_time_author);
            txtUrl = (TextView) itemView.findViewById(R.id.txt_url);
            txtCommentCount = (TextView) itemView.findViewById(R.id.txt_comment_count);
        }
    }
}
