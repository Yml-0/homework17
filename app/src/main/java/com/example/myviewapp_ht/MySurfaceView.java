package com.example.myviewapp_ht;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.method.Touch;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private DrawThread drawThread;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        drawThread = new DrawThread();
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        surfaceHolder = holder;
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        drawThread.setTowardPoint(event.getX(),event.getY());
        return false;

    }
    protected class DrawThread extends Thread{
        public float towardPointX = 1440/2f;
        public float towardPointY = 2112/2f;
        private boolean running = true;


        Paint paint = new Paint();

        {
            paint.setColor(Color.RED);
        }



        @Override
        public void run() {
            float smileY = 2112/2f;
            float smileX = 1440/2f;
            while (running){
                Canvas canvas = surfaceHolder.lockCanvas();
                    if (canvas!= null){
                        canvas.drawColor(Color.BLUE);
                        canvas.drawCircle(smileX, smileY, 50, paint);
//                        canvas.drawText(String.valueOf(canvas.getHeight()) + "   "+String.valueOf(canvas.getWidth()), 100, 100, paint);
                        try{
                            if (smileX  < towardPointX) smileX += 20;
                            if (smileX  > towardPointX) smileX -= 20;
                            if (smileY  < towardPointY) smileY += 20;
                            if (smileY > towardPointY) smileY -= 20;

                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            surfaceHolder.unlockCanvasAndPost(canvas);}
                }
            }
        }

        public void setTowardPoint(float x, float y) {
            towardPointX = x;
            towardPointY = y;
        }
    }

}
