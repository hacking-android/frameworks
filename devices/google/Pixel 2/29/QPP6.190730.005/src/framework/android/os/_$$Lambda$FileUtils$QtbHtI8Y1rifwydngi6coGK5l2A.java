/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.FileUtils;

public final class _$$Lambda$FileUtils$QtbHtI8Y1rifwydngi6coGK5l2A
implements Runnable {
    private final /* synthetic */ FileUtils.ProgressListener f$0;
    private final /* synthetic */ long f$1;

    public /* synthetic */ _$$Lambda$FileUtils$QtbHtI8Y1rifwydngi6coGK5l2A(FileUtils.ProgressListener progressListener, long l) {
        this.f$0 = progressListener;
        this.f$1 = l;
    }

    @Override
    public final void run() {
        FileUtils.lambda$copyInternalSendfile$2(this.f$0, this.f$1);
    }
}

