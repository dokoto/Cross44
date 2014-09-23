package cross.utils.inputs;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import cross.Cross44;

public class GestureHandler implements GestureListener
{
	private Cross44 cross;	
	private float initialScale = 1.0f;
	
	public GestureHandler(Cross44 cross)
	{
		this.cross = cross;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button)
	{		
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button)
	{
		return false;
	}

	@Override
	public boolean longPress(float x, float y)
	{
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button)
	{
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY)
	{
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button)
	{
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance)
	{		
		//Calculate pinch to zoom
        float ratio = initialDistance / distance;

        //Clamp range and set zoom
        cross.zoom = MathUtils.clamp(initialScale * ratio, 0.1f, 1.0f);

        return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
	{
		return false;
	}

}
