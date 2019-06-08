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

    int currentLevel = 0;
    OrthographicCamera camera;
    MyGdxGame game;
    Player player;
    DelayedRemovalArray<Enemy> enemies;
    SpriteBatch batch;
    int enemyAmount = 10;
    public GameScreen(MyGdxGame game){
        this.game = game;
    }
    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        enemies = new DelayedRemovalArray<Enemy>();
        batch = new SpriteBatch();
        if(currentLevel == 0){
            for(int i = 0; i < enemyAmount; i++){
                enemies.add(new Slime(new Vector2(MathUtils.random(600), MathUtils.random(400))));
            }
        }
        else if(currentLevel == 1){
            for(int i = 0; i < enemyAmount + 5; i++){
                enemies.add(new Slime(new Vector2(MathUtils.random(600), MathUtils.random(400))));
            }
        }
        else if(currentLevel == 2){
            for(int i = 0; i < enemyAmount + 10; i++){
                enemies.add(new Slime(new Vector2(MathUtils.random(600), MathUtils.random(400))));
            }
        }
        else if(currentLevel == 3){
            for(int i = 0; i < enemyAmount + 15; i++){
                enemies.add(new Slime(new Vector2(MathUtils.random(600), MathUtils.random(400))));
            }
        }
        for (Enemy e:
                enemies) {
            e.target = player;
        }
        player = new Player(200, 200);

        player.enemiesToAttack = enemies;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.8f,.8f,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            currentLevel += 1;
        }
        camera.position.x = MathUtils.lerp(camera.position.x, player.position.x, delta * 5);
        camera.position.y = MathUtils.lerp(camera.position.y, player.position.y, delta * 5);
        camera.update();
        //System.out.println(player.position);
        batch.setProjectionMatrix(camera.combined);

        for (Enemy e : enemies){
            e.render(delta, batch);
            e.EnemyTrade(player.position.x , player.position.y );
            if(e.health <= 0){
                enemies.removeValue(e, false);
                ScreenShake(40);
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
