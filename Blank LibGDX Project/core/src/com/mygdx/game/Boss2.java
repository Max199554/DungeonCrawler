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

    public Boss2 (Vector2 position) {

        super(position);
        AttackDamage = new Texture("Boss2-attack.png");
        Boss2Attack = new Animation(new TextureRegion(AttackDamage),5,1);

        Move = new Texture("Boss2-idle.png");
        Boss2Move = new Animation(new TextureRegion(Move),2,1);

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
        super.Init();
        selfCollider = new Rectangle(position.x, position.y, 200, 120);
        attackRange = 300;
        health = maxHealth;
        attackDuration = 1f;
    }

    @Override
    public void update(float dt){
        changeFacing();
        if(Attack == true){
            sprite.setRegion(Boss2Attack.getFrame());
            Boss2Attack.update(dt);
            attachAnimationEventAt(Boss2Attack, 1, dt);
        }
        else if(Walk == true){
            sprite.setRegion(Boss2Move.getFrame());
            Boss2Move.update(dt);
        }
        else {
            Boss1Idle.update(dt);
        }
        super.update(dt);
    }

    @Override
    public void changeFacing(){
        if(isFacingRight == true) {
            sprite.setScale(2, 2);
        }
        else {
            sprite.setScale(-2,2);
        }
    }

    @Override
    public void MoveRight(){
        sprite.setScale(2, 2);
    }
}