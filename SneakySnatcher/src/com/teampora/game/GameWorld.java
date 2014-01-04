package com.teampora.game;

import java.util.Iterator;
import java.util.Stack;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.teampora.accessor.SpriteAccessor;
import com.teampora.game.FruitModel.FruitType;
import com.teampora.game.FruitModel.TouchType;
import com.teampora.interfaces.Playable;
import com.teampora.managers.SettingsManager;
import com.teampora.managers.SoundManager;
import com.teampora.managers.SoundManager.SoundType;
import com.teampora.utils.Assets;
import com.teampora.utils.SpritePoolable;

public class GameWorld implements InputProcessor, GestureListener, ContactListener {
	
	
	private int MAX_FRUITS = 20;
	
	private World world;
	
	private SpriteBatch spriteBatch;
	
	@SuppressWarnings("unused")
	private Box2DDebugRenderer debugRenderer;
	
	private final float BONUS_FRUIT_TIME = 5f; //5 seconds
	
	private float bonus_time = 0f;
	
	private final float TIME_TO_REPLENISH = 5f;
	
	private float fruitCreation = 0f;
	
	private Array<FruitModel> fruits = new Array<FruitModel>();
	
	private Body touchedBody;
	
	private Pool<FruitModel> fruitPools = new Pool<FruitModel>(){

		@Override
		protected FruitModel newObject() {
			// TODO Auto-generated method stub
			return new FruitModel();
		}
		
	};
	
	private float timeElapsed = 0;
	
	private Array<SpritePoolable> squashed_fruits = new Array<SpritePoolable>();
	
	private Pool<SpritePoolable> squashed_pools = new Pool<SpritePoolable>(){

		@Override
		protected SpritePoolable newObject() {
			// TODO Auto-generated method stub
			
			return new SpritePoolable();
		}
		
	};
	
	private Playable gameHolder;
	
	private OrthographicCamera camera;
	
	private final static Vector2 GRAVITY = new Vector2(0  , -20);
	
