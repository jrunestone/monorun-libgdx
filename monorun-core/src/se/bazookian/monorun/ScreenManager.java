package se.bazookian.monorun;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import se.bazookian.monorun.screens.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class ScreenManager {
	private Game game;
	private HashMap<GameState, Screen> screens;
	
	public ScreenManager(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("Game must not be null");
		}
		
		this.game = game;
		screens = new HashMap<GameState, Screen>();
	}
	
	public <T extends GameScreen> void addScreen(GameState id, Class<T> screenType) {
		GameScreen screen = null;
		
		try {
			Constructor<T> ctor = screenType.getConstructor(ScreenManager.class);
			screen = ctor.newInstance(this);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("Invalid screen type");
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Invalid screen type");
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Invalid screen type");
		} catch (SecurityException e) {
			throw new IllegalArgumentException("Invalid screen type");
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid screen type");
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException("Invalid screen type");
		}
		
		screens.put(id, screen);
	}
	
	public Screen getScreen(GameState id) {
		return screens.get(id);
	}
	
	public void setScreen(GameState id) {
		Screen screen = getScreen(id);
		
		if (screen == null) {
			throw new IllegalArgumentException("Invalid id");
		}
		
		game.setScreen(screen);
	}
	
	public Screen getCurrentScreen() {
		return game.getScreen();
	}
}