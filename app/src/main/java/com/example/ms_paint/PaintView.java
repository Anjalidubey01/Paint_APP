package com.example.ms_paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import java.util.ArrayList;

public class PaintView extends View {
   public float x,y;
    public int fill=1;
    public static int BRUSH_SIZE = 20;
    public static final int DEFAULT_COLOR = Color.RED;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;
    public Paint mPaint;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    public int currentColor;
    private int backgroundColor = DEFAULT_BG_COLOR;
    public int strokeWidth;
    private boolean emboss;
    private boolean blur;
    public int width,height;
    private MaskFilter mEmboss;
    private MaskFilter mBlur;
    public Bitmap mBitmap;
    public Canvas mCanvas,zoomi;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private ScaleGestureDetector scaleGestureDetector;
    private float mscaleFactor = 2.1f;
    float scale_canvas=1.5f;
    public PaintView(Context context) {
        this(context, null);
    }
   Context context;
    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);

        mEmboss = new EmbossMaskFilter(new float[] {1, 1, 1}, 0.4f, 6, 3.5f);
        mBlur = new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL);
    }

    public void init(DisplayMetrics metrics) {
        height = metrics.heightPixels;
        width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
        scaleGestureDetector = new ScaleGestureDetector(context,new Scalelisener());
    }

    public void normal() {
        emboss = false;
        blur = false;
    }
    public void clear() {
        backgroundColor = DEFAULT_BG_COLOR;
        paths.clear();
        normal();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mCanvas.drawColor(backgroundColor);


          //  mPaint.setStyle(Paint.Style.STROKE);
        for (FingerPath fp : paths) {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mPaint.setMaskFilter(null);
            //if (fp.emboss)
            //    mPaint.setMaskFilter(mEmboss);
            //else if (fp.blur)
             //   mPaint.setMaskFilter(mBlur);
//canvas.scale(mscaleFactor,mscaleFactor);
   // canvas.scale(scale_canvas,scale_canvas);//invalidate();
mCanvas.drawPath(fp.path, mPaint);
        }
float xci,yci;
        xci=x;
        yci=y;
mCanvas.drawCircle(xci,yci,30,mPaint);
 canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y) {
        mPath = new Path();
        FingerPath fp = new FingerPath(currentColor, emboss, blur, strokeWidth, mPath,fill);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    public void valida(){
        invalidate();
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
//if(zoom==1){
   // scaleGestureDetector.onTouchEvent(event);zoom=0;//}
//else{
switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                touchUp();
                invalidate();
                break;

            //    scaleGestureDetector.onTouchEvent(event);
        }//}
       // scaleGestureDetector.onTouchEvent(event);
        return true;
    }
    private class Scalelisener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale_canvas +=detector.getScaleFactor()/5;
            mX = detector.getFocusX();
            mY= detector.getFocusY();
            scale_canvas = Math.max(1.5f,Math.min(mscaleFactor,10.0f));
            invalidate();
            return true;
        }

    }
}