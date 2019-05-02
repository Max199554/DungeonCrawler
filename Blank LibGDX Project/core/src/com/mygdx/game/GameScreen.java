package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.DelayedRemovalArray;

public class GameScreen implements Screen {
    MyGdxGame game;
    Player player;
    DelayedRemovalArray<Enemy> enemies;
    SpriteBatch batch;
    int enemyAmount = 4;
    public GameScreen(MyGdxGame game){
        this.game = game;
    }
    @Override
    public void show() {
        enemies = new DelayedRemovalArray<Enemy>();
        for(int i = 0; i < enemyAmount; i++){
            enemies.add(new Enemy(MathUtils.random(600), MathUtils.random(400)));
        }
        batch = new SpriteBatch();
        player = new Player(200, 200);
        for (Enemy e:
             enemies) {
            e.target = player;
            System.out.println(e.target);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.8f,.8f,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        player.render(delta, batch);
        for (Enemy e : enemies){
            e.render(delta, batch);
            if(e.health <= 0){
                enemies.removeValue(e, false);
            }
        }
        player.ApplyDamage(enemies);
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
