package com.teampora.managers;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.teampora.utils.Cache;
import com.teampora.utils.Cache.ElderRemoverListener;
import com.teampora.utils.Utils;

/*
 *  Special class to work with difficult task for sound effect to produce,
 *  so i decided to create a class that implements Music, but is a sound effet
 */
public class MusicEffectManager implements Disposable, ElderRemoverListener<MusicEffectManager.MusicEffectType, Music>{

	
	/*
	 * the min fps an effect can play 
	 */
	
	public static float MIN_FPS = 35f;
	
	/*
	 *  the type of musical effect works with
	 *  hammer swings and the like.
	 */
	public enum MusicEffectType{
		SWING(Utils.PARTICLES + "hammer_swing.ogg"),
		BOLT(Utils.PARTICLES + "bolt_particle.ogg"),
		FIRE(Utils.PARTICLES + "fire_particle.ogg"),
		GOLDEN(Utils.PARTICLES+"golden_particle.ogg"),
		ICE(Utils.PARTICLES+"ice_particle.ogg"),
		STAR(Utils.PARTICLES+"star_particle.ogg"),
		WIND(Utils.PARTICLES+"wind_particle.ogg"),
		COUNT(Utils.PARTICLES+"coin_count.ogg"),
		MURMUR(Utils.PARTICLES+"murmuring.ogg"),
		;
		private final String fileName;
		private MusicEffectType(String fileName){
			this.fileName = fileName;
		}
		
		public String getFileName(){
			return fileName;
		}
	}
	
	/*
	 *  the instance of mem 
	 */
	private static MusicEffectManager instance;
	
	/*
	 *  the cache [LRU] for the music to not go bound the limits
	 *  in order for the music effect to be permanently size to 2
	 */
	
	private Cache<MusicEffectType, Music> cache;
	
	
	/*
	 *  the music instance
	 */

	private Music current;
	
	/*
	 * 
	 */
	private MusicEffectType type;
	
	/*
	 *  the singleton pattern
	 */
	private MusicEffectManager(){
		cache = new Cache<MusicEffectManager.MusicEffectType , Music>(3);
		cache.setRemoverListener(this);
	}
	
	/*
	 *  gets the singleton instance
	 */
	public static MusicEffectManager getIns(){
		if(instance == null){
			instance = new MusicEffectManager();
		}
		return instance;
	}
	
	/*
	 *  creates new file which is music file
	 *  using a file hahdle then creates a new music file
	 */
	protected Music createNewEffect(FileHandle handle){
		return Gdx.audio.newMusic(handle);
	}
	/*
	 *  creates a new file which is a music file
	 *  using string file then parse it to an file
	 */
	protected Music createNewEffect(String fileName){
		return createNewEffect(Gdx.files.internal(fileName));
	}
	/*
	 *  creates a new effect from key, volume, if loop , if play
	 *  
	 */
	public void createNewEffect(MusicEffectType key , float vol , boolean loop , boolean play){
		this.type = key;
		try{
			if(current!= null && current.isPlaying()){
				current.stop();
				current = null;
				Gdx.app.log("MUSIC EFFECT MGR", " music stop current call");
			}
		}catch(Exception ex){
			current = createNewEffect(key.getFileName());
			current.setLooping(loop);
			current.setVolume(vol);
			if(play){
				play();
			}
			ex.printStackTrace();
			Gdx.app.log("MUSIC EFFECT MGR", "exception called");
		}
			try{
				current = (Music) cache.get(key);
				if(current == null){
					current = createNewEffect(key.getFileName());
				}
					current.setLooping(loop);
					current.setVolume(vol);
					if(play){
						play();
					}
					Gdx.app.log("MUSIC EFFECT MGR", " current == null");
				
			}catch(Exception ex){
				current = (Music) cache.get(key);
				if(play){
					play();
				}
				ex.printStackTrace();
				Gdx.app.log("MUSIC EFFECT MGR", "exception call");
			}
		
		cache.insert(key, current);
	}
	
	/*
	 *  creates a new effect which has a key
	 *  default vol , loop, play
	 */
	public void createNewEffect(MusicEffectType key){
		createNewEffect(key , 1 , false, true);
	}
	
	
	
	/*
	 *  plays the music effct
	 */
	public void play(){
		if(!SettingsManager.isSoundOn) return;
		try{
			if(current != null){
				if(current.isPlaying()) return;
				current.play();
				Gdx.app.log("ME<", "played");
			}
		}catch(Exception ex){
			//ignore this MF!
			
		}
	}
	
	/*
	 *  pause the effect
	 */
	public void pause(){
		try{
			if(current!= null){
				current.pause();
			}
		}catch(Exception ex){
			//ignore this MF!
		}
	}
	/*
	 * resumes the effct
	 */
	public void resume(){
		try{
			if(current!= null && !current.isPlaying()){
				play();
			}
		}catch(Exception ex){
			//ignore this MF!
		}
	}
	
	/*
	 *  stops the effect music
	 */
	public void stop(){
		try{
			if(current!= null && current.isPlaying()){
				current.stop();
				current = null;
			}
		}catch(Exception ex){
			//ignore this MF!
		}
	}
	/*
	 *  checks if still playing
	 */
	public boolean isPlaying(){
		try{
			if(current != null){
				return current.isPlaying();
			}
		}catch(Exception ex){
			//ignore this MF!
		}
		return false;
	}
	
	/*
	 *  dispose the music class for effeciency
	 *  (non-Javadoc)
	 * @see com.badlogic.gdx.utils.Disposable#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		for(Map.Entry<MusicEffectType, Music> entry : cache.getSet()){
			try{
				((Music)entry.getValue()).dispose();
			}catch(Exception e){
				/*
				 *  if an exception is occured remove the entry
				 */
				Gdx.app.log("Music Effect Manager", entry.getKey() + "");
				cache.remove(entry.getKey());
			}
		}
		if(cache.getSet().size() > 0){
			cache.removeAll();
		}
		Gdx.app.log("MUSIC EFFECT MGR", "has beed disposed!");
	}


	
	/**
	 * @return the type
	 */
	public MusicEffectType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(MusicEffectType type) {
		this.type = type;
	}

	/*
	 * destroys the elder entry
	 * (non-Javadoc)
	 * @see com.teampora.utils.Cache.ElderRemoverListener#destroy(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean destroy(MusicEffectManager.MusicEffectType key,
			Music value) {
		// TODO Auto-generated method stub
		Music music = cache.remove(key);
		if(music != null){
			try{
				music.stop();
				music.dispose();
			}catch(Exception ex){
				/*
				 * ignore this MF!
				 */
			}
			return true;
		}
		return false;
	}
}
