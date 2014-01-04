package com.teampora.adventure;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.teampora.managers.SettingsManager;
import com.teampora.managers.SettingsManager.FileType;

public class MapSelection {
	private String mapName;
	private ArrayList<Level> levels;
	private String fileName;
	private boolean choosen = false;
	private String buttonName;
	private Color color;
	private int choosenLevel;
	
	public MapSelection(){
		levels = new ArrayList<Level>(90);
	}
	
	public MapSelection(String mapName, ArrayList<Level> levels) {
		super();
		this.mapName = mapName;
		this.levels = levels;
	}
	/**
	 * @return the mapName
	 */
	public String getMapName() {
		return mapName;
	}
	/**
	 * @param mapName the mapName to set
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	/**
	 * @return the levels
	 */
	public ArrayList<Level> getLevels() {
		return levels;
	}
	/**
	 * @param levels the levels to set
	 */
	public void setLevels(ArrayList<Level> levels) {
		this.levels = levels;
	}
	
	/**
	 * returns the number of levels reached
	 */
	public int getLevelReached(){
		int reach = 0;
		
		for(int i = 0 ; i < levels.size() ; i++){
			if(levels.get(i).isLocked())break;
			reach++;
		}
		Gdx.app.log("Reached ", reach+"");
		return reach;
	}
	/**
	 *  returns the max levels
	 */
	public int getMaxLevels(){
		return levels.size();
	}
	
	
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	

	/**
	 * @return the buttonName
	 */
	public String getButtonName() {
		return buttonName;
	}

	/**
	 * @param buttonName the buttonName to set
	 */
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	
	

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the choosen
	 */
	public boolean isChoosen() {
		return choosen;
	}

	/**
	 * @param choosen the choosen to set
	 */
	public void setChoosen(boolean choosen) {
		this.choosen = choosen;
	}
	
	

	/**
	 * @return the choosenLevel
	 */
	public int getChoosenLevel() {
		return choosenLevel;
	}

	/**
	 * @param choosenLevel the choosenLevel to set
	 */
	public void setChoosenLevel(int choosenLevel) {
		this.choosenLevel = choosenLevel;
	}

	public String toString(){
		
		String returnString = mapName + "\n";
		for(int i = 0 ; i < levels.size(); i++){
			returnString+= levels.toString() + "\n";
		}
		return returnString;
	}
	
	/*
	 *  builder patter for laziness 
	 */
	public static class Builder{
		private MapSelection map;
		private FileType fileType;
		private String fileName;
		private int id;
		
		public Builder(){
			map = new MapSelection();
		}
		
		/*
		 *  sets the file type to fetch the file
		 */
		public Builder fileType(FileType fileType){
			this.fileType = fileType;
			return this;
		}
		/*
		 * sets the path 
		 */
		public Builder fileName(String fileName){
			this.fileName = fileName;
			return this;
		}
		/*
		 * sets the id
		 */
		public Builder id(int id){
			this.id = id;
			return this;
		}
		private StringBuffer buffer = new StringBuffer();
		public MapSelection build(){
			/*
			 *  the map buffer
			 */
			buffer = SettingsManager.getFileByLineNo(fileName, fileType, id);
			Object[] obj = buffer.toString().split("[,]");
			String mapName = obj[0].toString();
			String fileName = obj[1].toString();
			String btnName = obj[2].toString();
			Color c = getColor(obj[3].toString());
			map.setMapName(mapName);
			map.setFileName(fileName);
			map.setButtonName(btnName);
			map.setColor(c);
			int levels = SettingsManager.getLineCount("data/levels.txt", FileType.Internal);
			for(int i = 1 ; i <= levels ; i++){
				buffer.setLength(0);
				buffer = SettingsManager.getFileByLineNo("data/levels.txt", FileType.Internal, i);
				obj = buffer.toString().split("[,]");
				int starsAcc = Integer.parseInt(obj[0].toString());
				boolean isLock = Boolean.parseBoolean(obj[1].toString());
				int maxLevels = Integer.parseInt(obj[2].toString());
				map.levels.add(new Level(starsAcc,  isLock , maxLevels));
			}
			
			return map;
		}
		
		private Color getColor(String cName){
			if(cName.equals("gray")){
				return Color.GRAY;
			}else if(cName.equals("brown")){
				return Color.ORANGE;
			}else if(cName.equals("green")){
				return Color.GREEN;
			}else
				return Color.MAGENTA;
		}
		
	}
	
}
