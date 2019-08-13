/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.widget.-$
 *  android.widget.-$$Lambda
 *  android.widget.-$$Lambda$ImageView
 *  android.widget.-$$Lambda$ImageView$GWf2-Z-LHjSbTbrF-I3WzfR0LeM
 */
package android.widget;

import android.graphics.ImageDecoder;
import android.widget.-$;
import android.widget.ImageView;

public final class _$$Lambda$ImageView$GWf2_Z_LHjSbTbrF_I3WzfR0LeM
implements ImageDecoder.OnHeaderDecodedListener {
    public static final /* synthetic */ -$.Lambda.ImageView.GWf2-Z-LHjSbTbrF-I3WzfR0LeM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ImageView$GWf2_Z_LHjSbTbrF_I3WzfR0LeM();
    }

    private /* synthetic */ _$$Lambda$ImageView$GWf2_Z_LHjSbTbrF_I3WzfR0LeM() {
    }

    @Override
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        ImageView.lambda$getDrawableFromUri$0(imageDecoder, imageInfo, source);
    }
}

