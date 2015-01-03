package org.citrus.bezier_test;
import java.util.*;
import android.util.*;

public class PolynomialEquation extends Equation
{
	Vector<Double> coeffs;
	public PolynomialEquation(Vector<Double> coefficients, double nmin, double nmax)
	{
		
		coeffs = coefficients;
		this.minIn = nmin;
		this.maxIn = nmax;
	}
	@Override
	public Point getPoint(double t)
	{
		double yval = 0;
		for (int i = 0; i < coeffs.size(); i++)
		{
			yval += Math.pow(t, (coeffs.size() - i)) * coeffs.get(i);
		}
		return new Point(t, yval);
	}

	@Override
	public Equation getDerivative()
	{
		Vector<Double> nCoefficients = new Vector<Double>();
		for (int i = 0; i < coeffs.size(); i++)
		{
			nCoefficients.add(coeffs.get(i) * (coeffs.size() - i));
		}
		return new PolynomialEquation(nCoefficients, minv(), maxv());
	}
}
