package org.citrus.bezier_test;

public class Composite2D extends Equation
{
	Equation x, y;
	public Composite2D(Equation x, Equation y)
	{
		this.x = x;
		this.y = y;
		minIn = Math.max(x.minv(), y.minv());
		maxIn = Math.min(x.maxv(), y.maxv());
	}
	@Override
	public Point getPoint(double t)
	{
		return new Point(x.getPoint(t).y, y.getPoint(t).y);
	}

	@Override
	public Equation getDerivative()
	{
		return new Composite2D(x.getDerivative(), y.getDerivative());
	}
}
