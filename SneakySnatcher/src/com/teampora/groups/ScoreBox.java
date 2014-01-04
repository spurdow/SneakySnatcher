package com.teampora.groups;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.labels.SneakyLabel;
import com.teampora.screens.AbstractScreen;
import com.teampora.utils.Utils;

public class ScoreBox extends GameBox{

	private final Image basketIcon;
	private final Label fruitsText;
	private final Image goldenIcon;
	private final Label goldenText;
	private final Image coinIcon;
	private final Label coinText;
	private final AbstractScreen holder;
	private final String title;
	
	public ScoreBox(String title , NinePatch rBackground , 
					TextureRegion basket_icon , String fruits , 
					TextureRegion golden_icon , String golden,
					NinePatch coin_icon   , String coins,
			        AbstractScreen holder){
		super(new NinePatchDrawable(rBackground));
		this.title = title;
		this.basketIcon = new Image(new TextureRegionDrawable(basket_icon));
		this.fruitsText = new SneakyLabel("xlfont" , Color.YELLOW);
		this.goldenIcon = new Image(new TextureRegionDrawable(golden_icon));
		this.goldenText = new SneakyLabel("xlfont" , Color.YELLOW);
		this.coinIcon   = new Image(new NinePatchDrawable(coin_icon));
		this.coinText = new SneakyLabel("xlfont" , Color.YELLOW);
		this.holder = holder;
		initUI();
	}
	/*
	 *  sets the images and labels position and size
	 *  then put its into its parent the group parent
	 */
	public void initUI(){
		float width = holder.getStage().getWidth() * 1f;
		float height = holder.getStage().getHeight() * 1f;
		float padding = Utils.WIDTH_EXTRA_SMALL() / 3f;
		
		background.setBounds(getX(), getY(), width, height);
		//addActor(background);
		
		final Label label = new SneakyLabel("xlfont", Color.ORANGE );
		label.setText(title);
		label.setPosition(getX() + (width / 2 - label.getTextBounds().width /2)  , getY() + (height - Utils.HEIGHT_NORMAL() * 2f));
		addActor(label);
		
		basketIcon.setBounds(getX() + (width / 2 - Utils.WIDTH_EXTRA_SMALL()) , getY() + (height - Utils.HEIGHT_LARGE() ) , Utils.WIDTH_EXTRA_SMALL(), Utils.HEIGHT_EXTRA_SMALL());
		addActor(basketIcon);
		

		
		fruitsText.setPosition(basketIcon.getX() + basketIcon.getWidth() + padding, basketIcon.getY());
		addActor(fruitsText);
		
		goldenIcon.setBounds(basketIcon.getX() , basketIcon.getY() - (Utils.HEIGHT_EXTRA_SMALL()), Utils.WIDTH_EXTRA_SMALL(), Utils.HEIGHT_EXTRA_SMALL());
		addActor(goldenIcon);
		
		goldenText.setPosition(basketIcon.getX() + goldenIcon.getWidth() + padding , goldenIcon.getY()  );
		addActor(goldenText);
		
		coinIcon.setBounds(basketIcon.getX(), goldenIcon.getY() - (Utils.HEIGHT_EXTRA_SMALL()), Utils.WIDTH_EXTRA_SMALL(), Utils.HEIGHT_EXTRA_SMALL());
		addActor(coinIcon);
		
		coinText.setPosition(basketIcon.getX() + coinIcon.getWidth() + padding, coinIcon.getY());
		addActor(coinText);
		
		setBounds(getX() , getY() , background.getWidth() , background.getHeight());
		
	}
	/**
	 * @return the background
	 */
	public Image getBackground() {
		return background;
	}
	/**
	 * @return the basketIcon
	 */
	public Image getBasketIcon() {
		return basketIcon;
	}
	/**
	 * @return the fruitsText
	 */
	public Label getFruitsText() {
		return fruitsText;
	}
	/**
	 * @return the goldenIcon
	 */
	public Image getGoldenIcon() {
		return goldenIcon;
	}
	/**
	 * @return the goldenText
	 */
	public Label getGoldenText() {
		return goldenText;
	}
	/**
	 * @return the coinIcon
	 */
	public Image getCoinIcon() {
		return coinIcon;
	}
	/**
	 * @return the coinText
	 */
	public Label getCoinText() {
		return coinText;
	}
	
	
}
