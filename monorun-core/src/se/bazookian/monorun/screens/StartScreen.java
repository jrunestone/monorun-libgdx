package se.bazookian.monorun.screens;

import static com.badlogic.gdx.math.Interpolation.exp10Out;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import se.bazookian.monorun.GameState;
import se.bazookian.monorun.Monorun;
import se.bazookian.monorun.ScreenManager;
import se.bazookian.monorun.ui.ActionButton;
import se.bazookian.monorun.ui.ChangeScreenAction;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class StartScreen extends GameScreen {
	private ActionButton startButton;
	
	public StartScreen(ScreenManager screenManager) {
		super(screenManager, true);
		setBackgroundColor(1, 1, 1, 1);
	}

	@Override
	public void show() {
		super.show();
		createLayout();
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

		startButton.addAction(sequence(fadeOut(0), delay(1.0f), run(new Runnable() {
			@Override
			public void run() {
				float x = startButton.getX();
				float y = startButton.getY();
				
				startButton.setY(y + 50);
				startButton.addAction(parallel(moveTo(x, y, 1, exp10Out), fadeIn(1)));
			}
		})));
		
		table.add(logo).spaceBottom(30);
		table.row();
		table.add(tagLine).width(400).spaceBottom(30);
		table.row();
		table.add(startButton);
		
		getStage().addActor(table);
	}
}