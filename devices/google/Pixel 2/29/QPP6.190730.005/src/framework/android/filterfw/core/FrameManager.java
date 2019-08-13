/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GLEnvironment;
import android.filterfw.core.MutableFrameFormat;

public abstract class FrameManager {
    private FilterContext mContext;

    @UnsupportedAppUsage
    public Frame duplicateFrame(Frame frame) {
        Frame frame2 = this.newFrame(frame.getFormat());
        frame2.setDataFromFrame(frame);
        return frame2;
    }

    public Frame duplicateFrameToTarget(Frame frame, int n) {
        Object object = frame.getFormat().mutableCopy();
        ((MutableFrameFormat)object).setTarget(n);
        object = this.newFrame((FrameFormat)object);
        ((Frame)object).setDataFromFrame(frame);
        return object;
    }

    public FilterContext getContext() {
        return this.mContext;
    }

    public GLEnvironment getGLEnvironment() {
        Object object = this.mContext;
        object = object != null ? ((FilterContext)object).getGLEnvironment() : null;
        return object;
    }

    @UnsupportedAppUsage
    public abstract Frame newBoundFrame(FrameFormat var1, int var2, long var3);

    @UnsupportedAppUsage
    public abstract Frame newFrame(FrameFormat var1);

    public abstract Frame releaseFrame(Frame var1);

    public abstract Frame retainFrame(Frame var1);

    void setContext(FilterContext filterContext) {
        this.mContext = filterContext;
    }

    public void tearDown() {
    }
}

