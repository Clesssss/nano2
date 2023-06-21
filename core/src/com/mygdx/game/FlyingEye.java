package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class FlyingEye extends Character{
    Animation<TextureRegion> flightAnimation;
    private Animation<TextureRegion> flightAnimation2;
    private Animation<TextureRegion> attackAnimation;
    Animation<TextureRegion> deathAnimation;
    Animation<TextureRegion> takeHitAnimation;
    private TextureRegion currentFrame;
    private float deathStateTime;
    private float takeHitStateTime;
    private boolean takeHit;
    private float stateTime;
    private int posX;
    private int posY;
    private Rectangle hitbox;
    public FlyingEye(int hp, int attack) {
        super(hp, attack);
        flight();
        death();
        takeHit();
        stateTime = 0;
        deathStateTime = 0;
        takeHitStateTime = 0;
        takeHit = false;
    }

    public boolean isTakeHit() {
        return takeHit;
    }

    public void setTakeHit(boolean takeHit) {
        this.takeHit = takeHit;
    }

    public float getDeathStateTime() {
        return deathStateTime;
    }

    public void setDeathStateTime(float deathStateTime) {
        this.deathStateTime = deathStateTime;
    }
    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public float getTakeHitStateTime() {
        return takeHitStateTime;
    }

    public void setTakeHitStateTime(float takeHitStateTime) {
        this.takeHitStateTime = takeHitStateTime;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }
    public void update(float deltaTime){
        stateTime += deltaTime;
    }
    public void updateDeath(float deltaTime){
        deathStateTime += deltaTime;
    }
    public boolean isDeathFinished(){
        return deathAnimation.isAnimationFinished(deathStateTime);
    }
    void flight(){
        Texture flightSheet = new Texture("Monsters_Creatures_Fantasy/Flying eye/Flight.png");
        TextureRegion[][] tmpFlight = TextureRegion.split(flightSheet,
                flightSheet.getWidth()/ 4,
                flightSheet.getHeight());
        TextureRegion[] flightFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                flightFrames[index++] = tmpFlight[i][j];
            }
        }
        flightAnimation = new Animation<TextureRegion>(0.05f, flightFrames);
    }
    void death(){
        Texture deathSheet = new Texture("DeathS.png");
        TextureRegion[][] tmpDeath = TextureRegion.split(deathSheet,
                deathSheet.getWidth()/4,
                deathSheet.getHeight());
        TextureRegion[] deathFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                deathFrames[index++] = tmpDeath[i][j];
            }
        }
        deathAnimation = new Animation<TextureRegion>(0.2f, deathFrames);
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
        takeHitAnimation = new Animation<TextureRegion>(0.1f, takeHitFrames);
    }

    @Override
    public void isAttacked(int damage) {

    }

    @Override
    void attack(Character attackedCharacter) {

    }
}
