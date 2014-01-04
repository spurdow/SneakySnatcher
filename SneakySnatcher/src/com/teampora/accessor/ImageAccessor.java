package com.teampora.accessor;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import aurelienribon.tweenengine.TweenAccessor;

public class ImageAccessor implements TweenAccessor<Image> {
	
	public final static int POS_X = 0x01;
	
	public final static int POS_Y = 0x02;
	
	public final static int POS_XY = 0x03;
	
	public final static int WIDTH = 0x04;
	
	public final static int HEIGHT = 0x05;
	
	public final static int WIDTH_HEIGHT = 0x06;
	
	public final static int SCALE_XY = 0x07;
	
	public final static int ROTATE = 0x08;
	
	public final static int ALPHA = 0x09;

	@Override
	public int getValues(Image target, int type, float[] values) {
		// TODO Auto-generated method stub
		switch(type){
		case POS_X : values[0] = target.getX(); return 1;
		case POS_Y : values[0] = target.getY(); return 1;
		case POS_XY: values[0] = target.getX();
					 values[1] = target.getY(); return 2;
		case WIDTH_HEIGHT: values[0] = target.getWidth();
						   values[1] = target.getHeight();
						   return 2;
		case SCALE_XY : values[0] = target.getScaleX();
						values[1] = target.getScaleY();
						return 2;
		case ROTATE: values[0] = target.getRotation(); return 1;
		case ALPHA : values[0] = target.getColor().a; return 1;
		default:assert false; return -1;
		}
		
	}

	@Override
	public void setValues(Image target, int type, float[] values) {
		// TODO Auto-generated ethod stub
		switch(type){
		case POS_X: target.setX(values[0]);break;
		case POS_Y: target.setY(values[0]);break;
		case POS_XY:target.setPosition(values[0], values[1]);break;
		case WIDTH_HEIGHT: target.setSize(values[0], values[1]);break;
		case SCALE_XY : target.setScale(values[0], values[1]);break;
		case ROTATE: target.rotate(values[0]);break;
		case ALPHA : target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, values[0]);
			default:assert false;break;
		}
	}

}
