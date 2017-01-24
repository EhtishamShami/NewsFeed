package com.example.shami.newsfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Shami on 1/23/2017.
 */

public class NewsAdapter extends ArrayAdapter<Newsfeed> {

    public NewsAdapter(Context context, List<Newsfeed> newsfeeds){

        super(context,0,newsfeeds);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView=convertView;

        if(listView==null)
        {
          listView= LayoutInflater.from(getContext()).inflate(R.layout.news_list_item,parent,false);
        }


        Newsfeed newsfeed=getItem(position);

        TextView sectionName=(TextView) listView.findViewById(R.id.sectionName);
        sectionName.setText(newsfeed.getSectionName());

        String datendtime=newsfeed.getPublicationDate();
        String date;
        String time;
        String LOCATION_SEPARATOR="T";
        if (datendtime.contains(LOCATION_SEPARATOR)) {
            String[] parts = datendtime.split(LOCATION_SEPARATOR);
            date = parts[0] + LOCATION_SEPARATOR;
            time = parts[1];
        } else {
          date="Default";
          time="Default";
        }

        TextView dateview=(TextView)listView.findViewById(R.id.date);
        dateview.setText(date);
        TextView timeView=(TextView)listView.findViewById(R.id.time);
        timeView.setText(time);

        String title=newsfeed.getWebTitle();

        TextView webtitle=(TextView)listView.findViewById(R.id.webTitle);
        webtitle.setText(title);

        return listView;
    }



}
