/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.-$
 *  android.graphics.drawable.-$$Lambda
 *  android.graphics.drawable.-$$Lambda$BitmapDrawable
 *  android.graphics.drawable.-$$Lambda$BitmapDrawable$LMqt8JvxZ4giSOIRAtlCKDg39Jw
 */
package android.graphics.drawable;

import android.graphics.ImageDecoder;
import android.graphics.drawable.-$;
import android.graphics.drawable.BitmapDrawable;

public final class _$$Lambda$BitmapDrawable$LMqt8JvxZ4giSOIRAtlCKDg39Jw
implements ImageDecoder.OnHeaderDecodedListener {
    public static final /* synthetic */ -$.Lambda.BitmapDrawable.LMqt8JvxZ4giSOIRAtlCKDg39Jw INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$BitmapDrawable$LMqt8JvxZ4giSOIRAtlCKDg39Jw();
    }

    private /* synthetic */ _$$Lambda$BitmapDrawable$LMqt8JvxZ4giSOIRAtlCKDg39Jw() {
    }

    @Override
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        BitmapDrawable.lambda$updateStateFromTypedArray$2(imageDecoder, imageInfo, source);
    }
}

