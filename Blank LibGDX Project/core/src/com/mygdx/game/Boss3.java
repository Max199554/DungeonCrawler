package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Boss3 extends Enemy {

    Texture idle;
    Animation Boss1Idle;

    Texture Move;
    Animation Boss3Move;

    Texture AttackDamage;
    Animation Boss3Attack;

    public Boss3(Vector2 position) {
        super(position);
        AttackDamage = new Texture("Boss3-attack.png");
        Boss3Attack = new Animation(new TextureRegion(AttackDamage),12,1);

        Move = new Texture("Boss3-idle.png");
        Boss3Move = new Animation(new TextureRegion(Move),5,1);

        damage = 10;
    }

    @Override
    public void render(float dt, SpriteBatch batch){
        super.render(dt, batch);
        this.update(dt);
    }

    @Override
    public void Init() {
        idle = new Texture("Boss3-idle.png");
        Boss1Idle = new Animation(new TextureRegion(idle), 5, MathUtils.random(.7f, .9f));
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
            sprite.setRegion(Boss3Attack.getFrame());
            Boss3Attack.update(dt);
            attachAnimationEventAt(Boss3Attack, 6, dt);
        }
        else if(Walk == true){
            sprite.setRegion(Boss3Move.getFrame());
            Boss3Move.update(dt);
        }
        else {
            Boss1Idle.update(dt);
        }
        super.update(dt);
    }

    @Override
    public void changeFacing(){
        if(isFacingRight == true) {
            sprite.setScale(3, 3);
        }
        else {
            sprite.setScale(-3,3);
        }
    }

    @Override
    public void MoveRight(){
        sprite.setScale(3, 3);
    }

}