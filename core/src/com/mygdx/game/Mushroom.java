package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class Mushroom extends Enemy{

    public Mushroom(int hp,int attck){
        super(hp,attck);
    }

    @Override
    void death() {
        int index=0;
        Texture deadSheet = new Texture("Monsters_Creatures_Fantasy/Mushroom/Death.png");
        TextureRegion[][] tmpdead = TextureRegion.split(deadSheet, deadSheet.getWidth()/4, deadSheet.getHeight());
        TextureRegion[] deadFrame = new TextureRegion[4];
        for (int i=0;i<1;i++){
            for (int j=0;j<4;j++){
                deadFrame[index++] = tmpdead[i][j];
            }
        }
        deathAnimation = new Animation<>(0.2f,deadFrame);
    }

    @Override
    void takeHit() {
        int index=0;
        Texture hitSheet = new Texture("Monsters_Creatures_Fantasy/Mushroom/Take Hit2.png");
        TextureRegion[][] tmphit = TextureRegion.split(hitSheet, hitSheet.getWidth(), hitSheet.getHeight());
        TextureRegion[] hitFrame = new TextureRegion[1];
        for (int i=0;i<1;i++){
            for (int j=0;j<1;j++){
                hitFrame[index++] = tmphit[i][j];
            }
        }
        takeHitAnimation = new Animation<>(0.1f,hitFrame);
    }

    @Override
    void idle() {
        int index =0;
        Texture idleSheet = new Texture("Monsters_Creatures_Fantasy/Mushroom/Idle.png");
        TextureRegion[][] tmpIdle = TextureRegion.split(idleSheet, idleSheet.getWidth()/4, idleSheet.getHeight());
        TextureRegion[] idleFrame = new TextureRegion[4];
        for (int i=0;i<1;i++){
            for (int j=0;j<4;j++){
                idleFrame[index++] = tmpIdle[i][j];
            }
        }
        idleAnimation = new Animation<>(0.1f,idleFrame);
    }

    @Override
    void walk() {

    }

    @Override
    void attack() {

    }
    @Override
    public void isAttacked(int damage) {

    }

    @Override
    void attack(Character attackedCharacter) {

    }
}
