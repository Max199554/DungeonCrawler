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

    Texture idle;
    Animation idleAnimation;

    Texture move;
    Animation moveAnimation;

    Texture attack1;
    Animation attack1Animation;

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
        attack1Animation = new Animation(new TextureRegion(attack1), 6, .4f);
    }

    public void render(float dt, SpriteBatch batch){
        sprite.draw(batch);
        update(dt);
        sprite.setColor(1,1,1,1);
    }

    public void update(float dt){
        //sprite = new Sprite(idleAnimation.getFrame());
        //idleAnimation.update(dt);
        UpdateAnimation(dt);
        sprite.setPosition(position.x, position.y);
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)){
            attacking = true;
        }
        if(attacking == true){
            playerState = PlayerState.ATTACKING;
            playerSpeed = 50;
            float attackTime = 0;
            attackTime += dt;
            if(attackTime <= dt && attack1Animation.getFrameNum() == 3){
                ApplyDamage();
            }
            else if(attackTime > dt / 5){
                attackTime = 0;
            }
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
           if(attack1Animation.getFrameNum() == 5){

               attack1Animation.setFrameNum(0);
               playerState = PlayerState.IDLE;
               attacking = false;
           }
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

    public void ApplyDamage(){
        for (Enemy e: enemiesToAttack) {
            if(attackBox.overlaps(e.selfCollider) && attacking == true){
                e.TakeDamage(2);
                MyGdxGame.gameScreen.ScreenShake(5);
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
