package com.example.android.technews;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.ArrayList;

public class TechNewsLoader extends AsyncTaskLoader<ArrayList<TechNews>> {

    //Tag for log messages

    private static final String LOG_TAG = TechNewsLoader.class.getName();

    //Querying provided URL

    private String mURL;

    //Constructing a loader for TechNewsLoader.

    public TechNewsLoader(Context context, String url) {
        super(context);

        mURL = url;
    }

    @Override
    public ArrayList<TechNews> loadInBackground() {

        if (mURL == null) {
            return null;
        }


        ArrayList<TechNews> techNews = QueryUtils.fetchArticleData(mURL);
        return techNews;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (isStarted())
            forceLoad();
    }
}

