package se.bazookian.monorun.screens;

import se.bazookian.monorun.ScreenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameScreen implements Screen {
	protected AssetManager assetManager;
	protected ScreenManager screenManager;
	protected Stage stage;
	protected Color backgroundColor;
	
	public GameScreen(ScreenManager screenManager, AssetManager assetManager, boolean useStage) {
		if (screenManager == null) {
			throw new IllegalArgumentException("Screen manager must not be null");
		}
		
		if (assetManager == null) {
			throw new IllegalArgumentException("Asset manager must not be null");
		}
		
		this.screenManager = screenManager;
		this.assetManager = assetManager;
		
		if (useStage) {
			stage = new Stage(0, 0, true);
		}
		
		backgroundColor = new Color(0.3f, 0.3f, 0.4f, 1.0f);
	}
	
	public GameScreen(ScreenManager screenManager, AssetManager assetManager, boolean useStage, Color backgroundColor) {
		this(screenManager, assetManager, useStage);
		this.backgroundColor = backgroundColor;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (stage != null) {
			stage.act(delta);
			stage.draw();
		}
	}

	@Override
	public void resize(int width, int height) {
		if (stage != null) {
			stage.setViewport(width, height, true);
		}
	}

	@Override
	public void show() {
		if (stage != null) {
			Gdx.input.setInputProcessor(stage);
		}
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		if (stage != null) {
			stage.dispose();
		}
	}

	protected ScreenManager getScreenManager() {
		return screenManager;
	}

	protected AssetManager getAssetManager() {
		return assetManager;
	}
	
	protected Stage getStage() {
		return stage;
	}
}