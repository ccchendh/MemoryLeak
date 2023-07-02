package com.example.memoryleak;


import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

//    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
//        // Set the background frame color
//        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
//    }
//
//    public void onDrawFrame(GL10 unused) {
//        // Redraw background color
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//    }
    private int textureId;
    private int program; // 着色器程序对象
    private Bitmap bitmap; // 加载的纹理图像

    private static final String vertexShaderCode =
            "attribute vec4 aPosition;\n" +
                    "attribute vec2 aTextureCoord;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "void main() {\n" +
                    "    gl_Position = aPosition;\n" +
                    "    vTextureCoord = aTextureCoord;\n" +
                    "}";

    private static final String fragmentShaderCode =
            "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform sampler2D uTexture;\n" +
                    "void main() {\n" +
                    "    gl_FragColor = texture2D(uTexture, vTextureCoord);\n" +
                    "}";

    private int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    private int createAndLinkProgram(int vertexShader, int fragmentShader) {
        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
        return program;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 创建纹理对象
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        textureId = textures[0];

        // 绑定纹理对象
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        // 设置纹理参数
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        // 加载纹理图像
        int targetSize = 10240 * 10240; // 1MB
        byte[] byteArray = new byte[targetSize];

        // 创建一个1MB大小的Bitmap对象
        bitmap = Bitmap.createBitmap(5120, 5120, Bitmap.Config.ARGB_8888);
        //从将字数组内容复制到Bitmap中
        bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(byteArray));

        // 设置纹理图像数据
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        // 释放位图资源
        bitmap.recycle();

        // 创建和链接着色器程序
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        program = createAndLinkProgram(vertexShader, fragmentShader);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置视口大小
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除画布
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // 设置顶点坐标和纹理坐标数据
        float[] vertices = {
                -0.5f,  0.5f,  0.0f,  // 左上角顶点
                -0.5f, -0.5f,  0.0f,  // 左下角顶点
                0.5f, -0.5f,  0.0f,  // 右下角顶点
                0.5f,  0.5f,  0.0f   // 右上角顶点
        };

        float[] textureCoords = {
                0.0f, 0.0f,  // 左上角纹理坐标
                0.0f, 1.0f,  // 左下角纹理坐标
                1.0f, 1.0f,  // 右下角纹理坐标
                1.0f, 0.0f   // 右上角纹理坐标
        };

        // 设置顶点坐标数据
        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // 设置纹理坐标数据
        FloatBuffer textureCoordBuffer = ByteBuffer.allocateDirect(textureCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        textureCoordBuffer.put(textureCoords);
        textureCoordBuffer.position(0);

        // 设置顶点坐标和纹理坐标属性
        int positionHandle = GLES20.glGetAttribLocation(program, "aPosition");
        int textureCoordHandle = GLES20.glGetAttribLocation(program, "aTextureCoord");
        GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(textureCoordHandle, 2, GLES20.GL_FLOAT, false, 0, textureCoordBuffer);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glEnableVertexAttribArray(textureCoordHandle);

        // 绘制纹理
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
    }
}
