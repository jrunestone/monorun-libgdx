package se.bazookian.monorun.screens;

import java.util.ArrayList;
import java.util.HashMap;

import se.bazookian.monorun.GameState;
import se.bazookian.monorun.Resources;
import se.bazookian.monorun.ScreenManager;
import se.bazookian.monorun.ui.ActionButton;
import se.bazookian.monorun.ui.ChangeScreenAction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.HttpParametersUtils;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;

public class HighScoreScreen extends GameScreen implements HttpResponseListener {
	private static final Color BACKGROUND_COLOR = new Color(1, 1, 1, 1);
	private static final String HIGHSCORE_API_URL = "http://monorun.com/api/api.php";
	
	private Table table;
	private Label loadingLabel;
	private int score;
	
	public static class HighScore {
		public int id;
		public String username;
		public long dateline;
		public int score;
		public int position;
	}
	
	public HighScoreScreen(ScreenManager screenManager, AssetManager assetManager) {
		super(screenManager, assetManager, true, BACKGROUND_COLOR);
	}
	
	
	@Override
	public void show() {
		super.show();
		
		getHighScore();
		createLayout();
	}
	
	@Override
	public void hide() {
		stage.clear();
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
		
		loadingLabel = new Label("loading scores...", skin);
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
		
		ActionButton retryButton = new ActionButton("stay positive!", skin, new ChangeScreenAction(getScreenManager(), GameState.GAMEPLAY));
		table.add(retryButton).colspan(3);
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
	}
	
	private void createFailLayout() {
		Skin skin = getAssetManager().get(Resources.UI_SKIN, Skin.class);
		
		Label error = new Label("can't fetch scores at this time.", skin);
		error.setAlignment(Align.center);
		
		table.removeActor(loadingLabel);
		table.add(error).colspan(3);
		table.row();
	}
	
	private void getHighScore() {
		HttpRequest request = new HttpRequest(HttpMethods.POST);
		HashMap<String, String> args = new HashMap<String, String>();
		
		args.put("do", "get");
		
		request.setUrl(HIGHSCORE_API_URL);
		request.setContent(HttpParametersUtils.convertHttpParameters(args));
		
		Gdx.net.sendHttpRequest(request, this);
	}

	@Override
	public void handleHttpResponse(HttpResponse response) {
		String json = response.getResultAsString();
		JsonReader reader = new JsonReader();
		Json parser = new Json();
		Array<Object> items = (Array<Object>)reader.parse(json);
		ArrayList<HighScore> scores = new ArrayList<HighScore>();
		
		for (Object i : items) {
			HighScore item = parser.readValue(HighScore.class, i);
			scores.add(item);
		}
		
		createScoreLayout(scores);
		createBottomLayout();
	}
	
	@Override
	public void failed(Throwable error) {
		createFailLayout();
		createBottomLayout();
	}
}