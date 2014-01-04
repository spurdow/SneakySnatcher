package com.teampora.groups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.teampora.utils.Utils;

public class FruitMeter extends GameBox {

	private NinePatch fruit_active;
	private NinePatch fruit_inactive;
	private int max_number;
	private int active_fruits = 0;
	private float alpha = 1f;
	private FruitMeterType type = FruitMeterType.VERTICAL;
	private FruitMeterSize size;
	private boolean showBg;
	
	public enum FruitMeterType{
		VERTICAL,
		HORIZONTAL
	}
	
	public enum FruitMeterSize{
		SMALL( Utils.WIDTH_SMALL() * 0.8f , Utils.WIDTH_EXTRA_SMALL() * 0.8f ),
		NORMAL( Utils.WIDTH_SMALL() , Utils.WIDTH_EXTRA_SMALL() ),
		
		;
		private float width;
		private float height;
		
		private FruitMeterSize(float width, float height){
			this.width = width;
			this.height = height;
		}
		
		public float getWidth(){return width;}
		public float getHeight(){return height;}
	}
	
	/*
	 *  bomb active image ninepatch
	 *  bomb inactive image ninepatch
	 *  number of bombs
	 */
	public FruitMeter(NinePatch background , NinePatch bomb_active , NinePatch bomb_inactive , int number , FruitMeterType type , FruitMeterSize size){
		super(new NinePatchDrawable(background));
		this.fruit_active = bomb_active;
		this.fruit_inactive = bomb_inactive;
		this.max_number = number;
		this.type = type;
		this.size = size;
		this.showBg = true;
		initUI();
	}
	
	
	
	public FruitMeter(NinePatch background , NinePatch bomb_active , NinePatch bomb_inactive){
		this(background , bomb_active , bomb_inactive , 3 , FruitMeterType.VERTICAL , FruitMeterSize.NORMAL);
	}
	
	public FruitMeter(NinePatch background , NinePatch bomb_active , NinePatch bomb_inactive , FruitMeterType type){
		this(background , bomb_active , bomb_inactive , 3 , type , FruitMeterSize.NORMAL);
	}
	
	public void initUI(){
		
		setType(type);
		addActor(background);
		
		setBounds(getX() , getY() , background.getWidth() , background.getHeight());
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Group#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		for(int i =0 ; i < max_number ; i++){
				if(type == FruitMeterType.VERTICAL){
					if(i < active_fruits){
						fruit_active.draw(batch, getX() + (size.getHeight() - size.getWidth()) / 2, getY() + ( i * size.getHeight() ) , size.getWidth(), size.getHeight());
					}else{
						fruit_inactive.draw(batch, getX() + (size.getHeight() - size.getWidth()) / 2, getY() + ( i * size.getHeight()) , size.getWidth(), size.getHeight());
					}
				}else{
					if(i < active_fruits){
						fruit_active.draw(batch, getX()   + ( i * size.getWidth() ), getY()  + size.getHeight() / 4 , size.getWidth(), size.getHeight());
					}else{
						fruit_inactive.draw(batch, getX()  + ( i * size.getWidth() ), getY() + size.getHeight() / 4 , size.getWidth(), size.getHeight());
					}
				}
		}

	}

	/**
	 * @return the active_number
	 */
	public int getActiveFruits() {
		return active_fruits;
	}

	/**
	 * @param active_number the active_number to set
	 */
	public void setActiveFruits(int active_number) {
		this.active_fruits = active_number;
	}
	

	
	/**
	 * @return the max_number
	 */
	public int getMax_number() {
		return max_number;
	}

	/**
	 * @param max_number the max_number to set
	 */
	public void setMax_number(int max_number) {
		this.max_number = max_number;
	}

	public void reset(){
		active_fruits = 0;
	}

	/*
	 *  background alpha
	 */
	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		/*
		 *  change the alpha if not the same
		 */
		if(background.getColor().a != alpha){
			Color c = background.getColor();
			background.setColor(c.r, c.g, c.b, alpha);
		}
		background.invalidate();
	}

	public FruitMeterType getType() {
		return type;
	}

	public void setType(FruitMeterType type) {
		if(type == FruitMeterType.VERTICAL){
			background.setBounds(getX(), getY(), size.getHeight(), size.getHeight() * max_number);
		}else if(type == FruitMeterType.HORIZONTAL){
			background.setBounds(getX(), getY(), size.getWidth()* 1.5f * max_number, Utils.WIDTH_EXTRA_SMALL() );
		}

		this.type = type;
		
	}



	public boolean isShowBg() {
		return showBg;
	}



	public void setShowBg(boolean showBg) {
		this.showBg = showBg;
		if(!showBg){
			if(background.hasParent()){
				background.remove();
			}
		}else{
			if(background.hasParent()){
				addActor(background);
			}
		}
	}
	
	
	
	
	
	
}
