/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.ui;

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
import android.util.Log;
import android.view.Surface;

public class SurfaceTargetFilter
extends Filter {
    private static final String TAG = "SurfaceRenderFilter";
    private final int RENDERMODE_FILL_CROP;
    private final int RENDERMODE_FIT;
    private final int RENDERMODE_STRETCH;
    private float mAspectRatio = 1.0f;
    private GLEnvironment mGlEnv;
    private boolean mLogVerbose = Log.isLoggable("SurfaceRenderFilter", 2);
    private ShaderProgram mProgram;
    private int mRenderMode = 1;
    @GenerateFieldPort(hasDefault=true, name="renderMode")
    private String mRenderModeString;
    private GLFrame mScreen;
    @GenerateFieldPort(name="oheight")
    private int mScreenHeight;
    @GenerateFieldPort(name="owidth")
    private int mScreenWidth;
    @GenerateFinalPort(name="surface")
    private Surface mSurface;
    private int mSurfaceId = -1;

    public SurfaceTargetFilter(String string2) {
        super(string2);
        this.RENDERMODE_STRETCH = 0;
        this.RENDERMODE_FIT = 1;
        this.RENDERMODE_FILL_CROP = 2;
    }

    private void registerSurface() {
        this.mSurfaceId = this.mGlEnv.registerSurface(this.mSurface);
        if (this.mSurfaceId >= 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not register Surface: ");
        stringBuilder.append(this.mSurface);
        throw new RuntimeException(stringBuilder.toString());
    }

    private void unregisterSurface() {
        int n = this.mSurfaceId;
        if (n > 0) {
            this.mGlEnv.unregisterSurfaceId(n);
        }
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
        this.unregisterSurface();
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
        this.mScreen.setViewport(0, 0, this.mScreenWidth, this.mScreenHeight);
        this.updateTargetRect();
    }

    @Override
    public void open(FilterContext filterContext) {
        this.registerSurface();
    }

    @Override
    public void prepare(FilterContext filterContext) {
        this.mGlEnv = filterContext.getGLEnvironment();
        this.mProgram = ShaderProgram.createIdentity(filterContext);
        this.mProgram.setSourceRect(0.0f, 1.0f, 1.0f, -1.0f);
        this.mProgram.setClearsOutput(true);
        this.mProgram.setClearColor(0.0f, 0.0f, 0.0f);
        MutableFrameFormat mutableFrameFormat = ImageFormat.create(this.mScreenWidth, this.mScreenHeight, 3, 3);
        this.mScreen = (GLFrame)filterContext.getFrameManager().newBoundFrame(mutableFrameFormat, 101, 0L);
        this.updateRenderMode();
    }

    @Override
    public void process(FilterContext object) {
        StringBuilder stringBuilder;
        if (this.mLogVerbose) {
            Log.v(TAG, "Starting frame processing");
        }
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
        this.mGlEnv.activateSurfaceWithId(this.mSurfaceId);
        this.mProgram.process((Frame)object, (Frame)this.mScreen);
        this.mGlEnv.swapBuffers();
        if (bl) {
            ((Frame)object).release();
        }
    }

    @Override
    public void setupPorts() {
        if (this.mSurface != null) {
            this.addMaskedInputPort("frame", ImageFormat.create(3));
            return;
        }
        throw new RuntimeException("NULL Surface passed to SurfaceTargetFilter");
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

