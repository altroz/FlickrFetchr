package com.example.ramrooter.flickrfetchr;

/**
 * Created by Ram Rooter on 10/15/2016.
 */

public class GalleryItem{
    private String mCaption;
    private String mId;
    private String mUrl;

    public String toString(){
        return mCaption;
    }

    public void setCaption(String caption){
        mCaption = caption;
    }

    public String getCaption(){
        return mCaption;
    }

    public void setId(String id){
        mId = id;
    }

    public String getId(){
        return mId;
    }

    public void setUrl(String url){
        mUrl = url;
    }

    public String getUrl(){
        return mUrl;
    }
}