package com.teampora.utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;



public class Model {

	
	private ParticleEffect effect;
	
	private Array<ParticleEmitter> emitter;
	
	private Image drawable;
	
	private float x;
	
	private float y;
	
	private float xpercent;
	
	private float ypercent;
	
	
	private float oX;
	
	private float oY;
	
	public Model(Image drawable , float xpercent , float ypercent , Action action){
		this.drawable = drawable;
		this.effect = new ParticleEffect();
		this.xpercent = xpercent;
		this.ypercent = ypercent;
		this.oX = drawable.getWidth()/2;
		this.oY = drawable.getHeight()/2;
		reconfigure();
		
		
		this.drawable.setOrigin(oX, oY);
		if(action!=null)
			this.drawable.addAction(action);
		
		
	}
	
	public void reconfigure(){
		
		
		this.x =Utils.getDynamicWidth(xpercent);
		this.y =Utils.getDynamicHeight(ypercent);
		this.drawable.setPosition(x, y);
		this.effect.setPosition(x + oX, y + oY);
		this.drawable.setSize(Utils.WIDTH_SMALL(), Utils.HEIGHT_SMALL());
	}
	
	public Image getImage(){ 
		return drawable;
	}
	
	public void loadEffect(String fh , String fh1){
		if(effect != null){
			effect.load(Gdx.files.internal(fh), Gdx.files.internal(fh1));
			emitter = new Array<ParticleEmitter>(effect.getEmitters());
		}
	}
	
	public ParticleEffect getEffect(){
		return effect;
	}
	
	public void release(){
		drawable.clear();
		drawable = null;
		
		effect.dispose();
		effect = null;
		
		emitter.clear();
		emitter = null;
	}

}
