package com.teampora.accessor;

import com.badlogic.gdx.math.Vector2;
import com.teampora.utils.AnimatedImage;

import aurelienribon.tweenengine.TweenAccessor;

public class AnimatedImageAccessor implements TweenAccessor<AnimatedImage> {

	public final static int POS_X = 0x00;
	
	public final static int POS_Y = 0x01;
	
	public final static int POS_XY = 0x02;
	
	
	
	@Override
	public int getValues(AnimatedImage target, int type, float[] values) {
		// TODO Auto-generated method stub
		switch(type){
		case POS_X : values[0] = target.getPosition().x ; return 1;
		case POS_Y : values[0] = target.getPosition().y ; return 1;
		case POS_XY : values[0] = target.getPosition().x ;
					  values[1] = target.getPosition().y; return 2;
		default: assert false; return -1;
		}
	}

	@Override
	public void setValues(AnimatedImage target, int type, float[] values) {
		// TODO Auto-generated method stub
		Vector2 pos = target.getPosition();
		switch(type){
		case POS_X : pos.x = values[0]; target.setPosition(pos) ; break;
		case POS_Y : pos.y = values[0]; target.setPosition(pos); break;
		case POS_XY: pos.x = values[0]; pos.y = values[1]; target.setPosition(pos); break;
		default : assert false; break;
		}
	}

}
