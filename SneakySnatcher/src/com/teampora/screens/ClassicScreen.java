package com.teampora.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Quint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.accessor.LabelAccessor;
import com.teampora.accessor.TableAccessor;
import com.teampora.game.FruitModel.FruitType;
import com.teampora.game.GameState;
import com.teampora.game.State;
import com.teampora.groups.FruitMeter;
import com.teampora.groups.FruitMeter.FruitMeterSize;
import com.teampora.groups.FruitMeter.FruitMeterType;
import com.teampora.groups.MissionBox;
import com.teampora.interfaces.Playable.WindowAnimation;
import com.teampora.labels.SneakyLabel;
import com.teampora.managers.MusicEffectManager;
import com.teampora.managers.SettingsManager;
import com.teampora.managers.SoundManager;
import com.teampora.managers.MusicEffectManager.MusicEffectType;
import com.teampora.managers.SettingsManager.FileType;
import com.teampora.managers.SoundManager.SoundType;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class ClassicScreen extends GameScreen{
	
	private FruitMeter golden_fruit;

	
	public ClassicScreen(SneakySnatcher game, Stage stage, State state) {
		super(game, stage, state);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#showSub()
	 */
	@Override
	public void showSub() {
		// TODO Auto-generated method stub
		/*
		 *  background table instance for the bg image
		 */
		background = new Table();
		background.setFillParent(true);
		background.setBackground(new TextureRegionDrawable(Assets.background));

		super.showSub();
		
	}




	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#checkGame(float)
	 */
	@Override
	public void checkGame(float delta) {
		// TODO Auto-generated method stub
		Integer golds = golden_fruit.getActiveFruits();
		if(golds >= SettingsManager.goal){
			gameSuccess();
			
		}else if(gameState != GameState.Pause){
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
		if(gameState==GameState.Ready){
			SettingsManager.goal =  generateRandomGoal();
			toggleWindow(WindowAnimation.unshow_overlay_show_lowerbar).start(game.tween);
			
			clearScores();
			bombs.reset();
			gameWorld.reset();
		}
		
		if(mission == null){
			mission = new MissionBox(gameState,"" , "", SettingsManager.goal, Assets.golden_apple , Assets.msg_box , this );	
		}
		
		if(golden_fruit == null){
			golden_fruit = new FruitMeter(Assets.border , Assets.golden_active , Assets.golden_inactive  , SettingsManager.goal , FruitMeterType.HORIZONTAL , FruitMeterSize.SMALL);
		}
		golden_fruit.setPosition(0, 0);
		golden_fruit.setShowBg(false);
		golden_fruit.setMax_number(SettingsManager.goal);
		upperBar.addActor(golden_fruit);
		
		
		Integer golds = golden_fruit.getActiveFruits();
		//StringBuffer buffer = new StringBuffer();
		//buffer.append("X ").append(golds.toString()).append(" / ").append(SettingsManager.goal);			
		golden_fruit.setActiveFruits(golds);
		
		mission.setText(SettingsManager.getSayings(gameState , "data/classic.txt"));
		
	}
	
	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#clearScores()
	 */
	@Override
	public void clearScores() {
		// TODO Auto-generated method stub
		if(score!=null){
			score.setText("0");
		}
		if(golden_fruit != null){
			golden_fruit.setActiveFruits(0);
		}
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#setGoal(com.teampora.game.FruitModel.FruitType)
	 */
	@Override
	public void setGoal(FruitType type) {
		// TODO Auto-generated method stub
		if(type == FruitType.GOLDEN_APPLE){
			Integer golds = golden_fruit.getActiveFruits();
			golds++;

			//StringBuffer buffer = new StringBuffer();
			//buffer.append("X ").append(golds.toString()).append(" / ").append(SettingsManager.goal);			
			golden_fruit.setActiveFruits(golds);
			

		}
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#success()
	 */
	@Override
	public void success() {
		// TODO Auto-generated method stub
		
		Integer golds = golden_fruit.getActiveFruits();
		goalAchieved = true;
		
		score_window.getGoldenText().setText("x 0");
		score_window.getFruitsText().setText("x 0");
		score_window.getCoinText().setText("x 0");
		if(comboScore > 0)
			local_score+=comboScore;
		Integer coins = (int) (local_score / 2 + golds);
		overlay.add(score_window);
		MusicEffectManager.getIns().createNewEffect(MusicEffectType.COUNT, 5f, true, true);
		Timeline.createSequence()
		.push(toggleWindow(WindowAnimation.show_overlay))
		.beginParallel()
			//.push(Tween.to(new MusicEffectManager(), MusicEffectAccessor.PLAY, 2.0f).target())
			//.push(Tween.to(overlay, TableAccessor.POS_Y, 2.0f).target( Gdx.graphics.getHeight() * 0.05f).ease(Quint.OUT))
			.push(Tween.to(score_window.getGoldenText(), LabelAccessor.TEXT, 2.0f).target(golds).ease(Cubic.INOUT))
			.push(Tween.to(score_window.getFruitsText(), LabelAccessor.TEXT, 2.0f).target(local_score).ease(Cubic.INOUT))
		.end()
		.beginParallel()
			.push(Tween.to(score_window.getGoldenText(), LabelAccessor.TEXT, 2.0f).target(0).ease(Cubic.INOUT))
			.push(Tween.to(score_window.getFruitsText(), LabelAccessor.TEXT, 2.0f).target(0).ease(Cubic.INOUT))
			.push(Tween.to(score_window.getCoinText(), LabelAccessor.TEXT, 2.0f).target(coins).ease(Cubic.INOUT))
		.end()
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
		if(gameState == GameState.GameOver){
			reset();
		}
		//show("Mission Failed!" , Color.RED , 0f , 1f , 2f , 1.5f , ShowType.TOP);
		SoundManager.play(SoundType.FAILED);
		
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.GameScreen#resetScreen()
	 */
	@Override
	public void retry() {
		// TODO Auto-generated method stub
		setMission(gameState);
		mission.setState(gameState);
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
		overlay.removeActor(score_window);
		setMission(GameState.Ready);
		mission.setState(GameState.Ready);
		
		start_window.setVisible(true);
		toggleWindow(WindowAnimation.unshow_overlay_show_lowerbar).start(game.tween);
		overlay.add(start_window).center();
		start.setTag("Start");
		start.setDrawableUp(Assets.play_icon);
		start.setRightArrow(false);

	}

	@Override
	public void pauseNext() {
		// TODO Auto-generated method stub
		int ran = random.nextInt(SettingsManager.getLineCount("data/help.txt", FileType.Internal)) + 1;
		mission.setLabelTitleText("Game Hints # " + ran );
		mission.setText(SettingsManager.getFileByLineNoFixed("data/help.txt", FileType.Internal, ran).toString() );
	
		
	}
	
	
}
