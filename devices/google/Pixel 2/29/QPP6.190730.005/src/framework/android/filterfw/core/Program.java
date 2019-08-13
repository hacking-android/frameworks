/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.Frame;

public abstract class Program {
    public abstract Object getHostValue(String var1);

    @UnsupportedAppUsage
    public void process(Frame frame, Frame frame2) {
        this.process(new Frame[]{frame}, frame2);
    }

    @UnsupportedAppUsage
    public abstract void process(Frame[] var1, Frame var2);

    public void reset() {
    }

    @UnsupportedAppUsage
    public abstract void setHostValue(String var1, Object var2);
}

