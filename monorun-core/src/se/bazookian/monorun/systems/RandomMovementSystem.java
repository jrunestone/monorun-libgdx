package se.bazookian.monorun.systems;

import se.bazookian.monorun.Monorun;
import se.bazookian.monorun.components.Position;
import se.bazookian.monorun.components.RandomMovement;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class RandomMovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> positionMapper;
	@Mapper ComponentMapper<RandomMovement> movementMapper;
	
	public RandomMovementSystem() {
		super(Aspect.getAspectForAll(Position.class, RandomMovement.class));
	}

	@Override
	protected void process(Entity entity) {
		Position position = positionMapper.get(entity);
		RandomMovement movement = movementMapper.get(entity);

		if (Utils.distance(position.x, position.y, movement.next.x, movement.next.y) < 5) {
			resetMovement(position, movement);
		}
		
		movement.elapsed += world.delta;
		
		position.x = easeOut(movement.elapsed, movement.previous.x, movement.next.x - movement.previous.x, movement.speed);
		position.y = easeOut(movement.elapsed, movement.previous.y, movement.next.y - movement.previous.y, movement.speed);
	}
	
	private static void resetMovement(Position currentPosition, RandomMovement movement) {
		movement.previous = new Position(currentPosition.x, currentPosition.y);
		movement.next = new Position(MathUtils.random(0.0f, Monorun.WIDTH), MathUtils.random(0.0f, Monorun.HEIGHT)); 
		movement.speed = MathUtils.random(2.0f, 5.0f);
		movement.elapsed = 0;
	}
	
	private static float easeOut(float t, float b, float c, float d) {
		return (t == d) ? b + c : c * (-(float)Math.pow(2, -10 * t / d) + 1) + b;	
	}
}