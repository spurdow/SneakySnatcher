package com.teampora.groups;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.teampora.adventure.MapSelection;
import com.teampora.managers.SettingsManager;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class MapBox extends GameBox{
	
	private final Label title;
	private final Label levels;
	private final Image mapImage;
	private final Image borderImage;
	
	public MapBox(MapSelection map ,  NinePatchDrawable image){
		super(image);
		title = new Label(map.getMapName() , Assets.skin , "xlfont", map.getColor() );
		levels = new Label((map.getLevelReached() - 1 ) + " / " + map.getMaxLevels() , Assets.skin , "lfont", map.getColor());
		mapImage = new Image(SettingsManager.getRegion(map.getFileName()));
		borderImage = new Image(Assets.border);
		init();
	}
	
	private void init(){

		
		background.setBounds(getX(), getY(), Utils.WIDTH_LARGE() , Utils.HEIGHT_LARGE());
		addActor(background);
		
		title.setPosition(getX() + ( background.getWidth() / 2 - title.getTextBounds().width /2  ) , getY() + background.getHeight() + title.getTextBounds().height);
		addActor(title);
		

		mapImage.setBounds(getX() + (background.getWidth() / 2 - (Utils.WIDTH_LARGE() * 0.8f) / 2), getY() + (background.getHeight() / 2 - (Utils.HEIGHT_LARGE() * 0.8f) / 2), Utils.WIDTH_LARGE() * 0.8f, Utils.HEIGHT_LARGE() * 0.8f);
		addActor(mapImage);
		
		borderImage.setBounds(getX() + (background.getWidth() / 2 - (Utils.WIDTH_LARGE() * 0.81f) / 2), getY() + (background.getHeight() / 2 - (Utils.HEIGHT_LARGE() * 0.85f) / 2), Utils.WIDTH_LARGE() * 0.81f, Utils.HEIGHT_LARGE() * 0.85f);
		addActor(borderImage);
		
		
		levels.setPosition(getX() + ( background.getWidth() / 2 - levels.getTextBounds().width /2  ) , getY() - levels.getTextBounds().height * 1.5f);
		addActor(levels);
		
		setBounds(getX() , getY() , background.getWidth() , background.getHeight());
	}
	
}
