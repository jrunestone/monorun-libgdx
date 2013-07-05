package se.bazookian.monorun.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;

import se.bazookian.monorun.ScreenManager;

public class GameplayScreen extends GameScreen {
	private static final Color BACKGROUND_COLOR = new Color(0.086f, 0.1f, 0.11f, 1);
	
	public GameplayScreen(ScreenManager screenManager, AssetManager assetManager) {
		super(screenManager, assetManager, false, BACKGROUND_COLOR);
	}
	
	@Override
	public void show() {
		
	}
}