package se.bazookian.monorun.screens;

import se.bazookian.monorun.Resources;
import se.bazookian.monorun.ScreenManager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.esotericsoftware.tablelayout.Value;

public class HighScoreScreen extends GameScreen {
	private static final Color BACKGROUND_COLOR = new Color(1, 1, 1, 1);
	
	public HighScoreScreen(ScreenManager screenManager, AssetManager assetManager) {
		super(screenManager, assetManager, true, BACKGROUND_COLOR);
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
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
	}
	
	private void createLayout() {
		Skin skin = getAssetManager().get(Resources.UI_SKIN, Skin.class);
		
		Table table = new Table(skin);
		table.setFillParent(true);
		
		Label heading = new Label("high score!", skin, "heading");
		heading.setAlignment(Align.center);
		
		Label rankHeading = new Label("rank", skin, "table-heading");
		rankHeading.setAlignment(Align.left);
		
		Label nameHeading = new Label("name", skin, "table-heading");
		nameHeading.setAlignment(Align.center);
		
		Label hoursHeading = new Label("hours", skin, "table-heading");
		hoursHeading.setAlignment(Align.right);
		
		table.add(heading).spaceBottom(30).colspan(3);
		table.row();
		table.add(rankHeading).width(125);
		table.add(nameHeading).width(167);
		table.add(hoursHeading).width(125);
		table.row();
		
		for (int i = 0; i < 10; i++) {
			Label rank = new Label("" + i, skin);
			rank.setAlignment(Align.left);

			Label name = new Label("Kronos Corona", skin);
			name.setAlignment(Align.center);
			
			Label hours = new Label("" + MathUtils.random(100), skin);
			hours.setAlignment(Align.right);
			
			table.add(rank).width(125);
			table.add(name).width(167);
			table.add(hours).width(125);
			table.row();
		}
		
		getStage().addActor(table);
	}
}