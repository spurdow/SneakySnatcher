package com.teampora.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.teampora.utils.Assets;

public class SoundManager implements Disposable {
	
	
	private final static float default_volume = 1f;
	private final static float default_pan = 1f;
	private final static float default_pitch = 1f;
	
	public enum SoundType{
		CLICK,
		BOMB,
		DROP,
		DIALOG,
		BUY,
		COIN,
		SQUASH,
		FAILED,
		SUCCESS,
		TOP,
		STEAL,
		TOUCH1,
		TOUCH2,
		TOUCH3,
		TOUCH4
		
		
	}
	/*
	 * 
	 */
	
	public static void play(SoundType type ,float volume , float pitch, float pan){
		if(!SettingsManager.isSoundOn) return;
		
		switch(type){
		case CLICK : Assets.touch1.play(volume, pitch, pan); break;
		case BOMB  : Assets.bomb.play(volume, pitch, pan); break;
		case DROP  : Assets.drop.play(volume, pitch, pan); break;
		case DIALOG: Assets.dialog.play(volume, pitch, pan); break;
		case BUY   : Assets.buy.play(volume, pitch, pan); break;
		case COIN  : Assets.coin.play(volume, pitch, pan); break;
		case SQUASH: Assets.squash.play(volume, pitch, pan); break;
		case FAILED: Assets.failed.play(volume, pitch, pan); break;
		case SUCCESS: Assets.success.play(volume, pitch, pan); break;
		case TOP   : Assets.top.play(volume, pitch, pan); break;
		case STEAL : Assets.steal.play(volume, pitch, pan); break;
		case TOUCH1: Assets.touch1.play(volume, pitch, pan); break;
		case TOUCH2: Assets.touch2.play(volume, pitch, pan); break;
		case TOUCH3: Assets.touch3.play(volume, pitch, pan); break;
		case TOUCH4: Assets.touch4.play(volume, pitch, pan); break;
		default: assert false; break;
		}
	}
	
	/*
	 * 
	 */
	public static void play(SoundType type, float vol){
		play(type, vol , default_pitch , default_pan);
	}
	
	/*
	 * 
	 */
	public static void play(SoundType type){
		play(type, default_volume);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	
		Gdx.app.log("SoundManager","is disposed...");
	}

}
