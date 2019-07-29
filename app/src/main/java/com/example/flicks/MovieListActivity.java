package com.example.flicks;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    // constants
    //the bas eURL fot the API
    public final static String API_BASE_URL = "https//api.themoviedb.org/3";
    // the parameter name fot the API key
    public final static String API_KEY_PARAM = "api_key";
    //the API key -- TODO move to a secure location
    public final static String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    // tag fot logging from this Activity
    public final static String TAG = "MovieListActivity";

    // instance fields
    AsyncHttpClient client;
    // the base url for loading images
    String imageBaseUrl;
    // the poster size to use when fetching images, part of the url
    String posterSize;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        // iniialize the client
        client = new AsyncHttpClient();
    }
    // get the configuration from API
    private void getConfiguration(){
        // create the url
        String url = API_BASE_URL + "/configuration";
        //set the request parameters
        RequestParams params= new RequestParams();
        params.put(API_KEY_PARAM, API_KEY); // API key, always requiered
        //execute a GET request expecting a JSON object response
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess (int statusCode, Header[] headers, JSONObject response) {
                // get the image base url
                try {
                    imageBaseUrl = response.getString("secure_base_url");
                    // get the poster size
                    JSONArray posterSizeOptions = response.getJSONArray("poster_sizes");
                    // use the option at index 3 or w342 as a fallback
                    posterSize = posterSizeOptions.optString(3, "w342");
                } catch (JSONException e) {
                    LogError("Failed parsing configuration", e, true);
                }
            }

            @Override
            public void onFailure (int statusCode, Header[] headers, String responseString, Throwable throwable) {
                LogError("Failed getting configuration", throwable, true);
            }
        });
    }

    // handle errors, Log and alert user
    private void LogError(String message, Throwable error, boolean alertUser){
        // always log the error
        Log.e(TAG, message, error);
        // alert the user to avoid silent errors
        if (alertUser) {
           // show a long toast with the error message
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
