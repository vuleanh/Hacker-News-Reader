package com.anhvu.hackernewsreader.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.anhvu.hackernewsreader.Constant;
import com.anhvu.hackernewsreader.R;
import com.anhvu.hackernewsreader.Util;
import com.anhvu.hackernewsreader.model.Comment;
import com.anhvu.hackernewsreader.model.Story;
import com.anhvu.hackernewsreader.ui.adapter.CommentListAdapter;
import com.anhvu.hackernewsreader.ui.viewmodel.CommentViewModel;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoryDetailActivity extends AppCompatActivity {

    private RecyclerView rcvCommentList;
    private CommentListAdapter adapter;
    private TextView txtContent, txtUrl, txtTime, txtAuthor, txtCommentCount;

    private Long storyID;
    private Firebase baseRef;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.story);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_story_detail);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        rcvCommentList = (RecyclerView) findViewById(R.id.recycler_view_comment_list);
        txtContent = (TextView) findViewById(R.id.txt_content);
        txtUrl = (TextView) findViewById(R.id.txt_url);
        txtTime = (TextView) findViewById(R.id.txt_story_time);
        txtAuthor = (TextView) findViewById(R.id.txt_story_author);
        txtCommentCount = (TextView) findViewById(R.id.txt_story_comment_count);

        Intent intent = getIntent();
        storyID = intent.getLongExtra(Constant.KEY_STORY_ID, 0);

        baseRef = new Firebase(Constant.FIREBASE_URL);
        rcvCommentList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommentListAdapter(this);
        rcvCommentList.setAdapter(adapter);
        updateStory(storyID);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateStory(Long id) {
        dialog.setMessage(getString(R.string.loading));
        dialog.show();
        Firebase story = baseRef.child("/item/" + id);

        story.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Story story = dataSnapshot.getValue(Story.class);
                updateView(story);
                // get first 10 comments

                List<Long> kids;
                if (story.getKids() != null && story.getKids().size() >= 10) {
                    kids = story.getKids().subList(0, 9);
                } else {
                    kids = story.getKids();
                }
                if (dialog != null) {
                    dialog.hide();
                }
                updateCommentList(kids);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void updateView(Story story) {
        txtContent.setText(story.getTitle() != null ? Html.fromHtml(story.getTitle()) : "");
        txtUrl.setText(story.getUrl());
        txtTime.setText(Util.getPostedTime(story.getTime(), this) + getString(R.string.divider));
        txtAuthor.setText(story.getBy());
        txtCommentCount.setText(Util.getCommentCountText(story.getDescendants(), this));
    }

    private void updateCommentList(List<Long> ids) {
        if (ids != null) {
            for (Long id : ids) {
                updateSingleComment(id, null);
            }
        }
    }

    private void updateSingleComment(Long id, CommentViewModel parent) {
        final CommentViewModel par = parent;
        Firebase story = baseRef.child("item/" + id);

        story.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Comment com = dataSnapshot.getValue(Comment.class);
                CommentViewModel viewModel = CommentViewModel.convertToViewModel(com);
                if (par == null) {
                    viewModel.setHierarchy(0);
                    adapter.add(viewModel);
                    if (com.getKids() != null && !com.getKids().isEmpty()) {
                        // get first reply
                        updateSingleComment(com.getKids().get(0), viewModel);
                    }
                } else {
                    viewModel.setHierarchy(par.getHierarchy() + 1);
                    adapter.add(adapter.getPosition(par) + 1, viewModel);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_right, R.anim.exit_right);
    }
}
