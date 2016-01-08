package com.johncroker.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Pipe extends Scrollable {
	private Random r;
	private Rectangle pipeTop, skullTop, pipeBottom, skullBottom;

	public static final int VERTICAL_GAP = 45;
	public static final int SKULL_WIDTH = 24;
	public static final int SKULL_HEIGHT = 11;

	private float groundY;
	private boolean isScored = false;

	public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
		super(x, y, width, height, scrollSpeed);
		r = new Random();
		pipeTop = new Rectangle();
		skullTop = new Rectangle();
		pipeBottom = new Rectangle();
		skullBottom = new Rectangle();
		this.groundY = groundY;

	}

	@Override
	public void reset(float newX) {
		super.reset(newX);
		height = r.nextInt(90) + 15;
		isScored = false;
	}

	public void onRestart(float x, float scrollSpeed) {
		velocity.x = scrollSpeed;
		reset(x);
	}

	public boolean isScored() {
		return isScored;
	}

	public void setScored(boolean isScored) {
		this.isScored = isScored;
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		pipeTop.set(position.x, position.y, width, height);
		pipeBottom.set(position.x, position.y + height + VERTICAL_GAP, width,
				groundY - (position.y + height + VERTICAL_GAP));
		skullTop.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height - SKULL_HEIGHT, SKULL_WIDTH,
				SKULL_HEIGHT);
		skullBottom.set(position.x - (SKULL_WIDTH - width) / 2, pipeBottom.y, SKULL_WIDTH, SKULL_HEIGHT);

	}

	public boolean collides(Bird bird) {
		if (position.x < bird.getX() + bird.getWidth()) {
			return (Intersector.overlaps(bird.getHitBox(), pipeBottom)
					|| Intersector.overlaps(bird.getHitBox(), pipeTop)
					|| Intersector.overlaps(bird.getHitBox(), skullBottom)
					|| Intersector.overlaps(bird.getHitBox(), skullTop));
		}
		return false;
	}

	public Rectangle getPipeTop() {
		return pipeTop;
	}

	public Rectangle getSkullTop() {
		return skullTop;
	}

	public Rectangle getPipeBottom() {
		return pipeBottom;
	}

	public Rectangle getSkullBottom() {
		return skullBottom;
	}

}
