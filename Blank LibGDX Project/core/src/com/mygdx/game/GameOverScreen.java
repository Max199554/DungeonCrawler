package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameOverScreen implements Screen {
    MyGdxGame game;
    public GameOverScreen(MyGdxGame game){this.game = game;}
    Label deathText;
    Stage stage;
    Skin skin;
    Button restart;

    Texture RestartButtonTexture = new Texture("Retry.png");

    SpriteBatch batch;

    Texture gameOverScreen = new Texture("GameOverScreen.png");
    Image gameScreenImg;

    @Override
    public void show() {
        MyGdxGame.gameScreen.currentLevel = 0;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        gameScreenImg = new Image(gameOverScreen);
        gameScreenImg.setFillParent(true);

        restart = new ImageButton(new TextureRegionDrawable(new TextureRegion(RestartButtonTexture)));

        restart.setSize(250, 164);
        restart.setPosition(Gdx.graphics.getWidth() / 2 - restart.getWidth() / 2 + 200,
                Gdx.graphics.getHeight() / 2 - 100);

        stage.addActor(gameScreenImg);
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
            MyGdxGame.menuScreen.bgMusic.stop();
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
