package se.bazookian.monorun.systems;

import java.util.HashMap;

import se.bazookian.monorun.Resources;
import se.bazookian.monorun.components.Position;
import se.bazookian.monorun.components.Sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class RenderingSystem extends EntityProcessingSystem {
	@Mapper ComponentMapper<Sprite> spriteMapper;
	@Mapper ComponentMapper<Position> positionMapper;
	
	private AssetManager assetManager;
	private HashMap<String, AtlasRegion> regions;
	private SpriteBatch spriteBatch;
	
	public RenderingSystem(AssetManager assetManager) {
		super(Aspect.getAspectForAll(Sprite.class, Position.class));
		
		if (assetManager == null) {
			throw new IllegalArgumentException("Asset manager must not be null");
		}
		
		this.assetManager = assetManager;
		
		regions = new HashMap<String, AtlasRegion>();
		spriteBatch = new SpriteBatch();
	}

	@Override
	protected void initialize() {
		super.initialize();
		
		TextureAtlas atlas = assetManager.get(Resources.SPRITE_ATLAS, TextureAtlas.class);
		
		for (AtlasRegion region : atlas.getRegions()) {
			regions.put(region.name, region);
		}
	}
	
	@Override
	protected void begin() {
		super.begin();
		spriteBatch.begin();
	}
	
	@Override
	protected void process(Entity entity) {
		Sprite sprite = spriteMapper.get(entity);
		Position position = positionMapper.get(entity);
		AtlasRegion region = regions.get(sprite.name);
		
		float centerX = position.x - region.getRegionWidth() / 2;
		float centerY = position.y - region.getRegionHeight() / 2;
		
		spriteBatch.draw(region, centerX, centerY, 0, 0, region.getRegionWidth(), region.getRegionHeight(), sprite.scale, sprite.scale, 0);
	}
	
	@Override
	protected void end() {
		super.end();
		spriteBatch.end();
	}
}