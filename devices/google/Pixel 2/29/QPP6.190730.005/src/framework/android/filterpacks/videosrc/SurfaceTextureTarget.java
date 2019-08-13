/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.videosrc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLEnvironment;
import android.filterfw.core.GLFrame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;
import android.graphics.SurfaceTexture;
import android.util.Log;

public class SurfaceTextureTarget
extends Filter {
    private static final String TAG = "SurfaceTextureTarget";
    private final int RENDERMODE_CUSTOMIZE;
    private final int RENDERMODE_FILL_CROP;
    private final int RENDERMODE_FIT;
    private final int RENDERMODE_STRETCH;
    private float mAspectRatio = 1.0f;
    private boolean mLogVerbose = Log.isLoggable("SurfaceTextureTarget", 2);
    private ShaderProgram mProgram;
    private int mRenderMode = 1;
    @GenerateFieldPort(hasDefault=true, name="renderMode")
    private String mRenderModeString;
    private GLFrame mScreen;
    @GenerateFinalPort(name="height")
    private int mScreenHeight;
    @GenerateFinalPort(name="width")
    private int mScreenWidth;
    @GenerateFieldPort(hasDefault=true, name="sourceQuad")
    private Quad mSourceQuad = new Quad(new Point(0.0f, 1.0f), new Point(1.0f, 1.0f), new Point(0.0f, 0.0f), new Point(1.0f, 0.0f));
    private int mSurfaceId;
    @GenerateFinalPort(name="surfaceTexture")
    private SurfaceTexture mSurfaceTexture;
    @GenerateFieldPort(hasDefault=true, name="targetQuad")
    private Quad mTargetQuad = new Quad(new Point(0.0f, 0.0f), new Point(1.0f, 0.0f), new Point(0.0f, 1.0f), new Point(1.0f, 1.0f));

    public SurfaceTextureTarget(String string2) {
        super(string2);
        this.RENDERMODE_STRETCH = 0;
        this.RENDERMODE_FIT = 1;
        this.RENDERMODE_FILL_CROP = 2;
        this.RENDERMODE_CUSTOMIZE = 3;
    }

    private void updateTargetRect() {
        int n;
        int n2;
        StringBuilder stringBuilder;
        if (this.mLogVerbose) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("updateTargetRect. Thread: ");
            stringBuilder.append(Thread.currentThread());
            Log.v(TAG, stringBuilder.toString());
        }
        if ((n = this.mScreenWidth) > 0 && (n2 = this.mScreenHeight) > 0 && this.mProgram != null) {
            float f = (float)n / (float)n2;
            float f2 = f / this.mAspectRatio;
            if (this.mLogVerbose) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("UTR. screen w = ");
                stringBuilder.append((float)this.mScreenWidth);
                stringBuilder.append(" x screen h = ");
                stringBuilder.append((float)this.mScreenHeight);
                stringBuilder.append(" Screen AR: ");
                stringBuilder.append(f);
                stringBuilder.append(", frame AR: ");
                stringBuilder.append(this.mAspectRatio);
                stringBuilder.append(", relative AR: ");
                stringBuilder.append(f2);
                Log.v(TAG, stringBuilder.toString());
            }
            if (f2 == 1.0f && this.mRenderMode != 3) {
                this.mProgram.setTargetRect(0.0f, 0.0f, 1.0f, 1.0f);
                this.mProgram.setClearsOutput(false);
            } else {
                n2 = this.mRenderMode;
                if (n2 != 0) {
                    if (n2 != 1) {
                        if (n2 != 2) {
                            if (n2 == 3) {
                                this.mProgram.setSourceRegion(this.mSourceQuad);
                            }
                        } else {
                            if (f2 > 1.0f) {
                                this.mTargetQuad.p0.set(0.0f, 0.5f - f2 * 0.5f);
                                this.mTargetQuad.p1.set(1.0f, 0.5f - f2 * 0.5f);
                                this.mTargetQuad.p2.set(0.0f, f2 * 0.5f + 0.5f);
                                this.mTargetQuad.p3.set(1.0f, f2 * 0.5f + 0.5f);
                            } else {
                                this.mTargetQuad.p0.set(0.5f - 0.5f / f2, 0.0f);
                                this.mTargetQuad.p1.set(0.5f / f2 + 0.5f, 0.0f);
                                this.mTargetQuad.p2.set(0.5f - 0.5f / f2, 1.0f);
                                this.mTargetQuad.p3.set(0.5f / f2 + 0.5f, 1.0f);
                            }
                            this.mProgram.setClearsOutput(true);
                        }
                    } else {
                        if (f2 > 1.0f) {
                            this.mTargetQuad.p0.set(0.5f - 0.5f / f2, 0.0f);
                            this.mTargetQuad.p1.set(0.5f / f2 + 0.5f, 0.0f);
                            this.mTargetQuad.p2.set(0.5f - 0.5f / f2, 1.0f);
                            this.mTargetQuad.p3.set(0.5f / f2 + 0.5f, 1.0f);
                        } else {
                            this.mTargetQuad.p0.set(0.0f, 0.5f - f2 * 0.5f);
                            this.mTargetQuad.p1.set(1.0f, 0.5f - f2 * 0.5f);
                            this.mTargetQuad.p2.set(0.0f, f2 * 0.5f + 0.5f);
                            this.mTargetQuad.p3.set(1.0f, f2 * 0.5f + 0.5f);
                        }
                        this.mProgram.setClearsOutput(true);
                    }
                } else {
                    this.mTargetQuad.p0.set(0.0f, 0.0f);
                    this.mTargetQuad.p1.set(1.0f, 0.0f);
                    this.mTargetQuad.p2.set(0.0f, 1.0f);
                    this.mTargetQuad.p3.set(1.0f, 1.0f);
                    this.mProgram.setClearsOutput(false);
                }
                if (this.mLogVerbose) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("UTR. quad: ");
                    stringBuilder.append(this.mTargetQuad);
                    Log.v(TAG, stringBuilder.toString());
                }
                this.mProgram.setTargetRegion(this.mTargetQuad);
            }
        }
    }

    @Override
    public void close(FilterContext filterContext) {
        synchronized (this) {
            if (this.mSurfaceId > 0) {
                filterContext.getGLEnvironment().unregisterSurfaceId(this.mSurfaceId);
                this.mSurfaceId = -1;
            }
            return;
        }
    }

    public void disconnect(FilterContext filterContext) {
        synchronized (this) {
            if (this.mLogVerbose) {
                Log.v(TAG, "disconnect");
            }
            if (this.mSurfaceTexture == null) {
                Log.d(TAG, "SurfaceTexture is already null. Nothing to disconnect.");
                return;
            }
            this.mSurfaceTexture = null;
            if (this.mSurfaceId > 0) {
                filterContext.getGLEnvironment().unregisterSurfaceId(this.mSurfaceId);
                this.mSurfaceId = -1;
            }
            return;
        }
    }

    @Override
    public void fieldPortValueUpdated(String charSequence, FilterContext filterContext) {
        if (this.mLogVerbose) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("FPVU. Thread: ");
            ((StringBuilder)charSequence).append(Thread.currentThread());
            Log.v(TAG, ((StringBuilder)charSequence).toString());
        }
        this.updateRenderMode();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void open(FilterContext object) {
        synchronized (this) {
            if (this.mSurfaceTexture == null) {
                Log.e(TAG, "SurfaceTexture is null!!");
                object = new StringBuilder();
                ((StringBuilder)object).append("Could not register SurfaceTexture: ");
                ((StringBuilder)object).append(this.mSurfaceTexture);
                RuntimeException runtimeException = new RuntimeException(((StringBuilder)object).toString());
                throw runtimeException;
            }
            this.mSurfaceId = ((FilterContext)object).getGLEnvironment().registerSurfaceTexture(this.mSurfaceTexture, this.mScreenWidth, this.mScreenHeight);
            int n = this.mSurfaceId;
            if (n > 0) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not register SurfaceTexture: ");
            ((StringBuilder)object).append(this.mSurfaceTexture);
            RuntimeException runtimeException = new RuntimeException(((StringBuilder)object).toString());
            throw runtimeException;
        }
    }

    @Override
    public void prepare(FilterContext filterContext) {
        Object object;
        if (this.mLogVerbose) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Prepare. Thread: ");
            ((StringBuilder)object).append(Thread.currentThread());
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        this.mProgram = ShaderProgram.createIdentity(filterContext);
        this.mProgram.setSourceRect(0.0f, 1.0f, 1.0f, -1.0f);
        this.mProgram.setClearColor(0.0f, 0.0f, 0.0f);
        this.updateRenderMode();
        object = new MutableFrameFormat(2, 3);
        ((MutableFrameFormat)object).setBytesPerSample(4);
        ((MutableFrameFormat)object).setDimensions(this.mScreenWidth, this.mScreenHeight);
        this.mScreen = (GLFrame)filterContext.getFrameManager().newBoundFrame((FrameFormat)object, 101, 0L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void process(FilterContext object) {
        synchronized (this) {
            int n = this.mSurfaceId;
            if (n <= 0) {
                return;
            }
            GLEnvironment gLEnvironment = ((FilterContext)object).getGLEnvironment();
            Frame frame = this.pullInput("frame");
            n = 0;
            float f = (float)frame.getFormat().getWidth() / (float)frame.getFormat().getHeight();
            if (f != this.mAspectRatio) {
                if (this.mLogVerbose) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Process. New aspect ratio: ");
                    stringBuilder.append(f);
                    stringBuilder.append(", previously: ");
                    stringBuilder.append(this.mAspectRatio);
                    stringBuilder.append(". Thread: ");
                    stringBuilder.append(Thread.currentThread());
                    Log.v(TAG, stringBuilder.toString());
                }
                this.mAspectRatio = f;
                this.updateTargetRect();
            }
            if (frame.getFormat().getTarget() != 3) {
                object = ((FilterContext)object).getFrameManager().duplicateFrameToTarget(frame, 3);
                n = 1;
            } else {
                object = frame;
            }
            gLEnvironment.activateSurfaceWithId(this.mSurfaceId);
            this.mProgram.process((Frame)object, (Frame)this.mScreen);
            gLEnvironment.setSurfaceTimestamp(frame.getTimestamp());
            gLEnvironment.swapBuffers();
            if (n != 0) {
                ((Frame)object).release();
            }
            return;
        }
    }

    @Override
    public void setupPorts() {
        synchronized (this) {
            if (this.mSurfaceTexture != null) {
                this.addMaskedInputPort("frame", ImageFormat.create(3));
                return;
            }
            RuntimeException runtimeException = new RuntimeException("Null SurfaceTexture passed to SurfaceTextureTarget");
            throw runtimeException;
        }
    }

    @Override
    public void tearDown(FilterContext object) {
        object = this.mScreen;
        if (object != null) {
            ((Frame)object).release();
        }
    }

    public void updateRenderMode() {
        CharSequence charSequence;
        if (this.mLogVerbose) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("updateRenderMode. Thread: ");
            ((StringBuilder)charSequence).append(Thread.currentThread());
            Log.v(TAG, ((StringBuilder)charSequence).toString());
        }
        if ((charSequence = this.mRenderModeString) != null) {
            if (((String)charSequence).equals("stretch")) {
                this.mRenderMode = 0;
            } else if (this.mRenderModeString.equals("fit")) {
                this.mRenderMode = 1;
            } else if (this.mRenderModeString.equals("fill_crop")) {
                this.mRenderMode = 2;
            } else if (this.mRenderModeString.equals("customize")) {
                this.mRenderMode = 3;
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unknown render mode '");
                ((StringBuilder)charSequence).append(this.mRenderModeString);
                ((StringBuilder)charSequence).append("'!");
                throw new RuntimeException(((StringBuilder)charSequence).toString());
            }
        }
        this.updateTargetRect();
    }
}

