package com.teampora.groups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.managers.SettingsManager;
import com.teampora.screens.AbstractScreen;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class ImageNumberBox extends GameBox{

	private Label coins_score ;
	private final Image icon ;	
	private final float MAX_WIDTH = 136;

	private float newWidth = 1.3f;
	
	private AbstractScreen holder;
	
	public ImageNumberBox(long coin , NinePatch image , TextureRegion background, AbstractScreen holder){
		super(new TextureRegionDrawable(background) );
		this.holder = holder;
		builder.setLength(0);
		builder.append(coin);
		builder = SettingsManager.StringToNumber(builder);
		this.coins_score = new Label(builder.toString() , Assets.skin , "lfont" , Color.BLACK);
		this.icon = new Image(new NinePatchDrawable(image));
		
		
		initUI();
	}
	
	public ImageNumberBox(long score , TextureRegion image , TextureRegion background
			, String fontStyle, Color color){
		super(new TextureRegionDrawable(background));
		builder.setLength(0);
		builder.append(score);
		this.coins_score = new Label(builder.toString() , Assets.skin , fontStyle , color);
		this.icon = new Image(new TextureRegionDrawable(image));

		
		initUI();
	}
	
	private void initUI(){
		
		
		background.setBounds(getX(), getY(),coins_score.getTextBounds().width +  (Utils.WIDTH_SMALL() * 2), Utils.HEIGHT_NORMAL());
		//addActor(background);
		
		icon.setBounds(getX() + (Utils.WIDTH_SMALL() / 3f), getY() + (background.getHeight() / 2) - (Utils.HEIGHT_SMALL() / 2) , Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
		addActor(icon);
		
		coins_score.setPosition(icon.getX() + icon.getWidth() , icon.getY() + 2f );
		addActor(coins_score);
		
		setBounds(getX(), getY() , background.getWidth() , background.getHeight());
	}
	

	private StringBuilder builder = new StringBuilder();
	public void setCoin(long coin){
		builder.setLength(0);
		builder.append(coin);
		builder = SettingsManager.StringToNumber(builder);
		coins_score.setText(builder.toString());
		background.setBounds(getX(), getY(),coins_score.getTextBounds().width +  (Utils.WIDTH_SMALL() * 2), Utils.HEIGHT_NORMAL());
	}
	
	public void setScore(long score){
		setCoin(score);	
	}
	
	
}
