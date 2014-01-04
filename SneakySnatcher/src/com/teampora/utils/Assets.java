package com.teampora.utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.teampora.managers.SettingsManager;
import com.teampora.screens.SplashScreen;
import com.teampora.sneakysnatcher.SneakySnatcher;

public class Assets implements Disposable{
	
	// let the implementing class dispose what was used...
	
	final static String SPLASH_PATH = "";
	final static String GAME_PATH = "";
	
	//
	public static TextureAtlas gameAtlas;
	
	// splash screen items
	public static Texture splash;
	public static Image splash_fruit;
	
	
	
	public static TextureRegion background;
	public static TextureRegion additional_background;
	public static NinePatch selected;
	public static NinePatch unselected;
	public static Image leaves;
	public static Image body;
	public static Image bricks;
	public static Image transitionBricks;
	public static TextureRegion sneaky_snatcher_titles;
	public static TextureRegion temp_fill;
	public static TextureRegion temp_top;
	public static TextureRegion background_dialog;
	public static TextureRegion basket;
	public static TextureRegion window_dialog;
	public static TextureRegion lock;
	public static TextureRegion leftArrow_icon;
	public static TextureRegion menu_icon;
	public static TextureRegion play_icon;
	public static TextureRegion retry_icon;
	public static TextureRegion pause_icon;
	public static TextureRegion pointingGlove;
	public static TextureRegion pointingGloveFlip;
	public static TextureRegion star_filled;
	public static TextureRegion star_unfilled;
	public static TextureRegion help_gloves;

	//sound
	public static Sound drop;
	public static Sound bomb;
	public static Sound click;
	public static Sound buy;
	public static Sound coin;
	public static Sound squash;
	public static Sound failed;
	public static Sound success;
	public static Sound top;
	public static Sound down;
	public static Sound steal;
	public static Sound dialog;
	public static Sound touch1;
	public static Sound touch2;
	public static Sound touch3;
	public static Sound touch4;
	public static Sound starred;
	
	// nine PATCH!!
	public static NinePatch orange_gradient;
	public static NinePatch blue_gradient;
	public static NinePatch tool_dialog;
	public static NinePatch border;
	public static NinePatch bomb_active;
	public static NinePatch bomb_inactive;
	public static NinePatch dialog_patch;
	public static NinePatch nextButton;
	public static NinePatch prevButton;
	public static NinePatch menu_overlay;
	public static NinePatch fruit_coin;
	public static NinePatch ninePatchButton;
	public static NinePatch msg_box;
	public static NinePatch golden_active;
	public static NinePatch golden_inactive;
	
	//music
	//none

	//fruit regions
	public static TextureRegion golden_apple;
	public static TextureRegion green_apple;
	public static TextureRegion red_apple;
	public static TextureRegion lemon;
	public static TextureRegion lime;
	public static TextureRegion orange;
	public static TextureRegion watermelon;
	public static TextureRegion coconut;
	public static TextureRegion bomb_fruit;
	
	// animation
	public static Array<AtlasRegion> atlasRegions;
	
	
	
	// fruit squash effect
	public static TextureRegion fruit_splash;
	
	/*
	 *  ui skins!
	 */
	public static Skin skin;
	
	/*
	 *  de game
	 */
	private SneakySnatcher game;
	
	

	public Assets(SneakySnatcher game){
		this.game = game;
	}
	
	//load images, etc.
	public void loadAll(){
		loadSounds();
		loadImages();
		loadSkin();
	}
	
	public void loadSounds(){
		
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL+"fruit_drop.ogg" , Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL+ "wooden_dialog.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL + "bomb.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL +  "button_click.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL + "buy.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL+"fruit_coin.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL+"fruit_squash.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL+"game_failed.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL+"game_success.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL+"new_top_score.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL+"score_down.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL+"fruit_steal.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL +"touch_1.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL +"touch_2.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL +"touch_3.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL +"touch_4.ogg", Sound.class);
		game.assetMgr.load(Utils.SOUND_PATH_GENERAL +"starred.ogg", Sound.class);
		
	}
	
	public void loadImages(){
		game.assetMgr.load(Utils.ATLAS_PATH, TextureAtlas.class);
	}


	
	public void loadSkin(){
		game.assetMgr.load(Utils.SKIN_PATH, Skin.class);
		
	}
	
	
	public void loadSplash(){
		splash = new Texture(Gdx.files.internal(SplashScreen.BG_PATH + SplashScreen.BG));
	}
		


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		drop.dispose();
		click.dispose();
		bomb.dispose();
		steal.dispose();
		success.dispose();
		failed.dispose();
		squash.dispose();
		coin.dispose();
		dialog.dispose();
		top.dispose();
		down.dispose();
		touch1.dispose();
		touch2.dispose();
		touch3.dispose();
		touch4.dispose();
		
		
		game.assetMgr.unload(Utils.ATLAS_PATH);
		game.assetMgr.unload(Utils.SKIN_PATH);
		//game.assetMgr.unload(Utils.SOUND_PATH_GENERAL+"fruit_drop.ogg");
		//game.assetMgr.unload(Utils.SOUND_PATH_GENERAL+"WOODEN_DIALOG.ogg");
	}
	
	

}
