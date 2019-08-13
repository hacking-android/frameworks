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

public class FrameFetch
extends Filter {
    @GenerateFinalPort(hasDefault=true, name="format")
    private FrameFormat mFormat;
    @GenerateFieldPort(name="key")
    private String mKey;
    @GenerateFieldPort(hasDefault=true, name="repeatFrame")
    private boolean mRepeatFrame = false;

    public FrameFetch(String string2) {
        super(string2);
    }

    @Override
    public void process(FilterContext object) {
        if ((object = ((FilterContext)object).fetchFrame(this.mKey)) != null) {
            this.pushOutput("frame", (Frame)object);
            if (!this.mRepeatFrame) {
                this.closeOutputPort("frame");
            }
        } else {
            this.delayNextProcess(250);
        }
    }

    @Override
    public void setupPorts() {
        FrameFormat frameFormat;
        FrameFormat frameFormat2 = frameFormat = this.mFormat;
        if (frameFormat == null) {
            frameFormat2 = FrameFormat.unspecified();
        }
        this.addOutputPort("frame", frameFormat2);
    }
}

