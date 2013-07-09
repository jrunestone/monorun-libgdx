package se.bazookian.monorun.systems;

import se.bazookian.monorun.components.Health;
import se.bazookian.monorun.components.Player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;

public class ScoreSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Health> healthMapper;
	
	public ScoreSystem() {
		super(Aspect.getAspectForAll(Player.class, Health.class));
	}

	@Override
	protected void process(Entity entity) {
		Health health = healthMapper.get(entity);
		
		if (health.isDead) {
			Gdx.app.log("asd", "dead");
		}
	}
}