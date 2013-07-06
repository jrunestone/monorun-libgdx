package se.bazookian.monorun.systems;

import se.bazookian.monorun.components.Drag;
import se.bazookian.monorun.components.Position;
import se.bazookian.monorun.components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class MovementSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> positionMapper;
	@Mapper ComponentMapper<Velocity> velocityMapper;
	@Mapper ComponentMapper<Drag> dragMapper;
	
	public MovementSystem() {
		super(Aspect.getAspectForAll(Position.class, Velocity.class));
	}

	@Override
	protected void process(Entity entity) {
		Position position = positionMapper.get(entity);
		Velocity velocity = velocityMapper.get(entity);
		
		if (dragMapper.has(entity)) {
			Drag drag = dragMapper.get(entity);
			
			velocity.x -= (velocity.x * drag.value) * world.getDelta();
			velocity.y -= (velocity.y * drag.value) * world.getDelta();
		}
		
		position.x += velocity.x * world.getDelta();
		position.y += velocity.y * world.getDelta();
	}
}