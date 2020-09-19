package com.example.zacharysy_networking.utilities;

import android.graphics.drawable.Drawable;
import android.util.Log;

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

    public static URL buildCountriesUrl(){
        URL countryUrl = null;
        String countryUrlString = "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json";

        try {
            countryUrl = new URL(countryUrlString);

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }

        return countryUrl;
    } // end of buildCountriesUrl

    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A"); // delimiter for end of file
            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    } // end of getResponseFromUrl

    public static ArrayList<String> parseCountriesJson(String countriesResponseString) {
        ArrayList<String> countryList = new ArrayList<String>();

        try {
            JSONObject allCountriesObject = new JSONObject(countriesResponseString);
            JSONArray allCountriesArray = allCountriesObject.getJSONArray("pokemon");

            for (int i = 0; i < allCountriesArray.length(); i++) {
                JSONObject childJson = allCountriesArray.getJSONObject(i);
                if (childJson.has("img")) {
                    try{
                        String name = childJson.getString("img");
                        InputStream is = (InputStream) new URL(name).getContent();
                        Drawable d = Drawable.createFromStream(is, "src name");
                        countryList.add(name);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

            }

        } catch (JSONException e) {
            Log.d("info", "JSON PARSING ISSUE!!");
            System.out.println("JSON PARSING ISSUE!!");
            e.printStackTrace();

        }

        return countryList;
    } // end of parseCountriesJson
} // end of network utils class
