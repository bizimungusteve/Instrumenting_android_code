package com.example.android.opengl;


public class OpenGLES20Activity extends android.app.Activity {
    private android.opengl.GLSurfaceView mGLView;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new com.example.android.opengl.MyGLSurfaceView(com.example.android.opengl.OpenGLES20Activity.this);
        setContentView(mGLView);
    }

    @java.lang.Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @java.lang.Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }
}

