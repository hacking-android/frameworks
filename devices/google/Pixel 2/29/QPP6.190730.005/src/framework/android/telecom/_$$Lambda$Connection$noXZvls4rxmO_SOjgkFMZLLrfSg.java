/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.telecom.Connection;
import java.util.function.Consumer;

public final class _$$Lambda$Connection$noXZvls4rxmO_SOjgkFMZLLrfSg
implements Consumer {
    private final /* synthetic */ Connection f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$Connection$noXZvls4rxmO_SOjgkFMZLLrfSg(Connection connection, int n) {
        this.f$0 = connection;
        this.f$1 = n;
    }

    public final void accept(Object object) {
        this.f$0.lambda$sendRttInitiationFailure$1$Connection(this.f$1, (Connection.Listener)object);
    }
}

