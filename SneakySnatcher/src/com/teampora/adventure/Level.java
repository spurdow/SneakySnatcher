package com.teampora.adventure;

public class Level {
	private int starsAccumulated;
	private boolean isLocked;
	private int maxFruits;
	
	public Level(){}
	
	public Level(int starsAccumulated, boolean isLocked, int maxFruits) {
		super();
		this.starsAccumulated = starsAccumulated;
		this.isLocked = isLocked;
		this.maxFruits = maxFruits;
	}
	/**
	 * @return the starsAccumulated
	 */
	public int getStarsAccumulated() {
		return starsAccumulated;
	}
	/**
	 * @param starsAccumulated the starsAccumulated to set
	 */
	public void setStarsAccumulated(int starsAccumulated) {
		this.starsAccumulated = starsAccumulated;
	}
	/**
	 * @return the isLocked
	 */
	public boolean isLocked() {
		return isLocked;
	}
	/**
	 * @param isLocked the isLocked to set
	 */
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	/**
	 * @return the maxFruits
	 */
	public int getMaxFruits() {
		return maxFruits;
	}
	/**
	 * @param maxFruits the maxFruits to set
	 */
	public void setMaxFruits(int maxFruits) {
		this.maxFruits = maxFruits;
	}
	
	
	public String toString(){
		return starsAccumulated + " " + isLocked + " " + maxFruits ;
	}
	
	
	
}
