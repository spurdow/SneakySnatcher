package com.teampora.screens;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.accessor.StarBoxAccessor;
import com.teampora.adventure.Level;
import com.teampora.adventure.MapSelection;

import com.teampora.game.FruitModel.FruitType;
import com.teampora.game.GameState;
import com.teampora.game.State;
import com.teampora.groups.MissionBox;
import com.teampora.groups.StarBox;
import com.teampora.interfaces.Playable.WindowAnimation;
import com.teampora.labels.SneakyLabel;
import com.teampora.managers.MusicEffectManager;
import com.teampora.managers.SettingsManager;
import com.teampora.managers.MusicEffectManager.MusicEffectType;
import com.teampora.managers.SettingsManager.FileType;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class AdventureScreen extends GameScreen{
	
	private ArrayList<MapSelection> maps;
	
	private MapSelection map;
	
	private Level level;
	
	private StarBox starBox;
	
	private Label levelNo;

	public AdventureScreen(SneakySnatcher game, Stage stage, State state) {
		super(game, stage, state);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#showSub()
	 */
	@Override
	public void showSub() {
		// TODO Auto-generated method stub
		

		maps = (ArrayList<MapSelection>) game.screen.profile.getMaps();
		for(MapSelection map : maps){
			if(map.isChoosen()){
				this.map = map;
				break;
			}
		}
		/*
		 *  background table instance for the bg image
		 */
		background = new Table();
		background.setFillParent(true);
		background.setBackground(new TextureRegionDrawable(SettingsManager.getRegion(map.getFileName())));
		level = map.getLevels().get(map.getChoosenLevel() - 1);
		Gdx.app.log("level", level.toString());
		super.showSub();
		levelNo = new SneakyLabel("Level " + map.getChoosenLevel() + " of " + map.getLevels().size(),"medium_font");
		levelNo.setPosition(0, 0);
		levelNo.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
		upperBar.addActor(levelNo);
		
		//getBackground().invalidate();
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#checkGame(float)
	 */
	@Override
	public void checkGame(float delta) {
		// TODO Auto-generated method stub
		 Integer newScore = Integer.parseInt(score.getText().toString());
		
		 if(newScore >= SettingsManager.goal){
			 gameSuccess();
		 } else if(gameState != GameState.Pause){
				gameWorld.update(delta);
				touch.update(delta);
				//meter.render(spriteBatch, delta);
				/*
				 *  for tweening of objects 
				 *  if tween size is greater than no objects then we should update
				 *  the tween manager
				 */
				if(game.tween.size() > 0){
					game.tween.update(delta);
				}
			}
		
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#setMission()
	 */
	@Override
	public void setMission(GameState gameState) {
		// TODO Auto-generated method stub
		if(gameState == GameState.Ready){
			
			toggleWindow(WindowAnimation.unshow_overlay_show_lowerbar).start(game.tween);
			SettingsManager.goal = level.getMaxFruits();
			clearScores();
			bombs.reset();
			gameWorld.reset();
		}
		else if(gameState == GameState.NextLevel){
			game.setScreen(game.screen.getNextScreen(State.LEVEL));
		}
		
		if(mission == null){
			mission = new MissionBox(gameState,"" , "Level " + map.getChoosenLevel(), SettingsManager.goal, Assets.basket , Assets.dialog_patch , this );	
		}
		mission.setText(SettingsManager.getSayings(gameState , "data/adventure.txt"));
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#setGoal(com.teampora.game.FruitModel.FruitType)
	 */
	@Override
	public void setGoal(FruitType type) {
		// TODO Auto-generated method stub
		super.setGoal(type);
		/*
		 *  we dont do anything fancy here! so ignored!
		 */
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#clearScores()
	 */
	@Override
	public void clearScores() {
		// TODO Auto-generated method stub
		if(score != null){
			score.setText("0");
		}
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#success()
	 */
	@Override
	public void success() {
		// TODO Auto-generated method stub
		Integer newScore = Integer.parseInt(score.getText().toString());
	    starBox = new StarBox(Assets.dialog_patch, Assets.star_filled, Assets.star_unfilled,
			                 newScore, map.getChoosenLevel(), SettingsManager.goal, this);
	    starBox.setCurrentScore(0);
	    overlay.add(starBox);
	    int coins = SettingsManager.goal / 2;
	    //MusicEffectManager.getIns().createNewEffect(MusicEffectType.COUNT, 5f, true, true);
		Timeline.createSequence()
		.push(toggleWindow(WindowAnimation.show_overlay))
		 // dummy
		.beginParallel()
			.push(Tween.to(starBox, StarBoxAccessor.PLAY_COINS, 1f).target(starBox.getAchievedScore()))
			.push(Tween.to(starBox, StarBoxAccessor.SCORE, 1f).target(starBox.getAchievedScore()))
		.end()
		.setCallback(new TweenCallback(){
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				// TODO Auto-generated method stub
				
				MusicEffectManager.getIns().stop();
			}
		})
		.setUserData(coins)
		.setCallback(coinAddCallback)
		.start(game.tween);
		
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#gameOver()
	 */
	@Override
	public void gameOver(float delta) {
		// TODO Auto-generated method stub
		gameWorld.update(delta);
		gameState = GameState.GameOver;
		int stars = 0;
		Integer newScore = Integer.parseInt(score.getText().toString());
		int goal = SettingsManager.goal / 3;
		if(newScore  >= goal ){
			//1 star
			stars = 1;
			goalAchieved = true;
			
		}
		if(newScore >= (goal * 2)){
			//2 stars
			stars = 2;
			goalAchieved = true;
		}
		if(newScore >= (goal * 3)){
			//3 stars max!
			stars = 3;
			goalAchieved = true;
		}
		
		if(goalAchieved){
			gameState = GameState.NextLevel;
			gameSuccess();
			level.setStarsAccumulated(stars);
			/*
			 * set the next level to unlock
			 */
			if(map.getLevelReached() < map.getLevels().size()){
				map.getLevels().get(map.getChoosenLevel()).setLocked(false);
				Gdx.app.log("Levels", map.getLevelReached()  + " " + map.getLevels().size() + " Choosen level =>" + map.getChoosenLevel());
			}
				map.setChoosen(true);
	
			
		}else{
			reset();
		}
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#resetScreen()
	 */
	@Override
	public void retry() {
		// TODO Auto-generated method stub
		
		setMission(GameState.GameOver);
		mission.setState(GameState.GameOver);
		start.setTag("Retry");
		start.setDrawableUp(Assets.leftArrow_icon);
		start.setRightArrow(true);
	
		start_window.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#continueNextLevel()
	 */
	@Override
	public void continueNext() {
		// TODO Auto-generated method stub
		/*
		 *  continue because weve had success
		 */
		overlay.removeActor(starBox);
		SettingsManager.goal  = generateRandomGoal();
		setMission(GameState.NextLevel);
		/*
		mission.setState(GameState.NextLevel);
		overlay.add(start_window).center();
		start_window.setVisible(true);
		toggleWindow(WindowAnimation.unshow_overlay_lowerbar_show_it);
		start.setTag("Start");
		start.setDrawableUp(Assets.play_icon);
		start.setRightArrow(false);*/
	}

	@Override
	public void pauseNext() {
		// TODO Auto-generated method stub
		//int ran = random.nextInt(SettingsManager.getLineCount("data/help.txt", FileType.Internal)) + 1;
		//mission.hideIconAndText();
		mission.setLabelTitleText("Level " + map.getChoosenLevel() + " of " + map.getLevels().size());
		mission.setText("PAUSED!");
		//mission.setText(SettingsManager.getFileByLineNoFixed("data/help.txt", FileType.Internal, ran).toString() );
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		//mission.makeVisibleIconAndText();
		super.resume();
	}
	
	
	
	
}
