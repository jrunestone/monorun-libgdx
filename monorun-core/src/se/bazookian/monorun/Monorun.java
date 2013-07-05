package se.bazookian.monorun;

import se.bazookian.monorun.screens.GameplayScreen;
import se.bazookian.monorun.screens.EndScreen;
import se.bazookian.monorun.screens.StartScreen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Monorun extends Game implements ApplicationListener {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
		
	private static Skin defaultSkin;
	
	private ScreenManager screenManager;
	
	@Override
	public void create() {
		screenManager = new ScreenManager(this);
		
		createScreens();
		init();
	}
	
	public static Skin getDefaultSkin() {
		if (defaultSkin == null) {
			defaultSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		}
		
		return defaultSkin;
	}
	
	private void createScreens() {
		screenManager.addScreen(GameState.START, StartScreen.class);
		screenManager.addScreen(GameState.GAMEPLAY, GameplayScreen.class);
		screenManager.addScreen(GameState.END, EndScreen.class);
	}
	
	private void init() {
		screenManager.setScreen(GameState.START);
	}
}