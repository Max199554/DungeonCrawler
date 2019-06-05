package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.DelayedRemovalArray;

public class GameScreen implements Screen {
    OrthographicCamera camera;
    MyGdxGame game;
    Player player;
    DelayedRemovalArray<Enemy> enemies;
    SpriteBatch batch;
    int enemyAmount = 20;
    public GameScreen(MyGdxGame game){
        this.game = game;
    }
    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        enemies = new DelayedRemovalArray<Enemy>();
        for(int i = 0; i < enemyAmount; i++){
            enemies.add(new Slime(new Vector2(MathUtils.random(600), MathUtils.random(400))));
        }
        batch = new SpriteBatch();
        player = new Player(200, 200);
        for (Enemy e:
             enemies) {
            e.target = player;
        }
        player.enemiesToAttack = enemies;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.8f,.8f,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();


        camera.position.x = MathUtils.lerp(camera.position.x, player.position.x, delta * 5);
        camera.position.y = MathUtils.lerp(camera.position.y, player.position.y, delta * 5);
        camera.update();
        //System.out.println(player.position);
        batch.setProjectionMatrix(camera.combined);

        for (Enemy e : enemies){
            e.render(delta, batch);
            e.EnemyTrade(player.position.x, player.position.y);
            if(e.health <= 0){
                enemies.removeValue(e, false);
                ScreenShake(10);
            }
        }
        player.render(delta, batch);
        batch.end();
    }

    public void ScreenShake(float amount){
        camera.position.x += MathUtils.random(-1.0f, 1.0f) * amount;
        camera.position.y += MathUtils.random(-1.0f, 1.0f) * amount;
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
