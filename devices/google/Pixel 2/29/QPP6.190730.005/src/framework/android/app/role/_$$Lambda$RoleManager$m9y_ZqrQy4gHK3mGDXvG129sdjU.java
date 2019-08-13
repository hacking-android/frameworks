/*
 * Decompiled with CFR 0.145.
 */
package android.app.role;

import android.app.role.RoleManager;
import android.os.Bundle;
import android.os.RemoteCallback;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class _$$Lambda$RoleManager$m9y_ZqrQy4gHK3mGDXvG129sdjU
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ Consumer f$1;

    public /* synthetic */ _$$Lambda$RoleManager$m9y_ZqrQy4gHK3mGDXvG129sdjU(Executor executor, Consumer consumer) {
        this.f$0 = executor;
        this.f$1 = consumer;
    }

    @Override
    public final void onResult(Bundle bundle) {
        RoleManager.lambda$createRemoteCallback$1(this.f$0, this.f$1, bundle);
    }
}

