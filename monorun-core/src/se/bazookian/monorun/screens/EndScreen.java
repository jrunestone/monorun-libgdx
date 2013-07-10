package se.bazookian.monorun.screens;

import se.bazookian.monorun.GameState;
import se.bazookian.monorun.Resources;
import se.bazookian.monorun.ScreenManager;
import se.bazookian.monorun.ui.ActionButton;
import se.bazookian.monorun.ui.ChangeScreenAction;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class EndScreen extends GameScreen {
	private static final Color BACKGROUND_COLOR = new Color(1, 1, 1, 1);
	
	private float elapsedTime;
	
	private ActionButton retryButton;
	private ActionButton highScoreButton;
	
	public EndScreen(ScreenManager screenManager, AssetManager assetManager) {
		super(screenManager, assetManager, true, BACKGROUND_COLOR);
	}
	
	public void setElapsedTime(float elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	
	@Override
	public void show() {
		super.show();
		createLayout();
	}
	
	@Override
	public void hide() {
		stage.clear();
	}
	
	private void createLayout() {
		Skin skin = getAssetManager().get(Resources.UI_SKIN, Skin.class);
		
		Table table = new Table(skin);
		table.setFillParent(true);
		
		Label heading = new Label("congratulations!", skin, "heading");
		heading.setAlignment(Align.center);
		
		int elapsedMs = Math.round(elapsedTime * 1000.0f);
		int halfLife = Math.round(elapsedTime);
		
		Label text1 = new Label("You stayed positive for " + elapsedMs + "ms which gives your high score a half-life of " + halfLife + "h.", skin);
		text1.setWrap(true);
		text1.setAlignment(Align.center);
		
		Label text2 = new Label("Like everything in life, your high score will decay with time. Enjoy it while it lasts.", skin);
		text2.setWrap(true);
		text2.setAlignment(Align.center);
		
		TextField nameField = new TextField("Dirk Diggler", skin);

		retryButton = new ActionButton("retry", skin, new ChangeScreenAction(getScreenManager(), GameState.GAMEPLAY));
		highScoreButton = new ActionButton("view high score!", skin, new ChangeScreenAction(getScreenManager(), GameState.HIGHSCORE));	
		
		table.add(heading).spaceBottom(30);
		table.row();
		table.add(text1).width(400).spaceBottom(30);
		table.row();
		table.add(text2).width(400).spaceBottom(30);
		table.row();
		table.add(nameField).width(242).height(32).padLeft(10).spaceBottom(30);
		table.row();
		table.add(retryButton);
		table.row();
		table.add(highScoreButton);
		
		getStage().addActor(table);
	}
}