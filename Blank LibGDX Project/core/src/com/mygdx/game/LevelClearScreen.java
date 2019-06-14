package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

public class LevelClearScreen implements Screen {
    SpriteBatch batch;
    public GameScreen gameScreen;
    Label finishTimeText;
    Button nextLevelButton;
    Texture ContinueButtonTexture = new Texture("Continue.png");
    String finishTip;
    Skin skin;

    MyGdxGame game;
    Stage stage;
    public LevelClearScreen(MyGdxGame game){
        this.game = game;
    }

    Texture LevelClearScreen = new Texture("ContinueMenu.png");
    Image levelclearScreenImg;

    Texture winTexture = new Texture("WinScreen.png");
    Image winScreenImg;

    @Override
    public void show() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        nextLevelButton = new TextButton("Continue",skin,"default");
        nextLevelButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(ContinueButtonTexture)));

        levelclearScreenImg = new Image(LevelClearScreen);
        levelclearScreenImg.setFillParent(true);

        winScreenImg = new Image(winTexture);
        winScreenImg.setFillParent(true);

        nextLevelButton.setWidth(300);
        nextLevelButton.setHeight(89);
        nextLevelButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);


        if(MyGdxGame.gameScreen.currentLevel < 5){
            stage.addActor(levelclearScreenImg);
            stage.addActor(nextLevelButton);
        }
        else{
            stage.addActor(winScreenImg);
        }
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
