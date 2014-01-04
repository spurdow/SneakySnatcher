package com.teampora.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.teampora.managers.SettingsManager;
import com.teampora.managers.SoundManager;
import com.teampora.managers.SoundManager.SoundType;


import com.teampora.utils.Assets;

public class SneakyTextButton extends TextButton{
	
	private final Label label;
	
	private TextureRegion lockRegion;
	
	private boolean imageOnly;
	
	
	public SneakyTextButton(String text, Skin skin) {
		super(text, skin);
		// TODO Auto-generated constructor stub
		
		removeActor(getLabel());
		label = new Label(text, Assets.skin , "large_font");
		label.setAlignment(Align.center);
		label.setWrap(true);
		add(label);
		this.invalidateHierarchy();
		addListener(new ClickListener(){

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				SoundManager.play(SoundType.CLICK);
				super.clicked(event, x, y);
			}
			
		});
	}
	public SneakyTextButton(String text, Skin skin , String font) {
		super(text, skin);
		// TODO Auto-generated constructor stub
		
		removeActor(getLabel());
		label = new Label(text, Assets.skin , font);
		label.setAlignment(Align.center);
		label.setWrap(true);
		add(label);
		this.invalidateHierarchy();
	}
	
	public SneakyTextButton(String text, Skin skin , String font , Color color) {
		super(text, skin);
		// TODO Auto-generated constructor stub
		
		removeActor(getLabel());
		label = new Label(text, Assets.skin , font , color);
		label.setAlignment(Align.center);
		label.setWrap(true);
		add(label);
		this.invalidateHierarchy();
		addListener(new ClickListener(){

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				SoundManager.play(SoundType.CLICK);
				super.clicked(event, x, y);
			}
			
		});
	}
	
	public SneakyTextButton(Skin skin , String style){
		super("",skin,style);
		removeActor(getLabel());
		label = new Label("", skin , "large_font");
		label.setAlignment(Align.center);
		label.setWrap(true);
		add(label);
		this.invalidateHierarchy();
	}
	

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		label.setText(text);
	}
	@Override
	public CharSequence getText() {
		// TODO Auto-generated method stub
		return label.getText();
	}
	/**
	 * @return the lockRegion
	 */
	public TextureRegion getLockRegion() {
		return lockRegion;
	}
	/**
	 * @param lockRegion the lockRegion to set
	 */
	public void setLockRegion(TextureRegion lockRegion) {
		this.lockRegion = lockRegion;
	}
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.ui.TextButton#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		
		super.draw(batch, parentAlpha);
		
		if(lockRegion!=null && getTouchable() == Touchable.disabled){
			batch.setColor(Color.WHITE);
			batch.draw(lockRegion, getX() , getY(), getWidth()  , getHeight() );
		}
	
	}
	/**
	 * @return the imageOnly
	 */
	public boolean isImageOnly() {
		return imageOnly;
	}
	/**
	 * @param imageOnly the imageOnly to set
	 */
	public void setImageOnly(boolean imageOnly) {
		this.imageOnly = imageOnly;
	}
	
	
	
	
	
	
}
