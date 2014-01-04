package com.teampora.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.buttons.SneakyImageButton;
import com.teampora.game.State;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.tools.Scores;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class ScoreScreen extends AbstractScreen{

	private List<Scores> scores =null;
	
	private Table background;
	
	private Table additional_bg;
	
	private Table myTable;
	
	private SneakyImageButton backButton;
	
	public ScoreScreen(SneakySnatcher game, Stage stage, State state) {
		super(game, stage, state);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderSub(float delta) {
		// TODO Auto-generated method stub
		stage.act(delta);
		stage.draw();
		//Table.drawDebug(stage);
		
	}


	@Override
	public void showSub() {
		// TODO Auto-generated method stub
		
		/*
		 *  back button
		 */
		backButton = new SneakyImageButton("Back", Assets.skin, Assets.leftArrow_icon);
		backButton.setPosition(0, 0);
		backButton.setSize(Utils.WIDTH_EXTRA_SMALL(), Utils.HEIGHT_NORMAL());
		backButton.addListener(new ClickListener(){

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.screen.getNextScreen(State.MENU));
				super.clicked(event, x, y);
			}
			
		});
		
		background = new Table();
		background.setBackground(new TextureRegionDrawable(Assets.background));
		background.setFillParent(true);
		
	    additional_bg = new Table();
	    additional_bg.setBackground(new TextureRegionDrawable(Assets.additional_background));
	    additional_bg.setFillParent(true);
		
		myTable = new Table(Assets.skin);
		myTable.setSize(stage.getWidth(), stage.getHeight());
		myTable.setPosition(0, 0);
		scores = game.screen.profile.getHighScores();

		myTable.add("Top Scores", "xlarge_font").colspan(2);
		myTable.row();
		for(int i = 0 ; i < scores.size() ; i ++){
			Label name = new Label(scores.get(i).getName() , Assets.skin, "lfont",Color.YELLOW);
			myTable.add(name).uniform();
			Label score = new Label(scores.get(i).getScore()+"",Assets.skin, "lfont",Color.YELLOW);
			myTable.add(score).uniform();
			myTable.row();
			
			Gdx.app.log(scores.get(i).getName(), scores.get(i).getScore()+"");
		}
		myTable.debug();
		stage.addActor(background);
		stage.addActor(additional_bg);
		stage.addActor(myTable);
		stage.addActor(backButton);
	}

	@Override
	public void disposeSub() {
		// TODO Auto-generated method stub
		myTable.clear();
		background.clear();
	}

	@Override
	public void hideSub() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.teampora.screens.AbstractScreen#BackKeyPressed()
	 */
	@Override
	public void BackKeyPressed() {
		// TODO Auto-generated method stub
		game.setScreen(game.screen.getNextScreen(State.MENU));
		Gdx.app.log("SCORE SCREEN", "Back key Pressed" );
	}
	
	

}
