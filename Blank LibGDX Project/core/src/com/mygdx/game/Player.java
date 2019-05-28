package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;

import sun.awt.EventQueueDelegate;

public class Player {

    PlayerState playerState;
    Vector2 position;
    Sprite sprite;
    Texture texture;
    int health;
    Rectangle attackBox;
    boolean attacking = false;
    Circle attackCircle;
    DelayedRemovalArray<Enemy> enemiesToAttack;
    float attackRangeX = 70;
    float attackRangeY = 35;
    boolean isFacingRight = true;
    public float playerSpeed = Constant.PLAYER_SPEED;

    float attackTime = 0;

    Texture idle;
    Animation idleAnimation;

    Texture move;
    Animation moveAnimation;

    Texture attack1;
    Animation attack1Animation;

    Texture attack2;
    Animation attack2Animation;

    Texture attack3;
    Animation attack3Animation;

    Texture attack4;
    Animation attack4Animation;

    public Player(Vector2 position){
        this.position = position;
        Init();
    }

    public Player(float x, float y){
        position = new Vector2(x, y);
        Init();
    }

    public void Init(){
        playerState = PlayerState.IDLE;
        texture = new Texture("Player.png");
        sprite = new Sprite(texture);
        sprite.setScale(2);


        attackBox = new Rectangle(position.x,
                position.y, attackRangeX, attackRangeY);

        idle = new Texture("PlayerIdle.png");
        idleAnimation = new Animation(new TextureRegion(idle), 4, .7f);

        move = new Texture("PlayerMove.png");
        moveAnimation = new Animation(new TextureRegion(move), 6, .5f);

        attack1 = new Texture(("PlayerAttack1.png"));
        attack1Animation = new Animation(new TextureRegion(attack1), 6, .55f);

        attack2 = new Texture("PlayerAttack2.png");
        attack2Animation = new Animation(new TextureRegion(attack2), 5, .55f);

        attack3 = new Texture("PlayerAttack3.png");
        attack3Animation = new Animation(new TextureRegion(attack3), 5, .55f);

        attack4 = new Texture("PlayerAttack4.png");
        attack4Animation = new Animation(new TextureRegion(attack4), 4, .55f);

    }

    public void render(float dt, SpriteBatch batch){
        sprite.draw(batch);
        update(dt);
        sprite.setColor(1,1,1,1);
    }

    public void update(float dt){
        UpdateAnimation(dt);
        sprite.setPosition(position.x, position.y);
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)){
            attacking = true;
        }
        if(attacking == true){
            playerSpeed = 50;
        }
        else{
            playerSpeed = Constant.PLAYER_SPEED;
        }
        attackBox.setPosition(isFacingRight == true ? sprite.getX() + 10 : sprite.getX() - 10, sprite.getY() - 32);
        if(isFacingRight == false){
            sprite.setScale(-2, 2);
        }
        else{
            sprite.setScale(2);
        }

        playerState = PlayerState.IDLE;

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            position.x -= playerSpeed * dt;
            isFacingRight = false;
            playerState = PlayerState.WALKING;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            position.x += playerSpeed * dt;
            isFacingRight = true;
            playerState = PlayerState.WALKING;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            position.y -= playerSpeed * dt;
            playerState = PlayerState.WALKING;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            position.y += playerSpeed * dt;
            playerState = PlayerState.WALKING;
        }

        CheckForBounds();
    }

    void UpdateAnimation(float dt){
        if(playerState == PlayerState.WALKING){
            sprite = new Sprite(moveAnimation.getFrame());
            moveAnimation.update(dt);
        }
        if(playerState == PlayerState.IDLE){
            sprite = new Sprite(idleAnimation.getFrame());
            idleAnimation.update(dt);
        }
        if(attacking == true){
           PlayAttackAni(dt);
           playerState = PlayerState.ATTACKING;
           attachAnimationEventAt(attack1Animation, 3, dt);
        }
        else{
            playerState = playerState.IDLE;
        }
    }

    void attachAnimationEventAt(Animation animation, int frameNum, float dt){
        if(animation.getFrameNum() == frameNum){
            attackTime += dt;
            if(attackTime <= dt){
                ApplyDamage(2);
            }
        }
        if(animation.getFrameNum() == animation.getRegion().size - 1 ){
            animation.setFrameNum(0);
            attacking = false;
            attackTime = 0;
        }
    }

    void CheckForBounds(){
        if(position.x <= 0){
            position.x = 0;
        }
        if(position.x > 700){
            position.x = 700;
        }
        if(position.y <= 0){
            position.y = 0;
        }
        if(position.y > 500){
            position.y = 500;
        }
    }

    public void ApplyDamage(int damage){
        //attackTime = 0;
        for (Enemy e: enemiesToAttack) {
            if(attackBox.overlaps(e.selfCollider) && attacking == true){
                e.TakeDamage(damage);
                MyGdxGame.gameScreen.ScreenShake(20);
                System.out.println("damage");
                if(isFacingRight == true){
                    e.position.x += 2;
                    e.position.y += MathUtils.random(-3.0f, 2.0f);
                }
                else{
                    e.position.x -= 2;
                    e.position.y += MathUtils.random(-3.0f, 2.0f);
                }
            }
        }
        //attacking = false;
    }

    void PlayAttackAni(float deltaTime){
        sprite = new Sprite(attack1Animation.getFrame());
        attack1Animation.update(deltaTime);
    }

    public void TakeDamge(int damage){
        health -= damage;
        sprite.setColor(1,0,0,1);
    }

    enum PlayerState{
        WALKING,
        IDLE,
        ATTACKING,
        DEAD
    }

}
