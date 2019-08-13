/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.pm.-$
 *  android.content.pm.-$$Lambda
 *  android.content.pm.-$$Lambda$ciir_QAmv6RwJro4I58t77dPnxU
 */
package android.content.pm;

import android.content.pm.-$;
import android.content.pm.PackageInstaller;
import java.util.function.BiConsumer;

public final class _$$Lambda$ciir_QAmv6RwJro4I58t77dPnxU
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.ciir_QAmv6RwJro4I58t77dPnxU INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ciir_QAmv6RwJro4I58t77dPnxU();
    }

    private /* synthetic */ _$$Lambda$ciir_QAmv6RwJro4I58t77dPnxU() {
    }

    public final void accept(Object object, Object object2) {
        ((PackageInstaller.SessionCallback)object).onCreated((Integer)object2);
    }
}

