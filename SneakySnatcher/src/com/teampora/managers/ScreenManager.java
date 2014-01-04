package com.teampora.managers;

import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.teampora.game.FruitModel;
import com.teampora.game.State;
import com.teampora.interfaces.Playable;
import com.teampora.profile.Profile;
import com.teampora.screens.AbstractScreen;
import com.teampora.screens.AdventureScreen;
import com.teampora.screens.ClassicScreen;
import com.teampora.screens.GameScreen;
import com.teampora.screens.CreditsScreen;
import com.teampora.screens.HelpScreen;
import com.teampora.screens.LevelScreen;
import com.teampora.screens.MapScreen;
import com.teampora.screens.MenuScreen;
import com.teampora.screens.ScoreScreen;
import com.teampora.screens.SplashScreen;
import com.teampora.screens.ToolsScreen;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.utils.Assets;
import com.teampora.utils.Cache;
import com.teampora.utils.Cache.ElderRemoverListener;
import com.teampora.utils.Utils;

public class ScreenManager implements ElderRemoverListener<State, AbstractScreen> {
	
	private final static String LOG_TAG = "SCREEN_MANAGER";
	
	private SneakySnatcher game;
	
	public Profile profile;
	
	private Cache<State , AbstractScreen> cache;
	
	public ScreenManager(){
		cache = new Cache<State , AbstractScreen>(2);
		cache.setRemoverListener(this);
	}
	
	public void setUpGame(SneakySnatcher game){
		this.game = game;
		
		if(!SettingsManager.isLiteVersion && game.resolver != null){
			if(game.resolver.checkConnection()){
				game.resolver.retrieve(game.resolver.emails());
			}else{
				SettingsManager.hasRetrieved = true;
			}
		}
		SettingsManager.PROFILE_VERSION = SettingsManager.getPROFILE_VERSION();
		/*
		 *  retrieves the data that have been used
		 *  in the profile service
		 *  ie hammer used, gloves used etc.
		 *  almost all information is saved here
		 *  
		 *  if no profile retrieved, then it persist to create a new one
		 */
		profile = game.service.retrieveProfile();	
		
		
		if(SettingsManager.isFirstLaunch()){
			/*
			 *  create and retrieve all information from storage
			 *  using preferences we can save / restore the state of 
			 *  settings buttons
			 */
			
			SettingsManager.setMusic(true);
			SettingsManager.setSound(true);
			SettingsManager.setHelp(true);
			SettingsManager.particles = profile.getEquippedHammer().getSwingType();
			SettingsManager.particle_path = profile.getEquippedHammer().getSound().concat("-particle.p");
			/*
			 *  chance default is 0% so no need to change it
			 */
			Gdx.app.log(LOG_TAG, "FIRST LAUNCH" );
			
		}
		else{
			/*
			 *  restore all information from storage
			 *  using preferences we can save/restore the state of
			 *  settings buttons
			 */
			SettingsManager.setMusic(SettingsManager.getMusic());
			SettingsManager.setSound(SettingsManager.getSound());
			SettingsManager.setHelp(SettingsManager.getHelp());
			SettingsManager.color = game.screen.profile.getEquippedHammer().getColor();
			//Settings.chance = profile.getEquippedGloves()==null? 0f: profile.getEquippedGloves().getChance(); 
			SettingsManager.particles = profile.getEquippedHammer().getSwingType();
			SettingsManager.particle_path = profile.getEquippedHammer().getSound().concat("-particle.p");
			Gdx.app.log(LOG_TAG, "NOT FIRST LAUNCH");
		}
		
		

	}
	/*
	 *  load splash screen textures/regions
	 *  this is where the images,music are loaded
	 *  loading all of the game's texture, sounds, music , atlas files etc
	 *  
	 */
	public SplashScreen getSplashScreen(){
		game.assets.loadSplash();
		game.assets.loadAll();
		
		
		return new SplashScreen(game,new Stage(),null,false,State.SPLASH);
	}
	



