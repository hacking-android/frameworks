/*
 * Decompiled with CFR 0.145.
 */
package android.telecom.Logging;

import android.telecom.Logging.EventManager;
import java.util.function.Consumer;

public final class _$$Lambda$EventManager$uddp6XAJ90VBwdTiuzBdSYelntQ
implements Consumer {
    private final /* synthetic */ EventManager f$0;

    public /* synthetic */ _$$Lambda$EventManager$uddp6XAJ90VBwdTiuzBdSYelntQ(EventManager eventManager) {
        this.f$0 = eventManager;
    }

    public final void accept(Object object) {
        this.f$0.lambda$changeEventCacheSize$1$EventManager((EventManager.EventRecord)object);
    }
}

