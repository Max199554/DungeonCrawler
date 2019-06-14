package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Menu;
import java.util.logging.Level;

public class MyGdxGame extends Game implements ApplicationListener {



	public static GameScreen gameScreen;
	public static MenuScreen menuScreen;
	public static LevelClearScreen levelClearScreen;
	public static GameOverScreen gameOverScreen;

	@Override
	public void create() {
		//bgMusic.loop();
		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		levelClearScreen = new LevelClearScreen(this);
		gameOverScreen = new GameOverScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void render() {
		super.render();
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
	public void dispose () {

	}
}
