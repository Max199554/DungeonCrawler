package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class DestoryFX extends SpawnableFX {
    public DestoryFX(Vector2 position) {
        super(position);
        fxAnimationTexture = new Texture("BrickExplode.png");
        fxAnimation = new Animation(new TextureRegion(fxAnimationTexture), 3, .5f);
        sprite = new Sprite(fxAnimation.getFrame());
        sprite.setPosition(position.x,position.y);
        sprite.setScale(.7f);
    }
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void update(float dt){
        fxAnimation.update(dt);
        sprite.setPosition(position.x, position.y);
        sprite.setRegion(fxAnimation.getFrame());
    }
}
