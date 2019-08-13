/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.Program;
import android.filterfw.format.ImageFormat;
import java.lang.reflect.Field;

public abstract class SimpleImageFilter
extends Filter {
    protected int mCurrentTarget = 0;
    protected String mParameterName;
    protected Program mProgram;

    public SimpleImageFilter(String string2, String string3) {
        super(string2);
        this.mParameterName = string3;
    }

    protected abstract Program getNativeProgram(FilterContext var1);

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    protected abstract Program getShaderProgram(FilterContext var1);

    @Override
    public void process(FilterContext filterContext) {
        Frame frame = this.pullInput("image");
        FrameFormat frameFormat = frame.getFormat();
        Frame frame2 = filterContext.getFrameManager().newFrame(frameFormat);
        this.updateProgramWithTarget(frameFormat.getTarget(), filterContext);
        this.mProgram.process(frame, frame2);
        this.pushOutput("image", frame2);
        frame2.release();
    }

    @Override
    public void setupPorts() {
        if (this.mParameterName != null) {
            try {
                Field field = SimpleImageFilter.class.getDeclaredField("mProgram");
                this.addProgramPort(this.mParameterName, this.mParameterName, field, Float.TYPE, false);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                throw new RuntimeException("Internal Error: mProgram field not found!");
            }
        }
        this.addMaskedInputPort("image", ImageFormat.create(3));
        this.addOutputBasedOnInput("image", "image");
    }

    protected void updateProgramWithTarget(int n, FilterContext object) {
        if (n != this.mCurrentTarget) {
            this.mProgram = n != 2 ? (n != 3 ? null : this.getShaderProgram((FilterContext)object)) : this.getNativeProgram((FilterContext)object);
            Program program = this.mProgram;
            if (program != null) {
                this.initProgramInputs(program, (FilterContext)object);
                this.mCurrentTarget = n;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Could not create a program for image filter ");
                ((StringBuilder)object).append(this);
                ((StringBuilder)object).append("!");
                throw new RuntimeException(((StringBuilder)object).toString());
            }
        }
    }
}

