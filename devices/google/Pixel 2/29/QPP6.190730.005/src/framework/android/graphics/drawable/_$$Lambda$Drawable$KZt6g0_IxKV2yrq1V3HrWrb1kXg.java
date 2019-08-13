/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.-$
 *  android.graphics.drawable.-$$Lambda
 *  android.graphics.drawable.-$$Lambda$Drawable
 *  android.graphics.drawable.-$$Lambda$Drawable$KZt6g0-IxKV2yrq1V3HrWrb1kXg
 */
package android.graphics.drawable;

import android.graphics.ImageDecoder;
import android.graphics.drawable.-$;
import android.graphics.drawable.Drawable;

public final class _$$Lambda$Drawable$KZt6g0_IxKV2yrq1V3HrWrb1kXg
implements ImageDecoder.OnPartialImageListener {
    public static final /* synthetic */ -$.Lambda.Drawable.KZt6g0-IxKV2yrq1V3HrWrb1kXg INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Drawable$KZt6g0_IxKV2yrq1V3HrWrb1kXg();
    }

    private /* synthetic */ _$$Lambda$Drawable$KZt6g0_IxKV2yrq1V3HrWrb1kXg() {
    }

    @Override
    public final boolean onPartialImage(ImageDecoder.DecodeException decodeException) {
        return Drawable.lambda$getBitmapDrawable$0(decodeException);
    }
}

