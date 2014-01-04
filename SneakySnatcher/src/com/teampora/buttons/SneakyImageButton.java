package com.teampora.buttons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.teampora.managers.SettingsManager;
import com.teampora.managers.SoundManager;
import com.teampora.managers.SoundManager.SoundType;
import com.teampora.utils.Assets;


public class SneakyImageButton extends ImageButton{

	private final StringBuilder tag = new StringBuilder();
	
	private TextureRegion drawableUp;
	
	private boolean isRightArrow = false;
	
	private boolean useNinePatch = false;
	
	private final static float padding = 1f;
	
	private NinePatchDrawable ninePatchDrawable;
	
	public SneakyImageButton(String tag , Skin skin) {
		super(skin);
		// TODO Auto-generated constructor stub
		this.tag.append(tag);
		initClickListener();
	}
	
	public SneakyImageButton(String tag, Skin skin , TextureRegion drawableUp){
		super(skin);
		this.tag.append(tag);
		this.drawableUp = drawableUp;
		initClickListener();
	}
	
	public SneakyImageButton(String tag, Skin skin , String imageButton , TextureRegion drawableUp ){
		super(skin, imageButton);
		this.tag.append(tag);
		this.drawableUp  = drawableUp;
		initClickListener();
	}
	
	public SneakyImageButton(String tag, Skin skin , TextureRegion drawableUp , boolean rightArrow){
		super(skin);
		this.tag.append(tag);
		this.drawableUp = drawableUp;
		this.isRightArrow = rightArrow;
		initClickListener();
	}
	
	public void initClickListener(){
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

	/*
	 *  sets the tag
	 */
	
	public void setTag(String tag){
		this.tag.setLength(0);
		this.tag.append(tag);
	}
	
	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag.toString();
	}

	/**
	 * @return the drawableUp
	 */
	public TextureRegion getDrawableUp() {
		return drawableUp;
	}

	/**
	 * @param drawableUp the drawableUp to set
	 */
	public void setDrawableUp(TextureRegion drawableUp) {
		this.drawableUp = drawableUp;
	}
	
	

	
	/**
	 * @return the isRightArrow
	 */
	public boolean isRightArrow() {
		return isRightArrow;
	}

	/**
	 * @param isRightArrow the isRightArrow to set
	 */
	public void setRightArrow(boolean isRightArrow) {
		this.isRightArrow = isRightArrow;
	}
	
	

	/**
	 * @return the useNinePatch
	 */
	public boolean isUseNinePatch() {
		return useNinePatch;
	}

	/**
	 * @param useNinePatch the useNinePatch to set
	 */
	public void setUseNinePatch(boolean useNinePatch) {
		this.useNinePatch = useNinePatch;
	}

	/**
	 * @return the ninePatchDrawable
	 */
	public NinePatchDrawable getNinePatchDrawable() {
		return ninePatchDrawable;
	}

	/**
	 * @param ninePatchDrawable the ninePatchDrawable to set
	 */
	public void setNinePatchDrawable(NinePatchDrawable ninePatchDrawable) {
		this.ninePatchDrawable = ninePatchDrawable;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.ui.ImageButton#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		if(drawableUp != null ){
			if(isRightArrow){
				batch.draw(drawableUp, getX() - getWidth() * 0.05f, getY() - getHeight() * 0.15f, getWidth() / 2 , getHeight() / 2, getWidth() * 0.9f, getHeight() * 0.7f, getScaleX(), getScaleY(), getRotation() +  (180f));
			}else{
				batch.draw(drawableUp, getX() + getWidth() * 0.05f, getY() + getHeight() * 0.15f, getWidth() * 0.9f , getHeight() * 0.7f);
			}
		}
		
	}

	
	
	
}
