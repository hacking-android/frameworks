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

public class ImageStitcher
extends Filter {
    private int mImageHeight;
    private int mImageWidth;
    private int mInputHeight;
    private int mInputWidth;
    private Frame mOutputFrame;
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

    public ImageStitcher(String string2) {
        super(string2);
    }

    private FrameFormat calcOutputFormatForInput(FrameFormat frameFormat) {
        MutableFrameFormat mutableFrameFormat = frameFormat.mutableCopy();
        this.mInputWidth = frameFormat.getWidth();
        this.mInputHeight = frameFormat.getHeight();
        int n = this.mInputWidth;
        int n2 = this.mPadSize;
        this.mSliceWidth = n - n2 * 2;
        this.mSliceHeight = this.mInputHeight - n2 * 2;
        this.mImageWidth = this.mSliceWidth * this.mXSlices;
        this.mImageHeight = this.mSliceHeight * this.mYSlices;
        mutableFrameFormat.setDimensions(this.mImageWidth, this.mImageHeight);
        return mutableFrameFormat;
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override
    public void process(FilterContext object) {
        block7 : {
            Frame frame;
            block6 : {
                FrameFormat frameFormat;
                block5 : {
                    frame = this.pullInput("image");
                    frameFormat = frame.getFormat();
                    if (this.mSliceIndex != 0) break block5;
                    this.mOutputFrame = ((FilterContext)object).getFrameManager().newFrame(this.calcOutputFormatForInput(frameFormat));
                    break block6;
                }
                if (frameFormat.getWidth() != this.mInputWidth || frameFormat.getHeight() != this.mInputHeight) break block7;
            }
            if (this.mProgram == null) {
                this.mProgram = ShaderProgram.createIdentity((FilterContext)object);
            }
            int n = this.mPadSize;
            float f = (float)n / (float)this.mInputWidth;
            float f2 = (float)n / (float)this.mInputHeight;
            int n2 = this.mSliceIndex;
            int n3 = this.mXSlices;
            n = this.mSliceWidth;
            int n4 = n2 % n3 * n;
            n2 = n2 / n3 * this.mSliceHeight;
            float f3 = Math.min(n, this.mImageWidth - n4);
            float f4 = Math.min(this.mSliceHeight, this.mImageHeight - n2);
            ((ShaderProgram)this.mProgram).setSourceRect(f, f2, f3 / (float)this.mInputWidth, f4 / (float)this.mInputHeight);
            object = (ShaderProgram)this.mProgram;
            f2 = n4;
            n = this.mImageWidth;
            f = n2;
            n4 = this.mImageHeight;
            ((ShaderProgram)object).setTargetRect(f2 /= (float)n, f / (float)n4, f3 / (float)n, f4 / (float)n4);
            this.mProgram.process(frame, this.mOutputFrame);
            ++this.mSliceIndex;
            if (this.mSliceIndex == this.mXSlices * this.mYSlices) {
                this.pushOutput("image", this.mOutputFrame);
                this.mOutputFrame.release();
                this.mSliceIndex = 0;
            }
            return;
        }
        throw new RuntimeException("Image size should not change.");
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3, 3));
        this.addOutputBasedOnInput("image", "image");
    }
}

