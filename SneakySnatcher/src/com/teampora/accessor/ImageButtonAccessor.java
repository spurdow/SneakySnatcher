package com.teampora.accessor;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.teampora.buttons.SneakyTextButton;

import aurelienribon.tweenengine.TweenAccessor;

public class ImageButtonAccessor implements TweenAccessor<ImageButton> {


	public final static int POS_XY = 0x00;
	
	@Override
	public int getValues(ImageButton target, int type, float[] values) {
		// TODO Auto-generated method stub
		switch(type){
		case POS_XY: values[0] = target.getX() ; 
		             values[1] = target.getY() ; 
		             return 2;
		default: assert false; return -1;
		}
	}

	@Override
	public void setValues(ImageButton target, int type, float[] values) {
		// TODO Auto-generated method stub
		switch(type){
		case POS_XY : target.setX(values[0]);
		              target.setY(values[1]); break;
		default: assert false; break;
		}
	}
}
