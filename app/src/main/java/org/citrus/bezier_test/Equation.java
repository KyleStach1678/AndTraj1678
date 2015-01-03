package org.citrus.bezier_test;
import android.graphics.*;
public abstract class Equation
{
	protected double minIn, maxIn;
	public abstract Point getPoint(double t);
	public abstract Equation getDerivative();
	public double minv()
	{
		return minIn;
	}
	public double maxv()
	{
		return maxIn;
	}
}
