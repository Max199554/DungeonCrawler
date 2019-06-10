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

    Texture idleAndWalk;
    Animation slimeWalkAndIdle;

    Texture TakeDamage;
    Animation slimeTakeDamage;

    Texture AttackDamage;
    Animation slimeAttack;

    public Slime(Vector2 position) {
        //texture = new Texture("Slime Jump.png");
        super(position);
        texture = new Texture("GreenSlime-idle.png");

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
        idleAndWalk = new Texture("GreenSlime-idle.png");
        slimeWalkAndIdle = new Animation(new TextureRegion(idleAndWalk), 7, MathUtils.random(.9f, 1.3f));
        sprite = new Sprite(slimeWalkAndIdle.getFrame());
        super.Init();
    }

    @Override
    public void update(float dt){
        super.update(dt);
        attackRateTimer += dt;
        //sprite.setRegion(slimeWalkAndIdle.getFrame());
        if(takingDamage == true){
            sprite.setRegion(slimeTakeDamage.getFrame());
        }
        else if(Attack == true && attackRateTimer >= attackRate){
           sprite.setRegion(slimeAttack.getFrame());
           slimeAttack.update(dt);
           if(slimeAttack.getFrameNum() == slimeAttack.getRegion().size - 1){
               attackRateTimer = 0;
           }
        }
        else {
            //attackRateTimer = 0;
            slimeWalkAndIdle.update(dt);
            sprite.setRegion(slimeWalkAndIdle.getFrame());
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
}
