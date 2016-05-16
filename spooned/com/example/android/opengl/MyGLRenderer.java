package com.example.android.opengl;


public class MyGLRenderer implements android.opengl.GLSurfaceView.Renderer {
    private static final java.lang.String TAG = "MyGLRenderer";

    private com.example.android.opengl.Triangle mTriangle;

    private com.example.android.opengl.Square mSquare;

    private final float[] mMVPMatrix = new float[16];

    private final float[] mProjectionMatrix = new float[16];

    private final float[] mViewMatrix = new float[16];

    private final float[] mRotationMatrix = new float[16];

    private float mAngle;

    @java.lang.Override
    public void onSurfaceCreated(javax.microedition.khronos.opengles.GL10 unused, javax.microedition.khronos.egl.EGLConfig config) {
        android.opengl.GLES20.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        mTriangle = new com.example.android.opengl.Triangle();
        mSquare = new com.example.android.opengl.Square();
    }

    @java.lang.Override
    public void onDrawFrame(javax.microedition.khronos.opengles.GL10 unused) {
        float[] scratch = new float[16];
        android.opengl.GLES20.glClear(((android.opengl.GLES20.GL_COLOR_BUFFER_BIT) | (android.opengl.GLES20.GL_DEPTH_BUFFER_BIT)));
        android.opengl.Matrix.setLookAtM(mViewMatrix, 0, 0, 0, (-3), 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F);
        android.opengl.Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        mSquare.draw(mMVPMatrix);
        android.opengl.Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0F);
        android.opengl.Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        mTriangle.draw(scratch);
    }

    @java.lang.Override
    public void onSurfaceChanged(javax.microedition.khronos.opengles.GL10 unused, int width, int height) {
        android.opengl.GLES20.glViewport(0, 0, width, height);
        float ratio = ((float)(width)) / height;
        android.opengl.Matrix.frustumM(mProjectionMatrix, 0, (-ratio), ratio, (-1), 1, 3, 7);
    }

    public static int loadShader(int type, java.lang.String shaderCode) {
        int shader = android.opengl.GLES20.glCreateShader(type);
        android.opengl.GLES20.glShaderSource(shader, shaderCode);
        android.opengl.GLES20.glCompileShader(shader);
        return shader;
    }

    public static void checkGlError(java.lang.String glOperation) {
        int error;
        while ((error = android.opengl.GLES20.glGetError()) != (android.opengl.GLES20.GL_NO_ERROR)) {
            android.util.Log.e(com.example.android.opengl.MyGLRenderer.TAG, ((glOperation + ": glError ") + error));
            throw new java.lang.RuntimeException(((glOperation + ": glError ") + error));
        }
    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }
}

