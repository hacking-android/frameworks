/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.util.Log;

public abstract class FilterPort {
    private static final String TAG = "FilterPort";
    protected boolean mChecksType = false;
    protected Filter mFilter;
    protected boolean mIsBlocking = true;
    protected boolean mIsOpen = false;
    private boolean mLogVerbose;
    protected String mName;
    protected FrameFormat mPortFormat;

    public FilterPort(Filter filter, String string2) {
        this.mName = string2;
        this.mFilter = filter;
        this.mLogVerbose = Log.isLoggable(TAG, 2);
    }

    protected void assertPortIsOpen() {
        if (this.isOpen()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal operation on closed ");
        stringBuilder.append(this);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    protected void checkFrameManager(Frame frame, FilterContext object) {
        if (frame.getFrameManager() != null && frame.getFrameManager() != ((FilterContext)object).getFrameManager()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Frame ");
            ((StringBuilder)object).append(frame);
            ((StringBuilder)object).append(" is managed by foreign FrameManager! ");
            throw new RuntimeException(((StringBuilder)object).toString());
        }
    }

    protected void checkFrameType(Frame frame, boolean bl) {
        if ((this.mChecksType || bl) && this.mPortFormat != null && !frame.getFormat().isCompatibleWith(this.mPortFormat)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Frame passed to ");
            stringBuilder.append(this);
            stringBuilder.append(" is of incorrect type! Expected ");
            stringBuilder.append(this.mPortFormat);
            stringBuilder.append(" but got ");
            stringBuilder.append(frame.getFormat());
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    public abstract void clear();

    public void close() {
        if (this.mIsOpen && this.mLogVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Closing ");
            stringBuilder.append(this);
            Log.v("FilterPort", stringBuilder.toString());
        }
        this.mIsOpen = false;
    }

    public abstract boolean filterMustClose();

    public Filter getFilter() {
        return this.mFilter;
    }

    public String getName() {
        return this.mName;
    }

    public FrameFormat getPortFormat() {
        return this.mPortFormat;
    }

    public abstract boolean hasFrame();

    public boolean isAttached() {
        boolean bl = this.mFilter != null;
        return bl;
    }

    public boolean isBlocking() {
        return this.mIsBlocking;
    }

    public boolean isOpen() {
        return this.mIsOpen;
    }

    public abstract boolean isReady();

    public void open() {
        if (!this.mIsOpen && this.mLogVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Opening ");
            stringBuilder.append(this);
            Log.v("FilterPort", stringBuilder.toString());
        }
        this.mIsOpen = true;
    }

    public abstract Frame pullFrame();

    public abstract void pushFrame(Frame var1);

    public void setBlocking(boolean bl) {
        this.mIsBlocking = bl;
    }

    public void setChecksType(boolean bl) {
        this.mChecksType = bl;
    }

    public abstract void setFrame(Frame var1);

    public void setPortFormat(FrameFormat frameFormat) {
        this.mPortFormat = frameFormat;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("port '");
        stringBuilder.append(this.mName);
        stringBuilder.append("' of ");
        stringBuilder.append(this.mFilter);
        return stringBuilder.toString();
    }
}

