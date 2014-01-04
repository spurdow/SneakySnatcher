package com.teampora.game;

import java.util.Random;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.teampora.managers.SettingsManager;
import com.teampora.managers.SoundManager;
import com.teampora.managers.SoundManager.SoundType;
import com.teampora.utils.Assets;



public class FruitModel implements Poolable{
	
	public final static String GOLDEN_APPLE = "fs_golden_apple";
	
	public final static String GREEN_APPLE = "fs_green_apple";
	
	public final static String RED_APPLE = "fs_red_apple";
	
	public final static String LEMON = "fs_lemon";
	
	public final static String LIME = "fs_lime";
	
	public final static String ORANGE = "fs_orange";
	
	public final static String WATERMELON = "fs_watermelon";
	
	public final static String COCONUT = "fs_coconut";
	
	public final static String BOMB = "ss-fruit-bomb";
	
	public final static String FRUIT_COIN = "ss-fruit-coin";
	
	public final static String FRUIT_SQUASH = "ss-splatter-white";

	
	public Body fruitBody;
	
	private String FRUIT_PNG_FIXTURE = "";
	
	public enum FruitType{
		GOLDEN_APPLE,
		GREEN_APPLE,
		RED_APPLE,
		LEMON,
		LIME,
		ORANGE,
		WATERMELON,
		COCONUT,
		BOMB,
		COIN
	}
	
	private FruitType type;

	public Color color;
	
	private float a = 1.0f;
	
	private SpriteBatch batch;
	
	private Vector2 position;
	
	private Vector2 origin;
	
	private Vector2 scale;
	
	private float angle;
	
	private float height;
	
	private float width;
	
	
	private float tick;
	
	private float time_step;
	
	private final static float TICK_PER_SECOND = 0.02f;
	
	private final static float ALLOWED_TICK_FOR_TRANSPARENCY = 3.0f;
	
	private final static float TICK_TO_TRANSPARENCY = 0.09f;
	
	public boolean isVisible  = false;
	
	private TextureRegion fruit;
	
	private ParticleEffect particleEffect;
	
	private final Random randomizer = new Random();
	
	private BodyDef bodyDef;
	
	private FixtureDef fixDef;
	
	public int points;
	
	private BodyEditorLoader loader;
	
	private Vector2 loaderOrigin = null;

	private boolean isFruitBonus = false;
	
	private TouchType touchType;
	
	public enum TouchType{
		TOUCH1,
		TOUCH2,
		TOUCH3,
		TOUCH4,
		TOUCH5,
		BOMB,
		COIN
	}
	
	public FruitModel(TextureRegion fruit,
			    Vector2 position ,
				Vector2 origin, 
				Vector2 scale,
				float angle,
				float height,
				float width,
				
				SpriteBatch batch,
				Color color	){
		this.fruit  = fruit;
		this.position = position;
		this.origin = origin;
		this.scale = scale;
		this.angle = angle;
		this.height = height;
		this.width = width;
		this.batch  = batch;
		this.color = color;
	}
	
	public FruitModel(FruitType type, SpriteBatch batch, Vector2 position){
		this.type = type;
		this.batch = batch;
		this.position = position;
		
	}
	
