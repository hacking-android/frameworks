/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.-$
 *  android.graphics.drawable.-$$Lambda
 *  android.graphics.drawable.-$$Lambda$BitmapDrawable
 *  android.graphics.drawable.-$$Lambda$BitmapDrawable$T1BUUqQwU4Z6Ve8DJHFuQvYohkY
 */
package android.graphics.drawable;

import android.graphics.ImageDecoder;
import android.graphics.drawable.-$;
import android.graphics.drawable.BitmapDrawable;

public final class _$$Lambda$BitmapDrawable$T1BUUqQwU4Z6Ve8DJHFuQvYohkY
implements ImageDecoder.OnHeaderDecodedListener {
    public static final /* synthetic */ -$.Lambda.BitmapDrawable.T1BUUqQwU4Z6Ve8DJHFuQvYohkY INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$BitmapDrawable$T1BUUqQwU4Z6Ve8DJHFuQvYohkY();
    }

    private /* synthetic */ _$$Lambda$BitmapDrawable$T1BUUqQwU4Z6Ve8DJHFuQvYohkY() {
    }

    @Override
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        BitmapDrawable.lambda$new$1(imageDecoder, imageInfo, source);
    }
}

