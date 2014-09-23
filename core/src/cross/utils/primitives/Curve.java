package cross.utils.primitives;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.FlatteningPathIterator;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Curve
{
	// http://java-sl.com/tip_flatteningpathiterator_moving_shape.html
	public static ArrayList<Vector2> generateCurve2()
	{
		ArrayList<Vector2> curve = new ArrayList<Vector2>();
		Shape s = new Ellipse2D.Float(100, 55, 100, 100);
		FlatteningPathIterator iter = new FlatteningPathIterator(s.getPathIterator(new AffineTransform()), 1);
		float[] coords=new float[6];
		while (!iter.isDone())
		{
			iter.currentSegment(coords);
			curve.add(new Vector2((int)coords[0], (int)coords[1]));
		}
		
		return curve;
	}
	
	public static ArrayList<Vector2> generateCurve(Vector2 from, Vector2 to, float radius, float minDistance, boolean shortest, boolean side)
	{
		ArrayList<Vector2> curve = new ArrayList<Vector2>();	
		
		for(PointF point : generateCurve(new PointF(from.x, from.y), new PointF(to.x, to.y), radius, minDistance, shortest, side ) )
		{
			curve.add(new Vector2(point.x, point.y));
		}
		
		return curve;		
	}
	
    static class PointF
    {

        public float x, y;

        public PointF(float x, float y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString()
        {
            return "(" + x + "," + y + ")";
        }
    }
	
    private static List<PointF> generateCurve(PointF pFrom, PointF pTo, float pRadius, float pMinDistance, boolean shortest, boolean side)
    {

        List<PointF> pOutPut = new ArrayList<PointF>();

        // Calculate the middle of the two given points.
        PointF mPoint = new PointF(pFrom.x + pTo.x, pFrom.y + pTo.y);
        mPoint.x /= 2.0f;
        mPoint.y /= 2.0f;
        System.out.println("Middle Between From and To = " + mPoint);


        // Calculate the distance between the two points
        float xDiff = pTo.x - pFrom.x;
        float yDiff = pTo.y - pFrom.y;
        float distance = (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        System.out.println("Distance between From and To = " + distance);

        if (pRadius * 2.0f < distance) {
            throw new IllegalArgumentException("The radius is too small! The given points wont fall on the circle.");
        }

        // Calculate the middle of the expected curve.
        float factor = (float) Math.sqrt((pRadius * pRadius) / ((pTo.x - pFrom.x) * (pTo.x - pFrom.x) + (pTo.y - pFrom.y) * (pTo.y - pFrom.y)) - 0.25f);
        PointF circleMiddlePoint = new PointF(0, 0);
        if (side) {
            circleMiddlePoint.x = 0.5f * (pFrom.x + pTo.x) + factor * (pTo.y - pFrom.y);
            circleMiddlePoint.y = 0.5f * (pFrom.y + pTo.y) + factor * (pFrom.x - pTo.x);
        } else {
            circleMiddlePoint.x = 0.5f * (pFrom.x + pTo.x) - factor * (pTo.y - pFrom.y);
            circleMiddlePoint.y = 0.5f * (pFrom.y + pTo.y) - factor * (pFrom.x - pTo.x);
        }
        System.out.println("Middle = " + circleMiddlePoint);

        // Calculate the two reference angles
        float angle1 = (float) Math.atan2(pFrom.y - circleMiddlePoint.y, pFrom.x - circleMiddlePoint.x);
        float angle2 = (float) Math.atan2(pTo.y - circleMiddlePoint.y, pTo.x - circleMiddlePoint.x);

        // Calculate the step.
        float step = pMinDistance / pRadius;
        System.out.println("Step = " + step);

        // Swap them if needed
        if (angle1 > angle2) {
            float temp = angle1;
            angle1 = angle2;
            angle2 = temp;

        }
        boolean flipped = false;
        if (!shortest) {
            if (angle2 - angle1 < Math.PI) {
                float temp = angle1;
                angle1 = angle2;
                angle2 = temp;
                angle2 += Math.PI * 2.0f;
                flipped = true;
            }
        }
        for (float f = angle1; f < angle2; f += step) {
            PointF p = new PointF((float) Math.cos(f) * pRadius + circleMiddlePoint.x, (float) Math.sin(f) * pRadius + circleMiddlePoint.y);
            pOutPut.add(p);
        }
        if (flipped ^ side) {
            pOutPut.add(pFrom);
        } else {
            pOutPut.add(pTo);
        }

        return pOutPut;
    }
	
}
