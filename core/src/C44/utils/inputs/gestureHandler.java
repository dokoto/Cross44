package C44.utils.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class gestureHandler implements GestureListener {
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (x >= 0.0f && x <= 50.0f)
		{
			if(y >= 0.0f && y <= 300.0f) // TURBO
			{
				Gdx.app.log("GESTURES", "TURBO");
			}
			else // ACELERAR
			{
				Gdx.app.log("GESTURES", "ACELERANDO");
			}
		}
		if (x >= 1230.0f && x <= 1280.0f)
		{
			if(y >= 0.0f && y <= 300.0f) // ESTABILIZA MORRO HACIA ABAJO
			{
				Gdx.app.log("GESTURES", "ESTABILIZA ABAJO");
			}
			else // // ESTABILIZA MORRO HACIA ARRIBA
			{
				Gdx.app.log("GESTURES", "ESTABILIZA ARRIBA");
			}
		}
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {

		return false;
	}

	@Override
	public boolean longPress(float x, float y) {

		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {

		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {

		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {

		return false;
	}

	@Override
	public boolean zoom(float originalDistance, float currentDistance) {

		return false;
	}

	@Override
	public boolean pinch(Vector2 initialFirstPointer,
			Vector2 initialSecondPointer, Vector2 firstPointer,
			Vector2 secondPointer) {

		return false;
	}

}
