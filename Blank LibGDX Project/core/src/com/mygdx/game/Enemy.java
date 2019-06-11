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
        speed = MathUtils.random(1f,3f);
        //velocity = new Vector2(speed, speed);
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
            randomStopPosition = new Vector2(MathUtils.random(-32,32), MathUtils.random(-32,32));
            randomPositionChangeTimer = stopPositionChangeRate;
        }else{
            randomPositionChangeTimer -= dt;
        }
        sprite.setPosition(position.x, position.y);
        selfCollider.setPosition(position.x, position.y);
        takeDamageForDuration(1f, dt);
        AttackDuration(1.2f, dt);
        selfCollider.setPosition(position.x, position.y);

        CheckForBounds();

        if(velocity.x > 0){
            MoveRight();
        }else if(velocity.x < 0){
            MoveLeft();
        }
        else{
            MoveRight();
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

    void CheckForBounds(){
        if(position.x <= 0){
            position.x = 0;
        }
        if(position.x > GameScreen.mapBoundX - 100){
            position.x = GameScreen.mapBoundX - 100;
        }
        if(position.y <= 0){
            position.y = 0;
        }
        if(position.y > GameScreen.mapBoundY){
            position.y = GameScreen.mapBoundY;
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
                resetToIdleStart();
            }
        }
    }

    public void resetToIdleStart(){

    }

    public void TakeDamage(int damage){
        health -= damage;
        hitFXs.add(new HitFX(new Vector2(position.x, position.y)));
        //sprite.setColor(1,0,0,1);
        takingDamage = true;

    }

    public void MoveLeft(){

    }

    public void MoveRight(){

    }

    public void EnemyTrace(float x,float y){
        if(EnemyCheckAttack(x,y) == true){
            Attack = true;
            //velocity = Vector2.Zero;

        }
        //pixel的坐标有问题，player的坐标和enemy的坐标不一致
        else if(EnemyDetect(x,y) == true && detect == false && Attack == false){
            detect = true;
        }
        if(detect == true && Attack == false) {
                Walk = true;
                if (position.x - (x + randomStopPosition.x) > 0) {
                    velocity.x = -speed;
                    position.x += velocity.x;
                } else if (position.x - (x + randomStopPosition.x) < 0) {
                    velocity.x = speed;
                    position.x += velocity.x;
                }
                else {
                    velocity = Vector2.Zero;
                    //position.x += velocity.x;
                    Walk = false;
                }

                if (position.y - y > 0) {
                    velocity.y = - speed;
                    position.y += velocity.y;
                } else if(position.y - y < 0){
                    velocity.y = speed;
                    position.y += velocity.y;
                }
                else{
                    velocity = Vector2.Zero;
                    //position.y += velocity.y;
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
        if(Vector2.dst(position.x, position.y, x, y) < 30){
            return true;
        }else return false;
    }
}

/*
  if (Math.abs(position.x - x - diffx) >= Math.abs(position.y - y - diffy)) {
                Walk = true;
                if(Math.abs(position.x - x - diffx)<40){
                    Attack = true;
                    return;
                }
                if (position.x - x - diffx > 0 ) {
                    MoveLeft();
                    position.x = position.x - speed;
                }
                if (position.x - x - diffx < 0 ){
                    MoveRight();
                    position.x = position.x + speed;
                }
            }
            else {
                if (position.y - y - diffy > 0) {
                    position.y = position.y - speed;
                } else {
                    position.y = position.y + speed;
                }
            }
 */


