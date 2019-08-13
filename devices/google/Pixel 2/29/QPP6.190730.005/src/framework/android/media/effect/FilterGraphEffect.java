/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.GraphRunner;
import android.filterfw.core.SyncRunner;
import android.filterfw.io.GraphIOException;
import android.filterfw.io.GraphReader;
import android.filterfw.io.TextGraphReader;
import android.media.effect.EffectContext;
import android.media.effect.FilterEffect;

public class FilterGraphEffect
extends FilterEffect {
    private static final String TAG = "FilterGraphEffect";
    protected FilterGraph mGraph;
    protected String mInputName;
    protected String mOutputName;
    protected GraphRunner mRunner;
    protected Class mSchedulerClass;

    public FilterGraphEffect(EffectContext effectContext, String string2, String string3, String string4, String string5, Class class_) {
        super(effectContext, string2);
        this.mInputName = string4;
        this.mOutputName = string5;
        this.mSchedulerClass = class_;
        this.createGraph(string3);
    }

    private void createGraph(String string2) {
        TextGraphReader textGraphReader = new TextGraphReader();
        try {
            this.mGraph = ((GraphReader)textGraphReader).readGraphString(string2);
            if (this.mGraph != null) {
                this.mRunner = new SyncRunner(this.getFilterContext(), this.mGraph, this.mSchedulerClass);
                return;
            }
            throw new RuntimeException("Could not setup effect");
        }
        catch (GraphIOException graphIOException) {
            throw new RuntimeException("Could not setup effect", graphIOException);
        }
    }

    @Override
    public void apply(int n, int n2, int n3, int n4) {
        this.beginGLEffect();
        Filter filter = this.mGraph.getFilter(this.mInputName);
        if (filter != null) {
            filter.setInputValue("texId", n);
            filter.setInputValue("width", n2);
            filter.setInputValue("height", n3);
            filter = this.mGraph.getFilter(this.mOutputName);
            if (filter != null) {
                filter.setInputValue("texId", n4);
                try {
                    this.mRunner.run();
                }
                catch (RuntimeException runtimeException) {
                    throw new RuntimeException("Internal error applying effect: ", runtimeException);
                }
                this.endGLEffect();
                return;
            }
            throw new RuntimeException("Internal error applying effect");
        }
        throw new RuntimeException("Internal error applying effect");
    }

    @Override
    public void release() {
        this.mGraph.tearDown(this.getFilterContext());
        this.mGraph = null;
    }

    @Override
    public void setParameter(String string2, Object object) {
    }
}

