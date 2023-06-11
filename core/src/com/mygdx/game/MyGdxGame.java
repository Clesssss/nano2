package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture rekt;
	private OrthographicCamera camera;
	private float playerX;
	private float playerY;
	private FitViewport fitViewport;
	private boolean flip = false;
	Rectangle playerHitbox;
	Rectangle skeletonHitbox;
	boolean skeletonIdle;
	int count;
	Player player = new Player(300,30);
	Skeleton skeleton = new Skeleton(10,10);
	TextureRegion prevFrame;

	// A variable for tracking elapsed time for the animation
	float stateTime;
	
	@Override
	public void create () {
		skeletonIdle = true;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 2784,1650);
		fitViewport = new FitViewport(2784,1650,camera);
		batch = new SpriteBatch();
		playerX = 195;
		playerY = 90;
		player.idle();
		player.walk();
		player.attack();
		player.slide();
		skeleton.idle();
		skeleton.takeHit();
		playerHitbox = new Rectangle(playerX + 104.5f ,playerY + 99.75f, 204.25f,152);
		skeletonHitbox = new Rectangle(800 + 240,0 + 196,180,204);
		rekt = new Texture("Untitled (1).png");
		img = new Texture("download.png");
		stateTime = 0f;

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		TextureRegion currentFrame;
		TextureRegion skeletonFrame = skeleton.idleAnimation.getKeyFrame(stateTime,true);;
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
			int countfps = count / 3;
			if(playerHitbox.overlaps(skeletonHitbox) && (countfps % 26 == 3
					|| countfps % 26 == 11
					|| countfps % 26 == 18 )){
				skeleton.hp -= player.attack;
				skeletonFrame = skeleton.takeHitAnimation.getKeyFrame(stateTime, true);
			} else{

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
			// CHANGE USING - tommorowt
			playerHitbox.setPosition(playerX - 44.5f ,playerY+ 99.75f);
		}else {
			playerHitbox.setPosition(playerX + 104.5f ,playerY+ 99.75f);
		}
		batch.begin();
		batch.draw(img, 0, 0);
		if(flip){
			batch.draw(rekt, playerX - 44.5f,playerY + 99.75f, 204.25f,152);
		} else{
			batch.draw(rekt, playerX + 104.5f,playerY + 99.75f, 204.25f,152);
		}
		batch.draw(currentFrame, flip ? playerX+266 : playerX, playerY, flip ? -380 : 380, 380);
		batch.draw(skeletonFrame, 800, 0,600,600);
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
