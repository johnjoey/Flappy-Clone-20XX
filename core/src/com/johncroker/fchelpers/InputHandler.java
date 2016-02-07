package com.johncroker.fchelpers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.johncroker.gameobjects.Bird;
import com.johncroker.gameworld.GameWorld;
import com.johncroker.ui.Button;

public class InputHandler implements GestureListener, InputProcessor {
	private Bird bird;
	private GameWorld worldInstance;

	private List<Button> menuButtons;

	private Button playButton;
	private Button boostButton;

	private float scaleFactorX;
	private float scaleFactorY;

	private Vector2 boostDirection;
	private boolean isAiming;

	public InputHandler(GameWorld wi, float scaleFactorX, float scaleFactorY) {
		this.worldInstance = wi;
		bird = worldInstance.getBird();

		int midPointY = worldInstance.getMidPointY();

		boostDirection = new Vector2(0, 0);
		isAiming = false;

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
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int x = scaleX(screenX);
		int y = scaleY(screenY);
		if (worldInstance.isMenu()) {
			playButton.isTouchDown(x, y); // make play button appear clicked
		} else if (worldInstance.isReady()) {
			worldInstance.start();
		}

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
				worldInstance.ready(); // START GAME
				return true;
			}
		} else if (worldInstance.isRunning()) {

			worldInstance.setSlowmo(false);

			bird.onClick();

			return true;

		}

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		if (worldInstance.isRunning()) {
			worldInstance.setSlowmo(true);
			return true;
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
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
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

}
