package org.citrus.bezier_test;
import android.util.*;

public class PathFollower
{
	public double direction;
	public double speed;
	public Point currentPos;
	private double progress;
	Equation toFollow;
	public double findAngle(Point p)
	{
		return Math.atan2(p.y, p.x);
	}
	public double findSpeed(Point p)
	{
		return Math.sqrt(p.x*p.x + p.y*p.y);
	}
	public PathFollower(Equation e)
	{
		progress = 0;
		toFollow = e;
		currentPos = e.getPoint(progress);
		direction = findAngle(toFollow.getDerivative().getPoint(progress));
		speed = findSpeed(toFollow.getDerivative().getPoint(progress));
	}
	public void updateBearings()
	{
		double newDir = findAngle(toFollow.getDerivative().getPoint(progress));
		double deltaDir = newDir - direction;
		direction += deltaDir;
		speed = findSpeed(toFollow.getDerivative().getPoint(progress));
	}
	public void move()
	{
		System.out.println(Double.toString(direction));
		currentPos.x += Math.cos(direction) * .01 * speed;
		currentPos.y += Math.sin(direction) * .01 * speed;
	}
	public void update(double dp)
	{
		progress += dp;
		updateBearings();
		move();
	}
}
