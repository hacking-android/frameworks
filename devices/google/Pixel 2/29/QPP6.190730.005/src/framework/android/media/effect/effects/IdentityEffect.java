/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterfw.core.Frame;
import android.media.effect.EffectContext;
import android.media.effect.FilterEffect;

public class IdentityEffect
extends FilterEffect {
    public IdentityEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2);
    }

    @Override
    public void apply(int n, int n2, int n3, int n4) {
        this.beginGLEffect();
        Frame frame = this.frameFromTexture(n, n2, n3);
        Frame frame2 = this.frameFromTexture(n4, n2, n3);
        frame2.setDataFromFrame(frame);
        frame.release();
        frame2.release();
        this.endGLEffect();
    }

    @Override
    public void release() {
    }

    @Override
    public void setParameter(String string2, Object object) {
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown parameter ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" for IdentityEffect!");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }
}

