package com.johncroker.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.johncroker.fchelpers.AssetLoader;
import com.johncroker.gameobjects.Bird;
import com.johncroker.gameobjects.Grass;
import com.johncroker.gameobjects.Pipe;
import com.johncroker.gameobjects.ScrollHandler;

public class GameRenderer {

	private GameWorld worldInstance;
	private OrthographicCamera camera;
	private ShapeRenderer sr;

	private SpriteBatch batcher;

	private int midPointY;
	private int gameHeight;

	private Bird bird;
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;

	private TextureRegion bg, grass;
	private Animation birdAnimation;
	private TextureRegion birdMid, birdDown, birdUp;
	private TextureRegion skullUp, skullDown, pipeBody;

	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		worldInstance = world;

		this.gameHeight = gameHeight;
		this.midPointY = midPointY;

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 136, 204);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(camera.combined);
		sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);

		initGameObjects();
		initAssets();
	}

	public void render(float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sr.begin(ShapeType.Filled);

		// Draw Background color
		sr.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
		sr.rect(0, 0, 136, midPointY + 66);

		// Draw Grass
		sr.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
		sr.rect(0, midPointY + 66, 136, 11);

		// Draw Dirt
		sr.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		sr.rect(0, midPointY + 77, 136, 52);

		sr.end();

		batcher.begin();
		batcher.disableBlending();
		batcher.draw(bg, 0, midPointY + 23, 136, 43);

		drawGrass();
		drawPipes();
		// enable transparency
		batcher.enableBlending();
		drawSkulls();

		// Draw bird
		if (bird.stopAnimation()) {
			batcher.draw(birdMid, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
					bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
		} else {
			batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
					bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
		}

		String score = worldInstance.getScore() + "";
		AssetLoader.shadow.draw(batcher, "" + worldInstance.getScore(), (136 / 2) - (3 * score.length()), 12);
		AssetLoader.font.draw(batcher, "" + worldInstance.getScore(), (136 / 2) - (3 * score.length() - 1), 11);

		batcher.end();
	}

	private void initGameObjects() {
		bird = worldInstance.getBird();
		scroller = worldInstance.getScroller();
		frontGrass = scroller.getGrass1();
		backGrass = scroller.getGrass2();
		pipe1 = scroller.getPipe1();
		pipe2 = scroller.getPipe2();
		pipe3 = scroller.getPipe3();
	}

	private void initAssets() {
		bg = AssetLoader.bg;
		grass = AssetLoader.grass;
		birdAnimation = AssetLoader.birdAnimation;
		birdMid = AssetLoader.bird;
		birdUp = AssetLoader.birdUp;
		birdDown = AssetLoader.birdDown;
		skullUp = AssetLoader.skullUp;
		skullDown = AssetLoader.skullDown;
		pipeBody = AssetLoader.pipeBody;
	}

	private void drawGrass() {
		// Draw the grass
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
	}

	private void drawSkulls() {
		// Temporary code! Sorry about the mess :)
		// We will fix this when we finish the Pipe class.

		batcher.draw(skullDown, pipe1.getX() - 1, pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
		batcher.draw(skullUp, pipe1.getX() - 1, pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

		batcher.draw(skullDown, pipe2.getX() - 1, pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
		batcher.draw(skullUp, pipe2.getX() - 1, pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

		batcher.draw(skullDown, pipe3.getX() - 1, pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
		batcher.draw(skullUp, pipe3.getX() - 1, pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
	}

	private void drawPipes() {
		// Temporary code! Sorry about the mess :)
		// We will fix this when we finish the Pipe class.
		batcher.draw(pipeBody, pipe1.getX(), pipe1.getY(), pipe1.getWidth(), pipe1.getHeight());
		batcher.draw(pipeBody, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45, pipe1.getWidth(),
				midPointY + 66 - (pipe1.getHeight() + 45));

		batcher.draw(pipeBody, pipe2.getX(), pipe2.getY(), pipe2.getWidth(), pipe2.getHeight());
		batcher.draw(pipeBody, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45, pipe2.getWidth(),
				midPointY + 66 - (pipe2.getHeight() + 45));

		batcher.draw(pipeBody, pipe3.getX(), pipe3.getY(), pipe3.getWidth(), pipe3.getHeight());
		batcher.draw(pipeBody, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45, pipe3.getWidth(),
				midPointY + 66 - (pipe3.getHeight() + 45));
	}

}
