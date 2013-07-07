package se.bazookian.monorun.entities;

import se.bazookian.monorun.components.Bounds;
import se.bazookian.monorun.components.Position;
import se.bazookian.monorun.components.AutonomousMovement;
import se.bazookian.monorun.components.Sprite;

import com.artemis.Entity;
import com.artemis.World;

public class Enemy {
	public static final String SPRITE_NAME = "enemy";
	
	public static Entity create(World world, Position position, float scale) {
		Entity enemy = world.createEntity();
		
		enemy.addComponent(new se.bazookian.monorun.components.Enemy());
		enemy.addComponent(new Sprite(SPRITE_NAME, scale));
		enemy.addComponent(position);
		enemy.addComponent(new AutonomousMovement(new Position(position.x, position.y), new Position(position.x, position.y), 0));
		enemy.addComponent(new Bounds(36.0f * scale));
		
		return enemy;
	}
}