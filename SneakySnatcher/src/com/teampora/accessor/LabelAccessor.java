package com.teampora.accessor;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.teampora.managers.SettingsManager;

import aurelienribon.tweenengine.TweenAccessor;

public class LabelAccessor implements TweenAccessor<Label>{
	
	public final static int COLOR = 0x00;
	
	public final static int POS_X = 0x01;
	
	public final static int POS_Y = 0x02;
	
	public final static int POS_XY = 0x03;
	
	public final static int SIZE_X = 0x04;
	
	public final static int SIZE_Y = 0x05;
	
	public final static int SIZE_XY = 0x06;
	
	public final static int SCORE = 0x07;
	
	public final static int SCALE_XY = 0x08;
	
	public final static int TEXT = 0x09;
	
	public final static int COLOR_A = 0x10;

	@Override
	public int getValues(Label target, int type, float[] values) {
		// TODO Auto-generated method stub
		switch(type){
		case COLOR: values[0] = target.getColor().r; 
					values[1] = target.getColor().g;
					values[2] = target.getColor().b;
					values[3] = target.getColor().a;return 4;
		case POS_X: values[0] = target.getX(); return 1;
		case POS_Y: values[0] = target.getY();return 1;
		case POS_XY: values[0] = target.getX();
					 values[1] = target.getY();return 2;
		case SIZE_X: values[0] = target.getWidth();return 1;
		case SIZE_Y:  values[0] = target.getHeight();return 1;
		case SIZE_XY: values[0] = target.getWidth();
						values[1] = target.getHeight();return 2;
		case SCORE: values[0] = Float.parseFloat(target.getText().toString());
		case SCALE_XY : values[0] = target.getScaleX();
					    values[1] = target.getScaleY();
					    return 2;
		case TEXT : values[0] = Integer.parseInt(target.getText().toString().split("[ ]")[1]);
					return 1;
		case COLOR_A : values[0] = target.getColor().a;
					return 1;
		default:assert false; return -1;
		}
	}
	private StringBuilder builder = new StringBuilder();
	@Override
	public void setValues(Label target, int type, float[] values) {
		// TODO Auto-generated method stub
		switch(type){
		case COLOR: target.setColor(values[0], values[1], values[2], values[3]); break;
		case POS_X: target.setX(values[0]);break;
		case POS_Y: target.setY(values[0]);break;
		case POS_XY: target.setX(values[0]);
					 target.setY(values[1]);break;
		case SIZE_X: target.setWidth(values[0]);
					 break;
		case SIZE_Y: target.setHeight(values[0]);
					 break;
		case SIZE_XY:target.setWidth(values[0]);
					 target.setHeight(values[1]);
					 break;
		case SCORE : target.setText(String.valueOf(values[0]));
		case SCALE_XY : target.setScaleX(values[0]);
						target.setScaleY(values[1]);
					break;
		case TEXT:  builder.setLength(0);
				    builder.append("x ").append((int)values[0]);
				    target.setText(builder.toString());
					break;
		case COLOR_A : target.setColor(target.getColor().r , target.getColor().g, target.getColor().b , values[0]) ;
					break;
		default:assert false; break;
		}
		
	}

}
