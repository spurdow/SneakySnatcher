package com.teampora.tools;

import com.badlogic.gdx.graphics.Color;
import com.teampora.managers.MusicEffectManager.MusicEffectType;

public class Hammer extends Tools{
	
	private String fileName;

	private MusicEffectType swingType;
	
	public Hammer(int id, CharSequence name, Color color, String effects,
			double price, ToolType type, boolean equipped , String fileName, String sound ,MusicEffectType swingType) {
		super(id, name, color, effects, price, type, equipped, sound);
		// TODO Auto-generated constructor stub
		this.fileName = fileName;
		this.swingType = swingType;
	}

	public Hammer(){}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

	/**
	 * @return the swingType
	 */
	public MusicEffectType getSwingType() {
		return swingType;
	}

	/**
	 * @param swingType the swingType to set
	 */
	public void setSwingType(MusicEffectType swingType) {
		this.swingType = swingType;
	}

	/* (non-Javadoc)
	 * @see com.teampora.tools.Tools#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + " " + fileName;
	}
	
	
	
}
