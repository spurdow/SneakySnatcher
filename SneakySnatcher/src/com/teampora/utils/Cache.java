package com.teampora.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.gdx.Gdx;

public class Cache<K , V> {

	public interface ElderRemoverListener<K , V>{
		/*
		 *  destroyes the eldest 
		 */
		boolean destroy(K key , V value);
	}
	
	/*
	 *  the remove listener for the class eg. Sound Manager
	 *  to implement to
	 */
	private ElderRemoverListener<K , V> removeListener;
	
	/*
	 *  the storage for least recently used eg. sounds
	 */
	private final  LinkedHashMap<K, V> linkMap;
	
	
	/*
	 *  constructor that accepts max map size
	 */
	
	@SuppressWarnings("serial")
	public Cache(final int max){
		
		linkMap = new LinkedHashMap<K, V>(max + 1 , 0.75f, true){

			/* (non-Javadoc)
			 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
			 */
			@Override
			protected boolean removeEldestEntry(Entry<K, V> eldest) {
				// TODO Auto-generated method stub
				if(size() > max ){
					return removeListener.destroy(eldest.getKey(), eldest.getValue());
				}
				return false;
			}
			
			
			
		};
		
		
	}
	/*
	 *  insert key and value at first index
	 */
	public void insert(K key, V value){
		if(linkMap != null){
			linkMap.put(key, value);
		}
	}
	
	/*
	 *  
	 *  returns the specified key value pair 
	 */
	public V get(K key){
		return linkMap.get(key);
	}
	
	
	
	/*
	 *  implmenting a remove key for disposal
	 *  
	 */
	public V remove(K key){
		V value = linkMap.remove(key);
		return value;
	}
	
	/*
	 * entry set returner
	 */
	public Set<Map.Entry<K , V>> getSet(){
		return linkMap.entrySet();
	}
	
	/*
	 *  remove all K,V
	 */
	public void removeAll(){
		linkMap.clear();
	}
	
	/*
	 *  set the remove listener, @destroy for the implementing class
	 */
	public void setRemoverListener(ElderRemoverListener<K , V> lsitener){
		this.removeListener = lsitener;
	}
	
}
