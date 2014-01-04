package com.teampora.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.teampora.managers.MusicEffectManager;
import com.teampora.managers.SettingsManager;

import mdesl.swipe.SwipeHandler;

public class SwipeHandle extends SwipeHandler {

	public SwipeHandle(int maxInputPoints) {
		super(maxInputPoints);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Array<Vector2> path() {
		// TODO Auto-generated method stub
	
		if(simplified.size >= 15 && SettingsManager.isSoundOn ){
			//Gdx.app.log("Handler", simplified.size + " " + SettingsManager.isSoundOn + " " + Gdx.graphics.getFramesPerSecond() + " " + !MusicEffectManager.getIns().isPlaying());
			if(Gdx.graphics.getFramesPerSecond() >= MusicEffectManager.MIN_FPS && !MusicEffectManager.getIns().isPlaying()){
				MusicEffectManager.getIns().createNewEffect(SettingsManager.particles, 2f, false, true);
				Gdx.app.log("MusicEffect", "swipe played");
				
			}
		}
		return super.path();
	}
	
	

}
