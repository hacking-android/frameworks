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
import android.filterfw.core.ShaderProgram;
import android.filterfw.format.ImageFormat;
import android.filterfw.format.ObjectFormat;
import android.filterfw.geometry.Quad;

public class DrawOverlayFilter
extends Filter {
    private ShaderProgram mProgram;

    public DrawOverlayFilter(String string2) {
        super(string2);
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override
    public void prepare(FilterContext filterContext) {
        this.mProgram = ShaderProgram.createIdentity(filterContext);
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("source");
        Frame frame2 = this.pullInput("overlay");
        Quad quad = ((Quad)this.pullInput("box").getObjectValue()).translated(1.0f, 1.0f).scaled(2.0f);
        this.mProgram.setTargetRegion(quad);
        object = ((FilterContext)object).getFrameManager().newFrame(frame.getFormat());
        ((Frame)object).setDataFromFrame(frame);
        this.mProgram.process(frame2, (Frame)object);
        this.pushOutput("image", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        MutableFrameFormat mutableFrameFormat = ImageFormat.create(3, 3);
        this.addMaskedInputPort("source", mutableFrameFormat);
        this.addMaskedInputPort("overlay", mutableFrameFormat);
        this.addMaskedInputPort("box", ObjectFormat.fromClass(Quad.class, 1));
        this.addOutputBasedOnInput("image", "source");
    }
}

