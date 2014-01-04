package com.teampora.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class EffectModels extends Actor{
	
	private final static String LOG_TAG = "EFFECT_MODELS";

	private TextureRegion image;
	private Action[] actions;
	private Action intro;
	private int action;
	
	
	public static final int ACTIONS_FOREVER = 0x00;
	public static final int ACTIONS_ONCE = 0x01;
	public static final int ACTIONS_BOTH = 0x02;
	

	
	public EffectModels(TextureRegion image  ,int action, Action... actions ){
		this.image = image;
		this.actions = actions;
		this.action = action;
		
	}
	
	public EffectModels(TextureRegion image , Action act_intro , int action  ,
			Action... actions){
		this(image , action , actions);
		this.intro = act_intro;
	}
	
	public EffectModels(TextureRegion image , float startX , float startY  , 
			             float endX , float endY , Action act_intro  ,
			             int action , Action... actions){
		this(image , act_intro, action  , actions);
		setPosition(startX, startY);
		if(this.intro == null)
			this.intro = Actions.moveBy(endX, 0 , 1.0f);
		
		Gdx.app.log(LOG_TAG, "INTRO ADDED");
		
	}
	
	public enum next{
		left,
		right
	}
	
	public void setUpForTitles(next n){
		Action shake_left = Actions.rotateBy(10f , 0.07f);
		Action shake_right = Actions.rotateBy(-10f , 0.07f);
		
		Action op_shake_left = Actions.rotateBy(-10f , 0.07f);
		Action op_shake_right = Actions.rotateBy(10f , 0.07f);
		actions[0] = n==next.left ? shake_left : shake_right;
		actions[1] = n==next.left ? shake_right : shake_left;
		
		actions[2] = n==next.left ? op_shake_left : op_shake_right;
		actions[3] = n==next.left ? op_shake_right : op_shake_left;
				
		actions[4] = Actions.delay(2.0f);
		setUpActions();
	}
	
	
	
	public void setUpActions(){
		if(actions != null && actions[0] !=null){
			if(action == ACTIONS_FOREVER){
				addAction(Actions.forever(Actions.sequence(actions)));
				
			}else if(action == ACTIONS_ONCE){
				addAction(Actions.sequence(actions));
			}
			else if(action == ACTIONS_BOTH && intro != null){
				addAction(Actions.sequence(intro,
						Actions.forever(Actions.sequence(actions))));
			}
		}
	}
	
	public void release(){
		this.clear();
		image = null;
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		
		if(image != null){
			//batch.draw(image, x, y, this.get, image.getTexture().getHeight()/2, image.getRegionWidth(), image.getRegionHeight(),image_drawable.getScaleX(), image_drawable.getScaleY(), image_drawable.getRotation());
			batch.draw(image, getX(), getY(), getWidth()/2, getHeight()/2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		}
		super.draw(batch, parentAlpha);

		
	}

}
