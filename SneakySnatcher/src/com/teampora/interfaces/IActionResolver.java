package com.teampora.interfaces;

import java.util.ArrayList;

public interface IActionResolver {

	
	String retrieveProfile();
	
	/*
	 *  android's toast
	 */
	void toast(String message);
	
	/*
	 * retrieves the saved data
	 */
	void retrieve(ArrayList<String> emails);
	
	/*
	 *  saves the updated data
	 */
	void save(ArrayList<String> emails, String json);
	
	
	/*
	 * gets all emails accounts
	 */
	ArrayList<String> emails();
	/*
	 *  check internet connection
	 */

	boolean checkConnection();
	
	/*
	 *  buy premium
	 */
	void buyPremium();
	/*
	 *  show confirm exit
	 */
	void exit();
}
