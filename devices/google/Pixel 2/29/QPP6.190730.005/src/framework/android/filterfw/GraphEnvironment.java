/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.filterfw.MffEnvironment;
import android.filterfw.core.AsyncRunner;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GraphRunner;
import android.filterfw.core.RoundRobinScheduler;
import android.filterfw.core.SyncRunner;
import android.filterfw.io.GraphIOException;
import android.filterfw.io.GraphReader;
import android.filterfw.io.TextGraphReader;
import java.util.ArrayList;

public class GraphEnvironment
extends MffEnvironment {
    public static final int MODE_ASYNCHRONOUS = 1;
    public static final int MODE_SYNCHRONOUS = 2;
    private GraphReader mGraphReader;
    private ArrayList<GraphHandle> mGraphs = new ArrayList();

    @UnsupportedAppUsage
    public GraphEnvironment() {
        super(null);
    }

    public GraphEnvironment(FrameManager frameManager, GraphReader graphReader) {
        super(frameManager);
        this.mGraphReader = graphReader;
    }

    public int addGraph(FilterGraph object) {
        object = new GraphHandle((FilterGraph)object);
        this.mGraphs.add((GraphHandle)object);
        return this.mGraphs.size() - 1;
    }

    @UnsupportedAppUsage
    public void addReferences(Object ... arrobject) {
        this.getGraphReader().addReferencesByKeysAndValues(arrobject);
    }

    public FilterGraph getGraph(int n) {
        if (n >= 0 && n < this.mGraphs.size()) {
            return this.mGraphs.get(n).getGraph();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid graph ID ");
        stringBuilder.append(n);
        stringBuilder.append(" specified in runGraph()!");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public GraphReader getGraphReader() {
        if (this.mGraphReader == null) {
            this.mGraphReader = new TextGraphReader();
        }
        return this.mGraphReader;
    }

    @UnsupportedAppUsage
    public GraphRunner getRunner(int n, int n2) {
        if (n2 != 1) {
            if (n2 == 2) {
                return this.mGraphs.get(n).getSyncRunner(this.getContext());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid execution mode ");
            stringBuilder.append(n2);
            stringBuilder.append(" specified in getRunner()!");
            throw new RuntimeException(stringBuilder.toString());
        }
        return this.mGraphs.get(n).getAsyncRunner(this.getContext());
    }

    @UnsupportedAppUsage
    public int loadGraph(Context object, int n) {
        try {
            object = this.getGraphReader().readGraphResource((Context)object, n);
            return this.addGraph((FilterGraph)object);
        }
        catch (GraphIOException graphIOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not read graph: ");
            ((StringBuilder)object).append(graphIOException.getMessage());
            throw new RuntimeException(((StringBuilder)object).toString());
        }
    }

    private class GraphHandle {
        private AsyncRunner mAsyncRunner;
        private FilterGraph mGraph;
        private SyncRunner mSyncRunner;

        public GraphHandle(FilterGraph filterGraph) {
            this.mGraph = filterGraph;
        }

        public AsyncRunner getAsyncRunner(FilterContext filterContext) {
            if (this.mAsyncRunner == null) {
                this.mAsyncRunner = new AsyncRunner(filterContext, RoundRobinScheduler.class);
                this.mAsyncRunner.setGraph(this.mGraph);
            }
            return this.mAsyncRunner;
        }

        public FilterGraph getGraph() {
            return this.mGraph;
        }

        public GraphRunner getSyncRunner(FilterContext filterContext) {
            if (this.mSyncRunner == null) {
                this.mSyncRunner = new SyncRunner(filterContext, this.mGraph, RoundRobinScheduler.class);
            }
            return this.mSyncRunner;
        }
    }

}

