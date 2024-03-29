package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Skeleton extends Enemy {

    public Skeleton(int hp, int attack) {
        super(hp, attack);
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

    @Override
    void walk() {

    }

    @Override
    void attack() {

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
        hp -= damage;
    }
    @Override
    void attack(Character attackedCharacter) {

    }
}
