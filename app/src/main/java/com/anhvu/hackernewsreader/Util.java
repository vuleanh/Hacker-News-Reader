package com.anhvu.hackernewsreader;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by leanh on 5/31/17.
 */

public class Util {

    public static String getPostedTime(Long time, Context context) {
        Date posted = new Date(time * 1000);
        Date now = new Date();

        if (TimeUnit.MILLISECONDS.toDays(now.getTime() - posted.getTime()) > 0) {
            int day = (int) TimeUnit.MILLISECONDS.toDays(now.getTime() - posted.getTime());
            return context.getResources().getQuantityString(R.plurals.dayAgo, day, day);
        } else if (TimeUnit.MILLISECONDS.toHours(now.getTime() - posted.getTime()) > 0) {
            int hour = (int) TimeUnit.MILLISECONDS.toHours(now.getTime() - posted.getTime());
            return context.getResources().getQuantityString(R.plurals.hourAgo, hour, hour);
        }

        if (TimeUnit.MILLISECONDS.toMinutes(now.getTime() - posted.getTime()) > 0) {
            int minute = (int) TimeUnit.MILLISECONDS.toMinutes(now.getTime() - posted.getTime());
            return context.getResources().getQuantityString(R.plurals.minuteAgo, minute, minute);
        } else if (TimeUnit.MILLISECONDS.toSeconds(now.getTime() - posted.getTime()) > 0) {
            int second = (int) TimeUnit.MILLISECONDS.toSeconds(now.getTime() - posted.getTime());
            return context.getResources().getQuantityString(R.plurals.secondAgo, second, second);
        }
        return "";
    }

    public static float dpToPixels(float dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String getCommentCountText(int count, Context context) {
        return context.getResources().getQuantityString(R.plurals.comment, count, count);
    }
}
