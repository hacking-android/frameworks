/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;

public class FrameSource
extends Filter {
    @GenerateFinalPort(name="format")
    private FrameFormat mFormat;
    @GenerateFieldPort(hasDefault=true, name="frame")
    private Frame mFrame = null;
    @GenerateFieldPort(hasDefault=true, name="repeatFrame")
    private boolean mRepeatFrame = false;

    public FrameSource(String string2) {
        super(string2);
    }

    @Override
    public void process(FilterContext object) {
        object = this.mFrame;
        if (object != null) {
            this.pushOutput("frame", (Frame)object);
        }
        if (!this.mRepeatFrame) {
            this.closeOutputPort("frame");
        }
    }

    @Override
    public void setupPorts() {
        this.addOutputPort("frame", this.mFormat);
    }
}

