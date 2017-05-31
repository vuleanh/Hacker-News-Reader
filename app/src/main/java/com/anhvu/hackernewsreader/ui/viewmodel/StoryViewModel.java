package com.anhvu.hackernewsreader.ui.viewmodel;

import com.anhvu.hackernewsreader.HackerNewsApplication;
import com.anhvu.hackernewsreader.Util;
import com.anhvu.hackernewsreader.model.Story;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by leanh on 5/31/17.
 */

public class StoryViewModel {

    Long id;
    String score;
    String title;
    String time;
    String author;
    String shortUrl;
    String commentCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    private static String getShortUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return url.getHost().replace("www.", "");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getScoreText(int score) {
        return "+" + score;
    }

    public static StoryViewModel convertToViewModel(Story story) {
        StoryViewModel viewModel = new StoryViewModel();
        viewModel.setId(story.getId());
        viewModel.setShortUrl(getShortUrl(story.getUrl()));
        viewModel.setTitle(story.getTitle());
        viewModel.setScore(getScoreText(story.getScore()));
        viewModel.setTime(Util.getPostedTime(story.getTime(), HackerNewsApplication.getContext()));
        viewModel.setAuthor(story.getBy());
        viewModel.setCommentCount(story.getDescendants() + "");
        return viewModel;
    }
}
