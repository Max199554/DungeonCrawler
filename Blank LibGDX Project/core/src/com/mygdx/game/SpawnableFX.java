package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SpawnableFX {
    Vector2 position;
    SpriteBatch batch;
    Sprite sprite;
    Texture fxAnimationTexture;
    Animation fxAnimation;
    public SpawnableFX(Vector2 position){
        this.position = position;
    }
}
