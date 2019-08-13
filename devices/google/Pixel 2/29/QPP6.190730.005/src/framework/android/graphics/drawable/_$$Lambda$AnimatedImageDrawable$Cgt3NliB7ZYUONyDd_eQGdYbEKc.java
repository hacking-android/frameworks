/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.-$
 *  android.graphics.drawable.-$$Lambda
 *  android.graphics.drawable.-$$Lambda$AnimatedImageDrawable
 *  android.graphics.drawable.-$$Lambda$AnimatedImageDrawable$Cgt3NliB7ZYUONyDd-eQGdYbEKc
 */
package android.graphics.drawable;

import android.graphics.ImageDecoder;
import android.graphics.drawable.-$;
import android.graphics.drawable.AnimatedImageDrawable;

public final class _$$Lambda$AnimatedImageDrawable$Cgt3NliB7ZYUONyDd_eQGdYbEKc
implements ImageDecoder.OnHeaderDecodedListener {
    public static final /* synthetic */ -$.Lambda.AnimatedImageDrawable.Cgt3NliB7ZYUONyDd-eQGdYbEKc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$AnimatedImageDrawable$Cgt3NliB7ZYUONyDd_eQGdYbEKc();
    }

    private /* synthetic */ _$$Lambda$AnimatedImageDrawable$Cgt3NliB7ZYUONyDd_eQGdYbEKc() {
    }

    @Override
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        AnimatedImageDrawable.lambda$updateStateFromTypedArray$0(imageDecoder, imageInfo, source);
    }
}

