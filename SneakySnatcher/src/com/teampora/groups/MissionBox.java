package com.teampora.groups;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.game.GameState;
import com.teampora.labels.SneakyLabel;
import com.teampora.screens.AbstractScreen;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class MissionBox extends GameBox {

	private final Label text;
	private final Image icon;
	private final Label iconText;
	private final AbstractScreen holder;
	private String title;
	private Label labelTitle;
	private GameState state;
	private Image bgImage;
	
	public MissionBox(GameState state,String str , String title , int number  , TextureRegion golden_apple , NinePatch backgroundTexture , AbstractScreen holderScreen  ){
		super(new NinePatchDrawable(backgroundTexture));
		holder = holderScreen;
		this.state = state;
		float width = holder.getStage().getWidth() * 1f;
		float height = holder.getStage().getHeight() * 1f;
		
		labelTitle = new SneakyLabel("xlfont", Color.YELLOW);
			if(state == GameState.GameSuccess){
				this.title  = "Mission Success!";
			}else if(state == GameState.GameOver){
				this.title = "Mission Failed!";
			}else if(state == GameState.Ready){
				this.title = "Your Mission!";
			}
		
		labelTitle.setText(this.title);
		labelTitle.setPosition(getX() + (width / 2 - labelTitle.getTextBounds().width /2)  , getY() + height - (Utils.HEIGHT_NORMAL() * 2f));
		
		
		background.setBounds(getX(), getY(), width, height);
		//addActor(background);
		
		/*
		 *  it isnt going to be covered by the bg
		 */
		addActor(labelTitle);
		
		text = new Label(str , Assets.skin , "lfont", Color.BLACK);
		text.setBounds(getX(), getY() + 10f , width , height );
		text.setAlignment(Align.center);
		addActor(text);
		
		bgImage = new Image(new NinePatchDrawable(Assets.border));
		bgImage.setPosition(getX() + Utils.WIDTH_SMALL() * 2.8f, getY() + Utils.HEIGHT_SMALL() * 2);
		//addActor(bgImage);
		
		icon = new Image(new TextureRegionDrawable(golden_apple));
		icon.setBounds(getX() + labelTitle.getX(), getY() + Utils.HEIGHT_SMALL() * 3 , Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
		addActor(icon);
		
		iconText = new Label("" , Assets.skin , "lfont" , Color.YELLOW);
		setIconText(number);
		iconText.setPosition(getX() + icon.getX() + icon.getWidth(),getY() +  icon.getY() + 10f );
		addActor(iconText);
		
		setBounds(getX() , getY() , background.getWidth() , background.getHeight());
		
	}
	private final StringBuilder builder = new StringBuilder();
	
	public void setText(String newText){
		builder.setLength(0);
		builder.append(newText);
		text.setText(builder);
	}
	
	public void setIconText(int number){
		builder.setLength(0);
		builder.append(" x ");
		builder.append(number);
		iconText.setText(builder);
		bgImage.setPosition(icon.getX(), icon.getY() - iconText.getTextBounds().height);
		bgImage.setSize(icon.getWidth() + iconText.getTextBounds().width, icon.getHeight() + iconText.getTextBounds().height );
	}
	
	public void setIconTextWithLeft(int number){
		builder.setLength(0);
		builder.append(" x ");
		builder.append(number);
		builder.append(" left!");
		iconText.setText(builder);
	}
	
	public void hideIconAndText(){
		//bgImage.remove();
		icon.remove();
		iconText.remove();
	}
	
	public void makeVisibleIconAndText(){
		//addActor(bgImage);
		addActor(icon);
		addActor(iconText);
	}

	/**
	 * @return the state
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(GameState state) {
		this.state = state;
		if(state == GameState.GameSuccess){
			this.title = "Mission Success!";
			
		}else if(state == GameState.GameOver){
			this.title = "Mission Failed!";
		}
		else if(state == GameState.Ready){
			this.title = "Your Mission!";
		}
		labelTitle.setText(title);
	}

	/**
	 * @param labelTitle the labelTitle to set
	 */
	public void setLabelTitleText(String text) {
		this.labelTitle.setText(text);
		labelTitle.setX(getX() + (holder.getStage().getWidth() / 2 -  labelTitle.getTextBounds().width / 2) );
	}
	
	
	

	
	
	
}
