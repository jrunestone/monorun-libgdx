package se.bazookian.monorun.entities;

import se.bazookian.monorun.components.Bounds;
import se.bazookian.monorun.components.Drag;
import se.bazookian.monorun.components.Position;
import se.bazookian.monorun.components.Sprite;
import se.bazookian.monorun.components.Velocity;

import com.artemis.Entity;
import com.artemis.World;

public class Enemy {
	public static final String SPRITE_NAME = "enemy";
	
	public static Entity create(World world, Position position, Velocity velocity) {
		Entity enemy = world.createEntity();
		
		enemy.addComponent(new se.bazookian.monorun.components.Enemy());
		enemy.addComponent(new Sprite(SPRITE_NAME));
		enemy.addComponent(position);
		enemy.addComponent(velocity);
		enemy.addComponent(new Drag(1));
		enemy.addComponent(new Bounds(36));
		
		return enemy;
	}
}