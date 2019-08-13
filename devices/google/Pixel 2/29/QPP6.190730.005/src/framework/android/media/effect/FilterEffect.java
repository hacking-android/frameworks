/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect;

import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.format.ImageFormat;
import android.media.effect.Effect;
import android.media.effect.EffectContext;

public abstract class FilterEffect
extends Effect {
    protected EffectContext mEffectContext;
    private String mName;

    protected FilterEffect(EffectContext effectContext, String string2) {
        this.mEffectContext = effectContext;
        this.mName = string2;
    }

    protected void beginGLEffect() {
        this.mEffectContext.assertValidGLState();
        this.mEffectContext.saveGLState();
    }

    protected void endGLEffect() {
        this.mEffectContext.restoreGLState();
    }

    protected Frame frameFromTexture(int n, int n2, int n3) {
        Frame frame = this.getFilterContext().getFrameManager().newBoundFrame(ImageFormat.create(n2, n3, 3, 3), 100, n);
        frame.setTimestamp(-1L);
        return frame;
    }

    protected FilterContext getFilterContext() {
        return this.mEffectContext.mFilterContext;
    }

    @Override
    public String getName() {
        return this.mName;
    }
}

