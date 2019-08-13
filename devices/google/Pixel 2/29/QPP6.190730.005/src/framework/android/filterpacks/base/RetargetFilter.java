/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.core.MutableFrameFormat;

public class RetargetFilter
extends Filter {
    private MutableFrameFormat mOutputFormat;
    private int mTarget = -1;
    @GenerateFinalPort(hasDefault=false, name="target")
    private String mTargetString;

    public RetargetFilter(String string2) {
        super(string2);
    }

    @Override
    public FrameFormat getOutputFormat(String object, FrameFormat frameFormat) {
        object = frameFormat.mutableCopy();
        ((MutableFrameFormat)object).setTarget(this.mTarget);
        return object;
    }

    @Override
    public void process(FilterContext object) {
        Frame frame = this.pullInput("frame");
        object = ((FilterContext)object).getFrameManager().duplicateFrameToTarget(frame, this.mTarget);
        this.pushOutput("frame", (Frame)object);
        ((Frame)object).release();
    }

    @Override
    public void setupPorts() {
        this.mTarget = FrameFormat.readTargetString(this.mTargetString);
        this.addInputPort("frame");
        this.addOutputBasedOnInput("frame", "frame");
    }
}

