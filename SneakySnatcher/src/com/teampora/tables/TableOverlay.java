package com.teampora.tables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TableOverlay extends Table{

	private NinePatch bg;
	private float alpha = 1f;
	private Color color ;
	
	public TableOverlay(NinePatch bg , float x, float y , float width, float height){
		this.bg = bg;
		setBounds(x , y , width , height);
		setPosition(x , y);
		setWidth(width);
		setHeight(height);
		this.color = bg.getColor();
	}
	
	public TableOverlay(NinePatch bg , float x, float y , float width, float height , float alpha){
		this(bg,x,y,width,height);
		this.alpha = alpha;
	}
	
	public TableOverlay(NinePatch bg, float x, float y , float width, float height , float alpha , Color c){
		this(bg,x,y,width,height, alpha);
		this.color =c;
	}
	
	
	
	
	
	/**
	 * @return the bg
	 */
	public NinePatch getBg() {
		return bg;
	}

	/**
	 * @param bg the bg to set
	 */
	public void setBg(NinePatch bg) {
		this.bg = bg;
	}

	/**
	 * @return the alpha
	 */
	public float getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub	
		
		if(bg != null){
			//batch.setColor(color.r, color.g, color.b, alpha);
			Color c = bg.getColor();
			c.set(c.r, c.g, c.b, alpha);
			bg.setColor(c);
			bg.draw(batch, getX() , getY() , getWidth() , getHeight());
		}
		
		super.draw(batch, parentAlpha);
	}
	
}
