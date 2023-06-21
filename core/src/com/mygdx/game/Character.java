package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Character implements Attackable{
    protected Animation<TextureRegion> idleAnimation;
    protected Animation<TextureRegion> walkAnimation;
    protected Animation<TextureRegion> attackAnimation;
    protected float stateTime;
    protected int posX;
    protected int posY;
    protected Rectangle hitbox;
    protected int hp;
    protected int attack;
    protected TextureRegion currentFrame;

    abstract void attack(Character attackedCharacter);
    public Character(int hp, int attack) {
        this.hp = hp;
        this.attack = attack;
        idle();
        walk();
        attack();
    }
    abstract void idle();
    abstract void walk();
    abstract void attack();
    public void update(float deltaTime){
        stateTime += deltaTime;
    }
}
