package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LevelClearScreen implements Screen {
    SpriteBatch batch;
    public GameScreen gameScreen;
    Label finishTimeText;
    Button nextLevelButton;
    String finishTip;
    Skin skin;
    MyGdxGame game;
    Stage stage;
    public LevelClearScreen(MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        nextLevelButton = new TextButton("Continue",skin,"default");
        nextLevelButton.setWidth(100);
        nextLevelButton.setHeight(50);
        nextLevelButton.setPosition(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);
        stage.addActor(nextLevelButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f,.1f,.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        stage.draw();
        if(nextLevelButton.isPressed()){
            MyGdxGame.gameScreen.currentLevel += 1;
            game.setScreen(MyGdxGame.gameScreen);
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
