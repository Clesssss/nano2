package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
    private Rectangle mHitArea;

    @Override
    public void isAttacked(int damage) {

    }

    @Override
    void attack(Character attackedCharacter) {

    }
}
