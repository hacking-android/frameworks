/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterPort;
import android.filterfw.core.Frame;
import android.filterfw.core.InputPort;

public class OutputPort
extends FilterPort {
    protected InputPort mBasePort;
    protected InputPort mTargetPort;

    public OutputPort(Filter filter, String string2) {
        super(filter, string2);
    }

    @Override
    public void clear() {
        InputPort inputPort = this.mTargetPort;
        if (inputPort != null) {
            inputPort.clear();
        }
    }

    @Override
    public void close() {
        super.close();
        InputPort inputPort = this.mTargetPort;
        if (inputPort != null && inputPort.isOpen()) {
            this.mTargetPort.close();
        }
    }

    public void connectTo(InputPort object) {
        if (this.mTargetPort == null) {
            this.mTargetPort = object;
            this.mTargetPort.setSourcePort(this);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" already connected to ");
        ((StringBuilder)object).append(this.mTargetPort);
        ((StringBuilder)object).append("!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @Override
    public boolean filterMustClose() {
        boolean bl = !this.isOpen() && this.isBlocking();
        return bl;
    }

    public InputPort getBasePort() {
        return this.mBasePort;
    }

    public Filter getTargetFilter() {
        Object object = this.mTargetPort;
        object = object == null ? null : ((FilterPort)object).getFilter();
        return object;
    }

    public InputPort getTargetPort() {
        return this.mTargetPort;
    }

    @Override
    public boolean hasFrame() {
        InputPort inputPort = this.mTargetPort;
        boolean bl = inputPort == null ? false : inputPort.hasFrame();
        return bl;
    }

    public boolean isConnected() {
        boolean bl = this.mTargetPort != null;
        return bl;
    }

    @Override
    public boolean isReady() {
        boolean bl = this.isOpen() && this.mTargetPort.acceptsFrame() || !this.isBlocking();
        return bl;
    }

    @Override
    public void open() {
        super.open();
        InputPort inputPort = this.mTargetPort;
        if (inputPort != null && !inputPort.isOpen()) {
            this.mTargetPort.open();
        }
    }

    @Override
    public Frame pullFrame() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot pull frame on ");
        stringBuilder.append(this);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public void pushFrame(Frame object) {
        InputPort inputPort = this.mTargetPort;
        if (inputPort != null) {
            inputPort.pushFrame((Frame)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attempting to push frame on unconnected port: ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append("!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public void setBasePort(InputPort inputPort) {
        this.mBasePort = inputPort;
    }

    @Override
    public void setFrame(Frame object) {
        this.assertPortIsOpen();
        InputPort inputPort = this.mTargetPort;
        if (inputPort != null) {
            inputPort.setFrame((Frame)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attempting to set frame on unconnected port: ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append("!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("output ");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}

