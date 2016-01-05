package com.johncroker.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.johncroker.fchelpers.AssetLoader;
import com.johncroker.gameobjects.Bird;

public class GameRenderer {

	private GameWorld worldInstance;
	private OrthographicCamera camera;
	private ShapeRenderer sr;

	private SpriteBatch batcher;

	private int midPointY;
	private int gameHeight;

	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		worldInstance = world;

		this.gameHeight = gameHeight;
		this.midPointY = midPointY;

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 136, 204);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(camera.combined);

		sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
	}

	public void render(float runTime) {
		Bird bird = worldInstance.getBird();

		// Fill the entire screen with black, to prevent potential flickering.
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Begin sr
		sr.begin(ShapeType.Filled);

		// Draw Background color
		sr.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		sr.rect(0, 0, 136, midPointY + 66);

		// Draw Grass
		sr.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
		sr.rect(0, midPointY + 66, 136, 11);

		// Draw Dirt
		sr.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		sr.rect(0, midPointY + 77, 136, 52);

		// End sr
		sr.end();

		// Begin SpriteBatch
		batcher.begin();
		// Disable transparency
		// This is good for performance when drawing images that do not require
		// transparency.
		batcher.disableBlending();
		batcher.draw(AssetLoader.bg, 0, midPointY + 23, 136, 43);

		// The bird needs transparency, so we enable that again.
		batcher.enableBlending();

		// Draw bird at its coordinates. Retrieve the Animation object from
		// AssetLoader
		// Pass in the runTime variable to get the current frame.
		batcher.draw(AssetLoader.birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth(),
				bird.getHeight());

		// End SpriteBatch
		batcher.end();
	}

}
