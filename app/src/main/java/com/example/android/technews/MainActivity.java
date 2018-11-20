package com.example.android.technews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<TechNews>> {

    static final String apiKey = "a02e452c-60be-43eb-b1e7-d98b1b821704";

    //Fixing request: adding the Uri Builder to have less hard-coding in the app.
    private static String uriBuilder() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("q", "technology")
                .appendQueryParameter("show-fields", "byline")
                .appendQueryParameter("order-by", "newest")
                .appendQueryParameter("api-key", apiKey);

        String myUrl = builder.build().toString();
        return myUrl;
    }

    private static final String TECH_NEWEST_URL = uriBuilder();
    private static final int TECH_NEWS_LOADER_ID = 1;
    private TechNewsAdapter techAdapter;
    private TextView whenEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView techNewsListView = findViewById(R.id.list);

        whenEmptyStateTextView = findViewById(R.id.empty_view);
        techNewsListView.setEmptyView(whenEmptyStateTextView);


        techAdapter = new TechNewsAdapter(this, new ArrayList<TechNews>());


        techNewsListView.setAdapter(techAdapter);

        techNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                TechNews currentArticle = techAdapter.getItem(position);


                Uri articleUri = Uri.parse(currentArticle.getWebUrl());


                Intent webIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                //Send the order to start the activity

                startActivity(webIntent);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data work
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //If there is a network connection, fetch data

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            //Get a reference to the LoadManager, in order to interact with loaders
            LoaderManager loaderManager = getLoaderManager();

            //Initialize the loader. Pass in the int ID constant defined above and pass in null

            loaderManager.initLoader(TECH_NEWS_LOADER_ID, null, this);
        } else {

            //If there isn't network connection, display error message
            View loadingIndicator = (View) findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(GONE);

            //Update empty state with no connection error message

            whenEmptyStateTextView.setText("No internet connection.");

        }

    }

    @Override
    public Loader<ArrayList<TechNews>> onCreateLoader(int id, Bundle args) {
        return new TechNewsLoader(this, TECH_NEWEST_URL);
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<TechNews>> loader, ArrayList<TechNews> techNews) {
        Log.i(MainActivity.class.getName(), "onLoadFinished called");
        {
            //Hide loading indicator
            View loadingIndicator = (View) findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(GONE);


            whenEmptyStateTextView.setText("No news to display. Sorry!");

            //Clear the adapter of previous data

            techAdapter.clear();


            if (techNews != null && !techNews.isEmpty()) {
                techAdapter.addAll(techNews);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<TechNews>> loader) {
        techAdapter.clear();
    }
}
