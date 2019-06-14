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

import java.util.ArrayList;
import java.util.List;

import sun.awt.EventQueueDelegate;

public class Player {

    float damage = 5;
    PlayerState playerState;
    Vector2 position;
    Sprite sprite;
    int health;
    Rectangle attackBox;
    Rectangle selfBox;
    boolean attacking = false;
    DelayedRemovalArray<Enemy> enemiesToAttack;
    float attackRangeX = 70;
    float attackRangeY = 35;
    boolean isFacingRight = true;
    public float playerSpeed = Constant.PLAYER_SPEED;

    //Time for determining when to start and stop the attack animation event
    float attackTime = 0;

    //Timer to check when to reset combo
    float comboTimer = 0;

    //How much time have passed, if pass this value, combo will be reset
    float comboMaxTime = .6f;

    //which attack animation to trigger next
    int currentAttackNum = 0;

    float dodgeDurationTimer = .5f;
    boolean isDodge = false;

    boolean[] comboSetter;
    ArrayList<Animation> attacks;

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

    float attackTimerLag = .5f;

    public Player(Vector2 position){
        this.position = position;
        Init();
    }

    public Player(float x, float y){
        position = new Vector2(x, y);
        Init();
    }

    public void Init(){
        health = 70;

        attacks = new ArrayList<Animation>();


        playerState = PlayerState.IDLE;
        attackBox = new Rectangle(position.x,
                position.y, attackRangeX, attackRangeY);



        idle = new Texture("PlayerIdle.png");
        idleAnimation = new Animation(new TextureRegion(idle), 4, .7f);

        sprite = new Sprite(idleAnimation.getFrame());
        sprite.setScale(2);

        selfBox = new Rectangle(position.x, position.y, idle.getWidth(), idle.getHeight());

        move = new Texture("PlayerMove.png");
        moveAnimation = new Animation(new TextureRegion(move), 6, .5f);

        attack4 = new Texture("PlayerAttack4.png");
        attack4Animation = new Animation(new TextureRegion(attack4), 5, .5f);
        attacks.add(attack4Animation);

        attack3 = new Texture("PlayerAttack3.png");
        attack3Animation = new Animation(new TextureRegion(attack3), 5, .5f);
        attacks.add(attack3Animation);

        attack1 = new Texture(("PlayerAttack1.png"));
        attack1Animation = new Animation(new TextureRegion(attack1), 7, .5f);
        attacks.add(attack1Animation);

        attack2 = new Texture("PlayerAttack2.png");
        attack2Animation = new Animation(new TextureRegion(attack2), 5, .5f);
        attacks.add(attack2Animation);

        comboSetter = new boolean[attacks.size()];
        for(int i = 0; i < comboSetter.length; i++){
            comboSetter[i] = false;
        }
    }

    public void render(float dt, SpriteBatch batch){
        sprite.draw(batch);
        update(dt);
    }

    public void update(float dt){
        selfBox.setPosition(position.x, position.y - 32);
        //System.out.println(selfBox.getX() + ", " + selfBox.getY());
        UpdateAnimation(dt);
        comboTimer += dt;
        attackTimerLag -= dt;
        sprite.setPosition(isFacingRight ? position.x : position.x - 64, position.y);
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)){
            attacking = true;
            for(int i = 0; i < comboSetter.length; i++){
                if(comboTimer <= comboMaxTime && comboSetter[i] != true) {
                    comboSetter[i] = true;
                    comboTimer = 0;
                    break;
                }
            }
        }
        System.out.println("Dodge: " + isDodge);
        if(Gdx.input.isKeyJustPressed(Input.Keys.L)){
            isDodge = true;

        }

        if(dodgeDurationTimer <= 0){
            playerSpeed = Constant.PLAYER_SPEED;
            dodgeDurationTimer = 0.2f;
            isDodge = false;
        }

        if(comboTimer > comboMaxTime){
            for(int i = 0; i < comboSetter.length; i++){
                comboSetter[i] = false;
            }
            currentAttackNum = 0;
            comboTimer = 0;
        }
        if(attacking == true){
            playerSpeed = 50;
            comboTimer = 0;
        }
        else if(isDodge == true){
            playerSpeed = 700;
            dodgeDurationTimer -= dt;
            sprite.setColor(.5f, .5f,.5f, .5f);
        }
        else{
            playerSpeed = Constant.PLAYER_SPEED;
            sprite.setColor(1,1,1,1);
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
            sprite.setRegion(moveAnimation.getFrame());
            moveAnimation.update(dt);
        }
        if(playerState == PlayerState.IDLE){
            sprite.setRegion(idleAnimation.getFrame());
            idleAnimation.update(dt);
        }
        if(attacking == true){
            PlayAttackAni(dt);
            playerState = PlayerState.ATTACKING;
            attachAnimationEventAt(attack1Animation, 4, dt);
            attachAnimationEventAt(attack2Animation, 2, dt);
            attachAnimationEventAt(attack3Animation, 3, dt);
            attachAnimationEventAt(attack4Animation, 2, dt);
        }
        else{
            playerState = playerState.IDLE;
        }
    }

    void attachAnimationEventAt(Animation animation, int frameNum, float dt){
        if(animation.getFrameNum() == frameNum){
            attackTime += dt;
            if(attackTime <= dt){
                ApplyDamage((int)damage);
            }
        }
        if(animation.getFrameNum() == animation.getRegion().size - 1 ){
            animation.setFrameNum(0);
            if(attackTimerLag < 0){
                attacking = false;
                attackTime = 0;
                attackTimerLag = .5f;
            }
            else{
                animation.setFrameNum(animation.getRegion().size - 1);
                sprite.setRegion(animation.getFrame());
            }
        }
    }

    void CheckForBounds(){
        if(position.x <= 0){
            position.x = 0;
        }
        if(position.x > GameScreen.mapBoundX){
            position.x = GameScreen.mapBoundX;
        }
        if(position.y <= 0){
            position.y = 0;
        }
        if(position.y > GameScreen.mapBoundY){
            position.y = GameScreen.mapBoundY;
        }
    }

    public void ApplyDamage(int damage){
        //attackTime = 0;
        for (Enemy e: enemiesToAttack) {
            if(attackBox.overlaps(e.selfCollider) && attacking == true){
                e.TakeDamage(damage);
                MyGdxGame.gameScreen.ScreenShake(25);
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
    }

    void PlayAttackAni(float deltaTime){
        if(currentAttackNum == 4)
            currentAttackNum = 0;
        sprite = new Sprite(attacks.get(currentAttackNum).getFrame());
        attacks.get(currentAttackNum).update(deltaTime);
        //attack1Animation.update(deltaTime);
        /*if(comboSetter[currentAttackNum] == true)
            attacks.get(currentAttackNum).update(deltaTime);
        else
            */

        if(comboSetter[currentAttackNum] == true &&
         attacks.get(currentAttackNum).getFrameNum() == attacks.get(currentAttackNum).getRegion().size - 1 ){
            currentAttackNum += 1;
        }

    }

    public void TakeDamge(int damage){
        health -= damage;
        sprite.setColor(1,0,0,1);
        if(health - damage == 0){
            playerState = PlayerState.DEAD;


        }
    }

    enum PlayerState{
        WALKING,
        IDLE,
        ATTACKING,
        DEAD
    }

}
