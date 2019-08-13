/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.gles_jni;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.google.android.gles_jni.EGLContextImpl;
import com.google.android.gles_jni.EGLDisplayImpl;
import com.google.android.gles_jni.EGLSurfaceImpl;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public class EGLImpl
implements EGL10 {
    private EGLContextImpl mContext = new EGLContextImpl(-1L);
    private EGLDisplayImpl mDisplay = new EGLDisplayImpl(-1L);
    private EGLSurfaceImpl mSurface = new EGLSurfaceImpl(-1L);

    static {
        EGLImpl._nativeClassInit();
    }

    private native long _eglCreateContext(EGLDisplay var1, EGLConfig var2, EGLContext var3, int[] var4);

    private native long _eglCreatePbufferSurface(EGLDisplay var1, EGLConfig var2, int[] var3);

    private native void _eglCreatePixmapSurface(EGLSurface var1, EGLDisplay var2, EGLConfig var3, Object var4, int[] var5);

    private native long _eglCreateWindowSurface(EGLDisplay var1, EGLConfig var2, Object var3, int[] var4);

    private native long _eglCreateWindowSurfaceTexture(EGLDisplay var1, EGLConfig var2, Object var3, int[] var4);

    private native long _eglGetCurrentContext();

    private native long _eglGetCurrentDisplay();

    private native long _eglGetCurrentSurface(int var1);

    private native long _eglGetDisplay(Object var1);

    private static native void _nativeClassInit();

    public static native int getInitCount(EGLDisplay var0);

    @Override
    public native boolean eglChooseConfig(EGLDisplay var1, int[] var2, EGLConfig[] var3, int var4, int[] var5);

    @Override
    public native boolean eglCopyBuffers(EGLDisplay var1, EGLSurface var2, Object var3);

    @Override
    public EGLContext eglCreateContext(EGLDisplay eGLDisplay, EGLConfig eGLConfig, EGLContext eGLContext, int[] arrn) {
        long l = this._eglCreateContext(eGLDisplay, eGLConfig, eGLContext, arrn);
        if (l == 0L) {
            return EGL10.EGL_NO_CONTEXT;
        }
        return new EGLContextImpl(l);
    }

    @Override
    public EGLSurface eglCreatePbufferSurface(EGLDisplay eGLDisplay, EGLConfig eGLConfig, int[] arrn) {
        long l = this._eglCreatePbufferSurface(eGLDisplay, eGLConfig, arrn);
        if (l == 0L) {
            return EGL10.EGL_NO_SURFACE;
        }
        return new EGLSurfaceImpl(l);
    }

    @Override
    public EGLSurface eglCreatePixmapSurface(EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object object, int[] arrn) {
        EGLSurfaceImpl eGLSurfaceImpl = new EGLSurfaceImpl();
        this._eglCreatePixmapSurface(eGLSurfaceImpl, eGLDisplay, eGLConfig, object, arrn);
        if (eGLSurfaceImpl.mEGLSurface == 0L) {
            return EGL10.EGL_NO_SURFACE;
        }
        return eGLSurfaceImpl;
    }

    @Override
    public EGLSurface eglCreateWindowSurface(EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object object, int[] arrn) {
        block11 : {
            long l;
            block10 : {
                block9 : {
                    Surface surface = null;
                    if (object instanceof SurfaceView) {
                        surface = ((SurfaceView)object).getHolder().getSurface();
                    } else if (object instanceof SurfaceHolder) {
                        surface = ((SurfaceHolder)object).getSurface();
                    } else if (object instanceof Surface) {
                        surface = (Surface)object;
                    }
                    if (surface == null) break block9;
                    l = this._eglCreateWindowSurface(eGLDisplay, eGLConfig, surface, arrn);
                    break block10;
                }
                if (!(object instanceof SurfaceTexture)) break block11;
                l = this._eglCreateWindowSurfaceTexture(eGLDisplay, eGLConfig, object, arrn);
            }
            if (l == 0L) {
                return EGL10.EGL_NO_SURFACE;
            }
            return new EGLSurfaceImpl(l);
        }
        throw new UnsupportedOperationException("eglCreateWindowSurface() can only be called with an instance of Surface, SurfaceView, SurfaceHolder or SurfaceTexture at the moment.");
    }

    @Override
    public native boolean eglDestroyContext(EGLDisplay var1, EGLContext var2);

    @Override
    public native boolean eglDestroySurface(EGLDisplay var1, EGLSurface var2);

    @Override
    public native boolean eglGetConfigAttrib(EGLDisplay var1, EGLConfig var2, int var3, int[] var4);

    @Override
    public native boolean eglGetConfigs(EGLDisplay var1, EGLConfig[] var2, int var3, int[] var4);

    @Override
    public EGLContext eglGetCurrentContext() {
        synchronized (this) {
            EGLContextImpl eGLContextImpl;
            long l;
            block6 : {
                l = this._eglGetCurrentContext();
                if (l != 0L) break block6;
                EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
                return eGLContext;
            }
            if (this.mContext.mEGLContext != l) {
                this.mContext = eGLContextImpl = new EGLContextImpl(l);
            }
            eGLContextImpl = this.mContext;
            return eGLContextImpl;
        }
    }

    @Override
    public EGLDisplay eglGetCurrentDisplay() {
        synchronized (this) {
            long l;
            EGLDisplayImpl eGLDisplayImpl;
            block6 : {
                l = this._eglGetCurrentDisplay();
                if (l != 0L) break block6;
                EGLDisplay eGLDisplay = EGL10.EGL_NO_DISPLAY;
                return eGLDisplay;
            }
            if (this.mDisplay.mEGLDisplay != l) {
                this.mDisplay = eGLDisplayImpl = new EGLDisplayImpl(l);
            }
            eGLDisplayImpl = this.mDisplay;
            return eGLDisplayImpl;
        }
    }

    @Override
    public EGLSurface eglGetCurrentSurface(int n) {
        synchronized (this) {
            long l;
            EGLSurfaceImpl eGLSurfaceImpl;
            block6 : {
                l = this._eglGetCurrentSurface(n);
                if (l != 0L) break block6;
                EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
                return eGLSurface;
            }
            if (this.mSurface.mEGLSurface != l) {
                this.mSurface = eGLSurfaceImpl = new EGLSurfaceImpl(l);
            }
            eGLSurfaceImpl = this.mSurface;
            return eGLSurfaceImpl;
        }
    }

    @Override
    public EGLDisplay eglGetDisplay(Object object) {
        synchronized (this) {
            long l;
            block6 : {
                l = this._eglGetDisplay(object);
                if (l != 0L) break block6;
                object = EGL10.EGL_NO_DISPLAY;
                return object;
            }
            if (this.mDisplay.mEGLDisplay != l) {
                this.mDisplay = object = new EGLDisplayImpl(l);
            }
            object = this.mDisplay;
            return object;
        }
    }

    @Override
    public native int eglGetError();

    @Override
    public native boolean eglInitialize(EGLDisplay var1, int[] var2);

    @Override
    public native boolean eglMakeCurrent(EGLDisplay var1, EGLSurface var2, EGLSurface var3, EGLContext var4);

    @Override
    public native boolean eglQueryContext(EGLDisplay var1, EGLContext var2, int var3, int[] var4);

    @Override
    public native String eglQueryString(EGLDisplay var1, int var2);

    @Override
    public native boolean eglQuerySurface(EGLDisplay var1, EGLSurface var2, int var3, int[] var4);

    @Override
    public native boolean eglReleaseThread();

    @Override
    public native boolean eglSwapBuffers(EGLDisplay var1, EGLSurface var2);

    @Override
    public native boolean eglTerminate(EGLDisplay var1);

    @Override
    public native boolean eglWaitGL();

    @Override
    public native boolean eglWaitNative(int var1, Object var2);
}

