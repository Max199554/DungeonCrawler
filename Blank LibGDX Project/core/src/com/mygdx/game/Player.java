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

    Vector2 position;
    Sprite sprite;
    Texture texture;
    int health;
    Rectangle attackBox;
    boolean attacking = false;
    Circle attackCircle;
    DelayedRemovalArray<Enemy> enemiesToAttack;
    float attackRange = 70;
    boolean isFacingRight = true;
    public float playerSpeed = Constant.PLAYER_SPEED;

    Texture idle;
    Animation idleAnimation;
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

        attackBox = new Rectangle(position.x,
                position.y, attackRange, attackRange);

        idle = new Texture("PlayerIdle.png");
        idleAnimation = new Animation(new TextureRegion(idle), 4, .5f);
    }

    public void render(float dt, SpriteBatch batch){
        sprite.draw(batch);
        update(dt);
    }

    public void update(float dt){
        System.out.println(attackBox.getX());
        System.out.println(sprite.getX());
        sprite = new Sprite(idleAnimation.getFrame());
        idleAnimation.update(dt);
        sprite.setPosition(position.x, position.y);
        if(Gdx.input.isKeyJustPressed(Input.Keys.J)){
            attacking = true;
            ApplyDamage();

        }
        else{
            attacking = false;
        }
        if(attacking == true){

        }
        else{
            playerSpeed = Constant.PLAYER_SPEED;
        }
        attackBox.setPosition(isFacingRight == true ? sprite.getX() + 30 : sprite.getX() - 30, sprite.getY());
        if(isFacingRight == false){
            sprite.setScale(-2, 2);
        }
        else{
            sprite.setScale(2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            position.x -= playerSpeed * dt;
            isFacingRight = false;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            position.x += playerSpeed * dt;
            isFacingRight = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            position.y -= playerSpeed * dt;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            position.y += playerSpeed * dt;
        }

        CheckForBounds();
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

                attacking = false;
            }
        }
    }

    void PlayAttackAni(){

    }


}
