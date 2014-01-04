package com.teampora.groups;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.teampora.utils.Utils;

public class IndicatorBox extends Group{

	private int max_indicators ;
	private int current_indicator;
	private NinePatch selected;
	private NinePatch unselected;
	private int padding = 30;
	
	public IndicatorBox(int max , int curr, NinePatch selected, 
						NinePatch unselected){
		this.max_indicators = max;
		this.current_indicator = curr;
		this.selected = selected;
		this.unselected = unselected;
		
		//this.setBounds(getX(), getY(), Utils.WIDTH_LARGE(), Utils.HEIGHT_NORMAL());
		
		
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Group#drawChildren(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		float width = getWidth() / max_indicators;
		for(int i = 0 ; i < max_indicators ; i++){
			if(i == current_indicator){
				//batch.draw(selected, getX() + (i * width) + padding, getY(), width , getHeight() * 0.5f);
				selected.draw(batch, getX() + (i * width) + padding, getY(), width , getHeight() * 0.5f);
			}else
				//batch.draw(unselected, getX() + (i * width) + padding, getY(), width, getHeight() * 0.5f);
				unselected.draw(batch, getX() + (i * width) + padding, getY(), width , getHeight() * 0.5f);
		}
	}

	/**
	 * @return the current_indicator
	 */
	public int getCurrent_indicator() {
		return current_indicator;
	}

	/**
	 * @param current_indicator the current_indicator to set
	 */
	public void setCurrent_indicator(int current_indicator) {
		this.current_indicator = current_indicator;
	}


	
	
	
	
}
