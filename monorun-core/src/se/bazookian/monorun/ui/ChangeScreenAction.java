package se.bazookian.monorun.ui;

import se.bazookian.monorun.GameState;
import se.bazookian.monorun.ScreenManager;

public class ChangeScreenAction implements UIAction {
	private ScreenManager screenManager;
	private GameState screenId;
	
	public ChangeScreenAction(ScreenManager screenManager, GameState screenId) {
		if (screenManager == null) {
			throw new IllegalArgumentException("Screen manager must not be null");
		}
		
		this.screenManager = screenManager;
		this.screenId = screenId;
	}
	
	public void execute() {
		screenManager.setScreen(screenId);
	}
}
