/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.legacy.CaptureCollector;
import android.hardware.camera2.legacy.LegacyCameraDevice;
import android.hardware.camera2.legacy.LegacyExceptionUtils;
import android.hardware.camera2.legacy.PerfMeasurement;
import android.hardware.camera2.legacy.RequestHolder;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Environment;
import android.os.SystemProperties;
import android.text.format.Time;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import java.io.File;
import java.io.Serializable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SurfaceTextureRenderer {
    private static final boolean DEBUG = false;
    private static final int EGL_COLOR_BITLENGTH = 8;
    private static final int EGL_RECORDABLE_ANDROID = 12610;
    private static final int FLIP_TYPE_BOTH = 3;
    private static final int FLIP_TYPE_HORIZONTAL = 1;
    private static final int FLIP_TYPE_NONE = 0;
    private static final int FLIP_TYPE_VERTICAL = 2;
    private static final int FLOAT_SIZE_BYTES = 4;
    private static final String FRAGMENT_SHADER = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
    private static final int GLES_VERSION = 2;
    private static final int GL_MATRIX_SIZE = 16;
    private static final String LEGACY_PERF_PROPERTY = "persist.camera.legacy_perf";
    private static final int PBUFFER_PIXEL_BYTES = 4;
    private static final String TAG = SurfaceTextureRenderer.class.getSimpleName();
    private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
    private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 20;
    private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
    private static final int VERTEX_POS_SIZE = 3;
    private static final String VERTEX_SHADER = "uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n";
    private static final int VERTEX_UV_SIZE = 2;
    private static final float[] sBothFlipTriangleVertices;
    private static final float[] sHorizontalFlipTriangleVertices;
    private static final float[] sRegularTriangleVertices;
    private static final float[] sVerticalFlipTriangleVertices;
    private FloatBuffer mBothFlipTriangleVertices;
    private EGLConfig mConfigs;
    private List<EGLSurfaceHolder> mConversionSurfaces = new ArrayList<EGLSurfaceHolder>();
    private EGLContext mEGLContext = EGL14.EGL_NO_CONTEXT;
    private EGLDisplay mEGLDisplay = EGL14.EGL_NO_DISPLAY;
    private final int mFacing;
    private FloatBuffer mHorizontalFlipTriangleVertices;
    private float[] mMVPMatrix = new float[16];
    private ByteBuffer mPBufferPixels;
    private PerfMeasurement mPerfMeasurer = null;
    private int mProgram;
    private FloatBuffer mRegularTriangleVertices;
    private float[] mSTMatrix = new float[16];
    private volatile SurfaceTexture mSurfaceTexture;
    private List<EGLSurfaceHolder> mSurfaces = new ArrayList<EGLSurfaceHolder>();
    private int mTextureID = 0;
    private FloatBuffer mVerticalFlipTriangleVertices;
    private int maPositionHandle;
    private int maTextureHandle;
    private int muMVPMatrixHandle;
    private int muSTMatrixHandle;

    static {
        sHorizontalFlipTriangleVertices = new float[]{-1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};
        sVerticalFlipTriangleVertices = new float[]{-1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f};
        sBothFlipTriangleVertices = new float[]{-1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f};
        sRegularTriangleVertices = new float[]{-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
    }

    public SurfaceTextureRenderer(int n) {
        this.mFacing = n;
        this.mRegularTriangleVertices = ByteBuffer.allocateDirect(sRegularTriangleVertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mRegularTriangleVertices.put(sRegularTriangleVertices).position(0);
        this.mHorizontalFlipTriangleVertices = ByteBuffer.allocateDirect(sHorizontalFlipTriangleVertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mHorizontalFlipTriangleVertices.put(sHorizontalFlipTriangleVertices).position(0);
        this.mVerticalFlipTriangleVertices = ByteBuffer.allocateDirect(sVerticalFlipTriangleVertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mVerticalFlipTriangleVertices.put(sVerticalFlipTriangleVertices).position(0);
        this.mBothFlipTriangleVertices = ByteBuffer.allocateDirect(sBothFlipTriangleVertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mBothFlipTriangleVertices.put(sBothFlipTriangleVertices).position(0);
        Matrix.setIdentityM(this.mSTMatrix, 0);
    }

    private void addGlTimestamp(long l) {
        PerfMeasurement perfMeasurement = this.mPerfMeasurer;
        if (perfMeasurement == null) {
            return;
        }
        perfMeasurement.addTimestamp(l);
    }

    private void beginGlTiming() {
        PerfMeasurement perfMeasurement = this.mPerfMeasurer;
        if (perfMeasurement == null) {
            return;
        }
        perfMeasurement.startTimer();
    }

    private void checkEglDrawError(String string2) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        if (EGL14.eglGetError() != 12299) {
            int n = EGL14.eglGetError();
            if (n == 12288) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(": EGL error: 0x");
            stringBuilder.append(Integer.toHexString(n));
            throw new IllegalStateException(stringBuilder.toString());
        }
        throw new LegacyExceptionUtils.BufferQueueAbandonedException();
    }

    private void checkEglError(String string2) {
        int n = EGL14.eglGetError();
        if (n == 12288) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(": EGL error: 0x");
        stringBuilder.append(Integer.toHexString(n));
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void checkGlDrawError(String string2) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        int n;
        boolean bl = false;
        boolean bl2 = false;
        while ((n = GLES20.glGetError()) != 0) {
            if (n == 1285) {
                bl = true;
                continue;
            }
            bl2 = true;
        }
        if (!bl2) {
            if (!bl) {
                return;
            }
            throw new LegacyExceptionUtils.BufferQueueAbandonedException();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(": GLES20 error: 0x");
        stringBuilder.append(Integer.toHexString(n));
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void checkGlError(String string2) {
        int n = GLES20.glGetError();
        if (n == 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(": GLES20 error: 0x");
        stringBuilder.append(Integer.toHexString(n));
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void clearState() {
        this.mSurfaces.clear();
        for (EGLSurfaceHolder eGLSurfaceHolder : this.mConversionSurfaces) {
            try {
                LegacyCameraDevice.disconnectSurface(eGLSurfaceHolder.surface);
            }
            catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                Log.w(TAG, "Surface abandoned, skipping...", bufferQueueAbandonedException);
            }
        }
        this.mConversionSurfaces.clear();
        this.mPBufferPixels = null;
        if (this.mSurfaceTexture != null) {
            this.mSurfaceTexture.release();
        }
        this.mSurfaceTexture = null;
    }

    private void configureEGLContext() {
        this.mEGLDisplay = EGL14.eglGetDisplay(0);
        if (this.mEGLDisplay != EGL14.EGL_NO_DISPLAY) {
            int[] arrn = new int[2];
            if (EGL14.eglInitialize(this.mEGLDisplay, arrn, 0, arrn, 1)) {
                EGLConfig[] arreGLConfig = new EGLConfig[1];
                arrn = new int[1];
                EGLDisplay eGLDisplay = this.mEGLDisplay;
                int n = arreGLConfig.length;
                EGL14.eglChooseConfig(eGLDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12352, 4, 12610, 1, 12339, 5, 12344}, 0, arreGLConfig, 0, n, arrn, 0);
                this.checkEglError("eglCreateContext RGB888+recordable ES2");
                this.mConfigs = arreGLConfig[0];
                this.mEGLContext = EGL14.eglCreateContext(this.mEGLDisplay, arreGLConfig[0], EGL14.EGL_NO_CONTEXT, new int[]{12440, 2, 12344}, 0);
                this.checkEglError("eglCreateContext");
                if (this.mEGLContext != EGL14.EGL_NO_CONTEXT) {
                    return;
                }
                throw new IllegalStateException("No EGLContext could be made");
            }
            throw new IllegalStateException("Cannot initialize EGL14");
        }
        throw new IllegalStateException("No EGL14 display");
    }

    private void configureEGLOutputSurfaces(Collection<EGLSurfaceHolder> object) {
        if (object != null && object.size() != 0) {
            Iterator<EGLSurfaceHolder> iterator = object.iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                ((EGLSurfaceHolder)object).eglSurface = EGL14.eglCreateWindowSurface(this.mEGLDisplay, this.mConfigs, ((EGLSurfaceHolder)object).surface, new int[]{12344}, 0);
                this.checkEglError("eglCreateWindowSurface");
            }
            return;
        }
        throw new IllegalStateException("No Surfaces were provided to draw to");
    }

    private void configureEGLPbufferSurfaces(Collection<EGLSurfaceHolder> object) {
        if (object != null && object.size() != 0) {
            int n = 0;
            Iterator<EGLSurfaceHolder> iterator = object.iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                int n2 = ((EGLSurfaceHolder)object).width * ((EGLSurfaceHolder)object).height;
                if (n2 > n) {
                    n = n2;
                }
                n2 = ((EGLSurfaceHolder)object).width;
                int n3 = ((EGLSurfaceHolder)object).height;
                ((EGLSurfaceHolder)object).eglSurface = EGL14.eglCreatePbufferSurface(this.mEGLDisplay, this.mConfigs, new int[]{12375, n2, 12374, n3, 12344}, 0);
                this.checkEglError("eglCreatePbufferSurface");
            }
            this.mPBufferPixels = ByteBuffer.allocateDirect(n * 4).order(ByteOrder.nativeOrder());
            return;
        }
        throw new IllegalStateException("No Surfaces were provided to draw to");
    }

    private int createProgram(String arrn, String string2) {
        int n = this.loadShader(35633, (String)arrn);
        if (n == 0) {
            return 0;
        }
        int n2 = this.loadShader(35632, string2);
        if (n2 == 0) {
            return 0;
        }
        int n3 = GLES20.glCreateProgram();
        this.checkGlError("glCreateProgram");
        if (n3 == 0) {
            Log.e(TAG, "Could not create program");
        }
        GLES20.glAttachShader(n3, n);
        this.checkGlError("glAttachShader");
        GLES20.glAttachShader(n3, n2);
        this.checkGlError("glAttachShader");
        GLES20.glLinkProgram(n3);
        arrn = new int[1];
        GLES20.glGetProgramiv(n3, 35714, arrn, 0);
        if (arrn[0] == 1) {
            return n3;
        }
        Log.e(TAG, "Could not link program: ");
        Log.e(TAG, GLES20.glGetProgramInfoLog(n3));
        GLES20.glDeleteProgram(n3);
        throw new IllegalStateException("Could not link program");
    }

    private void drawFrame(SurfaceTexture object, int n, int n2, int n3) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        this.checkGlError("onDrawFrame start");
        ((SurfaceTexture)object).getTransformMatrix(this.mSTMatrix);
        Matrix.setIdentityM(this.mMVPMatrix, 0);
        try {
            object = LegacyCameraDevice.getTextureSize((SurfaceTexture)object);
        }
        catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
            throw new IllegalStateException("Surface abandoned, skipping drawFrame...", bufferQueueAbandonedException);
        }
        float f = ((Size)object).getWidth();
        float f2 = ((Size)object).getHeight();
        if (!(f <= 0.0f) && !(f2 <= 0.0f)) {
            RectF rectF = new RectF(0.0f, 0.0f, f, f2);
            RectF rectF2 = new RectF(0.0f, 0.0f, n, n2);
            object = new android.graphics.Matrix();
            ((android.graphics.Matrix)object).setRectToRect(rectF2, rectF, Matrix.ScaleToFit.CENTER);
            ((android.graphics.Matrix)object).mapRect(rectF2);
            f = rectF.width() / rectF2.width();
            f2 = rectF.height() / rectF2.height();
            Matrix.scaleM(this.mMVPMatrix, 0, f, f2, 1.0f);
            GLES20.glViewport(0, 0, n, n2);
            GLES20.glUseProgram(this.mProgram);
            this.checkGlError("glUseProgram");
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, this.mTextureID);
            object = n3 != 1 ? (n3 != 2 ? (n3 != 3 ? this.mRegularTriangleVertices : this.mBothFlipTriangleVertices) : this.mVerticalFlipTriangleVertices) : this.mHorizontalFlipTriangleVertices;
            ((Buffer)object).position(0);
            GLES20.glVertexAttribPointer(this.maPositionHandle, 3, 5126, false, 20, (Buffer)object);
            this.checkGlError("glVertexAttribPointer maPosition");
            GLES20.glEnableVertexAttribArray(this.maPositionHandle);
            this.checkGlError("glEnableVertexAttribArray maPositionHandle");
            ((Buffer)object).position(3);
            GLES20.glVertexAttribPointer(this.maTextureHandle, 2, 5126, false, 20, (Buffer)object);
            this.checkGlError("glVertexAttribPointer maTextureHandle");
            GLES20.glEnableVertexAttribArray(this.maTextureHandle);
            this.checkGlError("glEnableVertexAttribArray maTextureHandle");
            GLES20.glUniformMatrix4fv(this.muMVPMatrixHandle, 1, false, this.mMVPMatrix, 0);
            GLES20.glUniformMatrix4fv(this.muSTMatrixHandle, 1, false, this.mSTMatrix, 0);
            GLES20.glDrawArrays(5, 0, 4);
            this.checkGlDrawError("glDrawArrays");
            return;
        }
        throw new IllegalStateException("Illegal intermediate texture with dimension of 0");
    }

    private void dumpGlTiming() {
        if (this.mPerfMeasurer == null) {
            return;
        }
        Serializable serializable = new File(Environment.getExternalStorageDirectory(), "CameraLegacy");
        if (!((File)serializable).exists() && !((File)serializable).mkdirs()) {
            Log.e(TAG, "Failed to create directory for data dump");
            return;
        }
        serializable = new StringBuilder(((File)serializable).getPath());
        ((StringBuilder)serializable).append(File.separator);
        ((StringBuilder)serializable).append("durations_");
        Time iterator2 = new Time();
        iterator2.setToNow();
        ((StringBuilder)serializable).append(iterator2.format2445());
        ((StringBuilder)serializable).append("_S");
        for (EGLSurfaceHolder eGLSurfaceHolder : this.mSurfaces) {
            ((StringBuilder)serializable).append(String.format("_%d_%d", eGLSurfaceHolder.width, eGLSurfaceHolder.height));
        }
        ((StringBuilder)serializable).append("_C");
        for (EGLSurfaceHolder eGLSurfaceHolder : this.mConversionSurfaces) {
            ((StringBuilder)serializable).append(String.format("_%d_%d", eGLSurfaceHolder.width, eGLSurfaceHolder.height));
        }
        ((StringBuilder)serializable).append(".txt");
        this.mPerfMeasurer.dumpPerformanceData(((StringBuilder)serializable).toString());
    }

    private void endGlTiming() {
        PerfMeasurement perfMeasurement = this.mPerfMeasurer;
        if (perfMeasurement == null) {
            return;
        }
        perfMeasurement.stopTimer();
    }

    private int getTextureId() {
        return this.mTextureID;
    }

    private void initializeGLState() {
        this.mProgram = this.createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
        int n = this.mProgram;
        if (n != 0) {
            this.maPositionHandle = GLES20.glGetAttribLocation(n, "aPosition");
            this.checkGlError("glGetAttribLocation aPosition");
            if (this.maPositionHandle != -1) {
                this.maTextureHandle = GLES20.glGetAttribLocation(this.mProgram, "aTextureCoord");
                this.checkGlError("glGetAttribLocation aTextureCoord");
                if (this.maTextureHandle != -1) {
                    this.muMVPMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uMVPMatrix");
                    this.checkGlError("glGetUniformLocation uMVPMatrix");
                    if (this.muMVPMatrixHandle != -1) {
                        this.muSTMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uSTMatrix");
                        this.checkGlError("glGetUniformLocation uSTMatrix");
                        if (this.muSTMatrixHandle != -1) {
                            int[] arrn = new int[1];
                            GLES20.glGenTextures(1, arrn, 0);
                            this.mTextureID = arrn[0];
                            GLES20.glBindTexture(36197, this.mTextureID);
                            this.checkGlError("glBindTexture mTextureID");
                            GLES20.glTexParameterf(36197, 10241, 9728.0f);
                            GLES20.glTexParameterf(36197, 10240, 9729.0f);
                            GLES20.glTexParameteri(36197, 10242, 33071);
                            GLES20.glTexParameteri(36197, 10243, 33071);
                            this.checkGlError("glTexParameter");
                            return;
                        }
                        throw new IllegalStateException("Could not get attrib location for uSTMatrix");
                    }
                    throw new IllegalStateException("Could not get attrib location for uMVPMatrix");
                }
                throw new IllegalStateException("Could not get attrib location for aTextureCoord");
            }
            throw new IllegalStateException("Could not get attrib location for aPosition");
        }
        throw new IllegalStateException("failed creating program");
    }

    private int loadShader(int n, String object) {
        int n2 = GLES20.glCreateShader(n);
        CharSequence charSequence = new StringBuilder();
        charSequence.append("glCreateShader type=");
        charSequence.append(n);
        this.checkGlError(charSequence.toString());
        GLES20.glShaderSource(n2, (String)object);
        GLES20.glCompileShader(n2);
        object = new int[1];
        GLES20.glGetShaderiv(n2, 35713, (int[])object, 0);
        if (object[0] != 0) {
            return n2;
        }
        charSequence = TAG;
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not compile shader ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(":");
        Log.e((String)charSequence, ((StringBuilder)object).toString());
        charSequence = TAG;
        object = new StringBuilder();
        ((StringBuilder)object).append(" ");
        ((StringBuilder)object).append(GLES20.glGetShaderInfoLog(n2));
        Log.e((String)charSequence, ((StringBuilder)object).toString());
        GLES20.glDeleteShader(n2);
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not compile shader ");
        ((StringBuilder)object).append(n);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    private void makeCurrent(EGLSurface eGLSurface) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        EGL14.eglMakeCurrent(this.mEGLDisplay, eGLSurface, eGLSurface, this.mEGLContext);
        this.checkEglDrawError("makeCurrent");
    }

    private void releaseEGLContext() {
        if (this.mEGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGLSurfaceHolder eGLSurfaceHolder;
            EGL14.eglMakeCurrent(this.mEGLDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            this.dumpGlTiming();
            Object object = this.mSurfaces;
            if (object != null) {
                object = object.iterator();
                while (object.hasNext()) {
                    eGLSurfaceHolder = (EGLSurfaceHolder)object.next();
                    if (eGLSurfaceHolder.eglSurface == null) continue;
                    EGL14.eglDestroySurface(this.mEGLDisplay, eGLSurfaceHolder.eglSurface);
                }
            }
            if ((object = this.mConversionSurfaces) != null) {
                object = object.iterator();
                while (object.hasNext()) {
                    eGLSurfaceHolder = (EGLSurfaceHolder)object.next();
                    if (eGLSurfaceHolder.eglSurface == null) continue;
                    EGL14.eglDestroySurface(this.mEGLDisplay, eGLSurfaceHolder.eglSurface);
                }
            }
            EGL14.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
            EGL14.eglReleaseThread();
            EGL14.eglTerminate(this.mEGLDisplay);
        }
        this.mConfigs = null;
        this.mEGLDisplay = EGL14.EGL_NO_DISPLAY;
        this.mEGLContext = EGL14.EGL_NO_CONTEXT;
        this.clearState();
    }

    private void setupGlTiming() {
        if (PerfMeasurement.isGlTimingSupported()) {
            Log.d(TAG, "Enabling GL performance measurement");
            this.mPerfMeasurer = new PerfMeasurement();
        } else {
            Log.d(TAG, "GL performance measurement not supported on this device");
            this.mPerfMeasurer = null;
        }
    }

    private boolean swapBuffers(EGLSurface object) throws LegacyExceptionUtils.BufferQueueAbandonedException {
        boolean bl = EGL14.eglSwapBuffers(this.mEGLDisplay, (EGLSurface)object);
        int n = EGL14.eglGetError();
        if (n != 12288) {
            if (n != 12299 && n != 12301) {
                object = new StringBuilder();
                ((StringBuilder)object).append("swapBuffers: EGL error: 0x");
                ((StringBuilder)object).append(Integer.toHexString(n));
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            throw new LegacyExceptionUtils.BufferQueueAbandonedException();
        }
        return bl;
    }

    public void cleanupEGLContext() {
        this.releaseEGLContext();
    }

    public void configureSurfaces(Collection<Pair<Surface, Size>> object) {
        this.releaseEGLContext();
        if (object != null && object.size() != 0) {
            object = object.iterator();
            while (object.hasNext()) {
                Object object2 = (Pair)object.next();
                Surface surface = (Surface)((Pair)object2).first;
                Size size = (Size)((Pair)object2).second;
                try {
                    object2 = new EGLSurfaceHolder();
                    ((EGLSurfaceHolder)object2).surface = surface;
                    ((EGLSurfaceHolder)object2).width = size.getWidth();
                    ((EGLSurfaceHolder)object2).height = size.getHeight();
                    if (LegacyCameraDevice.needsConversion(surface)) {
                        this.mConversionSurfaces.add((EGLSurfaceHolder)object2);
                        LegacyCameraDevice.connectSurface(surface);
                        continue;
                    }
                    this.mSurfaces.add((EGLSurfaceHolder)object2);
                }
                catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                    Log.w(TAG, "Surface abandoned, skipping configuration... ", bufferQueueAbandonedException);
                }
            }
            this.configureEGLContext();
            if (this.mSurfaces.size() > 0) {
                this.configureEGLOutputSurfaces(this.mSurfaces);
            }
            if (this.mConversionSurfaces.size() > 0) {
                this.configureEGLPbufferSurfaces(this.mConversionSurfaces);
            }
            try {
                object = this.mSurfaces.size() > 0 ? this.mSurfaces.get((int)0).eglSurface : this.mConversionSurfaces.get((int)0).eglSurface;
                this.makeCurrent((EGLSurface)object);
            }
            catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                Log.w(TAG, "Surface abandoned, skipping configuration... ", bufferQueueAbandonedException);
            }
            this.initializeGLState();
            this.mSurfaceTexture = new SurfaceTexture(this.getTextureId());
            if (SystemProperties.getBoolean(LEGACY_PERF_PROPERTY, false)) {
                this.setupGlTiming();
            }
            return;
        }
        Log.w(TAG, "No output surfaces configured for GL drawing.");
    }

    public void drawIntoSurfaces(CaptureCollector captureCollector) {
        Collection<Object> collection = this.mSurfaces;
        if (collection != null && collection.size() != 0 || (collection = this.mConversionSurfaces) != null && collection.size() != 0) {
            int n;
            int n2;
            int n3;
            SurfaceTexture surfaceTexture;
            boolean bl = captureCollector.hasPendingPreviewCaptures();
            this.checkGlError("before updateTexImage");
            if (bl) {
                this.beginGlTiming();
            }
            this.mSurfaceTexture.updateTexImage();
            long l = this.mSurfaceTexture.getTimestamp();
            Pair<RequestHolder, Long> pair = captureCollector.previewCaptured(l);
            if (pair == null) {
                if (bl) {
                    this.endGlTiming();
                }
                return;
            }
            RequestHolder requestHolder = (RequestHolder)pair.first;
            Iterator<EGLSurfaceHolder> iterator = requestHolder.getHolderTargets();
            if (bl) {
                this.addGlTimestamp(l);
            }
            collection = new ArrayList<EGLSurfaceHolder>();
            try {
                iterator = LegacyCameraDevice.getSurfaceIds(iterator);
                collection = iterator;
            }
            catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                Log.w(TAG, "Surface abandoned, dropping frame. ", bufferQueueAbandonedException);
                requestHolder.setOutputAbandoned();
            }
            for (EGLSurfaceHolder eGLSurfaceHolder : this.mSurfaces) {
                if (!LegacyCameraDevice.containsSurfaceId(eGLSurfaceHolder.surface, collection)) continue;
                LegacyCameraDevice.setSurfaceDimens(eGLSurfaceHolder.surface, eGLSurfaceHolder.width, eGLSurfaceHolder.height);
                this.makeCurrent(eGLSurfaceHolder.eglSurface);
                LegacyCameraDevice.setNextTimestamp(eGLSurfaceHolder.surface, (Long)pair.second);
                surfaceTexture = this.mSurfaceTexture;
                n3 = eGLSurfaceHolder.width;
                n2 = eGLSurfaceHolder.height;
                n = this.mFacing == 0 ? 1 : 0;
                try {
                    this.drawFrame(surfaceTexture, n3, n2, n);
                    this.swapBuffers(eGLSurfaceHolder.eglSurface);
                }
                catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                    Log.w(TAG, "Surface abandoned, dropping frame. ", bufferQueueAbandonedException);
                    requestHolder.setOutputAbandoned();
                }
            }
            for (EGLSurfaceHolder eGLSurfaceHolder : this.mConversionSurfaces) {
                if (!LegacyCameraDevice.containsSurfaceId(eGLSurfaceHolder.surface, collection)) continue;
                try {
                    this.makeCurrent(eGLSurfaceHolder.eglSurface);
                    surfaceTexture = this.mSurfaceTexture;
                    n2 = eGLSurfaceHolder.width;
                    n3 = eGLSurfaceHolder.height;
                    n = this.mFacing == 0 ? 3 : 2;
                }
                catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                    throw new IllegalStateException("Surface abandoned, skipping drawFrame...", bufferQueueAbandonedException);
                }
                this.drawFrame(surfaceTexture, n2, n3, n);
                this.mPBufferPixels.clear();
                GLES20.glReadPixels(0, 0, eGLSurfaceHolder.width, eGLSurfaceHolder.height, 6408, 5121, this.mPBufferPixels);
                this.checkGlError("glReadPixels");
                try {
                    n = LegacyCameraDevice.detectSurfaceType(eGLSurfaceHolder.surface);
                    LegacyCameraDevice.setSurfaceDimens(eGLSurfaceHolder.surface, eGLSurfaceHolder.width, eGLSurfaceHolder.height);
                    LegacyCameraDevice.setNextTimestamp(eGLSurfaceHolder.surface, (Long)pair.second);
                    LegacyCameraDevice.produceFrame(eGLSurfaceHolder.surface, this.mPBufferPixels.array(), eGLSurfaceHolder.width, eGLSurfaceHolder.height, n);
                }
                catch (LegacyExceptionUtils.BufferQueueAbandonedException bufferQueueAbandonedException) {
                    Log.w(TAG, "Surface abandoned, dropping frame. ", bufferQueueAbandonedException);
                    requestHolder.setOutputAbandoned();
                }
            }
            captureCollector.previewProduced();
            if (bl) {
                this.endGlTiming();
            }
            return;
        }
    }

    public void flush() {
        Log.e(TAG, "Flush not yet implemented.");
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.mSurfaceTexture;
    }

    private class EGLSurfaceHolder {
        EGLSurface eglSurface;
        int height;
        Surface surface;
        int width;

        private EGLSurfaceHolder() {
        }
    }

}

