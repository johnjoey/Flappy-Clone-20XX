package com.johncroker.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameRenderer {

	private GameWorld worldInstance;
	private OrthographicCamera camera;
	private ShapeRenderer sr;

	public GameRenderer(GameWorld world) {
		worldInstance = world;
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 136, 204);
		sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
	}

	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}

}
