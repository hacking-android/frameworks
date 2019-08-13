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
import android.opengl.Matrix;
import android.os.ConditionVariable;
import android.util.Log;

public class SurfaceTextureSource
extends Filter {
    private static final String TAG = "SurfaceTextureSource";
    private static final boolean mLogVerbose;
    private static final float[] mSourceCoords;
    @GenerateFieldPort(hasDefault=true, name="closeOnTimeout")
    private boolean mCloseOnTimeout = false;
    private boolean mFirstFrame;
    private ShaderProgram mFrameExtractor;
    private float[] mFrameTransform = new float[16];
    @GenerateFieldPort(name="height")
    private int mHeight;
    private float[] mMappedCoords = new float[16];
    private GLFrame mMediaFrame;
    private ConditionVariable mNewFrameAvailable = new ConditionVariable();
    private MutableFrameFormat mOutputFormat;
    private final String mRenderShader;
    @GenerateFinalPort(name="sourceListener")
    private SurfaceTextureSourceListener mSourceListener;
    private SurfaceTexture mSurfaceTexture;
    @GenerateFieldPort(hasDefault=true, name="waitForNewFrame")
    private boolean mWaitForNewFrame = true;
    @GenerateFieldPort(hasDefault=true, name="waitTimeout")
    private int mWaitTimeout = 1000;
    @GenerateFieldPort(name="width")
    private int mWidth;
    private SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener = new SurfaceTexture.OnFrameAvailableListener(){

        @Override
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            if (mLogVerbose) {
                Log.v(SurfaceTextureSource.TAG, "New frame from SurfaceTexture");
            }
            SurfaceTextureSource.this.mNewFrameAvailable.open();
        }
    };

    static {
        mSourceCoords = new float[]{0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};
        mLogVerbose = Log.isLoggable(TAG, 2);
    }

    public SurfaceTextureSource(String string2) {
        super(string2);
        this.mRenderShader = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nuniform samplerExternalOES tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n}\n";
    }

    private void createFormats() {
        this.mOutputFormat = ImageFormat.create(this.mWidth, this.mHeight, 3, 3);
    }

    @Override
    public void close(FilterContext filterContext) {
        if (mLogVerbose) {
            Log.v(TAG, "SurfaceTextureSource closed");
        }
        this.mSourceListener.onSurfaceTextureSourceReady(null);
        this.mSurfaceTexture.release();
        this.mSurfaceTexture = null;
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
        if (string2.equals("width") || string2.equals("height")) {
            this.mOutputFormat.setDimensions(this.mWidth, this.mHeight);
        }
    }

    @Override
    public void open(FilterContext filterContext) {
        if (mLogVerbose) {
            Log.v(TAG, "Opening SurfaceTextureSource");
        }
        this.mSurfaceTexture = new SurfaceTexture(this.mMediaFrame.getTextureId());
        this.mSurfaceTexture.setOnFrameAvailableListener(this.onFrameAvailableListener);
        this.mSourceListener.onSurfaceTextureSourceReady(this.mSurfaceTexture);
        this.mFirstFrame = true;
    }

    @Override
    protected void prepare(FilterContext filterContext) {
        if (mLogVerbose) {
            Log.v(TAG, "Preparing SurfaceTextureSource");
        }
        this.createFormats();
        this.mMediaFrame = (GLFrame)filterContext.getFrameManager().newBoundFrame(this.mOutputFormat, 104, 0L);
        this.mFrameExtractor = new ShaderProgram(filterContext, "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nuniform samplerExternalOES tex_sampler_0;\nvarying vec2 v_texcoord;\nvoid main() {\n  gl_FragColor = texture2D(tex_sampler_0, v_texcoord);\n}\n");
    }

    @Override
    public void process(FilterContext object) {
        if (mLogVerbose) {
            Log.v(TAG, "Processing new frame");
        }
        if (this.mWaitForNewFrame || this.mFirstFrame) {
            int n = this.mWaitTimeout;
            if (n != 0) {
                if (!this.mNewFrameAvailable.block(n)) {
                    if (this.mCloseOnTimeout) {
                        if (mLogVerbose) {
                            Log.v(TAG, "Timeout waiting for a new frame. Closing.");
                        }
                        this.closeOutputPort("video");
                        return;
                    }
                    throw new RuntimeException("Timeout waiting for new frame");
                }
            } else {
                this.mNewFrameAvailable.block();
            }
            this.mNewFrameAvailable.close();
            this.mFirstFrame = false;
        }
        this.mSurfaceTexture.updateTexImage();
        this.mSurfaceTexture.getTransformMatrix(this.mFrameTransform);
        Matrix.multiplyMM(this.mMappedCoords, 0, this.mFrameTransform, 0, mSourceCoords, 0);
        ShaderProgram shaderProgram = this.mFrameExtractor;
        float[] arrf = this.mMappedCoords;
        shaderProgram.setSourceRegion(arrf[0], arrf[1], arrf[4], arrf[5], arrf[8], arrf[9], arrf[12], arrf[13]);
        object = ((FilterContext)object).getFrameManager().newFrame(this.mOutputFormat);
        this.mFrameExtractor.process(this.mMediaFrame, (Frame)object);
        ((Frame)object).setTimestamp(this.mSurfaceTexture.getTimestamp());
        this.pushOutput("video", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.addOutputPort("video", ImageFormat.create(3, 3));
    }

    @Override
    public void tearDown(FilterContext object) {
        object = this.mMediaFrame;
        if (object != null) {
            ((Frame)object).release();
        }
    }

    public static interface SurfaceTextureSourceListener {
        public void onSurfaceTextureSourceReady(SurfaceTexture var1);
    }

}

