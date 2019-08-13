/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.pm.-$
 *  android.content.pm.-$$Lambda
 *  android.content.pm.-$$Lambda$B12dZLpdwpXn89QSesmkaZjD72Q
 */
package android.content.pm;

import android.content.pm.-$;
import android.content.pm.PackageInstaller;
import java.util.function.BiConsumer;

public final class _$$Lambda$B12dZLpdwpXn89QSesmkaZjD72Q
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.B12dZLpdwpXn89QSesmkaZjD72Q INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$B12dZLpdwpXn89QSesmkaZjD72Q();
    }

    private /* synthetic */ _$$Lambda$B12dZLpdwpXn89QSesmkaZjD72Q() {
    }

    public final void accept(Object object, Object object2) {
        ((PackageInstaller.SessionCallback)object).onBadgingChanged((Integer)object2);
    }
}

