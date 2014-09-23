package C44.utils.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

import cross.Cross44;
import cross.Cross44.Consts;

public class gestureHandler implements InputProcessor
{
	private static final float MARGIN_WIDTH = 300.0f;
	private Cross44 cross;

	public gestureHandler(Cross44 cross)
	{
		this.cross = cross;
	}

	@Override
	public boolean keyDown(int keycode)
	{
		if (Keys.W == keycode)
		{
			Gdx.app.log("KEY-DOWN", "ACELERANDO");
			cross.currentMovement = Cross44.Moves.ACCELERAR;
		}
		
		if (Keys.S == keycode)
		{
			Gdx.app.log("KEY-DOWN", "TURBO");
			cross.currentMovement = Cross44.Moves.TURBO;
		}
		
		if (Keys.UP == keycode)
		{
			Gdx.app.log("KEY-DOWN", "ESTABILIZA_ARRIBA");
			cross.currentMovement = Cross44.Moves.ESTABILIZA_ARRIBA;
		}
		
		if (Keys.DOWN == keycode)
		{
			Gdx.app.log("KEY-DOWN", "ESTABILIZA_ABAJO");
			cross.currentMovement = Cross44.Moves.ESTABILIZA_ABAJO;
		}
		
		if (Keys.ESCAPE == keycode)
		{
			Gdx.app.exit();
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		if (Keys.W == keycode)
		{
			Gdx.app.log("KEY-DOWN", "STOP-ACELERANDO");
			cross.currentMovement = Cross44.Moves.STOP;
		}
		
		if (Keys.S == keycode)
		{
			Gdx.app.log("KEY-DOWN", "STOP-TURBO");
			cross.currentMovement = Cross44.Moves.STOP;
		}
		
		if (Keys.UP == keycode)
		{
			Gdx.app.log("KEY-DOWN", "STOP-ESTABILIZA_ARRIBA");
			cross.currentMovement = Cross44.Moves.STOP;
		}
		
		if (Keys.DOWN == keycode)
		{
			Gdx.app.log("KEY-DOWN", "STOP-ESTABILIZA_ABAJO");
			cross.currentMovement = Cross44.Moves.STOP;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button)
	{
		if (x >= 0.0f && x <= MARGIN_WIDTH)
		{
			if (y >= 0.0f && y <= Gdx.graphics.getHeight() / 2)
			{
				Gdx.app.log("TOUCH-DOWN", "ACELERANDO");
				cross.currentMovement = Cross44.Moves.ACCELERAR;
			} else
			{
				Gdx.app.log("TOUCH-DOWN", "TURBO");
				cross.currentMovement = Cross44.Moves.TURBO;
			}
		} else if (x >= Gdx.graphics.getWidth() - MARGIN_WIDTH && x <= Gdx.graphics.getWidth())
		{
			if (y >= 0.0f && y <= Gdx.graphics.getHeight() / 2)
			{
				Gdx.app.log("TOUCH-DOWN", "ESTABILIZA ARRIBA");
				cross.currentMovement = Cross44.Moves.ESTABILIZA_ARRIBA;
			} else
			{
				Gdx.app.log("TOUCH-DOWN", "ESTABILIZA ABAJO");
				cross.currentMovement = Cross44.Moves.ESTABILIZA_ABAJO;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button)
	{
		if (x >= 0.0f && x <= MARGIN_WIDTH)
		{
			if (y >= 0.0f && y <= Gdx.graphics.getHeight() / 2)
			{
				Gdx.app.log("TOUCH-UP", "PARO-ACELERANDO");
				cross.currentMovement = Cross44.Moves.STOP;
			} else
			{
				Gdx.app.log("TOUCH-DOWN", "CONTINUA-TURBO-HASTA-ACABAR");
				cross.currentMovement = Cross44.Moves.CONTINUA_MOV;
			}
		} else if (x >= Gdx.graphics.getWidth() - MARGIN_WIDTH && x <= Gdx.graphics.getWidth())
		{
			if (y >= 0.0f && y <= Gdx.graphics.getHeight() / 2)
			{
				Gdx.app.log("TOUCH-UP", "PARO-ESTABILIZA ARRIBA");
				cross.currentMovement = Cross44.Moves.STOP;
			} else
			{
				Gdx.app.log("TOUCH-UP", "PARO-ESTABILIZA ABAJO");
				cross.currentMovement = Cross44.Moves.STOP;
			}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
