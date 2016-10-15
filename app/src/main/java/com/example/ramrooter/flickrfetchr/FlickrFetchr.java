package com.example.ramrooter.flickrfetchr;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ram Rooter on 10/15/2016.
 */

/*FlickrFetchr starts with two methods: getUrlBytes(String) and getUrlString(String)
	The getUrlBytes(String) method fetches raw data from a URL and returns it as an array of bytes
	The getUrlString(String) method converts the result from getUrlBytes(String) to a String.
*/

public class FlickrFetchr{
    private static final String TAG = "FlickrFetchr";
    private static final String API_KEY = "42c5b78b1967addfcc77e10ed0e5b129";

    public byte[] getUrlBytes(String urlSpec)throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try{

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() + ": with" + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer))>0){
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();
        }

        finally{
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

	/*Ones GET request URL will look something like this:
	https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=xxx&format=json&nojsoncallback=1
	Uri.Builder to build the complete URL for your Flickr Api request. Uri.BUilder is a convenience class for
	creating properly escaped parameterized  URLs. Uri.Builder.appendQueryParameter(String, String).
	*/

    // The parseItems(...) method needs a List and JSONObjec. Update fetchItems() to call parseItems(...)
    // and return a List of GalleryItems.
    public List<GalleryItem> fetchItems(){

        List<GalleryItem> items = new ArrayList<>();
        try{
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key",API_KEY)
                    .appendQueryParameter("format","json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras","url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON:  " + jsonString);
            // json.org API provides Java objects corresponding to JSON text, such as JSONObject and JSONArray.
            // YOu can easily parse JSON text into corresponding Java objects using the JSONObject(String) constructor.
            JSONObject jsonBody =  new JSONObject(jsonString);
            parseItems(items,jsonBody);
        }

        catch(JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        catch(IOException ioe){
            Log.e(TAG, "Failed to fetch items", ioe);
        }

        return items;
    }

    // Writes a method that pulls out information for each photo. Make a GalleryItem for each photo and add it to
    // a List.

    private void parseItems(List<GalleryItem> items, JSONObject jsonBody) throws IOException, JSONException{
        JSONObject photoJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photoJsonObject.getJSONArray("photo");

        for(int i = 0; i<photoJsonArray.length(); i++){
            photoJsonObject = photoJsonArray.getJSONObject(i);

            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));

            if(!photoJsonObject.has("url_s")){
                continue;
            }

            item.setUrl(photoJsonObject.getString("url_s"));
            items.add(item);
        }
    }


}