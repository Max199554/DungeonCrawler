package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    Vector2 position;
    Sprite sprite;
    Texture texture;
    Rectangle attackBox;
    Circle attackCircle;
    float attackRange = 64;
    boolean isFacingRight = true;
    public Player(Vector2 position){
        this.position = position;
        Init();
    }

    public Player(float x, float y){
        position = new Vector2(x, y);
        Init();
    }

    public void Init(){
        texture = new Texture("Player.png");
        sprite = new Sprite(texture);
        sprite.setScale(2);

        attackBox = new Rectangle(position.x + sprite.getTexture().getWidth() * 2,
                position.y, attackRange, attackRange);

    }

    public void render(float dt, SpriteBatch batch){
        sprite.draw(batch);
        sprite.setPosition(position.x, position.y);
        update(dt);
    }

    public void update(float dt){
        attackBox.setPosition(position.x + (isFacingRight == true ? 64 : -32), position.y);
        if(isFacingRight == false){
            sprite.setScale(-2, 2);
            attackBox.x -= sprite.getTexture().getWidth() * 4;
        }
        else{
            sprite.setScale(2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            position.x -= Constant.PLAYER_SPEED * dt;
            isFacingRight = false;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            position.x += Constant.PLAYER_SPEED * dt;
            isFacingRight = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            position.y -= Constant.PLAYER_SPEED * dt;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            position.y += Constant.PLAYER_SPEED * dt;
        }
    }
}
