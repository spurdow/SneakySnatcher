package com.teampora.credits;

import com.teampora.managers.SettingsManager;
import com.teampora.managers.SettingsManager.FileType;

public class Credits {
	private String firstName;
	private String lastName;
	private String position;
	public Credits(String firstName, String lastName, String position) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
	}
	
	public Credits(){}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return firstName + " " + lastName + " " + position;
	}
	
	
	public static class Builder{
		private Credits credit;
		private String fileName;
		private FileType type;
		public Builder(){
			credit = new Credits();
		}
		
		public Builder filename(String fileName){
			this.fileName = fileName;
			return this;
		}
		
		public Builder filetype(FileType type){
			this.type = type;
			return this;
		}
		
		/*
		 *  check if filename and type is null
		 *  and also check if line count > @param i
		 *  laziness dont code
		 */
		public Credits build(int i){
			StringBuffer buffer = null;
			buffer = SettingsManager.getFileByLineNo(fileName, type, i);
			Object[] obj = buffer.toString().split("[,]");
			String firstName = obj[0].toString();
			String lastName = obj[1].toString()+"";
			String position = obj[2].toString();
			credit.setFirstName(firstName);
			credit.setLastName(lastName);
			credit.setPosition(position);			
			return credit;
		}
	}
	
}
