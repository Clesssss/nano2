package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Skeleton extends Character {
    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> walkAnimation;
    Animation<TextureRegion> attackAnimation;
    Animation<TextureRegion> takeHitAnimation;
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
        takeHitAnimation = new Animation<TextureRegion>(0.05f, takeHitFrames);
    }

    @Override
    public void isAttacked(int damage) {

    }

    @Override
    void attack(Character attackedCharacter) {

    }
}
