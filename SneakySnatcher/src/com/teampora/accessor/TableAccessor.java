package com.teampora.accessor;

import com.badlogic.gdx.graphics.Color;
import com.teampora.tables.TableOverlay;

import aurelienribon.tweenengine.TweenAccessor;

public class TableAccessor implements TweenAccessor<TableOverlay> {

	public final static int ALPHA = 0x00;
	
	public final static int POS_X = 0x01;
	
	public final static int POS_Y = 0x02;
	
	public final static int POS_XY = 0x03;
	
	public final static int SCALE_X = 0x04;
	
	public final static int SCALE_Y = 0x05;
	
	public final static int SCALE_XY = 0x06;
	
	public final static int WIDTH = 0x07;
	
	public final static int HEIGHT = 0x08;
	
	public final static int SIZE = 0x09;
	
	
	@Override
	public int getValues(TableOverlay arg0, int arg1, float[] arg2) {
		// TODO Auto-generated method stub
		switch(arg1){
		
		case ALPHA : arg2[0] = arg0.getColor().a; return 1;
		case POS_X : arg2[0] = arg0.getX(); return 1;
		case POS_Y : arg2[0] = arg0.getY(); return 1;
		case POS_XY: arg2[0] = arg0.getX(); 
					 arg2[1] = arg0.getY(); return 2;
					
		case SCALE_X: arg2[0] = arg0.getScaleX(); return 1;
		case SCALE_Y: arg2[0] = arg0.getScaleY(); return 1;
		case SCALE_XY : arg2[0] = arg0.getScaleX(); 
					    arg2[1] = arg0.getScaleY(); return 2;
					    
		case SIZE : arg2[0] = arg0.getWidth(); arg2[1] = arg0.getHeight(); return 2;
		default:assert false; return -1;
		}
		
		
	}

	@Override
	public void setValues(TableOverlay arg0, int arg1, float[] arg2) {
		// TODO Auto-generated method stub
		Color rgba = arg0.getColor();
		switch(arg1){
		case ALPHA: arg0.getColor().set(rgba.r, rgba.g, rgba.b, arg2[0]);break;
		case POS_X: arg0.setX(arg2[0]);break;
		case POS_Y: arg0.setY(arg2[0]);break;
		case POS_XY:arg0.setX(arg2[0]);arg0.setY(arg2[1]);break;
		case SCALE_X: arg0.setScaleX(arg2[0]); break;
		case SCALE_Y: arg0.setScaleY(arg2[0]); break;
		case SCALE_XY : arg0.setScale(arg2[0] , arg2[1]); break;
		case SIZE : arg0.setSize(arg2[0], arg2[1]); break;
			default: assert false; break;
		}
		
	}

}
