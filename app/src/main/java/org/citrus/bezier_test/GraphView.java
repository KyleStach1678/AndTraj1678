package org.citrus.bezier_test;
import android.view.*;
import android.graphics.*;
import android.app.*;
import java.util.ArrayList;
import java.security.acl.*;
import android.content.*;
public class GraphView extends View
{
	public PathFollower pFollow;
	double progress;
	public ArrayList<Equation> equations;
	Paint grid;
	Paint eq;
	Paint points;
	Paint text;
	RectF area;
	ScaleGestureDetector mScaleDetector;
	ScaleListener scaler;
	public GraphView(Activity act, RectF bounds, Context context)
	{
		super(act);
		equations = new ArrayList<Equation>();
		grid = new Paint();
		grid.setStrokeWidth(5);
		eq = new Paint();
		eq.setStrokeWidth(5);
		eq.setColor(Color.RED);
		points = new Paint();
		points.setStrokeWidth(20);
		points.setColor(Color.GREEN);
		text = new Paint();
		text.setTextSize(50);
		text.setStrokeWidth(5);
		text.setColor(Color.rgb(192, 0, 0));
		area = bounds;
		progress=0;
		mScaleDetector = new ScaleGestureDetector(context, scaler = new ScaleListener());
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// Let the ScaleGestureDetector inspect all events.
		mScaleDetector.onTouchEvent(ev);
		return true;
	}
	private void drawEquation(Equation e, Canvas c)
	{
		double scaleX = area.width() / (float)c.getWidth();
		double scaleY = area.height() / (float)c.getHeight();
		for(double t = e.minv() + .01; t < e.maxv() + .01; t+=.01)
		{
			Point last = e.getPoint(t-.01);
			last.x = last.x / scaleX + c.getWidth() / 2;
			last.y = (area.height()-last.y) / scaleY - c.getHeight() / 2;
			Point cur = e.getPoint(t);
			cur.x = cur.x / scaleX + c.getWidth() / 2;
			cur.y = (area.height()-cur.y) / scaleY - c.getHeight() / 2;
			c.drawLine((float)last.x, (float)last.y, (float)cur.x, (float)cur.y, eq);
			last=cur;
		}
		
	}
	@Override
	public void onDraw(Canvas c)
	{
		RectF originalarea = new RectF(area.left, area.top, area.right, area.bottom);
		area.left = area.centerX() - (area.centerX() - area.left) / scaler.mScaleFactor;
		area.right = area.centerX() - (area.centerX() - area.right) / scaler.mScaleFactor;
		area.top = area.centerY() - (area.centerY() - area.top) / scaler.mScaleFactor;
		area.bottom = area.centerY() - (area.centerY() - area.bottom) / scaler.mScaleFactor;
		progress += .001;
		pFollow.update(.001);
		c.drawText(Double.toString(pFollow.speed), 10, 10, text);
		double scaleX = area.width() / (float)c.getWidth();
		double scaleY = area.height() / (float)c.getHeight();
		c.drawPoint((float) (pFollow.currentPos.x / scaleX + c.getWidth() / 2), (float) ((area.height()-pFollow.currentPos.y) / scaleY - c.getHeight() / 2), points);
		if (progress > 1) progress = 0;
		c.drawLine(c.getWidth()/2, 0, c.getWidth()/2, c.getHeight(), grid);
		c.drawLine(0, c.getHeight()/2, c.getWidth(), c.getHeight()/2, grid);
		for(Equation e : equations)
		{
			drawEquation(e, c);
			Point p = e.getPoint(progress);
			Point p2 = e.getPoint(progress - .01);
			double dist = Math.sqrt((p.x - p2.x)*(p.x - p2.x) +
									(p.y - p2.y)*(p.y - p2.y));
			p.x = p.x / scaleX + c.getWidth() / 2;
			p.y = (area.height()-p.y) / scaleY - c.getHeight() / 2;
			c.drawPoint((float)p.x, (float)p.y, points);
			
			p2.x = p2.x / scaleX + c.getWidth() / 2;
			p2.y = (area.height()-p2.y) / scaleY - c.getHeight() / 2;
			
			c.drawText(String.format("%.2f", dist), (float)p.x, (float)p.y, text);
			Point diff = e.getDerivative().getPoint(progress);
			diff.x /= scaleX;
			diff.y /= scaleY;
			diff.y = -diff.y;
			double len = Math.sqrt(diff.y*diff.y + diff.x*diff.x);
			diff.x *= 500 / len;
			diff.y *= 500 / len;
			c.drawLine((float)p.x, (float)p.y, (float)(diff.x + p.x), (float)(diff.y + p.y), grid);
		}
		area = originalarea;
		invalidate();
	}
	
	
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		public float mScaleFactor = 1;
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			mScaleFactor *= detector.getScaleFactor();

			// Don't let the object get too small or too large.
			mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 50.0f));
			invalidate();
			return true;
		}
	}
}


