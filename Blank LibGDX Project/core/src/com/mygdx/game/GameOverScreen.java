package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameOverScreen implements Screen {
    MyGdxGame game;
    public GameOverScreen(MyGdxGame game){this.game = game;}
    Label deathText;
    Stage stage;
    Skin skin;
    TextButton restart;
    SpriteBatch batch;

    Texture gameOverScreen = new Texture("GameOverScreen.png");
    Image gameScreenImg;
    
    @Override
    public void show() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        deathText = new Label("Game Over", skin);
        deathText.setSize(500,500);
        deathText.setFontScale(1.5f,1.5f);
        deathText.setPosition(Gdx.graphics.getWidth() / 2 , Gdx.graphics.getHeight() / 2);
        deathText.setColor(Color.WHITE);

        gameScreenImg = new Image(gameOverScreen);
        gameScreenImg.setFillParent(true);
        restart = new TextButton("Retry", skin);
        restart.setSize(150, 64);
        restart.setPosition(Gdx.graphics.getWidth() / 2 - restart.getWidth() / 2 + 400,
                Gdx.graphics.getHeight() / 2 - 100);

        stage.addActor(gameScreenImg);
        stage.addActor(deathText);
        stage.addActor(restart);
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f,.1f,.1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        stage.draw();
        if(restart.isPressed()){
            game.setScreen(MyGdxGame.menuScreen);
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
