/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.RegisteredServicesCache;
import android.content.pm.RegisteredServicesCacheListener;

public final class _$$Lambda$RegisteredServicesCache$lDXmLhKoG7lZpIyDOuPYOrjzDYY
implements Runnable {
    private final /* synthetic */ RegisteredServicesCacheListener f$0;
    private final /* synthetic */ Object f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ boolean f$3;

    public /* synthetic */ _$$Lambda$RegisteredServicesCache$lDXmLhKoG7lZpIyDOuPYOrjzDYY(RegisteredServicesCacheListener registeredServicesCacheListener, Object object, int n, boolean bl) {
        this.f$0 = registeredServicesCacheListener;
        this.f$1 = object;
        this.f$2 = n;
        this.f$3 = bl;
    }

    @Override
    public final void run() {
        RegisteredServicesCache.lambda$notifyListener$0(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}

