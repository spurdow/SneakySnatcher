package com.teampora.screens;



import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Quint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.accessor.AnimatedImageAccessor;
import com.teampora.accessor.ImageAccessor;
import com.teampora.accessor.StarBoxAccessor;
import com.teampora.accessor.TableAccessor;
import com.teampora.buttons.SettingsButton;
import com.teampora.buttons.SettingsButton.SettingsButtonType;
import com.teampora.buttons.SneakyTextButton;
import com.teampora.game.State;
import com.teampora.groups.FruitMeter;
import com.teampora.groups.HelpBox;
import com.teampora.groups.ImageNumberBox;
import com.teampora.groups.ScoreBox;
import com.teampora.groups.StarBox;
import com.teampora.managers.MusicEffectManager;
import com.teampora.managers.MusicEffectManager.MusicEffectType;
import com.teampora.managers.SettingsManager;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.tables.TableOverlay;
import com.teampora.utils.AnimatedImage;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class MenuScreen extends AbstractScreen{
	
	public final static String LOG_TAG = "MENU";
	
	public final static String MUSIC_BG = "data/music_ogg/menuBGMusic.ogg";
	
	private Button music;
	private Button sound;
	private Button help;
	
	private SneakyTextButton adventure;
	private TextButton classic;
	
	private TextButton helpButton;
	private TextButton tools;
	private TextButton credits;
	private TextButton highScores;
	
	private Table background;
	private Table settings;
	
	private TableOverlay leftTable;
	private TableOverlay rightTable;
	
	private Image sneaky_snatcher_image;
	
	private ImageNumberBox coinBox;
	
	HelpBox helpBox;
	
	
	
	public MenuScreen(SneakySnatcher game, Stage stage,State state) {
		super(game, stage, state);
		
		Tween.registerAccessor(TableOverlay.class, new TableAccessor());
		Tween.registerAccessor(Image.class, new ImageAccessor());
		
		
	}
	
	@Override
	public void renderSub(float delta) {
		// TODO Auto-generated method stub
		stage.act(delta);
		stage.draw();
		
	}


	@Override
	public void showSub() {
		// TODO Auto-generated method stub
		Gdx.app.log("MENU", "SHOW");	
		
		
		Assets.skin.add("sfont",game.small_font);
		Assets.skin.add("mfont", game.mid_font);
		Assets.skin.add("lfont", game.large_font);
		Assets.skin.add("xlfont", game.xl_font);
		Assets.skin.add("smallFontColor" , Color.WHITE);
		Assets.skin.add("mediumFontColor", new Color(255f/255f , 251f/255f , 0f , 1f));
		Assets.skin.add("bigFontColor", Color.BLACK);
	
		Assets.skin.add("small_font",new LabelStyle(Assets.skin.getFont("sfont") , Assets.skin.getColor("smallFontColor")));
		Assets.skin.add("medium_font", new LabelStyle(Assets.skin.getFont("mfont") , Assets.skin.getColor("mediumFontColor")));
		Assets.skin.add("large_font",new LabelStyle(Assets.skin.getFont("lfont") , Assets.skin.getColor("bigFontColor")) );
		Assets.skin.add("xlarge_font" , new LabelStyle(Assets.skin.getFont("xlfont") , new Color(0.9f,0.7f,0f,1)));
		
		
		/*
		 *  Coin box gives the user the visibility of its current achieved coins
		 */
		
		coinBox = new ImageNumberBox(game.screen.profile.getCoins()  , Assets.fruit_coin, Assets.background_dialog , this);
		coinBox.setPosition(0, stage.getHeight() - coinBox.getHeight());
		
		LabelStyle scoreLabelStyle = new LabelStyle(Assets.skin.getFont("lfont") , Color.LIGHT_GRAY);
		scoreLabelStyle.background = new TextureRegionDrawable(Assets.skin.getRegion("ss-scoreboard"));
		Assets.skin.add("scoreLabel", scoreLabelStyle);
		Image image = new Image(Assets.basket);
		ImageTextButton.ImageTextButtonStyle itbStyle = new ImageTextButton.ImageTextButtonStyle();
		itbStyle.up = image.getDrawable();
		itbStyle.font = Assets.skin.getFont("lfont");
		itbStyle.fontColor = Color.LIGHT_GRAY;
		Assets.skin.add("score", itbStyle);
				
		
		ImageTextButton button = new ImageTextButton("test", Assets.skin, "score");
		button.setSize(Utils.WIDTH_NORMAL(), Utils.HEIGHT_NORMAL());
		button.setPosition(stage.getWidth()*0.4f, stage.getHeight()/3);
		
		
		background = new Table();
		background.setBackground(new TextureRegionDrawable(Assets.background));
		background.setFillParent(true);
	
		settings = new Table();
		settings.setFillParent(true);
		

		help = new SettingsButton(Assets.skin, "ss-toggle-help" , SettingsButtonType.Help , this);
		music = new SettingsButton(Assets.skin , "ss-toggle-music" , SettingsButtonType.Music , this);
		sound = new SettingsButton(Assets.skin , "ss-toggle-sound" , SettingsButtonType.Sound , this);
		
		settings.right().top().add(music).width(Utils.WIDTH_SMALL()).height(Utils.HEIGHT_SMALL()).pad(3);
		settings.add(sound).width(Utils.WIDTH_SMALL()).height(Utils.HEIGHT_SMALL()).pad(3);
		settings.add(help).width(Utils.WIDTH_SMALL()).height(Utils.HEIGHT_SMALL()).pad(3);
		
		@SuppressWarnings("unused")
		float scale = stage.getWidth() / stage.getHeight() / 3f;
		
		adventure = new SneakyTextButton("ADVENTURE", Assets.skin);
		classic = new SneakyTextButton("CLASSIC" , Assets.skin  );
		helpButton = new SneakyTextButton("HELP" , Assets.skin  );
		tools = new SneakyTextButton("TOOLS" , Assets.skin );
		credits = new SneakyTextButton("CREDITS" , Assets.skin );
		highScores = new SneakyTextButton("SCORES" , Assets.skin);

		
		/*
		 *  check whether it is lite version or not
		 *  true = disable adventure (default)
		 *  false = enable adventure button
		 */
		if(SettingsManager.isLiteVersion){
			adventure.setLockRegion(Assets.lock);
			adventure.setTouchable(Touchable.disabled);
		}
	
		/*
		 * main menu actions
		 */
		highScores.addListener(new ClickListener(){

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.screen.getNextScreen(State.SCORES));
				super.clicked(event, x, y);
				
			}
			
		});
		
		adventure.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.screen.getNextScreen(State.MAP));
				super.clicked(event, x, y);
			}
			
		});
		
		
		credits.addListener(new ClickListener(){

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.screen.getNextScreen(State.CREDITS));
				super.clicked(event, x, y);
			}
			
		});
		
		classic.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.screen.getNextScreen(State.CLASSIC));
				super.clicked(event, x, y);
			}
			
		});
		
		tools.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				//game.setScreen(new TransitionScreen(game,(MenuScreen) game.getScreen(),game.screen.getNextScreen(State.TOOLS))); 
				game.setScreen(game.screen.getNextScreen(State.TOOLS));
				super.clicked(event, x, y);
			}
			
		});
		
		sneaky_snatcher_image = new Image(Assets.sneaky_snatcher_titles);
		
		
		sneaky_snatcher_image.setSize(Utils.WIDTH_LARGE() * 0.8f, Utils.HEIGHT_LARGE() * 1.3f);
		sneaky_snatcher_image.setPosition(stage.getWidth() / 2 - (sneaky_snatcher_image.getWidth() / 2) , stage.getHeight() / 2 - (sneaky_snatcher_image.getHeight() / 2));
		
		

		
		leftTable = new TableOverlay(Assets.dialog_patch , - (Utils.NORMAL_SCALE_X * stage.getWidth() + 10f) , 0 , Utils.NORMAL_SCALE_X * stage.getWidth() + 10f , stage.getHeight());
		leftTable.setAlpha(0);
		leftTable.center().add(adventure).size(Utils.WIDTH_NORMAL(), Utils.HEIGHT_NORMAL()).row();
		leftTable.add(classic).width(Utils.WIDTH_NORMAL()).height(Utils.HEIGHT_NORMAL()).row();
		
		rightTable = new TableOverlay(Assets.dialog_patch , stage.getWidth() + Utils.WIDTH_NORMAL() , 0 , Utils.NORMAL_SCALE_X * stage.getWidth() + 10f  , stage.getHeight() );
		rightTable.setAlpha(0);
		rightTable.center().right().add(helpButton).width(Utils.WIDTH_NORMAL()).height(Utils.HEIGHT_NORMAL()).row();
		//menu.add(classic).width(Utils.WIDTH_NORMAL()).height(Utils.HEIGHT_NORMAL()).row();
		rightTable.add(tools).width(Utils.WIDTH_NORMAL()).height(Utils.HEIGHT_NORMAL()).row();
		rightTable.add(credits).width(Utils.WIDTH_NORMAL()).height(Utils.HEIGHT_NORMAL()).row();
		rightTable.add(highScores).width(Utils.WIDTH_NORMAL()).height(Utils.HEIGHT_NORMAL()).row();
		
		
		helpButton.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.screen.getNextScreen(State.HELP));
				super.clicked(event, x, y);
			}
			
		});


		helpBox = new HelpBox(new NinePatchDrawable(Assets.menu_overlay) , 0 , this);
		
		
		
		stage.addActor(background);
		stage.addActor(coinBox);
		stage.addActor(sneaky_snatcher_image);
		stage.addActor(rightTable);
		stage.addActor(settings);
		stage.addActor(leftTable);
		//stage.addActor(helpBox);

		Timeline.createParallel()
		.beginParallel()
			.push(Tween.to(rightTable, TableAccessor.POS_X, 2.0f).target( stage.getWidth() - (Utils.WIDTH_NORMAL() + 10f) ).ease(Quint.IN))
			.push(Tween.to(leftTable, TableAccessor.POS_X, 2.0f).target( Utils.NORMAL_SCALE_X ).ease(Quint.IN))
		.end()
		.start(game.tween);
		sneaky_snatcher_image.setOrigin(sneaky_snatcher_image.getWidth()/2, sneaky_snatcher_image.getHeight()/2);

		
	}

	@Override
	public void disposeSub() {
		// TODO Auto-generated method stub
		settings.clear();
		leftTable.clear();
		rightTable.clear();
		background.clear();
	}

	@Override
	public void hideSub() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void BackKeyPressed() {
		// TODO Auto-generated method stub
		if(game.resolver != null)
			game.resolver.exit();
	}

	
	
}
