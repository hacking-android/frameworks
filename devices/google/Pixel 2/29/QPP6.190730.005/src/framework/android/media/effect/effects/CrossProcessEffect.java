/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.CrossProcessFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class CrossProcessEffect
extends SingleFilterEffect {
    public CrossProcessEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, CrossProcessFilter.class, "image", "image", new Object[0]);
    }
}

