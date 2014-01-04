package com.teampora.tools;

import com.teampora.managers.SettingsManager;
import com.teampora.managers.SettingsManager.FileType;

public class Scores {
	private int score;
	private String name;
	public Scores(int score, String name) {
		super();
		this.score = score;
		this.name = name;
	}
	
	public Scores(){}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "SCORE => "+ score + " NAME=> " + name;
	}
	
	
	public static class Builder{
		private Scores score;
		private String fileName;
		private FileType type;
		
		public Builder(){
			this.score = new Scores();
		}
		
		public Builder setFileName(String fName){
			this.fileName = fName;
			return this;
		}
		
		public Builder setFileType(FileType type){
			this.type = type;
			return this;
		}
		
		private StringBuffer buffer = new StringBuffer();
		public Scores build(int i ){
			buffer = SettingsManager.getFileByLineNo(fileName, type, i);
			
			Object[] obj = buffer.toString().split("[,]");
			String topLevel = obj[0].toString();
			int topScore = Integer.parseInt((obj[1].toString()));
			score.setName(topLevel);
			score.setScore(topScore);
			return score;
		}
		
	}
	
	
}
