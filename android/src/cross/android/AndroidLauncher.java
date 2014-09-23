package cross.android;

import android.os.Bundle;
import android.view.View;

import cross.Cross44;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		View decorView = getWindow().getDecorView(); 
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		decorView.setSystemUiVisibility(uiOptions);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		config.useAccelerometer = false;
	    config.useCompass = false;
		
	    initialize(new Cross44(), config);
	}
}
