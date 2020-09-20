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
	public static ArrayList<Pokemon> generatePokemon(String pokemonListString){
		ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();

		try {

			JSONObject pokemonListContainer = new JSONObject(pokemonListString);
			JSONArray pokemonList = pokemonListContainer.getJSONArray("pokemon");

			for(int i = 0; i < pokemonList.length(); i++){
				JSONObject pokeData = pokemonList.getJSONObject(i);
				Pokemon poke = new Pokemon(pokeData);

				pokemon.add(poke);
			}

		} catch (JSONException e){
			e.printStackTrace();
		}

		return pokemon;
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
