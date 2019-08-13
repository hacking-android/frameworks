/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.NativeProgram;
import android.filterfw.core.Program;

public class ToRGBFilter
extends Filter {
    private int mInputBPP;
    private FrameFormat mLastFormat = null;
    private Program mProgram;

    public ToRGBFilter(String string2) {
        super(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void createProgram(FilterContext object, FrameFormat frameFormat) {
        this.mInputBPP = frameFormat.getBytesPerSample();
        object = this.mLastFormat;
        if (object != null && ((FrameFormat)object).getBytesPerSample() == this.mInputBPP) {
            return;
        }
        this.mLastFormat = frameFormat;
        int n = this.mInputBPP;
        if (n == 1) {
            this.mProgram = new NativeProgram("filterpack_imageproc", "gray_to_rgb");
            return;
        }
        if (n == 4) {
            this.mProgram = new NativeProgram("filterpack_imageproc", "rgba_to_rgb");
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unsupported BytesPerPixel: ");
        ((StringBuilder)object).append(this.mInputBPP);
        ((StringBuilder)object).append("!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public FrameFormat getConvertedFormat(FrameFormat frameFormat) {
        frameFormat = frameFormat.mutableCopy();
        ((MutableFrameFormat)frameFormat).setMetaValue("colorspace", 2);
        ((MutableFrameFormat)frameFormat).setBytesPerSample(3);
        return frameFormat;
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return this.getConvertedFormat(frameFormat);
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("image");
        this.createProgram((FilterContext)object, frame.getFormat());
        object = ((FilterContext)object).getFrameManager().newFrame(this.getConvertedFormat(frame.getFormat()));
        this.mProgram.process(frame, (Frame)object);
        this.pushOutput("image", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat(2, 2);
        mutableFrameFormat.setDimensionCount(2);
        this.addMaskedInputPort("image", mutableFrameFormat);
        this.addOutputBasedOnInput("image", "image");
    }
}

