package com.anhvu.hackernewsreader.viewmodel;

import com.anhvu.hackernewsreader.HackerNewsApplication;
import com.anhvu.hackernewsreader.Util;
import com.anhvu.hackernewsreader.model.Comment;

/**
 * Created by leanh on 5/31/17.
 */

public class CommentViewModel {

    private Long id;
    private String time;
    private String author;
    private String comment;
    private int viewType;

    private int hierarchy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }

    public static CommentViewModel convertToViewModel(Comment comment) {
        CommentViewModel viewModel = new CommentViewModel();
        viewModel.setId(comment.getId());
        viewModel.setTime(Util.getPostedTime(comment.getTime(), HackerNewsApplication.getContext()));
        viewModel.setAuthor(comment.getBy());
        viewModel.setComment(comment.getText());
        return viewModel;
    }
}
