/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.graphics.ImageDecoder;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;

public final class _$$Lambda$NinePatchDrawable$yQvfm7FAkslD5wdGFysjgwt8cLE
implements ImageDecoder.OnHeaderDecodedListener {
    private final /* synthetic */ Rect f$0;

    public /* synthetic */ _$$Lambda$NinePatchDrawable$yQvfm7FAkslD5wdGFysjgwt8cLE(Rect rect) {
        this.f$0 = rect;
    }

    @Override
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        NinePatchDrawable.lambda$updateStateFromTypedArray$0(this.f$0, imageDecoder, imageInfo, source);
    }
}

