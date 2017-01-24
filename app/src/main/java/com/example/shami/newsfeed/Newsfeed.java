package com.example.shami.newsfeed;

/**
 * Created by Shami on 1/23/2017.
 */

public class Newsfeed {

    private  String sectionName;
    private  String publicationDate;
    private  String webTitle;
    private String url;

    public Newsfeed(String sName,String wTitle,String pDate,String wUrl)
    {
        sectionName=sName;
        publicationDate=pDate;
        webTitle=wTitle;
        url=wUrl;
    }

    public String getSectionName()
    {
        return sectionName;
    }

    public String getPublicationDate()
    {
        return  publicationDate;
    }

    public String getWebTitle()
    {
        return  webTitle;
    }

    public String getUrl()
    {
        return url;
    }


}
