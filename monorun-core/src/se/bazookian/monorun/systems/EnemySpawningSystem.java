package se.bazookian.monorun.systems;

import se.bazookian.monorun.Monorun;
import se.bazookian.monorun.components.Position;
import se.bazookian.monorun.components.Velocity;
import se.bazookian.monorun.entities.Enemy;

import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Timer;
import com.badlogic.gdx.math.MathUtils;

public class EnemySpawningSystem extends VoidEntitySystem {
	private Timer spawnTimer;
	
	@Override
	protected void initialize() {
		super.initialize();
		
		spawnTimer = new Timer(2, true) {
			@Override
			public void execute() {
				Position position = new Position(0, Monorun.HEIGHT);
				Velocity velocity = new Velocity(MathUtils.random(100, 1000), -MathUtils.random(100, 1000));
				
				Enemy.create(world, position, velocity).addToWorld();
			}
		}; 
	}
	
	@Override
	protected void processSystem() {
		spawnTimer.update(world.getDelta());
	}
}