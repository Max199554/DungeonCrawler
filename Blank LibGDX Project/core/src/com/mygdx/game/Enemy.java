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
import com.badlogic.gdx.utils.DelayedRemovalArray;

public class Enemy {

    float stopPositionChangeRate = 3;
    Vector2 randomStopPosition;


    float randomPositionChangeTimer;
    int health = 10;
    int diffx = 0;
    int diffy = 0;
    float speed = 0;
    Vector2 velocity = Vector2.Zero;
    boolean detect=false;
    Vector2 position;
    Sprite sprite;
    Texture texture;
    public Rectangle selfCollider;
    Rectangle hitBox;
    public Player target;
    Color originColor;
    boolean takingDamage = false;
    boolean Attack = false;
    boolean Walk = false;
    float damageColorTimer = 0;
    float AttackColorTimer = 0;
    DelayedRemovalArray<HitFX> hitFXs = new DelayedRemovalArray<HitFX>();


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
        velocity = new Vector2(MathUtils.random(1f,3f), MathUtils.random(1f,3f));
        speed = MathUtils.random(1f,3f);
    }

    public void render(float dt, SpriteBatch batch){
        sprite.draw(batch);
        for(int i = 0; i < hitFXs.size; i++){
            hitFXs.get(i).render(batch);
            hitFXs.get(i).update(dt);
            if(hitFXs.get(i).fxAnimation.getFrameNum() == 5){
                hitFXs.removeIndex(i);
            }
        }
        update(dt);
    }

    public void update(float dt){

        if(randomPositionChangeTimer <= 0){
            randomStopPosition = new Vector2(MathUtils.random(-64,64), MathUtils.random(-64,64));
            randomPositionChangeTimer = stopPositionChangeRate;
        }else{
            randomPositionChangeTimer -= dt;
        }
        sprite.setPosition(position.x, position.y);
        selfCollider.setPosition(position.x, position.y);
        takeDamageForDuration(1f, dt);
        AttackDuration(1.5f, dt);
        selfCollider.setPosition(position.x, position.y);



        checkEnemyBound();
    }


    void checkEnemyBound(){
        if(position.x <= 0){
            position.x = 0;
        }
        if(position.x > GameScreen.mapBoundX){
            position.x = GameScreen.mapBoundX;
        }
        if(position.y <= 0){
            position.y = 0;
        }
        if(position.y > GameScreen.mapBoundY - 100){
            position.y = GameScreen.mapBoundY - 100;
        }
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

    void AttackDuration(float duration, float dt){
        if(Attack == true){
            AttackColorTimer += dt;
            if(AttackColorTimer <= duration){
            }
            else{
                AttackColorTimer = 0;
                Attack = false;
            }
        }
    }

    public void TakeDamage(int damage){
        hitFXs.add(new HitFX(position));

        health -= damage;
        //sprite.setColor(1,0,0,1);
        takingDamage = true;
    }

    public void MoveLeft(){
    }

    public void MoveRight(){
    }

    public void EnemyTrace(float x,float y){
        if(EnemyCheckAttack(x,y)){
            Attack = true;

        }
        //pixel的坐标有问题，player的坐标和enemy的坐标不一致
        else if(EnemyDetect(x,y) == true && detect == false && Attack == false) {
            detect = true;
        }
        if(detect==true) {
            if (Math.abs(position.x - x ) >= Math.abs(position.y - y - diffy)) {
                Walk = true;
                position.x = position.x + velocity.x;
                if (position.x - x >= 0) {
                    velocity.x = -speed;
                } else if (position.x - (x + randomStopPosition.x) < 0) {
                    velocity.x = speed;
                }
                else{
                    velocity = Vector2.Zero;
                }
            } else {
                if (position.y - y >= 0) {
                    position.y = position.y - speed;
                } else if (position.y - (y + randomStopPosition.y) < 0) {
                    position.y = position.y + speed;
                }
            }
        }
    }

    public boolean EnemyDetect(float x, float y){
        if(Math.sqrt((position.x-x)*(position.x-x)+(position.y-y)*(position.y-y))<=100){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean EnemyCheckAttack(float x, float y){
        if(Vector2.dst(position.x, position.y, x, y) < 50){
            return true;
        }
        else return false;
    }
}