	private Stack<Contact> contacts = new Stack<Contact>();

	
	public GameWorld(Playable gameHolder, OrthographicCamera camera,
					SpriteBatch spriteBatch ) {
		this.gameHolder = gameHolder;
		this.camera = camera;
		this.spriteBatch = new SpriteBatch();
		this.world = new World(GRAVITY , false);
		this.touchedBody = null;
		this.debugRenderer = new Box2DDebugRenderer();
		this.world.setContactListener(this);
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());	 
	}
	
	public void update(float delta){		
		//debugRenderer.render(world, camera.combined);
		timeElapsed +=delta;
		world.step(delta, 6, 2);
		if(gameHolder.getState() == GameState.End || gameHolder.getState() == GameState.GameOver){
			return;
		} 
		spriteBatch.getProjectionMatrix().set(camera.combined);
		spriteBatch.begin();
		
		
		FruitModel fruit;
		for(int i =0;i<fruits.size;i++){
			if(!fruits.get(i).isVisible){
				fruit = fruits.get(i);
				if(fruits.get(i).getType() == FruitType.BOMB){
					SpritePoolable sprite = squashed_pools.obtain();
					sprite.createNew(fruit.getWidth(), fruit.getHeight(),fruit.getPosition().x - 2, fruit.getPosition().y - 2, fruit.color);
					squashed_fruits.add(sprite);
					gameHolder.bomb();
				}
				fruit.leftIgnored();
				world.destroyBody(fruit.fruitBody);
				fruits.removeIndex(i);
				fruitPools.free(fruit);
			}else{				
				fruits.get(i).update(delta);
			}
		}
		

		
		/*
		 *  render fruits squaashed
		 */
		if(squashed_fruits.size > 0){
			for(int i =0 ; i<squashed_fruits.size ; i++){
				if(squashed_fruits.get(i).isVisible){
					squashed_fruits.get(i).draw(spriteBatch);
				}
				else{
					squashed_pools.free(squashed_fruits.removeIndex(i));
				}
			}
		}
		

		
		
		fruitCreation+=delta;
		if((fruits.size < MAX_FRUITS || fruits.size < 0) && fruitCreation >= TIME_TO_REPLENISH){
			createNewFruits();
			fruitCreation = 0f;
		}
		spriteBatch.end();
		
		if(timeElapsed >= 30 && MAX_FRUITS <= 40){
			MAX_FRUITS+= 1;
			Gdx.app.log("Adding" , "1 fruit!");
			timeElapsed = 0;
		}
		
		checkCollision();

	}
	
	private void checkCollision(){
		/*
		int contacts = world.getContactCount();
		
		if(contacts > 0){
			for(Contact contact : world.getContactList()){
				Fixture fa = contact.getFixtureA();
				Fixture fb = contact.getFixtureB();
				boolean collideOnce  = false;
				if(fa.getBody().getType() == BodyType.DynamicBody){
					collideOnce = fa.getUserData()==null? true : false;
					fa.setUserData("t");
				}else if(fb.getBody().getType() == BodyType.DynamicBody){
					collideOnce = fb.getUserData()==null? true: false;
					fb.setUserData("t");
				}
				
				if((fa.getBody().getType() == BodyType.DynamicBody && fb.getBody().getType() == BodyType.StaticBody) || (fb.getBody().getType() == BodyType.DynamicBody && fa.getBody().getType() == BodyType.StaticBody)){
					if(collideOnce )
						SoundManager.play(SoundType.DROP , 0.5f);				
				}
			}
		}*/
		
		/*
		 *  force close problem
		 *  i can't control the problem here
		 *  
		 */
		if(contacts.isEmpty() ) return;
		while(!contacts.isEmpty()){
			Contact contact = contacts.pop();
			Fixture fa = contact.getFixtureA();
			Fixture fb = contact.getFixtureB();
			
		    if(fa == null || fb == null){ return ;}
			
			boolean collideOnce  = false;
			if(fa.getBody().getType() == BodyType.DynamicBody){
				collideOnce = fa.getUserData()==null? true : false;
				fa.setUserData("t");
			}else if(fb.getBody().getType() == BodyType.DynamicBody){
				collideOnce = fb.getUserData()==null? true: false;
				fb.setUserData("t");
			}
			
			if((fa.getBody().getType() == BodyType.DynamicBody && fb.getBody().getType() == BodyType.StaticBody) || (fb.getBody().getType() == BodyType.DynamicBody && fa.getBody().getType() == BodyType.StaticBody)){
				if( collideOnce ){
					SoundManager.play(SoundType.DROP , 0.5f);
				}
			}
		}
	}
	
	public void setUpFloor(){
		// the ground body
		
				PolygonShape groundBox = new PolygonShape();
				
				/*
				 *  the floor
				 */
				BodyDef bodyDef = new BodyDef();
				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set(new Vector2(24 , -15.5f));
				
				Body groundBody = world.createBody(bodyDef);

				groundBox.setAsBox(camera.viewportWidth / 2 - 0.2f,1);
				groundBody.createFixture(groundBox, 0f);
				
				
				/*
				 *  the left square box
				 */
				bodyDef = new BodyDef();
				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set(new Vector2(0 , - 12.5f));
				
				groundBody = world.createBody(bodyDef);
				
				groundBox.setAsBox(4.5f, 2);
				groundBody.createFixture(groundBox , 0f);
				
				/*
				 *  the left rectangle box 
				 */
				bodyDef = new BodyDef();
				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set(new Vector2(0  , - 8.5f));
				
				groundBody = world.createBody(bodyDef);
				
				groundBox.setAsBox(2f, 5);
				groundBody.createFixture(groundBox , 0f);
				
				
				/*
				 *  the right square box
				 */
				bodyDef = new BodyDef();
				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set(new Vector2(45  , - 14.5f));
				
				groundBody = world.createBody(bodyDef);
				
				groundBox.setAsBox(3f, 1);
				groundBody.createFixture(groundBox , 0f);
				
				/*
				 *  the right rectangle box
				 */
				bodyDef = new BodyDef();
				bodyDef.type = BodyType.StaticBody;
				bodyDef.position.set(new Vector2(47  , - 8.5f));
				
				groundBody = world.createBody(bodyDef);
				
				groundBox.setAsBox(2f, 5);
				groundBody.createFixture(groundBox , 0f);
				
				groundBox.dispose();
	}
	
	public void createNewFruits(){		
		for(int i =0;i<MAX_FRUITS - fruits.size ; i++){
			FruitModel newModel = fruitPools.obtain();
			newModel.createNew( world);
			newModel.isVisible = true;
			newModel.setBatch(spriteBatch);
			newModel.setBodyAll(newModel.fruitBody);	
			fruits.add(newModel);
		}

	}


	
	public void reset(){
		Iterator<Body> bodies = world.getBodies();
		
		while(bodies.hasNext()){
			Body b = bodies.next();
			if(b!=null){
				world.destroyBody(b);
				b.setUserData(null);
				b = null;
			}
		}
		fruits.clear();
		//fruitPools.clear();
		squashed_fruits.clear();
		squashed_pools.clear();
		world.setGravity(GRAVITY);
		//world = new World(GRAVITY , false);
		setUpFloor();
		Gdx.app.log("gameworld", "fruits has been destroyed " + world.getBodyCount());
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		//camera.projection.setToWorld(position, forward, up)
		pointTouched.set(screenX, screenY, 0);
		camera.unproject(pointTouched);
		
		touchedBody = null;
		world.QueryAABB(callback, pointTouched.x - 0.1f, pointTouched.y - 0.1f,
				                  pointTouched.x + 0.1f, pointTouched.y + 0.1f);
		
		if(touchedBody!=null){
			//the fruit has been touched
			for(FruitModel fruit : fruits){
				if(fruit.isVisible && fruit.fruitBody.equals(touchedBody)){
					if(fruit.getType()!=FruitType.BOMB){
						gameHolder.addScoreWithAnimation(fruit.points , pointTouched.x, pointTouched.y , true);
						gameHolder.setGoal(fruit.getType());						

					}else if(fruit.getType()==FruitType.BOMB){
						//Gdx.app.log("lucky", "luck! " + SettingsManager.chance + " " + (MathUtils.random()*100) + " " + ((MathUtils.random()*100) >= SettingsManager.chance));
						if(SettingsManager.chance > 0 && (MathUtils.random()*100) >= SettingsManager.chance){
							gameHolder.addScoreWithAnimation(1, pointTouched.x, pointTouched.y, true);
							gameHolder.luckyBomb(pointTouched.x, pointTouched.y);
							
						}else{
							gameHolder.addScoreWithAnimation(fruit.points, pointTouched.x, pointTouched.y, true);
							gameHolder.bomb();
							
						}
						SpritePoolable sprite = squashed_pools.obtain();
						sprite.createNew(fruit.getWidth(), fruit.getHeight(),pointTouched.x - 2, pointTouched.y - 2, fruit.color);
						squashed_fruits.add(sprite);
					}
					fruit.hasBeenTouched();
					fruit.setVisible(false);
					world.destroyBody(fruit.fruitBody);
					fruits.removeValue(fruit,false);
					fruitPools.free(fruit);
				}
			}
		}else{
			//Gdx.app.log("" , "" + screenX + " " + screenY + " " + pointTouched.x + " " + pointTouched.y);
		}
		
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub		
		pointTouched.set(screenX, screenY, 0);
		camera.unproject(pointTouched);
		
		
		
		touchedBody = null;
		world.QueryAABB(callback, pointTouched.x - 0.1f, pointTouched.y - 0.1f,
				                  pointTouched.x + 0.1f, pointTouched.y + 0.1f);
		
		///Gdx.app.log("TESPOINT1", pointTouched.x + " "  + pointTouched.y);
		//Settings.playSound(SoundType.Swing);
		if(touchedBody!=null){
			//the fruit has been dragged
			for(FruitModel fruit : fruits){

				if(fruit.isVisible && fruit.fruitBody.equals(touchedBody)){
					
					
					fruit.hasBeenDragged();
					fruit.setVisible(false);

					//squashed
					SpritePoolable sprite = squashed_pools.obtain();
					sprite.createNew(fruit.getWidth(), fruit.getHeight(),pointTouched.x - 2, pointTouched.y - 2, fruit.color);
					squashed_fruits.add(sprite);
					
					/*
					if(fruit.getType() == FruitType.BOMB && MathUtils.random() < 0.2f){
						fruit.setVisible(true);
						fruit.setFruit(Assets.fruit_coin);
						fruit.setA(1f);
						fruit.setType(FruitType.COIN);
						fruit.points = 2;
						fruit.setSoundType(TouchType.COIN);
						return false;
					}*/
					
					
					
					//destroy fruit body from the world
					world.destroyBody(fruit.fruitBody);
					fruits.removeValue(fruit,false);
					fruitPools.free(fruit);
					

				}
			}
		}
		
		return false;
	}
	

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	

	Vector3 pointTouched = new Vector3();
	QueryCallback callback = new QueryCallback(){

		@Override
		public boolean reportFixture(Fixture fixture) {
			// TODO Auto-generated method stub
			
			// if false then it reports the fixture has been touched
			if(fixture.testPoint(pointTouched.x, pointTouched.y)){
				//Gdx.app.log("TESPOINT2", pointTouched.x + " "  + pointTouched.y);
				touchedBody = fixture.getBody();
				return false;
			}
			return true;
		}
		
	};
	
	public int fruitSize(){
		return fruits.size;
	}


	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void release(){
		fruitPools.clear();
		fruits.clear();
		world.clearForces();
		
	}
	
	
	/*
	 * bonus fruit implementation here
	 */
	
	@SuppressWarnings("unused")
	private void bonus(float delta){
		if(bonus_time == 0){
			GRAVITY.y = -5;
			world.setGravity(GRAVITY);
		}
		
		bonus_time+=delta;
		
		if(bonus_time >= BONUS_FRUIT_TIME){
			GRAVITY.y = -40;
			world.setGravity(GRAVITY);
		}
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		contacts.push(contact);
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	

	
	
	
}
