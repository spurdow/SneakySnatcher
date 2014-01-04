package com.teampora.accessor;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import aurelienribon.tweenengine.TweenAccessor;

public class ScrollAccessor implements TweenAccessor<ScrollPane>{
	
	public final static int SCROLL_Y_PERCENT = 0x00;
	
	public final static int SCROLL_Y = 0x01;
	
	public final static int ALPHA   = 0x01;

	@Override
	public int getValues(ScrollPane arg0, int arg1, float[] arg2) {
		// TODO Auto-generated method stub
		switch(arg1){
		case SCROLL_Y_PERCENT : arg2[0] = arg0.getScrollPercentY(); return 1;
		case ALPHA : arg2[0] = arg0.getColor().a; return 1;
		default: assert false; return -1;
		}
	}

	@Override
	public void setValues(ScrollPane arg0, int arg1, float[] arg2) {
		// TODO Auto-generated method stub
		switch(arg1){
		case SCROLL_Y_PERCENT : arg0.setScrollPercentY(arg2[0]);break;
		case ALPHA : arg0.setColor(arg0.getColor().r , arg0.getColor().g ,arg0.getColor().b , arg2[0]); break;
		default: assert false; break;
		}
		
	}

}
