/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFinalPort;

public class FrameBranch
extends Filter {
    @GenerateFinalPort(hasDefault=true, name="outputs")
    private int mNumberOfOutputs = 2;

    public FrameBranch(String string2) {
        super(string2);
    }

    @Override
    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return frameFormat;
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("in");
        for (int i = 0; i < this.mNumberOfOutputs; ++i) {
            object = new StringBuilder();
            ((StringBuilder)object).append("out");
            ((StringBuilder)object).append(i);
            this.pushOutput(((StringBuilder)object).toString(), frame);
        }
    }

    @Override
    public void setupPorts() {
        this.addInputPort("in");
        for (int i = 0; i < this.mNumberOfOutputs; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("out");
            stringBuilder.append(i);
            this.addOutputBasedOnInput(stringBuilder.toString(), "in");
        }
    }
}

