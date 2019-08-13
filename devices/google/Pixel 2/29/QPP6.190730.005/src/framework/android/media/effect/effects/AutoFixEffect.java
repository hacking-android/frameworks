/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.AutoFixFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class AutoFixEffect
extends SingleFilterEffect {
    public AutoFixEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, AutoFixFilter.class, "image", "image", new Object[0]);
    }
}

