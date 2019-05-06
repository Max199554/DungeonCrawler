package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Slime extends Enemy {
    public Slime(Vector2 position) {
        //texture = new Texture("Slime Jump.png");
        super(position);
        texture = new Texture("Slime Jump.png");
    }

    @Override
    public void render(float dt, SpriteBatch batch){
        super.render(dt, batch);
    }

    @Override
    public void Init() {
        super.Init();
    }

    @Override
    public void update(float dt){
        super.update(dt);
    }
}
