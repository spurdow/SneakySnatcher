package com.teampora.profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.teampora.adventure.MapSelection;
import com.teampora.credits.Credits;
import com.teampora.managers.SettingsManager;
import com.teampora.tools.Gloves;
import com.teampora.tools.Hammer;
import com.teampora.tools.Scores;

public class Profile implements Serializable {
	
	private long coins;
	private List<Scores> highScores;
	private List<Hammer> hammers;
	private List<Gloves> gloves;
	private List<Credits> credits;
	private List<MapSelection> maps;
	private int tries;
	
	public final static int MAX_TRIES = 10;
	
	public Profile(){
		this.highScores = new ArrayList<Scores>();
		this.hammers = new ArrayList<Hammer>();
		this.gloves = new ArrayList<Gloves>();
		this.credits = new ArrayList<Credits>();
		this.maps = new ArrayList<MapSelection>();
	}
	
	

	public long getCoins() {
		return coins;
	}


	
	public void revertHammer(List<Hammer> tools ){
		for(Hammer tool  : tools){
			tool.setEquipped(false);
		}
	}
	public void revertGloves(List<Gloves> tools){
		for(Gloves tool  : tools){
			tool.setEquipped(false);
		}
	}
	
	public Gloves getEquippedGloves(){
		for(Gloves glove : gloves){
			if(glove.isEquipped()){
				return glove;
			}
		}
		return null;
	}
	
	public Hammer getEquippedHammer(){
		for(Hammer hammer : hammers){
			if(hammer.isEquipped()){
				return hammer;
			}
		}
		return null;
	}

	public void setCoins(long coins) {
		this.coins = coins;
	}

	public boolean canBuy(double price){
		return coins >= price;
	}


	public List<Scores> getHighScores() {
		return highScores;
	}

	@SuppressWarnings("unused")
	private void sort(List<Scores> list){
		Comparator<Scores> comparator = new Comparator<Scores>(){

			@Override
			public int compare(Scores arg0, Scores arg1) {
				// TODO Auto-generated method stub
				if(arg0.getScore() >= arg1.getScore()){
					return -1;
				}
				else
					return 1;
			}
		};
		
		Collections.sort(list, comparator);
	}

	public void setHighScores(List<Scores> highScores) {
		this.highScores = highScores;
	}


	public List<Hammer> getHammers() {
		return hammers;
	}



	public void setHammers(List<Hammer> hammers) {
		this.hammers = hammers;
	}



	public List<Gloves> getGloves() {
		return gloves;
	}



	public void setGloves(List<Gloves> gloves) {
		this.gloves = gloves;
	}


	public Hammer getHammer(int id){
		return hammers.get(--id);
	}
	
	public Gloves getGloves(int id){
		return gloves.get(--id);
	}
	



	/**
	 * @return the credits
	 */
	public List<Credits> getCredits() {
		return credits;
	}



	/**
	 * @param credits the credits to set
	 */
	public void setCredits(List<Credits> credits) {
		this.credits = credits;
	}

	/**
	 * @return the maps
	 */
	public List<MapSelection> getMaps() {
		return maps;
	}



	/**
	 * @param maps the maps to set
	 */
	public void setMaps(List<MapSelection> maps) {
		this.maps = maps;
	}

	


	public int getTries() {
		return tries;
	}



	public void setTries(int tries) {
		this.tries = tries;
	}



	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub
		
		for(int  i = 0 ; i < highScores.size() ; i++){
			((Scores)highScores.get(i)).setName("Top " + (i+1));
		}
		json.writeValue("tries" , tries);
		json.writeValue("coins",coins);
		json.writeValue("highScores" , highScores);
		json.writeValue("hammers", hammers);
		json.writeValue("gloves", gloves);
		json.writeValue("credits", credits);
		json.writeValue("maps" , maps);
		
		
		Gdx.app.log("PROFILE " + SettingsManager.PROFILE_VERSION, "Writing Json Files " + json.toString());
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub
		if(SettingsManager.isLiteVersion){
			tries = -1;
		}else{
			tries = json.readValue("tries", Integer.class,  jsonData);	
		}
		coins = json.readValue("coins",Long.class, jsonData);
		List<Scores> highScores = json.readValue("highScores", ArrayList.class,  jsonData);
		List<Hammer> hammers = json.readValue("hammers", ArrayList.class,jsonData);
		List<Gloves> gloves = json.readValue("gloves", ArrayList.class,jsonData);
		List<Credits> credits = json.readValue("credits",ArrayList.class ,jsonData);
		List<MapSelection> maps = json.readValue("maps", ArrayList.class, jsonData);
		
		for(Scores score : highScores){
			this.highScores.add(score);
		}
		
		for(Hammer hammer : hammers){
			this.hammers.add(hammer);
		}
		
		for(Gloves glove : gloves){
			this.gloves.add(glove);
		}
		
		for(Credits credit : credits){
			this.credits.add(credit);
		}
		
		for(MapSelection map : maps){
			this.maps.add(map);
		}
		
		Gdx.app.log("read", "Reading Json Files");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String returnedString = "";
		
		returnedString+= "Coins => " + coins + "\n";
		

		for(Scores score : highScores){
			returnedString+= "Score => "  + score.getScore() + " = " + score.getName() + "\n";
		}
		
		for(Hammer hammer : hammers){
			returnedString+= "Hammer => " + hammer.toString() + "\n";
		}
		
		for(Gloves glove : gloves){
			returnedString+= "Gloves => " + glove.toString() + "\n";
		}
		
		for(Credits credit : credits){
			returnedString+= "Credits => " + credit.toString() + "\n";
		}
		
		for(MapSelection map : maps){
			returnedString+= "Maps => " + map.toString() + "\n";
		}
		
		return returnedString;
	}
	
	
	
	

}
