package C44.utils.primitives;

import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;

public class Curve
{
	public static ArrayList<Vector2> generateCurve(Vector2 center, float angle, float radius, float minDistance)
	{
		ArrayList<Vector2> curve = new ArrayList<Vector2>();	
		
		for( float angleIndex = 0.0f; angleIndex <= angle; angleIndex+=minDistance)
		{
			float rad  = (float) (Math.PI * angleIndex / angle);
			float x = (float) (center.x + radius * Math.cos(rad));
			float y = (float) (center.y + radius * Math.sin(rad));
			
			curve.add(new Vector2(x, y));
		}				
		
		return curve;		
	}
		

}
