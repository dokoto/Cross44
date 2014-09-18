package C44.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import C44.Cross44;

public class HtmlLauncher extends GwtApplication
{

	@Override
	public GwtApplicationConfiguration getConfig()
	{
		return new GwtApplicationConfiguration(C44.Cross44.Consts.SCREEN_WIDTH, C44.Cross44.Consts.SCREEN_HEIGHT);
	}

	@Override
	public ApplicationListener getApplicationListener()
	{
		return new Cross44();
	}
}