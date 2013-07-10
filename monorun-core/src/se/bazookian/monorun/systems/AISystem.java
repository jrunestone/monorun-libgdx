package se.bazookian.monorun.systems;

import se.bazookian.monorun.components.Enemy;
import se.bazookian.monorun.components.IdleTimer;
import se.bazookian.monorun.components.Position;
import se.bazookian.monorun.components.AutonomousMovement;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.MathUtils;

public class AISystem extends EntitySystem {
	@Mapper ComponentMapper<IdleTimer> playerIdleMapper;
	@Mapper ComponentMapper<AutonomousMovement> movementMapper;
	@Mapper ComponentMapper<Position> positionMapper;
	
	private static final float IDLE_TIMEOUT = 500;
	private IdleTimer playerIdleTimer;
	
	public AISystem() {
		super(Aspect.getAspectForAll(Enemy.class, AutonomousMovement.class));
	}

	@Override
	protected void initialize() {
		super.initialize();
		
		Entity player = world.getManager(TagManager.class).getEntity(se.bazookian.monorun.entities.Player.TAG_NAME);
		playerIdleTimer = playerIdleMapper.get(player);
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		if (entities.size() < 2 || playerIdleTimer.milliseconds < IDLE_TIMEOUT) {
			return;
		}
		
		int randomIndex = MathUtils.random(0, entities.size() - 1);
		
		Entity chaser = entities.get(randomIndex);
		AutonomousMovement movement = movementMapper.get(chaser);
		
		movement.chasePlayer = true;
		playerIdleTimer.reset();
	}
}