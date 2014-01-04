package com.teampora.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.teampora.utils.Utils;

public class MusicManager implements Disposable{

	/*
	 *  enumeration for music
	 */
	public enum MusicType{
		HAMMER(Utils.MUSIC_PATH + "hammer_swing.ogg"),
		INGAME(Utils.MUSIC_PATH + "inGameBGMusic.ogg"),
		MAP(Utils.MUSIC_PATH + "mapBGMusic.ogg"),
		MENU(Utils.MUSIC_PATH+"menuBGMusic.ogg"),
		TOOLS(Utils.MUSIC_PATH+"toolsBGMusic.ogg"),
		;		
		private final String strName;
		private MusicType(String strName){
			this.strName = strName;
		}
		
		public String getFileName(){
			return strName;
		}
	}
	
	
	/*
	 *  the music manager instance
	 */
	private static MusicManager instance;
	
	/*
	 *  the music file to be returned when using in each screens
	 */
	private Music music;
	
	/*
	 *  the isntance for singleton pattern
	 */
	private MusicManager(){}
	
	/*
	 *  Return the music manager instance
	 *  singleton pattern for ease of use anywhere in screen
	 *  possibly one could determine where to put it, possible in Game class
	 *  but for simplicity reason  
	 */
	public static MusicManager getInst(){
		/*
		 *  if instance null then return new Manager
		 */
		if(instance == null){
			instance = new MusicManager();
		}
		
		/*
		 *  instance beeing returned
		 */
		return instance;
	}
	
	/*
	 *  returns the music using the file name
	 */
	protected Music getMusic(String fileName){
		return getMusic(Gdx.files.internal(fileName));
	}
	
	/*
	 *  returns the music using the file handle
	 */
	protected Music getMusic(FileHandle handle){
		return Gdx.audio.newMusic(handle);
		
	}
	
	/*
	 *  creates a new music 
	 *  @param fileName, volume, looping
	 */
	public void createNew(String fileName , float volume, boolean looping ){
		if(music != null){
			stop();
			music.dispose();
		}
			try{
				music = getMusic(fileName);
				music.setLooping(looping);
				music.setVolume(volume);
			}catch(Exception ex){
				music = getMusic(fileName);
			}
		
		
	}
	
	public void createNew(String fileName , boolean play){
		createNew(fileName);
		if(play){
			play();
		}
	}
	
	public void createNew(MusicType type , boolean play){
		createNew(type.getFileName() , play);
	}
	
	/*
	 * musuc tyoe create new
	 */
	public void createNew(MusicType type){
		createNew(type.getFileName() , true);
	}

	/*
	 *  creates a new muisc
	 *  @param fileName
	 *  default vol, looping
	 *  then plays it
	 */
	public void createNew(String fileName){
		createNew(fileName, 1.0f, true);
	}
	
	
	
	/*
	 *  helper meth isPlaying
	 */
	public boolean isPlaying(){
		try{
			if(music != null){
				return music.isPlaying();
			}
		}catch(Exception ex){
			//ignore this MF!
		}
		return false;
	}
	
	public boolean isLooping(){
		if(music != null){
			return music.isLooping();
		}
		return false;
	}
	
	/*
	 *  resumes music 
	 */
	public void resume(){
		if(music != null && !music.isPlaying()){
			play();
		}
	}
	
	/*
	 *  stops music instance
	 */
	public void stop(){
		try{
			if(music != null){
				music.stop();
			}
		}catch(Exception e){
			Gdx.app.log("MUSIC MANAGER", "Exception Occured" + e.toString());
		}
		Gdx.app.log("MUSIC MANAGER", "stop been called");
	}
	
	/*
	 *  pause music
	 */
	public void pause(){
		if(music != null ){
			try{
				music.pause();
			}catch(Exception e){
				Gdx.app.log("MUSIC MANAGER", "Exception Occured" + e.toString());
				/*
				 * occurs when in splash screen users automatically presses the back key press
				 */
			}
		}
	}
	
	/*
	 *  play music instance
	 */
	public void play(){
		if(!SettingsManager.isMusicOn) return;
		if(music != null && SettingsManager.isMusicOn){
			music.play();
		}
	}
	
	/*
	 * dispose unusable music for resource efficiency
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.utils.Disposable#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		try{
			if(music!=null){
				if( music.isLooping()){
					music.setLooping(false);
				}
				if( music.isPlaying()){
					stop();
				}
				music.dispose();
			}
		}catch(Exception ex){
			/*
			 * exception fired when loading all data at the same time disposing
			 */
		}
		
		Gdx.app.log("MUISC MANAGER", "music manager dispose");
	}
	
	
}
