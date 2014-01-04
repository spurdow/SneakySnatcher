package com.teampora.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.teampora.managers.MusicEffectManager;
import com.teampora.managers.MusicEffectManager.MusicEffectType;

public class AnimatedImage extends Animation{
	

	private float stateTime;
	
	private Vector2 position;
	
	private Vector2 size;
	
	private boolean stop;
	
	private boolean flip;
	
	private boolean draw;
	
	private final float MAX_STATE_TIME = 3f;
	
	
	public AnimatedImage(float frameDuration,
			Array<? extends TextureRegion> keyFrames ,
			float x, float y , float width, float height) {
		super(frameDuration, keyFrames);
		// TODO Auto-generated constructor stub
		this.stateTime = 0;
		this.position = new Vector2(x , y);
		this.size = new Vector2(width , height);
	}

	public AnimatedImage(float frameDuration,
			Array<? extends TextureRegion> keyFrames, int playType, 
			float x , float y , float width , float height) {
		super(frameDuration, keyFrames, playType);
		// TODO Auto-generated constructor stub
		this.stateTime = 0;
		this.position = new Vector2(x , y);
		this.size = new Vector2(width , height);
	}

	public AnimatedImage(float frameDuration, float x , float y ,
			float width , float height , TextureRegion... keyFrames) {
		super(frameDuration, keyFrames);
		// TODO Auto-generated constructor stub
		this.stateTime = 0;
		this.position = new Vector2(x , y);
		this.size = new Vector2(width , height);
	}

	private TextureRegion region;
	
	public void update(float delta){
		if(stateTime >= MAX_STATE_TIME ) stop = true;
		if( stop ) return;
		stateTime+= delta;
		region = this.getKeyFrame(stateTime, true);
		if(flip && !region.isFlipX()){
			region.flip(true, false);
		}
		
		
	}
	
	public void draw(SpriteBatch batch){
		if( !draw ) return;
		batch.begin();
		batch.draw(region, position.x, position.y, size.x, size.y);
		batch.end();
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
		stateTime = 0;
		MusicEffectManager.getIns().createNewEffect(MusicEffectType.MURMUR);
		if( stop ){
			MusicEffectManager.getIns().pause();
		}else{
			MusicEffectManager.getIns().play();
		}
	}

	public boolean isFlip() {
		return flip;
	}

	public void setFlip(boolean flip) {
		this.flip = flip;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}
	

	
}
