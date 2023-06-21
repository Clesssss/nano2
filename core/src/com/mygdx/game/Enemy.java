package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Enemy extends Character {
    protected Animation<TextureRegion> deathAnimation;
    protected Animation<TextureRegion> takeHitAnimation;
    protected float deathStateTime;
    protected boolean takeHit;
    public Enemy(int hp, int attack) {
        super(hp, attack);
        stateTime = 0;
        deathStateTime = 0;
        takeHit = false;
        death();
        takeHit();
    }
    abstract void death();
    abstract void takeHit();
    public void updateDeath(float deltaTime){
        deathStateTime += deltaTime;
    }
    public boolean isDeathFinished(){
        return deathAnimation.isAnimationFinished(deathStateTime);
    }

}
