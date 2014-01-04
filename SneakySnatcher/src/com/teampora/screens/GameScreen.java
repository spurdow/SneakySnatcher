package com.teampora.screens;

import java.util.ArrayList;
import java.util.Random;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Quint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;
import com.teampora.accessor.AnimatedImageAccessor;
import com.teampora.accessor.ImageAccessor;
import com.teampora.accessor.LabelAccessor;
import com.teampora.accessor.TableAccessor;
import com.teampora.buttons.SneakyImageButton;
import com.teampora.game.GameState;
import com.teampora.game.GameTouch;
import com.teampora.game.GameWorld;
import com.teampora.game.State;
import com.teampora.game.FruitModel.FruitType;
import com.teampora.groups.FruitMeter;
import com.teampora.groups.ImageNumberBox;
import com.teampora.groups.MissionBox;
import com.teampora.groups.ScoreBox;
import com.teampora.groups.FruitMeter.FruitMeterType;
import com.teampora.interfaces.Playable;
import com.teampora.interfaces.Playable.WindowAnimation;
import com.teampora.labels.SneakyLabel;
import com.teampora.labels.TextImageLabel;
import com.teampora.managers.MusicEffectManager;
import com.teampora.managers.MusicManager;
import com.teampora.managers.SettingsManager;
import com.teampora.managers.SoundManager;
import com.teampora.managers.MusicEffectManager.MusicEffectType;
import com.teampora.managers.SettingsManager.FileType;
import com.teampora.managers.SoundManager.SoundType;
import com.teampora.profile.Profile;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.tables.TableOverlay;
import com.teampora.tools.Scores;
import com.teampora.utils.AnimatedImage;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public abstract class GameScreen extends AbstractScreen implements Playable{
	
	/*
	 *  the game world 
	 *  handles the fruit creation , fruit collision,
	 *  everything about physics objects
	 */
	protected GameWorld gameWorld;
	
	/*
	 *  the game touch
	 *  handles the swipe effect touch
	 */
	protected GameTouch touch;
	
	/*
	 *  handles drawing low level textures
	 */
	protected SpriteBatch spriteBatch;
	
	// in game windows
	protected Window start_window;
	protected ScoreBox score_window;
	protected Table background;
	
	protected MissionBox mission;
	

	protected boolean goalAchieved = false;
	
	public GameState gameState;
	
	protected SneakyImageButton start;
	private SneakyImageButton main_menu;
	protected SneakyImageButton restart;
	protected TableOverlay overlay;
	protected TableOverlay upperBar;
	private TableOverlay lowerBar;
	private InputMultiplexer im;
	
	protected TextImageLabel score;
	private Label comboLabel;
	
	private Label luckyLabel;
	
	private final Image basketImage = new Image(Assets.basket);	
	
	private final Image gloveImage = new Image();
	
	private final static float TICK_PER_COMBO = 4;
	private float TICK =  0f;
	
	
	protected int local_score = 0;
	
	private boolean generateCombo = false;
	
	private int comboGenerated = 0;
	/*
	 *  coin box instance
	 *  let the user view the coins
	 */
	
	
	
	private ImageNumberBox coinBox;
	
	private float overlayWidth ;
	private float overlayHeight;
	
	private final Pool<SneakyLabel> sneakyLabels = new Pool<SneakyLabel>(){

		@Override
		protected SneakyLabel newObject() {
			// TODO Auto-generated method stub
			return new SneakyLabel("lfont", new Color(1,0.8f,0,1));
		}
		
	};
	
	private final Pool<SneakyLabel> comboLabels = new Pool<SneakyLabel>(){

		@Override
		protected SneakyLabel newObject() {
			// TODO Auto-generated method stub
			return new SneakyLabel("xlfont", new Color(1,0.8f,0,1));
		}
		
	};
	
	
	private SneakyLabel helpLabel;
	
	
	private SneakyImageButton pauseButton;
	
	
	protected FruitMeter bombs;
	
	private boolean showMessageWhenLite = false;
	
	protected boolean showJoe  = false;
	
	protected Timeline movingJoe ;
	
	protected AnimatedImage farmerJoe;
	
	protected Timeline buyPremium;
	
	public GameScreen(SneakySnatcher game, Stage stage, State state) {
		super(game, stage, state);
		
		// TODO Auto-generated constructor stub
		this.gameState = GameState.Ready;
		Tween.registerAccessor(TableOverlay.class, new TableAccessor());
		Tween.registerAccessor(Label.class, new LabelAccessor());
		Tween.registerAccessor(AnimatedImage.class , new AnimatedImageAccessor());
		
	}

	@Override
	public void renderSub(float delta) {
		// TODO Auto-generated method 
		stage.act(delta);
		stage.draw();
		
		camera.update();

		
		if(gameState == GameState.Running  ){
			checkGame(delta);
		}
		else if(gameState == GameState.End){
			if(goalAchieved){
				gameState = GameState.GameSuccess;
				goalAchieved = false;
				//show("Mission Success!" , Color.GREEN , 0f , 1f, 2f, 1.5f , ShowType.TOP);
				SoundManager.play(SoundType.SUCCESS);
			}
			else{
				gameOver(delta);
				farmerJoe.setStop(false);
				farmerJoe.setDraw(true);
			}
		}


		if(canCombo){
			TICK+=delta;
			if(TICK >= TICK_PER_COMBO){
				generateCombo = true;
				comboGenerated = comboScore;
				TICK = 0f;
				canCombo = false;
				comboScore = 0;
				
			}
		}
		
		/*
		 *  show only help if idle time is eq or greater to max idle time and if game is running
		 *  and inner check if help is on.
		 */
		if(getIdle_time() >= MAX_IDLE_TIME   && gameState == GameState.Running){
			setIdle_time(0);
			if(SettingsManager.isHelpOn){
				int type = random.nextInt(SettingsManager.getLineCount("data/help.txt", FileType.Internal));
				helpUtilities(type);
			}
		}
		
		Table.drawDebug(stage);
		
		
		farmerJoe.update(delta);
		farmerJoe.draw(stage.getSpriteBatch());

	}
	/*
	 *  Override this to change the goal of the game
	 */

	public abstract void checkGame(float delta);
	public abstract void gameOver(float delta);

	@Override
	public void showSub() {
		// TODO Auto-generated method stub
		//setDebugging(true);
		
		
		farmerJoe = new AnimatedImage(0.1f , Assets.atlasRegions , stage.getWidth() - Utils.WIDTH_NORMAL()* 1.8f , 0 , Utils.WIDTH_LARGE()  , Utils.HEIGHT_LARGE() * 2);
		farmerJoe.setStop(false);
		farmerJoe.setDraw(true);
		farmerJoe.setFlip(true);
		
		if(movingJoe == null ){
				movingJoe = Timeline.createSequence()
					.push(Tween.to(farmerJoe, AnimatedImageAccessor.POS_Y, 1).target(5))
					.push(Tween.to(farmerJoe, AnimatedImageAccessor.POS_Y, 1).target(0)
					.repeat(20, 1));
		}
		
		helpLabel = new SneakyLabel("xlfont" , Color.ORANGE);
		helpLabel.setPosition(0, stage.getHeight() * 0.5f);
		helpLabel.setColor(helpLabel.getColor().r , helpLabel.getColor().g , helpLabel.getColor().b,0);
		
		gloveImage.setColor(gloveImage.getColor().r,gloveImage.getColor().g,gloveImage.getColor().b,0);
		
		// camera
		camera = new OrthographicCamera(48 , 32);
		camera.position.set( 24	 ,0 , 0);
		
		/*
		 *  spritebatch instance
		 */
		spriteBatch = new SpriteBatch();
		
		/*
		 *  world instance
		 */
		gameWorld = new GameWorld(this  , camera , spriteBatch);
		gameWorld.setUpFloor();
		
		touch = new GameTouch();
		
		/* 
		 * pause button isntance
		 */
		pauseButton = new SneakyImageButton("Pause" , Assets.skin , Assets.pause_icon );
		pauseButton.setSize(Utils.WIDTH_EXTRA_SMALL(), Utils.HEIGHT_NORMAL());
		pauseButton.setPosition(0, 0);
		pauseButton.setTouchable(Touchable.disabled);
		
		pauseButton.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				if(gameState == GameState.Running){
					pause();
					restart.setVisible(true);
					super.clicked(event, x, y);
				}
			}
			
		});
		
		
		/*
		 *  the coinbox implementation with its coins, fruit coin image , the dialog bg
		 *  positioned at the right upper corner of the screen
		 *  designed to resize itself
		 */
		coinBox = new ImageNumberBox(game.screen.profile.getCoins() , Assets.fruit_coin, Assets.background_dialog , this);
		coinBox.setPosition(stage.getWidth() - coinBox.getWidth(), stage.getHeight() - coinBox.getHeight());
		/*
		 *  handles event processing input from users, i actually put 3 for the 
		 *  stage = > buttons, etc.
		 *  gameWorld => for checking collision detection between finger and fruits ie slash/touch
		 *  swipe => for creating slash like shape using user's finger touchDrag detection
		 *  
		 */
		
		im = new InputMultiplexer(stage , gameWorld , touch.swipe);
		
		/*
		 *  adding the input multiplexer to the system so that it knows what to process in event
		 *  and it seems that 3 input processors have been joined into one...
		 *  
		 */
		
		Gdx.input.setInputProcessor(im);
		
		
		


		
		/*
		 *  window instance to be displayed during start, pause, reset gameover, game success 
		 */
		start_window = new Window("", Assets.skin ,"ss-window-dim");
		start_window.setSize(stage.getWidth()   , stage.getHeight() + 50f );

		
		/*
		 *  score instance to be displayed only after the game's goal has achieved
		 */
		score_window = new ScoreBox("Mission Success!",Assets.dialog_patch ,
		                Assets.basket , "x 0",
		                Assets.golden_apple , "x 0",
		                Assets.fruit_coin , "x 0",
		                this);
		score_window.setSize(stage.getWidth(), stage.getHeight());

		/*
		score_window.add("Your Scores!", "lfont", Color.ORANGE).width(100);
		score_window.row();
		score_window.add(new Image(Assets.golden_apple)).size(Utils.WIDTH_SMALL() , Utils.HEIGHT_SMALL()).pad(5f);
		score_window.add(newGoldenLabel).width(100);
		score_window.row();			
		score_window.add(new Image(Assets.basket)).size(Utils.WIDTH_SMALL() , Utils.HEIGHT_SMALL()).pad(5f);
		score_window.add(newScoreLabel).width(100);
		score_window.row();
		score_window.add(new Image(Assets.fruit_coin)).size(Utils.WIDTH_SMALL() , Utils.HEIGHT_SMALL()).pad(5f);
		score_window.add(newMyCoins).width(100);
		*/
		
		/*
		 *  bomb disturbance
		 */
		bombs = new FruitMeter(Assets.border, Assets.bomb_active, Assets.bomb_inactive);
		bombs.setPosition(stage.getWidth() - bombs.getWidth() , stage.getHeight() / 2 - bombs.getHeight() / 2);
		bombs.setAlpha(1);
		
		// the upper bar
		upperBar = new TableOverlay(Assets.menu_overlay , 0 , stage.getHeight() * 2 , stage.getWidth() , stage.getHeight() * (Utils.BAR_HEIGHT ) );		
		upperBar.setAlpha(0f);
		
		/*
		 * set mission's goal , mission text etc
		 */
		setMission(GameState.Ready);
		

		
		luckyLabel = new Label("Lucky!" , Assets.skin , "xlfont" , Color.YELLOW);
		luckyLabel.setPosition(-stage.getWidth(), -stage.getHeight());
		luckyLabel.setSize(stage.getWidth()/2, stage.getHeight()/2);
		
		overlayWidth = stage.getWidth() * 0.55f;
		overlayHeight = stage.getHeight() * 0.9f;
		
		overlay = new TableOverlay(Assets.ninePatchButton , -stage.getWidth() * 2 , stage.getHeight() * 0.05f , overlayWidth, overlayHeight );
		//overlay.setAlpha(0.2f);
		//overlay.setSize(Gdx.graphics.getWidth() / 2 - window.getWidth() / 2, Gdx.graphics.getHeight()*2 );
		//overlay.add(start_window).width(stage.getWidth()  ).height(stage.getHeight() ).padTop(20f).padBottom(10f);
		overlay.add(start_window).center();
		overlay.row();
		
		start = new SneakyImageButton("Start" , Assets.skin, Assets.play_icon);
		main_menu = new SneakyImageButton("Main Menu" , Assets.skin , Assets.menu_icon);
		restart  = new SneakyImageButton("Retry" , Assets.skin , Assets.retry_icon);
		restart.setVisible(false);
		
		restart.addListener(new ClickListener(){

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				gameState = GameState.Ready;
				mission.makeVisibleIconAndText();
				setMission(gameState);
				mission.setState(gameState);
				mission.setIconText(SettingsManager.goal);
				restart.setVisible(false);
				farmerJoe.setStop(false);
				super.clicked(event, x, y);
			}
			
		});
		
		start.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				
				if(start.getTag().toString().equals("Start") || start.getTag().toString().equals("Resume")  ){
					if(start.getTag().toString().equals("Start")){
						local_score = 0;
						//score.setTag("0");
					}
					farmerJoe.setStop(true);
					farmerJoe.setDraw(false);
					pauseButton.setTouchable(Touchable.enabled);
					toggleWindow(WindowAnimation.unshow_overlay_lowerbar_show_upperbar).start(game.tween);
					gameState = GameState.Running;
					start_window.setVisible(false);
					start.setTag("Resume");
				}else if(start.getTag().toString().equals("Retry")){
					farmerJoe.setStop(true);
					farmerJoe.setDraw(false);
					gameState = GameState.Ready;
					mission.makeVisibleIconAndText();
					setMission(gameState);
					mission.setState(gameState);
					mission.setIconText(SettingsManager.goal);
					toggleWindow(WindowAnimation.unshow_overlay_show_lowerbar).start(game.tween);
					start.setTag("Start");
					start.setDrawableUp(Assets.play_icon);
					start.setRightArrow(false);
					farmerJoe.setStop(false);
					farmerJoe.setDraw(true);
				}else if(start.getTag().toString().equals("Continue")){
					farmerJoe.setStop(true);
					farmerJoe.setDraw(false);
					continueNext();
				}
				super.clicked(event, x, y);
			}
			
		});
		
		main_menu.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				
				game.setScreen(game.screen.getNextScreen(State.MENU));
				super.clicked(event, x, y);
			}
			
		});
		start_window.center();
		/*
		 *  fix the problem with its width
		 */
		start_window.add(mission).width(start_window.getWidth()).height(start_window.getHeight());
		

		
		// upper bar elements
		score = new TextImageLabel(Assets.background_dialog, "0", "large_font",basketImage, 2f);
		score.setSize(Utils.WIDTH_NORMAL(), Utils.HEIGHT_NORMAL());
		score.setPosition(0, stage.getHeight() - score.getHeight());
		
		
		
		// lower bar
		
		
		lowerBar = new TableOverlay(Assets.blue_gradient , overlay.getWidth() * 0.45f   , 0 ,overlay.getWidth() ,0);
		lowerBar.setAlpha(0);
		//lowerBar.center();
		lowerBar.add(main_menu).width(Utils.WIDTH_EXTRA_SMALL()).height(Utils.HEIGHT_NORMAL()).pad(Utils.WIDTH_SMALL()).uniform();
		//lowerBar.row();
		lowerBar.add(restart).width(Utils.WIDTH_EXTRA_SMALL()).height(Utils.HEIGHT_NORMAL()).pad(Utils.WIDTH_SMALL()).uniform();
		//lowerBar.row();
		lowerBar.add(start).width(Utils.WIDTH_EXTRA_SMALL()).height(Utils.HEIGHT_NORMAL()).pad(Utils.WIDTH_SMALL()).uniform();
		
		//overlay.add(lowerBar).right();
		
		/*
		if(golden_fruit != null && golden_fruit_taken != null){
			upperBar.addActor(golden_fruit);
			upperBar.addActor(golden_fruit_taken);
		}*/
		
		/*
		 * adding background first so that it will be behind the rest of the
		 * elements to be drawn in the screen
		 */
		stage.addActor(background);
		
		/*
		 *  adding coinBox next to background
		 *  its use only for posting current achieved coins
		 *  and its designed to resize itself
		 *  let see , IT DOES RESIZE !! YEAH
		 */
		
		stage.addActor(coinBox);
		
		/*
		 * bombs
		 */
		stage.addActor(bombs);
		
		/*
		 * 
		 */
		stage.addActor(gloveImage);
		
		/*
		 * 
		 */
		stage.addActor(helpLabel);
		
		/*
		 * adding score next to background , this doesnt interfere with coinBox 
		 * we dont need these to be upfront,
		 * its use is only for posting scores
		 */
		stage.addActor(score);
		
		/*
		 * pause button next to the score label
		 */
		stage.addActor(pauseButton);
		
		/*
		 * the upper bar which holds the basket score , and golden fruit score taken
		 */
		stage.addActor(upperBar);
		
		
		/*
		 * the combo label only outputs when there is a combo
		 * see combo label, combo time, tick , tick to combo
		 
		stage.addActor(comboLabel);
		*/
		/*
		 *  the lucky label only outputs when there is a lucky bomb
		 *  see luckyCombo, luckyBomb(), Settings.chance
		 */
		stage.addActor(luckyLabel);
		
		/*
		 * the overlay is the one responsible for the message that will ne displayed 
		 */
		stage.addActor(overlay);
		
		/*
		 *  the lowerbar is responsible for holding the menu buttons during pause, reset, gameover
		 */
		stage.addActor(lowerBar);
		

		
		/*
		 *  the animation of a window dialog using a tween manager
		 *  the overlay as the holder of window dialog
		 *  the lowerbar as the holder of the buttons
		 */
		toggleWindow(WindowAnimation.show_overlay_lowerbar).start(game.tween);
		movingJoe.start(game.tween);
	}


	@Override
	public void disposeSub() {
		// TODO Auto-generated method stub
		start.clear();
		main_menu.clear();
		restart.clear();
		overlay.clear();
		upperBar.clear();
		im.clear();
		gameWorld.release();
		score.clear();
		movingJoe.kill();
		farmerJoe = null;
		Gdx.app.log("CLASSIC_SCREEN", "Disposed");
		
	}

	@Override
	public void hideSub() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		//Gdx.app.log("TAG", "PAUSED");
		if(gameState == GameState.Running){

			farmerJoe.setStop(false);
			farmerJoe.setDraw(true);
			pauseNext();
			gameState = GameState.Pause;
			start_window.setVisible(true);
			toggleWindow(WindowAnimation.show_overlay_lowerbar_unshow_upperbar).start(game.tween);
		}	
		super.pause();
	}
	
	@Override
	public void BackKeyPressed() {
		// TODO Auto-generated method stub
		
		if(gameState == GameState.Running){
			pause();
		}
		
	}
	
	private Label newGoldenLabel = new SneakyLabel("xlfont",Color.YELLOW);
	private Label newScoreLabel = new SneakyLabel("xlfont", Color.YELLOW);
	private Label newMyCoins = new SneakyLabel("xlfont", Color.YELLOW);
	
	@Override
	public void setGoal(FruitType type) {
		/*
		 *  let the other class implements this
		 */
	}
	
	public void gameSuccess(){
		gameState = GameState.End;
		overlay.removeActor(start_window);
		success();
		start.setTag("Continue");
		start.setDrawableUp(Assets.leftArrow_icon);
		start.setRightArrow(true);
		gameWorld.reset();

	}
	
	public abstract void success();
	public abstract void continueNext();
	public abstract void pauseNext();
	
	private void helpUtilities(int type){
		type++;
		Gdx.app.log("type",type+"");
		
		//helpLabel.setColor(helpLabel.getColor().r,helpLabel.getColor().g,helpLabel.getColor().b,0f );
		//helpLabel.setText(Settings.getFileByLineNo("data/help.txt", FileType.Internal, type).toString());
		gloveImage.setPosition(-stage.getWidth(), -stage.getHeight());
		gloveImage.setSize(Utils.WIDTH_NORMAL(), Utils.HEIGHT_NORMAL());
		gloveImage.setDrawable(null);
		Gdx.app.log("HELP", "Message Showing");

		if(type == 1 || type == 4 || type == 5){
			show(SettingsManager.getFileByLineNoFixed("data/help.txt", FileType.Internal, type).toString() , Color.GREEN,0f);
			gloveImage.setDrawable(new TextureRegionDrawable(Assets.pointingGloveFlip));
			gloveImage.setOrigin(gloveImage.getWidth()/2, gloveImage.getHeight()/2);
			gloveImage.setRotation(-20);
			Timeline.createSequence()
			.push(Tween.set(gloveImage, ImageAccessor.ALPHA).target(0))
			.push(Tween.set(gloveImage, ImageAccessor.POS_XY ).target(stage.getWidth() - gloveImage.getWidth() , stage.getHeight()/2))
			.push(Tween.to(gloveImage, ImageAccessor.ALPHA, 10.0f).target(1f).ease(Cubic.INOUT))
			.push(Tween.to(gloveImage, ImageAccessor.POS_XY, 2.0f ).target(stage.getWidth() * 0.6f , 10f).ease(Cubic.INOUT) )
			.push(Tween.to(gloveImage, ImageAccessor.POS_XY, 2.0f ).target(10f , 10f).ease(Cubic.INOUT) )
			.push(Tween.to(gloveImage, ImageAccessor.ALPHA, 1.0f).target(0).ease(Cubic.INOUT))
			.setCallback(new TweenCallback(){
				@Override
				public void onEvent(int arg0, BaseTween<?> arg1) {
					// TODO Auto-generated method stub
					//Assets.pointingGlove.flip(true, false);	
					if(this!=null && stage!=null && gloveImage!=null){
						String m = "IS DISPOSE";
						Gdx.app.log("GLOVE IMAGE ", m);
						stage.getRoot().removeActor(gloveImage);
					}else{
						String m = "WAS NOT DISPOSE BECAUSE STAGE = " + stage;
						Gdx.app.log("GLOVE IMAGE ", m);
					}
				}
			})
			.repeat(2, 10f)
			.start(game.tween);
		}else if(type == 2){
			show(SettingsManager.getFileByLineNoFixed("data/help.txt", FileType.Internal, type).toString() , Color.BLUE , 0);
		}
		else if(type == 3){
			show(SettingsManager.getFileByLineNoFixed("data/help.txt", FileType.Internal, type).toString() , Color.RED , 0);
			gloveImage.setDrawable(new TextureRegionDrawable(Assets.pointingGlove));
			gloveImage.setOrigin(gloveImage.getWidth()/2, gloveImage.getHeight()/2);
			gloveImage.setRotation(0);
			Timeline.createSequence()
			.push(Tween.set(gloveImage, ImageAccessor.ALPHA).target(0))
			.push(Tween.set(gloveImage, ImageAccessor.POS_XY ).target(stage.getWidth() - gloveImage.getWidth() - 10f , stage.getHeight()/2))
			
			.push(Tween.to(gloveImage, ImageAccessor.ALPHA, 10.0f).target(1f).ease(Cubic.INOUT))
			.push(Tween.to(gloveImage, ImageAccessor.POS_X, 2.0f ).target(stage.getWidth() - gloveImage.getWidth() - 20f))
			.push(Tween.to(gloveImage, ImageAccessor.POS_X, 2.0f ).target(stage.getWidth() - gloveImage.getWidth() - 10f))
			.push(Tween.to(gloveImage, ImageAccessor.POS_X, 2.0f ).target(stage.getWidth() - gloveImage.getWidth() - 20f))
			.push(Tween.to(gloveImage, ImageAccessor.POS_X, 2.0f ).target(stage.getWidth() - gloveImage.getWidth() - 10f))
			.push(Tween.to(gloveImage, ImageAccessor.ALPHA, 1.0f).target(0).ease(Cubic.INOUT))
						.setCallback(new TweenCallback(){
				@Override
				public void onEvent(int arg0, BaseTween<?> arg1) {
					// TODO Auto-generated method stub
					if(this!=null && stage!=null && gloveImage!=null){
						String m = "IS DISPOSE";
						Gdx.app.log("GLOVE IMAGE ", m);
						stage.getRoot().removeActor(gloveImage);
					}else{
						String m = "WAS NOT DISPOSE BECAUSE STAGE = " + stage;
						Gdx.app.log("GLOVE IMAGE ", m);
					}
				}
			})
			.repeat(2, 10f)
			.start(game.tween);
		}
	}
	
	protected TweenCallback coinAddCallback = new TweenCallback(){

		@Override
		public void onEvent(int arg0, BaseTween<?> arg1) {
			// TODO Auto-generated method stub
			MusicEffectManager.getIns().stop();
			Gdx.app.log("coin callbak", "called");
			int coins = Integer.parseInt(arg1.getUserData().toString());
			
			/*
			 *  save the coin only when is premium version or isLiteversion but less than max tries
			 */
			
			if(!SettingsManager.isLiteVersion){
				game.screen.profile.setCoins(game.screen.profile.getCoins() + coins);
				coinBox.setCoin(game.screen.profile.getCoins());
			}
			else if(SettingsManager.isLiteVersion && game.screen.profile.getTries() <= Profile.MAX_TRIES){
				game.screen.profile.setCoins(game.screen.profile.getCoins() + coins);
				coinBox.setCoin(game.screen.profile.getCoins());
				game.screen.profile.setTries(game.screen.profile.getTries() + 1);
			}else if(SettingsManager.isLiteVersion && game.screen.profile.getTries() > Profile.MAX_TRIES){
				//show message
				showMessageWhenLite = true;
				checkForPremium();
			}
			
			
			
			ArrayList<Scores> scores = (ArrayList<Scores>) game.screen.profile.getHighScores();
			
			for(int i = 0; i < scores.size() ; i++){
				if(scores.get(i).getScore() < (2*coins)){
					show("New Top Score!"  , Color.GREEN ,1f, ShowType.TOP);

					for(int x = (scores.size() - 1) ; x > i ; x--){
						scores.set(x, scores.get(x - 1));
					}
					scores.set(i, new Scores(2*coins,""));

					break;
				}
			}

			if(showMessageWhenLite){
				show("Buy Premium \n To Continue \n Getting Gold Coins!" , Color.GREEN , 1f , ShowType.BOTTOM);
				
				showMessageWhenLite = false;
			}
			/*
			 *  saving the new coins achieved
			 */
			//game.screen.profile.setHighScores(scores);
			game.service.setProfile(game.screen.profile);
			game.service.persist();
			Gdx.app.log("coins", "saved " + coins);
			clearScores();
			toggleWindow(WindowAnimation.show_lowerbar).start(game.tween);
			local_score = 0;
		}
		
	};
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		if(gameState == GameState.GameOver || 
		   gameState == GameState.Ready    ||
		   gameState == GameState.Pause    ||
		   gameState == GameState.GameSuccess) return;
		

		gameState = GameState.Running;
		super.resume();
					
	}

	@Override
	public void addScore(float nSCore){
		if(gameState == GameState.Running){
			int newScore = Integer.parseInt(score.getText().toString());
			newScore+=nSCore;
			StringBuilder builder = new StringBuilder();
			
			builder.append(newScore);
			score.setText(builder , game.tween);
		}
	}
	protected Random random = new Random();
	@Override
	public int generateRandomGoal() {
		// TODO Auto-generated method stub
		int randomGold = random.nextInt(MAX_GOLD_FRUIT);
		return randomGold < MIN_GOLD_FRUIT ? 5 : randomGold ;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

		mission.hideIconAndText();
		pauseButton.setTouchable(Touchable.disabled);
		retry();
		toggleWindow(WindowAnimation.show_overlay_lowerbar_unshow_upperbar).start(game.tween);
		//reset the bomb meter
		gameWorld.reset();
		clearScores();
	}
	
	public abstract void retry();

	@Override
	public AbstractScreen getScreen() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void bomb() {
		// TODO Auto-generated method stub
		bombs.setActiveFruits((bombs.getActiveFruits() + 1 ));
		if(bombs.getMax_number() == bombs.getActiveFruits()){
			gameState = GameState.End;
		}		
	}

	@Override
	public GameState getState() {
		// TODO Auto-generated method stub
		return gameState;
	}
	
	/*
	 * 
	 *  Toggles the window depnding on windowanimation
	 * 
	 */
	@Override
	public Timeline toggleWindow(WindowAnimation anim ){
		SoundManager.play(SoundType.DIALOG);
		
		float overlayX= stage.getWidth() / 4;
		float lowerBarX = stage.getHeight() * 0.13f;
		
		
		if(anim == WindowAnimation.show_overlay){
			return Timeline.createParallel()
					//.push(Tween.set(overlay, TableAccessor.SIZE).target(0 , 0))
					.push(Tween.set(overlay, TableAccessor.ALPHA).target(0))
					.push(Tween.to(overlay, TableAccessor.ALPHA, 2).target(1))
					//.push(Tween.to(overlay, TableAccessor.SIZE, 2).target(overlayWidth , overlayHeight).ease(Quint.OUT))
			 		.push(Tween.to(overlay, TableAccessor.POS_X, 2).target( overlayX ).ease(Quint.OUT))
			 		
			 		
			 	.setCallback(killCallback);
		 		
		}else if(anim == WindowAnimation.show_lowerbar){
			return Timeline.createParallel()
	 			.push(Tween.to(lowerBar, TableAccessor.POS_Y, 2.0f).target( lowerBarX ).ease(Quint.OUT))
	 		.setCallback(killCallback);
	 		
		}else if(anim == WindowAnimation.show_overlay_lowerbar){
			return Timeline.createParallel()
				//.push(Tween.set(overlay, TableAccessor.SIZE).target(0 , 0))
				.push(Tween.set(overlay, TableAccessor.ALPHA).target(0))
				.push(Tween.to(overlay, TableAccessor.ALPHA, 2).target(1))
				//.push(Tween.to(overlay, TableAccessor.SIZE, 2).target(overlayWidth , overlayHeight).ease(Quint.OUT))
		 		.push(Tween.to(overlay, TableAccessor.POS_X, 2).target( overlayX ).ease(Quint.OUT))
		 		.push(Tween.to(lowerBar, TableAccessor.POS_Y, 2.0f).target( lowerBarX ).ease(Quint.OUT))
		 		.setCallback(killCallback);
		}else if(anim == WindowAnimation.show_overlay_lowerbar_unshow_upperbar){
			return Timeline.createParallel()
				.push(Tween.set(overlay, TableAccessor.SIZE).target(0 , 0))
				.push(Tween.set(overlay, TableAccessor.ALPHA).target(0))
				.push(Tween.to(overlay, TableAccessor.ALPHA, 2).target(1))
				.push(Tween.to(overlay, TableAccessor.SIZE, 2).target(overlayWidth , overlayHeight).ease(Quint.OUT))
		 		.push(Tween.to(overlay, TableAccessor.POS_X, 2).target( overlayX ).ease(Quint.OUT))
		 		.push(Tween.to(upperBar, TableAccessor.POS_Y, 2.0f).target( stage.getHeight() *2 ).ease(Quint.OUT))
		 		.push(Tween.to(lowerBar, TableAccessor.POS_Y, 2.0f).target( lowerBarX ).ease(Quint.OUT))
		 		.setCallback(killCallback);
		}
		else if(anim == WindowAnimation.unshow_overlay_lowerbar){
			return Timeline.createParallel()
				.push(Tween.to(overlay, TableAccessor.POS_X, 2.0f).target( - stage.getWidth() * 3).ease(Quint.OUT))
				//.push(Tween.to(upperBar, TableAccessor.POS_Y, 2.0f).target(  stage.getHeight() * 2).ease(Quint.OUT))
				.push(Tween.to(lowerBar, TableAccessor.POS_Y, 2.0f).target(-stage.getHeight()).ease(Quint.OUT))
				.setCallback(killCallback);
		}else if(anim == WindowAnimation.unshow_overlay_lowerbar_show_upperbar){
			return Timeline.createParallel()
				.push(Tween.to(overlay, TableAccessor.POS_X, 2.0f).target( - stage.getWidth() * 3).ease(Quint.OUT))
				.push(Tween.to(upperBar, TableAccessor.POS_Y, 2.0f).target( stage.getHeight() - (upperBar.getHeight() )).ease(Quint.OUT))
				.push(Tween.to(lowerBar, TableAccessor.POS_Y, 2.0f).target(-stage.getHeight()).ease(Quint.OUT))
				.setCallback(killCallback);
		}else if(anim == WindowAnimation.unshow_overlay_show_lowerbar){
			return Timeline.createSequence()
				.beginParallel()
					.push(Tween.to(overlay, TableAccessor.POS_X, 2.0f).target( -stage.getWidth() * 3).ease(Quint.OUT))
					.push(Tween.to(lowerBar, TableAccessor.POS_Y, 2.0f).target(-stage.getHeight()).ease(Quint.OUT))				
				.end()
				.beginParallel()
				//.push(Tween.set(overlay, TableAccessor.SIZE).target(0 , 0))
				.push(Tween.set(overlay, TableAccessor.ALPHA).target(0))
				.push(Tween.to(overlay, TableAccessor.ALPHA, 2).target(1))
				//.push(Tween.to(overlay, TableAccessor.SIZE, 2).target(overlayWidth , overlayHeight).ease(Quint.OUT))
		 		.push(Tween.to(overlay, TableAccessor.POS_X, 2).target( overlayX ).ease(Quint.OUT))
		 		.push(Tween.to(lowerBar, TableAccessor.POS_Y, 2.0f).target( lowerBarX ).ease(Quint.OUT))
				.end()
				.setCallback(killCallback);
		}
				

		
		
		return null;

	}

	//world to screen position
	private Vector3 position = new Vector3();
	@Override
	public void addScoreWithAnimation(final float score, float startX, float startY, boolean projectPosition) {
		// TODO Auto-generated method stub
		//float targetY =
		local_score+=score;
		position.set(startX , startY , 0);
		if(projectPosition){
			camera.project(position);
		}
		SneakyLabel scoreLabel = sneakyLabels.obtain();
		if(score > 0)
			scoreLabel.setText(new String("+").concat(String.valueOf((int)score)));
		else
			scoreLabel.setText(String.valueOf((int)score));
		//listLabels.add(scoreLabel);
		stage.addActor(scoreLabel);
		if(score <= 2 && score > 0){
			comboScore++;
			if(comboScore == MIN_COMBO){
				canCombo = true;
			}
			if(comboScore > MIN_COMBO){
				canCombo = true;
				TICK = 0;
			}
			if(comboScore > 4 ){
				int comNo = comboScore < 10 ? ((comboScore % 5 ) + 1 ): 5;
				String[] text = SettingsManager.getFileByLineNo("data/combo.txt", FileType.Internal,comNo).toString().split("[,]");
				Color c = null;
				if(text[1].equals("green")){
					c  = Color.GREEN;
				}else if(text[1].equals("magenta")){
					c = Color.MAGENTA;
				}else if(text[1].equals("cyan")){
					c = Color.CYAN;
				}else if(text[1].equals("pink")){
					c = Color.PINK;
				}else if(text[1].equals("red")){
					c = Color.RED;
				}
				show(text[0] , c , 1f , 1f , 1f , 0.5f , ShowType.MIDDLE);
				switch(comNo){
				case 1 : SoundManager.play(SoundType.STEAL , 2f, 1 , 0);break;
				case 2 : SoundManager.play(SoundType.STEAL , 3f, 1.1f , 0);break;
				case 3 : SoundManager.play(SoundType.STEAL, 4f, 1.2f, 0); break;
				case 4 : SoundManager.play(SoundType.STEAL , 5f , 1.3f , 0); break;
				case 5 : SoundManager.play(SoundType.STEAL , 6f , 1.4f , 0); break;
					
				}
				/*
				 * combo generator
				 */
				
				if(projectPosition && gameState == GameState.Running){
					comboLabel = comboLabels.obtain();
					builder.setLength(0);
					builder.append("Combo ").append("+ ").append(comboScore);
					comboLabel.setText(builder.toString());
					stage.addActor(comboLabel);
					Timeline.createSequence()
					//.push(Tween.set(comboLabel, LabelAccessor.SIZE_XY).target(Utils.WIDTH_NORMAL()*5 , Utils.HEIGHT_NORMAL()*4))
					.push(Tween.set(comboLabel, LabelAccessor.POS_XY).target(position.x , position.y + 40f ))
					.push(Tween.set(comboLabel, LabelAccessor.SCALE_XY).target(0,0))
					.push(Tween.to(comboLabel, LabelAccessor.COLOR, 1.0f).target(comboLabel.getColor().r,comboLabel.getColor().g,comboLabel.getColor().b,1f).ease(Bounce.INOUT))
					.push(Tween.to(comboLabel, LabelAccessor.SCALE_XY, 0.5f).target(3,3).ease(Bounce.INOUT))
					.push(Tween.to(comboLabel, LabelAccessor.COLOR, 0.5f).target(comboLabel.getColor().r,comboLabel.getColor().g,comboLabel.getColor().b,0).ease(Quint.OUT))
					.setUserData(comboLabel)
					.setCallback(new TweenCallback(){

						@Override
						public void onEvent(int arg0, BaseTween<?> arg1) {
							// TODO Auto-generated method stub
							if(stage != null){
								SneakyLabel comboSource = (SneakyLabel)arg1.getUserData();
								stage.getRoot().removeActor(comboSource);
								comboLabels.free(comboSource);
								if(generateCombo && gameState == GameState.Running){
									addScoreWithAnimation(comboGenerated * 2 , position.x , position.y , false);
									comboGenerated = 0;
									generateCombo = false;
								}
							}else{
								Gdx.app.log("SNEAKY LABEL ", "WAS NOT REMOVED PROPERLY");
							}
							
						}
						
					})
					.start(game.tween);
				}
			}
		}else if(score < 0){
			TICK+=2;
		}
		Timeline.createSequence()
			.push(Tween.set(scoreLabel, LabelAccessor.SIZE_XY).target(Utils.WIDTH_NORMAL() , Utils.HEIGHT_NORMAL()))
			.push(Tween.set(scoreLabel, LabelAccessor.POS_XY).target(position.x, position.y))
			.push(Tween.to(scoreLabel, LabelAccessor.COLOR, 1.0f).target(scoreLabel.getColor().r,scoreLabel.getColor().g,scoreLabel.getColor().b,1f))
			.push(Tween.to(scoreLabel, LabelAccessor.POS_XY, 1.0f).target(position.x, position.y + 60f).ease(Quint.OUT))
			.push(Tween.to(scoreLabel, LabelAccessor.POS_XY, 1f).target(0f , (stage.getHeight() - this.score.getHeight())).ease(Quint.OUT))
			.push(Tween.to(scoreLabel, LabelAccessor.COLOR, 1f).target(scoreLabel.getColor().r,scoreLabel.getColor().g,scoreLabel.getColor().b,0).ease(Quint.OUT))
			.setUserData(scoreLabel)
			.setCallback(new TweenCallback(){
				@Override
				public void onEvent(int arg0, BaseTween<?> arg1) {
					// TODO Auto-generated method stub
					if(stage != null){
						SneakyLabel source = (SneakyLabel) arg1.getUserData();
						addScore(score);
						stage.getRoot().removeActor(source);
						sneakyLabels.free(source);
					}else{
						Gdx.app.log("SNEAKY LABEL ", "WAS NOT DISPOSED PROPERLY");
					}
				}
			})
			//.setCallback(killCallback)
		.start(game.tween);
		
		
		
		
	}
	//combo builder
	private final StringBuilder builder = new StringBuilder();
	/*
	 *  combo due time
	 *  volatile because it changes anytime
	 */
	protected volatile boolean canCombo = false;
	
	protected int comboScore = 0;
	
	private final static int MIN_COMBO = 3;

	/*
	 * (non-Javadoc)
	 * @see com.teampora.interfaces.Playable#luckyBomb(float, float)
	 */
	@Override
	public void luckyBomb(float startX , float startY) {
		// TODO Auto-generated method stub
		position.set(startX, startY, 0);
		camera.project(position);
		
		Timeline.createSequence()
			.push(Tween.set(luckyLabel, LabelAccessor.POS_XY).target(position.x, position.y))
			.push(Tween.to(luckyLabel, LabelAccessor.COLOR, 1.0f).target(luckyLabel.getColor().r,luckyLabel.getColor().g,luckyLabel.getColor().b,1f))
			.push(Tween.set(luckyLabel, LabelAccessor.SIZE_XY).target(Utils.WIDTH_NORMAL() , Utils.HEIGHT_NORMAL()))
			.push(Tween.to(luckyLabel, LabelAccessor.POS_XY, 1.0f).target(position.x, position.y + 45f).ease(Quint.OUT))
			.push(Tween.to(luckyLabel, LabelAccessor.COLOR, 3f).target(luckyLabel.getColor().r,luckyLabel.getColor().g,luckyLabel.getColor().b,0).ease(Quint.OUT))
		.start(game.tween);
	}
	
	public abstract void setMission(GameState state);
	public abstract void clearScores();
}
