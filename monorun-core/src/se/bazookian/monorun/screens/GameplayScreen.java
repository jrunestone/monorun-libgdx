package se.bazookian.monorun.screens;

import se.bazookian.monorun.ScreenManager;
import se.bazookian.monorun.entities.Player;
import se.bazookian.monorun.systems.RandomMovementSystem;
import se.bazookian.monorun.systems.PlayerInputSystem;
import se.bazookian.monorun.systems.SpriteRenderingSystem;
import se.bazookian.monorun.systems.EnemySpawningSystem;

import com.artemis.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;

public class GameplayScreen extends GameScreen {
	private static final Color BACKGROUND_COLOR = new Color(0.086f, 0.1f, 0.11f, 1);
	private World world;
	
	public GameplayScreen(ScreenManager screenManager, AssetManager assetManager) {
		super(screenManager, assetManager, false, BACKGROUND_COLOR);
	}
	
	@Override
	public void show() {
		world = new World();
		
		world.setSystem(new EnemySpawningSystem());
		world.setSystem(new PlayerInputSystem());
		world.setSystem(new RandomMovementSystem());
		world.setSystem(new SpriteRenderingSystem(getAssetManager()));
		
		Player.create(world).addToWorld();
		
		world.initialize();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		world.setDelta(delta);
		world.process();
	}
}