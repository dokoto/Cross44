package C44.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import C44.Cross44;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Cross44";
		config.width = 1280;
		config.height = 780;
		//config.fullscreen = true;
		config.vSyncEnabled = true;
		
		new LwjglApplication(new Cross44(), config);
	}
}
