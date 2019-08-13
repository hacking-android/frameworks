/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.graphics.ImageDecoder;
import android.util.Size;
import com.android.internal.util.ImageUtils;

public final class _$$Lambda$ImageUtils$rnRZcgsdC1BtH9FpHTN2Kf_FXwE
implements ImageDecoder.OnHeaderDecodedListener {
    private final /* synthetic */ Size f$0;

    public /* synthetic */ _$$Lambda$ImageUtils$rnRZcgsdC1BtH9FpHTN2Kf_FXwE(Size size) {
        this.f$0 = size;
    }

    @Override
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        ImageUtils.lambda$loadThumbnail$1(this.f$0, imageDecoder, imageInfo, source);
    }
}

