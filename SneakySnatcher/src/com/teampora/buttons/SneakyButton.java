package com.teampora.buttons;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.tables.TableOverlay;

public class SneakyButton extends TextButton {

	public enum SneakType{
		POS_X,
		POS_Y,
		POS_XY
	}
	
	public SneakyButton(String text, Skin skin, String styleName , TableOverlay parentOverlay
			, SneakySnatcher game , float x_pos_to , float y_pos_to, SneakType type) {
		super(text, skin, styleName);
		getX();
		getY();
	}

	@Override
	public void toggle() {
		// TODO Auto-generated method stub
		
		
		super.toggle();
	}
	
	

}
