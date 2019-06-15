package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

    public int damage = 5;

    float detectRange = 100;

    float attackRateTimer = 0;
    protected float attackRate = 2f;
    protected float attackDuration = 1f;
    public float attackRange;
    boolean isFacingRight = true;
    float stopPositionChangeRate = 3;
    Vector2 randomStopPosition;

    float randomPositionChangeTimer;
    public int maxHealth = 200;
    int health = 10;
    int diffx = 0;
    int diffy = 0;
    float speed = 0;
    Vector2 velocity = Vector2.Zero;

    Vector2 fxSpawnOffset = Vector2.Zero;
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

    float attackTime = 0;
    float attackTimerLag = .5f;

    Sound enemyHitSound = Gdx.audio.newSound(Gdx.files.internal("EnemyHit.wav" ));

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
        hitBox = new Rectangle(position.x, position.y, attackRange, attackRange);
    }

    public void render(float dt, SpriteBatch batch){
        sprite.draw(batch);
        selfCollider.setPosition(position.x, position.y);
        sprite.setPosition(position.x, position.y);
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
        attackRateTimer += dt;
        attackTimerLag -= dt;
        if(randomPositionChangeTimer <= 0){
            randomStopPosition = new Vector2(MathUtils.random(-64,64), MathUtils.random(-64,64));
            randomPositionChangeTimer = stopPositionChangeRate;
        }else{
            randomPositionChangeTimer -= dt;
        }

        takeDamageForDuration(1f, dt);
        AttackDuration(attackDuration, dt);
        selfCollider.setPosition(position.x, position.y);
        hitBox.setPosition(position.x, position.y);
        CheckForBounds();

        if(velocity.x > 0.3f){
            isFacingRight = true;
        }else if(velocity.x < -0.3f){
            isFacingRight = false;
        }
        else{
            Walk = false;
        }
        System.out.println(Attack);
    }

    void takeDamageForDuration(float duration, float dt){
        if(takingDamage == true){
            damageColorTimer += dt;
            if(damageColorTimer <= duration){
            }
            else{
                damageColorTimer = 0;
                takingDamage = false;
            }
        }
    }

    //
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

    //during this period, attack animation will be played
    void AttackDuration(float duration, float dt){
        if(Attack == true){
            AttackColorTimer += dt;
            if(AttackColorTimer <= duration){
            }
            else{
                AttackColorTimer = 0;
                attackRateTimer = 0;
                Attack = false;
                resetToIdleStart();
            }
        }
    }

    public void resetToIdleStart(){

    }

    public void TakeDamage(int damage){
        enemyHitSound.play();
        health -= damage;
        hitFXs.add(new HitFX(new Vector2(position.x + fxSpawnOffset.x, position.y + fxSpawnOffset.y)));
        //sprite.setColor(1,0,0,1);
        takingDamage = true;
    }

    public void changeFacing(){

    }

    public void MoveRight(){

    }

    //for the enemy to follow player and attack player in certain range
    public void EnemyTrace(float x,float y){
        if(EnemyCheckAttack(x,y) == true && attackRateTimer > attackRate){
            Attack = true;
        }
        if(EnemyDetect(x,y) == true && detect == false){
            detect = true;
        }
        if(detect == true && Attack == false) {
            if (Math.abs(position.x - x - diffx) >= Math.abs(position.y - y - diffy)){
                Walk = true;

                if (position.x - (x - randomStopPosition.x) > 0.3f) {
                    velocity.x = -speed;
                    position.x += velocity.x;
                } else if (position.x - x < -0.3f) {
                    velocity.x = speed;
                    position.x += velocity.x;
                }
                else {
                    velocity.x = 0;
                    Walk = false;
                }
            }else
                {
                    if (position.y - (y - randomStopPosition.y) > 0) {
                        velocity.y = - speed;
                        position.y += velocity.y;
                    } else if(position.y - (y - 48) < 0){
                        velocity.y = speed;
                        position.y += velocity.y;
                    }
                    else{
                        velocity.y = 0;
                    }
                }
        }
    }

    public void ApplyDamage(int damage){
        //System.out.println(target);
        System.out.println(hitBox);

            if(hitBox.overlaps(target.selfBox) && Attack == true ){
                target.TakeDamge(damage);
                MyGdxGame.gameScreen.ScreenShake(10);

        }
    }

    public boolean EnemyDetect(float x, float y){
        if(Math.sqrt((position.x-x)*(position.x-x)+(position.y-y)*(position.y-y)) <= detectRange){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean EnemyCheckAttack(float x, float y){
        if(Vector2.dst(position.x, position.y, x, y) < 100){
            return true;
        }else return false;
    }

    public void attachAnimationEventAt(Animation animation, int frameNum, float dt){
        if(animation.getFrameNum() == frameNum){
            attackTime += dt;
            if(attackTime <= dt){
                ApplyDamage(damage);
            }
        }
        if(animation.getFrameNum() == animation.getRegion().size - 1 ){
            animation.setFrameNum(0);
            if(attackTimerLag < 0){
                Attack = false;
                attackTime = 0;
                attackTimerLag = .5f;
            }
            else{
                animation.setFrameNum(animation.getRegion().size - 1);
                sprite.setRegion(animation.getFrame());
            }
        }
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


