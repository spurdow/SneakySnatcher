package com.teampora.groups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.teampora.labels.SneakyLabel;
import com.teampora.managers.SettingsManager;
import com.teampora.managers.SettingsManager.FileType;
import com.teampora.screens.AbstractScreen;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class HelpBox extends GameBox{

	
	private int index ;
	
	private Table table;
	
	private AbstractScreen holder;
	
	public HelpBox(Drawable background , int index , AbstractScreen holder) {
		super(background);
		// TODO Auto-generated constructor stub
		this.index = index;
		table = new Table();
		this.holder = holder;
		initUI();
	}
	
	private void initUI(){
		background.setBounds(getX(), getY(), holder.getStage().getWidth(), holder.getStage().getHeight());
		background.setColor(background.getColor().r,background.getColor().g, background.getColor().b, 0.2f);
		addActor(background);
		
		
		Image gloves = new Image(Assets.help_gloves);
		gloves.setSize(Utils.WIDTH_NORMAL(), Utils.HEIGHT_NORMAL());
		Table text = new Table().pad(20, 0, 0, 0);
		text.setSkin(Assets.skin);
		table.row();
		index++;
		if(index == 1){
			Image right = new Image(Assets.leftArrow_icon);
			right.setOrigin(right.getWidth()/2, right.getHeight()/2);
			right.setRotation(180);
			text.top();
			//text.add(SettingsManager.getFileByLineNoFixed("data/help.txt", FileType.Internal, index).toString() , "ss-label-help");
			table.center();
			table.row();
			table.add(new Image(Assets.leftArrow_icon)).pad(10);
			table.add(gloves).pad(10);
			table.add(right).pad(10);
			
		}else if(index == 2){
			Image right = new Image(Assets.leftArrow_icon);
			right.setOrigin(right.getWidth()/2, right.getHeight()/2);
			right.setRotation(180);
			text.top();
			//text.add(SettingsManager.getFileByLineNoFixed("data/help.txt", FileType.Internal, index).toString() , "ss-label-help");
			table.center();
			table.row();
			table.add(new Image(Assets.fruit_coin)).pad(10);
			table.add(gloves).pad(10);
			
		}else if(index == 3){
			Image bomb = new Image(Assets.bomb_active);
			table.center();
			table.row();
			table.add(bomb).center();
		}
		table.setBounds(getX(), getY(), background.getWidth(), background.getHeight());
		addActor(table);
		text.setBounds(getX(), getY(), background.getWidth(), background.getHeight());
		addActor(text);
		
		setBounds(getX() , getY() , background.getWidth() , background.getHeight());
	}

	
	
	
	
	
	

}
