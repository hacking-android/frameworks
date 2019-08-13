/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.Filter;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLEnvironment;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FilterContext {
    private FrameManager mFrameManager;
    private GLEnvironment mGLEnvironment;
    private Set<FilterGraph> mGraphs = new HashSet<FilterGraph>();
    private HashMap<String, Frame> mStoredFrames = new HashMap();

    final void addGraph(FilterGraph filterGraph) {
        this.mGraphs.add(filterGraph);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Frame fetchFrame(String object) {
        synchronized (this) {
            object = this.mStoredFrames.get(object);
            if (object != null) {
                ((Frame)object).onFrameFetch();
            }
            return object;
        }
    }

    @UnsupportedAppUsage
    public FrameManager getFrameManager() {
        return this.mFrameManager;
    }

    @UnsupportedAppUsage
    public GLEnvironment getGLEnvironment() {
        return this.mGLEnvironment;
    }

    public void initGLEnvironment(GLEnvironment gLEnvironment) {
        if (this.mGLEnvironment == null) {
            this.mGLEnvironment = gLEnvironment;
            return;
        }
        throw new RuntimeException("Attempting to re-initialize GL Environment for FilterContext!");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeFrame(String string2) {
        synchronized (this) {
            Frame frame = this.mStoredFrames.get(string2);
            if (frame != null) {
                this.mStoredFrames.remove(string2);
                frame.release();
            }
            return;
        }
    }

    public void setFrameManager(FrameManager frameManager) {
        if (frameManager != null) {
            if (frameManager.getContext() == null) {
                this.mFrameManager = frameManager;
                this.mFrameManager.setContext(this);
                return;
            }
            throw new IllegalArgumentException("Attempting to set FrameManager which is already bound to another FilterContext!");
        }
        throw new NullPointerException("Attempting to set null FrameManager!");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void storeFrame(String string2, Frame frame) {
        synchronized (this) {
            void var2_2;
            Frame frame2 = this.fetchFrame(string2);
            if (frame2 != null) {
                frame2.release();
            }
            var2_2.onFrameStore();
            this.mStoredFrames.put(string2, var2_2.retain());
            return;
        }
    }

    public void tearDown() {
        synchronized (this) {
            Iterator<Object> iterator = this.mStoredFrames.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().release();
            }
            this.mStoredFrames.clear();
            iterator = this.mGraphs.iterator();
            while (iterator.hasNext()) {
                ((FilterGraph)iterator.next()).tearDown(this);
            }
            this.mGraphs.clear();
            if (this.mFrameManager != null) {
                this.mFrameManager.tearDown();
                this.mFrameManager = null;
            }
            if (this.mGLEnvironment != null) {
                this.mGLEnvironment.tearDown();
                this.mGLEnvironment = null;
            }
            return;
        }
    }

    public static interface OnFrameReceivedListener {
        public void onFrameReceived(Filter var1, Frame var2, Object var3);
    }

}