	public FruitModel(){

		
	}
	/*
	 *  Creates a new fruit depending on the random no, 
	 *  @param World world to put the fruits in
	 *  
	 */
	public void createNew(World world){
		int no = randomizer.nextInt(FruitType.values().length);
		this.type = FruitType.values()[no];
		
		switch(type){
		case GOLDEN_APPLE: fruit = Assets.golden_apple;
							color = new Color(1f, 0.84f, 0f , 1f);
							FRUIT_PNG_FIXTURE = GOLDEN_APPLE;
							isFruitBonus = (randomizer.nextFloat() < 0.1)? true:false ;
							touchType = TouchType.TOUCH1;
							break;
		case GREEN_APPLE : fruit = Assets.green_apple ;
						   color = Color.GREEN; 
						   FRUIT_PNG_FIXTURE = GREEN_APPLE;
						   touchType = TouchType.TOUCH2;
						   break;
		case RED_APPLE	 : fruit = Assets.red_apple   ;
						   color = Color.RED;  
						   FRUIT_PNG_FIXTURE = RED_APPLE;
						   touchType = TouchType.TOUCH3;
						   break;
		case LEMON		 : fruit = Assets.lemon   	  ; 
						   color = Color.YELLOW; 
						   FRUIT_PNG_FIXTURE = LEMON;
						   touchType = TouchType.TOUCH4;
						   break;
		case LIME 		 : fruit = Assets.lime		  ;
						   color = Color.GREEN ; 
						   FRUIT_PNG_FIXTURE = LIME;
						   touchType = TouchType.TOUCH1;
						   break;
		case ORANGE		 : fruit = Assets.orange      ;
		                   color = Color.ORANGE ; 
		                   FRUIT_PNG_FIXTURE = ORANGE;
		                   touchType = TouchType.TOUCH2;
		                   break;
		case WATERMELON	 : fruit = Assets.watermelon  ;
		                   color = new Color(0.2f,0.8f,0.2f,1f); 
		                   FRUIT_PNG_FIXTURE = WATERMELON;
		                   touchType = TouchType.TOUCH3;
		                   break;
		case COCONUT	 : fruit = Assets.coconut     ;
							color = new Color(0.5f , 0.3f, 0.07f, 1f  );
							FRUIT_PNG_FIXTURE = COCONUT;
							touchType = TouchType.TOUCH4;
							break;
		case BOMB        : 
		default:			fruit = Assets.bomb_fruit  ;
							color = Color.WHITE;
							FRUIT_PNG_FIXTURE = BOMB;
							touchType = TouchType.BOMB;
							break;
		}		

	
		if(type==FruitType.GOLDEN_APPLE){
			points = 2;
		}else if(type != FruitType.BOMB){
			points = 1;
		}else{
			points = -1;
		}
		/*
		 *  the probability that bomb will go out
		 */
		if(randomizer.nextFloat() > 0.5f && type!=FruitType.BOMB){
			fruit = Assets.bomb_fruit;
			type = FruitType.BOMB;
			color = Color.WHITE;
			FRUIT_PNG_FIXTURE = BOMB;
			touchType = TouchType.BOMB;
			points = -1;
		}
		
		/*
		 *  for the fixtures loader
		 *  different fruits different fixtures
		 */
		loader = new BodyEditorLoader(Gdx.files.internal("data/fixtures/fruit-fixtures.json"));
		

		
		
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.x = (float) 12 + randomizer.nextFloat() * 24;
		bodyDef.position.y = (float) 8  + randomizer.nextFloat() * 16;
		
		
		
		fruitBody = world.createBody(bodyDef);
		
        fixDef = new FixtureDef();
		fixDef.density = 1.0f;
		fixDef.friction = 0.1f;
		fixDef.restitution = 0.1f;

		loader.attachFixture(fruitBody, FRUIT_PNG_FIXTURE+".png", fixDef, 2.0f, 2f ,2f);
		loaderOrigin = loader.getOrigin(FRUIT_PNG_FIXTURE+".png", 2.0f);
		
		isVisible = true;
		

	}

	
	public void update(float delta ){
		setBody(fruitBody);
		if(fruit == null){
			Gdx.app.log("fruit =", "null");
			return;
		}
		time_step+=delta;
		if(time_step >= 1.0f){
			tick+=TICK_PER_SECOND;
		}
		
		if(tick >= ALLOWED_TICK_FOR_TRANSPARENCY){
			a -= TICK_TO_TRANSPARENCY;
			tick = 0;
		}
		Color b = batch.getColor();

		batch.setColor(b.r, b.g, b.b, a);
		
		batch.draw(fruit,
				position.x , position.y ,
				loaderOrigin.x , loaderOrigin.y,
				width , height,
				scale.x , scale.y,
				MathUtils.radiansToDegrees * angle);

		
		if(a <= 0.3f){
			isVisible = false;
		}
		else{
			isVisible = true;
		}
				
		
	}	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		a = 1.0f;
		isVisible = false;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}

	public Vector2 getScale() {
		return scale;
	}

	public void setScale(Vector2 scale) {
		this.scale = scale;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public TextureRegion getFruit() {
		return fruit;
	}

	public void setFruit(TextureRegion fruit) {
		this.fruit = fruit;
	}

	public FruitType getType() {
		return type;
	}

	public void setType(FruitType type) {
		this.type = type;
	}

	public float getA() {
		return a;
	}

	public void setA(float a) {
		this.a = a;
	}

	public ParticleEffect getParticleEffect() {
		return particleEffect;
	}

	public void setParticleEffect(ParticleEffect particleEffect) {
		this.particleEffect = particleEffect;
	}
	
	
	public boolean isFruitBonus() {
		return isFruitBonus;
	}

	public void setFruitBonus(boolean isFruitBonus) {
		this.isFruitBonus = isFruitBonus;
	}

	public void hasBeenTouched(){
		makeSound();
	}
	
	public void hasBeenDragged(){
		makeSoundDragged();
	}
	
	public void leftIgnored(){
		if (type == FruitType.BOMB){
			SoundManager.play(SoundType.BOMB);
		}
	}
	
	private void makeSound(){
		if(touchType == TouchType.BOMB){
			SoundManager.play(SoundType.BOMB);
		}else if(touchType == TouchType.COIN){
			SoundManager.play(SoundType.COIN , 2);
		}else if(touchType == TouchType.TOUCH1){
			SoundManager.play(SoundType.TOUCH1);
		}else if(touchType == TouchType.TOUCH2){
			SoundManager.play(SoundType.TOUCH2);
		}else if(touchType == TouchType.TOUCH3){
			SoundManager.play(SoundType.TOUCH3);
		}else if(touchType == TouchType.TOUCH4){
			SoundManager.play(SoundType.TOUCH4);
		}else if(touchType == TouchType.TOUCH5){
			SoundManager.play(SoundType.TOUCH4 , 1 , 1.5f , 0);
		}	
	}
	
	private void makeSoundDragged(){
		SoundManager.play(SoundType.SQUASH);
	}
	
	public void setBodyAll(Body fruit){
		setBody(fruit);
		setOrigin(new Vector2(1f , 1f));
		setWidth(2);
		setHeight(2);
		setScale(new Vector2(2 , 2));
		
	}
	public void setBody(Body fruit){
		setPosition(new Vector2(fruit.getPosition().x , fruit.getPosition().y).sub(loaderOrigin));
		setAngle(fruit.getAngle());
		fruitBody = fruit;
	}

	/**
	 * @return the loader
	 */
	public BodyEditorLoader getLoader() {
		return loader;
	}

	/**
	 * @param loader the loader to set
	 */
	public void setLoader(BodyEditorLoader loader) {
		this.loader = loader;
	}

	public void setSoundType(TouchType touch) {
		// TODO Auto-generated method stub
		this.touchType = touch;
		
	}
	
}
