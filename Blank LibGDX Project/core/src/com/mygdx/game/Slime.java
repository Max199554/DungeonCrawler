package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Slime extends Enemy {
    Texture idleAndWalk;
    Animation slimeWalkAndIdle;

    Texture TakeDamage;
    Animation slimeTakeDamage;

    Texture AttackDamage;
    Animation slimeAttack;

    public Slime(Vector2 position) {
        //texture = new Texture("Slime Jump.png");
        super(position);
        //texture = new Texture("GreenSlime-idle.png");

        TakeDamage = new Texture("GreenSlime-hit.png");
        slimeTakeDamage = new Animation(new TextureRegion(TakeDamage), 2, 1);

        AttackDamage = new Texture("GreenSlime-Attack.png");
        slimeAttack = new Animation(new TextureRegion(AttackDamage),11,1);
    }

    @Override
    public void render(float dt, SpriteBatch batch){
        super.render(dt, batch);
        this.update(dt);
    }

    @Override
    public void Init() {
        idleAndWalk = new Texture("GreenSlime-idle.png");
        slimeWalkAndIdle = new Animation(new TextureRegion(idleAndWalk), 7, MathUtils.random(.7f, .9f));
        sprite = new Sprite(slimeWalkAndIdle.getFrame());
        super.Init();
    }

    @Override
    public void update(float dt){
        sprite = new Sprite((slimeWalkAndIdle.getFrame()));
        if(takingDamage == true){
            sprite = new Sprite(slimeTakeDamage.getFrame());
        }
        if(Attack == true){
            sprite = new Sprite(slimeAttack.getFrame());
        }
        else {
            slimeWalkAndIdle.update(dt);
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
