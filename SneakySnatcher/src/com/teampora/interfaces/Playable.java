package com.teampora.interfaces;

import aurelienribon.tweenengine.Timeline;

import com.teampora.game.FruitModel.FruitType;
import com.teampora.game.GameState;
import com.teampora.screens.AbstractScreen;

public interface Playable{
	public final static String MUSIC_BG = "data/music_ogg/inGameBGMusic.ogg";
	public final static int MAX_GOLD_FRUIT = 10;
	public final static int MIN_GOLD_FRUIT = 5;
	
	public enum ScreenType{
		Adventure,
		Classic
	}
	
	public enum WindowAnimation{
		show_overlay_lowerbar_unshow_upperbar,
		unshow_overlay_lowerbar_show_upperbar,
		show_overlay_lowerbar,
		show_overlay,
		show_lowerbar,
		unshow_overlay_lowerbar,
		unshow_overlay_show_lowerbar
		
	}
	

	AbstractScreen getScreen();
	
	void setGoal(FruitType type);
	
	void addScore(float score);

	void addScoreWithAnimation(final float score , float startX, float startY, boolean projectPosition);
	
	void bomb();
	
	void luckyBomb(float startX, float startY);
	
	int generateRandomGoal();
	
	GameState getState();
	
	void reset();
	
	Timeline toggleWindow(WindowAnimation anim);
}
