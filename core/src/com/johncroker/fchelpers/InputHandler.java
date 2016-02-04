package com.johncroker.fchelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.johncroker.gameobjects.Bird;
import com.johncroker.gameworld.GameWorld;
import com.johncroker.ui.Button;

public class InputHandler implements InputProcessor {
	private Bird bird;
	private GameWorld worldInstance;

	private List<Button> menuButtons;

	private Button playButton;
	private Button boostButton;

	private float scaleFactorX;
	private float scaleFactorY;

	public InputHandler(GameWorld wi, float scaleFactorX, float scaleFactorY) {
		this.worldInstance = wi;
		bird = worldInstance.getBird();

		int midPointY = worldInstance.getMidPointY();

		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;

		menuButtons = new ArrayList<Button>();
		playButton = new Button(136 / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2), midPointY + 50, 29, 16,
				AssetLoader.playButtonUp, AssetLoader.playButtonDown);
		boostButton = new Button(136 / 2 - (AssetLoader.boostButtonUp.getRegionWidth() / 2), midPointY + 70, 29, 16,
				AssetLoader.boostButtonUp, AssetLoader.boostButtonDown);

		menuButtons.add(playButton);
		menuButtons.add(boostButton);

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int x = scaleX(screenX);
		int y = scaleY(screenY);
		if (worldInstance.isMenu()) {
			playButton.isTouchDown(x, y);
		} else if (worldInstance.isReady()) {
			worldInstance.start();
		} else if (worldInstance.isRunning()) {
			if (boostButton.isTouchDown(x, y)) {
				worldInstance.setSlowmo(true);
			}
		}

		bird.onClick();

		if (worldInstance.isGameOver() || worldInstance.isHighScore()) {
			worldInstance.restart();
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);

		if (worldInstance.isMenu()) {
			if (playButton.isTouchUp(screenX, screenY)) {
				worldInstance.ready();
				return true;
			}
		} else if (worldInstance.isRunning()) {
			if (boostButton.isTouchUp(screenX, screenY)) {
				worldInstance.setSlowmo(false);
				return true;
			}
		}

		return false;
	}

	private int scaleX(int screenX) {
		return (int) (screenX / scaleFactorX);
	}

	private int scaleY(int screenY) {
		return (int) (screenY / scaleFactorY);
	}

	public List<Button> getMenuButtons() {
		return menuButtons;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
