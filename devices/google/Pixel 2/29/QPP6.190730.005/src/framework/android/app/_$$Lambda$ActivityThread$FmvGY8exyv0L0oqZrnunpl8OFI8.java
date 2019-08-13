/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.ActivityThread;
import android.os.RemoteCallback;
import java.util.List;
import java.util.function.Consumer;

public final class _$$Lambda$ActivityThread$FmvGY8exyv0L0oqZrnunpl8OFI8
implements Consumer {
    private final /* synthetic */ ActivityThread.ActivityClientRecord f$0;
    private final /* synthetic */ RemoteCallback f$1;

    public /* synthetic */ _$$Lambda$ActivityThread$FmvGY8exyv0L0oqZrnunpl8OFI8(ActivityThread.ActivityClientRecord activityClientRecord, RemoteCallback remoteCallback) {
        this.f$0 = activityClientRecord;
        this.f$1 = remoteCallback;
    }

    public final void accept(Object object) {
        ActivityThread.lambda$handleRequestDirectActions$0(this.f$0, this.f$1, (List)object);
    }
}

