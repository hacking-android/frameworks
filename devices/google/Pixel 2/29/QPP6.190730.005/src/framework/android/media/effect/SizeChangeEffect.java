/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect;

import android.filterfw.core.FilterFunction;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class SizeChangeEffect
extends SingleFilterEffect {
    public SizeChangeEffect(EffectContext effectContext, String string2, Class class_, String string3, String string4, Object ... arrobject) {
        super(effectContext, string2, class_, string3, string4, arrobject);
    }

    @Override
    public void apply(int n, int n2, int n3, int n4) {
        this.beginGLEffect();
        Frame frame = this.frameFromTexture(n, n2, n3);
        Frame frame2 = this.mFunction.executeWithArgList(this.mInputName, frame);
        Frame frame3 = this.frameFromTexture(n4, frame2.getFormat().getWidth(), frame2.getFormat().getHeight());
        frame3.setDataFromFrame(frame2);
        frame.release();
        frame3.release();
        frame2.release();
        this.endGLEffect();
    }
}

