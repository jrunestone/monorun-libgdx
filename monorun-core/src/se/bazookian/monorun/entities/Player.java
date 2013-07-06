package se.bazookian.monorun.entities;

import se.bazookian.monorun.Monorun;
import se.bazookian.monorun.components.Bounds;
import se.bazookian.monorun.components.Position;
import se.bazookian.monorun.components.Sprite;

import com.artemis.Entity;
import com.artemis.World;

public class Player {
	public static final String SPRITE_NAME = "player";
	
	public static Entity create(World world) {
		Entity player = world.createEntity();
		
		player.addComponent(new se.bazookian.monorun.components.Player());
		player.addComponent(new Sprite(SPRITE_NAME));
		player.addComponent(new Position(Monorun.WIDTH / 2, Monorun.HEIGHT / 2));
		player.addComponent(new Bounds(36));
		
		return player;
	}
}