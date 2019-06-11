package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Interactable {
    Vector2 position;
    Circle interactCircle;
    Sprite sprite;
    Texture img = new Texture("LevelChangeDoor.png");

    public boolean canSetScene = false;

    public Interactable(Vector2 position, float interactRadius){
        sprite = new Sprite(img);

        sprite.setPosition(position.x, position.y);
        sprite.setScale(3);
        this.position = position;
        interactCircle = new Circle(position, interactRadius);
        interactCircle.setPosition(position);
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

   public void DetactDisToPlayer(float x, float y){
        if(Vector2.dst(position.x, position.y, x, y) < interactCircle.radius &&
                Gdx.input.isKeyJustPressed(Input.Keys.F)){
            canSetScene = true;
        }
   }


}
