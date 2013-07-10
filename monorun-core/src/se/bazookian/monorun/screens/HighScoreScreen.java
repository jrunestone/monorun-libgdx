package se.bazookian.monorun.screens;

import se.bazookian.monorun.ScreenManager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;

public class HighScoreScreen extends GameScreen {
	private static final Color BACKGROUND_COLOR = new Color(1, 1, 1, 1);
	
	public HighScoreScreen(ScreenManager screenManager, AssetManager assetManager) {
		super(screenManager, assetManager, true, BACKGROUND_COLOR);
	}
}