package com.johncroker.fchelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	public static Texture spriteSheet;
	public static TextureRegion bg, grass;

	public static Animation birdAnimation;
	public static TextureRegion bird, birdDown, birdUp;

	public static TextureRegion skullUp, skullDown, pipeBody;

	public static Sound dead;
	public static Sound coin;
	public static Sound flap;

	public static BitmapFont font, shadow;

	public static void load() {
		spriteSheet = new Texture(Gdx.files.internal("data/texture.png"));
		spriteSheet.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		bg = new TextureRegion(spriteSheet, 0, 0, 136, 43);
		bg.flip(false, true);

		grass = new TextureRegion(spriteSheet, 0, 43, 143, 11);
		grass.flip(false, true);

		birdDown = new TextureRegion(spriteSheet, 136, 0, 17, 12);
		birdDown.flip(false, true);

		bird = new TextureRegion(spriteSheet, 153, 0, 17, 12);
		bird.flip(false, true);

		birdUp = new TextureRegion(spriteSheet, 170, 0, 17, 12);
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

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.getData().setScale(.25f, -.25f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.getData().setScale(.25f, -.25f);
	}

	public static void dispose() {
		spriteSheet.dispose();
		font.dispose();
		font.dispose();
	}
}
