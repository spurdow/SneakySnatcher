package com.teampora.labels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.teampora.utils.Assets;

public class SneakyLabel extends Label implements Poolable {

	private TextureRegion texture;
	private final StringBuilder builder = new StringBuilder();
	
	public SneakyLabel(String fontName , Color color){
		super("NA" , Assets.skin , fontName , color);
		builder.append("NA");
	}
	
	
	
	public SneakyLabel(CharSequence text, TextureRegion region){
		super(text , Assets.skin);
		builder.append(text);
		texture =region;
	}
	
	
	public SneakyLabel(CharSequence text, String styleName){
		super(text, Assets.skin, styleName);
		builder.append(text);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		
		if(texture!=null){
			batch.setColor(Color.WHITE);
			//batch.draw(texture, getX(), getY(), getWidth()/2, getHeight()/2, getWidth(), getHeight());
			batch.draw(texture, getX(), getY() , getWidth() , getHeight());
			getStyle().font.draw(batch, builder.toString(), getWidth() , getY() + 50f);
			
		}else{
			super.draw(batch, parentAlpha);
		}
		
		//super.draw(batch, parentAlpha);
	}

	@Override
	public void setText(CharSequence newText) {
		// TODO Auto-generated method stub
		builder.setLength(0);
		builder.append(newText);
		super.setText(newText);
	}

	@Override
	public CharSequence getText() {
		// TODO Auto-generated method stub
		return builder.toString();
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		builder.setLength(0);
		setPosition( - 50, 0);

	}

	
	
}
