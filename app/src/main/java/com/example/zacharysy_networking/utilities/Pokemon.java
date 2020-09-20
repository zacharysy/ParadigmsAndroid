package com.example.zacharysy_networking.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Pokemon{
	public int num;
	public String name;
	public ArrayList<String> types;
	public URL spriteURL;

	public Pokemon(int num, String name, ArrayList<String> types, URL spriteURL){
		this.num = num;
		this.name = name;
		this.types = types;
		this.spriteURL = spriteURL;
	}

	public Pokemon(JSONObject pokeJSON){
		try{
			int num = pokeJSON.getInt("num");
			String name = pokeJSON.getString("name");

			ArrayList<String> types = new ArrayList<String>();
			JSONArray typesArray = pokeJSON.getJSONArray("type");

			for(int i = 0; i < typesArray.length(); i++) {
				String typeString = typesArray.getString(i);
				types.add(typeString);
			}

			String spriteURLString = pokeJSON.getString("img");
			URL spriteURL = null;

			try{
				spriteURL = new URL(spriteURLString);
			}catch(MalformedURLException e){
				e.printStackTrace();
			}

			this.num = num;
			this.name = name;
			this.types = types;
			this.spriteURL = spriteURL;

		}catch(JSONException e){
			e.printStackTrace();
		}
	}

}
