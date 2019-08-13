/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.res.-$
 *  android.content.res.-$$Lambda
 *  android.content.res.-$$Lambda$ResourcesImpl
 *  android.content.res.-$$Lambda$ResourcesImpl$99dm2ENnzo9b0SIUjUj2Kl3pi90
 */
package android.content.res;

import android.content.res.-$;
import android.content.res.ResourcesImpl;
import android.graphics.ImageDecoder;

public final class _$$Lambda$ResourcesImpl$99dm2ENnzo9b0SIUjUj2Kl3pi90
implements ImageDecoder.OnHeaderDecodedListener {
    public static final /* synthetic */ -$.Lambda.ResourcesImpl.99dm2ENnzo9b0SIUjUj2Kl3pi90 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ResourcesImpl$99dm2ENnzo9b0SIUjUj2Kl3pi90();
    }

    private /* synthetic */ _$$Lambda$ResourcesImpl$99dm2ENnzo9b0SIUjUj2Kl3pi90() {
    }

    @Override
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        ResourcesImpl.lambda$decodeImageDrawable$1(imageDecoder, imageInfo, source);
    }
}

