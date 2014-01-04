package com.teampora.tools;

import com.badlogic.gdx.graphics.Color;

public class Gloves extends Tools{

	private float chance;
	private String fileName;
	
	public Gloves(){
		super();
	}
	
	

	public Gloves(int id, CharSequence name, Color color, String effects,
			double price, ToolType type, boolean equipped , float chance, String fileName , String sound) {
		super(id, name, color, effects, price, type, equipped , sound);
		// TODO Auto-generated constructor stub
		this.chance = chance;
		this.fileName = fileName;
	}

	/**
	 * @return the chance
	 */
	public float getChance() {
		return chance;
	}



	/**
	 * @param chance the chance to set
	 */
	public void setChance(float chance) {
		this.chance = chance;
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



	/* (non-Javadoc)
	 * @see com.teampora.tools.Tools#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + " " + chance;
	}

	
	
}
