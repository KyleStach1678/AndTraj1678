package org.citrus.bezier_test;
import java.util.Vector;
import android.graphics.*;


public class Bezier extends Equation
{
	public Vector<Point> controlPoints;
	public Bezier()
	{
		controlPoints = new Vector<Point>();
		minIn=0;
		maxIn=1;
	}
	public static Point lerp(Point p0, Point p1, double t)
	{
		Point ret = new Point(0, 0);
		ret.x = (1-t)*p0.x + t * p1.x;
		ret.y = (1-t)*p0.y + t * p1.y;
		return ret;
	}
	public static Point interpPoints(double t, Vector<Point> controlPoints)
	{
		if (controlPoints.size() < 1)
		{
			return new Point(0, 0);
		}
		else if (controlPoints.size() == 1)
		{
			return controlPoints.get(0);
		}
		else if (controlPoints.size() == 2)
		{
			return lerp(controlPoints.get(0), controlPoints.get(1), t);
		}
		else
		{
			Vector<Point> recPoints = new Vector<Point>();
			for (int i =0; i < controlPoints.size() - 1; i++)
			{
				recPoints.add(lerp(controlPoints.get(i), controlPoints.get(i+1), t));
			}
			return interpPoints(t, recPoints);
		}
	}
	public Point getPoint(double t)
	{
		Point retval = new Point(0, 0);
		if (controlPoints.size() > 0)
		{
			retval = interpPoints(t, controlPoints);
		}
		return retval;
	}

	@Override
	public Equation getDerivative()
	{
		// TODO: Implement this method
		return null;
	}
}
