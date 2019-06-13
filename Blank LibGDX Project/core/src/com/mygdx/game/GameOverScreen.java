package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameOverScreen implements Screen {
    MyGdxGame game;
    public GameOverScreen(MyGdxGame game){this.game = game;}
    Label deathText;
    Stage stage;
    Skin skin;
    SpriteBatch batch;
    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        deathText = new Label(null, skin);
        deathText.setText("Game Over");
        stage.addActor(deathText);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f,1f,1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        
        stage.draw();
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
