package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Enemy {

    float stopPositionChangeRate = 3;
    Vector2 randomStopPosition;


    float randomPositionChangeTimer;
    int health = 10;
    int diffx = 0;
    int diffy = 0;
    float speed = 0;
    Vector2 position;
    Sprite sprite;
    Texture texture;
    public Rectangle selfCollider;
    Rectangle hitBox;
    public Player target;
    Color originColor;
    boolean takingDamage = false;
    float damageColorTimer = 0;

    public Enemy(Vector2 position){

        this.position = position;
        Init();
    }

    public Enemy(float x, float y){

        position = new Vector2(x, y);
        Init();
    }

    public void Init(){
        sprite.setPosition(position.x, position.y);
        selfCollider = new Rectangle(position.x, position.y, 32, 32);
        originColor = sprite.getColor();
        speed = MathUtils.random(1f,3f);
    }

    public void render(float dt, SpriteBatch batch){
        sprite.draw(batch);

        update(dt);
    }

    public void update(float dt){

        if(randomPositionChangeTimer <= 0){
            randomStopPosition = new Vector2(MathUtils.random(-70f,70f), MathUtils.random(-70f,70f));
            randomPositionChangeTimer = stopPositionChangeRate;
        }else{
            randomPositionChangeTimer -= dt;
        }
        sprite.setPosition(position.x, position.y);
        selfCollider.setPosition(position.x, position.y);
        takeDamageForDuration(.3f, dt);
    }

    void takeDamageForDuration(float duration, float dt){
        if(takingDamage == true){
            damageColorTimer += dt;
            if(damageColorTimer <= duration){
                //sprite.setColor(1,0,0,1);
            }
            else{
                damageColorTimer = 0;
                //sprite.setColor(1,1,1,1);
                takingDamage = false;
            }

        }
    }

    public void TakeDamage(int damage){
        health -= damage;
        //sprite.setColor(1,0,0,1);
        takingDamage = true;
    }

    public void MoveLeft(){
    }

    public void MoveRight(){
    }

    public void EnemyTrade(float x,float y){

        //pixel的坐标有问题，player的坐标和enemy的坐标不一致
        if(Vector2.dot(position.x, position.y, x, y) > 100) {
            if (Math.abs(position.x - x ) >
                    Math.abs(position.y - y )) {
                if (position.x - (x + randomStopPosition.x) > 1) {
                    // MoveLeft();
                    position.x = position.x - speed;
                } else if (position.x - (x + randomStopPosition.x) < 1) {
                    //MoveRight();
                    position.x = position.x + speed;
                }
            } else if (Math.abs(position.x - x ) <
                    Math.abs(position.y - y )) {
                if (position.y - (y + randomStopPosition.y) > 1) {
                    position.y = position.y - speed;
                } else if (position.y - (y + randomStopPosition.y) < 1) {
                    position.y = position.y + speed;
                }
            }
        }
    }

}
