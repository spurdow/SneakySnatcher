package com.teampora.tools;

import com.badlogic.gdx.graphics.Color;
import com.teampora.managers.MusicEffectManager.MusicEffectType;
import com.teampora.managers.SettingsManager;
import com.teampora.managers.SettingsManager.FileType;

public class Tools {
	private int id;
	private CharSequence name;
	private Color color;	
	private String effects;
	private double price;
	private ToolType type;
	private boolean equipped;
	private String sound;
	
	public enum ToolType{
		Hammer,
		Gloves,
		CoinPack
	}

	

	public Tools(int id, CharSequence name, Color color, String effects,
			double price, ToolType type, boolean equipped, String sound) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
		this.effects = effects;
		this.price = price;
		this.type = type;
		this.equipped = equipped;
		this.sound = sound;
	}

	public Tools(){}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public CharSequence getName() {
		return name;
	}

	public void setName(CharSequence name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEffects() {
		return effects;
	}

	public void setEffects(String effects) {
		this.effects = effects;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ToolType getType() {
		return type;
	}

	public void setType(ToolType type) {
		this.type = type;
	}
	
	
	
	
	
	/**
	 * @return the equipped
	 */
	public boolean isEquipped() {
		return equipped;
	}

	/**
	 * @param equipped the equipped to set
	 */
	public void setEquipped(boolean equipped) {		
		this.equipped = equipped;
	}
	
	/**
	 * @return the sound
	 */
	public String getSound() {
		return sound;
	}

	/**
	 * @param sound the sound to set
	 */
	public void setSound(String sound) {
		this.sound = sound;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + " " + name + " " + ((color==null)?"color is null":"color not null") +  " " + effects + " " +price + " " + type;
	}




	public static class ToolBuilder{
		private Tools tool;
		private FileType type;
		private CharSequence filename;
		private int id;
		private ToolType toolType;
		
		public ToolBuilder(){}
		
		
		/*
		 *  Sets the tooltype 
		 *  this is indeed needed for upcasting the tool
		 */
		public ToolBuilder toolType(ToolType toolType){
			this.toolType = toolType;
			return this;
		}
		
		/*
		 *  sets the filetype if external or internal
		 *  useful because were going to port the internal file to external
		 *  so that we can write/change data
		 */
		public ToolBuilder fileType(FileType type){
			this.type = type;
			return this;
		}
		
		/*
		 *  sets the filename so we can trace it base on file type
		 */
		public ToolBuilder fileName(CharSequence filename){
			this.filename = filename;
			return this;
		}
		
		/*
		 *  use for searching the file using line no, specifically its id
		 */
		public ToolBuilder id(int id){
			this.id = id;
			return this;
		}
		
		
		
		/*
		 *  creates the tool
		 *  finding the appropriate number then upcast it to its specific tool
		 */
		private StringBuffer buffer = new StringBuffer();
		public ToolBuilder createTool(){
			
			buffer = SettingsManager.getFileByLineNo((String) filename, type, id);

			if(toolType == ToolType.Hammer){
				Object[] obj = buffer.toString().split("[,]");
				int id = Integer.parseInt(obj[0].toString());
				String name = obj[1].toString();
				Color c = getColor(obj[2].toString());
				String effects =  obj[3].toString();
				double price = Double.parseDouble(obj[4].toString());
				boolean equip = Boolean.parseBoolean(obj[5].toString());
				String filename = obj[6].toString();
				String sound = obj[7].toString();
				
				tool = new Hammer(id, name, c, effects, price ,toolType ,equip, filename , sound , getEffectType(sound));
			}else if(toolType == ToolType.Gloves){
				Object[] obj = buffer.toString().split("[,]");
				int id = Integer.parseInt(obj[0].toString());
				String name = obj[1].toString();
				Color c = getColor(obj[2].toString());
				String effects = obj[3].toString();
				double price = Double.parseDouble(obj[4].toString());
				float chance = Float.parseFloat(obj[5].toString());
				boolean equip = Boolean.parseBoolean(obj[6].toString());
				String filename = obj[7].toString();
				String sound = obj[8].toString();
				tool = new Gloves(id , name , c , effects, price, toolType ,equip, chance,filename ,sound );
			}
			
			
			return this;
		}
		
		/*
		 *  returns the created tool for buying/viewing purposes
		 *  use in tools screen class
		 */
		public Tools getCreatedTool(){
			return tool;
		}
		/*
		 *  helper method for creating the tool
		 */
		private Color getColor(String colorName){
			Color c = null;
			if(colorName.equals("white")){
				c = Color.WHITE;
			}else if(colorName.equals("blue")){
				c = Color.BLUE;
			}else if(colorName.equals("orange")){
				c = Color.ORANGE;
			}else if(colorName.equals("yellow")){
				c = Color.YELLOW;
			}else if(colorName.equals("black")){
				c = Color.BLACK;
			}else if(colorName.equals("brown")){
				c = new Color(94/255f,20/255f,20/255f, 1f);
			}else if(colorName.equals("light-gray")){
				c = Color.LIGHT_GRAY;
			}else if(colorName.equals("pink")){
				c = Color.PINK;
			}else if(colorName.equals("red")){
				c = Color.RED;
			}
			
			
			
			return c;
		}
		
		public MusicEffectType getEffectType(String sound){
			MusicEffectType type = null;
			if(sound.equals("wind")){
				type = MusicEffectType.SWING;
			}else if(sound.equals("bolt")){
				type = MusicEffectType.BOLT;
			}else if(sound.equals("fire")){
				type = MusicEffectType.FIRE;
			}else if(sound.equals("gold")){
				type = MusicEffectType.GOLDEN;
			}else if(sound.equals("ice")){
				type = MusicEffectType.ICE;
			}
			
			return type;
		}
		
	}
	
	
}
