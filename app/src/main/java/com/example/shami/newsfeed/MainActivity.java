package com.example.shami.newsfeed;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Newsfeed>> {


    ListView listView;
    ProgressBar progressBar;
    TextView notfound;

    private int loaderID=1;
    private NewsAdapter newsAdapter;

    private static final String USGS_REQUEST_URL =
            "http://content.guardianapis.com/search?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView=(ListView)findViewById(R.id.list);
        progressBar=(ProgressBar)findViewById(R.id.loading_spinner);
        notfound=(TextView)findViewById(R.id.empty_view);
        newsAdapter=new NewsAdapter(this,new ArrayList<Newsfeed>());
        listView.setAdapter(newsAdapter);
        listView.setEmptyView(notfound);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Newsfeed newsfeed = newsAdapter.getItem(position);
                Uri earthquakeUri = Uri.parse(newsfeed.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(websiteIntent);
            }
        });

        progressBar=(ProgressBar)findViewById(R.id.loading_spinner);
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(loaderID, null, this);
            progressBar.setVisibility(View.VISIBLE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            notfound.setText("No Internet Connection");
        }

    }


    @Override
    public Loader<List<Newsfeed>> onCreateLoader(int i, Bundle bundle) {
        URL url=QueryUtils.createurl(USGS_REQUEST_URL);
        return new NewsLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Newsfeed>> loader, List<Newsfeed> newsfeeds) {

        progressBar.setVisibility(View.GONE);
        notfound.setText(R.string.no_news);
        newsAdapter.clear();
        if (newsfeeds != null && !newsfeeds.isEmpty()) {
            newsAdapter.addAll(newsfeeds);
        }


    }
    @Override
    public void onLoaderReset(Loader<List<Newsfeed>> loader) {

        newsAdapter.clear();
    }

}
