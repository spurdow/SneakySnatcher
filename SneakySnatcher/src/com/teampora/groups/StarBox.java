package com.teampora.groups;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.accessor.ImageAccessor;
import com.teampora.accessor.StarBoxAccessor;
import com.teampora.labels.SneakyLabel;
import com.teampora.screens.AbstractScreen;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class StarBox extends GameBox {

	private int stars;
	private boolean[] starsAlreadyFilled = new boolean[3];
	private final Image[] star_images = new Image[3];
	private int scoreAchieved;
	private int maxScore;
	private int currentScore;
	private final Label scoreLabel;
	private final Label title;
	private StringBuilder builder = new StringBuilder();
	private AbstractScreen holder;
	private final int levelNo;
	private final Label level;
	private float width;
	private float height;
	private TextureRegion starFilled;
	


	
	public StarBox(NinePatch background ,  TextureRegion starFilled,
					TextureRegion starUnfilled , int _scoreAchieved, int _levelNo,
					int _maxScore,
					AbstractScreen _holder ){
		super(new NinePatchDrawable(background));
		// TODO Auto-generated constructor stub
		Tween.registerAccessor(StarBox.class, new StarBoxAccessor());
		this.starFilled = starFilled;
		for(int i  = 0 ; i < 3 ; i++){
			star_images[i] = new Image(new TextureRegionDrawable(starUnfilled));
			starsAlreadyFilled[i] = false;
		}
		scoreAchieved = _scoreAchieved;
		maxScore = _maxScore;
		scoreLabel = new SneakyLabel("xlfont" , Color.YELLOW);
		title = new Label("Your Score!" , Assets.skin , "xlfont" , Color.YELLOW);
		level = new SneakyLabel("xlfont" , Color.WHITE);
		levelNo = _levelNo;
		holder = _holder;
		initUI();
	}
	
	private void initUI(){
		width = holder.getStage().getWidth() * 1f;
		height = holder.getStage().getHeight() * 1f;
		background.setBounds(getX(), getY(), width, height);
		//addActor(background);
		
		title.setPosition(getX() + (width / 2 - title.getTextBounds().width / 2), getY() + (height - Utils.HEIGHT_NORMAL() * 2f));
		addActor(title);
		
		float star_width = width / 5;
		float star_height = height / 6;
		for(int i = 0 ;i < 3 ; i++){
			star_images[i].setBounds((getX() + (i * star_width )) + star_width  , getY() + (height * 0.5f), star_width, star_height);
			addActor(star_images[i]);
		}
		
		//level.setPosition(getX() + (width / 2 - level.getTextBounds().width / 2), getY() + (star_images[0].getY() - star_images[0].getHeight()));
		setLevelText(levelNo);
		addActor(level);
		
		//scoreLabel.setPosition(getX() + (width / 2 - scoreLabel.getTextBounds().width / 2), getY() + (level.getY() - level.getHeight()));
		
		setScoreLabelText(0);
		addActor(scoreLabel);
		
		
		setBounds(getX() , getY() , background.getWidth(),  background.getHeight());	
	}
	
	public SneakyLabel getScoreLabel(){
		return (SneakyLabel) scoreLabel;
	}
	
	
	public void setScoreLabelText(int currentScore ){
		builder.setLength(0);
		builder.append(currentScore).append(" / ").append(maxScore);
		scoreLabel.setText(builder);
		scoreLabel.setPosition(level.getX() + (level.getTextBounds().width / 2 - scoreLabel.getTextBounds().width / 2  ), getY() + (level.getY() - level.getHeight()));

	}
	
	public void setLevelText(int levelno){
		builder.setLength(0);
		builder.append("Level ").append(levelno);
		level.setText(builder);
		level.setPosition(getX() + (width / 2 - level.getTextBounds().width / 2), getY() + (star_images[0].getY() - star_images[0].getHeight()));
		
	}
	
	public void setLevelText(Object... objs){
		builder.setLength(0);
		for(int i =0 ; i < objs.length ; i++){
			builder.append(objs[i]).append(" ");
		}
		level.setText(builder);
		level.setPosition(getX() + (width / 2 - level.getTextBounds().width / 2), getY() + (star_images[0].getY() - star_images[0].getHeight()));
		
	}
	

	
	public void setCurrentScore(int score){
		this.currentScore = score;
		setScoreLabelText(score);
		int goal = maxScore / 3;
		if(currentScore >= goal ){
			if(currentScore >= goal){
				stars = 1;
				
			}
			if(currentScore > goal * 2){
				stars = 2;
			}
			if(currentScore >= goal * 3 ){
				stars = 3;
			}
		}
		if(stars > 0 && !starsAlreadyFilled[stars - 1] ){
			this.star_images[stars - 1].setDrawable(new TextureRegionDrawable(starFilled));
			starsAlreadyFilled[stars - 1] = true;
			Assets.starred.play(4f);
		}
	}
	
	public int getCurrentScore(){
		return currentScore;
	}
	
	public int getAchievedScore(){
		return scoreAchieved;
	}


}
