/*
 * Decompiled with CFR 0.145.
 */
package android.app.role;

import android.app.role.RoleManager;
import android.os.Bundle;
import java.util.function.Consumer;

public final class _$$Lambda$RoleManager$DrSVQgbDoLZaqkfPdGzAK3BvOGQ
implements Runnable {
    private final /* synthetic */ Bundle f$0;
    private final /* synthetic */ Consumer f$1;

    public /* synthetic */ _$$Lambda$RoleManager$DrSVQgbDoLZaqkfPdGzAK3BvOGQ(Bundle bundle, Consumer consumer) {
        this.f$0 = bundle;
        this.f$1 = consumer;
    }

    @Override
    public final void run() {
        RoleManager.lambda$createRemoteCallback$0(this.f$0, this.f$1);
    }
}

