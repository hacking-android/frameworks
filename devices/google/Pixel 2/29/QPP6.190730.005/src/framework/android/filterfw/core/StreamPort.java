/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.InputPort;

public class StreamPort
extends InputPort {
    private Frame mFrame;
    private boolean mPersistent;

    public StreamPort(Filter filter, String string2) {
        super(filter, string2);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void assignFrame(Frame object, boolean bl) {
        synchronized (this) {
            void var2_2;
            this.assertPortIsOpen();
            this.checkFrameType((Frame)object, (boolean)var2_2);
            if (var2_2 != false) {
                if (this.mFrame != null) {
                    this.mFrame.release();
                }
            } else if (this.mFrame != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Attempting to push more than one frame on port: ");
                stringBuilder.append(this);
                stringBuilder.append("!");
                object = new RuntimeException(stringBuilder.toString());
                throw object;
            }
            this.mFrame = ((Frame)object).retain();
            this.mFrame.markReadOnly();
            this.mPersistent = var2_2;
            return;
        }
    }

    @Override
    public void clear() {
        Frame frame = this.mFrame;
        if (frame != null) {
            frame.release();
            this.mFrame = null;
        }
    }

    @Override
    public boolean hasFrame() {
        synchronized (this) {
            Frame frame = this.mFrame;
            boolean bl = frame != null;
            return bl;
        }
    }

    @Override
    public Frame pullFrame() {
        synchronized (this) {
            block6 : {
                if (this.mFrame == null) break block6;
                Frame frame = this.mFrame;
                if (this.mPersistent) {
                    this.mFrame.retain();
                } else {
                    this.mFrame = null;
                }
                return frame;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No frame available to pull on port: ");
            stringBuilder.append(this);
            stringBuilder.append("!");
            RuntimeException runtimeException = new RuntimeException(stringBuilder.toString());
            throw runtimeException;
        }
    }

    @Override
    public void pushFrame(Frame frame) {
        this.assignFrame(frame, false);
    }

    @Override
    public void setFrame(Frame frame) {
        this.assignFrame(frame, true);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("input ");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }

    @Override
    public void transfer(FilterContext filterContext) {
        synchronized (this) {
            if (this.mFrame != null) {
                this.checkFrameManager(this.mFrame, filterContext);
            }
            return;
        }
    }
}

