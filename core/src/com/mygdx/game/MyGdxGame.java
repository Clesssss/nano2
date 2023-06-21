package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture rekt;
	private OrthographicCamera camera;
	private float playerX;
	private float playerY;
	private Array<Skeleton> skeletons;
	private Array<FlyingEye> flyingeyes;
	private Array<Mushroom> mushrooms;
	private Array<Goblin> goblins;
	private long lastSpawnTimeSkeleton = 0;
	private long lastspawnTimegoblin = 0;
	private long lastSpawnTimeFlyingEye = 0;
	private long lastSpawnTimeMushroom =0;
	private FitViewport fitViewport;
	private boolean flip = false;
	Rectangle playerHitbox;
	Rectangle skeletonHitbox;
	Rectangle flyingEyeHitbox;
	Rectangle GoblinHitbox;
	int count;
	Player player;
	State current = State.START;
	BitmapFont font;

	// A variable for tracking elapsed time for the animation
	float stateTime;
	
	@Override
	public void create () {
		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean keyDown (int keyCode) {

				if (current == State.START && keyCode == Input.Keys.ENTER){
					current = State.DESC;
				} else if(current == State.MAIN && keyCode == Input.Keys.ESCAPE){
					current = State.PAUSE;
				} else if(current == State.PAUSE && keyCode == Input.Keys.ENTER){
					current = State.MAIN;
				} else if(current == State.DESC && keyCode == Input.Keys.ENTER){
					current = State.MAIN;
				} else if(current == State.GAMEOVER && keyCode == Input.Keys.ENTER){
					current = State.START;
				}
				return true;
			}
		});
		font = new BitmapFont();
		font.getData().setScale(5);
		player = new Player(300,50);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 2784,1650);
		fitViewport = new FitViewport(2784,1650,camera);
		batch = new SpriteBatch();
		playerX = 195;
		playerY = 90;
		playerHitbox = new Rectangle(playerX + 104.5f ,playerY + 99.75f, 204.25f,152);
		rekt = new Texture("Untitled (1).png");
		img = new Texture("download.png");
		stateTime = 0f;
		skeletons = new Array<Skeleton>();
		flyingeyes = new Array<FlyingEye>();
		goblins = new Array<Goblin>();
		mushrooms = new Array<Mushroom>();
	}
	private void spawnSkeleton(){
		Skeleton skeleton = new Skeleton(500,10);
		//not fixed
		skeleton.setPosX(MathUtils.random(-240,2400));
		skeleton.setPosY(0);
		Rectangle hitbox = new Rectangle(skeleton.getPosX()+ 240, 196,180,204);
		skeleton.setHitbox(hitbox);
		skeletons.add(skeleton);
		lastSpawnTimeSkeleton = TimeUtils.nanoTime();
	}
	private void spawngoblin(){

		Goblin goblin1 = new Goblin(800,20);
		//not fixed
		goblin1.setPosX(MathUtils.random(-240,2400));
		goblin1.setPosY(0);
		Rectangle hitbox = new Rectangle(goblin1.getPosX()+ 240, 196,180,204);
		goblin1.setHitbox(hitbox);
		goblins.add(goblin1);
		lastspawnTimegoblin = TimeUtils.nanoTime();
	}
	private void spawnFlyingEye(){

		FlyingEye flyingEye = new FlyingEye(500,10);
		//not fixed
		flyingEye.setPosX(MathUtils.random(-240,2400));
		flyingEye.setPosY(10);
		Rectangle hitbox = new Rectangle(flyingEye.getPosX()+ 240, 196,180,204);
		flyingEye.setHitbox(hitbox);
		flyingeyes.add(flyingEye);
		lastSpawnTimeFlyingEye = TimeUtils.nanoTime();
	}
	private void spawnMushroom(){
		Mushroom mushroom = new Mushroom(500,10);
		//not fixed
		mushroom.setX(MathUtils.random(-240,2400));
		mushroom.setY(0);
		Rectangle hitbox = new Rectangle(mushroom.getX()+ 240, 196,180,204);
		mushroom.setmHitArea(hitbox);
		mushrooms.add(mushroom);
		lastSpawnTimeMushroom = TimeUtils.nanoTime();
	}
	@Override
	public void render () {
		if (current == State.MAIN){
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
			stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

			TextureRegion currentFrame;
			if (TimeUtils.nanoTime() - lastSpawnTimeSkeleton > 10000000000L){
				spawnSkeleton();
			}
			System.out.println(skeletons.size);

			if (TimeUtils.nanoTime() - lastSpawnTimeFlyingEye > 10000000000L){
				spawnFlyingEye();
			}
			System.out.println(flyingeyes.size);

			if (TimeUtils.nanoTime() - lastspawnTimegoblin > 10000000000L){
				spawngoblin();
			}
			System.out.println(goblins.size);
			if (TimeUtils.nanoTime() - lastSpawnTimeMushroom > 10000000000L){
				spawnMushroom();
			}
			System.out.println(mushrooms.size);


			if(Gdx.input.isKeyPressed(Input.Keys.A)){
				flip = true;
				currentFrame = player.walkAnimation.getKeyFrame(stateTime, true);
				playerX -= 400 * Gdx.graphics.getDeltaTime();
				count = 0;
			} else if(Gdx.input.isKeyPressed(Input.Keys.D)){
				flip = false;
				currentFrame = player.walkAnimation.getKeyFrame(stateTime, true);
				playerX += 400 * Gdx.graphics.getDeltaTime();
				count = 0;
			} else if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				currentFrame = player.attackAnimation.getKeyFrame(stateTime, true);
				count++;
				for(Skeleton skeleton : skeletons){
					if(playerHitbox.overlaps(skeleton.getHitbox()) && (count % 78 == 12
							|| count % 78 == 33
							|| count % 78 == 54 )){
						skeleton.setTakeHit(true);
						player.attack(skeleton);
						skeleton.hp -= player.attack;
					}
				}
				for(Goblin goblin1 : goblins){
					if(playerHitbox.overlaps(goblin1.getHitbox()) && (count % 78 == 12
							|| count % 78 == 33
							|| count % 78 == 54 )){
						goblin1.setTakeHit(true);
						player.attack(goblin1);
						goblin1.hp -= player.attack;
					}
				}
				for(FlyingEye fl : flyingeyes){
					if(playerHitbox.overlaps(fl.getHitbox()) && (count % 78 == 12
							|| count % 78 == 33
							|| count % 78 == 54 )){
						fl.setTakeHit(true);
						player.attack(fl);
						fl.hp -= player.attack;
					}
				}
			} else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
				currentFrame = player.slideAnimation.getKeyFrame(stateTime, true);
				if(flip){
					playerX -= 600 * Gdx.graphics.getDeltaTime();
				} else{
					playerX += 600 * Gdx.graphics.getDeltaTime();
				}
				count = 0;
			}else {
				currentFrame = player.idleAnimation.getKeyFrame(stateTime, true);
				count = 0;
			}
			if(flip){
				playerHitbox.setPosition(playerX - 22.14f ,playerY+ 99.75f);
			}else {
				playerHitbox.setPosition(playerX + 118.75f ,playerY+ 99.75f);
			}
			batch.begin();
			batch.draw(img, 0, 0);
			for (Iterator<Skeleton> iter = skeletons.iterator(); iter.hasNext();){
				Skeleton skeleton = iter.next();
				batch.draw(rekt, skeleton.getPosX()+ 240, 196,180,204);
				if(skeleton.hp <= 0){
					skeleton.updateDeath(Gdx.graphics.getDeltaTime());
					if (skeleton.isDeathFinished()) {
						iter.remove();
					} else {
						skeleton.setCurrentFrame(skeleton.deathAnimation.getKeyFrame(skeleton.getDeathStateTime(),true));
					}
				} else if(skeleton.isTakeHit()){
					skeleton.setCurrentFrame(skeleton.takeHitAnimation.getKeyFrame(skeleton.getTakeHitStateTime(),true));
					skeleton.setTakeHit(false);
				}else {
					skeleton.update(Gdx.graphics.getDeltaTime());
					skeleton.setCurrentFrame(skeleton.idleAnimation.getKeyFrame(skeleton.getStateTime(),true));
				}
				batch.draw(skeleton.getCurrentFrame(), skeleton.getPosX(), skeleton.getPosY(), 600, 600);

			}

			for (Iterator<FlyingEye> iter = flyingeyes.iterator(); iter.hasNext();){
				FlyingEye fl2 = iter.next();
				batch.draw(rekt, fl2.getPosX()+ 240, 196,180,204);
				if(fl2.hp <= 0){
					fl2.updateDeath(Gdx.graphics.getDeltaTime());
					if (fl2.isDeathFinished()) {
						iter.remove();
					} else {
						fl2.setCurrentFrame(fl2.deathAnimation.getKeyFrame(fl2.getDeathStateTime(),true));
					}
				} else if(fl2.isTakeHit()){
					fl2.setCurrentFrame(fl2.takeHitAnimation.getKeyFrame(fl2.getTakeHitStateTime(),true));
					fl2.setTakeHit(false);
				}else {
					fl2.update(Gdx.graphics.getDeltaTime());
					fl2.setCurrentFrame(fl2.flightAnimation.getKeyFrame(fl2.getStateTime(),true));
				}
				batch.draw(fl2.getCurrentFrame(), fl2.getPosX(),fl2.getPosY(), 600, 600);

			}
			for (Iterator<Goblin> iter = goblins.iterator(); iter.hasNext();){
				Goblin goblin1 = iter.next();
				batch.draw(rekt, goblin1.getPosX()+ 240, 196,180,204);
				if(goblin1.hp <= 0){
					goblin1.updateDeath(Gdx.graphics.getDeltaTime());
					if (goblin1.isDeathFinished()) {
						iter.remove();
					} else {
						goblin1.setCurrentFrame(goblin1.deathAnimation.getKeyFrame(goblin1.getDeathStateTime(),true));
					}
				} else if(goblin1.isTakeHit()){
					goblin1.setCurrentFrame(goblin1.takeHitAnimation.getKeyFrame(goblin1.getTakeHitStateTime(),true));
					goblin1.setTakeHit(false);
				}else {
					goblin1.update(Gdx.graphics.getDeltaTime());
					goblin1.setCurrentFrame(goblin1.idleAnimation.getKeyFrame(goblin1.getStateTime(),true));
				}
				batch.draw(goblin1.getCurrentFrame(), goblin1.getPosX(), goblin1.getPosY(), 600, 600);

			}
			if(flip){
				batch.draw(rekt, playerX -22.14f, playerY+ 99.75f, 166.25f,185.25f);
			} else{
				batch.draw(rekt, playerX + 118.75f,playerY + 99.75f, 166.25f,185.25f);
			}
			batch.draw(currentFrame, flip ? playerX+266 : playerX, playerY, flip ? -380 : 380, 380);
			font.draw(batch,(int)stateTime +"", 2500,1400);
			batch.end();
			if(skeletons.size + flyingeyes.size + goblins.size + mushrooms.size >=10){
				current = State.GAMEOVER;
			}
		}else if(current == State.START){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, "Nano", Gdx.graphics.getWidth()*.47f, Gdx.graphics.getHeight() * .6f);
			font.draw(batch, "Press ENTER to play.", Gdx.graphics.getWidth()*.38f, Gdx.graphics.getHeight() * .4f);
			batch.end();
		} else if(current == State.DESC){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, "Objective: no more than 10 monsters in the map", Gdx.graphics.getWidth()*.2f, Gdx.graphics.getHeight() * .8f);
			font.draw(batch, "Key A to move left", Gdx.graphics.getWidth()*.3f, Gdx.graphics.getHeight() * .6f);
			font.draw(batch, "Key D to move right", Gdx.graphics.getWidth()*.3f, Gdx.graphics.getHeight() * .5f);
			font.draw(batch, "Left Click to attack", Gdx.graphics.getWidth()*.3f, Gdx.graphics.getHeight() * .4f);
			font.draw(batch, "Right Click to slide", Gdx.graphics.getWidth()*.3f, Gdx.graphics.getHeight() * .3f);
			font.draw(batch, "Press ENTER to play.", Gdx.graphics.getWidth()*.3f, Gdx.graphics.getHeight() * .2f);
			batch.end();
		} else if(current == State.GAMEOVER){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, "GAME OVER", Gdx.graphics.getWidth()*.4f, Gdx.graphics.getHeight() * .5f);
			font.draw(batch, "Press ENTER to return to START screen.", Gdx.graphics.getWidth()*.3f, Gdx.graphics.getHeight() * .2f);
			batch.end();
		}else{
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, "Press ENTER to resume", Gdx.graphics.getWidth()*.5f, Gdx.graphics.getHeight() * .5f);
			batch.end();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	public void resize(int width, int height){
		fitViewport.update(width, height);
	}
}
