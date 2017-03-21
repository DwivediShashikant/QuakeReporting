package com.example.android.quakereport;

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

/**
 * Created by Shashikant on 3/21/2017.
 */

public final class Utils {

    public static String LOG_TAG = Utils.class.getSimpleName();

    // query the USGS dataset and return (@Link quakes)
    public static void fetchEarthQuake(String requestUrl){
        Log.w(LOG_TAG,"in Utils activity");
        if(requestUrl == null){
            return;
        }

        // crrating  the url from String
        URL url = createURL(requestUrl);
        //perform HTTP request to USGS server and get the JSON response in return
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){
            Log.e(LOG_TAG,"error in fetching the json response");
        }
        Log.w(LOG_TAG,"in Utils activity again!!!!!!!");
          extractFeatureFromJson(jsonResponse);
    }

    private static  URL createURL(String requestURL){
        Log.w(LOG_TAG,"in Utils activity:create url");
        URL url = null;
        try{
             url = new URL(requestURL);
        }catch(MalformedURLException e){
            Log.e(LOG_TAG,"error with creating URL");
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        Log.w(LOG_TAG,"in Utils activity:makeHTTPRequest");
        String jsonResponse= "";
        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream= null;

        try{
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000 /*milliseconds*/);
            urlConnection.setConnectTimeout(15000 /*milliseconds*/);
            Log.w(LOG_TAG,"hahaha");
            urlConnection.setRequestMethod("GET");
            Log.w(LOG_TAG,"h000");
            urlConnection.connect();
            Log.w(LOG_TAG,"hiiiiiiii");

            if(urlConnection.getResponseCode()== 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG,"problem in reading the json response @utils.java" + urlConnection.getResponseCode());
            }
        }
        catch(IOException e){
           Log.e(LOG_TAG,"problem in making the HTTP request",e);
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if (inputStream!=null){
                inputStream.close();
            }
        }

        return  jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        Log.w(LOG_TAG,"in Utils activity:read from stream");
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static void extractFeatureFromJson(String jsonData){
        Log.w(LOG_TAG,"in Utils activity:extractFeatureFromJSON");
        try{
            JSONObject baseObject = new JSONObject(jsonData);
            JSONArray featuresArray = baseObject.getJSONArray("features");
             int features_len = featuresArray.length();
             if(features_len>0){
                 for(int i=0;i<features_len;i++){
                     JSONObject features = featuresArray.getJSONObject(i);
                     JSONObject properties = features.getJSONObject("properties");
                     Double magnitude = properties.getDouble("mag");
                     String place = properties.getString("place");
                     Long time = properties.getLong("time");
                     arrayList.earthquakes.add(new quake(magnitude,time , place));
                 }
             }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
