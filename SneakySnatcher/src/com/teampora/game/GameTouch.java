package com.teampora.game;

import mdesl.swipe.*;
import mdesl.swipe.mesh.*;
import mdesl.swipe.simplify.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.teampora.interfaces.Touchable;
import com.teampora.managers.SettingsManager;


public class GameTouch implements Touchable, Disposable {

	private OrthographicCamera cam;
	private SpriteBatch batch;
	
	public SwipeHandle swipe;
	
	private Texture tex;
	private ShapeRenderer shapes;
	
	private SwipeTriStrip tris;
	
	private ParticleEffect particleEffect;
	
	private Array<ParticleEmitter> emitters;
	
	public static float alpha = 0.5f;

	
	public GameTouch(){
		create();
	}


	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		//the triangle strip renderer
		tris = new SwipeTriStrip();
		
		//tchikenss of line
		tris.thickness = (float)Gdx.graphics.getHeight() / 28f;

		
		//a swipe handler with max # of input points to be kept alive
		swipe = new SwipeHandle(10);
		
		//minimum distance between two points
		swipe.minDistance = 10;
		
		//minimum distance between first and second point
		swipe.initialDistance = 10;
		
		//we will use a texture for the smooth edge, and also for stroke effects
		tex = new Texture(Gdx.files.internal("data/gradient.png"));
		tex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		shapes = new ShapeRenderer();
		batch = new SpriteBatch();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal("data/particles/".concat(SettingsManager.particle_path)), Gdx.files.internal("data/particles"));
		
		//emitters = new Array<ParticleEmitter>(particleEffect.getEmitters());
		//particleEffect.getEmitters().clear();
	}



	@Override
	public void resize(float width, float height) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		//Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if(!swipe.isDrawing)
			return;
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		//Gdx.gl.glHint(GL20.GL_TRIANGLE_STRIP, GL20.GL_NICEST);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		tex.bind();
		
		//the endcap scale
 		tris.endcap = 5f;
		
		//the thickness of the line
		tris.thickness = 30f;//(float)Gdx.graphics.getHeight() / 28f;
		
		//generate the triangle strip from our path
		tris.update(swipe.path());
		
		//the vertex color for tinting, i.e. for opacity
		
		tris.color = SettingsManager.color;
		tris.color.set(tris.color.r, tris.color.g, tris.color.b, alpha);
		
		//render the triangles to the screen
		tris.draw(cam);
		
		batch.begin();
	    Vector2 swipeVec = null;
		if(swipe.path().size > 0){
			//particleEffect.getEmitters().addAll(emitters);
			int index = MathUtils.random(0, swipe.path().size - 1);
			swipeVec = swipe.path().get(index);
			// set position for particles the head of swipe
			particleEffect.setPosition(swipeVec.x, swipeVec.y);
			
			// render partciles
			particleEffect.draw(batch, delta);
		}
		batch.end();
		
		if(swipe.path().size <= 0){
			//particleEffect.getEmitters().clear();
		}
		
	}
	
	
	public void load(String effectFile , String imagesDir){
		load(Gdx.files.internal(effectFile), Gdx.files.internal(imagesDir));
	}

	protected void load(FileHandle effectFile , FileHandle imagesDir){
		particleEffect.load(effectFile, imagesDir);
	}
	
	public void load(String effectFile){
		load(Gdx.files.internal(effectFile));
	}
	
	protected void load(FileHandle effectFile){
		particleEffect.loadEmitters(effectFile);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		shapes.dispose();
		tex.dispose();
		
	}


	
	

}
