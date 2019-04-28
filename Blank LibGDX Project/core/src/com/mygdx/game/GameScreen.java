package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.DelayedRemovalArray;

public class GameScreen implements Screen {
    MyGdxGame game;
    Player player;
    DelayedRemovalArray<Enemy> enemies;
    SpriteBatch batch;
    public GameScreen(MyGdxGame game){
        this.game = game;
    }
    @Override
    public void show() {
        enemies = new DelayedRemovalArray<Enemy>();
        enemies.add(new Enemy(100, 100));
        batch = new SpriteBatch();
        player = new Player(200, 200);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        player.render(delta, batch);
        for (Enemy e : enemies){
            e.render(delta, batch);
            if(player.attackBox.overlaps(e.selfCollider)){
                System.out.println("Collide");
            }
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
