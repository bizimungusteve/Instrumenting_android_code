package com.example.android.opengl;


public class MyGLSurfaceView extends android.opengl.GLSurfaceView {
    private final com.example.android.opengl.MyGLRenderer mRenderer;

    public MyGLSurfaceView(android.content.Context context) {
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new com.example.android.opengl.MyGLRenderer();
        setRenderer(mRenderer);
        setRenderMode(android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0F / 320;

    private float mPreviousX;

    private float mPreviousY;

    @java.lang.Override
    public boolean onTouchEvent(android.view.MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case android.view.MotionEvent.ACTION_MOVE :
                float dx = x - (mPreviousX);
                float dy = y - (mPreviousY);
                if (y > ((getHeight()) / 2)) {
                    dx = dx * (-1);
                } 
                if (x < ((getWidth()) / 2)) {
                    dy = dy * (-1);
                } 
                mRenderer.setAngle(((mRenderer.getAngle()) + ((dx + dy) * (TOUCH_SCALE_FACTOR))));
                requestRender();
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}

