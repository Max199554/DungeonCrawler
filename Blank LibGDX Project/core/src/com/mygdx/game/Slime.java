package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Slime extends Enemy {
    Texture idleAndWalk;
    Animation slimeWalkAndIdle;

    public Slime(Vector2 position) {
        //texture = new Texture("Slime Jump.png");
        super(position);
        texture = new Texture("Slime Jump.png");
    }

    @Override
    public void render(float dt, SpriteBatch batch){
        super.render(dt, batch);
        this.update(dt);
    }

    @Override
    public void Init() {
        idleAndWalk = new Texture("Slime Jump.png");
        slimeWalkAndIdle = new Animation(new TextureRegion(idleAndWalk), 7, MathUtils.random(.7f, .9f));
        sprite = new Sprite(slimeWalkAndIdle.getFrame());
        super.Init();
    }

    @Override
    public void update(float dt){
        sprite = new Sprite((slimeWalkAndIdle.getFrame()));
        slimeWalkAndIdle.update(dt);
        super.update(dt);


    }
}
