package se.bazookian.monorun.screens;

import static com.badlogic.gdx.math.Interpolation.exp10Out;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import se.bazookian.monorun.GameState;
import se.bazookian.monorun.Monorun;
import se.bazookian.monorun.ScreenManager;
import se.bazookian.monorun.ui.ActionButton;
import se.bazookian.monorun.ui.ChangeScreenAction;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class StartScreen extends GameScreen {
	private static final Color BACKGROUND_COLOR = new Color(1, 1, 1, 1);
	
	private boolean assetsLoaded;
	private ActionButton startButton;
	
	public StartScreen(ScreenManager screenManager, AssetManager assetManager) {
		super(screenManager, assetManager, true, BACKGROUND_COLOR);
	}

	@Override
	public void show() {
		super.show();
		createLayout();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		if (!assetsLoaded && getAssetManager().update()) {
			assetsLoaded = true;
			
			startButton.addAction(sequence(delay(1), run(new Runnable() {
				@Override
				public void run() {
					float x = startButton.getX();
					float y = startButton.getY();
					
					startButton.setY(y + 50);
					startButton.addAction(parallel(moveTo(x, y, 1, exp10Out), fadeIn(1)));
				}
			}), delay(1), run(new Runnable() {
				@Override
				public void run() {
					startButton.setDisabled(false);
				}
			})));
		}
	}
	
	private void createLayout() {
		Skin skin = Monorun.getDefaultSkin();
		Table table = new Table(skin);

		table.setFillParent(true);
		
		Image logo = new Image(skin, "monorun-logo");
		
		Label tagLine = new Label("Monorun! is a minimalistic game about the transience of life. As the nucleus of an atom, it's your purpose to stay positive for as long as you can. Avoid those negative electrons.", skin);
		tagLine.setAlignment(Align.center);
		tagLine.setWrap(true);
		
		startButton = new ActionButton("stay positive!", skin, new ChangeScreenAction(getScreenManager(), GameState.GAMEPLAY));				
		startButton.setDisabled(true);
		startButton.addAction(fadeOut(0));
		
		table.add(logo).spaceBottom(30);
		table.row();
		table.add(tagLine).width(400).spaceBottom(30);
		table.row();
		table.add(startButton);
		
		getStage().addActor(table);
	}
}