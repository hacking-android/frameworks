/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;

public class EGLExt {
    public static final int EGL_CONTEXT_FLAGS_KHR = 12540;
    public static final int EGL_CONTEXT_MAJOR_VERSION_KHR = 12440;
    public static final int EGL_CONTEXT_MINOR_VERSION_KHR = 12539;
    public static final int EGL_OPENGL_ES3_BIT_KHR = 64;
    public static final int EGL_RECORDABLE_ANDROID = 12610;

    static {
        EGLExt._nativeClassInit();
    }

    private static native void _nativeClassInit();

    public static native boolean eglPresentationTimeANDROID(EGLDisplay var0, EGLSurface var1, long var2);
}

