package com.teampora.managers;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Stack;
import java.util.StringTokenizer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.teampora.game.GameState;
import com.teampora.managers.MusicEffectManager.MusicEffectType;
import com.teampora.utils.Assets;

public class SettingsManager {
	
	private final static String TITLE = "SneakySnatcher";
	private final static Preferences pref = Gdx.app.getPreferences(TITLE);
	
	private final static String NO_VALUE = "novalue";
	private final static String DONE = "done";
	private final static String FIRST_LAUNCH = "firstlaunch";
	
	public final static String MUSIC = "music";
	public final static String SOUND = "sound";
	public final static String HELP = "help";
	public final static String VERSION = "version";
	public final static String EMAIL = "email";
	
	
	public static boolean isSoundOn;
	public static boolean isMusicOn;
	public static boolean isHelpOn;
	
	public static boolean hasRetrieved = false;
	
	public static boolean soundSet = false;
	public static boolean musicSet = false;

	public static boolean isLiteVersion = false;
	
	public static int PROFILE_VERSION = 0;
	
	public static String EMAIL_ADDRESS = "";

	public static float chance = 0.0f;
	
	public static int tries = -1;
	
	public static int goal = 0;
	
	public static Color color = new Color(94/255f,20/255f,20/255f, 1f);
	
	public static String[] comboText = null;
	
	/*
	 *  special music to be used here
	 */
	public static MusicEffectType particles;
	
	public static String particle_path;
	
	private enum Type{
		String,
		Boolean,
		Integer
	}
	
	public enum FileType{
		Internal,
		External,
		Local
	}
	
	public enum MoveType{
		moveTo,
		copyTo
	}
	
	/*
	 *  copies the files base on the strFile 
	 *  creates the first filetype to move in to the second file type
	 */
	public static void copyFile(String strFile , FileType firstFileType, FileType secondFileType){
		FileHandle firstFileToMove = getFile(strFile , firstFileType);
		FileHandle secondFileToMoveIn = getFile(strFile , secondFileType);
		transferFile(firstFileToMove , secondFileToMoveIn , MoveType.copyTo);	
	}
	/*
	 *  moves the files base on the strFile 
	 *  creates the first filetype to move in to the second file type
	 */
	public static void moveFile(String strFile , FileType firstFileType, FileType secondFileType){
		FileHandle firstFileToMove = getFile(strFile , firstFileType);
		FileHandle secondFileToMoveIn = getFile(strFile , secondFileType);
		transferFile(firstFileToMove , secondFileToMoveIn , MoveType.moveTo);
	}
	
	
	public static void transferFile(FileHandle file_to_transfer , FileHandle file_to_transfer_in ,MoveType moveType ){
		if(moveType == MoveType.copyTo){
			file_to_transfer.copyTo(file_to_transfer_in);
		}else if(moveType == MoveType.moveTo){
			file_to_transfer.moveTo(file_to_transfer_in);
		}
	}
	

	
	public static FileHandle getFile(String strFile , FileType type){
		
		FileHandle file = null;
		
		if(type == FileType.Internal){
			try{
				file = Gdx.files.internal(strFile);
			}catch(Exception ex){
				//Gdx.files.internal(strFile).mk
			}
		}else if(type == FileType.External){
			try{
				file = Gdx.files.external(strFile);
			}catch(Exception ex){
			}
		}else if(type == FileType.Local){
			try{
				file = Gdx.files.local(strFile);
			}catch(Exception ex){
				
			}
		}
		
		return file;
	}
	/*
	 * point refers to what char
	 * what line number refers to putting characters[point] in every after that number
	 * 
	 */
	
	public static StringBuffer getFileText(String strFile , FileType type , char point , int everyCount){
		FileHandle file = getFile(strFile , type);
		if(file == null) return null;
		
		
		StringBuffer output = getLines(strFile , type);
		int counter = 0;

		StringTokenizer token = new StringTokenizer(output.toString(), " ");
		output = new StringBuffer();
		while(token.hasMoreElements()){
			counter++;
			output.append(token.nextToken()).append(" ");
			if(counter == everyCount){
				output.append(point);
				counter = 0;
			}
		}
		return output;
	}
	
