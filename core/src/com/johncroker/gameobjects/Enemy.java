package com.johncroker.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

public class Enemy extends Scrollable {

	private Circle hitBox;
	private Random r;
	private int screenHeight;

	public Enemy(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		hitBox = new Circle();
		r = new Random();
		screenHeight = ((Gdx.graphics.getHeight() / (Gdx.graphics.getWidth() / 136)) / 2) + 77;

	}

	@Override
	public void update(float delta) {
		super.update(delta);
		hitBox.set(position.x + 9, position.y + 6, 6.5f);
	}

	@Override
	public void reset(float x) {
		super.reset(x);
		position.y = r.nextInt(screenHeight); // Sets a random Y position for
												// enemies to come from off
												// screen

	}

	public void onRestart(float x, float scrollSpeed) {
		velocity.x = scrollSpeed;
		reset(x);
	}

	public boolean collides(Bird bird) {
		if (position.x < bird.getX() + bird.getWidth()) {
			return (Intersector.overlaps(bird.getHitBox(), hitBox));
		}
		return false;
	}

}
