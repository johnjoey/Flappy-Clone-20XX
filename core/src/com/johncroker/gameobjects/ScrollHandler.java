package com.johncroker.gameobjects;

import com.johncroker.fchelpers.AssetLoader;
import com.johncroker.gameworld.GameWorld;

public class ScrollHandler {
	private Grass grass1, grass2;
	private Pipe pipe1, pipe2, pipe3;
	private GameWorld worldInstance;

	public static final int SLOWMO_SCROLL_SPEED = -20;
	public static final int SCROLL_SPEED = -59;
	public static final int PIPE_GAP = 49;

	public ScrollHandler(float yPos, GameWorld wi) {
		this.worldInstance = wi;
		grass1 = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
		grass2 = new Grass(grass1.getTailX(), yPos, 143, 11, SCROLL_SPEED);

		pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
		pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
		pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
	}

	public void updateReady(float delta) {
		grass1.update(delta);
		grass2.update(delta);
		if (grass1.isOffScreen()) {
			grass1.reset(grass2.getTailX());
		} else if (grass2.isOffScreen()) {
			grass2.reset(grass1.getTailX());
		}
	}

	public void update(float delta) {
		grass1.update(delta);
		grass2.update(delta);

		pipe1.update(delta);
		pipe2.update(delta);
		pipe3.update(delta);

		if (pipe1.isOffScreen()) {
			pipe1.reset(pipe3.getTailX() + PIPE_GAP);
		} else if (pipe2.isOffScreen()) {
			pipe2.reset(pipe1.getTailX() + PIPE_GAP);
		} else if (pipe3.isOffScreen()) {
			pipe3.reset(pipe2.getTailX() + PIPE_GAP);
		}

		if (grass1.isOffScreen()) {
			grass1.reset(grass2.getTailX());
		} else if (grass2.isOffScreen()) {
			grass2.reset(grass1.getTailX());
		}
	}

	public void stop() {
		grass1.stop();
		grass2.stop();
		pipe1.stop();
		pipe2.stop();
		pipe3.stop();
	}

	public boolean collides(Bird bird) {
		if (!pipe1.isScored() && pipe1.getX() + (pipe1.getWidth() / 2) < bird.getX() + bird.getWidth()) {
			addScore(1);
			pipe1.setScored(true);
			AssetLoader.coin.play();
		} else if (!pipe2.isScored() && pipe2.getX() + (pipe2.getWidth() / 2) < bird.getX() + bird.getWidth()) {
			addScore(1);
			pipe2.setScored(true);
			AssetLoader.coin.play();

		} else if (!pipe3.isScored() && pipe3.getX() + (pipe3.getWidth() / 2) < bird.getX() + bird.getWidth()) {
			addScore(1);
			pipe3.setScored(true);
			AssetLoader.coin.play();

		}

		return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3.collides(bird));
	}

	public void onRestart() {
		grass1.onRestart(0, SCROLL_SPEED);
		grass2.onRestart(grass1.getTailX(), SCROLL_SPEED);
		pipe1.onRestart(210, SCROLL_SPEED);
		pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
		pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
	}

	private void addScore(int increment) {
		worldInstance.addScore(increment);
	}

	public Grass getGrass1() {
		return grass1;
	}

	public Grass getGrass2() {
		return grass2;
	}

	public Pipe getPipe1() {
		return pipe1;
	}

	public Pipe getPipe2() {
		return pipe2;
	}

	public Pipe getPipe3() {
		return pipe3;
	}

	public void setSlowmo(Boolean state) {
		if (state) {
			grass1.setVelocity(SLOWMO_SCROLL_SPEED);
			grass2.setVelocity(SLOWMO_SCROLL_SPEED);
			pipe1.setVelocity(SLOWMO_SCROLL_SPEED);
			pipe2.setVelocity(SLOWMO_SCROLL_SPEED);
			pipe3.setVelocity(SLOWMO_SCROLL_SPEED);
		} else {
			grass1.setVelocity(SCROLL_SPEED);
			grass2.setVelocity(SCROLL_SPEED);
			pipe1.setVelocity(SCROLL_SPEED);
			pipe2.setVelocity(SCROLL_SPEED);
			pipe3.setVelocity(SCROLL_SPEED);
		}

	}

}
