/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.-$
 *  android.graphics.drawable.-$$Lambda
 *  android.graphics.drawable.-$$Lambda$Drawable
 *  android.graphics.drawable.-$$Lambda$Drawable$bbJz2VgQAwkXlE27mR8nPMYacEw
 */
package android.graphics.drawable;

import android.graphics.ImageDecoder;
import android.graphics.drawable.-$;
import android.graphics.drawable.Drawable;

public final class _$$Lambda$Drawable$bbJz2VgQAwkXlE27mR8nPMYacEw
implements ImageDecoder.OnHeaderDecodedListener {
    public static final /* synthetic */ -$.Lambda.Drawable.bbJz2VgQAwkXlE27mR8nPMYacEw INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Drawable$bbJz2VgQAwkXlE27mR8nPMYacEw();
    }

    private /* synthetic */ _$$Lambda$Drawable$bbJz2VgQAwkXlE27mR8nPMYacEw() {
    }

    @Override
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        Drawable.lambda$getBitmapDrawable$1(imageDecoder, imageInfo, source);
    }
}

