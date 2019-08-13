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

public abstract class ImageCombineFilter
extends Filter {
    protected int mCurrentTarget = 0;
    protected String[] mInputNames;
    protected String mOutputName;
    protected String mParameterName;
    protected Program mProgram;

    public ImageCombineFilter(String string2, String[] arrstring, String string3, String string4) {
        super(string2);
        this.mInputNames = arrstring;
        this.mOutputName = string3;
        this.mParameterName = string4;
    }

    private void assertAllInputTargetsMatch() {
        Object object = this.mInputNames;
        int n = this.getInputFormat(object[0]).getTarget();
        object = this.mInputNames;
        int n2 = ((String[])object).length;
        for (int i = 0; i < n2; ++i) {
            if (n == this.getInputFormat(object[i]).getTarget()) {
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Type mismatch of input formats in filter ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(". All input frames must have the same target!");
            throw new RuntimeException(((StringBuilder)object).toString());
        }
    }

    protected abstract Program getNativeProgram(FilterContext var1);

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    protected abstract Program getShaderProgram(FilterContext var1);

    @Override
    public void process(FilterContext filterContext) {
        Object object = this.mInputNames;
        Frame[] arrframe = new Frame[((String[])object).length];
        int n = ((String[])object).length;
        int n2 = 0;
        int n3 = 0;
        while (n3 < n) {
            arrframe[n2] = this.pullInput(object[n3]);
            ++n3;
            ++n2;
        }
        object = filterContext.getFrameManager().newFrame(arrframe[0].getFormat());
        this.updateProgramWithTarget(arrframe[0].getFormat().getTarget(), filterContext);
        this.mProgram.process(arrframe, (Frame)object);
        this.pushOutput(this.mOutputName, (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        Object object;
        if (this.mParameterName != null) {
            try {
                object = ImageCombineFilter.class.getDeclaredField("mProgram");
                this.addProgramPort(this.mParameterName, this.mParameterName, (Field)object, Float.TYPE, false);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                throw new RuntimeException("Internal Error: mProgram field not found!");
            }
        }
        object = this.mInputNames;
        int n = ((String[])object).length;
        for (int i = 0; i < n; ++i) {
            this.addMaskedInputPort(object[i], ImageFormat.create(3));
        }
        this.addOutputBasedOnInput(this.mOutputName, this.mInputNames[0]);
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

