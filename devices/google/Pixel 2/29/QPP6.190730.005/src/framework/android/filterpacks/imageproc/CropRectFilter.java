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

public class CropRectFilter
extends Filter {
    private int mHeight = 0;
    @GenerateFieldPort(name="height")
    private int mOutputHeight;
    @GenerateFieldPort(name="width")
    private int mOutputWidth;
    private Program mProgram;
    private int mTarget = 0;
    @GenerateFieldPort(hasDefault=true, name="tile_size")
    private int mTileSize = 640;
    private int mWidth = 0;
    @GenerateFieldPort(name="xorigin")
    private int mXorigin;
    @GenerateFieldPort(name="yorigin")
    private int mYorigin;

    public CropRectFilter(String string2) {
        super(string2);
    }

    @Override
    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
        if (this.mProgram != null) {
            this.updateSourceRect(this.mWidth, this.mHeight);
        }
    }

    public void initProgram(FilterContext object, int n) {
        if (n == 3) {
            object = ShaderProgram.createIdentity((FilterContext)object);
            ((ShaderProgram)object).setMaximumTileSize(this.mTileSize);
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
    public void process(FilterContext filterContext) {
        Frame frame = this.pullInput("image");
        FrameFormat frameFormat = frame.getFormat();
        Object object = ImageFormat.create(this.mOutputWidth, this.mOutputHeight, 3, 3);
        object = filterContext.getFrameManager().newFrame((FrameFormat)object);
        if (this.mProgram == null || frameFormat.getTarget() != this.mTarget) {
            this.initProgram(filterContext, frameFormat.getTarget());
        }
        if (frameFormat.getWidth() != this.mWidth || frameFormat.getHeight() != this.mHeight) {
            this.updateSourceRect(frameFormat.getWidth(), frameFormat.getHeight());
        }
        this.mProgram.process(frame, (Frame)object);
        this.pushOutput("image", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3));
        this.addOutputBasedOnInput("image", "image");
    }

    void updateSourceRect(int n, int n2) {
        this.mWidth = n;
        this.mHeight = n2;
        ShaderProgram shaderProgram = (ShaderProgram)this.mProgram;
        float f = this.mXorigin;
        n = this.mWidth;
        float f2 = this.mYorigin;
        n2 = this.mHeight;
        shaderProgram.setSourceRect(f /= (float)n, f2 / (float)n2, (float)this.mOutputWidth / (float)n, (float)this.mOutputHeight / (float)n2);
    }
}

