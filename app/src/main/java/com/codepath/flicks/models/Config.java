package com.codepath.flicks.models;

import org.json.JSONArray;
import org.json.JSONObject;

public class Config {
    // the base url for loading images
    String imageBaseUrl;
    // the poster size to use when fetching images, part of the url
    String posterSize;
    // the backdrop size to use when fetching images
    String BackdropSize;

    public  Config(JSONObject object){
        JSONObject images;
        images = object.getJSONObject("images");
        // get the image base url
        imageBaseUrl = images.getString("secure_base_url");
        // get the poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        // use the option at index 3 or w342 as a fallback
        posterSize = posterSizeOptions.optString(3, "w342");
        // parse the backdrop sizes and use the option at index 1 or w780 as a fallback
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        backdropSize= backdropSizeOptions.optString(1, "w780");

        )
    }
     // helper method for creating urls
    public String getImageUrl(String size, String path){
        return String.format("%%%" , ImageBaseUrl, size,path); // concatenate all three
        )
    public String getImageBaseUrl() {
        return imageBaseUrl;

    }
    public String getPosterSize() {
        return posterSize;
}
