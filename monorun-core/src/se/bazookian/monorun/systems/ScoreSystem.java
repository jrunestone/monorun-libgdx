package se.bazookian.monorun.systems;

import se.bazookian.monorun.GameState;
import se.bazookian.monorun.ScreenManager;
import se.bazookian.monorun.components.Health;
import se.bazookian.monorun.components.Player;
import se.bazookian.monorun.screens.EndScreen;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class ScoreSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Health> healthMapper;
	
	private ScreenManager screenManager;
	private float elapsedTime;
	
	public ScoreSystem(ScreenManager screenManager) {
		super(Aspect.getAspectForAll(Player.class, Health.class));
		this.screenManager = screenManager;
	}
	
	@Override
	protected void process(Entity entity) {
		Health health = healthMapper.get(entity);

		elapsedTime += world.getDelta();
		
		if (health.isDead) {
			EndScreen endScreen = (EndScreen)screenManager.getScreen(GameState.END);
			endScreen.setElapsedTime(elapsedTime);
			
			screenManager.setScreen(GameState.END);
		}
	}
}