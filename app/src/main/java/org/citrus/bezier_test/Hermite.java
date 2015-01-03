package org.citrus.bezier_test;
import java.util.*;

public class Hermite extends Equation
{
	double ax, bx, cx, dx;
	double ay, by, cy, dy;
	double ux, vx, uy, vy;
	public Hermite(Point start, Point end, Point startdir, Point enddir)
	{
		// h1(s) = 2s^3 - 3s^2 + 1
		// h2(s) = -2s^3 + 3s^2
		// h3(s) = s^3 - 2s^2 + s
		// h4(s) = s^3 - s^2
		
		ax = 2 * start.x
			- 2 * end.x
			+ startdir.x
			+ enddir.x;
		bx = 3 * end.x
			- 3 * start.x
			- 2 * startdir.x
			- enddir.x;
		cx = startdir.x;
		dx = start.x;
		
		ay = 2 * start.y
			- 2 * end.y
			+ startdir.y
			+ enddir.y;
		by = 3 * end.y
			- 3 * start.y
			- 2 * startdir.y
			- enddir.y;
		cy = startdir.y;
		dy = start.y;
		
		minIn = 0;
		maxIn = 1;
		ux = 0;
		vx = 0;
	}
	public Hermite(Point startp, Point endp, Point startv, Point endv, Point starta, Point enda)
	{
		// H5(t) = 1 − 10t^3 + 15t^4 − 6t^5
		// H5(t) = t − 6t^3 + 8t^4 − 3t^5
		// H5(t) = .5t^2 - 1.5t^3 + 1.5t^4 - .5t^5
		// H5(t) = .5x^3 - x^4 +﻿ .5x^5
		// H5(t) = -4t^3 + 7t^4 - 3t^5
		// H5(t) = 10t^3 - 15t^4 + 6t^5
		// -6 -3 -.5 .5 -3 -6
		// 
		
		vx = -6 * startp.x - 3 * startv.x - .5 * starta.x + .5 * enda.x - 3 * endv.x + 6 * endp.x;
		ux = 15 * startp.x + 8 * startv.x + 1.5 * starta.x - 1 * enda.x + 7 * endv.x - 15 * endp.x;
		ax = -10 * startp.x - 6 * startv.x - 1.5 * starta.x + .5 * enda.x - 4 * endv.x + 10 * endp.x;
		bx = .5 * starta.x;
		cx = startv.x;
		dx = startp.x;
		
		vy = -6 * startp.y - 3 * startv.y - .5 * starta.y + .5 * enda.y - 3 * endv.y + 6 * endp.y;
		uy = 15 * startp.y + 8 * startv.y + 1.5 * starta.y - 1 * enda.y + 7 * endv.y - 15 * endp.y;
		ay = -10 * startp.y - 6 * startv.y - 1.5 * starta.y + .5 * enda.y - 4 * endv.y + 10 * endp.y;
		by = .5 * starta.y;
		cy = startv.y;
		dy = startp.y;
		
		minIn = 0;
		maxIn = 1;
	}
	@Override
	public Point getPoint(double t)
	{
		Point retval = new Point(0, 0);
		retval.x =  vx * t*t*t*t*t + 
					ux * t*t*t*t + 
					ax * t*t*t +
					bx * t*t +
					cx * t +
					dx;
		retval.y = 	vy * t*t*t*t*t + 
					uy * t*t*t*t + 
					ay * t*t*t +
					by * t*t +
					cy * t +
					dy;
		return retval;
	}

	@Override
	public Equation getDerivative()
	{
		Vector<Double> derivCoeffsX = new Vector<Double>();
		Vector<Double> derivCoeffsY = new Vector<Double>();
		derivCoeffsX.add(vx*5); derivCoeffsY.add(vy*5);
		derivCoeffsX.add(ux*4); derivCoeffsY.add(uy*4);
		derivCoeffsX.add(ax*3); derivCoeffsY.add(ay*3);
		derivCoeffsX.add(bx*2); derivCoeffsY.add(by*2);
		derivCoeffsX.add(cx*1); derivCoeffsY.add(cy*1);
		return new Composite2D(new PolynomialEquation(derivCoeffsX, 0, 1), new PolynomialEquation(derivCoeffsY, 0, 1));
	}
}