	/*
	 *  creating a new instance of abstract screen 
	 *  the menu screen 
	 *  check if sound is set/ music is set 
	 *  creating stage
	 *  instance of needed textures and stuff
	 */
	private MenuScreen getMenuScreen(Stage stage){

		
		SettingsManager.chance = profile.getEquippedGloves()==null? 0f: profile.getEquippedGloves().getChance();
		
		Assets.gameAtlas = game.assetMgr.get(Utils.ATLAS_PATH, TextureAtlas.class);
		Assets.skin  = game.assetMgr.get(Utils.SKIN_PATH);
		
		
		Assets.golden_apple =  Assets.gameAtlas.findRegion(FruitModel.GOLDEN_APPLE);
		Assets.background = Assets.gameAtlas.findRegion("menu-screen-bg");
		
		Assets.menu_overlay = Assets.gameAtlas.createPatch("brown");
		
		Assets.temp_fill = Assets.gameAtlas.findRegion("brown");
		Assets.temp_top  = Assets.gameAtlas.findRegion("temp-log");
		
		Assets.sneaky_snatcher_titles = Assets.gameAtlas.findRegion("ss-title");
		
		Assets.background_dialog = Assets.gameAtlas.findRegion("ss-scoreboard");
		Assets.basket = Assets.gameAtlas.findRegion("FS-Basket");
		Assets.tool_dialog = Assets.gameAtlas.createPatch("ss-tool-dialog");
		Assets.window_dialog = Assets.gameAtlas.findRegion("ss-window-bg");
		Assets.fruit_coin = Assets.gameAtlas.createPatch(FruitModel.FRUIT_COIN);
		Assets.bomb_fruit = Assets.gameAtlas.findRegion(FruitModel.BOMB);
		
		Assets.lock = Assets.gameAtlas.findRegion("locked");
		
		Assets.leftArrow_icon = Assets.gameAtlas.findRegion("left-arrow");
		Assets.retry_icon = Assets.gameAtlas.findRegion("ss-retry-icon");
		Assets.play_icon = Assets.gameAtlas.findRegion("ss-play-icon");
		Assets.menu_icon = Assets.gameAtlas.findRegion("ss-menu-icon");
		Assets.pause_icon = Assets.gameAtlas.findRegion("ss-pause-icon");
		Assets.pointingGlove =Assets.gameAtlas.findRegion("pointing-glove");
		Assets.pointingGloveFlip  = Assets.gameAtlas.findRegion("pointing-glove-flip");
		
		Assets.background = Assets.gameAtlas.findRegion("menu-screen");
		
		Assets.star_filled = Assets.gameAtlas.findRegion("star-fill");
		Assets.star_unfilled = Assets.gameAtlas.findRegion("star-nofill");
		
		Assets.selected = Assets.gameAtlas.createPatch("scroll-indicator-selected");
		Assets.unselected = Assets.gameAtlas.createPatch("scroll-indicator-unselected");
		
		Assets.border = Assets.gameAtlas.createPatch("ss-border");
		Assets.bomb_active = Assets.gameAtlas.createPatch("bomb-active");
		Assets.bomb_inactive = Assets.gameAtlas.createPatch("bomb-inactive");
		Assets.dialog_patch = Assets.gameAtlas.createPatch("dialog");
		Assets.nextButton = Assets.gameAtlas.createPatch("right-button");
		Assets.prevButton = Assets.gameAtlas.createPatch("left-button");
		Assets.blue_gradient  = Assets.gameAtlas.createPatch("blue-gradient");
		Assets.help_gloves = Gdx.graphics.getHeight() > 500 ? Assets.gameAtlas.findRegion("Gloves-1024x1024") : Assets.gameAtlas.findRegion("Gloves-512x512");
		Assets.ninePatchButton = Assets.gameAtlas.createPatch("ss-new-button-up");
		Assets.atlasRegions = Assets.gameAtlas.findRegions("farmerJoe");
		
		/*
		 *  special unit of sound
		 */
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL+"fruit_drop.ogg")){
			Assets.drop = game.assetMgr.get(Utils.SOUND_PATH_GENERAL+"fruit_drop.ogg" , Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL+"bomb.ogg")){
			Assets.bomb = game.assetMgr.get(Utils.SOUND_PATH_GENERAL+"bomb.ogg" , Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL+ "wooden_dialog.ogg", Sound.class)){
			Assets.dialog = game.assetMgr.get(Utils.SOUND_PATH_GENERAL+ "wooden_dialog.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL + "button_click.ogg", Sound.class)){
			Assets.click = game.assetMgr.get(Utils.SOUND_PATH_GENERAL + "button_click.ogg");
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL +  "buy.ogg", Sound.class)){
			Assets.buy = game.assetMgr.get(Utils.SOUND_PATH_GENERAL +  "buy.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL+"fruit_coin.ogg", Sound.class)){
			Assets.coin = game.assetMgr.get(Utils.SOUND_PATH_GENERAL+"fruit_coin.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL+"fruit_squash.ogg", Sound.class)){
			Assets.squash = game.assetMgr.get(Utils.SOUND_PATH_GENERAL+"fruit_squash.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL+"game_failed.ogg", Sound.class)){
			Assets.failed = game.assetMgr.get(Utils.SOUND_PATH_GENERAL+"game_failed.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL+"game_success.ogg", Sound.class)){
			Assets.success = game.assetMgr.get(Utils.SOUND_PATH_GENERAL+"game_success.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL+"new_top_score.ogg", Sound.class)){
			Assets.top = game.assetMgr.get(Utils.SOUND_PATH_GENERAL+"new_top_score.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL+"score_down.ogg", Sound.class)){
			Assets.down = game.assetMgr.get(Utils.SOUND_PATH_GENERAL+"score_down.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL+"fruit_steal.ogg", Sound.class)){
			Assets.steal = game.assetMgr.get(Utils.SOUND_PATH_GENERAL+"fruit_steal.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL +"touch_1.ogg", Sound.class)){
			Assets.touch1 = game.assetMgr.get(Utils.SOUND_PATH_GENERAL +"touch_1.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL +"touch_2.ogg", Sound.class)){
			Assets.touch2 = game.assetMgr.get(Utils.SOUND_PATH_GENERAL +"touch_2.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL +"touch_3.ogg", Sound.class)){
			Assets.touch3 = game.assetMgr.get(Utils.SOUND_PATH_GENERAL +"touch_3.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL +"touch_4.ogg", Sound.class)){
			Assets.touch4 = game.assetMgr.get(Utils.SOUND_PATH_GENERAL +"touch_4.ogg", Sound.class);
		}
		if(game.assetMgr.isLoaded(Utils.SOUND_PATH_GENERAL +"starred.ogg", Sound.class)){
			Assets.starred = game.assetMgr.get(Utils.SOUND_PATH_GENERAL +"starred.ogg", Sound.class);
		}
			
		
			
		return new MenuScreen(game, stage,State.MENU);
	}
	
	private ClassicScreen getClassicScreen(Stage stage){
	
		Assets.green_apple =  Assets.gameAtlas.findRegion(FruitModel.GREEN_APPLE);
		Assets.red_apple =  Assets.gameAtlas.findRegion(FruitModel.RED_APPLE);
		Assets.lemon =  Assets.gameAtlas.findRegion(FruitModel.LEMON);
		Assets.lime =  Assets.gameAtlas.findRegion(FruitModel.LIME);
		Assets.orange =  Assets.gameAtlas.findRegion(FruitModel.ORANGE);
		Assets.watermelon =  Assets.gameAtlas.findRegion(FruitModel.WATERMELON);
		Assets.coconut = Assets.gameAtlas.findRegion(FruitModel.COCONUT);
		Assets.bomb_fruit = Assets.gameAtlas.findRegion(FruitModel.BOMB);
		Assets.fruit_splash = Assets.gameAtlas.findRegion(FruitModel.FRUIT_SQUASH);
		
		Assets.background = Assets.gameAtlas.findRegion("classic-screen");
		
		return new ClassicScreen(game , stage , State.CLASSIC );
	}
	
	private ToolsScreen getToolsScreen(Stage stage){
		Assets.background = Assets.gameAtlas.findRegion("menu-screen");
		Assets.additional_background = Assets.gameAtlas.findRegion("ss-tree");
		Assets.orange_gradient  = Assets.gameAtlas.createPatch("orange-gradient");
		return new ToolsScreen(game, stage ,State.TOOLS);
	}
	
	private CreditsScreen getCreditsScreen(Stage stage) {
		// TODO Auto-generated method stub
		Assets.background = Assets.gameAtlas.findRegion("menu-screen");
		Assets.additional_background = Assets.gameAtlas.findRegion("ss-tree");
		Assets.orange_gradient  = Assets.gameAtlas.createPatch("orange-gradient");
		Assets.blue_gradient  = Assets.gameAtlas.createPatch("blue-gradient");
		return new CreditsScreen(game, stage, State.CREDITS);
	}
	
	private ScoreScreen getScoreScreen(Stage stage){
		Assets.background = Assets.gameAtlas.findRegion("menu-screen");
		Assets.additional_background = Assets.gameAtlas.findRegion("ss-tree");
		Assets.blue_gradient  = Assets.gameAtlas.createPatch("blue-gradient");
		return new ScoreScreen(game, stage, State.SCORES);
	}
	
	private MapScreen getMapScreen(Stage stage){
		Assets.background = Assets.gameAtlas.findRegion("menu-screen");
		Assets.additional_background = Assets.gameAtlas.findRegion("ss-tree");
		Assets.orange_gradient  = Assets.gameAtlas.createPatch("orange-gradient");
		Assets.blue_gradient  = Assets.gameAtlas.createPatch("blue-gradient");
		return new MapScreen(game,stage, State.MAP);
	}
	
	private LevelScreen getLevelScreen(Stage stage){
		Assets.background = Assets.gameAtlas.findRegion("menu-screen");
		Assets.additional_background = Assets.gameAtlas.findRegion("ss-tree");
		Assets.lock = Assets.gameAtlas.findRegion("locked");
		Assets.blue_gradient  = Assets.gameAtlas.createPatch("blue-gradient");
		return new LevelScreen(game, stage, State.LEVEL);
	}
	
	
	private AbstractScreen getHelpScreen(Stage stage) {
		// TODO Auto-generated method stub
		Assets.background = Assets.gameAtlas.findRegion("menu-screen");
		Assets.additional_background = Assets.gameAtlas.findRegion("ss-tree");
		Assets.blue_gradient  = Assets.gameAtlas.createPatch("blue-gradient");
		return new HelpScreen(game , stage, State.HELP);
	}
	
	private AdventureScreen getAdventureScreen(Stage stage){
		Assets.green_apple =  Assets.gameAtlas.findRegion(FruitModel.GREEN_APPLE);
		Assets.red_apple =  Assets.gameAtlas.findRegion(FruitModel.RED_APPLE);
		Assets.lemon =  Assets.gameAtlas.findRegion(FruitModel.LEMON);
		Assets.lime =  Assets.gameAtlas.findRegion(FruitModel.LIME);
		Assets.orange =  Assets.gameAtlas.findRegion(FruitModel.ORANGE);
		Assets.watermelon =  Assets.gameAtlas.findRegion(FruitModel.WATERMELON);
		Assets.coconut = Assets.gameAtlas.findRegion(FruitModel.COCONUT);
		Assets.bomb_fruit = Assets.gameAtlas.findRegion(FruitModel.BOMB);
		Assets.fruit_splash = Assets.gameAtlas.findRegion(FruitModel.FRUIT_SQUASH);
		Assets.blue_gradient  = Assets.gameAtlas.createPatch("blue-gradient");
		Assets.background = Assets.gameAtlas.findRegion("classic-screen");
		return new AdventureScreen(game,stage, State.ADVENTURE);
	}
	
	/*
	 *  designed for transition effects but no more time
	 */
	public AbstractScreen getNextScreen(State state){
		
		if(MusicManager.getInst().isPlaying()){
			MusicManager.getInst().stop();
		}
		
		Stage stage = null;
		
		AbstractScreen screen = null;
		
		Gdx.app.log("screen manager", screen +"");
		
		Gdx.app.log("Screen Manager", (screen==null? "null screen creating new" : screen.toString()) + " = " +  state.toString() + "\n Cache size => " + cache.getSet().size());
		
		if(screen == null){
			stage = new Stage();
			switch(state){
			
			case SPLASH:    screen =  getSplashScreen(); break;
			case MENU:      screen = getMenuScreen(stage); break;
			case CLASSIC :  screen = getClassicScreen(stage); break;
			case TOOLS:     screen = getToolsScreen(stage); break;
			case CREDITS:   screen =  getCreditsScreen(stage); break;
			case SCORES:    screen =  getScoreScreen(stage); break;
			case MAP :      screen =  getMapScreen(stage); break;
			case LEVEL: 	screen =  getLevelScreen(stage); break;
			case ADVENTURE: screen = getAdventureScreen(stage); break;
			case HELP : screen = getHelpScreen(stage);break;
			}
		}
		screen.getStage().addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1 , Interpolation.fade)));
		screen.installProcessor();
		return screen;
	}



	@Override
	public boolean destroy(State key, AbstractScreen value) {
		// TODO Auto-generated method stub
		AbstractScreen screen = cache.remove(key);
		Gdx.app.log("Screen Manager"  , "Disposing " + key.toString() + " screen");
		screen.dispose();
		return true;
	}

}