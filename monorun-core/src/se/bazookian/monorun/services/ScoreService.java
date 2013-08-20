package se.bazookian.monorun.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.HttpParametersUtils;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;

public class ScoreService {
	private static final String HIGHSCORE_API_URL = "http://dev.monorun.com/api/api.php";
	private static String sessionId;
	
	public static enum Device {
		Web(1),
		Android(2),
		WindowsPhone(3);
		
		private int id;
		
		private Device(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}
	
	public static interface ScoreListener {
		public void scoresReceived(ArrayList<HighScore> scores);
		public void fetchScoresFailure(Throwable error);
		
		public void sessionStarted();
		public void startSessionFailure(Throwable error);
		
		public void sessionEnded();
		public void endSessionFailure(Throwable error);
	}
	
	public static class HighScore {
		public int id;
		public String username;
		public long dateline;
		public int score;
		public int position;
	}

	private ScoreListener listener;
	
	public ScoreService(ScoreListener listener) {
		this.listener = listener;
	}

	public void startSession() {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("do", "register");

		HttpRequest request = this.buildRequest(args);
		
		Gdx.net.sendHttpRequest(request, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse response) {
				sessionStarted(response);
			}
			
			@Override
			public void failed(Throwable error) {
				startSessionFailure(error);
			}
		});
	}
	
	public void endSession(String name, int score) {
		HashMap<String, String> args = new HashMap<String, String>();
		
		args.put("do", "put");
		args.put("username", name);
		args.put("score", Integer.toString(score));
		args.put("playerid", sessionId);
		args.put("sourceid", Integer.toString(getDeviceId()));

		HttpRequest request = this.buildRequest(args);
		
		Gdx.net.sendHttpRequest(request, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse response) {
				sessionEnded(response);
			}
			
			@Override
			public void failed(Throwable error) {
				endSessionFailure(error);
			}
		});
		
		sessionId = "";
	}
	
	public void fetchHighScores() {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("do", "get");

		HttpRequest request = this.buildRequest(args);
		
		Gdx.net.sendHttpRequest(request, new HttpResponseListener() {
			@Override
			public void handleHttpResponse(HttpResponse response) {
				scoresFetched(response);
			}
			
			@Override
			public void failed(Throwable error) {
				fetchScoresFailure(error);
			}
		});
	}

	public void scoresFetched(HttpResponse response) {
		String json = response.getResultAsString();
		
		JsonReader reader = new JsonReader();
		Json parser = new Json();
		
		try {
			Array<Object> items = (Array<Object>)reader.parse(json);
			ArrayList<HighScore> scores = new ArrayList<HighScore>();
			
			for (Object i : items) {
				HighScore item = parser.readValue(HighScore.class, i);
				scores.add(item);
			}
			
			listener.scoresReceived(scores);
		} catch (Exception e) {
			fetchScoresFailure(e);
		}
	}
	
	public void fetchScoresFailure(Throwable error) {
		listener.fetchScoresFailure(error);
	}
	
	public void sessionStarted(HttpResponse response) {
		String result = response.getResultAsString();
		
		try {
			JsonReader reader = new JsonReader();
			sessionId = (String)reader.parse(result);
		} catch (Exception e) {
			sessionId = "";
		}
		
		if (sessionId == null || sessionId.length() == 0) {
			startSessionFailure(null);
			return;
		}
		
		listener.sessionStarted();
	}
	
	public void startSessionFailure(Throwable error) {
		listener.startSessionFailure(error);
	}
	
	public void sessionEnded(HttpResponse response) {
		String result = response.getResultAsString();
		
		if (result.equalsIgnoreCase("false")) {
			endSessionFailure(null);
			return;
		}
		
		listener.sessionEnded();
	}
	
	public void endSessionFailure(Throwable error) {
		listener.endSessionFailure(error);
	}
	
	private HttpRequest buildRequest(HashMap<String, String> args) {
		HttpRequest request = new HttpRequest(HttpMethods.GET);
		
		request.setUrl(HIGHSCORE_API_URL);
		request.setContent(HttpParametersUtils.convertHttpParameters(args));
		
		return request;
	}
	
	private int getDeviceId() {
		ApplicationType appType = Gdx.app.getType();
		
		if (appType == ApplicationType.Android)
			return Device.Android.getId();
		
		return Device.Web.getId();
	}
}