/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.graphics.Path;
import android.text.Layout;

public final class _$$Lambda$Layout$MzjK2UE2G8VG0asK8_KWY3gHAmY
implements Layout.SelectionRectangleConsumer {
    private final /* synthetic */ Path f$0;

    public /* synthetic */ _$$Lambda$Layout$MzjK2UE2G8VG0asK8_KWY3gHAmY(Path path) {
        this.f$0 = path;
    }

    @Override
    public final void accept(float f, float f2, float f3, float f4, int n) {
        Layout.lambda$getSelectionPath$0(this.f$0, f, f2, f3, f4, n);
    }
}

