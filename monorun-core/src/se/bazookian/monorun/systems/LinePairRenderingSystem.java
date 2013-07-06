package se.bazookian.monorun.systems;

import se.bazookian.monorun.components.Enemy;
import se.bazookian.monorun.components.Position;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LinePairRenderingSystem extends EntitySystem {
	@Mapper ComponentMapper<Position> positionMapper;
	
	private ShapeRenderer shapeRenderer;
	
	public LinePairRenderingSystem() {
		super(Aspect.getAspectForAll(Position.class, Enemy.class));
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(1, 1, 1, 1);
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void begin() {
		super.begin();
		shapeRenderer.begin(ShapeType.Line);
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		if (entities.size() < 2) {
			return;
		}
		
		for (int i = 0; i < entities.size(); i += 2) {
			Entity a = entities.get(i);
			Entity b = entities.get(i + 1);
			
			if (b == null) {
				continue;
			}
			
			Position firstPosition = positionMapper.get(a);
			Position lastPosition = positionMapper.get(b);
			
			shapeRenderer.line(firstPosition.x, firstPosition.y, lastPosition.x, lastPosition.y);
		}
	}
	
	@Override
	protected void end() {
		super.end();
		shapeRenderer.end();
	}
}