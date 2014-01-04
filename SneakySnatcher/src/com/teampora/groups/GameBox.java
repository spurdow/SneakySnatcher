package com.teampora.groups;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class GameBox extends Group{

	protected final Image background;
	
	public GameBox(Drawable background){
		this.background = new Image(background);
	}
}
