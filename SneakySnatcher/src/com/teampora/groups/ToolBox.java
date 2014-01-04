package com.teampora.groups;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.teampora.buttons.SneakyTextButton;
import com.teampora.game.GameTouch;
import com.teampora.managers.SettingsManager;
import com.teampora.profile.Profile;
import com.teampora.screens.AbstractScreen.ShowType;
import com.teampora.screens.ToolsScreen;
import com.teampora.tools.Gloves;
import com.teampora.tools.Hammer;
import com.teampora.tools.Tools;
import com.teampora.tools.Tools.ToolType;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class ToolBox extends GameBox{

	   private final Label labelName;
	   private final Label labelCoins;
	   private final Label labelDesc;
	   private Label chance;
	   private TextButton controlButton;
	   private Tools tool;
	   private Profile profile;
	   private ToolsScreen holder;
	   public ToolType type;
	   
	   public ToolBox(Hammer tool_ , Profile prof , ToolsScreen holder_) {
		  super(new NinePatchDrawable(Assets.orange_gradient));
		  this.tool = tool_; 		 
		  this.profile = prof;
		  this.holder = holder_;
		  this.type = tool_.getType();
		  
	      background.setBounds(getX(), getY(), Utils.WIDTH_LARGE(), Utils.HEIGHT_NORMAL());
	      addActor(background);
	      
	      Image icon = new Image(Assets.gameAtlas.findRegion(((Hammer)tool).getFileName()));
	      icon.setBounds(getX() + 1.5f , getY() + 1.5f, Gdx.graphics.getWidth() * 0.09f - 3f , background.getHeight() - 3f);
	      addActor(icon);
	      
	      labelName = new Label(this.tool.getName(), Assets.skin, "mfont" , Color.BLACK );
	      labelName.setPosition(getX() + icon.getWidth() , (getY() + background.getHeight()/2));
	      labelName.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
	      addActor(labelName);
	      
	      Image coinIcon = new Image(Assets.fruit_coin);
	      coinIcon.setBounds(getX()+icon.getWidth() ,getY() + labelName.getY() - labelName.getTextBounds().height * 1.8f , Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
	      addActor(coinIcon);
	      
	      labelCoins = new Label(this.tool.getPrice()+"", Assets.skin, "medium_font");
	      labelCoins.setPosition(coinIcon.getX()+ coinIcon.getWidth(), getY() + coinIcon.getY());
	      labelCoins.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
	      addActor(labelCoins);
	      
	      labelDesc = new Label(this.tool.getId()+"", Assets.skin, "small_font");
	      labelDesc.setPosition(getX()+8, getY()+2);
	      labelDesc.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
	      //addActor(labelDesc);
	      String textButton = null;
	      if(tool.getPrice() <= 0){
	    	  textButton = tool.isEquipped()? "Used" : "Use";
	      }else{
	    	  textButton = "Buy";
	      }
	      controlButton = new SneakyTextButton(textButton, Assets.skin , "lfont" , Color.LIGHT_GRAY);
	      controlButton.setSize(Utils.WIDTH_SMALL()*2, Utils.HEIGHT_NORMAL());
	      controlButton.setPosition(getX() + background.getWidth() - controlButton.getWidth() , getY());
	      
	      
	      /*
	       *  action to control button depends on text
	       */
	      
	      controlButton.addListener(new ClickListener(){

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				if(controlButton.getText().toString().equals("Buy")){
					if(profile.canBuy((long)tool.getPrice())){
						/*
						 *  1. get the hammer
						 *  2. remove the hammer from the list
						 *  3. add the hammer to the list from 1st step the same index as step 2.
						 *  4. set the price to 0 of step 3.
						 *  5. change the text of the button
						 */
						

						Hammer hammer = profile.getHammer(tool.getId());
						profile.getHammers().remove(tool.getId() - 1);
						profile.getHammers().add(tool.getId()-1, hammer);
						/*
						 *  holder should be first
						 */
						holder.setCoinBox(profile.getCoins() - (long)tool.getPrice());
						profile.setCoins(profile.getCoins() - (long)tool.getPrice());
						
						profile.getHammer(tool.getId()).setPrice(0);
						labelCoins.setText(tool.getPrice()+"");
						controlButton.setText("Use");
						Assets.buy.play();
					}else{
						/*
						 * 	cant buy  enough money
						 *  create dialog message that users cant buy 
						 */
						//if(holder.getGame().resolver != null)
						//	holder.getGame().resolver.showPayPal(0.99f);
						holder.show("You don't have enough \n coins to purchase!", Color.RED, 1f, ShowType.TOP);
					}
					
					super.clicked(event, x, y);
				}else if(controlButton.getText().toString().equals("Use")){
					
					profile.revertHammer(profile.getHammers());
					profile.getHammer(tool.getId()).setEquipped(true);
					holder.revertTools(tool.getType(), "Use");
					controlButton.setText("Used");
					SettingsManager.color = profile.getEquippedHammer().getColor();
					SettingsManager.color.set(SettingsManager.color.r, SettingsManager.color.g, SettingsManager.color.b, GameTouch.alpha);
					SettingsManager.particles = profile.getEquippedHammer().getSwingType();
					SettingsManager.particle_path = profile.getEquippedHammer().getSound().concat("-particle.p");
					Gdx.app.log("tag", SettingsManager.particle_path);
					holder.getTouch().load("data/particles/particle.p", "data/particles");
					super.clicked(event, x, y);
				}
			}
	    	  
	      });
	      
	      addActor(controlButton);
	      setBounds(getX(), getY(), background.getWidth(), background.getHeight());
	   }

	   public ToolBox(Gloves tool_ , Profile prof , ToolsScreen holder_) {
		  super(new NinePatchDrawable(Assets.orange_gradient));
		  this.tool = tool_; 		 
		  this.profile = prof;
		  this.holder = holder_;
		  this.type = tool_.getType();
		  background.setBounds(getX(), getY(), Utils.WIDTH_LARGE(), Utils.HEIGHT_NORMAL());
	      addActor(background);
	      
	      Image icon = new Image(Assets.gameAtlas.findRegion(((Gloves)tool).getFileName()));
	      icon.setBounds(getX() + 1.5f , getY() + 1.5f, Gdx.graphics.getWidth() * 0.09f - 3f , background.getHeight() - 3f);
	      addActor(icon);
	      
	      labelName = new Label(this.tool.getName(), Assets.skin, "mfont" , Color.BLACK );
	      labelName.setPosition(getX() + icon.getWidth() , (getY() + background.getHeight()/2));
	      labelName.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
	      addActor(labelName);
	      
	      Image coinIcon = new Image(Assets.fruit_coin);
	      coinIcon.setBounds(getX()+icon.getWidth() ,getY() + labelName.getY() - labelName.getTextBounds().height * 1.8f , Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
	      addActor(coinIcon);
	      
	      labelCoins = new Label(this.tool.getPrice()+"", Assets.skin, "medium_font");
	      labelCoins.setPosition(coinIcon.getX()+ coinIcon.getWidth(), getY() + coinIcon.getY());
	      labelCoins.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
	      addActor(labelCoins);

	      
	      labelDesc = new Label(this.tool.getId()+"", Assets.skin, "small_font");
	      labelDesc.setPosition(getX()+8, getY()+2);
	      labelDesc.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
	      //addActor(labelDesc);
	      
	      chance = new Label(((int)((Gloves)this.tool).getChance() +" % lucky"), Assets.skin,"small_font" );
	      chance.setSize(labelDesc.getWidth(), labelDesc.getHeight());
	      chance.setPosition(labelName.getX() + labelName.getTextBounds().width, labelName.getY() - 2f);
	      //chance.setSize(labelDesc.getWidth(), labelDesc.getHeight());
	      addActor(chance);
	      
	      String textButton = null;
	      if(tool.getPrice() <= 0){
	    	  textButton = tool.isEquipped()? "Used" : "Use";
	      }else{
	    	  textButton = "Buy";
	      }
	      controlButton = new SneakyTextButton(textButton, Assets.skin , "lfont" , Color.LIGHT_GRAY);
	      controlButton.setSize(Utils.WIDTH_SMALL()*2, Utils.HEIGHT_NORMAL());
	      controlButton.setPosition(getX() + background.getWidth() - controlButton.getWidth() , getY());
	      
	      
	      /*
	       *  action to control button depends on text
	       */
	      
	      controlButton.addListener(new ClickListener(){

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				if(controlButton.getText().toString().equals("Buy")){
					if(profile.canBuy((long)tool.getPrice())){
						/*
						 *  1. get the hammer
						 *  2. remove the hammer from the list
						 *  3. add the hammer to the list from 1st step the same index as step 2.
						 *  4. set the price to 0 of step 3.
						 *  5. change the text of the button
						 */
						
						Gloves glove = profile.getGloves(tool.getId());
						profile.getGloves().remove(tool.getId() - 1);
						profile.getGloves().add(tool.getId()-1, glove);
						holder.setCoinBox(profile.getCoins() - (long)tool.getPrice());
						profile.setCoins(profile.getCoins() - (long)tool.getPrice());
						profile.getGloves(tool.getId()).setPrice(0);
						labelCoins.setText(tool.getPrice()+"");
						controlButton.setText("Use");
						Assets.buy.play();
					}else{
						/*
						 * 	cant buy  enough money
						 *  create dialog message that users cant buy 
						 */
						holder.show("You don't have enough \n coins to purchase!", Color.RED, 1f, ShowType.TOP);
						
					}
					
					super.clicked(event, x, y);
				}else if(controlButton.getText().toString().equals("Use")){
					
					profile.revertGloves(profile.getGloves());
					profile.getGloves(tool.getId()).setEquipped(true);
					holder.revertTools(tool.getType(), "Use");
					controlButton.setText("Used");
					super.clicked(event, x, y);
				}
			}
	    	  
	      });
	      
	      addActor(controlButton);
	      setBounds(getX(), getY(), background.getWidth(), background.getHeight());
	   }
	   
	   
	   public ToolBox(Tools tool_ , Profile prof , ToolsScreen holder_) {
			  super(new NinePatchDrawable(Assets.orange_gradient));
			  this.tool = tool_; 		 
			  this.profile = prof;
			  this.holder = holder_;
			  this.type = tool_.getType();
			  
			  background.setBounds(getX(), getY(), Utils.WIDTH_LARGE(), Utils.HEIGHT_LARGE());
		      addActor(background);
		      
		      Image icon = new Image(Assets.gameAtlas.findRegion(((Hammer)tool).getFileName()));
		      icon.setBounds(getX()+Utils.WIDTH_NORMAL() / 3 , getY()+Utils.HEIGHT_NORMAL(), Gdx.graphics.getWidth() * 0.09f, Gdx.graphics.getHeight() * 0.2f);
		      addActor(icon);
		      
		      labelName = new Label(this.tool.getName(), Assets.skin, "mfont" , Color.BLACK );
		      labelName.setPosition((getX() + background.getWidth() /2.5f) , (getY() + background.getHeight()/2));
		      labelName.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
		      addActor(labelName);
		      
		      Image coinIcon = new Image(Assets.fruit_coin);
		      coinIcon.setBounds(getX()+Utils.WIDTH_NORMAL() , getY()+Utils.HEIGHT_NORMAL(), Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
		      addActor(coinIcon);
		      
		      labelCoins = new Label(this.tool.getPrice()+"", Assets.skin, "medium_font");
		      labelCoins.setPosition(getX()+Utils.WIDTH_NORMAL()+coinIcon.getWidth(), coinIcon.getY());
		      labelCoins.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
		      addActor(labelCoins);
		      
		      labelDesc = new Label(this.tool.getId()+"", Assets.skin, "small_font");
		      labelDesc.setPosition(getX()+8, getY()+2);
		      labelDesc.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
		      addActor(labelDesc);
		      String textButton = null;
		      if(tool.getPrice() <= 0){
		    	  textButton = tool.isEquipped()? "Used" : "Use";
		      }else{
		    	  textButton = "Buy";
		      }
		      controlButton = new SneakyTextButton(textButton, Assets.skin , "lfont" , Color.LIGHT_GRAY);
		      controlButton.setPosition(getX() + (this.getWidth() - Utils.WIDTH_SMALL()+Utils.WIDTH_SMALL()) , getY());
		      controlButton.setSize(Utils.WIDTH_SMALL()*2, Utils.HEIGHT_NORMAL());
		      
		      /*
		       *  action to control button depends on text
		       */
		      
		      controlButton.addListener(new ClickListener(){

				/* (non-Javadoc)
				 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
				 */
				@Override
				public void clicked(InputEvent event, float x, float y) {
					// TODO Auto-generated method stub
					if(controlButton.getText().toString().equals("Buy")){
						if(profile.canBuy((long)tool.getPrice())){
							/*
							 *  1. get the hammer
							 *  2. remove the hammer from the list
							 *  3. add the hammer to the list from 1st step the same index as step 2.
							 *  4. set the price to 0 of step 3.
							 *  5. change the text of the button
							 */
							

							Hammer hammer = profile.getHammer(tool.getId());
							profile.getHammers().remove(tool.getId() - 1);
							profile.getHammers().add(tool.getId()-1, hammer);
							/*
							 *  holder should be first
							 */
							holder.setCoinBox(profile.getCoins() - (long)tool.getPrice());
							profile.setCoins(profile.getCoins() - (long)tool.getPrice());
							
							profile.getHammer(tool.getId()).setPrice(0);
							labelCoins.setText(tool.getPrice()+"");
							controlButton.setText("Use");
							Assets.buy.play();
						}else{
							/*
							 * 	cant buy  enough money
							 *  create dialog message that users cant buy 
							 */
							//if(holder.getGame().resolver != null)
							//	holder.getGame().resolver.showPayPal(0.99f);
							holder.show("You don't have enough \n coins to purchase!", Color.RED, 1f, ShowType.TOP);
						}
						
						super.clicked(event, x, y);
					}else if(controlButton.getText().toString().equals("Use")){
						
						profile.revertHammer(profile.getHammers());
						profile.getHammer(tool.getId()).setEquipped(true);
						holder.revertTools(tool.getType(), "Use");
						controlButton.setText("Used");
						SettingsManager.color = profile.getEquippedHammer().getColor();
						SettingsManager.particles = profile.getEquippedHammer().getSwingType();
						SettingsManager.particle_path = profile.getEquippedHammer().getSound().concat("-particle.p");
						Gdx.app.log("tag", SettingsManager.particle_path);
						holder.getTouch().load("data/particles/particle.p", "data/particles");
						super.clicked(event, x, y);
					}
				}
		    	  
		      });
		      
		      addActor(controlButton);
		      setBounds(getX(), getY(), background.getWidth(), background.getHeight());
	   }
	   
	/**
	 * @return the controlButton
	 */
	public TextButton getControlButton() {
		return controlButton;
	}

	/**
	 * @param controlButton the controlButton to set
	 */
	public void setControlButton(TextButton controlButton) {
		this.controlButton = controlButton;
	}

	/**
	 * @return the tool
	 */
	public Tools getTool() {
		return tool;
	}

	/**
	 * @param tool the tool to set
	 */
	public void setTool(Tools tool) {
		this.tool = tool;
	}
	

	   
}