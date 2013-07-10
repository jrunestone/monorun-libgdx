package se.bazookian.monorun.systems;

import se.bazookian.monorun.Monorun;
import se.bazookian.monorun.components.IdleTimer;
import se.bazookian.monorun.components.Player;
import se.bazookian.monorun.components.Position;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {
	@Mapper ComponentMapper<Position> positionMapper;
	@Mapper ComponentMapper<IdleTimer> idleTimerMapper;
	
	private boolean isDragging;
	
	private float dragX;
	private float dragY;
	
	public PlayerInputSystem() {
		super(Aspect.getAspectForAll(Player.class, Position.class, IdleTimer.class));
	}

	@Override
	protected void initialize() {
		super.initialize();
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	protected void process(Entity entity) {
		Position position = positionMapper.get(entity);
		IdleTimer idleTimer = idleTimerMapper.get(entity);
		
		boolean isMoving = position.x != dragX && position.y != dragY;
		
		if (isMoving) {
			idleTimer.reset();
		}
		
		idleTimer.update();
		
		if (isDragging) {
			position.x = MathUtils.clamp(dragX, 0, Monorun.WIDTH);
			position.y = MathUtils.clamp(dragY, 0, Monorun.HEIGHT);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isDragging = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		isDragging = true;
		
		dragX = screenX;
		dragY = Monorun.HEIGHT - screenY;
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}