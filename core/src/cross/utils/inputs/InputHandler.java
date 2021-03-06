package cross.utils.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import cross.Cross44;

public class InputHandler implements InputProcessor
{
	private static final float MARGIN_WIDTH = 300.0f;
	private Cross44 cross;

	public InputHandler(Cross44 cross)
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
			cross.currentMovement = Cross44.Moves.STOP;
		}

		if (Keys.S == keycode)
		{
			cross.currentMovement = Cross44.Moves.STOP;
		}

		if (Keys.UP == keycode)
		{
			cross.currentMovement = Cross44.Moves.STOP_ESTABILIZA;
		}

		if (Keys.DOWN == keycode)
		{
			cross.currentMovement = Cross44.Moves.STOP_ESTABILIZA;
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
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
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		return false;
	}

}
