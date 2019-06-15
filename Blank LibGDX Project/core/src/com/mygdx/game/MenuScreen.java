package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class MenuScreen implements Screen {

    MyGdxGame game;
    SpriteBatch batch;
    Stage stage;
    Button startButton;
    Button exitButton;
    Skin skin;
    Drawable shellDraw;

    Texture StartButtonTexture = new Texture("Start.png");


    Texture ExitButtonTexture = new Texture("Exit.png");
    Texture ExitButtonTexturePressed = new Texture("Exit.png");

    Texture ShellScreenimg = new Texture("ShellScreen.png");
    Sprite shellSprite = new Sprite(ShellScreenimg);
    Sound bgMusic = Gdx.audio.newSound(Gdx.files.internal("BgMusic.wav" ));

    Image shellScreenimg;
    public MenuScreen(MyGdxGame game){
        this.game = game;

    }

    @Override
    public void show() {
        bgMusic.play(.5f);
        bgMusic.loop();
        batch = new SpriteBatch();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));


        shellSprite.setScale(5);
        shellScreenimg = new Image(ShellScreenimg);
        shellScreenimg.setFillParent(true);
        //startButton = new TextButton("Play",skin,"default");
        startButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(StartButtonTexture)));

        startButton.setWidth(200);
        startButton.setHeight(89);

        exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(ExitButtonTexture)));
        //exitButton = new TextButton("Exit",skin,"default");
        exitButton.setWidth(200);
        exitButton.setHeight(89);

        startButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2 - 200,
                Gdx.graphics.getHeight() / 2 + 64);

        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2 - 200,
                Gdx.graphics.getHeight() / 2 - 64);


        stage.addActor(shellScreenimg);
        stage.addActor(startButton);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.5f,.8f,.8f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //shellSprite.draw(batch);
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
