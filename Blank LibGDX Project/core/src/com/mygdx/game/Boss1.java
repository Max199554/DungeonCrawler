package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Boss1 extends Enemy{
    Texture idle;
    Animation Boss1Idle;

    Texture Move;
    Animation Boss1Move;

    Texture TakeDamage;
    Animation Boss1TakeDamage;

    Texture AttackDamage;
    Animation Boss1Attack;

    public Boss1(Vector2 position) {
        //texture = new Texture("Slime Jump.png");
        super(position);
        AttackDamage = new Texture("Boss1-attack2.png");
        Boss1Attack = new Animation(new TextureRegion(AttackDamage),9,1);

        Move = new Texture("Boss1-move.png");
        Boss1Move = new Animation(new TextureRegion(Move),14,1);
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
    }

    @Override
    public void update(float dt){
        sprite = new Sprite((Boss1Idle.getFrame()));
        if(Attack == true){
            sprite = new Sprite(Boss1Attack.getFrame());
            Boss1Attack.update(dt);
        }
        else if(Walk == true){
            sprite = new Sprite(Boss1Move.getFrame());
            Boss1Move.update(dt);
        }
        else {
            Boss1Idle.update(dt);
        }
        super.update(dt);
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