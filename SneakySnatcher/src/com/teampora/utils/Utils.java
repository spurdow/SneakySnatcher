package com.teampora.utils;

import com.badlogic.gdx.Gdx;

public class Utils {
	
	public static final String MUSIC_PATH = "data/music_ogg/";
	public static final String SOUND_PATH_GENERAL = "data/sound_ogg/general/";
	public static final String PARTICLES = "data/sound_ogg/particles/";
	
	public static final String ATLAS_PATH = "data/uiskin/uiskin.atlas";
	
	public static final String SKIN_PATH = "data/uiskin/uiskin.json";
	
	
	
	public final static String BUTTON = "";
	public final static String TEXTBUTTON = "";
	public final static String LABEL = "";
	public final static String WINDOW = "";
	
	public final static float LARGE_SCALE_X = 0.6f;
	public final static float LARGE_SCALE_Y = 0.5f;

	public final static float NORMAL_SCALE_X = 0.23f;
	public final static float NORMAL_SCALE_Y = 0.15f;
	
	public final static float SMALL_SCALE_X = 0.07f;
	public final static float SMALL_SCALE_Y = 0.1f;
	
	public final static float EXTRA_SMALL_SCALE_X = 0.1f;
	public final static float EXTRA_SMALL_SCALE_Y = 0.13f;
	
	public final static float BAR_HEIGHT = 0.3f;
	
	
	
	public static float WIDTH_LARGE(){
		return Gdx.graphics.getWidth() * LARGE_SCALE_X;
	}
	
	public static float HEIGHT_LARGE(){
		return Gdx.graphics.getHeight() * LARGE_SCALE_Y;
	}
	
	public static float WIDTH_NORMAL(){
		return Gdx.graphics.getWidth() * NORMAL_SCALE_X;
	}
	
	public static float HEIGHT_NORMAL(){
		return Gdx.graphics.getHeight() * NORMAL_SCALE_Y;
	}
	
	public static float WIDTH_SMALL(){
		return Gdx.graphics.getWidth() * SMALL_SCALE_X;
	}
	
	public static float HEIGHT_SMALL(){
		return Gdx.graphics.getHeight() * SMALL_SCALE_Y;
	}
	
	public static float WIDTH_EXTRA_SMALL(){
		return Gdx.graphics.getWidth() * EXTRA_SMALL_SCALE_X;
	}
	
	public static float HEIGHT_EXTRA_SMALL(){
		return Gdx.graphics.getHeight() * EXTRA_SMALL_SCALE_Y;
	}
	
	
	public static float getWRatio(float srcWidth , float srcHeight , float width , float height){
		return srcWidth / srcHeight * height;
	}
	
	public static float getHRatio(float srcWidth , float srcHeight , float width , float height){
		return srcHeight / srcWidth * width;
	}
	
	public static float getDynamicWidth(float percent){
		return Gdx.graphics.getWidth() * percent;
	}
	
	public static float getDynamicHeight(float percent){
		return Gdx.graphics.getHeight() * percent;
	}


}
