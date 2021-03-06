package com.johncroker.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.johncroker.fchelpers.AssetLoader;
import com.johncroker.gameobjects.Bird;
import com.johncroker.gameobjects.ScrollHandler;

public class GameWorld {
	private Bird bird;
	private ScrollHandler scroller;
	private Rectangle ground;
	private int score = 0;
	private float runTime = 0;
	private int midPointY;

	public enum GameState {
		MENU, READY, RUNNING, GAMEOVER, HIGHSCORE;
	}

	private GameState currentState;

	public GameWorld(int midPointY) {
		currentState = GameState.MENU;
		this.midPointY = midPointY;
		bird = new Bird(33, midPointY - 5, 17, 12);
		scroller = new ScrollHandler(midPointY + 66, this);
		ground = new Rectangle(0, midPointY + 66, 136, 11);

	}

	public void update(float delta) {
		runTime += delta;
		switch (currentState) {
		case READY:
		case MENU:
			updateReady(delta);
			break;
		case RUNNING:
			updateRunning(delta);
			break;
		default:
			break;
		}
	}

	public void updateReady(float delta) {
		bird.updateReady(runTime);
		scroller.updateReady(delta);
	}

	public void updateRunning(float delta) {

		if (delta > .15f)
			delta = .15f;

		bird.update(delta);
		scroller.update(delta);

		if (bird.isAlive() && scroller.collides(bird)) {
			if (!bird.isInvul()) {
				if (bird.hasLives()) {
					AssetLoader.dead.play();
					bird.loseLife();
				} else {
					scroller.stop();
					bird.die();
					AssetLoader.dead.play();
					AssetLoader.fall.play();
				}
			}
		}

		if (Intersector.overlaps(bird.getHitBox(), ground)) {
			scroller.stop();
			bird.setInvul(false);
			bird.die();
			bird.decelerate();
			currentState = GameState.GAMEOVER;

			if (score > AssetLoader.getHighScore()) {
				AssetLoader.setHighScore(score);
				currentState = GameState.HIGHSCORE;
			}
		}
	}

	public boolean isHighScore() {
		return currentState == GameState.HIGHSCORE;
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public int getMidPointY() {
		return midPointY;
	}

	public boolean isReady() {
		return currentState == GameState.READY;
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public void start() {
		currentState = GameState.RUNNING;
		AssetLoader.music.play();
	}

	public void ready() {
		currentState = GameState.READY;
	}

	public void restart() {
		currentState = GameState.READY;
		score = 0;
		bird.onRestart(midPointY - 5);
		scroller.onRestart();
		AssetLoader.music.stop();
	}

	public Bird getBird() {
		return bird;
	}

	public ScrollHandler getScroller() {
		return scroller;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int increment) {
		score += increment;
	}

}
