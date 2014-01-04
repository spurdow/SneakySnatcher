package com.teampora.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.buttons.SneakyImageButton;
import com.teampora.game.GameTouch;
import com.teampora.game.State;
import com.teampora.groups.ImageNumberBox;
import com.teampora.groups.ToolBox;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.tools.Gloves;
import com.teampora.tools.Hammer;
import com.teampora.tools.Tools.ToolType;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class ToolsScreen extends AbstractScreen{

	
	private ScrollPane scroll;
	
	private List<Hammer> hammers = null;
	
	private List<Gloves> gloves = null;
	
	private Table background;
	
	private Table additional_bg;
	
	private Table myTable;
	
	private Table hintBox;
	
	private ImageNumberBox coinBox;
	
	private GameTouch touch;
	
	private InputMultiplexer input;
	
	private SneakyImageButton backButton;
	
	
	
	
	public ToolsScreen(SneakySnatcher game, Stage stage, State state) {
		super(game, stage, state);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderSub(float delta) {
		// TODO Auto-generated method stub
//		myTable.act(delta);
		stage.act(delta);
		stage.draw();
		
		if(touch != null){
			touch.update(delta);
		}
		
		if(game.tween.size() > 0){
			game.tween.update(delta);
		}
		//Table.drawDebug(stage);
		
	}
	@Override
	public void showSub() {
		// TODO Auto-generated method stub
		
		
		touch = new GameTouch();
		
		input = new InputMultiplexer(stage, touch.swipe);
		
		/*
		 *  setting the input to the system
		 */
		Gdx.input.setInputProcessor(input);
		
		
		/*
		 *  new instance for back button 
		 *  no further explanation
		 */
		
		backButton = new SneakyImageButton("Back",Assets.skin , Assets.leftArrow_icon);
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
		
		coinBox = new ImageNumberBox(game.screen.profile.getCoins() , Assets.fruit_coin, Assets.background_dialog ,this);
		coinBox.setPosition(0, stage.getHeight() - coinBox.getHeight());
		

		background = new Table();
		background.setBackground(new TextureRegionDrawable(Assets.background));
	    background.setFillParent(true);
	    
	    additional_bg = new Table();
	    additional_bg.setBackground(new TextureRegionDrawable(Assets.additional_background));
	    additional_bg.setFillParent(true);
		 
		myTable = new Table();

		hintBox = new Table();
		hintBox.setFillParent(true);
		hintBox.setSkin(Assets.skin);
		hintBox.left().top().add("Test it here!", "mfont", Color.WHITE).pad(90);
		
		hammers = game.screen.profile.getHammers();
		for(int i = 0 ; i < hammers.size() ; i ++){
			ToolBox toolBox = new ToolBox(hammers.get(i), game.screen.profile , this);
			myTable.add(toolBox);
			myTable.row();
		}
		gloves = game.screen.profile.getGloves();
		for(int i = 0 ; i < gloves.size() ; i ++){
			ToolBox toolBox = new ToolBox(gloves.get(i), game.screen.profile , this);
			myTable.add(toolBox);
			myTable.row();
		}
		
	
		

		scroll = new ScrollPane(myTable, Assets.skin);
		scroll.setScrollingDisabled(true , false);
		scroll.setWidth((stage.getWidth() * 0.7f));
		scroll.setHeight(stage.getHeight());
		scroll.setPosition(stage.getWidth() - scroll.getWidth(), 0);
		stage.addActor(background);
		stage.addActor(additional_bg);
		stage.addActor(scroll);
		stage.addActor(coinBox);
		stage.addActor(hintBox);
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
		
	}
	
	public void revertTools(ToolType type , String text ){
		Gdx.app.log("REVERTING", type.toString());
			for(int i = 0 ; i < myTable.getChildren().size; i ++){
				if(myTable.getChildren().get(i) instanceof ToolBox){
					ToolBox box = (ToolBox) myTable.getChildren().get(i);
					if(box.getControlButton().getText().toString().equals("Used")){
						if(type == box.type ){
							box.getControlButton().setText(text);
						}else if(type == box.type){
							box.getControlButton().setText(text);
						}
					}
				}
			}
	}
	
	public void setCoinBox(long newCoins){
		coinBox.setCoin(newCoins);
	}

	/**
	 * @return the touch
	 */
	public GameTouch getTouch() {
		return touch;
	}
	
	

}
