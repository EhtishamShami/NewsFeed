package com.example.shami.newsfeed;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Shami on 1/23/2017.
 */

public class QueryUtils  {


    public QueryUtils()
    {}

public static final String Log_Tag=QueryUtils.class.getSimpleName();

    public static URL createurl(String url)
    {
        URL myurl=null;
        try{
            Uri builtUri = Uri.parse(url)
                    .buildUpon()
                    .appendQueryParameter("tag", "politics/politics")
                    .appendQueryParameter("from-date", "2014-01-01")
                    .appendQueryParameter("api-key", "test")
                    .build();

            myurl=new URL(builtUri.toString());



        }catch(MalformedURLException exception)
        {
            Log.e(Log_Tag,"URl cannot be parsed "+exception);
        }

        return myurl;

    }


    public static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse="";

        if(url==null)
        {
            return jsonResponse;
        }
        HttpURLConnection connection=null;
        InputStream in=null;

        try{
            connection=(HttpURLConnection)url.openConnection();
            connection.setReadTimeout(20000 /* milliseconds */);
            connection.setConnectTimeout(20000);
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode()==200)
            {
                in=connection.getInputStream();
                jsonResponse=readfromstream(in);
            }

        }
        catch (IOException e)
        {
            Log.e(Log_Tag,"Error response code: " + connection.getResponseCode());
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {

                in.close();
            }
        }

        return jsonResponse;
    }


    private static String readfromstream(InputStream inputStream)throws IOException
    {
        StringBuilder stringBuilder=new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while(line!=null)
            {
                stringBuilder.append(line);
                line=reader.readLine();
            }
        }
        return stringBuilder.toString();

    }

    public static ArrayList<Newsfeed> extractEarthquakes(String JSON_RESPONSE) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<Newsfeed> newsfeeds = new ArrayList<>();

        try {
            JSONObject object=new JSONObject(JSON_RESPONSE);
            JSONObject response=object.getJSONObject("response");
            JSONArray  results=response.getJSONArray("results");

            for(int i=0;i<results.length();i++)
            {
                JSONObject data=results.getJSONObject(i);
                String sectionName=data.getString("sectionName");
                String webTitle=data.getString("webTitle");
                String publishDate=data.getString("webPublicationDate");
                String publishUrl=data.getString("webUrl");

                newsfeeds.add(new Newsfeed(sectionName,webTitle,publishDate,publishUrl));

            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return newsfeeds;
    }




}
