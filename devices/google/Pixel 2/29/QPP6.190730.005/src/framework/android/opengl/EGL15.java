/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLImage;
import android.opengl.EGLSurface;
import android.opengl.EGLSync;
import java.nio.Buffer;

public final class EGL15 {
    public static final int EGL_CL_EVENT_HANDLE = 12444;
    public static final int EGL_CONDITION_SATISFIED = 12534;
    public static final int EGL_CONTEXT_MAJOR_VERSION = 12440;
    public static final int EGL_CONTEXT_MINOR_VERSION = 12539;
    public static final int EGL_CONTEXT_OPENGL_COMPATIBILITY_PROFILE_BIT = 2;
    public static final int EGL_CONTEXT_OPENGL_CORE_PROFILE_BIT = 1;
    public static final int EGL_CONTEXT_OPENGL_DEBUG = 12720;
    public static final int EGL_CONTEXT_OPENGL_FORWARD_COMPATIBLE = 12721;
    public static final int EGL_CONTEXT_OPENGL_PROFILE_MASK = 12541;
    public static final int EGL_CONTEXT_OPENGL_RESET_NOTIFICATION_STRATEGY = 12733;
    public static final int EGL_CONTEXT_OPENGL_ROBUST_ACCESS = 12722;
    public static final long EGL_FOREVER = -1L;
    public static final int EGL_GL_COLORSPACE = 12445;
    public static final int EGL_GL_COLORSPACE_LINEAR = 12426;
    public static final int EGL_GL_COLORSPACE_SRGB = 12425;
    public static final int EGL_GL_RENDERBUFFER = 12473;
    public static final int EGL_GL_TEXTURE_2D = 12465;
    public static final int EGL_GL_TEXTURE_3D = 12466;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 12468;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 12470;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 12472;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_POSITIVE_X = 12467;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 12469;
    public static final int EGL_GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 12471;
    public static final int EGL_GL_TEXTURE_LEVEL = 12476;
    public static final int EGL_GL_TEXTURE_ZOFFSET = 12477;
    public static final int EGL_IMAGE_PRESERVED = 12498;
    public static final int EGL_LOSE_CONTEXT_ON_RESET = 12735;
    public static final EGLContext EGL_NO_CONTEXT;
    public static final EGLDisplay EGL_NO_DISPLAY;
    public static final EGLImage EGL_NO_IMAGE;
    public static final int EGL_NO_RESET_NOTIFICATION = 12734;
    public static final EGLSurface EGL_NO_SURFACE;
    public static final EGLSync EGL_NO_SYNC;
    public static final int EGL_OPENGL_ES3_BIT = 64;
    public static final int EGL_PLATFORM_ANDROID_KHR = 12609;
    public static final int EGL_SIGNALED = 12530;
    public static final int EGL_SYNC_CL_EVENT = 12542;
    public static final int EGL_SYNC_CL_EVENT_COMPLETE = 12543;
    public static final int EGL_SYNC_CONDITION = 12536;
    public static final int EGL_SYNC_FENCE = 12537;
    public static final int EGL_SYNC_FLUSH_COMMANDS_BIT = 1;
    public static final int EGL_SYNC_PRIOR_COMMANDS_COMPLETE = 12528;
    public static final int EGL_SYNC_STATUS = 12529;
    public static final int EGL_SYNC_TYPE = 12535;
    public static final int EGL_TIMEOUT_EXPIRED = 12533;
    public static final int EGL_UNSIGNALED = 12531;

    static {
        EGL_NO_IMAGE = null;
        EGL_NO_SYNC = null;
        EGL_NO_CONTEXT = null;
        EGL_NO_DISPLAY = null;
        EGL_NO_SURFACE = null;
        EGL15._nativeClassInit();
    }

    private EGL15() {
    }

    private static native void _nativeClassInit();

    public static native int eglClientWaitSync(EGLDisplay var0, EGLSync var1, int var2, long var3);

    public static native EGLImage eglCreateImage(EGLDisplay var0, EGLContext var1, int var2, long var3, long[] var5, int var6);

    public static native EGLSurface eglCreatePlatformPixmapSurface(EGLDisplay var0, EGLConfig var1, Buffer var2, long[] var3, int var4);

    public static native EGLSurface eglCreatePlatformWindowSurface(EGLDisplay var0, EGLConfig var1, Buffer var2, long[] var3, int var4);

    public static native EGLSync eglCreateSync(EGLDisplay var0, int var1, long[] var2, int var3);

    public static native boolean eglDestroyImage(EGLDisplay var0, EGLImage var1);

    public static native boolean eglDestroySync(EGLDisplay var0, EGLSync var1);

    public static native EGLDisplay eglGetPlatformDisplay(int var0, long var1, long[] var3, int var4);

    public static native boolean eglGetSyncAttrib(EGLDisplay var0, EGLSync var1, int var2, long[] var3, int var4);

    public static native boolean eglWaitSync(EGLDisplay var0, EGLSync var1, int var2);
}

