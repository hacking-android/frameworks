/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.content.Context;
import android.filterfw.core.GLEnvironment;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class FilterSurfaceView
extends SurfaceView
implements SurfaceHolder.Callback {
    private static int STATE_ALLOCATED = 0;
    private static int STATE_CREATED = 1;
    private static int STATE_INITIALIZED = 2;
    private int mFormat;
    private GLEnvironment mGLEnv;
    private int mHeight;
    private SurfaceHolder.Callback mListener;
    private int mState = STATE_ALLOCATED;
    private int mSurfaceId = -1;
    private int mWidth;

    public FilterSurfaceView(Context context) {
        super(context);
        this.getHolder().addCallback(this);
    }

    public FilterSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.getHolder().addCallback(this);
    }

    private void registerSurface() {
        this.mSurfaceId = this.mGLEnv.registerSurface(this.getHolder().getSurface());
        if (this.mSurfaceId >= 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not register Surface: ");
        stringBuilder.append(this.getHolder().getSurface());
        stringBuilder.append(" in FilterSurfaceView!");
        throw new RuntimeException(stringBuilder.toString());
    }

    private void unregisterSurface() {
        int n;
        GLEnvironment gLEnvironment = this.mGLEnv;
        if (gLEnvironment != null && (n = this.mSurfaceId) > 0) {
            gLEnvironment.unregisterSurfaceId(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void bindToListener(SurfaceHolder.Callback object, GLEnvironment object2) {
        synchronized (this) {
            Throwable throwable2;
            if (object != null) {
                try {
                    if (this.mListener != null && this.mListener != object) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Attempting to bind filter ");
                        stringBuilder.append(object);
                        stringBuilder.append(" to SurfaceView with another open filter ");
                        stringBuilder.append(this.mListener);
                        stringBuilder.append(" attached already!");
                        object2 = new RuntimeException(stringBuilder.toString());
                        throw object2;
                    }
                    this.mListener = object;
                    if (this.mGLEnv != null && this.mGLEnv != object2) {
                        this.mGLEnv.unregisterSurfaceId(this.mSurfaceId);
                    }
                    this.mGLEnv = object2;
                    if (this.mState >= STATE_CREATED) {
                        this.registerSurface();
                        this.mListener.surfaceCreated(this.getHolder());
                        if (this.mState == STATE_INITIALIZED) {
                            this.mListener.surfaceChanged(this.getHolder(), this.mFormat, this.mWidth, this.mHeight);
                        }
                    }
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new NullPointerException("Attempting to bind null filter to SurfaceView!");
                throw object;
            }
            throw throwable2;
        }
    }

    public GLEnvironment getGLEnv() {
        synchronized (this) {
            GLEnvironment gLEnvironment = this.mGLEnv;
            return gLEnvironment;
        }
    }

    public int getSurfaceId() {
        synchronized (this) {
            int n = this.mSurfaceId;
            return n;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
        synchronized (this) {
            this.mFormat = n;
            this.mWidth = n2;
            this.mHeight = n3;
            this.mState = STATE_INITIALIZED;
            if (this.mListener != null) {
                this.mListener.surfaceChanged(surfaceHolder, n, n2, n3);
            }
            return;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        synchronized (this) {
            this.mState = STATE_CREATED;
            if (this.mGLEnv != null) {
                this.registerSurface();
            }
            if (this.mListener != null) {
                this.mListener.surfaceCreated(surfaceHolder);
            }
            return;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        synchronized (this) {
            this.mState = STATE_ALLOCATED;
            if (this.mListener != null) {
                this.mListener.surfaceDestroyed(surfaceHolder);
            }
            this.unregisterSurface();
            return;
        }
    }

    public void unbind() {
        synchronized (this) {
            this.mListener = null;
            return;
        }
    }
}

