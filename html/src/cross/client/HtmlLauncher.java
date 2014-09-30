package cross.client;

import cross.Cross44;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class HtmlLauncher extends GwtApplication
{

	@Override
	public GwtApplicationConfiguration getConfig()
	{
		return new GwtApplicationConfiguration(cross.Cross44.Consts.SCREEN_WIDTH_PX, cross.Cross44.Consts.SCREEN_HEIGHT_PX);
	}

	@Override
	public ApplicationListener getApplicationListener()
	{
		return new Cross44();
	}
}