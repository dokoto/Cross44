package cross.desktop;

import cross.Cross44;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Cross44";
		config.width = cross.Cross44.Consts.SCREEN_WIDTH;
		config.height = cross.Cross44.Consts.SCREEN_HEIGHT;
		config.vSyncEnabled = true;

		new LwjglApplication(new Cross44(), config);
	}
}
