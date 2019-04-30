package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    int health = 10;
    Vector2 position;
    Sprite sprite;
    Texture texture;
    public Rectangle selfCollider;
    Rectangle hitBox;
    public Player target;
    Color originColor;
    public Enemy(Vector2 position){
        this.position = position;
        Init();
    }

    public Enemy(float x, float y){
        position = new Vector2(x, y);
        Init();
    }

    public void Init(){
        texture = new Texture("Enemy1.png");
        sprite = new Sprite(texture);
        //sprite.setScale(2);
        sprite.setPosition(position.x, position.y);
        selfCollider = new Rectangle(position.x, position.y, 32, 32);
        originColor = sprite.getColor();
    }

    public void render(float dt, SpriteBatch batch){
        sprite.draw(batch);
        sprite.setColor(1,1,1,1);
        update(dt);
    }

    public void update(float dt){

    }

    public void TakeDamage(int damage){
        health -= damage;
        sprite.setColor(1,0,0,1);
    }
}
