package com.anhvu.hackernewsreader.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.anhvu.hackernewsreader.Constant;
import com.anhvu.hackernewsreader.ui.listener.OnItemClickListener;
import com.anhvu.hackernewsreader.R;
import com.anhvu.hackernewsreader.model.Story;
import com.anhvu.hackernewsreader.ui.adapter.StoryListAdapter;
import com.anhvu.hackernewsreader.ui.viewmodel.StoryViewModel;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private static final int ITEM_UPDATE_NUM = 15;
    private static final int VISIBLE_THRESHOLD = 1;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rcvStoryList;
    private StoryListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;

    private List<Long> storyIDs;

    private Firebase baseRef;

    private boolean isLoading;
    private int loadedStoryID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        rcvStoryList = (RecyclerView) findViewById(R.id.recycler_view_story_list);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_load_more);

        refreshLayout.setColorSchemeResources(R.color.toolBarColor);
        getSupportActionBar().setTitle(R.string.top_stories);

        layoutManager = new LinearLayoutManager(this);
        rcvStoryList.setLayoutManager(layoutManager);

        baseRef = new Firebase(Constant.FIREBASE_URL);

        refresh();

        rcvStoryList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = layoutManager.getItemCount();
                int lastItemIndex = layoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastItemIndex + VISIBLE_THRESHOLD)) {
                    isLoading = true;
                    progressBar.setVisibility(View.VISIBLE);
                    updateListStory(ITEM_UPDATE_NUM);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }


    private void refresh() {
        loadedStoryID = 0;
        adapter = new StoryListAdapter(this);
        rcvStoryList.setAdapter(adapter);
        loadedStoryID = adapter.getItemCount();
        updateTopStories();
    }

    private void updateListStory(int itemNum) {
        int start = loadedStoryID;
        for (; loadedStoryID < start + itemNum && loadedStoryID < storyIDs.size(); loadedStoryID++) {
            updateSingleStory(storyIDs.get(loadedStoryID));
        }
    }


    private void updateSingleStory(Long storyId) {
        Firebase story = baseRef.child(Constant.ITEM_CHILD_URL + storyId);

        story.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StoryViewModel story = StoryViewModel.convertToViewModel(dataSnapshot.getValue(Story.class));
                adapter.add(story);
                adapter.notifyDataSetChanged();
                isLoading = false;
                refreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void updateTopStories() {
        refreshLayout.setRefreshing(true);
        Firebase topStories = baseRef.child(Constant.TOP_STORIES_CHILD_URL);

        topStories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storyIDs = (ArrayList<Long>) dataSnapshot.getValue();
                updateListStory(ITEM_UPDATE_NUM);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onClick(Long storyID) {
        Intent i = new Intent(this, StoryDetailActivity.class);
        i.putExtra(Constant.KEY_STORY_ID, storyID);
        startActivity(i);
        overridePendingTransition(R.anim.enter_left, R.anim.exit_left);
    }
}
