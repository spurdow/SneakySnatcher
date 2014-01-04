package com.teampora.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.teampora.game.State;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.utils.Assets;
import com.teampora.utils.Container;

public class SplashScreen extends AbstractScreen {
	
	public final static String LOG_TAG = "SPLASH SCREEN";
	
	public final static String MUSIC_BG_PATH = "";
	
	public final static String BG_PATH = "data/splash/";
	
	public final static String BG = "splash-bg.png";
	
	private Container container;
		

	public SplashScreen(SneakySnatcher game, Stage stage, Music backgroundMusic,
			boolean playMusic, State state) {
		super(game, stage,state);
		// TODO Auto-generated constructor stub
		container = new Container(Assets.splash);		
	}
	
	@Override
	public void renderSub(float delta) {
		// TODO Auto-generated method stub
		
		GL20 gl20 = Gdx.graphics.getGL20();
		gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.getSpriteBatch().disableBlending();
		stage.act(delta);
		stage.draw();
		stage.getSpriteBatch().enableBlending();
		
		camera.update();
		
		if(game.assetMgr.update()){
			//game.setScreen(new TransitionScreen(game.screen.getNextScreen(State.MENU) ,game.screen.getNextScreen(State.CREDITS) ));	
			game.setScreen(game.screen.getNextScreen(State.MENU) );
		}
		
	}


	@Override
	public void showSub() {
		// TODO Auto-generated method stub
		container.setFillParent(true);
		stage.addActor(container);
	}

	@Override
	public void disposeSub() {
		// TODO Auto-generated method stub
		//stage.dispose();
		container.clear();
		Assets.splash = null;
		container = null;
	}

	@Override
	public void hideSub() {
		// TODO Auto-generated method stub
		
	}




}
