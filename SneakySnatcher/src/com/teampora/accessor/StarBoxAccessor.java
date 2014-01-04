package com.teampora.accessor;

import com.teampora.groups.StarBox;
import com.teampora.managers.MusicEffectManager;
import com.teampora.managers.MusicEffectManager.MusicEffectType;

import aurelienribon.tweenengine.TweenAccessor;

public class StarBoxAccessor implements TweenAccessor<StarBox>{

	public final static int SCORE = 0x00;
	
	public final static int PLAY_COINS = 0x01;
	
	@Override
	public int getValues(StarBox target, int type, float[] values) {
		
		// TODO Auto-generated method stub
		switch(type){
		case SCORE  : values[0] = target.getCurrentScore(); 
			return 1;
		case PLAY_COINS : 
			if( !MusicEffectManager.getIns().isPlaying())
				MusicEffectManager.getIns().createNewEffect(MusicEffectType.COUNT, 5f, true, true);
			return 1;
			default:assert false;return -1;
		}
	}

	@Override
	public void setValues(StarBox target, int type, float[] values) {
		// TODO Auto-generated method stub
		switch(type){
		case SCORE: target.setCurrentScore((int) values[0]); break;
		case PLAY_COINS: break;
		default: assert false; break;
		}
	}

}
