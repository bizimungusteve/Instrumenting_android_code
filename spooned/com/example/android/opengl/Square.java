package com.example.android.opengl;


public class Square {
    private final java.lang.String vertexShaderCode = "uniform mat4 uMVPMatrix;" + ("attribute vec4 vPosition;" + ("void main() {" + ("  gl_Position = uMVPMatrix * vPosition;" + "}")));

    private final java.lang.String fragmentShaderCode = "precision mediump float;" + ("uniform vec4 vColor;" + ("void main() {" + ("  gl_FragColor = vColor;" + "}")));

    private final java.nio.FloatBuffer vertexBuffer;

    private final java.nio.ShortBuffer drawListBuffer;

    private final int mProgram;

    private int mPositionHandle;

    private int mColorHandle;

    private int mMVPMatrixHandle;

    static final int COORDS_PER_VERTEX = 3;

    static float[] squareCoords = new float[]{ -0.5F , 0.5F , 0.0F , -0.5F , -0.5F , 0.0F , 0.5F , -0.5F , 0.0F , 0.5F , 0.5F , 0.0F };

    private final short[] drawOrder = new short[]{ 0 , 1 , 2 , 0 , 2 , 3 };

    private final int vertexStride = (com.example.android.opengl.Square.COORDS_PER_VERTEX) * 4;

    float[] color = new float[]{ 0.2F , 0.70980394F , 0.8980392F , 1.0F };

    public Square() {
        java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocateDirect(((com.example.android.opengl.Square.squareCoords.length) * 4));
        bb.order(java.nio.ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(com.example.android.opengl.Square.squareCoords);
        vertexBuffer.position(0);
        java.nio.ByteBuffer dlb = java.nio.ByteBuffer.allocateDirect(((drawOrder.length) * 2));
        dlb.order(java.nio.ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
        int vertexShader = com.example.android.opengl.MyGLRenderer.loadShader(android.opengl.GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = com.example.android.opengl.MyGLRenderer.loadShader(android.opengl.GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = android.opengl.GLES20.glCreateProgram();
        android.opengl.GLES20.glAttachShader(mProgram, vertexShader);
        android.opengl.GLES20.glAttachShader(mProgram, fragmentShader);
        android.opengl.GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mvpMatrix) {
        android.opengl.GLES20.glUseProgram(mProgram);
        mPositionHandle = android.opengl.GLES20.glGetAttribLocation(mProgram, "vPosition");
        android.opengl.GLES20.glEnableVertexAttribArray(mPositionHandle);
        android.opengl.GLES20.glVertexAttribPointer(mPositionHandle, com.example.android.opengl.Square.COORDS_PER_VERTEX, android.opengl.GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        mColorHandle = android.opengl.GLES20.glGetUniformLocation(mProgram, "vColor");
        android.opengl.GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        mMVPMatrixHandle = android.opengl.GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        com.example.android.opengl.MyGLRenderer.checkGlError("glGetUniformLocation");
        android.opengl.GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        com.example.android.opengl.MyGLRenderer.checkGlError("glUniformMatrix4fv");
        android.opengl.GLES20.glDrawElements(android.opengl.GLES20.GL_TRIANGLES, drawOrder.length, android.opengl.GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        android.opengl.GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
