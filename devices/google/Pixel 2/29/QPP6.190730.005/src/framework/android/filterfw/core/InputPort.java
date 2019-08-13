/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FilterPort;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.OutputPort;

public abstract class InputPort
extends FilterPort {
    protected OutputPort mSourcePort;

    public InputPort(Filter filter, String string2) {
        super(filter, string2);
    }

    public boolean acceptsFrame() {
        return this.hasFrame() ^ true;
    }

    @Override
    public void close() {
        OutputPort outputPort = this.mSourcePort;
        if (outputPort != null && outputPort.isOpen()) {
            this.mSourcePort.close();
        }
        super.close();
    }

    @Override
    public boolean filterMustClose() {
        boolean bl = !this.isOpen() && this.isBlocking() && !this.hasFrame();
        return bl;
    }

    public Filter getSourceFilter() {
        Object object = this.mSourcePort;
        object = object == null ? null : ((FilterPort)object).getFilter();
        return object;
    }

    public FrameFormat getSourceFormat() {
        Object object = this.mSourcePort;
        object = object != null ? ((FilterPort)object).getPortFormat() : this.getPortFormat();
        return object;
    }

    public OutputPort getSourcePort() {
        return this.mSourcePort;
    }

    public Object getTarget() {
        return null;
    }

    public boolean isConnected() {
        boolean bl = this.mSourcePort != null;
        return bl;
    }

    @Override
    public boolean isReady() {
        boolean bl = this.hasFrame() || !this.isBlocking();
        return bl;
    }

    @Override
    public void open() {
        super.open();
        OutputPort outputPort = this.mSourcePort;
        if (outputPort != null && !outputPort.isOpen()) {
            this.mSourcePort.open();
        }
    }

    public void setSourcePort(OutputPort object) {
        if (this.mSourcePort == null) {
            this.mSourcePort = object;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" already connected to ");
        ((StringBuilder)object).append(this.mSourcePort);
        ((StringBuilder)object).append("!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public abstract void transfer(FilterContext var1);
}

