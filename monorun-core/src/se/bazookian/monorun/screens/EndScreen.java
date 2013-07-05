package se.bazookian.monorun.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;

import se.bazookian.monorun.ScreenManager;

public class EndScreen extends GameScreen {
	private static final Color BACKGROUND_COLOR = new Color(1, 1, 1, 1);
	
	public EndScreen(ScreenManager screenManager, AssetManager assetManager) {
		super(screenManager, assetManager, false, BACKGROUND_COLOR);
	}
}