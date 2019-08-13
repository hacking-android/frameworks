/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.-$
 *  android.graphics.drawable.-$$Lambda
 *  android.graphics.drawable.-$$Lambda$BitmapDrawable
 *  android.graphics.drawable.-$$Lambda$BitmapDrawable$23eAuhdkgEf5MIRJC-rMNbn4Pyg
 */
package android.graphics.drawable;

import android.graphics.ImageDecoder;
import android.graphics.drawable.-$;
import android.graphics.drawable.BitmapDrawable;

public final class _$$Lambda$BitmapDrawable$23eAuhdkgEf5MIRJC_rMNbn4Pyg
implements ImageDecoder.OnHeaderDecodedListener {
    public static final /* synthetic */ -$.Lambda.BitmapDrawable.23eAuhdkgEf5MIRJC-rMNbn4Pyg INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$BitmapDrawable$23eAuhdkgEf5MIRJC_rMNbn4Pyg();
    }

    private /* synthetic */ _$$Lambda$BitmapDrawable$23eAuhdkgEf5MIRJC_rMNbn4Pyg() {
    }

    @Override
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        BitmapDrawable.lambda$new$0(imageDecoder, imageInfo, source);
    }
}

