/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.numeric;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.format.ObjectFormat;

public class SinWaveFilter
extends Filter {
    private FrameFormat mOutputFormat;
    @GenerateFieldPort(hasDefault=true, name="stepSize")
    private float mStepSize = 0.05f;
    private float mValue = 0.0f;

    public SinWaveFilter(String string2) {
        super(string2);
    }

    @Override
    public void open(FilterContext filterContext) {
        this.mValue = 0.0f;
    }

    @Override
    public void process(FilterContext object) {
        object = ((FilterContext)object).getFrameManager().newFrame(this.mOutputFormat);
        ((Frame)object).setObjectValue(Float.valueOf(((float)Math.sin(this.mValue) + 1.0f) / 2.0f));
        this.pushOutput("value", (Frame)object);
        this.mValue += this.mStepSize;
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.mOutputFormat = ObjectFormat.fromClass(Float.class, 1);
        this.addOutputPort("value", this.mOutputFormat);
    }
}

