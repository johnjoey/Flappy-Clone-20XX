package com.johncroker.flappyclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.johncroker.fchelpers.AssetLoader;
import com.johncroker.screen.GameScreen;

public class FCGame extends Game {
	SpriteBatch batch;
	Texture img;

	@Override
	public void create() {
		Gdx.app.log("FCGame", "created");
		AssetLoader.load();
		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
