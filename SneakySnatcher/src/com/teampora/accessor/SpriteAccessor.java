package com.teampora.accessor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAccessor implements TweenAccessor<Sprite>{

	
	public final static int SCALE_X = 0x0;
	
	public final static int SCALE_Y = 0x01;
	
	public final static int SCALE_XY = 0x02;
	
	public static final int POS_XY = 0x03;
	
	public static final int CPOS_XY = 0x04;
	
	public static final int ROTATION = 0x06;
	
	public static final int OPACITY = 0x07;
	
	public static final int TINT = 0x08;
	
	@Override
	public int getValues(Sprite target, int type, float[] values) {
		// TODO Auto-generated method stub
		
		switch(type){
		case SCALE_X : 
				values[0] = target.getScaleX();
				return 1;
		case SCALE_Y : 
				values[0] = target.getScaleY();
				return 1;
		case SCALE_XY: 
				values[0] = target.getScaleX();
				values[1] = target.getScaleY();
				return 2;
		case POS_XY:
			values[0] = target.getX();
			values[1] = target.getY();
			return 2;

		case CPOS_XY:
			values[0] = target.getX() + target.getWidth()/2;
			values[1] = target.getY() + target.getHeight()/2;
			return 2;
			
		case ROTATION: values[0] = target.getRotation(); return 1;
		case OPACITY:  values[0] = target.getColor().a; return 1;

		case TINT:
			values[0] = target.getColor().r;
			values[1] = target.getColor().g;
			values[2] = target.getColor().b;
			return 3;
			default: assert false; return -1;
		}
	}

	@Override
	public void setValues(Sprite target, int type, float[] values) {
		// TODO Auto-generated method stub
		switch(type){
		case SCALE_X : target.setScale(values[0], target.getScaleY());break;
		case SCALE_Y : target.setScale(target.getScaleX(), values[0]);break;
		case SCALE_XY: target.setScale(values[0], values[1]);	break;
		case POS_XY: target.setPosition(values[0], values[1]); break;
		case CPOS_XY: target.setPosition(values[0] - target.getWidth()/2, values[1] - target.getHeight()/2); break;
		case ROTATION: target.setRotation(values[0]); break;
		case OPACITY:
			Color c = target.getColor();
			c.set(c.r, c.g, c.b, values[0]);
			target.setColor(c);
			break;

		case TINT:
			c = target.getColor();
			c.set(values[0], values[1], values[2], c.a);
			target.setColor(c);
			break;

		default: assert false; break;
		
		}
		
	}

}
