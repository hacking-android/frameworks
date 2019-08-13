/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.RedEyeFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class RedEyeEffect
extends SingleFilterEffect {
    public RedEyeEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, RedEyeFilter.class, "image", "image", new Object[0]);
    }
}

