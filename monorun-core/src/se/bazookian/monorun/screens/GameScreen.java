package se.bazookian.monorun.screens;

import se.bazookian.monorun.ScreenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameScreen implements Screen {
	protected ScreenManager screenManager;
	protected Stage stage;
	protected Color backgroundColor;
	
	public GameScreen(ScreenManager screenManager, boolean useStage) {
		if (screenManager == null) {
			throw new IllegalArgumentException("Screen manager must not be null");
		}
		
		this.screenManager = screenManager;
		
		if (useStage) {
			stage = new Stage(0, 0, true);
		}
		
		backgroundColor = new Color(0.3f, 0.3f, 0.4f, 1.0f);
	}
	
	protected ScreenManager getScreenManager() {
		return screenManager;
	}
	
	protected Color getBackgroundColor() {
		return backgroundColor;
	}
	
	protected void setBackgroundColor(float r, float g, float b, float a) {
		backgroundColor.set(r, g, b, a);
	}

	protected Stage getStage() {
		return stage;
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
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		if (stage != null) {
			stage.dispose();
		}
	}
}