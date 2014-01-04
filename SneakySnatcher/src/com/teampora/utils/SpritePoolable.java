package com.teampora.utils;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool.Poolable;

public class SpritePoolable extends Sprite implements Poolable{

	public boolean isVisible = false;
	
	private final static float TICK_TO_DECREASE = 0.01f;
	
	public SpritePoolable() {
		super();
	}

	public SpritePoolable(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	public SpritePoolable(Texture texture, int srcX, int srcY, int srcWidth,
			int srcHeight) {
		super(texture, srcX, srcY, srcWidth, srcHeight);
		// TODO Auto-generated constructor stub
	}

	public SpritePoolable(Texture texture, int srcWidth, int srcHeight) {
		super(texture, srcWidth, srcHeight);
		// TODO Auto-generated constructor stub
	}

	public SpritePoolable(Texture texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

	public SpritePoolable(TextureRegion region, int srcX, int srcY,
			int srcWidth, int srcHeight) {
		super(region, srcX, srcY, srcWidth, srcHeight);
		// TODO Auto-generated constructor stub
	}

	public SpritePoolable(TextureRegion region) {
		super(region);
		// TODO Auto-generated constructor stub
	}
	public void createNew(float width ,float height, float x, float y  , Color c){
		isVisible = true;
		setRegion(Assets.fruit_splash);
		setSize(width * 3, height * 3);
		setPosition(x - 1, y - 1);
		setScale(1f);
		setOrigin(width * 3 / 2, height * 3/ 2);
		setRotation(MathUtils.random(360 * MathUtils.radiansToDegrees));
		this.c = c;
		alpha = 1f;
		
	}
	
	float alpha = 1.0f;
	public Color c = Color.WHITE;


	@Override
	public void draw(SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
		
		alpha-=TICK_TO_DECREASE;
		this.setColor(c.r, c.g, c.b, alpha);
		if(alpha <= 0f){
			isVisible = false;
		}
		if(!isVisible) return;
			super.draw(spriteBatch);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		setColor(c.r, c.g, c.b, 0f);
		setSize(0f, 0f);
		isVisible = false;
	}

	

}
