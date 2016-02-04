package com.johncroker.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.johncroker.fchelpers.InputHandler;
import com.johncroker.gameworld.GameRenderer;
import com.johncroker.gameworld.GameWorld;

public class GameScreen implements Screen {
	private GameWorld world;
	private GameRenderer renderer;
	private float runTime = 0;

	public GameScreen() {
		Gdx.app.log("GameScreen", "constructor");

		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);

		int midPointY = (int) (gameHeight / 2);

		world = new GameWorld(midPointY);

		InputMultiplexer im = new InputMultiplexer();
		InputHandler ih = new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight);
		InputProcessor ip = ih;
		GestureDetector gd = new GestureDetector(ih);

		gd.setLongPressSeconds(0.4f); // length of press before registered

		im.addProcessor(gd);
		im.addProcessor(ip);
		Gdx.input.setInputProcessor(im);

		renderer = new GameRenderer(world, (int) gameHeight, midPointY);
	}

	@Override
	public void show() {
		Gdx.app.log("GameScreen", "show");
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta, runTime);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		Gdx.app.log("GameScreen", "resizing");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Gdx.app.log("GameScreen", "pause called");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Gdx.app.log("GameScreen", "resume called");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.app.log("GameScreen", "hide called");
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
