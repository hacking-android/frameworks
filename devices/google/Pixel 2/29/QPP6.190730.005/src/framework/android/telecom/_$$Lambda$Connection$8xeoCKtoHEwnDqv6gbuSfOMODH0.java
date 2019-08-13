/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.telecom.Connection;
import java.util.function.Consumer;

public final class _$$Lambda$Connection$8xeoCKtoHEwnDqv6gbuSfOMODH0
implements Consumer {
    private final /* synthetic */ Connection f$0;

    public /* synthetic */ _$$Lambda$Connection$8xeoCKtoHEwnDqv6gbuSfOMODH0(Connection connection) {
        this.f$0 = connection;
    }

    public final void accept(Object object) {
        this.f$0.lambda$sendRttInitiationSuccess$0$Connection((Connection.Listener)object);
    }
}

