/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.ui;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FilterSurfaceView;
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
import android.util.Log;
import android.view.SurfaceHolder;

public class SurfaceRenderFilter
extends Filter
implements SurfaceHolder.Callback {
    private static final String TAG = "SurfaceRenderFilter";
    private final int RENDERMODE_FILL_CROP;
    private final int RENDERMODE_FIT;
    private final int RENDERMODE_STRETCH;
    private float mAspectRatio = 1.0f;
    private boolean mIsBound = false;
    private boolean mLogVerbose = Log.isLoggable("SurfaceRenderFilter", 2);
    private ShaderProgram mProgram;
    private int mRenderMode = 1;
    @GenerateFieldPort(hasDefault=true, name="renderMode")
    private String mRenderModeString;
    private GLFrame mScreen;
    private int mScreenHeight;
    private int mScreenWidth;
    @GenerateFinalPort(name="surfaceView")
    private FilterSurfaceView mSurfaceView;

    public SurfaceRenderFilter(String string2) {
        super(string2);
        this.RENDERMODE_STRETCH = 0;
        this.RENDERMODE_FIT = 1;
        this.RENDERMODE_FILL_CROP = 2;
    }

    private void updateTargetRect() {
        ShaderProgram shaderProgram;
        int n;
        int n2 = this.mScreenWidth;
        if (n2 > 0 && (n = this.mScreenHeight) > 0 && (shaderProgram = this.mProgram) != null) {
            float f = (float)n2 / (float)n / this.mAspectRatio;
            n = this.mRenderMode;
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        if (f > 1.0f) {
                            shaderProgram.setTargetRect(0.0f, 0.5f - f * 0.5f, 1.0f, f);
                        } else {
                            shaderProgram.setTargetRect(0.5f - 0.5f / f, 0.0f, 1.0f / f, 1.0f);
                        }
                    }
                } else if (f > 1.0f) {
                    shaderProgram.setTargetRect(0.5f - 0.5f / f, 0.0f, 1.0f / f, 1.0f);
                } else {
                    shaderProgram.setTargetRect(0.0f, 0.5f - f * 0.5f, 1.0f, f);
                }
            } else {
                shaderProgram.setTargetRect(0.0f, 0.0f, 1.0f, 1.0f);
            }
        }
    }

    @Override
    public void close(FilterContext filterContext) {
        this.mSurfaceView.unbind();
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
        this.updateTargetRect();
    }

    @Override
    public void open(FilterContext filterContext) {
        this.mSurfaceView.unbind();
        this.mSurfaceView.bindToListener(this, filterContext.getGLEnvironment());
    }

    @Override
    public void prepare(FilterContext filterContext) {
        this.mProgram = ShaderProgram.createIdentity(filterContext);
        this.mProgram.setSourceRect(0.0f, 1.0f, 1.0f, -1.0f);
        this.mProgram.setClearsOutput(true);
        this.mProgram.setClearColor(0.0f, 0.0f, 0.0f);
        this.updateRenderMode();
        MutableFrameFormat mutableFrameFormat = ImageFormat.create(this.mSurfaceView.getWidth(), this.mSurfaceView.getHeight(), 3, 3);
        this.mScreen = (GLFrame)filterContext.getFrameManager().newBoundFrame(mutableFrameFormat, 101, 0L);
    }

    @Override
    public void process(FilterContext object) {
        GLEnvironment gLEnvironment;
        if (!this.mIsBound) {
            object = new StringBuilder();
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(": Ignoring frame as there is no surface to render to!");
            Log.w(TAG, ((StringBuilder)object).toString());
            return;
        }
        if (this.mLogVerbose) {
            Log.v(TAG, "Starting frame processing");
        }
        if ((gLEnvironment = this.mSurfaceView.getGLEnv()) == ((FilterContext)object).getGLEnvironment()) {
            StringBuilder stringBuilder;
            Frame frame = this.pullInput("frame");
            boolean bl = false;
            float f = (float)frame.getFormat().getWidth() / (float)frame.getFormat().getHeight();
            if (f != this.mAspectRatio) {
                if (this.mLogVerbose) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("New aspect ratio: ");
                    stringBuilder.append(f);
                    stringBuilder.append(", previously: ");
                    stringBuilder.append(this.mAspectRatio);
                    Log.v(TAG, stringBuilder.toString());
                }
                this.mAspectRatio = f;
                this.updateTargetRect();
            }
            if (this.mLogVerbose) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Got input format: ");
                stringBuilder.append(frame.getFormat());
                Log.v(TAG, stringBuilder.toString());
            }
            if (frame.getFormat().getTarget() != 3) {
                object = ((FilterContext)object).getFrameManager().duplicateFrameToTarget(frame, 3);
                bl = true;
            } else {
                object = frame;
            }
            gLEnvironment.activateSurfaceWithId(this.mSurfaceView.getSurfaceId());
            this.mProgram.process((Frame)object, (Frame)this.mScreen);
            gLEnvironment.swapBuffers();
            if (bl) {
                ((Frame)object).release();
            }
            return;
        }
        throw new RuntimeException("Surface created under different GLEnvironment!");
    }

    @Override
    public void setupPorts() {
        if (this.mSurfaceView != null) {
            this.addMaskedInputPort("frame", ImageFormat.create(3));
            return;
        }
        throw new RuntimeException("NULL SurfaceView passed to SurfaceRenderFilter");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
        synchronized (this) {
            if (this.mScreen != null) {
                this.mScreenWidth = n2;
                this.mScreenHeight = n3;
                this.mScreen.setViewport(0, 0, this.mScreenWidth, this.mScreenHeight);
                this.updateTargetRect();
            }
            return;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        synchronized (this) {
            this.mIsBound = true;
            return;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        synchronized (this) {
            this.mIsBound = false;
            return;
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
        CharSequence charSequence = this.mRenderModeString;
        if (charSequence != null) {
            if (((String)charSequence).equals("stretch")) {
                this.mRenderMode = 0;
            } else if (this.mRenderModeString.equals("fit")) {
                this.mRenderMode = 1;
            } else if (this.mRenderModeString.equals("fill_crop")) {
                this.mRenderMode = 2;
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

