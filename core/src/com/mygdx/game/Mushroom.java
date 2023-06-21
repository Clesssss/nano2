package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Mushroom extends Character{
    Animation<TextureRegion> mIdleAnimation;
    Animation<TextureRegion> mDeadAnimation;
    Animation<TextureRegion> mIsHitAnimation;
    private Animation<TextureRegion>mWalkAnimation;
    private Animation<TextureRegion> mAtkAnimation;
    private TextureRegion mCurrFrame;
    private float mDeathTime;
    private float mIsHitTime;
    private boolean mIsHit;
    private float mSTime;
    private int x;
    private int y;
    private com.badlogic.gdx.math.Rectangle mHitArea;

    public Mushroom(int hp,int attck){
        super(hp,attck);
        mSTime = 0;
        mIsHit=false;
        mIsHitTime=0;
        mDeathTime=0;
        idling();
        died();
        takeHit();
    }

    public Animation<TextureRegion> getmWalkAnimation() {
        return mWalkAnimation;
    }

    public void setmWalkAnimation(Animation<TextureRegion> mWalkAnimation) {
        this.mWalkAnimation = mWalkAnimation;
    }

    public Animation<TextureRegion> getmAtkAnimation() {
        return mAtkAnimation;
    }

    public void setmAtkAnimation(Animation<TextureRegion> mAtkAnimation) {
        this.mAtkAnimation = mAtkAnimation;
    }

    public TextureRegion getmCurrFrame() {
        return mCurrFrame;
    }

    public void setmCurrFrame(TextureRegion mCurrFrame) {
        this.mCurrFrame = mCurrFrame;
    }

    public float getmDeathTime() {
        return mDeathTime;
    }

    public void setmDeathTime(float mDeathTime) {
        this.mDeathTime = mDeathTime;
    }

    public float getmIsHitTime() {
        return mIsHitTime;
    }

    public void setmIsHitTime(float mIsHitTime) {
        this.mIsHitTime = mIsHitTime;
    }

    public boolean ismIsHit() {
        return mIsHit;
    }

    public void setmIsHit(boolean mIsHit) {
        this.mIsHit = mIsHit;
    }

    public float getmSTime() {
        return mSTime;
    }

    public void setmSTime(float mSTime) {
        this.mSTime = mSTime;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getmHitArea() {
        return mHitArea;
    }
    public void update(float deltaTime){
        mDeathTime += deltaTime;
    }
    public void updateDeath(float deltaTime){
        mDeathTime += deltaTime;
    }

    public void setmHitArea(Rectangle mHitArea) {
        this.mHitArea = mHitArea;
    }

    public void idling(){
        int index =0;
        Texture idleSheet = new Texture("Monsters_Creatures_Fantasy/Mushroom/Idle.png");
        TextureRegion[][] tmpIdle = TextureRegion.split(idleSheet, idleSheet.getWidth()/4, idleSheet.getHeight());
        TextureRegion[] idleFrame = new TextureRegion[4];
        for (int i=0;i<1;i++){
            for (int j=0;j<4;j++){
                idleFrame[index++] = tmpIdle[i][j];
            }
        }
        mIdleAnimation = new Animation<>(0.05f,idleFrame);
    }

    public void died(){
        int index=0;
        Texture deadSheet = new Texture("Monsters_Creatures_Fantasy/Goblin/Death.png");
        TextureRegion[][] tmpdead = TextureRegion.split(deadSheet, deadSheet.getWidth()/4, deadSheet.getHeight());
        TextureRegion[] deadFrame = new TextureRegion[4];
        for (int i=0;i<1;i++){
            for (int j=0;j<4;j++){
                deadFrame[index++] = tmpdead[i][j];
            }
        }
        mDeadAnimation = new Animation<>(0.02f,deadFrame);
    }
    public void takeHit(){
        int index=0;
        Texture hitSheet = new Texture("Monsters_Creatures_Fantasy/Goblin/Take Hit.png");
        TextureRegion[][] tmphit = TextureRegion.split(hitSheet, hitSheet.getWidth()/4, hitSheet.getHeight());
        TextureRegion[] hitFrame = new TextureRegion[4];
        for (int i=0;i<1;i++){
            for (int j=0;j<4;j++){
                hitFrame[index++] = tmphit[i][j];
            }
        }
        mIsHitAnimation = new Animation<>(0.1f,hitFrame);
    }

    @Override
    public void isAttacked(int damage) {

    }

    @Override
    void attack(Character attackedCharacter) {

    }
}
