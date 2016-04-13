package com.johncroker.fchelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	public static Texture spriteSheet, kiloboltLogoSprite;
	public static TextureRegion gameLogo, kiloboltLogo, playButtonUp, playButtonDown, bg, grass, skullUp, skullDown,
			pipeBody, bird, birdDown, birdUp;

	public static Animation birdAnimation;

	public static Sound dead, coin, flap, fall;

	public static BitmapFont font, shadow;

	private static Preferences prefs;

	public static void load() {
		// LOADING SPLASH SCREENS
		kiloboltLogoSprite = new Texture(Gdx.files.internal("data/kilologothank.png"));
		kiloboltLogoSprite.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		kiloboltLogo = new TextureRegion(kiloboltLogoSprite, 0, 0, 501, 200);

		// LOADING MENU ASSETS
		spriteSheet = new Texture(Gdx.files.internal("data/texture2.png"));
		spriteSheet.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		playButtonUp = new TextureRegion(spriteSheet, 1, 85, 28, 12);
		playButtonDown = new TextureRegion(spriteSheet, 30, 85, 28, 12);
		playButtonUp.flip(false, true);
		playButtonDown.flip(false, true);

		gameLogo = new TextureRegion(spriteSheet, 1, 56, 133, 19);
		gameLogo.flip(false, true);

		// LOADING INGAME ASSETS

		bg = new TextureRegion(spriteSheet, 0, 0, 136, 43);
		bg.flip(false, true);

		grass = new TextureRegion(spriteSheet, 0, 43, 143, 11);
		grass.flip(false, true);

		birdDown = new TextureRegion(spriteSheet, 3, 106, 27, 15);
		birdDown.flip(false, true);

		bird = new TextureRegion(spriteSheet, 30, 106, 27, 15);
		bird.flip(false, true);

		birdUp = new TextureRegion(spriteSheet, 59, 106, 27, 15);
		birdUp.flip(false, true);

		TextureRegion[] birds = { birdDown, bird, birdUp };
		birdAnimation = new Animation(0.06f, birds);
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		skullUp = new TextureRegion(spriteSheet, 192, 0, 24, 14);
		skullDown = new TextureRegion(skullUp);
		skullUp.flip(false, true);

		pipeBody = new TextureRegion(spriteSheet, 136, 16, 22, 3);
		pipeBody.flip(false, true);

		dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
		flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
		coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		fall = Gdx.audio.newSound(Gdx.files.internal("data/fall.wav"));

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.getData().setScale(.25f, -.25f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.getData().setScale(.25f, -.25f);

		prefs = Gdx.app.getPreferences("FlappyClone");

		if (!prefs.contains("highscore")) {
			prefs.putInteger("highscore", 0);
		}
	}

	public static void dispose() {
		spriteSheet.dispose();
		font.dispose();
		shadow.dispose();
		dead.dispose();
		coin.dispose();
		flap.dispose();
		fall.dispose();
	}

	public static void setHighScore(int score) {
		prefs.putInteger("highscore", score);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highscore");
	}
}
