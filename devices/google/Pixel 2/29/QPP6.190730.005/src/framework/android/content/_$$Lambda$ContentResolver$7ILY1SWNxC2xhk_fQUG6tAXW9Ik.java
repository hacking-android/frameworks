/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.Int32Ref
 */
package android.content;

import android.content.ContentInterface;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.system.Int32Ref;
import java.util.concurrent.Callable;

public final class _$$Lambda$ContentResolver$7ILY1SWNxC2xhk_fQUG6tAXW9Ik
implements Callable {
    private final /* synthetic */ ContentInterface f$0;
    private final /* synthetic */ Uri f$1;
    private final /* synthetic */ Bundle f$2;
    private final /* synthetic */ CancellationSignal f$3;
    private final /* synthetic */ Int32Ref f$4;

    public /* synthetic */ _$$Lambda$ContentResolver$7ILY1SWNxC2xhk_fQUG6tAXW9Ik(ContentInterface contentInterface, Uri uri, Bundle bundle, CancellationSignal cancellationSignal, Int32Ref int32Ref) {
        this.f$0 = contentInterface;
        this.f$1 = uri;
        this.f$2 = bundle;
        this.f$3 = cancellationSignal;
        this.f$4 = int32Ref;
    }

    public final Object call() {
        return ContentResolver.lambda$loadThumbnail$0(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

