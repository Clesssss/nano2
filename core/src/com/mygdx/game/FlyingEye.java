package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FlyingEye extends Enemy{
    public FlyingEye(int hp, int attack) {
        super(hp, attack);
    }
    @Override
    void idle(){
        Texture flightSheet = new Texture("Monsters_Creatures_Fantasy/Flying eye/Flight.png");
        TextureRegion[][] tmpFlight = TextureRegion.split(flightSheet,
                flightSheet.getWidth()/ 8,
                flightSheet.getHeight());
        TextureRegion[] flightFrames = new TextureRegion[8];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 8; j++) {
                flightFrames[index++] = tmpFlight[i][j];
            }
        }
        idleAnimation = new Animation<TextureRegion>(0.1f, flightFrames);
    }
    @Override
    void walk() {

    }

    @Override
    void attack() {

    }
    void death(){
        Texture deathSheet = new Texture("Monsters_Creatures_Fantasy/Flying eye/Death.png");
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
        Texture takeHitSheet = new Texture("Monsters_Creatures_Fantasy/Flying eye/Take Hit2.png");
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
        hp -= damage;
    }

    @Override
    void attack(Character attackedCharacter) {

    }
}
