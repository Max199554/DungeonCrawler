package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


public class MenuScreen implements Screen {

    MyGdxGame game;
    SpriteBatch batch;
    Stage stage;
    Button startButton;
    Button exitButton;
    Skin skin;
    public MenuScreen(MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        startButton = new TextButton("Play",skin,"default");
        startButton.setWidth(128);
        startButton.setHeight(64);

        exitButton = new TextButton("Exit",skin,"default");
        exitButton.setWidth(128);
        exitButton.setHeight(64);

        startButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 + 64);

        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - 64);

        stage.addActor(startButton);
        stage.addActor(exitButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.5f,.8f,.8f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        stage.draw();
        if(startButton.isPressed()){
            game.setScreen(MyGdxGame.gameScreen);
        }
        else if(exitButton.isPressed()){
            Gdx.app.exit();
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
        batch.dispose();
    }
}