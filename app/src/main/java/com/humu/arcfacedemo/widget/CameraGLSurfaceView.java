package com.humu.arcfacedemo.widget;

import android.content.Context;
import android.graphics.ImageFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.guo.android_extend.GLES2Render;
import com.guo.android_extend.image.ImageConverter;
import com.guo.android_extend.widget.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2018/12/1.
 */

public class CameraGLSurfaceView extends ExtGLSurfaceView implements GLSurfaceView.Renderer{

    private final String TAG = this.getClass().getSimpleName();

    private int mWidth, mHeight, mFormat, mRenderFormat;
    private int mDegree;
    private int mMirror;
    private boolean mDebugFPS;
    private boolean mConfigSuccess = false;

    private BlockingQueue<CameraFrameData> mImageRenderBuffers;
    private GLES2Render mGLES2Render;
    private CameraGLSurfaceView.OnRenderListener mOnRenderListener;
    private CameraGLSurfaceView.OnDrawListener mOnDrawListener;

    public interface OnDrawListener{
        public void onDrawOverlap(GLES2Render render);
    }

    public interface OnRenderListener {
        public void onBeforeRender(CameraFrameData data);
        public void onAfterRender(CameraFrameData data);
    }

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        onCreate();
    }

    public CameraGLSurfaceView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        onCreate();
    }

    private void onCreate() {
        if (isInEditMode()) {
            return;
        }
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setZOrderMediaOverlay(true);
        mImageRenderBuffers = new LinkedBlockingQueue<>();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG,"onSurfaceCreated");
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG,"onSurfaceChanged");
        if (mGLES2Render == null && mConfigSuccess) {
            mGLES2Render = new GLES2Render(mMirror, mDegree, mRenderFormat, mDebugFPS);
        }
        if (mGLES2Render != null) {
            mGLES2Render.setViewPort(width, height);
            mGLES2Render.setViewDisplay(mMirror, mDegree);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        CameraFrameData data = mImageRenderBuffers.poll();
        if (data != null) {
            byte[] buffer = data.mData;
            if (mOnRenderListener != null) {
                mOnRenderListener.onBeforeRender(data);
            }
            if (mGLES2Render != null) {
                mGLES2Render.render(buffer, mWidth, mHeight);
            }
            if (mOnRenderListener != null) {
                mOnRenderListener.onAfterRender(data);
            }
        }
        if (mOnDrawListener != null) {
            mOnDrawListener.onDrawOverlap(mGLES2Render);
        }
    }

    public void requestRender(CameraFrameData data) {
        if (!mImageRenderBuffers.offer(data)) {
            Log.e(TAG, "RENDER QUEUE FULL!");
        } else {
            requestRender();
        }
    }

    public void setOnDrawListener(CameraGLSurfaceView.OnDrawListener lis) {
        mOnDrawListener = lis;
    }

    public void setOnRenderListener(CameraGLSurfaceView.OnRenderListener lis) {
        mOnRenderListener = lis;
    }

    public boolean setImageConfig(int width, int height, int format) {
        mWidth = width;
        mHeight = height;
        mFormat = format;
        switch(format) {
            case ImageFormat.NV21 : mRenderFormat = ImageConverter.CP_PAF_NV21; mConfigSuccess = true; break;
            case ImageFormat.RGB_565 : mRenderFormat = ImageConverter.CP_RGB565; mConfigSuccess = true; break;
            default:
                Log.e(TAG, "Current camera preview format = " + format + ", render is not support!");
                mConfigSuccess = false;
        }
        return mConfigSuccess;
    }

    public void setRenderConfig(int degree, int mirror) {
        mDegree = degree;
        mMirror = mirror;
        if (mGLES2Render != null) {
            mGLES2Render.setViewDisplay(mMirror, degree);
        }
    }

    @Override
    public boolean OnOrientationChanged(int degree, int offset, int flag) {
        if (mGLES2Render != null) {
            mGLES2Render.setViewDisplay(mMirror, degree);
        }
        return super.OnOrientationChanged(degree, offset, flag);
    }

    public GLES2Render getGLES2Render() {
        return mGLES2Render;
    }

    public void debug_print_fps(boolean show) {
        mDebugFPS = show;
    }

    private int mSizeWidth = -1;
    private int mSizeHeight = -1;

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (-1 == mSizeWidth || -1 == mSizeHeight) {
            super.onMeasure(widthSpec, heightSpec);
        }
        else {
            setMeasuredDimension(mSizeWidth, mSizeHeight);
        }
    }

    public void resize(int width, int height) {
        mSizeWidth = width;
        mSizeHeight = height;
        getHolder().setFixedSize(width, height);
        requestLayout();
        invalidate();
    }


}
