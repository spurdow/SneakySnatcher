package com.teampora.buttons;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.teampora.managers.MusicManager;
import com.teampora.managers.SettingsManager;
import com.teampora.screens.AbstractScreen;
import com.teampora.utils.Assets;

public class SettingsButton extends Button{
	
	public enum SettingsButtonType{
		Help,
		Sound,
		Music
	}
	
	private SettingsButtonType type;
	
	private AbstractScreen holder;

	public SettingsButton(Skin skin, String styleName , SettingsButtonType types , AbstractScreen holder) {
		super(skin, styleName);
		// TODO Auto-generated constructor stub
		this.type = types;
		this.holder = holder;
		
		if(type == SettingsButtonType.Help){
			setChecked(!SettingsManager.isHelpOn);
		}
		else if(type == SettingsButtonType.Music){
			setChecked(!SettingsManager.isMusicOn);
		}
		else if(type == SettingsButtonType.Sound){
			setChecked(!SettingsManager.isSoundOn);
		}
		
		addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
					toggleSettings();
					super.clicked(event, x, y);
					
			}
			
		});
	}
	
	public void toggleSettings(){
		if(type == SettingsButtonType.Help){
			SettingsManager.isHelpOn = !SettingsManager.isHelpOn;
		}
		else if(type == SettingsButtonType.Music){
			SettingsManager.isMusicOn = !SettingsManager.isMusicOn;
			if(SettingsManager.isMusicOn) {
				if(!MusicManager.getInst().isPlaying()){
					MusicManager.getInst().play();
				}
			}
			else{
				if(MusicManager.getInst().isPlaying()){
					MusicManager.getInst().stop();
				}
			}
		}
		else if(type == SettingsButtonType.Sound){
			SettingsManager.isSoundOn = !SettingsManager.isSoundOn;
		}
		
		Assets.click.play();
	}

	
	
	
	
	
	
	

}