	public static int getLineCount(String strFile , FileType type){
		FileHandle file = getFile(strFile , type);
		int no_lines = 0;
		if(file == null) return no_lines;
			@SuppressWarnings("unused")
			String line = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()));
				try {
					while((line = reader.readLine())!= null){
						no_lines++;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}finally{
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
		
		return no_lines;
	}
	
	public static StringBuffer getLines(String strFile , FileType type){
		StringBuffer string = new StringBuffer();
		FileHandle file = getFile(strFile , type);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()));
		
		String line = null;
		
		try {
			while((line = reader.readLine() ) != null){
				string.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return string;
	}
	/*
	 *  getting files by line numbers 
	 *  
	 */
	public static StringBuffer getFileByLineNo( String strFile , FileType type, int lineno){
		
		StringBuffer output = new StringBuffer();
		if(lineno > getLineCount(strFile , type)) return output.append("no sod").append(getLineCount(strFile , type));
		FileHandle file = getFile(strFile , type);
		BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()));
		
		String line = null;
		int counter = 0;
		
		
		try {
			while((line = reader.readLine() ) != null){
				if(++counter == lineno){
					output.append(line);
					break;
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		return output;
		
	}
	
	public static StringBuffer getFileByLineNoFixed(String strFile , FileType type, int lineNo){
		StringBuffer buffer = getFileByLineNo(strFile , type, lineNo);
		int  counter = 0;
		String[] tokens = buffer.toString().split("[ ]");
		buffer.setLength(0);
		for(String t : tokens){
			if((++counter % 4) == 0){
				buffer.append('\n');
			}
			buffer.append(t);	
			buffer.append(" ");
		}
		
		return buffer;
	}
	
	
	public static String getSayings(GameState state , String path){
		StringBuffer buffer = getFileText(path , FileType.Internal , '\n' , 4);
		if(buffer == null){
			return null;
		}
		
		String[] tokens = buffer.toString().split("[,]");
		if(state == GameState.Ready){
			MessageFormat format = new MessageFormat(tokens[0]);
			return format.format(new Object[]{Integer.valueOf(goal)});
		}else if(state == GameState.GameOver){
			return tokens[1];
		}else if(state == GameState.LevelEnd){
			return tokens[2];
		}else if(state == GameState.NextLevel){
			return tokens[2];
		}
		
		return buffer.toString();
	}
	
	public static String[] getParticlePath(){
		return new String[]{"data/particles/particle.p","data/particles"};
	}
	
	public static Object getPrefValue(String key , Type type){
		if(type == Type.String){
			return (Object)pref.getString(key, NO_VALUE);
		}
		else if(type == Type.Boolean){
			return (Object)pref.getBoolean(key, false);
		}else if(type == Type.Integer){
			return (Object)pref.getInteger(key , 0);
		}
		return null;
	}
	
	public static void setPrefValue(String key ,Object value){
		
		if(value instanceof String){
			pref.putString(key, String.valueOf(value));
		}
		else if(value instanceof Boolean){
			pref.putBoolean(key, (Boolean)value);
		}else if(value instanceof Integer){
			pref.putInteger(key, Integer.valueOf(String.valueOf(value)));
		}
		pref.flush();
	}
	
	public static boolean isFirstLaunch(){
		Object o = getPrefValue(FIRST_LAUNCH , Type.String);
		if(o==null) return false;
		if(o.toString().equals(NO_VALUE)){
			setPrefValue(FIRST_LAUNCH , DONE);
			return true;
		}
		return false;
	}
	
	

	/*
	 * returns the texture region of the given fileName
	 */
	
	public static TextureRegion getRegion(String fileName){
		return Assets.gameAtlas.findRegion(fileName);
	}
	
	public static void setEmail(String email){
		setPrefValue(EMAIL , email);
		
	}
	
	public static void setVersion(int version){
		setPrefValue(VERSION , version);
		PROFILE_VERSION = version;
	}
	
	public static void setMusic(boolean isOn){
		setPrefValue(MUSIC , isOn);
		isMusicOn = isOn;
	}
	
	public static void setSound(boolean isOn){
		setPrefValue(SOUND , isOn);
		isSoundOn = isOn;
	}
	
	public static void setHelp(boolean isOn){
		setPrefValue(HELP , isOn);
		isHelpOn = isOn;
	}
	
	public static String getEMAIL_ADDRESS(){
		return getPrefValue(EMAIL , Type.String).toString();
	}
	
	public static int getPROFILE_VERSION(){
		return Integer.parseInt(getPrefValue(VERSION , Type.Integer).toString());
	}
	
	public static boolean getMusic(){
		return Boolean.parseBoolean(getPrefValue(MUSIC , Type.Boolean).toString());
	}
	
	public static boolean getSound(){
		return Boolean.parseBoolean(getPrefValue(SOUND , Type.Boolean).toString());
	}
	
	public static boolean getHelp(){
		return Boolean.parseBoolean(getPrefValue(HELP , Type.Boolean).toString());
	}
	/*
	 *  text to number converts literal strings to currency numbers
	 *  char
	 */
	public static StringBuilder StringToNumber(StringBuilder number){
		Stack<String> stack = new Stack<String>();
		int noOfChar = number.toString().toCharArray().length;
		String text = number.toString();
		number.setLength(0);
		while(noOfChar > 2){
			stack.push(text.substring(noOfChar - 3 , noOfChar));
			
			noOfChar-=3;
			if(noOfChar > 0){
				stack.push(",");
			}
		}
		stack.push(text.substring(0, noOfChar));
		while(!stack.isEmpty()){
			number.append(stack.pop());
		}
		return number;
	}
	
		
	public static void save(){

		setVersion(PROFILE_VERSION);
		setMusic(isMusicOn);
		setSound(isSoundOn);
		setHelp(isHelpOn);
	}
	

	
}
