package com.example.zacharysy_networking.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Pokemon{
	public String num;
	public String name;
	public ArrayList<String> types;
	public String spriteURL;

	public Pokemon(String num, String name, ArrayList<String> types, String spriteURL){
		this.num = num;
		this.name = name;
		this.types = types;
		this.spriteURL = spriteURL;
	}

	public Pokemon(JSONObject pokeJSON){
		try{
			String num = pokeJSON.getString("num");
			String name = pokeJSON.getString("name");

			ArrayList<String> types = new ArrayList<String>();
			JSONArray typesArray = pokeJSON.getJSONArray("type");

			for(int i = 0; i < typesArray.length(); i++) {
				String typeString = typesArray.getString(i);
				types.add(typeString);
			}

			String spriteURLString = pokeJSON.getString("img");
			char secureChar = 's';
			StringBuilder stringBuilder = new StringBuilder(spriteURLString);
			spriteURLString = spriteURLString.substring(0, 4) + secureChar + spriteURLString.substring(4);
			URL spriteURL = null;

			try{
				spriteURL = new URL(spriteURLString);
			}catch(MalformedURLException e){
				e.printStackTrace();
			}

			this.num = num;
			this.name = name;
			this.types = types;
			this.spriteURL = spriteURLString;

		}catch(JSONException e){
			e.printStackTrace();
		}
	}

}
