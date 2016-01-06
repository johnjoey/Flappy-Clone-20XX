package com.johncroker.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Scrollable {
	protected Vector2 position;
	protected Vector2 velocity;
	protected int width;
	protected int height;
	protected boolean offScreen;

	public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
		position = new Vector2(x, y);
		velocity = new Vector2(scrollSpeed, 0);
		this.width = width;
		this.height = height;
		offScreen = false;
	}

	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));

		if (position.x + width < 0) {
			offScreen = true;
		}
	}

	public void reset(float x) {
		position.x = x;
		offScreen = false;
	}

	public void stop() {
		velocity.x = 0;
	}

	public boolean isOffScreen() {
		return offScreen;
	}

	public float getTailX() {
		return position.x + width;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
