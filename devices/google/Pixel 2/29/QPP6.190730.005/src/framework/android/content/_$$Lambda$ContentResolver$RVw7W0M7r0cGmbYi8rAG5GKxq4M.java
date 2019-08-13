/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.ContentResolver;
import android.graphics.ImageDecoder;
import android.os.CancellationSignal;
import android.util.Size;

public final class _$$Lambda$ContentResolver$RVw7W0M7r0cGmbYi8rAG5GKxq4M
implements ImageDecoder.OnHeaderDecodedListener {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ CancellationSignal f$1;
    private final /* synthetic */ Size f$2;

    public /* synthetic */ _$$Lambda$ContentResolver$RVw7W0M7r0cGmbYi8rAG5GKxq4M(int n, CancellationSignal cancellationSignal, Size size) {
        this.f$0 = n;
        this.f$1 = cancellationSignal;
        this.f$2 = size;
    }

    @Override
    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        ContentResolver.lambda$loadThumbnail$1(this.f$0, this.f$1, this.f$2, imageDecoder, imageInfo, source);
    }
}

