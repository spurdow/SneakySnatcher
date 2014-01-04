package com.teampora.buttons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SneakyLevelButton extends SneakyTextButton{

	private int count = 0;
	private final int max_count = 3;
	private TextureRegion star_filled;
	private TextureRegion unfilled;
	
	public SneakyLevelButton(Skin skin , String style , int count , TextureRegion star_filled,
			                 TextureRegion unfilled) {
		super(skin , style);
		// TODO Auto-generated constructor stub
		this.count = count;
		this.star_filled = star_filled;
		this.unfilled = unfilled;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.ui.ImageButton#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		if(getLockRegion() == null && getTouchable()==Touchable.enabled && star_filled != null && unfilled != null){
			float width = getWidth() * 0.33f;
			float height = getHeight() * 0.20f;
			for(int i =0 ;i < max_count ; i++){
				batch.draw(unfilled, getX() + (i * width), getY(), width, height);
				if((i+1) <= count){
					batch.draw(star_filled, getX() + (i * width), getY(), width , height );
				}
			}
			
		}
	}
	
	
	

}
