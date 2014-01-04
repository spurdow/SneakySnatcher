package com.teampora.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool.Poolable;

public class FruitParticle implements Poolable{

	private boolean isVisible;
	private Color color;
	
	public Body body;
	private BodyDef bodyDef;
	private FixtureDef def;
	
	private Texture particle;
	private float positionX , positionY;
	private float width, height;
	
	private float tick = 0f;
	private final float TICK_TO_TRANSPARENCY = 10f;
	
	
	public FruitParticle(){
		particle = new Texture(Gdx.files.internal("data/particles/particle.png"));
		particle.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		width = 0.4f;
		height = 0.4f;
	}
	
	
	public void createNew( World world ){
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.x = positionX;
		bodyDef.position.y = positionY;
		
		body = world.createBody(bodyDef);
		
		final CircleShape shape = new CircleShape();
		shape.setRadius(0.2f);
		
		def = new FixtureDef();
		def.density = 1f;
		def.friction = 0.1f;
		def.restitution = 1f;
		def.shape = shape;
		
		body.createFixture(def);
		
		shape.dispose();
		
	}
	
	public void draw(SpriteBatch batch , float delta){
		batch.setColor(color);
		positionX = body.getPosition().x;
		positionY = body.getPosition().y;
		batch.draw(particle, positionX, positionY, width, height);
		//batch.draw(particle,positionX, positionY, width/2, height/2, width , height, 0.5f, 0.5f, MathUtils.radiansToDegrees * body.getAngle(),positionX,positionY , (float)width, (float)height, false, false);
		//batch.draw
		
		tick+= delta;
		if(tick >= TICK_TO_TRANSPARENCY){
			reset();
		}
	}
	
	
	
	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}


	/**
	 * @param isVisible the isVisible to set
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}


	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}


	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}


	/**
	 * @return the particle
	 */
	public Texture getParticle() {
		return particle;
	}


	/**
	 * @param particle the particle to set
	 */
	public void setParticle(Texture particle) {
		this.particle = particle;
	}
	


	/**
	 * @return the positionX
	 */
	public float getPositionX() {
		return positionX;
	}




	/**
	 * @param positionX the positionX to set
	 */
	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}




	/**
	 * @return the positionY
	 */
	public float getPositionY() {
		return positionY;
	}




	/**
	 * @param positionY the positionY to set
	 */
	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}




	@Override
	public void reset() {
		// TODO Auto-generated method stub
		isVisible = false;
		tick = 0;
	}

}
