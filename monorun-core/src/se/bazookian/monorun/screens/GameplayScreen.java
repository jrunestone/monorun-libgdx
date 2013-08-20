package se.bazookian.monorun.screens;

import java.util.ArrayList;

import se.bazookian.monorun.ScreenManager;
import se.bazookian.monorun.entities.Player;
import se.bazookian.monorun.services.ScoreService;
import se.bazookian.monorun.services.ScoreService.HighScore;
import se.bazookian.monorun.services.ScoreService.ScoreListener;
import se.bazookian.monorun.systems.AISystem;
import se.bazookian.monorun.systems.CollisionSystem;
import se.bazookian.monorun.systems.LinePairRenderingSystem;
import se.bazookian.monorun.systems.AutonomousMovementSystem;
import se.bazookian.monorun.systems.PlayerInputSystem;
import se.bazookian.monorun.systems.ScoreSystem;
import se.bazookian.monorun.systems.SpriteRenderingSystem;
import se.bazookian.monorun.systems.EnemySpawningSystem;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;

public class GameplayScreen extends GameScreen implements ScoreListener {
	private static final Color BACKGROUND_COLOR = new Color(0.1f, 0.13f, 0.14f, 1);
	
	private World world;
	private ScoreService scoreService;
	
	public GameplayScreen(ScreenManager screenManager, AssetManager assetManager) {
		super(screenManager, assetManager, false, BACKGROUND_COLOR);
	}
	
	@Override
	public void show() {
		world = new World();
		scoreService = new ScoreService(this);
		
		TagManager tagManager = world.setManager(new TagManager());
		
		world.setSystem(new EnemySpawningSystem(getAssetManager()));
		world.setSystem(new PlayerInputSystem());
		world.setSystem(new AISystem());
		world.setSystem(new AutonomousMovementSystem());
		world.setSystem(new CollisionSystem());
		world.setSystem(new ScoreSystem(screenManager));
		world.setSystem(new LinePairRenderingSystem());
		world.setSystem(new SpriteRenderingSystem(getAssetManager()));
		
		Entity player = Player.create(world);
		player.addToWorld();
		tagManager.register(Player.TAG_NAME, player);
		
		world.initialize();
		scoreService.startSession();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		world.setDelta(delta);
		world.process();
	}

	@Override
	public void scoresReceived(ArrayList<HighScore> scores) {
		
	}

	@Override
	public void fetchScoresFailure(Throwable error) {
		
	}

	@Override
	public void sessionStarted() {
		
	}

	@Override
	public void startSessionFailure(Throwable error) {
		
	}

	@Override
	public void sessionEnded() {
	
	}

	@Override
	public void endSessionFailure(Throwable error) {
		
	}
}