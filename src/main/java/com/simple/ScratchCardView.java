package com.simple;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * Created by admin on 2017/5/3.
 */
public class ScratchCardView extends View {
    private static final String TAG ="ScratchCardView" ;
    private int width;
    private int height;
    private String text = "苍老师晚上约你";
    private Paint textPaint;
    private Paint paint;
    private Path path;
    private float downX,downY;
    private float tempX,tempY;
    private Canvas myCanvas;
    private Bitmap myBitmap;
    public ScratchCardView(Context context) {
        this(context,null);
    }

    public ScratchCardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScratchCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        paint.setTextSize(24);
        paint.setDither(true);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(20);
        textPaint.setTextSize(24);
        textPaint.setDither(true);
        textPaint.setColor(Color.BLACK);
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        myBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        myCanvas = new Canvas(myBitmap);
        myCanvas.drawColor(Color.GREEN);
    }
    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(bitmap,0,0,null);//底层图
        drawText(canvas);
        drawPath();//这个path是绘制在myvanvas画布上的
        canvas.drawBitmap(myBitmap,0,0,null);
        super.onDraw(canvas);
    }

    private void drawText(Canvas canvas) {
        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);
        int textWidth = rect.width();
        int textHeight = rect.height();
        float x = width/2-textWidth/2;
        float y = height/2+textHeight/2;
        canvas.drawText(text,x,y,textPaint);
    }
    private void drawPath() {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        myCanvas.drawPath(path,paint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                path.moveTo(downX,downY);
                invalidate();
                tempX = downX;
                tempY = downY;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                path.quadTo(tempX,tempY,moveX,moveY);
                invalidate();
                tempX = moveX;
                tempY = moveY;
                break;
        }
        return true;
    }
}
