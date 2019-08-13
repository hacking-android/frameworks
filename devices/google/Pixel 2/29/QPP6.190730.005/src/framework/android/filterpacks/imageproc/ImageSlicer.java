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
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;

public class ImageSlicer
extends Filter {
    private int mInputHeight;
    private int mInputWidth;
    private Frame mOriginalFrame;
    private int mOutputHeight;
    private int mOutputWidth;
    @GenerateFieldPort(name="padSize")
    private int mPadSize;
    private Program mProgram;
    private int mSliceHeight;
    private int mSliceIndex = 0;
    private int mSliceWidth;
    @GenerateFieldPort(name="xSlices")
    private int mXSlices;
    @GenerateFieldPort(name="ySlices")
    private int mYSlices;

    public ImageSlicer(String string2) {
        super(string2);
    }

    private void calcOutputFormatForInput(Frame frame) {
        this.mInputWidth = frame.getFormat().getWidth();
        this.mInputHeight = frame.getFormat().getHeight();
        int n = this.mInputWidth;
        int n2 = this.mXSlices;
        this.mSliceWidth = (n + n2 - 1) / n2;
        n2 = this.mInputHeight;
        n = this.mYSlices;
        this.mSliceHeight = (n2 + n - 1) / n;
        n2 = this.mSliceWidth;
        n = this.mPadSize;
        this.mOutputWidth = n2 + n * 2;
        this.mOutputHeight = this.mSliceHeight + n * 2;
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override
    public void process(FilterContext filterContext) {
        if (this.mSliceIndex == 0) {
            this.mOriginalFrame = this.pullInput("image");
            this.calcOutputFormatForInput(this.mOriginalFrame);
        }
        Object object = this.mOriginalFrame.getFormat().mutableCopy();
        ((MutableFrameFormat)object).setDimensions(this.mOutputWidth, this.mOutputHeight);
        object = filterContext.getFrameManager().newFrame((FrameFormat)object);
        if (this.mProgram == null) {
            this.mProgram = ShaderProgram.createIdentity(filterContext);
        }
        int n = this.mSliceIndex;
        int n2 = this.mXSlices;
        int n3 = n / n2;
        int n4 = this.mSliceWidth;
        int n5 = this.mPadSize;
        float f = n4 * (n % n2) - n5;
        n = this.mInputWidth;
        float f2 = this.mSliceHeight * n3 - n5;
        n5 = this.mInputHeight;
        ((ShaderProgram)this.mProgram).setSourceRect(f /= (float)n, f2 /= (float)n5, (float)this.mOutputWidth / (float)n, (float)this.mOutputHeight / (float)n5);
        this.mProgram.process(this.mOriginalFrame, (Frame)object);
        ++this.mSliceIndex;
        if (this.mSliceIndex == this.mXSlices * this.mYSlices) {
            this.mSliceIndex = 0;
            this.mOriginalFrame.release();
            this.setWaitsOnInputPort("image", true);
        } else {
            this.mOriginalFrame.retain();
            this.setWaitsOnInputPort("image", false);
        }
        this.pushOutput("image", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3, 3));
        this.addOutputBasedOnInput("image", "image");
    }
}

