/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.GenerateFieldPort;

public class FrameStore
extends Filter {
    @GenerateFieldPort(name="key")
    private String mKey;

    public FrameStore(String string2) {
        super(string2);
    }

    @Override
    public void process(FilterContext filterContext) {
        Frame frame = this.pullInput("frame");
        filterContext.storeFrame(this.mKey, frame);
    }

    @Override
    public void setupPorts() {
        this.addInputPort("frame");
    }
}

