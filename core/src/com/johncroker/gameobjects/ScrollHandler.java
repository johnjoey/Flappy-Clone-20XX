package com.johncroker.gameobjects;

import com.johncroker.fchelpers.AssetLoader;
import com.johncroker.gameworld.GameWorld;

public class ScrollHandler {
	private Grass grass1, grass2;
	private Pipe pipe1, pipe2, pipe3;
	private Enemy enemy1, enemy2, enemy3;
	private GameWorld worldInstance;

	public static final int SCROLL_SPEED = -59;
	public static final int PIPE_GAP = 49;

	public ScrollHandler(float yPos, GameWorld wi) {
		this.worldInstance = wi;
		grass1 = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
		grass2 = new Grass(grass1.getTailX(), yPos, 143, 11, SCROLL_SPEED);

		pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
		pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
		pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);

		enemy1 = new Enemy(250, 0, 16, 12, SCROLL_SPEED - 70);
		enemy2 = new Enemy(210, 50, 16, 12, SCROLL_SPEED - 70);
		enemy3 = new Enemy(230, 100, 16, 12, SCROLL_SPEED - 70);
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

		enemy1.update(delta);
		enemy2.update(delta);
		enemy3.update(delta);

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

		if (enemy1.isOffScreen()) {
			enemy1.reset(210);
		} else if (enemy2.isOffScreen()) {
			enemy2.reset(210);
		} else if (enemy3.isOffScreen()) {
			enemy3.reset(210);
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

		return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3.collides(bird) || enemy1.collides(bird)
				|| enemy2.collides(bird) || enemy3.collides(bird));
	}

	public void onRestart() {
		grass1.onRestart(0, SCROLL_SPEED);
		grass2.onRestart(grass1.getTailX(), SCROLL_SPEED);
		pipe1.onRestart(210, SCROLL_SPEED);
		pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
		pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
		enemy1.reset(210);
		enemy2.reset(210);
		enemy3.reset(210);
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

	public Enemy getEnemy1() {
		return enemy1;
	}

	public Enemy getEnemy2() {
		return enemy2;
	}

	public Enemy getEnemy3() {
		return enemy3;
	}

}
