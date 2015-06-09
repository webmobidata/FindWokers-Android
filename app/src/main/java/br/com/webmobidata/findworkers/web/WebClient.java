package br.com.webmobidata.findworkers.web;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by MarceloToyoda on 09/06/2015.
 */
public class WebClient {

    public JSONObject executeGet(String urlService) throws IOException, JSONException {
        StringBuilder content = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(urlService);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            if ( urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;

                while ((line = bufferedReader.readLine()) != null){
                    content.append(line + "\n");
                }
                bufferedReader.close();

                JSONObject jsonObject = new JSONObject(content.toString());
                return jsonObject;
            }else{
                return null;
            }
        }
        catch(Exception e){
            throw e;
        }finally {
            if( null != urlConnection){
                urlConnection.disconnect();
            }
        }
    }

    public JSONObject executePost(String urlService, String json) throws IOException, JSONException {
        StringBuilder content = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(urlService);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream os = urlConnection.getOutputStream();
            os.write(json.getBytes());
            os.flush();

            if ( urlConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;

                while ((line = bufferedReader.readLine()) != null){
                    content.append(line + "\n");
                }
                bufferedReader.close();

                JSONObject jsonObject = new JSONObject(content.toString());
                return jsonObject;
            }else{
                throw new RuntimeException("Failed : HTTP error code : "+ urlConnection.getResponseCode());
            }
        }
        catch(Exception e){
            throw e;
        }finally {
            if( null != urlConnection){
                urlConnection.disconnect();
            }
        }
    }
}
