package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Skeleton extends Character {
    Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> attackAnimation;
    Animation<TextureRegion> deathAnimation;
    Animation<TextureRegion> takeHitAnimation;
    private float deathStateTime;
    private float takeHitStateTime;
    private float stateTime;
    private int posX;
    private int posY;
    private Rectangle hitbox;
    Skeleton(){
        super();

    }
    public Skeleton(Rectangle hitbox, int hp, int attack) {
        super(hp, attack);
        this.hitbox = hitbox;
        posX = 800;
        posY = 0;
        idle();
        death();
        takeHit();
        stateTime = 0;
    }
    public void update(float deltaTime){
        deathStateTime += deltaTime;
    }
    public void draw(SpriteBatch batch){
        batch.draw(idleAnimation.getKeyFrame(stateTime), posX, posY,600,600);
    }
    public boolean isDeathFinished(){
        return deathAnimation.isAnimationFinished(stateTime);
    }
    void idle(){
        Texture idleSheet = new Texture("IdleS.png");
        TextureRegion[][] tmpIdle = TextureRegion.split(idleSheet,
                idleSheet.getWidth()/ 4,
                idleSheet.getHeight());
        TextureRegion[] idleFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                idleFrames[index++] = tmpIdle[i][j];
            }
        }
        idleAnimation = new Animation<TextureRegion>(0.05f, idleFrames);
    }
    void death(){
        Texture deathSheet = new Texture("DeathS.png");
        TextureRegion[][] tmpDeath = TextureRegion.split(deathSheet,
                deathSheet.getWidth(),
                deathSheet.getHeight());
        TextureRegion[] deathFrames = new TextureRegion[1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                deathFrames[index++] = tmpDeath[i][j];
            }
        }
        deathAnimation = new Animation<TextureRegion>(1f, deathFrames);
    }
    void takeHit(){
        Texture takeHitSheet = new Texture("Take HitS.png");
        TextureRegion[][] tmpTakeHit = TextureRegion.split(takeHitSheet,
                takeHitSheet.getWidth(),
                takeHitSheet.getHeight());
        TextureRegion[] takeHitFrames = new TextureRegion[1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                takeHitFrames[index++] = tmpTakeHit[i][j];
            }
        }
        takeHitAnimation = new Animation<TextureRegion>(1f, takeHitFrames);
    }

    @Override
    public void isAttacked(int damage) {

    }

    @Override
    void attack(Character attackedCharacter) {

    }
}
