/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityManager;
import android.net.Network;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;

public final class _$$Lambda$ConnectivityManager$3$BfAvTRJTF0an2PdeqkENEBULYBU
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ ConnectivityManager.OnTetheringEventCallback f$1;
    private final /* synthetic */ Network f$2;

    public /* synthetic */ _$$Lambda$ConnectivityManager$3$BfAvTRJTF0an2PdeqkENEBULYBU(Executor executor, ConnectivityManager.OnTetheringEventCallback onTetheringEventCallback, Network network) {
        this.f$0 = executor;
        this.f$1 = onTetheringEventCallback;
        this.f$2 = network;
    }

    @Override
    public final void runOrThrow() {
        ConnectivityManager.3.lambda$onUpstreamChanged$1(this.f$0, this.f$1, this.f$2);
    }
}

