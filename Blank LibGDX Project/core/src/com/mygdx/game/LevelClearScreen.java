package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LevelClearScreen implements Screen {
    public GameScreen gameScreen;
    Label finishTimeText;
    Button nextLevelButton;
    String finishTip;
    Skin skin;
    MyGdxGame game;
    public LevelClearScreen(MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show() {
        skin = new Skin();
        nextLevelButton = new TextButton("Play",skin,"default");
    }

    @Override
    public void render(float delta) {

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
