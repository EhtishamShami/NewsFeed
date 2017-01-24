package com.example.shami.newsfeed;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Shami on 1/23/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<Newsfeed>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    private URL mUrl;

    public NewsLoader(Context context, URL url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Newsfeed> loadInBackground() {
        if(mUrl == null)
        {
            return null;
        }
        String jsonResponse="";
        List<Newsfeed> newsfeeds=null;
        try{
            jsonResponse=QueryUtils.makeHttpRequest(mUrl);
            newsfeeds = QueryUtils.extractEarthquakes(jsonResponse);

        }
        catch (IOException e)
        {
            Log.e(LOG_TAG,"Error in making http request"+e );
        }

        return newsfeeds;
    }

}
