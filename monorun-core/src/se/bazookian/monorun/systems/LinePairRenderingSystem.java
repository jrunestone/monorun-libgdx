package se.bazookian.monorun.systems;

import se.bazookian.monorun.components.Enemy;
import se.bazookian.monorun.components.Position;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LinePairRenderingSystem extends EntitySystem {
	@Mapper ComponentMapper<Position> positionMapper;
	
	private ShapeRenderer shapeRenderer;
	
	public LinePairRenderingSystem() {
		super(Aspect.getAspectForAll(Enemy.class, Position.class));
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(0.11f, 0.17f, 0.19f, 0.89f);
	}
	
	@Override
	protected boolean checkProcessing() {
		return true;
	}

	@Override
	protected void begin() {
		super.begin();
		
		Gdx.gl.glLineWidth(10);
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
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
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}
}