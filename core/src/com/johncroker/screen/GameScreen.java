package com.johncroker.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.johncroker.fchelpers.InputHandler;
import com.johncroker.gameworld.GameRenderer;
import com.johncroker.gameworld.GameWorld;

public class GameScreen implements Screen {
	private GameWorld world;
	private GameRenderer renderer;

	public GameScreen() {
		Gdx.app.log("GameScreen", "constructor");

		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);

		int midPointY = (int) (gameHeight / 2);

		world = new GameWorld(midPointY);
		renderer = new GameRenderer(world);

		Gdx.input.setInputProcessor(new InputHandler(world.getBird()));
	}

	@Override
	public void show() {
		Gdx.app.log("GameScreen", "show");
	}

	@Override
	public void render(float delta) {
		world.update(delta);
		renderer.render();

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
