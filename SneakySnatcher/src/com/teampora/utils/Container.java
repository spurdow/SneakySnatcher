package com.teampora.utils;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Container extends Table {

	private TextureRegion background;
	
	private Texture bg;
	
	public Container(TextureRegion background){
		this.background = background;
		this.setHeight(Gdx.graphics.getHeight());
		this.setWidth(Gdx.graphics.getWidth());
		this.setX(0.0f);
		this.setY(0.0f);
	}
	public Container(Texture background){
		this.bg = background;
		this.setHeight(Gdx.graphics.getHeight());
		this.setWidth(Gdx.graphics.getWidth());
		this.setX(0.0f);
		this.setY(0.0f);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		if(background!=null){
			batch.draw(background, getX(), getY(), getWidth(), getHeight());
		}else if(bg != null){
			batch.draw(bg, getX(), getY(), getWidth(), getHeight());
		}
		
		super.draw(batch, parentAlpha);
	}
	
	
}
