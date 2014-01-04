package com.teampora.groups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.teampora.credits.Credits;
import com.teampora.profile.Profile;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class CreditBox extends GameBox{

	private final Label fullName;
	private final Label position;
	
	private final Image imageIcon;
	
	private final Credits credit;
	
	private final float MAX_WIDTH = 136;

	private float newWidth = 1.3f;
	
	public CreditBox(Credits credit_, Profile profile_){
		super(new NinePatchDrawable(Assets.orange_gradient));
		this.credit = credit_;
		imageIcon = new Image(Assets.fruit_coin);
		fullName = new Label(credit.getFirstName().concat(" ").concat(credit.getLastName()) , Assets.skin, "lfont",Color.BLACK);
		position = new Label(credit.getPosition() , Assets.skin , "mfont" , Color.WHITE);
		
		
		initUI();
	}
	
	private void initUI(){
		/*
		 *  checks the boundaries of the text if exceeds the default
		 *  will perform calculate another addition width so that coins 
		 *  that would go more or eq to 1 b the background will resize 
		 *  to fit exceeding numbers
		 */
		 if(fullName.getTextBounds().width > position.getTextBounds().width){
			if(fullName.getTextBounds().width > MAX_WIDTH){
				newWidth+= calculateWidth(fullName.getTextBounds().width);
			}
		 }else{
			if(position.getTextBounds().width > MAX_WIDTH){
				newWidth+=calculateWidth(position.getTextBounds().width) ;
			}
		 }
		
		background.setBounds(getX(), getY(), fullName.getTextBounds().width + newWidth + 10f , fullName.getTextBounds().height * 4f);
		//addActor(background);
		
		imageIcon.setBounds(getX() + (Utils.WIDTH_SMALL() / 3f), getY() + (background.getHeight() / 2) - (Utils.HEIGHT_SMALL() / 6) , Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
		//addActor(imageIcon);
		
		fullName.setPosition((background.getWidth() / 2) - (fullName.getTextBounds().width / 2) ,(background.getHeight() / 2) - (fullName.getTextBounds().height / 2)  );
		addActor(fullName);
	
		position.setPosition(fullName.getX(), fullName.getY() / 2);
		addActor(position);
		
		setBounds(getX(), getY() , background.getWidth() , background.getHeight());
	}
	
	private float calculateWidth(float thatWidth){
		
		float width = thatWidth - 136;
		float newWidth = 0f;
		while(width > 20){
			width-=20;
			newWidth+=0.13f;
		}
		
		return newWidth;
	}
}
