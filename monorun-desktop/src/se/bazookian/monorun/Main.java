package se.bazookian.monorun;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		cfg.title = "Monorun";
		cfg.useGL20 = true;
		cfg.width = Monorun.WIDTH;
		cfg.height = Monorun.HEIGHT;
		
		new LwjglApplication(new Monorun(), cfg);
	}
}
