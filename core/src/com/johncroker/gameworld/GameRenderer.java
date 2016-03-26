package com.johncroker.gameworld;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.johncroker.fchelpers.AssetLoader;
import com.johncroker.fchelpers.InputHandler;
import com.johncroker.gameobjects.Bird;
import com.johncroker.gameobjects.Grass;
import com.johncroker.gameobjects.Pipe;
import com.johncroker.gameobjects.ScrollHandler;
import com.johncroker.tweenaccessor.Value;
import com.johncroker.tweenaccessor.ValueAccessor;
import com.johncroker.ui.Button;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class GameRenderer {

	private GameWorld worldInstance;
	private OrthographicCamera camera;
	private ShapeRenderer sr;

	private SpriteBatch batcher;

	private int midPointY;

	// GAME OBJECTS
	private Bird bird;
	private ScrollHandler scroller;
	private Grass frontGrass, backGrass;
	private Pipe pipe1, pipe2, pipe3;
	private Button boostButton;

	// GAME ASSETS
	private TextureRegion bg, grass;
	private Animation birdAnimation;
	private TextureRegion birdMid, birdDown, birdUp;
	private TextureRegion skullUp, skullDown, pipeBody;
	private TextureRegion boostButtonUp, boostButtonDown;

	// TWEEN CRAP
	private TweenManager manager;
	private Value alpha = new Value();

	// BUTTONS
	private List<Button> menuButtons;

	public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
		worldInstance = world;

		this.midPointY = midPointY;

		InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
		Array<InputProcessor> ipList = im.getProcessors();
		this.menuButtons = ((InputHandler) ipList.get(1)).getMenuButtons();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 136, gameHeight);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(camera.combined);
		sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);

		initGameObjects();
		initAssets();
		setupTweens();
	}

	public void render(float delta, float runTime) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sr.begin(ShapeType.Filled);

		// Draw Background color
		sr.setColor(183 / 255.0f, 210 / 255.0f, 246 / 255.0f, 1);
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

		if (worldInstance.isRunning()) {
			drawBird(runTime);
			drawScore();
			drawAimingVector();
			// drawUI();
		} else if (worldInstance.isReady()) {
			drawBird(runTime);
			drawScore();
			// drawUI();
		} else if (worldInstance.isMenu()) {
			drawBirdCentered(runTime);
			drawMenuUI();
		} else if (worldInstance.isGameOver()) {
			drawBird(runTime);
			drawScore();
			// drawUI();
		} else if (worldInstance.isHighScore()) {
			drawBird(runTime);
			drawScore();
		}

		batcher.end();
		drawTransition(delta);
	}

	private void drawAimingVector() {
		if (worldInstance.isAiming()) {

			batcher.disableBlending();
			sr.begin(ShapeType.Line);
			sr.setColor(Color.RED);
			sr.line(new Vector2(bird.getX() + 9, bird.getY() + 6), worldInstance.getBoostDir());
			sr.end();
			batcher.enableBlending();

		}

	}

	private void drawTransition(float delta) {
		if (alpha.getValue() > 0) {
			manager.update(delta);
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			sr.begin(ShapeType.Filled);
			sr.setColor(1, 1, 1, alpha.getValue());
			sr.rect(0, 0, 136, 300);
			sr.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);

		}
	}

	private void setupTweens() {
		Tween.registerAccessor(Value.class, new ValueAccessor());
		manager = new TweenManager();
		Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad).start(manager);
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
		boostButtonUp = AssetLoader.boostButtonUp;
		boostButtonDown = AssetLoader.boostButtonDown;
	}

	private void drawGrass() {
		// Draw the grass
		batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
		batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
	}

	private void drawSkulls() {

		batcher.draw(skullDown, pipe1.getX() - 1, pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
		batcher.draw(skullUp, pipe1.getX() - 1, pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

		batcher.draw(skullDown, pipe2.getX() - 1, pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
		batcher.draw(skullUp, pipe2.getX() - 1, pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

		batcher.draw(skullDown, pipe3.getX() - 1, pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
		batcher.draw(skullUp, pipe3.getX() - 1, pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
	}

	private void drawPipes() {

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

	private void drawBirdCentered(float runTime) {
		batcher.draw(birdAnimation.getKeyFrame(runTime), 59, bird.getY() - 15, bird.getWidth() / 2.0f,
				bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
	}

	private void drawBird(float runTime) {

		if (bird.stopAnimation()) {
			batcher.draw(birdMid, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
					bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());

		} else {
			batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
					bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
		}

	}

	private void drawUI() {
		menuButtons.get(1).draw(batcher);
	}

	private void drawMenuUI() {
		batcher.draw(AssetLoader.gameLogo, 136 / 2 - 56, midPointY - 50, AssetLoader.gameLogo.getRegionWidth() / 1.2f,
				AssetLoader.gameLogo.getRegionHeight() / 1.2f);

		menuButtons.get(0).draw(batcher);

	}

	private void drawScore() {
		int length = ("" + worldInstance.getScore()).length();
		AssetLoader.shadow.draw(batcher, "" + worldInstance.getScore(), 68 - (3 * length), midPointY - 82);
		AssetLoader.font.draw(batcher, "" + worldInstance.getScore(), 68 - (3 * length), midPointY - 83);
	}

}
