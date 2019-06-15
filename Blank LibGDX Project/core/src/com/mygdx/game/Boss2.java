package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Boss2 extends Enemy {

    Texture idle;
    Animation Boss1Idle;

    Texture Move;
    Animation Boss2Move;

    Texture AttackDamage;
    Animation Boss2Attack;

    Texture AttackDamage1;
    Animation Boss2Attack1;

    Texture AttackDamage2;
    Animation Boss2Attack2;

    float randomCountTimer;
    float randomChangeRate = 5;

    int randomAttack;

    public Boss2 (Vector2 position) {

        super(position);
        AttackDamage = new Texture("Boss2-attack.png");
        Boss2Attack = new Animation(new TextureRegion(AttackDamage),5,1);

        Move = new Texture("Boss2-idle.png");
        Boss2Move = new Animation(new TextureRegion(Move),2,1);

        AttackDamage1 = new Texture("Boss2-attack2.png");
        Boss2Attack1 = new Animation(new TextureRegion(AttackDamage1), 6,1f);

        AttackDamage2 = new Texture("Boss2-attack3.png");
        Boss2Attack2 = new Animation(new TextureRegion(AttackDamage2), 6,1f);

        damage = 10;
    }

    public void render(float dt, SpriteBatch batch){
        super.render(dt, batch);
        this.update(dt);
    }

    @Override
    public void Init() {
        idle = new Texture("Boss2-idle2.png");
        Boss1Idle = new Animation(new TextureRegion(idle), 2, MathUtils.random(.7f, .9f));
        sprite = new Sprite(Boss1Idle.getFrame());
        attackRange = 2000;
        detectRange = 1000;
        maxHealth = 70;
        super.Init();
        selfCollider = new Rectangle(position.x, position.y, 200, 120);

        health = maxHealth;
        attackDuration = 1.3f;
        randomAttack = MathUtils.random(0,3);

    }

    @Override
    public void update(float dt){
        randomCountTimer += dt;
        if(randomCountTimer >= randomChangeRate){
            randomAttack = MathUtils.random(0, 3);
            randomCountTimer = 0;
        }
        changeFacing();
        if(Attack == true){
            if(randomAttack==0){
                sprite.setRegion(Boss2Attack.getFrame());
                Boss2Attack.update(dt);
                attachAnimationEventAt(Boss2Attack, 1, dt);
            }
            else if(randomAttack==1){
                sprite.setRegion(Boss2Attack1.getFrame());
                Boss2Attack1.update(dt);
                attachAnimationEventAt(Boss2Attack1, 2, dt);
            }
            else{
                sprite.setRegion(Boss2Attack2.getFrame());
                Boss2Attack2.update(dt);
                attachAnimationEventAt(Boss2Attack2, 3, dt);
            }

        }
        else if(Walk == true){
            sprite.setRegion(Boss2Move.getFrame());
            Boss2Move.update(dt);
        }
        else {
            Boss1Idle.update(dt);
        }
        super.update(dt);
        sprite.setPosition(isFacingRight == true ? position.x : position.x - sprite.getWidth(), position.y);
        selfCollider.setPosition(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() * 2);

    }

    @Override
    public void changeFacing(){
        if(isFacingRight == true) {
            sprite.setScale(4, 4);
        }
        else {
            sprite.setScale(-4,4);
        }
    }

    @Override
    public void MoveRight(){
        sprite.setScale(2, 2);
    }
}
