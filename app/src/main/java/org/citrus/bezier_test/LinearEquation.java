package org.citrus.bezier_test;

public class LinearEquation extends Equation
{
	private double m, b;
	public LinearEquation(double slope, double yinter, double nmin, double nmax)
	{
		this.m = slope;
		this.b = yinter;
		this.minIn = nmin;
		this.maxIn = nmax;
	}
	@Override
	public Point getPoint(double t) {
		return new Point(t, m * t + b);
	}

	@Override
	public Equation getDerivative()
	{
		return new LinearEquation(0, m, minv(), maxv());
	}
}
