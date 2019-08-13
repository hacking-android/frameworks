/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.geometry.Point;
import android.filterfw.geometry.Quad;

public class RotateFilter
extends Filter {
    @GenerateFieldPort(name="angle")
    private int mAngle;
    private int mHeight = 0;
    private int mOutputHeight;
    private int mOutputWidth;
    private Program mProgram;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;
    private int mWidth = 0;

    public RotateFilter(String string2) {
        super(string2);
    }

    private void updateParameters() {
        int n = this.mAngle;
        if (n % 90 == 0) {
            float f;
            float f2 = -1.0f;
            if (n % 180 == 0) {
                float f3 = 0.0f;
                if (n % 360 == 0) {
                    f2 = 1.0f;
                }
                f = f2;
                f2 = f3;
            } else {
                if ((n + 90) % 360 != 0) {
                    f2 = 1.0f;
                }
                this.mOutputWidth = this.mHeight;
                this.mOutputHeight = this.mWidth;
                f = 0.0f;
            }
            Quad quad = new Quad(new Point((-f + f2 + 1.0f) * 0.5f, (-f2 - f + 1.0f) * 0.5f), new Point((f + f2 + 1.0f) * 0.5f, (f2 - f + 1.0f) * 0.5f), new Point((-f - f2 + 1.0f) * 0.5f, (-f2 + f + 1.0f) * 0.5f), new Point((f - f2 + 1.0f) * 0.5f, (f2 + f + 1.0f) * 0.5f));
            ((ShaderProgram)this.mProgram).setTargetRegion(quad);
            return;
        }
        throw new RuntimeException("degree has to be multiply of 90.");
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
        if (this.mProgram != null) {
            this.updateParameters();
        }
    }

    public void initProgram(FilterContext object, int n) {
        if (n == 3) {
            object = ShaderProgram.createIdentity((FilterContext)object);
            ((ShaderProgram)object).setMaximumTileSize(this.mTileSize);
            ((ShaderProgram)object).setClearsOutput(true);
            this.mProgram = object;
            this.mTarget = n;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Filter Sharpen does not support frames of target ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append("!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("image");
        FrameFormat frameFormat = frame.getFormat();
        if (this.mProgram == null || frameFormat.getTarget() != this.mTarget) {
            this.initProgram((FilterContext)object, frameFormat.getTarget());
        }
        if (frameFormat.getWidth() != this.mWidth || frameFormat.getHeight() != this.mHeight) {
            this.mWidth = frameFormat.getWidth();
            this.mHeight = frameFormat.getHeight();
            this.mOutputWidth = this.mWidth;
            this.mOutputHeight = this.mHeight;
            this.updateParameters();
        }
        frameFormat = ImageFormat.create(this.mOutputWidth, this.mOutputHeight, 3, 3);
        object = ((FilterContext)object).getFrameManager().newFrame(frameFormat);
        this.mProgram.process(frame, (Frame)object);
        this.pushOutput("image", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3));
        this.addOutputBasedOnInput("image", "image");
    }
}

