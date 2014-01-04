package com.teampora.screens;

import com.badlogic.gdx.Screen;
import com.teampora.sneakysnatcher.SneakySnatcher;

public class TransitionScreen implements Screen{

	
	private AbstractScreen next;
	private AbstractScreen current;
	private boolean isFinished;
	private final float TIME_TO_FIN = 5f;
	private float timeLapse = 0f;
	private SneakySnatcher game;
	
	public TransitionScreen(SneakySnatcher game, AbstractScreen next, AbstractScreen current){
		this.next = next;
		this.current = current;
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		current.render(delta);
		
		if(!isFinished)
			timeLapse+=delta;
		
		if(timeLapse >= TIME_TO_FIN){
			isFinished = true;
			game.setScreen(next);
			timeLapse =  0;
			return;
		}
		
		current.setDebugging(true);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		current.resize(width, height);		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		current.show();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		current.hide();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		current.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		current.resume();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		current.dispose();
	}

	
	public boolean isFinished(){
		return isFinished;
	}

}
