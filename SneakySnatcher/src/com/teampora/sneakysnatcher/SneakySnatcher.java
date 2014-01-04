package com.teampora.sneakysnatcher;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.teampora.game.State;
import com.teampora.interfaces.IActionResolver;
import com.teampora.interfaces.IActivityRequestHandler;
import com.teampora.managers.MusicEffectManager;
import com.teampora.managers.MusicManager;
import com.teampora.managers.ProfileManager;
import com.teampora.managers.ScreenManager;
import com.teampora.screens.AbstractScreen;
import com.teampora.utils.Assets;

public class SneakySnatcher  extends Game{
	/*
	 *  asset manager loads and holds the resources for the game
	 *  used by most screens and other classes related to creating images, sounds lower level
	 */
	public AssetManager assetMgr = new AssetManager();
	
	/*
	 *  screen manager handles changing of screens , still a prototype
	 *  it provides the screens to get from the asset what resources it will get
	 */
	public ScreenManager screen = new ScreenManager();
	
	/*
	 * the assets handles loading of resources , and the resources will be get from this class 
	 */
	public Assets assets ;
	
	/*
	 *  state used to change screens , for crystal clear/ human readable
	 *  prototype
	 */
	public State currentState = State.NONE;
	
	/*
	 *  the tween manager handles all objects for tweenation/animation
	 *  remove, add, adds callback
	 */
	public TweenManager tween = new TweenManager();
	
	/*
	 * profile manager handles saving the users profile , scores,coins, etc. etc.
	 */
	public ProfileManager service = new ProfileManager();
	
	/*
	 *  request handler handles android phones advertisements 
	 *  if it can be shown or not
	 */
	public IActivityRequestHandler handler;
	
	
	/*
	 *  action resolver for android thread
	 */
	public IActionResolver resolver;
	
	/*
	 *  the free type generator handles creating fonts during runtime.
	 */
	private FreeTypeFontGenerator generator;
	
	/*
	 *  used for bigger text
	 */
	public BitmapFont large_font ;
	/*
	 *  used for scores 
	 */
	public BitmapFont mid_font;
	/*
	 * used for coins , description
	 */
	public BitmapFont small_font;
	/*
	 * used for the menu screen font
	 */
	public BitmapFont menu_font;
	/*
	 * used for titles
	 */
	public BitmapFont xl_font;

	/*
	 * the Main Game 
	 * param is for hander for ads
	 * null for desktop
	 */
	public SneakySnatcher(IActivityRequestHandler handler){
		this.handler = handler;
	}
	
	public SneakySnatcher(IActivityRequestHandler handler , IActionResolver resolver){
		this.handler = handler;
		this.resolver = resolver;
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
	
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("data/uiskin/sneaky.ttf"));
		
		assets = new Assets(this);
		screen.setUpGame(this);
		
		menu_font = generator.generateFont(Gdx.graphics.getHeight() / 10);
		xl_font = generator.generateFont(Gdx.graphics.getHeight() / 12);
		large_font = generator.generateFont(Gdx.graphics.getHeight() / 16);
		mid_font = generator.generateFont(Gdx.graphics.getHeight() / 20);
		small_font = generator.generateFont(Gdx.graphics.getHeight() / 24);
		
		Gdx.app.log("SneakySnatcher",(Gdx.app.getNativeHeap() / (1024*1024f)) +  "");

		Tween.setCombinedAttributesLimit(5);
		setScreen(screen.getNextScreen(State.SPLASH));
		generator.dispose();
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
		assets.loadAll();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		assetMgr.clear();
		MusicManager.getInst().dispose();
		MusicEffectManager.getIns().dispose();
		super.dispose();
		Gdx.app.log("SneakySnatcher", "has been disposed");
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#setScreen(com.badlogic.gdx.Screen)
	 */
	private Screen screenToDispose = null;
	@Override
	public void setScreen(Screen screen) {
		// TODO Auto-generated method stub
		screenToDispose = getScreen();
		
		super.setScreen(screen);
		
		Gdx.app.log("Sneaky", "disposing prev screen" );
		if(screenToDispose != null && ((AbstractScreen)screenToDispose).isCanDispose()){
			screenToDispose.dispose();
			Gdx.app.log("Sneaky", "Screen DISPOSED!"  );
		}else
			Gdx.app.log("Sneaky", "Screen WAS NOT DISPOSED!"  );
	}

	
	
	
	
	

}
