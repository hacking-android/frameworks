/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterpacks.imageproc.DocumentaryFilter;
import android.media.effect.EffectContext;
import android.media.effect.SingleFilterEffect;

public class DocumentaryEffect
extends SingleFilterEffect {
    public DocumentaryEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, DocumentaryFilter.class, "image", "image", new Object[0]);
    }
}

