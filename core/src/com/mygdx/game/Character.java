package com.mygdx.game;

public abstract class Character implements Attackable{
    protected int hp;
    protected int attack;

    abstract void attack(Character attackedCharacter);
    public Character(int hp, int attack) {
        this.hp = hp;
        this.attack = attack;
    }
}
