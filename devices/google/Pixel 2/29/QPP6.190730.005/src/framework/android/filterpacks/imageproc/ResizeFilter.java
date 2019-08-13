/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLFrame;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;

public class ResizeFilter
extends Filter {
    @GenerateFieldPort(hasDefault=true, name="generateMipMap")
    private boolean mGenerateMipMap = false;
    private int mInputChannels;
    @GenerateFieldPort(hasDefault=true, name="keepAspectRatio")
    private boolean mKeepAspectRatio = false;
    private FrameFormat mLastFormat = null;
    @GenerateFieldPort(name="oheight")
    private int mOHeight;
    @GenerateFieldPort(name="owidth")
    private int mOWidth;
    private MutableFrameFormat mOutputFormat;
    private Program mProgram;

    public ResizeFilter(String string2) {
        super(string2);
    }

    protected void createProgram(FilterContext filterContext, FrameFormat frameFormat) {
        FrameFormat frameFormat2 = this.mLastFormat;
        if (frameFormat2 != null && frameFormat2.getTarget() == frameFormat.getTarget()) {
            return;
        }
        this.mLastFormat = frameFormat;
        int n = frameFormat.getTarget();
        if (n != 2) {
            if (n == 3) {
                this.mProgram = ShaderProgram.createIdentity(filterContext);
                return;
            }
            throw new RuntimeException("ResizeFilter could not create suitable program!");
        }
        throw new RuntimeException("Native ResizeFilter not implemented yet!");
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("image");
        this.createProgram((FilterContext)object, frame.getFormat());
        Object object2 = frame.getFormat().mutableCopy();
        if (this.mKeepAspectRatio) {
            FrameFormat frameFormat = frame.getFormat();
            this.mOHeight = this.mOWidth * frameFormat.getHeight() / frameFormat.getWidth();
        }
        ((MutableFrameFormat)object2).setDimensions(this.mOWidth, this.mOHeight);
        object2 = ((FilterContext)object).getFrameManager().newFrame((FrameFormat)object2);
        if (this.mGenerateMipMap) {
            object = (GLFrame)((FilterContext)object).getFrameManager().newFrame(frame.getFormat());
            ((GLFrame)object).setTextureParameter(10241, 9985);
            ((GLFrame)object).setDataFromFrame(frame);
            ((GLFrame)object).generateMipMap();
            this.mProgram.process((Frame)object, (Frame)object2);
            ((Frame)object).release();
        } else {
            this.mProgram.process(frame, (Frame)object2);
        }
        this.pushOutput("image", (Frame)object2);
        ((Frame)object2).release();
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3));
        this.addOutputBasedOnInput("image", "image");
    }
}

