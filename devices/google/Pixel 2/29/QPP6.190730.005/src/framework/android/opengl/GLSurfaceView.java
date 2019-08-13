/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.opengl.EGLLogWrapper;
import android.opengl.GLDebugHelper;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GLSurfaceView
extends SurfaceView
implements SurfaceHolder.Callback2 {
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean LOG_ATTACH_DETACH = false;
    private static final boolean LOG_EGL = false;
    private static final boolean LOG_PAUSE_RESUME = false;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_RENDERER_DRAW_FRAME = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final String TAG = "GLSurfaceView";
    private static final GLThreadManager sGLThreadManager = new GLThreadManager();
    private int mDebugFlags;
    private boolean mDetached;
    private EGLConfigChooser mEGLConfigChooser;
    private int mEGLContextClientVersion;
    private EGLContextFactory mEGLContextFactory;
    private EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    @UnsupportedAppUsage
    private GLThread mGLThread;
    private GLWrapper mGLWrapper;
    private boolean mPreserveEGLContextOnPause;
    @UnsupportedAppUsage
    private Renderer mRenderer;
    private final WeakReference<GLSurfaceView> mThisWeakRef = new WeakReference<GLSurfaceView>(this);

    public GLSurfaceView(Context context) {
        super(context);
        this.init();
    }

    public GLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    static /* synthetic */ Renderer access$1000(GLSurfaceView gLSurfaceView) {
        return gLSurfaceView.mRenderer;
    }

    static /* synthetic */ boolean access$900(GLSurfaceView gLSurfaceView) {
        return gLSurfaceView.mPreserveEGLContextOnPause;
    }

    private void checkRenderThreadState() {
        if (this.mGLThread == null) {
            return;
        }
        throw new IllegalStateException("setRenderer has already been called for this instance.");
    }

    private void init() {
        this.getHolder().addCallback(this);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mGLThread != null) {
                this.mGLThread.requestExitAndWait();
            }
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.mPreserveEGLContextOnPause;
    }

    public int getRenderMode() {
        return this.mGLThread.getRenderMode();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDetached && this.mRenderer != null) {
            int n = 1;
            GLThread gLThread = this.mGLThread;
            if (gLThread != null) {
                n = gLThread.getRenderMode();
            }
            this.mGLThread = new GLThread(this.mThisWeakRef);
            if (n != 1) {
                this.mGLThread.setRenderMode(n);
            }
            this.mGLThread.start();
        }
        this.mDetached = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        GLThread gLThread = this.mGLThread;
        if (gLThread != null) {
            gLThread.requestExitAndWait();
        }
        this.mDetached = true;
        super.onDetachedFromWindow();
    }

    public void onPause() {
        this.mGLThread.onPause();
    }

    public void onResume() {
        this.mGLThread.onResume();
    }

    public void queueEvent(Runnable runnable) {
        this.mGLThread.queueEvent(runnable);
    }

    public void requestRender() {
        this.mGLThread.requestRender();
    }

    public void setDebugFlags(int n) {
        this.mDebugFlags = n;
    }

    public void setEGLConfigChooser(int n, int n2, int n3, int n4, int n5, int n6) {
        this.setEGLConfigChooser(new ComponentSizeChooser(n, n2, n3, n4, n5, n6));
    }

    public void setEGLConfigChooser(EGLConfigChooser eGLConfigChooser) {
        this.checkRenderThreadState();
        this.mEGLConfigChooser = eGLConfigChooser;
    }

    public void setEGLConfigChooser(boolean bl) {
        this.setEGLConfigChooser(new SimpleEGLConfigChooser(bl));
    }

    public void setEGLContextClientVersion(int n) {
        this.checkRenderThreadState();
        this.mEGLContextClientVersion = n;
    }

    public void setEGLContextFactory(EGLContextFactory eGLContextFactory) {
        this.checkRenderThreadState();
        this.mEGLContextFactory = eGLContextFactory;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory eGLWindowSurfaceFactory) {
        this.checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = eGLWindowSurfaceFactory;
    }

    public void setGLWrapper(GLWrapper gLWrapper) {
        this.mGLWrapper = gLWrapper;
    }

    public void setPreserveEGLContextOnPause(boolean bl) {
        this.mPreserveEGLContextOnPause = bl;
    }

    public void setRenderMode(int n) {
        this.mGLThread.setRenderMode(n);
    }

    public void setRenderer(Renderer renderer) {
        this.checkRenderThreadState();
        if (this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
        }
        if (this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new DefaultContextFactory();
        }
        if (this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
        }
        this.mRenderer = renderer;
        this.mGLThread = new GLThread(this.mThisWeakRef);
        this.mGLThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
        this.mGLThread.onWindowResize(n2, n3);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mGLThread.surfaceCreated();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.mGLThread.surfaceDestroyed();
    }

    @Deprecated
    @Override
    public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceRedrawNeededAsync(SurfaceHolder object, Runnable runnable) {
        object = this.mGLThread;
        if (object != null) {
            ((GLThread)object).requestRenderAndNotify(runnable);
        }
    }

    private abstract class BaseConfigChooser
    implements EGLConfigChooser {
        protected int[] mConfigSpec;

        public BaseConfigChooser(int[] arrn) {
            this.mConfigSpec = this.filterConfigSpec(arrn);
        }

        private int[] filterConfigSpec(int[] arrn) {
            if (GLSurfaceView.this.mEGLContextClientVersion != 2 && GLSurfaceView.this.mEGLContextClientVersion != 3) {
                return arrn;
            }
            int n = arrn.length;
            int[] arrn2 = new int[n + 2];
            System.arraycopy(arrn, 0, arrn2, 0, n - 1);
            arrn2[n - 1] = 12352;
            arrn2[n] = GLSurfaceView.this.mEGLContextClientVersion == 2 ? 4 : 64;
            arrn2[n + 1] = 12344;
            return arrn2;
        }

        @Override
        public EGLConfig chooseConfig(EGL10 object, EGLDisplay eGLDisplay) {
            int[] arrn = new int[1];
            if (object.eglChooseConfig(eGLDisplay, this.mConfigSpec, null, 0, arrn)) {
                int n = arrn[0];
                if (n > 0) {
                    EGLConfig[] arreGLConfig = new EGLConfig[n];
                    if (object.eglChooseConfig(eGLDisplay, this.mConfigSpec, arreGLConfig, n, arrn)) {
                        if ((object = this.chooseConfig((EGL10)object, eGLDisplay, arreGLConfig)) != null) {
                            return object;
                        }
                        throw new IllegalArgumentException("No config chosen");
                    }
                    throw new IllegalArgumentException("eglChooseConfig#2 failed");
                }
                throw new IllegalArgumentException("No configs match configSpec");
            }
            throw new IllegalArgumentException("eglChooseConfig failed");
        }

        abstract EGLConfig chooseConfig(EGL10 var1, EGLDisplay var2, EGLConfig[] var3);
    }

    private class ComponentSizeChooser
    extends BaseConfigChooser {
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue;

        public ComponentSizeChooser(int n, int n2, int n3, int n4, int n5, int n6) {
            super(new int[]{12324, n, 12323, n2, 12322, n3, 12321, n4, 12325, n5, 12326, n6, 12344});
            this.mValue = new int[1];
            this.mRedSize = n;
            this.mGreenSize = n2;
            this.mBlueSize = n3;
            this.mAlphaSize = n4;
            this.mDepthSize = n5;
            this.mStencilSize = n6;
        }

        private int findConfigAttrib(EGL10 eGL10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int n, int n2) {
            if (eGL10.eglGetConfigAttrib(eGLDisplay, eGLConfig, n, this.mValue)) {
                return this.mValue[0];
            }
            return n2;
        }

        @Override
        public EGLConfig chooseConfig(EGL10 eGL10, EGLDisplay eGLDisplay, EGLConfig[] arreGLConfig) {
            for (EGLConfig eGLConfig : arreGLConfig) {
                int n = this.findConfigAttrib(eGL10, eGLDisplay, eGLConfig, 12325, 0);
                int n2 = this.findConfigAttrib(eGL10, eGLDisplay, eGLConfig, 12326, 0);
                if (n < this.mDepthSize || n2 < this.mStencilSize) continue;
                int n3 = this.findConfigAttrib(eGL10, eGLDisplay, eGLConfig, 12324, 0);
                n = this.findConfigAttrib(eGL10, eGLDisplay, eGLConfig, 12323, 0);
                n2 = this.findConfigAttrib(eGL10, eGLDisplay, eGLConfig, 12322, 0);
                int n4 = this.findConfigAttrib(eGL10, eGLDisplay, eGLConfig, 12321, 0);
                if (n3 != this.mRedSize || n != this.mGreenSize || n2 != this.mBlueSize || n4 != this.mAlphaSize) continue;
                return eGLConfig;
            }
            return null;
        }
    }

    private class DefaultContextFactory
    implements EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION = 12440;

        private DefaultContextFactory() {
        }

        @Override
        public EGLContext createContext(EGL10 eGL10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            int n = this.EGL_CONTEXT_CLIENT_VERSION;
            int n2 = GLSurfaceView.this.mEGLContextClientVersion;
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            int[] arrn = GLSurfaceView.this.mEGLContextClientVersion != 0 ? new int[]{n, n2, 12344} : null;
            return eGL10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, arrn);
        }

        @Override
        public void destroyContext(EGL10 eGL10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
            if (!eGL10.eglDestroyContext(eGLDisplay, eGLContext)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("display:");
                stringBuilder.append(eGLDisplay);
                stringBuilder.append(" context: ");
                stringBuilder.append(eGLContext);
                Log.e("DefaultContextFactory", stringBuilder.toString());
                EglHelper.throwEglException("eglDestroyContex", eGL10.eglGetError());
            }
        }
    }

    private static class DefaultWindowSurfaceFactory
    implements EGLWindowSurfaceFactory {
        private DefaultWindowSurfaceFactory() {
        }

        @Override
        public EGLSurface createWindowSurface(EGL10 object, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object object2) {
            Object var5_6 = null;
            try {
                object = object.eglCreateWindowSurface(eGLDisplay, eGLConfig, object2, null);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.e(GLSurfaceView.TAG, "eglCreateWindowSurface", illegalArgumentException);
                object = var5_6;
            }
            return object;
        }

        @Override
        public void destroySurface(EGL10 eGL10, EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
            eGL10.eglDestroySurface(eGLDisplay, eGLSurface);
        }
    }

    public static interface EGLConfigChooser {
        public EGLConfig chooseConfig(EGL10 var1, EGLDisplay var2);
    }

    public static interface EGLContextFactory {
        public EGLContext createContext(EGL10 var1, EGLDisplay var2, EGLConfig var3);

        public void destroyContext(EGL10 var1, EGLDisplay var2, EGLContext var3);
    }

    public static interface EGLWindowSurfaceFactory {
        public EGLSurface createWindowSurface(EGL10 var1, EGLDisplay var2, EGLConfig var3, Object var4);

        public void destroySurface(EGL10 var1, EGLDisplay var2, EGLSurface var3);
    }

    private static class EglHelper {
        EGL10 mEgl;
        EGLConfig mEglConfig;
        @UnsupportedAppUsage
        EGLContext mEglContext;
        EGLDisplay mEglDisplay;
        EGLSurface mEglSurface;
        private WeakReference<GLSurfaceView> mGLSurfaceViewWeakRef;

        public EglHelper(WeakReference<GLSurfaceView> weakReference) {
            this.mGLSurfaceViewWeakRef = weakReference;
        }

        private void destroySurfaceImp() {
            Object object = this.mEglSurface;
            if (object != null && object != EGL10.EGL_NO_SURFACE) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                object = (GLSurfaceView)this.mGLSurfaceViewWeakRef.get();
                if (object != null) {
                    ((GLSurfaceView)object).mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
                }
                this.mEglSurface = null;
            }
        }

        public static String formatEglError(String string2, int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" failed: ");
            stringBuilder.append(EGLLogWrapper.getErrorString(n));
            return stringBuilder.toString();
        }

        public static void logEglErrorAsWarning(String string2, String string3, int n) {
            Log.w(string2, EglHelper.formatEglError(string3, n));
        }

        private void throwEglException(String string2) {
            EglHelper.throwEglException(string2, this.mEgl.eglGetError());
        }

        public static void throwEglException(String string2, int n) {
            throw new RuntimeException(EglHelper.formatEglError(string2, n));
        }

        GL createGL() {
            GL gL = this.mEglContext.getGL();
            GLSurfaceView gLSurfaceView = (GLSurfaceView)this.mGLSurfaceViewWeakRef.get();
            Object object = gL;
            if (gLSurfaceView != null) {
                GL gL2 = gL;
                if (gLSurfaceView.mGLWrapper != null) {
                    gL2 = gLSurfaceView.mGLWrapper.wrap(gL);
                }
                object = gL2;
                if ((gLSurfaceView.mDebugFlags & 3) != 0) {
                    int n = 0;
                    object = null;
                    if ((gLSurfaceView.mDebugFlags & 1) != 0) {
                        n = false | true;
                    }
                    if ((gLSurfaceView.mDebugFlags & 2) != 0) {
                        object = new LogWriter();
                    }
                    object = GLDebugHelper.wrap(gL2, n, (Writer)object);
                }
            }
            return object;
        }

        public boolean createSurface() {
            if (this.mEgl != null) {
                if (this.mEglDisplay != null) {
                    if (this.mEglConfig != null) {
                        this.destroySurfaceImp();
                        Object object = (GLSurfaceView)this.mGLSurfaceViewWeakRef.get();
                        this.mEglSurface = object != null ? ((GLSurfaceView)object).mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, ((SurfaceView)object).getHolder()) : null;
                        object = this.mEglSurface;
                        if (object != null && object != EGL10.EGL_NO_SURFACE) {
                            object = this.mEgl;
                            EGLDisplay eGLDisplay = this.mEglDisplay;
                            EGLSurface eGLSurface = this.mEglSurface;
                            if (!object.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.mEglContext)) {
                                EglHelper.logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.mEgl.eglGetError());
                                return false;
                            }
                            return true;
                        }
                        if (this.mEgl.eglGetError() == 12299) {
                            Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                        }
                        return false;
                    }
                    throw new RuntimeException("mEglConfig not initialized");
                }
                throw new RuntimeException("eglDisplay not initialized");
            }
            throw new RuntimeException("egl not initialized");
        }

        public void destroySurface() {
            this.destroySurfaceImp();
        }

        public void finish() {
            Object object;
            if (this.mEglContext != null) {
                object = (GLSurfaceView)this.mGLSurfaceViewWeakRef.get();
                if (object != null) {
                    ((GLSurfaceView)object).mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }
                this.mEglContext = null;
            }
            if ((object = this.mEglDisplay) != null) {
                this.mEgl.eglTerminate((EGLDisplay)object);
                this.mEglDisplay = null;
            }
        }

        public void start() {
            this.mEgl = (EGL10)EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.mEglDisplay != EGL10.EGL_NO_DISPLAY) {
                Object object = new int[2];
                if (this.mEgl.eglInitialize(this.mEglDisplay, (int[])object)) {
                    object = (GLSurfaceView)this.mGLSurfaceViewWeakRef.get();
                    if (object == null) {
                        this.mEglConfig = null;
                        this.mEglContext = null;
                    } else {
                        this.mEglConfig = ((GLSurfaceView)object).mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
                        this.mEglContext = ((GLSurfaceView)object).mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
                    }
                    object = this.mEglContext;
                    if (object == null || object == EGL10.EGL_NO_CONTEXT) {
                        this.mEglContext = null;
                        this.throwEglException("createContext");
                    }
                    this.mEglSurface = null;
                    return;
                }
                throw new RuntimeException("eglInitialize failed");
            }
            throw new RuntimeException("eglGetDisplay failed");
        }

        public int swap() {
            if (!this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
                return this.mEgl.eglGetError();
            }
            return 12288;
        }
    }

    static class GLThread
    extends Thread {
        @UnsupportedAppUsage
        private EglHelper mEglHelper;
        private ArrayList<Runnable> mEventQueue = new ArrayList();
        private boolean mExited;
        private Runnable mFinishDrawingRunnable = null;
        private boolean mFinishedCreatingEglSurface;
        private WeakReference<GLSurfaceView> mGLSurfaceViewWeakRef;
        private boolean mHasSurface;
        private boolean mHaveEglContext;
        private boolean mHaveEglSurface;
        private int mHeight = 0;
        private boolean mPaused;
        private boolean mRenderComplete;
        private int mRenderMode = 1;
        private boolean mRequestPaused;
        private boolean mRequestRender = true;
        private boolean mShouldExit;
        private boolean mShouldReleaseEglContext;
        private boolean mSizeChanged = true;
        private boolean mSurfaceIsBad;
        private boolean mWaitingForSurface;
        private boolean mWantRenderNotification = false;
        private int mWidth = 0;

        GLThread(WeakReference<GLSurfaceView> weakReference) {
            this.mGLSurfaceViewWeakRef = weakReference;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        private void guardedRun() throws InterruptedException {
            this.mEglHelper = new EglHelper(this.mGLSurfaceViewWeakRef);
            this.mHaveEglContext = false;
            this.mHaveEglSurface = false;
            this.mWantRenderNotification = false;
            var1_1 = null;
            var2_2 = 0;
            var3_3 = 0;
            var4_4 = 0;
            var5_5 = 0;
            var6_6 = 0;
            var7_7 = 0;
            var8_8 = 0;
            var9_9 = 0;
            var10_10 = 0;
            var11_11 = 0;
            var12_12 = null;
            var13_13 = null;
            block35 : do {
                var14_19 = GLSurfaceView.access$800();
                // MONITORENTER : var14_19
                var15_20 = var8_8;
                var8_8 = var6_6;
                var16_21 = var5_5;
                var5_5 = var4_4;
                var6_6 = var3_3;
                do {
                    block66 : {
                        block65 : {
                            block61 : {
                                block62 : {
                                    block60 : {
                                        block64 : {
                                            block59 : {
                                                block63 : {
                                                    if (this.mShouldExit) {
                                                        // MONITOREXIT : var14_19
                                                        var17_22 = GLSurfaceView.access$800();
                                                        // MONITORENTER : var17_22
                                                        this.stopEglSurfaceLocked();
                                                        this.stopEglContextLocked();
                                                        // MONITOREXIT : var17_22
                                                        return;
                                                    }
                                                    if (this.mEventQueue.isEmpty()) break block63;
                                                    var18_24 = this.mEventQueue.remove(0);
                                                    var19_25 = var6_6;
                                                    var4_4 = var5_5;
                                                    var5_5 = var16_21;
                                                    var6_6 = var8_8;
                                                    var8_8 = var15_20;
                                                    var15_20 = var9_9;
                                                    var20_26 = var10_10;
                                                    var21_27 = var11_11;
                                                    break block64;
                                                }
                                                var22_28 = this.mPaused;
                                                var23_29 = false;
                                                if (var22_28 != this.mRequestPaused) {
                                                    var23_29 = this.mRequestPaused;
                                                    this.mPaused = this.mRequestPaused;
                                                    GLSurfaceView.access$800().notifyAll();
                                                }
                                                var24_30 = var9_9;
                                                if (this.mShouldReleaseEglContext) {
                                                    this.stopEglSurfaceLocked();
                                                    this.stopEglContextLocked();
                                                    this.mShouldReleaseEglContext = false;
                                                    var24_30 = 1;
                                                }
                                                var25_31 = var16_21;
                                                if (var16_21 != 0) {
                                                    this.stopEglSurfaceLocked();
                                                    this.stopEglContextLocked();
                                                    var25_31 = 0;
                                                }
                                                if (var23_29 && this.mHaveEglSurface) {
                                                    this.stopEglSurfaceLocked();
                                                }
                                                if (var23_29 && this.mHaveEglContext && !(var23_29 = (var17_22 = (GLSurfaceView)this.mGLSurfaceViewWeakRef.get()) == null ? false : GLSurfaceView.access$900((GLSurfaceView)var17_22))) {
                                                    this.stopEglContextLocked();
                                                }
                                                if (!this.mHasSurface && !this.mWaitingForSurface) {
                                                    if (this.mHaveEglSurface) {
                                                        this.stopEglSurfaceLocked();
                                                    }
                                                    this.mWaitingForSurface = true;
                                                    this.mSurfaceIsBad = false;
                                                    GLSurfaceView.access$800().notifyAll();
                                                }
                                                if (this.mHasSurface && this.mWaitingForSurface) {
                                                    this.mWaitingForSurface = false;
                                                    GLSurfaceView.access$800().notifyAll();
                                                }
                                                var26_32 = var15_20;
                                                if (var15_20 != 0) {
                                                    this.mWantRenderNotification = false;
                                                    var26_32 = 0;
                                                    this.mRenderComplete = true;
                                                    GLSurfaceView.access$800().notifyAll();
                                                }
                                                if (this.mFinishDrawingRunnable != null) {
                                                    var17_22 = this.mFinishDrawingRunnable;
                                                    this.mFinishDrawingRunnable = null;
                                                } else {
                                                    var17_22 = var13_13;
                                                }
                                                if (!this.readyToDraw()) break block65;
                                                var23_29 = this.mHaveEglContext;
                                                var3_3 = var2_2;
                                                var9_9 = var24_30;
                                                if (!var23_29) {
                                                    if (var24_30 != 0) {
                                                        var9_9 = 0;
                                                        var3_3 = var2_2;
                                                    } else {
                                                        this.mEglHelper.start();
                                                        this.mHaveEglContext = true;
                                                        var3_3 = 1;
                                                        GLSurfaceView.access$800().notifyAll();
                                                        var9_9 = var24_30;
                                                    }
                                                    break block59;
                                                    catch (RuntimeException var13_14) {
                                                        GLSurfaceView.access$800().releaseEglContextLocked(this);
                                                        throw var13_14;
                                                    }
                                                }
                                            }
                                            var4_4 = var6_6;
                                            var24_30 = var5_5;
                                            var2_2 = var8_8;
                                            if (this.mHaveEglContext) {
                                                var4_4 = var6_6;
                                                var24_30 = var5_5;
                                                var2_2 = var8_8;
                                                if (!this.mHaveEglSurface) {
                                                    this.mHaveEglSurface = true;
                                                    var4_4 = 1;
                                                    var24_30 = 1;
                                                    var2_2 = 1;
                                                }
                                            }
                                            var27_33 = var3_3;
                                            var16_21 = var4_4;
                                            var20_26 = var24_30;
                                            var15_20 = var2_2;
                                            var19_25 = var9_9;
                                            var13_13 = var17_22;
                                            if (!this.mHaveEglSurface) break block66;
                                            var16_21 = var4_4;
                                            var27_33 = var2_2;
                                            if (this.mSizeChanged) {
                                                var27_33 = 1;
                                                var10_10 = this.mWidth;
                                                var11_11 = this.mHeight;
                                                this.mWantRenderNotification = true;
                                                var16_21 = 1;
                                                this.mSizeChanged = false;
                                            }
                                            this.mRequestRender = false;
                                            GLSurfaceView.access$800().notifyAll();
                                            var23_29 = this.mWantRenderNotification;
                                            var2_2 = var3_3;
                                            var19_25 = var16_21;
                                            var4_4 = var24_30;
                                            var5_5 = var25_31;
                                            var6_6 = var27_33;
                                            var8_8 = var26_32;
                                            var15_20 = var9_9;
                                            var20_26 = var10_10;
                                            var21_27 = var11_11;
                                            var18_24 = var12_12;
                                            var13_13 = var17_22;
                                            if (var23_29) {
                                                var7_7 = 1;
                                                var13_13 = var17_22;
                                                var18_24 = var12_12;
                                                var21_27 = var11_11;
                                                var20_26 = var10_10;
                                                var15_20 = var9_9;
                                                var8_8 = var26_32;
                                                var6_6 = var27_33;
                                                var5_5 = var25_31;
                                                var4_4 = var24_30;
                                                var19_25 = var16_21;
                                                var2_2 = var3_3;
                                            }
                                        }
                                        if (var18_24 == null) ** GOTO lbl174
                                        var18_24.run();
                                        var12_12 = null;
                                        var3_3 = var19_25;
                                        var9_9 = var15_20;
                                        var10_10 = var20_26;
                                        var11_11 = var21_27;
                                        continue block35;
lbl174: // 1 sources:
                                        var3_3 = var19_25;
                                        if (var19_25 == 0) ** GOTO lbl-1000
                                        if (!this.mEglHelper.createSurface()) ** GOTO lbl-1000
                                        var17_22 = GLSurfaceView.access$800();
                                        // MONITORENTER : var17_22
                                        this.mFinishedCreatingEglSurface = true;
                                        GLSurfaceView.access$800().notifyAll();
                                        // MONITOREXIT : var17_22
                                        var3_3 = 0;
                                        ** GOTO lbl-1000
lbl-1000: // 1 sources:
                                        {
                                            var17_22 = GLSurfaceView.access$800();
                                            // MONITORENTER : var17_22
                                        }
                                        this.mFinishedCreatingEglSurface = true;
                                        this.mSurfaceIsBad = true;
                                        GLSurfaceView.access$800().notifyAll();
                                        // MONITOREXIT : var17_22
                                        var3_3 = var19_25;
                                        var9_9 = var15_20;
                                        var10_10 = var20_26;
                                        var11_11 = var21_27;
                                        var12_12 = var18_24;
                                        continue block35;
lbl-1000: // 2 sources:
                                        {
                                            var24_30 = var4_4;
                                            if (var4_4 != 0) {
                                                var1_1 = (GL10)this.mEglHelper.createGL();
                                                var24_30 = 0;
                                            }
                                            var9_9 = var2_2;
                                            if (var2_2 == 0) ** GOTO lbl214
                                        }
                                        var17_22 = (GLSurfaceView)this.mGLSurfaceViewWeakRef.get();
                                        if (var17_22 == null) ** GOTO lbl213
                                        Trace.traceBegin(8L, "onSurfaceCreated");
                                        GLSurfaceView.access$1000((GLSurfaceView)var17_22).onSurfaceCreated(var1_1, this.mEglHelper.mEglConfig);
                                        {
                                            catch (Throwable var13_15) {
                                                Trace.traceEnd(8L);
                                                throw var13_15;
                                            }
                                        }
                                        Trace.traceEnd(8L);
lbl213: // 2 sources:
                                        var9_9 = 0;
lbl214: // 2 sources:
                                        if (var6_6 == 0) ** GOTO lbl227
                                        var17_22 = (GLSurfaceView)this.mGLSurfaceViewWeakRef.get();
                                        if (var17_22 == null) ** GOTO lbl226
                                        Trace.traceBegin(8L, "onSurfaceChanged");
                                        GLSurfaceView.access$1000((GLSurfaceView)var17_22).onSurfaceChanged(var1_1, var20_26, var21_27);
                                        {
                                            catch (Throwable var13_16) {
                                                Trace.traceEnd(8L);
                                                throw var13_16;
                                            }
                                        }
                                        Trace.traceEnd(8L);
lbl226: // 2 sources:
                                        var6_6 = 0;
lbl227: // 2 sources:
                                        if ((var17_22 = (GLSurfaceView)this.mGLSurfaceViewWeakRef.get()) == null) ** GOTO lbl241
                                        Trace.traceBegin(8L, "onDrawFrame");
                                        GLSurfaceView.access$1000((GLSurfaceView)var17_22).onDrawFrame(var1_1);
                                        if (var13_13 == null) break block60;
                                        var13_13.run();
                                        var13_13 = null;
                                        {
                                            catch (Throwable var13_17) {
                                                Trace.traceEnd(8L);
                                                throw var13_17;
                                            }
                                        }
                                    }
                                    Trace.traceEnd(8L);
lbl241: // 2 sources:
                                    if ((var2_2 = this.mEglHelper.swap()) == 12288) break block61;
                                    if (var2_2 == 12302) break block62;
                                    EglHelper.logEglErrorAsWarning("GLThread", "eglSwapBuffers", var2_2);
                                    var17_22 = GLSurfaceView.access$800();
                                    // MONITORENTER : var17_22
                                    this.mSurfaceIsBad = true;
                                    GLSurfaceView.access$800().notifyAll();
                                    break block61;
                                }
                                var5_5 = 1;
                            }
                            var19_25 = var7_7;
                            if (var7_7 != 0) {
                                var8_8 = 1;
                                var19_25 = 0;
                            }
                            var2_2 = var9_9;
                            var4_4 = var24_30;
                            var7_7 = var19_25;
                            var9_9 = var15_20;
                            var10_10 = var20_26;
                            var11_11 = var21_27;
                            var12_12 = var18_24;
                            continue block35;
                        }
                        var27_33 = var2_2;
                        var16_21 = var6_6;
                        var20_26 = var5_5;
                        var15_20 = var8_8;
                        var19_25 = var24_30;
                        var13_13 = var17_22;
                        if (var17_22 != null) {
                            Log.w("GLSurfaceView", "Warning, !readyToDraw() but waiting for draw finished! Early reporting draw finished.");
                            var17_22.run();
                            var13_13 = null;
                            var19_25 = var24_30;
                            var15_20 = var8_8;
                            var20_26 = var5_5;
                            var16_21 = var6_6;
                            var27_33 = var2_2;
                        }
                    }
                    GLSurfaceView.access$800().wait();
                    var2_2 = var27_33;
                    var6_6 = var16_21;
                    var5_5 = var20_26;
                    var16_21 = var25_31;
                    var8_8 = var15_20;
                    var15_20 = var26_32;
                    var9_9 = var19_25;
                } while (true);
                break;
            } while (true);
            catch (Throwable var17_23) {
                var13_13 = GLSurfaceView.access$800();
                // MONITORENTER : var13_13
                this.stopEglSurfaceLocked();
                this.stopEglContextLocked();
                // MONITOREXIT : var13_13
                throw var17_23;
            }
        }

        private boolean readyToDraw() {
            boolean bl = this.mPaused;
            boolean bl2 = true;
            if (bl || !this.mHasSurface || this.mSurfaceIsBad || this.mWidth <= 0 || this.mHeight <= 0 || !this.mRequestRender && this.mRenderMode != 1) {
                bl2 = false;
            }
            return bl2;
        }

        private void stopEglContextLocked() {
            if (this.mHaveEglContext) {
                this.mEglHelper.finish();
                this.mHaveEglContext = false;
                sGLThreadManager.releaseEglContextLocked(this);
            }
        }

        private void stopEglSurfaceLocked() {
            if (this.mHaveEglSurface) {
                this.mHaveEglSurface = false;
                this.mEglHelper.destroySurface();
            }
        }

        public boolean ableToDraw() {
            boolean bl = this.mHaveEglContext && this.mHaveEglSurface && this.readyToDraw();
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public int getRenderMode() {
            GLThreadManager gLThreadManager = sGLThreadManager;
            synchronized (gLThreadManager) {
                return this.mRenderMode;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onPause() {
            GLThreadManager gLThreadManager = sGLThreadManager;
            synchronized (gLThreadManager) {
                boolean bl;
                this.mRequestPaused = true;
                sGLThreadManager.notifyAll();
                while (!this.mExited && !(bl = this.mPaused)) {
                    try {
                        sGLThreadManager.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                    }
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onResume() {
            GLThreadManager gLThreadManager = sGLThreadManager;
            synchronized (gLThreadManager) {
                boolean bl;
                this.mRequestPaused = false;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                sGLThreadManager.notifyAll();
                while (!this.mExited && this.mPaused && !(bl = this.mRenderComplete)) {
                    try {
                        sGLThreadManager.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                    }
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onWindowResize(int n, int n2) {
            GLThreadManager gLThreadManager = sGLThreadManager;
            synchronized (gLThreadManager) {
                boolean bl;
                this.mWidth = n;
                this.mHeight = n2;
                this.mSizeChanged = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                if (Thread.currentThread() == this) {
                    return;
                }
                sGLThreadManager.notifyAll();
                while (!this.mExited && !this.mPaused && !this.mRenderComplete && (bl = this.ableToDraw())) {
                    try {
                        sGLThreadManager.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                    }
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void queueEvent(Runnable runnable) {
            if (runnable != null) {
                GLThreadManager gLThreadManager = sGLThreadManager;
                synchronized (gLThreadManager) {
                    this.mEventQueue.add(runnable);
                    sGLThreadManager.notifyAll();
                    return;
                }
            }
            throw new IllegalArgumentException("r must not be null");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void requestExitAndWait() {
            GLThreadManager gLThreadManager = sGLThreadManager;
            synchronized (gLThreadManager) {
                this.mShouldExit = true;
                sGLThreadManager.notifyAll();
                boolean bl;
                while (!(bl = this.mExited)) {
                    try {
                        sGLThreadManager.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        continue;
                    }
                    break;
                }
                return;
            }
        }

        public void requestReleaseEglContextLocked() {
            this.mShouldReleaseEglContext = true;
            sGLThreadManager.notifyAll();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void requestRender() {
            GLThreadManager gLThreadManager = sGLThreadManager;
            synchronized (gLThreadManager) {
                this.mRequestRender = true;
                sGLThreadManager.notifyAll();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void requestRenderAndNotify(Runnable runnable) {
            GLThreadManager gLThreadManager = sGLThreadManager;
            synchronized (gLThreadManager) {
                if (Thread.currentThread() == this) {
                    return;
                }
                this.mWantRenderNotification = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                this.mFinishDrawingRunnable = runnable;
                sGLThreadManager.notifyAll();
                return;
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            var1_1 = new StringBuilder();
            var1_1.append("GLThread ");
            var1_1.append(this.getId());
            this.setName(var1_1.toString());
            try {
                this.guardedRun();
lbl9: // 2 sources:
                do {
                    GLSurfaceView.access$800().threadExiting(this);
                    break;
                } while (true);
            }
            catch (Throwable var1_2) {
                GLSurfaceView.access$800().threadExiting(this);
                throw var1_2;
            }
            catch (InterruptedException var1_3) {
                ** continue;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void setRenderMode(int n) {
            if (n >= 0 && n <= 1) {
                GLThreadManager gLThreadManager = sGLThreadManager;
                synchronized (gLThreadManager) {
                    this.mRenderMode = n;
                    sGLThreadManager.notifyAll();
                    return;
                }
            }
            throw new IllegalArgumentException("renderMode");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void surfaceCreated() {
            GLThreadManager gLThreadManager = sGLThreadManager;
            synchronized (gLThreadManager) {
                boolean bl;
                this.mHasSurface = true;
                this.mFinishedCreatingEglSurface = false;
                sGLThreadManager.notifyAll();
                while (this.mWaitingForSurface && !this.mFinishedCreatingEglSurface && !(bl = this.mExited)) {
                    try {
                        sGLThreadManager.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                    }
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void surfaceDestroyed() {
            GLThreadManager gLThreadManager = sGLThreadManager;
            synchronized (gLThreadManager) {
                boolean bl;
                this.mHasSurface = false;
                sGLThreadManager.notifyAll();
                while (!this.mWaitingForSurface && !(bl = this.mExited)) {
                    try {
                        sGLThreadManager.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                    }
                }
                return;
            }
        }
    }

    private static class GLThreadManager {
        private static String TAG = "GLThreadManager";

        private GLThreadManager() {
        }

        public void releaseEglContextLocked(GLThread gLThread) {
            this.notifyAll();
        }

        public void threadExiting(GLThread gLThread) {
            synchronized (this) {
                gLThread.mExited = true;
                this.notifyAll();
                return;
            }
        }
    }

    public static interface GLWrapper {
        public GL wrap(GL var1);
    }

    static class LogWriter
    extends Writer {
        private StringBuilder mBuilder = new StringBuilder();

        LogWriter() {
        }

        private void flushBuilder() {
            if (this.mBuilder.length() > 0) {
                Log.v(GLSurfaceView.TAG, this.mBuilder.toString());
                StringBuilder stringBuilder = this.mBuilder;
                stringBuilder.delete(0, stringBuilder.length());
            }
        }

        @Override
        public void close() {
            this.flushBuilder();
        }

        @Override
        public void flush() {
            this.flushBuilder();
        }

        @Override
        public void write(char[] arrc, int n, int n2) {
            for (int i = 0; i < n2; ++i) {
                char c = arrc[n + i];
                if (c == '\n') {
                    this.flushBuilder();
                    continue;
                }
                this.mBuilder.append(c);
            }
        }
    }

    public static interface Renderer {
        public void onDrawFrame(GL10 var1);

        public void onSurfaceChanged(GL10 var1, int var2, int var3);

        public void onSurfaceCreated(GL10 var1, EGLConfig var2);
    }

    private class SimpleEGLConfigChooser
    extends ComponentSizeChooser {
        public SimpleEGLConfigChooser(boolean bl) {
            int n = bl ? 16 : 0;
            super(8, 8, 8, 0, n, 0);
        }
    }

}

