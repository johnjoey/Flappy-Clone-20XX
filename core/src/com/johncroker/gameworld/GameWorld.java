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

	public enum GameState {
		READY, RUNNING, GAMEOVER;
	}

	private GameState currentState;

	public GameWorld(int midPointY) {
		bird = new Bird(33, midPointY - 5, 17, 12);
		scroller = new ScrollHandler(midPointY + 66, this);
		ground = new Rectangle(0, midPointY + 66, 136, 11);
		currentState = GameState.READY;
	}

	public void update(float delta) {
		switch (currentState) {
		case READY:
			updateReady(delta);
			break;
		case RUNNING:
		default:
			updateRunning(delta);
			break;
		}
	}

	public void updateReady(float delta) {

	}

	public void updateRunning(float delta) {

		if (delta > .15f)
			delta = .15f;

		bird.update(delta);
		scroller.update(delta);

		if (bird.isAlive() && scroller.collides(bird)) {
			scroller.stop();
			bird.die();
			AssetLoader.dead.play();
		}

		if (Intersector.overlaps(bird.getHitBox(), ground)) {
			scroller.stop();
			bird.die();
			bird.decelerate();
		}
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
