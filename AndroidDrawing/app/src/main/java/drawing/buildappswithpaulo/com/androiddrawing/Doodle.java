package drawing.buildappswithpaulo.com.androiddrawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;


public class Doodle extends View {
    private static final float TOUCH_TOLERANCE = 10 ;
    private Bitmap bitmap;
    private Canvas bitmapCavas;
    private Paint paintScreen;
    private Paint paintLine;
    private Canvas canvas;
    private HashMap<Integer, Path> pathHashMap;
    private HashMap<Integer, Point> previousPointMap;

    public Doodle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        paintScreen = new Paint();
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(15);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
        pathHashMap = new HashMap<>();
        previousPointMap = new HashMap<>();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        bitmapCavas = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paintScreen);
       // canvas.drawCircle(23, 23, 89, paintScreen);

        for (Integer key : pathHashMap.keySet()) {
           // System.out.println(pathHashMap.get(key));
             canvas.drawPath(pathHashMap.get(key), paintLine);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // get the event type and the ID of the pointer that caused the event
        int action = event.getActionMasked(); // event type
        int actionIndex = event.getActionIndex(); // pointer (i.e., finger)

        // determine which type of action the given MotionEvent
        // represents, then call the corresponding handling method
        if (action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_POINTER_DOWN)
        {
            touchStarted(event.getX(actionIndex), event.getY(actionIndex),
                    event.getPointerId(actionIndex));
        } // end if
        else if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_POINTER_UP)
        {
            touchEnd(event.getPointerId(actionIndex));
        } // end else if
        else
        {
            touchMoved(event);
        } // end else

        invalidate(); // redraw
        return true; // consume the touch event
    }



    private void touchEnd(int pointerId) {
        Path path = pathHashMap.get(pointerId);
        bitmapCavas.drawPath(path, paintLine);
        path.reset();
    }

    private void touchStarted(float x, float y, int pointerId) {
        Path path;
        Point point;

        if (pathHashMap.containsKey(pointerId)) {
            path = pathHashMap.get(pointerId);
            path.reset();
            point = previousPointMap.get(pointerId);
        }else {
            path = new Path();
            pathHashMap.put(pointerId, path);
            point = new Point();
            previousPointMap.put(pointerId, point);
        }

        path.moveTo(x, y);
        point.x = (int)x;
        point.y = (int)y;
    }

    private void touchMoved(MotionEvent event)
    {
        // for each of the pointers in the given MotionEvent
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            // get the pointer ID and pointer index
            int pointerID = event.getPointerId(i);
            int pointerIndex = event.findPointerIndex(pointerID);

            // if there is a path associated with the pointer
            if (pathHashMap.containsKey(pointerID))
            {
                // get the new coordinates for the pointer
                float newX = event.getX(pointerIndex);
                float newY = event.getY(pointerIndex);

                // get the Path and previous Point associated with 
                // this pointer
                Path path = pathHashMap.get(pointerID);
                Point point = previousPointMap.get(pointerID);

                // calculate how far the user moved from the last update
                float deltaX = Math.abs(newX - point.x);
                float deltaY = Math.abs(newY - point.y);

                // if the distance is significant enough to matter
                if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE)
                {
                    // move the path to the new location
                    path.quadTo(point.x, point.y, (newX + point.x) / 2,
                            (newY + point.y) / 2);

                    // store the new coordinates
                    point.x = (int) newX;
                    point.y = (int) newY;
                } // end if
            } // end if
        } // end for
    } // end method touchMoved

}
