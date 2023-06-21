package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
	private Array<Goblin> goblins;
	private Array<>
	private long lastSpawnTimeSkeleton = 0;
	private long lastspawnTimegoblin = 0;
	private long lastSpawnTimeMushroom =0;
	private FitViewport fitViewport;
	private boolean flip = false;
	Rectangle playerHitbox;
	Rectangle skeletonHitbox;
	Rectangle GoblinHitbox;
	int count;
	Player player;
	Skeleton skeleton;
	Goblin goblin;

	// A variable for tracking elapsed time for the animation
	float stateTime;
	float timeElapsedDeath;
	
	@Override
	public void create () {
		player = new Player(300,50);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 2784,1650);
		fitViewport = new FitViewport(2784,1650,camera);
		batch = new SpriteBatch();
		playerX = 195;
		playerY = 90;
		playerHitbox = new Rectangle(playerX + 104.5f ,playerY + 99.75f, 204.25f,152);
		skeletonHitbox = new Rectangle(800 + 240,0 + 196,180,204);
		GoblinHitbox = new Rectangle(800 + 240,0 + 196,180,204);
		rekt = new Texture("Untitled (1).png");
		img = new Texture("download.png");
		stateTime = 0f;
		skeletons = new Array<Skeleton>();
		goblins = new Array<Goblin>();

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

		Skeleton skeleton = new Skeleton(800,20);
		//not fixed
		goblin.setPosX(MathUtils.random(-240,2400));
		goblin.setPosY(0);
		Rectangle hitbox = new Rectangle(skeleton.getPosX()+ 240, 196,180,204);
		goblin.setHitbox(hitbox);
		goblins.add(goblin);
		lastSpawnTimeSkeleton = TimeUtils.nanoTime();
	}
	private void spawnMushroom(){
		Mushroom mushroom = new Skeleton(500,10);
		//not fixed
		mushroom.setPosX(MathUtils.random(-240,2400));
		mushroom.setPosY(0);
		Rectangle hitbox = new Rectangle(mushroom.getPosX()+ 240, 196,180,204);
		mushroom.setHitbox(hitbox);
		mushroom.add(mushroom);
		lastSpawnTimeMushroom = TimeUtils.nanoTime();
	}
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		TextureRegion currentFrame;
		if (TimeUtils.nanoTime() - lastSpawnTimeSkeleton > 10000000000L){
			spawnSkeleton();
		}

		if (TimeUtils.nanoTime() - lastspawnTimegoblin > 10000000000L){
			spawngoblin();
		}
		System.out.println(goblins.size);

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
				goblin.setCurrentFrame(goblin1.idleAnimation.getKeyFrame(goblin1.getStateTime(),true));
			}
			batch.draw(goblin1.getCurrentFrame(), goblin1.getPosX(), goblin1.getPosY(), 600, 600);

		}
		if(flip){
			batch.draw(rekt, playerX -22.14f, playerY+ 99.75f, 166.25f,185.25f);
		} else{
			batch.draw(rekt, playerX + 118.75f,playerY + 99.75f, 166.25f,185.25f);
		}
		batch.draw(currentFrame, flip ? playerX+266 : playerX, playerY, flip ? -380 : 380, 380);
		batch.end();

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
