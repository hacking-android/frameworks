/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect;

import android.filterfw.core.CachedFrameManager;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLEnvironment;
import android.media.effect.EffectFactory;
import android.opengl.GLES20;

public class EffectContext {
    private final int GL_STATE_ARRAYBUFFER;
    private final int GL_STATE_COUNT;
    private final int GL_STATE_FBO;
    private final int GL_STATE_PROGRAM;
    private EffectFactory mFactory;
    FilterContext mFilterContext = new FilterContext();
    private int[] mOldState = new int[3];

    private EffectContext() {
        this.GL_STATE_FBO = 0;
        this.GL_STATE_PROGRAM = 1;
        this.GL_STATE_ARRAYBUFFER = 2;
        this.GL_STATE_COUNT = 3;
        this.mFilterContext.setFrameManager(new CachedFrameManager());
        this.mFactory = new EffectFactory(this);
    }

    public static EffectContext createWithCurrentGlContext() {
        EffectContext effectContext = new EffectContext();
        effectContext.initInCurrentGlContext();
        return effectContext;
    }

    private void initInCurrentGlContext() {
        if (GLEnvironment.isAnyContextActive()) {
            GLEnvironment gLEnvironment = new GLEnvironment();
            gLEnvironment.initWithCurrentContext();
            this.mFilterContext.initGLEnvironment(gLEnvironment);
            return;
        }
        throw new RuntimeException("Attempting to initialize EffectContext with no active GL context!");
    }

    final void assertValidGLState() {
        GLEnvironment gLEnvironment = this.mFilterContext.getGLEnvironment();
        if (gLEnvironment != null && gLEnvironment.isContextActive()) {
            return;
        }
        if (GLEnvironment.isAnyContextActive()) {
            throw new RuntimeException("Applying effect in wrong GL context!");
        }
        throw new RuntimeException("Attempting to apply effect without valid GL context!");
    }

    public EffectFactory getFactory() {
        return this.mFactory;
    }

    public void release() {
        this.mFilterContext.tearDown();
        this.mFilterContext = null;
    }

    final void restoreGLState() {
        GLES20.glBindFramebuffer(36160, this.mOldState[0]);
        GLES20.glUseProgram(this.mOldState[1]);
        GLES20.glBindBuffer(34962, this.mOldState[2]);
    }

    final void saveGLState() {
        GLES20.glGetIntegerv(36006, this.mOldState, 0);
        GLES20.glGetIntegerv(35725, this.mOldState, 1);
        GLES20.glGetIntegerv(34964, this.mOldState, 2);
    }
}

