package com.teampora.screens;


import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Quint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Pool;
import com.teampora.accessor.ImageButtonAccessor;
import com.teampora.accessor.LabelAccessor;
import com.teampora.accessor.TableAccessor;
import com.teampora.buttons.SneakyTextButton;
import com.teampora.game.State;
import com.teampora.labels.SneakyLabel;
import com.teampora.managers.MusicManager;
import com.teampora.managers.MusicManager.MusicType;
import com.teampora.managers.SettingsManager;
import com.teampora.profile.Profile;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.tables.TablePoolable;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public abstract class AbstractScreen implements Screen {
	
	protected final static int VIRTUAL_WIDTH = 800;
	
	protected final static int VIRTUAL_HEIGHT = 480;
	
	protected final static float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
	
	public Rectangle viewport;
	
	protected Stage stage;
	
	protected SneakySnatcher game;
	
	protected boolean hasPrevious;
	
	protected boolean transition;
	 
	protected OrthographicCamera camera;
	
	private Color bgColor = Color.ORANGE;

	private boolean debugging = false;
	
	private float timeLapse;
	
	private Label fpsLabel ;
	
	private State state;
	
	public final static float MAX_IDLE_TIME = 10f;
	
	private float idle_time = 0f;
	
	private boolean canDispose;
	
	protected ImageButton premium_button; 
	
	private Pool<TablePoolable> showLabels = new Pool<TablePoolable>(){

		@Override
		protected TablePoolable newObject() {
			// TODO Auto-generated method stub
			return new TablePoolable(Assets.blue_gradient , stage.getWidth()  , stage.getHeight() * 2, 0, 0 , new SneakyLabel("lfont",Color.WHITE));
		}
		
	};
	
	private Array<TablePoolable> labelsArray = new Array<TablePoolable>();
	
	
	/*
	 * the implementing methods for other screens
	 * 
	 * 
	 */
	
	public abstract void renderSub(float delta);
	
	// here we instantiate fields
	public abstract void showSub();
	// here we often dispose certain data.
	public abstract void disposeSub();
	
	public abstract void hideSub();
	
	
	
	
	@SuppressWarnings("unused")
	public AbstractScreen(SneakySnatcher game, Stage stage , State state ){
		this.stage = stage;
		this.game = game;
		this.state = state;
		/*
		 * The camera should be updated in the succeeding sub classes
		 */
		
		float wpw = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
		float wph = wpw * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0f);
		this.stage.setCamera(camera);
		
		/*
		 *  instantiate music manager
		 */
		//Gdx.app.log("MUSIC MANAGER",new MusicType(MusicType.HAMMER).getFileName() +"" );
		
		boolean showAds = false;
		if(state == State.NONE){
			transition = false;
			hasPrevious = false;
			
		}else if(state == State.SPLASH){
			transition = false;
			hasPrevious = false;
		}
		else if(state == State.MENU){
			
			Gdx.input.setInputProcessor(stage);
			hasPrevious = true;
			showAds = true;
			MusicManager.getInst().createNew(MusicType.MENU);
		}else if(state == State.CLASSIC){
			hasPrevious = true;
			//Gdx.input.setInputProcessor(stage);
		}
		else if(state == State.TOOLS){
			hasPrevious = true;
			showAds = true;
			MusicManager.getInst().createNew(MusicType.TOOLS);
		}
		else if(state == State.CREDITS){
			hasPrevious = true;
			Gdx.input.setInputProcessor(stage);
			showAds = true;
			MusicManager.getInst().createNew(MusicType.TOOLS);
		}
		else if(state == State.SCORES){
			hasPrevious = true;
			Gdx.input.setInputProcessor(stage);
			showAds = true;
			MusicManager.getInst().createNew(MusicType.TOOLS);
		}else if(state == State.MAP){
			hasPrevious = true;
			Gdx.input.setInputProcessor(stage);
			showAds = true;
			MusicManager.getInst().createNew(MusicType.MAP);
		}else if(state == State.LEVEL){
			hasPrevious = true;
			Gdx.input.setInputProcessor(stage);
			showAds = true;
			MusicManager.getInst().createNew(MusicType.MAP);
		}else if(state == State.ADVENTURE){
			hasPrevious = true;
			showAds= false;
		}
		else if(state == State.HELP){
			hasPrevious = true;
			showAds = true;
			Gdx.input.setInputProcessor(stage);
			MusicManager.getInst().createNew(MusicType.TOOLS);
		}
		game.currentState = state;
		Gdx.input.setCatchBackKey(hasPrevious);
		if(game.handler != null){
			if(showAds){
				game.handler.setType(0x00);
			}else{
				game.handler.setType(0x01);
			}
		}
		/*
		 *  sets for fps to be visible or not
		 */
		//setDebugging(true);
		
		/*
		 * 
		 */
		
		Tween.registerAccessor(ImageButton.class, new ImageButtonAccessor());
	
	}
	
	protected void checkForPremium(){
		
		if(state != State.NONE 
				&& state!= State.SPLASH 
				&& SettingsManager.isLiteVersion
				&& game.screen.profile.getTries() > Profile.MAX_TRIES
				//&& game.resolver != null
				){
			premium_button = new ImageButton(Assets.skin, "prem-button");
			premium_button.addListener(new ClickListener(){

				@Override
				public void clicked(InputEvent event, float x, float y) {
					// TODO Auto-generated method stub
					super.clicked(event, x, y);
					game.resolver.buyPremium();
				}
				
			});
			
			Gdx.app.log("ABSTRACT SCREEN", "CHECK PREMIuM");
			
			premium_button.setSize(Utils.WIDTH_NORMAL() * 0.8f, Utils.HEIGHT_NORMAL());
			stage.addActor(premium_button);
			Timeline.createSequence()
				.push(Tween.set(premium_button, ImageButtonAccessor.POS_XY).target(stage.getWidth() , 0))
				.push(Tween.to(premium_button, ImageButtonAccessor.POS_XY, 0.5f).target(stage.getWidth() - premium_button.getWidth() , premium_button.getHeight() * 0.5f))
			.start(game.tween);
			
		}
	}
	
	public AbstractScreen(SneakySnatcher game){
		this(game, new Stage() ,State.NONE);
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(stage == null) return;
		
		if(!SettingsManager.isLiteVersion){
			if(!SettingsManager.hasRetrieved && game.resolver != null){
				String profile = game.resolver.retrieveProfile();
				if(profile!=null && !profile.equals("")){
					if(profile.trim().equals("0")){
						SettingsManager.hasRetrieved = true;
					}
					else{
						//String[] text = profile.split("\\s+");
						Gdx.app.log("ABSTRACTSCREEN", "updating profile... "  );
						SettingsManager.hasRetrieved = true;
						//SettingsManager.setEmail( text[0] );
						game.service.setProfile( profile );
						game.resolver.toast("You need to restart the game. In order for the update data to take effect.");
						Gdx.app.log("ABSTRACTSCREEN", "updated profile... " + SettingsManager.EMAIL_ADDRESS);
					}
				}
			}
		}
			
		Gdx.gl20.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//Gdx.gl20.glClearColor(0, 0, 0, 1f);

        
        Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, -0.2f);
        
        // clear previous frame
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        camera.update();
        
		
		delta = Math.min(1/60f, delta);
		
		//stage.act(delta);
		//stage.draw();
		
				
		renderSub(delta);
		if( game.tween.getObjects().size() > 0 &&  game.tween.size() > 0){
			game.tween.update(delta);
		}
		
		if(hasPrevious){
			if(Gdx.input.isKeyPressed(Keys.BACK)){
					BackKeyPressed();
			}
			if(debugging){
				fpsLabel.setText("FPS : " + String.valueOf(Gdx.graphics.getFramesPerSecond()));
				Gdx.app.log( "ASCREEN" ,(Gdx.app.getNativeHeap() / (1024*1024f) + ", " + Gdx.app.getJavaHeap() / (1024*1024f)) );
			}
		}
		/*
		 *  checking if screen is touched
		 *  then it isnt idle
		 */
		if(Gdx.input.isTouched()){
			idle_time = 0;
		}
		/*
		 *  adding idle time 
		 *  delta time + users total idle time 
		 */
		idle_time+=delta;
		
		
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		//stage.setViewport(width, height, false);
		
		float aspectRatio = (float)width / (float)height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f,0f);
		
		if(aspectRatio > ASPECT_RATIO){
            scale = (float)height/(float)VIRTUAL_HEIGHT;
            crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
            crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        }
 
        float w = (float)VIRTUAL_WIDTH*scale;
        float h = (float)VIRTUAL_HEIGHT*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
        
        //stage.setViewport(viewport.width, viewport.height, true);
        //Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
        //        (int) viewport.width, (int) viewport.height);
        

	}
	
	public void BackKeyPressed(){
		Gdx.app.log("A SCREEN" , "Back key pressed disposed");		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub


		showSub();
		if(hasPrevious){
			fpsLabel = new Label("" , Assets.skin, "large_font");
			fpsLabel.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
			fpsLabel.setPosition(stage.getWidth() - fpsLabel.getWidth() * 2, 0);
			
			stage.addActor(fpsLabel);
		}		
		checkForPremium();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		hideSub();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		MusicManager.getInst().pause();
		SettingsManager.save();
		game.service.persist();
		game.resolver.save(game.resolver.emails() , new Json().toJson( game.service.profile ));
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		MusicManager.getInst().resume();
		Gdx.app.log("A SCREEN", "music manager and effect manager resumed!");
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		disposeSub();
				
		/*
		 *  problem with disposed
		 */
		if(stage!=null){
			stage.clear();
			stage.dispose();
			stage = null;
		}
	}
	
	public SneakySnatcher getGame(){
		return game;
	}
	
	

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public float getTimeLapse(){
		return timeLapse;
	}

	/**
	 * @return the debugging
	 */
	public boolean isDebugging() {
		return debugging;
	}

	/**
	 * @param debugging the debugging to set
	 */
	public void setDebugging(boolean debugging) {
		this.debugging = debugging;
	}	
	
	/**
	 * @return the idle_time
	 */
	public float getIdle_time() {
		return idle_time;
	}

	/**
	 * @param idle_time the idle_time to set
	 */
	public void setIdle_time(float idle_time) {
		this.idle_time = idle_time;
	}




	/*
	 *  the label to be used during showing messagees
	 */
	private SneakyLabel label; 
	/*
	 *  for messaging all screens have the ability to show messages
	 *  
	 */
	
	
	protected void show(String message ,final TablePoolable messageOverlay,
			float first_delay , float second_delay , float third_delay,
			ShowType type){
		stage.addActor(messageOverlay);
		if(labelsArray.size > 0 ){
			
			stage.getRoot().removeActor(labelsArray.removeIndex(labelsArray.size - 1));
		}
		labelsArray.add(messageOverlay);
		

		
		Timeline.createSequence()
			.push(Tween.to(messageOverlay, TableAccessor.POS_Y , first_delay).target(stage.getHeight() * type.getY()).ease(Quint.INOUT))
			.push(Tween.to(messageOverlay, TableAccessor.POS_Y , second_delay).target(stage.getHeight() * type.getY()))
			.push(Tween.to(messageOverlay, TableAccessor.POS_Y, third_delay).target(stage.getHeight() * 2).ease(Quint.OUT))
			.setCallbackTriggers(TweenCallback.BEGIN)
			.setCallback(new TweenCallback(){

				@Override
				public void onEvent(int arg0, BaseTween<?> arg1) {
					// TODO Auto-generated method stub
					
					if(this!= null && stage!= null && messageOverlay != null){
						stage.getRoot().removeActor(messageOverlay);
						Gdx.app.log("MESSAGE OVERLAY", "DISPOSED");
					}else{
						
						String m = "WAS NOT DISPOSED! because " + " THIS SCREEN = " + this + " STAGE = " + stage + " messageOverlay = " + messageOverlay; 
						Gdx.app.log("MESSAGE OVERLAY", m);
					
					}
				}
				
			})
		.start(game.tween);
	}
	
	protected void show(String message , Color color , float alpha ,
			float first , float second, float third,
			ShowType type){
		if(state == State.SPLASH || state == State.NONE) return;
		
		//label.setColor(color);
		//label.getStyle().fontColor = color;
		TablePoolable messageOverlay = showLabels.obtain();
		
		messageOverlay.setAlpha(alpha);
		SneakyLabel label = (SneakyLabel) messageOverlay.getChildren().get(0);
		label.getStyle().fontColor = color;
		label.setText(message);
		label.setAlignment(Align.center);
		messageOverlay.setPosition(stage.getWidth() - label.getTextBounds().width, stage.getHeight() * 2);
		messageOverlay.setSize(label.getTextBounds().width, label.getTextBounds().height + 10);
		
		show(message , messageOverlay , first, second , third , type);
	}
	
	
	public void show(String message , Color color , float alpha ){
		show(message , color , alpha , 2f, 5f, 3f , ShowType.BOTTOM);
	}
	
	public void show(String message , Color color){
		show(message, color , 0.2f );
	}
	
	public void show(String message , Color color , float alpha , ShowType type){
		show(message, color , alpha , 2 , 5 , 3 , type);
	}
	

	public enum ShowType{
		TOP(0.8f),
		MIDDLE(0.78f),
		BOTTOM(0.76f),
		;
		
		private float y = 0f;
		private ShowType(float yy){
			y = yy;
		}
		public float getY(){
			return y;
		}
	}
	
	protected TweenCallback killCallback = new TweenCallback(){

		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			// TODO Auto-generated method stub
			arg1.kill();
			
		}
		
	};
	
	public void installProcessor(){
		Gdx.input.setInputProcessor(stage);
	}

	public boolean isCanDispose() {
		return canDispose;
	}

	public void setCanDispose(boolean canDispose) {
		this.canDispose = canDispose;
	}
	
	
}
