package se.bazookian.monorun.systems;

import se.bazookian.monorun.components.Bounds;
import se.bazookian.monorun.components.Enemy;
import se.bazookian.monorun.components.Health;
import se.bazookian.monorun.components.Position;
import se.bazookian.monorun.entities.Player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Utils;

public class CollisionSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Position> positionMapper;
	@Mapper ComponentMapper<Bounds> boundsMapper;
	@Mapper ComponentMapper<Health> healthMapper;
		
	public CollisionSystem() {
		super(Aspect.getAspectForAll(Enemy.class, Position.class, Bounds.class));
	}

	@Override
	protected void process(Entity entity) {
		Entity player = world.getManager(TagManager.class).getEntity(Player.TAG_NAME);
		
		Position playerPosition = positionMapper.get(player);
		Position entityPosition = positionMapper.get(entity);
		
		Bounds playerBounds = boundsMapper.get(player);
		Bounds entityBounds = boundsMapper.get(entity);
		
		if (Utils.collides(entityPosition.x, entityPosition.y, entityBounds.radius, playerPosition.x, playerPosition.y, playerBounds.radius)) {
			Health playerHealth = healthMapper.get(player);
			playerHealth.isDead = true;
		}
	}
}