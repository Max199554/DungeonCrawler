package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Slime extends Enemy {
    float attackRateTimer = 0;
    float attackRate = 1f;

    float attackTime = 0;


    Texture idleAndWalk;
    Animation slimeWalkAndIdle;

    Texture TakeDamage;
    Animation slimeTakeDamage;

    Texture AttackDamage;
    Animation slimeAttack;

    float attackTimerLag = .5f;
    public Slime(Vector2 position) {
        //texture = new Texture("Slime Jump.png");
        super(position);
        //texture = new Texture("GreenSlime-idle.png");

        TakeDamage = new Texture("GreenSlime-hit.png");
        slimeTakeDamage = new Animation(new TextureRegion(TakeDamage), 2, 1);

        AttackDamage = new Texture("GreenSlime-Attack.png");
        slimeAttack = new Animation(new TextureRegion(AttackDamage),11,2f);
    }

    @Override
    public void render(float dt, SpriteBatch batch){
        super.render(dt, batch);
        this.update(dt);
    }

    @Override
    public void Init() {
        attackRange = 200;
        idleAndWalk = new Texture("GreenSlime-idle.png");
        slimeWalkAndIdle = new Animation(new TextureRegion(idleAndWalk), 7, MathUtils.random(.9f, 1.3f));
        sprite = new Sprite(slimeWalkAndIdle.getFrame());
        super.Init();
        health = 20;

    }

    @Override
    public void update(float dt){
        attackRateTimer += dt;
        //sprite.setRegion(slimeWalkAndIdle.getFrame());
        if(takingDamage == true){
            sprite.setRegion(slimeTakeDamage.getFrame());
            slimeTakeDamage.update(dt);

        }
        else if(Attack == true ){
           sprite.setRegion(slimeAttack.getFrame());
           slimeAttack.update(dt);

        }
        else {

            sprite.setRegion(slimeWalkAndIdle.getFrame());
            slimeWalkAndIdle.update(dt);
        }
        super.update(dt);
        attachAnimationEventAt(slimeAttack, 6, dt);
    }
    void attachAnimationEventAt(Animation animation, int frameNum, float dt){
        if(animation.getFrameNum() == frameNum){
            attackTime += dt;
            if(attackTime <= dt){
                ApplyDamage(5);
            }
        }
        if(animation.getFrameNum() == animation.getRegion().size - 1 ){
            animation.setFrameNum(0);
            if(attackTimerLag < 0){
                Attack = false;
                attackTime = 0;
                attackTimerLag = .5f;
            }
            else{
                animation.setFrameNum(animation.getRegion().size - 1);
                sprite.setRegion(animation.getFrame());
            }
        }
    }

    @Override
    public void MoveLeft(){
        sprite.setScale(-1,1);
    }

    @Override
    public void MoveRight(){
        sprite.setScale(1, 1);
    }

    @Override
    public void resetToIdleStart(){
        slimeWalkAndIdle.setFrameNum(0);
        sprite.setRegion(slimeWalkAndIdle.getFrame());
    }
}
