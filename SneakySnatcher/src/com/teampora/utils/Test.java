package com.teampora.utils;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.teampora.accessor.ImageAccessor;

public class Test extends Table{

	private final TextureRegion backgroundRegion;
	private final Label label;
	private final Image image;
	private float labelStart;

	public Test(TextureRegion backgroundRegion, CharSequence text,
			String styleName , Image image , float labelStart) {
		super();
		this.label = new Label(text, Assets.skin, styleName);
		this.backgroundRegion = backgroundRegion;
		this.image = image;
		this.labelStart = labelStart;
		Tween.registerAccessor(Image.class, new ImageAccessor());
	}
	
	public Test(TextureRegion backgroundRegion, CharSequence text,
			String fontname, Color color , Image image){
		super();
		this.label = new Label(text , Assets.skin, fontname , color);
		this.backgroundRegion = backgroundRegion;
		this.image = image;
	}

	@Override
	public void setSize(float width, float height) {
		// TODO Auto-generated method stub
		super.setSize(width + 20f, height);
		labelStart = width / 2f ;
		if(image!=null){
			image.setSize(labelStart, height * 0.8f);
			image.setOrigin(image.getWidth()/2, image.getHeight()/2);
		}
		
		label.setSize(width, height);
	}
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
		if(image!=null)
			image.setPosition(x , y +5);
		label.setPosition(x+getWidth()/2,y);
		Gdx.app.log("s", getWidth()+"");
	}



	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		
		if(backgroundRegion!=null){
			batch.setColor(Color.WHITE);
			batch.draw(backgroundRegion, getX(), getY(), getWidth(), getHeight());
		}
		if(image != null){
			image.draw(batch, parentAlpha);
			label.draw(batch, parentAlpha);
			
		}else{
			label.draw(batch, parentAlpha);
		}
		
		
		super.draw(batch, parentAlpha);
	}
	
	
	public void setText(CharSequence newText , TweenManager tween){
		label.setText(newText);
		if(newText.equals("0")) return;
		Timeline.createSequence()
		.beginSequence()
			.push(Tween.to(image, ImageAccessor.SCALE_XY, 1.0f).target(1,1).ease(Quint.IN))
			.push(Tween.to(image, ImageAccessor.SCALE_XY, 1.0f).target(2,2).ease(Quint.IN))
			.push(Tween.to(image, ImageAccessor.SCALE_XY, 1.0f).target(1,1).ease(Quint.IN))
			//.push(Tween.to(image, ImageAccessor.ROTATE, 1.0f).target(-5).ease(Cubic.INOUT))
			//.push(Tween.to(image, ImageAccessor.ROTATE, 1.0f).target(5).ease(Cubic.INOUT))
			//.push(Tween.to(image, ImageAccessor.ROTATE, 1.0f).target(0).ease(Cubic.INOUT))
		.end()
		.setCallback(new TweenCallback(){

			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				// TODO Auto-generated method stub
				arg1.kill();
			}
			
		})
		.start(tween);
		
	}
	
	public CharSequence getText(){
		return label.getText();
	}
}
