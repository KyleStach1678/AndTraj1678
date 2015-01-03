package org.citrus.bezier_test;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;

public class MainActivity extends Activity 
{
	LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		mLinearLayout = new LinearLayout(this);

		// Instantiate a GraphView
		GraphView i = new GraphView(this, new RectF(40, 40, 40, 40), getApplicationContext());
		i.area.set(-30, -30, 30, 30);
		Bezier b = new Bezier();
		// Bezier control points
		b.controlPoints.add(new Point(0, 0));
		b.controlPoints.add(new Point(0, 2));
		b.controlPoints.add(new Point(2, 2));
		b.controlPoints.add(new Point(2, 4));
		// i.equations.add(b);
		
		// Create the cubic and quintic splines
		Hermite h = new Hermite(new Point(0, 0), new Point(5, 2), new Point(4, 0), new Point(-4, -5), new Point(-10, 30), new Point(0, 10));
		//i.equations.add(h);
		Hermite h2 = new Hermite(new Point(5, 2), new Point(5, 2), new Point(-4, -5), new Point(4, 0), new Point(0, 10), new Point(-10, 30));
		i.equations.add(h2);
		i.pFollow = new PathFollower(h2);
		i.equations.add(h2.getDerivative());
		
		// Add the GraphView to the layout and set the layout as the content view
		mLinearLayout.addView(i);
		setContentView(mLinearLayout);
    }
}
