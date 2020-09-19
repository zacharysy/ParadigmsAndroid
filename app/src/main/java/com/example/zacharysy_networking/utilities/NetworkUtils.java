package com.example.zacharysy_networking.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {
    public static URL bulidCountriesUrl(){
        URL countryUrl = null;
        String countryUrlString = "https://api.openaq.org/v1/countries";

        try{
            countryUrl = new URL(countryUrlString);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        return countryUrl;
    }

    public static String getResponseFromUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput) return scanner.next();
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<String> parseCountriesJson(String countriesResponseString){
        ArrayList<String> countryList = new ArrayList<String>();

        try{
            JSONObject allCountriesObject = new JSONObject(countriesResponseString);
            JSONArray allCountriesArray = allCountriesObject.getJSONArray("results");

            for(int i = 0; i < allCountriesArray.length(); i++){
                JSONObject childJson = allCountriesArray.getJSONObject(i);
                if(childJson.has("name")){
                    String name = childJson.getString("name");
                    countryList.add(name);
                }
            }
        }catch(JSONException e){
            System.out.println();
            e.printStackTrace();
        }

        return countryList;
    }

}
