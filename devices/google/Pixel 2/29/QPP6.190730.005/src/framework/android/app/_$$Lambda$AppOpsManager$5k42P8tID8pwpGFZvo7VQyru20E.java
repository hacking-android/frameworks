/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.AppOpsManager;
import android.os.Bundle;
import android.os.RemoteCallback;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class _$$Lambda$AppOpsManager$5k42P8tID8pwpGFZvo7VQyru20E
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ Consumer f$1;

    public /* synthetic */ _$$Lambda$AppOpsManager$5k42P8tID8pwpGFZvo7VQyru20E(Executor executor, Consumer consumer) {
        this.f$0 = executor;
        this.f$1 = consumer;
    }

    @Override
    public final void onResult(Bundle bundle) {
        AppOpsManager.lambda$getHistoricalOpsFromDiskRaw$3(this.f$0, this.f$1, bundle);
    }
}

