package com.example.zacharysy_networking.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Pokemon{
	public String name;
	public ArrayList<String> types;
	public int hp;
	public int attack;
	public int defense;
	public int spAtk;
	public int spDfns;
	public int speed;
	public URL spriteURL;

	public Pokemon(String name, ArrayList<String> types, int hp, int attack, int defense, int spAtk, int spDfns, int speed){
		this.name = name;
		this.types = types;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.spAtk = spAtk;
		this.spDfns = spDfns;
		this.speed = speed;
	}

	public Pokemon(JSONObject pokeJSON){
		try{
			this.name = ((JSONObject) pokeJSON.getJSONArray("forms").get(0)).getString("name");

			ArrayList<String> types = new ArrayList<String>();
			JSONArray typesArray = pokeJSON.getJSONArray("types");

			for(int i = 0; i < typesArray.length(); i++) {
				String typeString = ((JSONObject) typesArray.get(i)).getJSONObject("type").getString("name");
				types.add(typeString);
			}

			this.types = types;

			JSONArray stats = pokeJSON.getJSONArray("stats");
			this.hp = ((JSONObject) stats.get(0)).getInt("base_stats");
			this.attack = ((JSONObject) stats.get(1)).getInt("base_stats");
			this.defense = ((JSONObject) stats.get(2)).getInt("base_stats");
			this.spAtk = ((JSONObject) stats.get(3)).getInt("base_stats");
			this.spDfns = ((JSONObject) stats.get(4)).getInt("base_stats");
			this.speed = ((JSONObject) stats.get(5)).getInt("base_stats");

			JSONObject sprites = pokeJSON.getJSONObject("sprites");
			String spriteURLString = sprites.getString("front_default");

			try{
				this.spriteURL = new URL(spriteURLString);
			}catch(MalformedURLException e){
				e.printStackTrace();
			}

		}catch(JSONException e){
			e.printStackTrace();
		}
	}

}
