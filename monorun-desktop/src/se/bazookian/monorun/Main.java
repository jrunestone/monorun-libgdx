package se.bazookian.monorun;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class Main {
	public static void main(String[] args) {
		packAssets(Resources.PACK_ASSET_FOLDERS);
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		cfg.title = "Monorun!";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 600;
		
		new LwjglApplication(new Monorun(1.0f), cfg);
	}
	
	private static void packAssets(String ... assetNames) {
		if (assetNames.length == 0) {
			return;
		}
		
		for (String asset : assetNames) {
			TexturePacker2.processIfModified("../monorun-android/assets/" + asset + "/unpacked", "../monorun-android/assets/" + asset, asset);
		}
	}
}