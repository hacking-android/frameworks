/*
 * Decompiled with CFR 0.145.
 */
package javax.microedition.khronos.egl;

import android.annotation.UnsupportedAppUsage;
import com.google.android.gles_jni.EGLContextImpl;
import com.google.android.gles_jni.EGLDisplayImpl;
import com.google.android.gles_jni.EGLSurfaceImpl;
import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

public interface EGL10
extends EGL {
    public static final int EGL_ALPHA_FORMAT = 12424;
    public static final int EGL_ALPHA_MASK_SIZE = 12350;
    public static final int EGL_ALPHA_SIZE = 12321;
    public static final int EGL_BAD_ACCESS = 12290;
    public static final int EGL_BAD_ALLOC = 12291;
    public static final int EGL_BAD_ATTRIBUTE = 12292;
    public static final int EGL_BAD_CONFIG = 12293;
    public static final int EGL_BAD_CONTEXT = 12294;
    public static final int EGL_BAD_CURRENT_SURFACE = 12295;
    public static final int EGL_BAD_DISPLAY = 12296;
    public static final int EGL_BAD_MATCH = 12297;
    public static final int EGL_BAD_NATIVE_PIXMAP = 12298;
    public static final int EGL_BAD_NATIVE_WINDOW = 12299;
    public static final int EGL_BAD_PARAMETER = 12300;
    public static final int EGL_BAD_SURFACE = 12301;
    public static final int EGL_BLUE_SIZE = 12322;
    public static final int EGL_BUFFER_SIZE = 12320;
    public static final int EGL_COLORSPACE = 12423;
    public static final int EGL_COLOR_BUFFER_TYPE = 12351;
    public static final int EGL_CONFIG_CAVEAT = 12327;
    public static final int EGL_CONFIG_ID = 12328;
    public static final int EGL_CORE_NATIVE_ENGINE = 12379;
    public static final Object EGL_DEFAULT_DISPLAY = null;
    public static final int EGL_DEPTH_SIZE = 12325;
    public static final int EGL_DONT_CARE = -1;
    public static final int EGL_DRAW = 12377;
    public static final int EGL_EXTENSIONS = 12373;
    public static final int EGL_GREEN_SIZE = 12323;
    public static final int EGL_HEIGHT = 12374;
    public static final int EGL_HORIZONTAL_RESOLUTION = 12432;
    public static final int EGL_LARGEST_PBUFFER = 12376;
    public static final int EGL_LEVEL = 12329;
    public static final int EGL_LUMINANCE_BUFFER = 12431;
    public static final int EGL_LUMINANCE_SIZE = 12349;
    public static final int EGL_MAX_PBUFFER_HEIGHT = 12330;
    public static final int EGL_MAX_PBUFFER_PIXELS = 12331;
    public static final int EGL_MAX_PBUFFER_WIDTH = 12332;
    public static final int EGL_NATIVE_RENDERABLE = 12333;
    public static final int EGL_NATIVE_VISUAL_ID = 12334;
    public static final int EGL_NATIVE_VISUAL_TYPE = 12335;
    public static final int EGL_NONE = 12344;
    public static final int EGL_NON_CONFORMANT_CONFIG = 12369;
    public static final int EGL_NOT_INITIALIZED = 12289;
    public static final EGLContext EGL_NO_CONTEXT;
    public static final EGLDisplay EGL_NO_DISPLAY;
    public static final EGLSurface EGL_NO_SURFACE;
    public static final int EGL_PBUFFER_BIT = 1;
    public static final int EGL_PIXEL_ASPECT_RATIO = 12434;
    public static final int EGL_PIXMAP_BIT = 2;
    public static final int EGL_READ = 12378;
    public static final int EGL_RED_SIZE = 12324;
    public static final int EGL_RENDERABLE_TYPE = 12352;
    public static final int EGL_RENDER_BUFFER = 12422;
    public static final int EGL_RGB_BUFFER = 12430;
    public static final int EGL_SAMPLES = 12337;
    public static final int EGL_SAMPLE_BUFFERS = 12338;
    public static final int EGL_SINGLE_BUFFER = 12421;
    public static final int EGL_SLOW_CONFIG = 12368;
    public static final int EGL_STENCIL_SIZE = 12326;
    public static final int EGL_SUCCESS = 12288;
    public static final int EGL_SURFACE_TYPE = 12339;
    public static final int EGL_TRANSPARENT_BLUE_VALUE = 12341;
    public static final int EGL_TRANSPARENT_GREEN_VALUE = 12342;
    public static final int EGL_TRANSPARENT_RED_VALUE = 12343;
    public static final int EGL_TRANSPARENT_RGB = 12370;
    public static final int EGL_TRANSPARENT_TYPE = 12340;
    public static final int EGL_VENDOR = 12371;
    public static final int EGL_VERSION = 12372;
    public static final int EGL_VERTICAL_RESOLUTION = 12433;
    public static final int EGL_WIDTH = 12375;
    public static final int EGL_WINDOW_BIT = 4;

    static {
        EGL_NO_DISPLAY = new EGLDisplayImpl(0L);
        EGL_NO_CONTEXT = new EGLContextImpl(0L);
        EGL_NO_SURFACE = new EGLSurfaceImpl(0L);
    }

    public boolean eglChooseConfig(EGLDisplay var1, int[] var2, EGLConfig[] var3, int var4, int[] var5);

    public boolean eglCopyBuffers(EGLDisplay var1, EGLSurface var2, Object var3);

    public EGLContext eglCreateContext(EGLDisplay var1, EGLConfig var2, EGLContext var3, int[] var4);

    public EGLSurface eglCreatePbufferSurface(EGLDisplay var1, EGLConfig var2, int[] var3);

    @Deprecated
    public EGLSurface eglCreatePixmapSurface(EGLDisplay var1, EGLConfig var2, Object var3, int[] var4);

    public EGLSurface eglCreateWindowSurface(EGLDisplay var1, EGLConfig var2, Object var3, int[] var4);

    public boolean eglDestroyContext(EGLDisplay var1, EGLContext var2);

    public boolean eglDestroySurface(EGLDisplay var1, EGLSurface var2);

    public boolean eglGetConfigAttrib(EGLDisplay var1, EGLConfig var2, int var3, int[] var4);

    public boolean eglGetConfigs(EGLDisplay var1, EGLConfig[] var2, int var3, int[] var4);

    public EGLContext eglGetCurrentContext();

    public EGLDisplay eglGetCurrentDisplay();

    public EGLSurface eglGetCurrentSurface(int var1);

    public EGLDisplay eglGetDisplay(Object var1);

    public int eglGetError();

    public boolean eglInitialize(EGLDisplay var1, int[] var2);

    public boolean eglMakeCurrent(EGLDisplay var1, EGLSurface var2, EGLSurface var3, EGLContext var4);

    public boolean eglQueryContext(EGLDisplay var1, EGLContext var2, int var3, int[] var4);

    public String eglQueryString(EGLDisplay var1, int var2);

    public boolean eglQuerySurface(EGLDisplay var1, EGLSurface var2, int var3, int[] var4);

    @UnsupportedAppUsage
    public boolean eglReleaseThread();

    public boolean eglSwapBuffers(EGLDisplay var1, EGLSurface var2);

    public boolean eglTerminate(EGLDisplay var1);

    public boolean eglWaitGL();

    public boolean eglWaitNative(int var1, Object var2);
}

