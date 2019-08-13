/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.videosrc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLFrame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.Matrix;
import android.util.Log;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class CameraSource
extends Filter {
    private static final int NEWFRAME_TIMEOUT = 100;
    private static final int NEWFRAME_TIMEOUT_REPEAT = 10;
    private static final String TAG = "CameraSource";
    private static final String mFrameShader = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nuniform samplerExternalOES tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n}\n";
    private static final float[] mSourceCoords = new float[]{0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};
    private Camera mCamera;
    private GLFrame mCameraFrame;
    @GenerateFieldPort(hasDefault=true, name="id")
    private int mCameraId = 0;
    private Camera.Parameters mCameraParameters;
    private float[] mCameraTransform = new float[16];
    @GenerateFieldPort(hasDefault=true, name="framerate")
    private int mFps = 30;
    private ShaderProgram mFrameExtractor;
    @GenerateFieldPort(hasDefault=true, name="height")
    private int mHeight = 240;
    private final boolean mLogVerbose = Log.isLoggable("CameraSource", 2);
    private float[] mMappedCoords = new float[16];
    private boolean mNewFrameAvailable;
    private MutableFrameFormat mOutputFormat;
    private SurfaceTexture mSurfaceTexture;
    @GenerateFinalPort(hasDefault=true, name="waitForNewFrame")
    private boolean mWaitForNewFrame = true;
    @GenerateFieldPort(hasDefault=true, name="width")
    private int mWidth = 320;
    private SurfaceTexture.OnFrameAvailableListener onCameraFrameAvailableListener = new SurfaceTexture.OnFrameAvailableListener(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            if (CameraSource.this.mLogVerbose) {
                Log.v(CameraSource.TAG, "New frame from camera");
            }
            CameraSource cameraSource = CameraSource.this;
            synchronized (cameraSource) {
                CameraSource.this.mNewFrameAvailable = true;
                CameraSource.this.notify();
                return;
            }
        }
    };

    public CameraSource(String string2) {
        super(string2);
    }

    private void createFormats() {
        this.mOutputFormat = ImageFormat.create(this.mWidth, this.mHeight, 3, 3);
    }

    private int[] findClosestFpsRange(int n, Camera.Parameters object) {
        Object object2 = object.getSupportedPreviewFpsRange();
        object = object2.get(0);
        Iterator<int[]> iterator = object2.iterator();
        while (iterator.hasNext()) {
            int[] arrn = iterator.next();
            object2 = object;
            if (arrn[0] < n * 1000) {
                object2 = object;
                if (arrn[1] > n * 1000) {
                    object2 = object;
                    if (arrn[0] > object[0]) {
                        object2 = object;
                        if (arrn[1] < object[1]) {
                            object2 = arrn;
                        }
                    }
                }
            }
            object = object2;
        }
        if (this.mLogVerbose) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Requested fps: ");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(".Closest frame rate range: [");
            ((StringBuilder)object2).append((double)object[0] / 1000.0);
            ((StringBuilder)object2).append(",");
            ((StringBuilder)object2).append((double)object[1] / 1000.0);
            ((StringBuilder)object2).append("]");
            Log.v(TAG, ((StringBuilder)object2).toString());
        }
        return object;
    }

    private int[] findClosestSize(int n, int n2, Camera.Parameters object) {
        int n3;
        object = ((Camera.Parameters)object).getSupportedPreviewSizes();
        int n4 = -1;
        int n5 = -1;
        int n6 = ((Camera.Size)object.get((int)0)).width;
        int n7 = ((Camera.Size)object.get((int)0)).height;
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            object = (Camera.Size)iterator.next();
            int n8 = n4;
            n3 = n5;
            if (((Camera.Size)object).width <= n) {
                n8 = n4;
                n3 = n5;
                if (((Camera.Size)object).height <= n2) {
                    n8 = n4;
                    n3 = n5;
                    if (((Camera.Size)object).width >= n4) {
                        n8 = n4;
                        n3 = n5;
                        if (((Camera.Size)object).height >= n5) {
                            n8 = ((Camera.Size)object).width;
                            n3 = ((Camera.Size)object).height;
                        }
                    }
                }
            }
            int n9 = n6;
            int n10 = n7;
            if (((Camera.Size)object).width < n6) {
                n9 = n6;
                n10 = n7;
                if (((Camera.Size)object).height < n7) {
                    n9 = ((Camera.Size)object).width;
                    n10 = ((Camera.Size)object).height;
                }
            }
            n4 = n8;
            n5 = n3;
            n6 = n9;
            n7 = n10;
        }
        n3 = n4;
        if (n4 == -1) {
            n5 = n7;
            n3 = n6;
        }
        if (this.mLogVerbose) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Requested resolution: (");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append("). Closest match: (");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(n5);
            ((StringBuilder)object).append(").");
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        return new int[]{n3, n5};
    }

    @Override
    public void close(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Closing");
        }
        this.mCamera.release();
        this.mCamera = null;
        this.mSurfaceTexture.release();
        this.mSurfaceTexture = null;
    }

    @Override
    public void fieldPortValueUpdated(String arrn, FilterContext filterContext) {
        if (arrn.equals("framerate")) {
            this.getCameraParameters();
            arrn = this.findClosestFpsRange(this.mFps, this.mCameraParameters);
            this.mCameraParameters.setPreviewFpsRange(arrn[0], arrn[1]);
            this.mCamera.setParameters(this.mCameraParameters);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Camera.Parameters getCameraParameters() {
        synchronized (this) {
            boolean bl = false;
            if (this.mCameraParameters == null) {
                if (this.mCamera == null) {
                    this.mCamera = Camera.open(this.mCameraId);
                    bl = true;
                }
                this.mCameraParameters = this.mCamera.getParameters();
                if (bl) {
                    this.mCamera.release();
                    this.mCamera = null;
                }
            }
            int[] arrn = this.findClosestSize(this.mWidth, this.mHeight, this.mCameraParameters);
            this.mWidth = arrn[0];
            this.mHeight = arrn[1];
            this.mCameraParameters.setPreviewSize(this.mWidth, this.mHeight);
            int[] arrn2 = this.findClosestFpsRange(this.mFps, this.mCameraParameters);
            this.mCameraParameters.setPreviewFpsRange(arrn2[0], arrn2[1]);
            return this.mCameraParameters;
        }
    }

    @Override
    public void open(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Opening");
        }
        this.mCamera = Camera.open(this.mCameraId);
        this.getCameraParameters();
        this.mCamera.setParameters(this.mCameraParameters);
        this.createFormats();
        this.mCameraFrame = (GLFrame)filterContext.getFrameManager().newBoundFrame(this.mOutputFormat, 104, 0L);
        this.mSurfaceTexture = new SurfaceTexture(this.mCameraFrame.getTextureId());
        try {
            this.mCamera.setPreviewTexture(this.mSurfaceTexture);
            this.mSurfaceTexture.setOnFrameAvailableListener(this.onCameraFrameAvailableListener);
            this.mNewFrameAvailable = false;
            this.mCamera.startPreview();
            return;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not bind camera surface texture: ");
            stringBuilder.append(iOException.getMessage());
            stringBuilder.append("!");
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    @Override
    public void prepare(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Preparing");
        }
        this.mFrameExtractor = new ShaderProgram(filterContext, mFrameShader);
    }

    @Override
    public void process(FilterContext object) {
        Object object2;
        if (this.mLogVerbose) {
            Log.v(TAG, "Processing new frame");
        }
        if (this.mWaitForNewFrame) {
            while (!this.mNewFrameAvailable) {
                if (10 != 0) {
                    try {
                        this.wait(100L);
                    }
                    catch (InterruptedException interruptedException) {
                        if (!this.mLogVerbose) continue;
                        Log.v(TAG, "Interrupted while waiting for new frame");
                    }
                    continue;
                }
                throw new RuntimeException("Timeout waiting for new frame");
            }
            this.mNewFrameAvailable = false;
            if (this.mLogVerbose) {
                Log.v(TAG, "Got new frame");
            }
        }
        this.mSurfaceTexture.updateTexImage();
        if (this.mLogVerbose) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Using frame extractor in thread: ");
            ((StringBuilder)object2).append(Thread.currentThread());
            Log.v(TAG, ((StringBuilder)object2).toString());
        }
        this.mSurfaceTexture.getTransformMatrix(this.mCameraTransform);
        Matrix.multiplyMM(this.mMappedCoords, 0, this.mCameraTransform, 0, mSourceCoords, 0);
        object2 = this.mFrameExtractor;
        float[] arrf = this.mMappedCoords;
        ((ShaderProgram)object2).setSourceRegion(arrf[0], arrf[1], arrf[4], arrf[5], arrf[8], arrf[9], arrf[12], arrf[13]);
        object = ((FilterContext)object).getFrameManager().newFrame(this.mOutputFormat);
        this.mFrameExtractor.process(this.mCameraFrame, (Frame)object);
        long l = this.mSurfaceTexture.getTimestamp();
        if (this.mLogVerbose) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Timestamp: ");
            ((StringBuilder)object2).append((double)l / 1.0E9);
            ((StringBuilder)object2).append(" s");
            Log.v(TAG, ((StringBuilder)object2).toString());
        }
        ((Frame)object).setTimestamp(l);
        this.pushOutput("video", (Frame)object);
        ((Frame)object).release();
        if (this.mLogVerbose) {
            Log.v(TAG, "Done processing new frame");
        }
    }

    public void setCameraParameters(Camera.Parameters parameters) {
        synchronized (this) {
            parameters.setPreviewSize(this.mWidth, this.mHeight);
            this.mCameraParameters = parameters;
            if (this.isOpen()) {
                this.mCamera.setParameters(this.mCameraParameters);
            }
            return;
        }
    }

    @Override
    public void setupPorts() {
        this.addOutputPort("video", ImageFormat.create(3, 3));
    }

    @Override
    public void tearDown(FilterContext object) {
        object = this.mCameraFrame;
        if (object != null) {
            ((Frame)object).release();
        }
    }

}

