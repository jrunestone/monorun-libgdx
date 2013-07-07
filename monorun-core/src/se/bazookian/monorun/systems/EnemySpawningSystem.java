package se.bazookian.monorun.systems;

import se.bazookian.monorun.Monorun;
import se.bazookian.monorun.components.Position;
import se.bazookian.monorun.entities.Enemy;

import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Timer;
import com.badlogic.gdx.math.MathUtils;

public class EnemySpawningSystem extends VoidEntitySystem {
	private static final float SPAWN_INTERVAL = 2;
	private Timer spawnTimer;
	
	@Override
	protected void initialize() {
		super.initialize();
		
		spawnTimer = new Timer(SPAWN_INTERVAL, true) {
			@Override
			public void execute() {
				Position position = new Position(0, Monorun.HEIGHT);
				float scale = MathUtils.random(0, 1) == 0 ? 0.7f : 1;
				
				Enemy.create(world, position, scale).addToWorld();
			}
		}; 
	}
	
	@Override
	protected void processSystem() {
		spawnTimer.update(world.getDelta());
	}
}