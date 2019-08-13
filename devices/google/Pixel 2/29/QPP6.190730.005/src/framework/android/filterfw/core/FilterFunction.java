/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.GLEnvironment;
import android.filterfw.core.InputPort;
import android.filterfw.core.KeyValueMap;
import android.filterfw.core.OutputPort;
import android.filterfw.core.StreamPort;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FilterFunction {
    private Filter mFilter;
    private FilterContext mFilterContext;
    private boolean mFilterIsSetup = false;
    private FrameHolderPort[] mResultHolders;

    public FilterFunction(FilterContext filterContext, Filter filter) {
        this.mFilterContext = filterContext;
        this.mFilter = filter;
    }

    private void connectFilterOutputs() {
        int n = 0;
        this.mResultHolders = new FrameHolderPort[this.mFilter.getNumberOfOutputs()];
        for (OutputPort outputPort : this.mFilter.getOutputPorts()) {
            this.mResultHolders[n] = new FrameHolderPort();
            outputPort.connectTo(this.mResultHolders[n]);
            ++n;
        }
    }

    public void close() {
        this.mFilter.performClose(this.mFilterContext);
    }

    public Frame execute(KeyValueMap object) {
        int n = this.mFilter.getNumberOfOutputs();
        if (n <= 1) {
            if (!this.mFilterIsSetup) {
                this.connectFilterOutputs();
                this.mFilterIsSetup = true;
            }
            boolean bl = false;
            GLEnvironment gLEnvironment = this.mFilterContext.getGLEnvironment();
            boolean bl2 = bl;
            if (gLEnvironment != null) {
                bl2 = bl;
                if (!gLEnvironment.isActive()) {
                    gLEnvironment.activate();
                    bl2 = true;
                }
            }
            for (Map.Entry entry2 : ((HashMap)object).entrySet()) {
                if (entry2.getValue() instanceof Frame) {
                    this.mFilter.pushInputFrame((String)entry2.getKey(), (Frame)entry2.getValue());
                    continue;
                }
                this.mFilter.pushInputValue((String)entry2.getKey(), entry2.getValue());
            }
            if (this.mFilter.getStatus() != 3) {
                this.mFilter.openOutputs();
            }
            this.mFilter.performProcess(this.mFilterContext);
            Object var6_7 = null;
            object = var6_7;
            if (n == 1) {
                object = var6_7;
                if (this.mResultHolders[0].hasFrame()) {
                    object = this.mResultHolders[0].pullFrame();
                }
            }
            if (bl2) {
                gLEnvironment.deactivate();
            }
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Calling execute on filter ");
        ((StringBuilder)object).append(this.mFilter);
        ((StringBuilder)object).append(" with multiple outputs! Use executeMulti() instead!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public Frame executeWithArgList(Object ... arrobject) {
        return this.execute(KeyValueMap.fromKeyValues(arrobject));
    }

    public FilterContext getContext() {
        return this.mFilterContext;
    }

    public Filter getFilter() {
        return this.mFilter;
    }

    public void setInputFrame(String string2, Frame frame) {
        this.mFilter.setInputFrame(string2, frame);
    }

    public void setInputValue(String string2, Object object) {
        this.mFilter.setInputValue(string2, object);
    }

    public void tearDown() {
        this.mFilter.performTearDown(this.mFilterContext);
        this.mFilter = null;
    }

    public String toString() {
        return this.mFilter.getName();
    }

    private class FrameHolderPort
    extends StreamPort {
        public FrameHolderPort() {
            super(null, "holder");
        }
    }

}

