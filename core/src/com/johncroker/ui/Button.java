package com.johncroker.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Button {
	private float x, y, width, height;

	private TextureRegion button;
	private TextureRegion buttonClicked;

	private Rectangle hitbox;

	private boolean isClicked = false;

	public Button(float x, float y, float width, float height, TextureRegion button, TextureRegion buttonClicked) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.button = button;
		this.buttonClicked = buttonClicked;

		hitbox = new Rectangle(x, y, width, height);
	}

	public boolean isClicked(int screenX, int screenY) {
		return hitbox.contains(screenX, screenY);
	}

	public void draw(SpriteBatch batcher) {
		if (isClicked) {
			batcher.draw(buttonClicked, x, y, width, height);
		} else {
			batcher.draw(button, x, y, width, height);
		}
	}

	public boolean isTouchDown(int screenX, int screenY) {

		if (hitbox.contains(screenX, screenY)) {
			isClicked = true;
			return true;
		}

		return false;
	}

	public boolean isTouchUp(int screenX, int screenY) {

		// It only counts as a touchUp if the button is in a pressed state.
		if (hitbox.contains(screenX, screenY) && isClicked) {
			isClicked = false;
			return true;
		}

		// Whenever a finger is released, we will cancel any presses.
		isClicked = false;
		return false;
	}

}
