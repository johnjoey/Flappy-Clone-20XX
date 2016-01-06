package com.johncroker.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.johncroker.fchelpers.AssetLoader;

public class Bird {
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;

	private float rotation;
	private int width;
	private int height;
	private Circle hitBox;

	private boolean isAlive = true;

	public Bird(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 460);
		hitBox = new Circle();
	}

	public void update(float delta) {
		velocity.add(acceleration.cpy().scl(delta));

		if (velocity.y > 200) {
			velocity.y = 200;
		}

		if (position.y < -13) {
			position.y = -13;
			velocity.y = 0;
		}

		position.add(velocity.cpy().scl(delta));
		hitBox.set(position.x + 9, position.y + 6, 6.5f);

		// rotate counterclockwise
		if (velocity.y < 0) {
			rotation -= 600 * delta;

			if (rotation < -20) {
				rotation = -20;
			}
		}

		if (isFalling() || !isAlive) {
			rotation += 480 * delta;

			if (rotation > 90) {
				rotation = 90;
			}
		}
	}

	public Circle getHitBox() {
		return hitBox;
	}

	public void onClick() {
		if (isAlive) {
			AssetLoader.flap.play();
			velocity.y = -140;
		}
	}

	public void die() {
		isAlive = false;
	}

	public void decelerate() {
		acceleration.y = 0;
	}

	public boolean isFalling() {
		return velocity.y > 110;
	}

	public boolean stopAnimation() {
		return velocity.y > 70 || !isAlive;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getRotation() {
		return rotation;
	}
}
