package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Minotaur extends Enemy{
    Texture idle;
    Animation MinotaurIdle;

    Texture Move;
    Animation MinotaurMove;

    Texture TakeDamage;
    Animation MinotaurTakeDamage;

    Texture AttackDamage;
    Animation MinotaurAttack;

    public Minotaur(Vector2 position) {
        //texture = new Texture("Slime Jump.png");
        super(position);
        texture = new Texture("Minotaur-idle.png");

        TakeDamage = new Texture("Minotaur-hit.png");
        MinotaurTakeDamage = new Animation(new TextureRegion(TakeDamage), 1, 1);

        AttackDamage = new Texture("Minotaur-attack01.png");
        MinotaurAttack = new Animation(new TextureRegion(AttackDamage),13,1);

        Move = new Texture("Minotaur-move.png");
        MinotaurMove = new Animation(new TextureRegion(Move),3,1);
    }

    @Override
    public void render(float dt, SpriteBatch batch){
        super.render(dt, batch);
        this.update(dt);
    }

    @Override
    public void Init() {
        idle = new Texture("Minotaur-idle.png");
        MinotaurIdle = new Animation(new TextureRegion(idle), 5, MathUtils.random(.7f, .9f));
        sprite = new Sprite(MinotaurIdle.getFrame());
        super.Init();
    }

    @Override
    public void update(float dt){
        changeFacing();
        sprite = new Sprite((MinotaurIdle.getFrame()));
        if(takingDamage == true){
            sprite.setRegion(MinotaurTakeDamage.getFrame());
            MinotaurTakeDamage.update(dt);
        }
        if(Attack == true){
            sprite.setRegion(MinotaurAttack.getFrame());
            MinotaurAttack.update(dt);
            if(MinotaurAttack.getFrameNum() == MinotaurAttack.getRegion().size - 1){
                Attack = false;
            }
        }
        if(Walk == true){
            sprite.setRegion(MinotaurMove.getFrame());
        }
        else {
            MinotaurIdle.update(dt);
        }

        super.update(dt);
    }

    @Override
    public void changeFacing(){
        if(isFacingRight == true) {
            sprite.setScale(1, 1);
        }
        else {
            sprite.setScale(-1,1);
        }
    }

    @Override
    public void MoveRight(){
        sprite.setScale(1, 1);
    }
}
