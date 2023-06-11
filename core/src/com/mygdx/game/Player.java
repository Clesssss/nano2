package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Character{
    private int shield;
    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> walkAnimation;
    Animation<TextureRegion> attackAnimation;
    Animation<TextureRegion> slideAnimation;
    public Player(int hp, int attack) {
        super(hp, attack);
    }
    void walk(){
        Texture walkSheet = new Texture("itch run-Sheet sheet.png");
        TextureRegion[][] tmpWalk = TextureRegion.split(walkSheet,
                walkSheet.getWidth()/ 24,
                walkSheet.getHeight());
        TextureRegion[] walkFrames = new TextureRegion[24];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 24; j++) {
                walkFrames[index++] = tmpWalk[i][j];
            }
        }
        walkAnimation = new Animation<TextureRegion>(0.05f, walkFrames);
    }
    void slide(){
        Texture slideSheet = new Texture("red hood itch free Copy-Sheet flip.png");
        TextureRegion[][] tmpSlide = TextureRegion.split(slideSheet,
                slideSheet.getWidth()/ 12,
                slideSheet.getHeight()/ 11);
        TextureRegion[] tmp = new TextureRegion[12 * 11];
        TextureRegion[] slideFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 12; j++) {
                tmp[index++] = tmpSlide[i][j];
            }
        }
        index = 0;
        for (int i = 52; i < 56; i++) {
            slideFrames[index++] = tmp[i];
        }
        slideAnimation = new Animation<TextureRegion>(0.05f, slideFrames);
    }
    void attack(){
        Texture attackSheet = new Texture("itch light atk sheet-Sheet.png");
        TextureRegion[][] tmpAttack = TextureRegion.split(attackSheet,
                attackSheet.getWidth()/ 26,
                attackSheet.getHeight());
        TextureRegion[] attackFrames = new TextureRegion[26];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 26; j++) {
                attackFrames[index++] = tmpAttack[i][j];
            }
        }
        attackAnimation = new Animation<TextureRegion>(0.05f, attackFrames);
    }
    void idle(){
        Texture idleSheet = new Texture("idle sheet-Sheet.png");
        TextureRegion[][] tmpIdle = TextureRegion.split(idleSheet,
                idleSheet.getWidth()/ 18,
                idleSheet.getHeight());
        TextureRegion[] idleFrames = new TextureRegion[18];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 18; j++) {
                idleFrames[index++] = tmpIdle[i][j];
            }
        }
        idleAnimation = new Animation<TextureRegion>(0.05f, idleFrames);
    }
    @Override
    void attack(Character attackedCharacter) {
        attackedCharacter.hp -= attack;
    }


    @Override
    public void isAttacked(int damage) {

    }
}
