package se.bazookian.monorun.screens;

import java.util.ArrayList;

import se.bazookian.monorun.GameState;
import se.bazookian.monorun.Resources;
import se.bazookian.monorun.ScreenManager;
import se.bazookian.monorun.services.ScoreService;
import se.bazookian.monorun.services.ScoreService.HighScore;
import se.bazookian.monorun.services.ScoreService.ScoreListener;
import se.bazookian.monorun.ui.ActionButton;
import se.bazookian.monorun.ui.ChangeScreenAction;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class HighScoreScreen extends GameScreen implements ScoreListener {
	private static final Color BACKGROUND_COLOR = new Color(1, 1, 1, 1);
	
	private ScoreService service;
	private Table table;
	private Label loadingLabel;
	
	private String username;
	private int score;
	
	public HighScoreScreen(ScreenManager screenManager, AssetManager assetManager) {
		super(screenManager, assetManager, true, BACKGROUND_COLOR);
	}
	
	
	@Override
	public void show() {
		super.show();
		
		service = new ScoreService(this);
		service.endSession(username, score);
		
		createLayout();
	}
	
	@Override
	public void hide() {
		stage.clear();
	}
	
	public void setUsername(String name) {
		username = name;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	private void createLayout() {
		Skin skin = getAssetManager().get(Resources.UI_SKIN, Skin.class);
		
		table = new Table(skin);
		table.setFillParent(true);
		
		Label heading = new Label("high score!", skin, "heading");
		heading.setAlignment(Align.center);
		
		loadingLabel = new Label("loading...", skin);
		loadingLabel.setAlignment(Align.center);
		
		table.add(heading).spaceBottom(30).colspan(3);
		table.row();
		
		table.add(loadingLabel).colspan(3);
		table.row();
		
		getStage().addActor(table);
	}
	
	private void createBottomLayout() {
		Skin skin = getAssetManager().get(Resources.UI_SKIN, Skin.class);
		
		Label text = new Label("Like everything in life, your high score will decay with time. Enjoy it while it lasts.", skin);
		text.setWrap(true);
		text.setAlignment(Align.center);
		
		table.add(text).width(400).spaceTop(30).spaceBottom(30).colspan(3);
		table.row();
		
		ActionButton retryButton = new ActionButton("stay positive!", skin, new ChangeScreenAction(getScreenManager(), GameState.START));
		table.add(retryButton).spaceBottom(30).colspan(3);
		table.row();
		
		Image divider = new Image(skin, "divider");
		table.add(divider).spaceBottom(30).colspan(3);
		table.row();
		
		Label authorInfo = new Label("https://github.com/beije/monorun\nhttps://github.com/swemaniac/monorun-libgdx", skin, "small");
		authorInfo.setAlignment(Align.center);
		authorInfo.setWrap(true);
		
		table.add(authorInfo).width(400).colspan(3);
	}
	
	private void createScoreLayout(ArrayList<HighScore> scores) {
		Skin skin = getAssetManager().get(Resources.UI_SKIN, Skin.class);
		
		Label rankHeading = new Label("rank", skin, "table-heading");
		rankHeading.setAlignment(Align.left);
		
		Label nameHeading = new Label("name", skin, "table-heading");
		nameHeading.setAlignment(Align.center);
		
		Label hoursHeading = new Label("hours", skin, "table-heading");
		hoursHeading.setAlignment(Align.right);
				
		table.removeActor(loadingLabel);
		table.add(rankHeading).width(125);
		table.add(nameHeading).width(167);
		table.add(hoursHeading).width(125);
		table.row();
		
		for (int i = 0; i < scores.size(); i++) {
			HighScore score = scores.get(i);
			
			Label rank = new Label("" + (i + 1), skin);
			rank.setAlignment(Align.left);

			Label name = new Label(score.username, skin);
			name.setAlignment(Align.center);
			
			Label hours = new Label("" + Math.round(score.score / 1000.0f), skin);
			hours.setAlignment(Align.right);
			
			table.add(rank).width(125);
			table.add(name).width(167);
			table.add(hours).width(125);
			
			table.row().spaceTop(5);
		}
		
		createBottomLayout();
	}
	
	private void createFailLayout() {
		Skin skin = getAssetManager().get(Resources.UI_SKIN, Skin.class);
		
		Label error = new Label("Sorry, can't fetch scores at this time.", skin, "error");
		error.setAlignment(Align.center);
		
		table.removeActor(loadingLabel);
		table.add(error).colspan(3);
		table.row();
		
		createBottomLayout();
	}
	
	@Override
	public void scoresReceived(ArrayList<HighScore> scores) {
		createScoreLayout(scores);
	}

	@Override
	public void fetchScoresFailure(Throwable error) {
		createFailLayout();
	}

	@Override
	public void sessionStarted() {
	
	}


	@Override
	public void startSessionFailure(Throwable error) {
		
	}

	@Override
	public void sessionEnded() {
		service.fetchHighScores();
	}


	@Override
	public void endSessionFailure(Throwable error) {
		createFailLayout();
	}
}