/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.pm.-$
 *  android.content.pm.-$$Lambda
 *  android.content.pm.-$$Lambda$T1UQAuePWRRmVQ1KzTyMAktZUPM
 */
package android.content.pm;

import android.content.pm.-$;
import android.content.pm.PackageInstaller;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$T1UQAuePWRRmVQ1KzTyMAktZUPM
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.T1UQAuePWRRmVQ1KzTyMAktZUPM INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$T1UQAuePWRRmVQ1KzTyMAktZUPM();
    }

    private /* synthetic */ _$$Lambda$T1UQAuePWRRmVQ1KzTyMAktZUPM() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((PackageInstaller.SessionCallback)object).onActiveChanged((Integer)object2, (Boolean)object3);
    }
}

