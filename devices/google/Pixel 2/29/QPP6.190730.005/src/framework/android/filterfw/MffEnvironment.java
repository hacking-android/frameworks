/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw;

import android.filterfw.core.CachedFrameManager;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLEnvironment;

public class MffEnvironment {
    private FilterContext mContext;

    protected MffEnvironment(FrameManager frameManager) {
        FrameManager frameManager2 = frameManager;
        if (frameManager == null) {
            frameManager2 = new CachedFrameManager();
        }
        this.mContext = new FilterContext();
        this.mContext.setFrameManager(frameManager2);
    }

    public void activateGLEnvironment() {
        if (this.mContext.getGLEnvironment() != null) {
            this.mContext.getGLEnvironment().activate();
            return;
        }
        throw new NullPointerException("No GLEnvironment in place to activate!");
    }

    public void createGLEnvironment() {
        GLEnvironment gLEnvironment = new GLEnvironment();
        gLEnvironment.initWithNewContext();
        this.setGLEnvironment(gLEnvironment);
    }

    public void deactivateGLEnvironment() {
        if (this.mContext.getGLEnvironment() != null) {
            this.mContext.getGLEnvironment().deactivate();
            return;
        }
        throw new NullPointerException("No GLEnvironment in place to deactivate!");
    }

    public FilterContext getContext() {
        return this.mContext;
    }

    public void setGLEnvironment(GLEnvironment gLEnvironment) {
        this.mContext.initGLEnvironment(gLEnvironment);
    }
}

