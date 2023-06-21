package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Goblin extends Character{
    Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> walkAnimation;
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
    public Goblin(int hp, int attack) {
        super(hp, attack);
        idle();
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

    public Animation<TextureRegion> getIdleAnimation() {
        return idleAnimation;
    }

    public void setIdleAnimation(Animation<TextureRegion> idleAnimation) {
        this.idleAnimation = idleAnimation;
    }

    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    public void setWalkAnimation(Animation<TextureRegion> walkAnimation) {
        this.walkAnimation = walkAnimation;
    }

    public Animation<TextureRegion> getAttackAnimation() {
        return attackAnimation;
    }

    public void setAttackAnimation(Animation<TextureRegion> attackAnimation) {
        this.attackAnimation = attackAnimation;
    }

    public Animation<TextureRegion> getDeathAnimation() {
        return deathAnimation;
    }

    public void setDeathAnimation(Animation<TextureRegion> deathAnimation) {
        this.deathAnimation = deathAnimation;
    }

    public Animation<TextureRegion> getTakeHitAnimation() {
        return takeHitAnimation;
    }

    public void setTakeHitAnimation(Animation<TextureRegion> takeHitAnimation) {
        this.takeHitAnimation = takeHitAnimation;
    }

    public com.badlogic.gdx.math.Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(com.badlogic.gdx.math.Rectangle hitbox) {
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
    void idle(){
        Texture idleSheet = new Texture("Monsters_Creatures_Fantasy/Goblin/Idle.png");
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
        Texture deathSheet = new Texture("Monsters_Creatures_Fantasy/Goblin/Death.png");
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
        Texture takeHitSheet = new Texture("Monsters_Creatures_Fantasy/Goblin/Take Hit.png");
        TextureRegion[][] tmpTakeHit = TextureRegion.split(takeHitSheet,
                takeHitSheet.getWidth()/2,
                takeHitSheet.getHeight());
        TextureRegion[] takeHitFrames = new TextureRegion[2];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
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
