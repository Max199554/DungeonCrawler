package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Boss1 extends Enemy{
    Texture idle;
    Animation Boss1Idle;


    Texture Move;
    Animation Boss1Move;

    Texture AttackDamage;
    Animation Boss1Attack;

    Texture AttackDamage1;
    Animation Boss1Attack1;

    int randomAttack;

    public Boss1(Vector2 position) {


        super(position);
        AttackDamage = new Texture("Boss1-attack2.png");
        Boss1Attack = new Animation(new TextureRegion(AttackDamage),9,1);

        Move = new Texture("Boss1-move.png");
        Boss1Move = new Animation(new TextureRegion(Move),14,1);

        AttackDamage1 = new Texture("Boss1-attack1.png");
        Boss1Attack1 = new Animation(new TextureRegion(AttackDamage1), 29,1);

        damage = 10;
    }

    @Override
    public void render(float dt, SpriteBatch batch){
        super.render(dt, batch);
        this.update(dt);
    }

    @Override
    public void Init() {
        idle = new Texture("Boss1-idle.png");
        Boss1Idle = new Animation(new TextureRegion(idle), 3, MathUtils.random(.7f, .9f));
        sprite = new Sprite(Boss1Idle.getFrame());
        super.Init();
        selfCollider = new Rectangle(position.x, position.y - sprite.getHeight() / 2, 200, 250);
        attackRange = 300;
        health = maxHealth;
        attackDuration = 1f;

        randomAttack = MathUtils.random(0,2);
    }

    @Override
    public void update(float dt){
        changeFacing();
        fxSpawnOffset.x = isFacingRight ? -sprite.getWidth() : sprite.getWidth();
        if(Attack == true){
            if(randomAttack == 0 ){
                sprite.setRegion(Boss1Attack.getFrame());
                Boss1Attack.update(dt);
                attachAnimationEventAt(Boss1Attack, 6, dt);
            }
            else{
                sprite.setRegion(Boss1Attack1.getFrame());
                Boss1Attack1.update(dt);
                attachAnimationEventAt(Boss1Attack1, 8, dt);
            }
        }
        else if(Walk == true){
            sprite.setRegion(Boss1Move.getFrame());
            Boss1Move.update(dt);
        }
        else {
            Boss1Idle.update(dt);
        }
        super.update(dt);
        sprite.setPosition(isFacingRight == true ? position.x: position.x - sprite.getWidth() * 2, position.y);
        selfCollider.setPosition(position.x, position.y);

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
        sprite.setScale(4, 4);
    }
}
