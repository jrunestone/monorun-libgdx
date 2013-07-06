package se.bazookian.monorun.systems;

import se.bazookian.monorun.Monorun;
import se.bazookian.monorun.components.Position;
import se.bazookian.monorun.components.Velocity;
import se.bazookian.monorun.entities.Enemy;

import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Timer;
import com.badlogic.gdx.math.MathUtils;

public class SpawningSystem extends VoidEntitySystem {
	public static final float SPAWN_INTERVAL = 2;
	public static final float MIN_VELOCITY_X = 200; 
	public static final float MAX_VELOCITY_Y = 1000;
	
	private Timer spawnTimer;
	
	@Override
	protected void initialize() {
		super.initialize();
		
		spawnTimer = new Timer(SPAWN_INTERVAL, true) {
			@Override
			public void execute() {
				Position position = new Position(0, Monorun.HEIGHT);
				Velocity velocity = new Velocity(MathUtils.random(MIN_VELOCITY_X, MAX_VELOCITY_Y), -MathUtils.random(MIN_VELOCITY_X, MAX_VELOCITY_Y));
				float scale = MathUtils.random(0, 1) == 0 ? 0.8f : 1;
				
				Enemy.create(world, position, velocity, scale).addToWorld();
			}
		}; 
	}
	
	@Override
	protected void processSystem() {
		spawnTimer.update(world.getDelta());
	}
}