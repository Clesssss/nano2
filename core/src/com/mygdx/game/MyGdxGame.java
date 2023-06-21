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
	private OrthographicCamera camera;
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
	int time;
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

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 2784,1650);
		fitViewport = new FitViewport(2784,1650,camera);
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(5);
		player = new Player(300,1);
		player.hitbox = new Rectangle(player.posX + 104.5f ,player.posY + 99.75f, 204.25f,152);
		img = new Texture("download.png");
		stateTime = 0f;
		skeletons = new Array<Skeleton>();
		flyingeyes = new Array<FlyingEye>();
		goblins = new Array<Goblin>();
		mushrooms = new Array<Mushroom>();
	}
	private void spawnSkeleton(){
		Skeleton skeleton = new Skeleton(20,10);
		skeleton.posX = MathUtils.random(-240,2360);
		skeleton.posY = 0;
		Rectangle hitbox = new Rectangle(skeleton.posX+ 240, 196,128,204);
		skeleton.hitbox = hitbox;
		skeletons.add(skeleton);
		lastSpawnTimeSkeleton = TimeUtils.nanoTime();
	}
	private void spawngoblin(){

		Goblin goblin1 = new Goblin(10,20);
		goblin1.posX = MathUtils.random(-240,2400);
		goblin1.posY = 0;
		Rectangle hitbox = new Rectangle(goblin1.posX+ 236, 196,124,204);
		goblin1.hitbox = hitbox;
		goblins.add(goblin1);
		lastspawnTimegoblin = TimeUtils.nanoTime();
	}
	private void spawnFlyingEye(){

		FlyingEye flyingEye = new FlyingEye(5,10);
		flyingEye.posX = MathUtils.random(-240,2400);
		flyingEye.posY = 10;
		Rectangle hitbox = new Rectangle(flyingEye.posX+ 236, 196,160,204);
		flyingEye.hitbox = hitbox;
		flyingeyes.add(flyingEye);
		lastSpawnTimeFlyingEye = TimeUtils.nanoTime();
	}
	private void spawnMushroom(){
		Mushroom mushroom = new Mushroom(5,10);
		mushroom.posX = MathUtils.random(-240,2400);
		mushroom.posY = 0;
		Rectangle hitbox = new Rectangle(mushroom.posX+ 252, 196,92,204);
		mushroom.hitbox = hitbox;
		mushrooms.add(mushroom);
		lastSpawnTimeMushroom = TimeUtils.nanoTime();
	}
	@Override
	public void render () {
		if (current == State.MAIN){
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
			stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

			TextureRegion currentFrame;
			if (TimeUtils.nanoTime() - lastSpawnTimeSkeleton > 17000000000L - 100000000 * stateTime){
				spawnSkeleton();
			}

			if (TimeUtils.nanoTime() - lastSpawnTimeFlyingEye > 13000000000L - 100000000 * stateTime){
				spawnFlyingEye();
			}

			if (TimeUtils.nanoTime() - lastspawnTimegoblin > 11000000000L - 100000000 * stateTime){
				spawngoblin();
			}
			if (TimeUtils.nanoTime() - lastSpawnTimeMushroom > 7000000000L - 100000000 * stateTime){
				spawnMushroom();
			}

			if(Gdx.input.isKeyPressed(Input.Keys.A)){
				flip = true;
				currentFrame = player.walkAnimation.getKeyFrame(stateTime, true);
				player.posX -= 400 * Gdx.graphics.getDeltaTime();
				count = 0;
			} else if(Gdx.input.isKeyPressed(Input.Keys.D)){
				flip = false;
				currentFrame = player.walkAnimation.getKeyFrame(stateTime, true);
				player.posX += 400 * Gdx.graphics.getDeltaTime();
				count = 0;
			} else if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				currentFrame = player.attackAnimation.getKeyFrame(stateTime, true);
				count++;
				for(Skeleton skeleton : skeletons){
					if(player.hitbox.overlaps(skeleton.hitbox) && (count % 78 == 12
							|| count % 78 == 33
							|| count % 78 == 54 )){
						skeleton.takeHit = true;
						player.attack(skeleton);
						skeleton.hp -= player.attack;
					}
				}
				for(Goblin goblin1 : goblins){
					if(player.hitbox.overlaps(goblin1.hitbox) && (count % 78 == 12
							|| count % 78 == 33
							|| count % 78 == 54 )){
						goblin1.takeHit = true;
						player.attack(goblin1);
						goblin1.hp -= player.attack;
					}
				}
				for(FlyingEye fl : flyingeyes){
					if(player.hitbox.overlaps(fl.hitbox) && (count % 78 == 12
							|| count % 78 == 33
							|| count % 78 == 54 )){
						fl.takeHit = true;
						player.attack(fl);
						fl.hp -= player.attack;
					}
				}
				for(Mushroom mushroom1 : mushrooms){
					if(player.hitbox.overlaps(mushroom1.hitbox) && (count % 78 == 12
							|| count % 78 == 33
							|| count % 78 == 54 )){
						mushroom1.takeHit = true;
						player.attack(mushroom1);
						mushroom1.hp -= player.attack;
					}
				}
			} else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
				currentFrame = player.getSlideAnimation().getKeyFrame(stateTime, true);
				if(flip){
					player.posX -= 600 * Gdx.graphics.getDeltaTime();
				} else{
					player.posX += 600 * Gdx.graphics.getDeltaTime();
				}
				count = 0;
			}else {
				currentFrame = player.idleAnimation.getKeyFrame(stateTime, true);
				count = 0;
			}
			if(flip){
				player.hitbox.setPosition(player.posX - 22.14f ,player.posY+ 99.75f);
			}else {
				player.hitbox.setPosition(player.posX + 118.75f ,player.posY+ 99.75f);
			}
			batch.begin();
			batch.draw(img, 0, 0);
			for (Iterator<Skeleton> iter = skeletons.iterator(); iter.hasNext();){
				Skeleton skeleton = iter.next();
				if(skeleton.hp <= 0){
					skeleton.updateDeath(Gdx.graphics.getDeltaTime());
					if (skeleton.isDeathFinished()) {
						iter.remove();
					} else {
						skeleton.currentFrame = skeleton.deathAnimation.getKeyFrame(skeleton.deathStateTime,true);
					}
				} else if(skeleton.takeHit){
					skeleton.currentFrame = skeleton.takeHitAnimation.getKeyFrame(skeleton.stateTime,true);
					skeleton.takeHit = false;
				}else {
					skeleton.update(Gdx.graphics.getDeltaTime());
					skeleton.currentFrame = skeleton.idleAnimation.getKeyFrame(skeleton.stateTime,true);
				}
				batch.draw(skeleton.currentFrame, skeleton.posX, skeleton.posY, 600, 600);

			}

			for (Iterator<FlyingEye> iter = flyingeyes.iterator(); iter.hasNext();){
				FlyingEye fl2 = iter.next();
				if(fl2.hp <= 0){
					fl2.updateDeath(Gdx.graphics.getDeltaTime());
					if (fl2.isDeathFinished()) {
						iter.remove();
					} else {
						fl2.currentFrame = fl2.deathAnimation.getKeyFrame(fl2.deathStateTime,true);
					}
				} else if(fl2.takeHit){
					fl2.currentFrame = fl2.takeHitAnimation.getKeyFrame(fl2.stateTime,true);
					fl2.takeHit = false;
				}else {
					fl2.update(Gdx.graphics.getDeltaTime());
					fl2.currentFrame = fl2.idleAnimation.getKeyFrame(fl2.stateTime,true);
				}
				batch.draw(fl2.currentFrame, fl2.posX,fl2.posY, 600, 600);

			}
			for (Iterator<Goblin> iter = goblins.iterator(); iter.hasNext();){
				Goblin goblin1 = iter.next();
				if(goblin1.hp <= 0){
					goblin1.updateDeath(Gdx.graphics.getDeltaTime());
					if (goblin1.isDeathFinished()) {
						iter.remove();
					} else {
						goblin1.currentFrame = goblin1.deathAnimation.getKeyFrame(goblin1.deathStateTime,true);
					}
				} else if(goblin1.takeHit){
					goblin1.currentFrame = goblin1.takeHitAnimation.getKeyFrame(goblin1.stateTime,true);
					goblin1.takeHit = false;
				}else {
					goblin1.update(Gdx.graphics.getDeltaTime());
					goblin1.currentFrame = goblin1.idleAnimation.getKeyFrame(goblin1.stateTime,true);
				}
				batch.draw(goblin1.currentFrame, goblin1.posX, goblin1.posY, 600, 600);

			}
			for (Iterator<Mushroom> iter = mushrooms.iterator(); iter.hasNext();){
				Mushroom m1 = iter.next();
				if(m1.hp <= 0){
					m1.updateDeath(Gdx.graphics.getDeltaTime());
					if (m1.isDeathFinished()) {
						iter.remove();
					} else {
						m1.currentFrame = m1.deathAnimation.getKeyFrame(m1.deathStateTime,true);
					}
				} else if(m1.takeHit){
					m1.currentFrame = m1.takeHitAnimation.getKeyFrame(m1.stateTime,true);
					m1.takeHit = false;
				}else {
					m1.update(Gdx.graphics.getDeltaTime());
					m1.currentFrame = m1.idleAnimation.getKeyFrame(m1.stateTime,true);
				}
				batch.draw(m1.currentFrame, m1.posX, m1.posY, 600, 600);

			}
			if(flip && player.posX <= -90){
				player.posX = -90;
			}
			if(!flip && player.posX >= 2599){
				player.posX = 2599;
			}
			batch.draw(currentFrame, flip ? player.posX+266 : player.posX, player.posY, flip ? -380 : 380, 380);
			font.draw(batch,(int)stateTime +"", 2500,1400);
			batch.end();
			if(skeletons.size + flyingeyes.size + goblins.size + mushrooms.size >=10){
				current = State.GAMEOVER;
				skeletons.clear();
				flyingeyes.clear();
				goblins.clear();
				mushrooms.clear();
				lastSpawnTimeSkeleton = 0;
				lastspawnTimegoblin = 0;
				lastSpawnTimeFlyingEye = 0;
				lastSpawnTimeMushroom =0;
				time = (int)stateTime;
				stateTime = 0;
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
			font.draw(batch, "Time: " + time + " sec", Gdx.graphics.getWidth()*.4f, Gdx.graphics.getHeight() * .4f);
			font.draw(batch, "Press ENTER to return to START screen.", Gdx.graphics.getWidth()*.3f, Gdx.graphics.getHeight() * .2f);
			batch.end();

		}else{
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, "Press ENTER to resume", Gdx.graphics.getWidth()*.4f, Gdx.graphics.getHeight() * .5f);
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
