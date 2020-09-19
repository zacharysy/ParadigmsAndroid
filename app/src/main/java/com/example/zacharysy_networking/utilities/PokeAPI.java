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

public class PokeAPI {
	public static PokeAPI shared = new PokeAPI();

	public ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
	
	public PokeAPI(){
		try{
			URL listURL = createURL("https://pokeapi.co/api/v2/pokemon?limit=100");
			String pokemonListString = getResponseFromUrl(listURL);

			JSONObject pokemonListContainer = new JSONObject(pokemonListString);
			JSONArray pokemonList = pokemonListContainer.getJSONArray("results");

			for(int i = 0; i < pokemonList.length(); i++){
				JSONObject newPokemon = pokemonList.getJSONObject(0);
				String pokeString = newPokemon.getString("url");
				URL pokeURL = createURL(pokeString);
				JSONObject pokeData = new JSONObject(getResponseFromUrl(pokeURL));
				Pokemon poke = new Pokemon(pokeData);

				pokemon.add(poke);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	// PokeAPI
	public static URL createURL(String str){
		URL listURL = null;
		
		try{
            listURL = new URL(str);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }

        return listURL;
	}


    // Helpers
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

}
