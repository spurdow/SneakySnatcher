package com.teampora.screens;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.accessor.ScrollAccessor;
import com.teampora.buttons.SneakyImageButton;
import com.teampora.credits.Credits;
import com.teampora.game.State;
import com.teampora.groups.CreditBox;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class CreditsScreen extends AbstractScreen {

	private ScrollPane scroll;
	
	private List<Credits> credits =null;
	
	private Table background;
	
	private Table additional_bg;
	
	private Table myTable;
	
	private SneakyImageButton backButton;

	
	public CreditsScreen(SneakySnatcher game, Stage stage, State state) {
		super(game, stage, state);
		// TODO Auto-generated constructor stub
		Tween.registerAccessor(ScrollPane.class, new ScrollAccessor());
	}

	@Override
	public void renderSub(float delta) {
		// TODO Auto-generated method stub
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void showSub() {
		// TODO Auto-generated method stub
		
		/*
		 *  back Button
		 */
		
		backButton = new SneakyImageButton("Back",Assets.skin, Assets.leftArrow_icon);
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
	    
		credits = (ArrayList<Credits>) game.screen.profile.getCredits();
		myTable = new Table();
		
		for(int i =0 ;i<game.screen.profile.getCredits().size();i ++){
			CreditBox creditBox = new CreditBox(credits.get(i) , game.screen.profile);
			myTable.add(creditBox);
			myTable.row();
		}
		
		scroll = new ScrollPane(myTable, Assets.skin);
		scroll.setScrollingDisabled(true ,false);
		scroll.setWidth((stage.getWidth() * 0.7f));
		scroll.setHeight(stage.getHeight());
		scroll.setPosition(stage.getWidth() - scroll.getWidth(), 0);
		stage.addActor(background);
		stage.addActor(additional_bg);
		stage.addActor(scroll);
		stage.addActor(backButton);
		
		Timeline.createSequence()
			.push(Tween.to(scroll, ScrollAccessor.SCROLL_Y_PERCENT, 10f).target(1f))
			.push(Tween.to(scroll, ScrollAccessor.ALPHA, 3f).target(0))
			.push(Tween.set(scroll, ScrollAccessor.SCROLL_Y_PERCENT).target(0))
			.push(Tween.to(scroll, ScrollAccessor.ALPHA, 3f).target(1))
		.start(game.tween);
	}

	@Override
	public void disposeSub() {
		// TODO Auto-generated method stub
		background.clear();
		myTable.clear();
		scroll.clear();
		
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
	}
	
	

}
