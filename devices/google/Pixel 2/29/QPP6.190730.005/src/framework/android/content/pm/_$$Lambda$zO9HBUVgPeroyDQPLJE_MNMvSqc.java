/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.pm.-$
 *  android.content.pm.-$$Lambda
 *  android.content.pm.-$$Lambda$zO9HBUVgPeroyDQPLJE-MNMvSqc
 */
package android.content.pm;

import android.content.pm.-$;
import android.content.pm.PackageInstaller;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$zO9HBUVgPeroyDQPLJE_MNMvSqc
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.zO9HBUVgPeroyDQPLJE-MNMvSqc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$zO9HBUVgPeroyDQPLJE_MNMvSqc();
    }

    private /* synthetic */ _$$Lambda$zO9HBUVgPeroyDQPLJE_MNMvSqc() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((PackageInstaller.SessionCallback)object).onFinished((Integer)object2, (Boolean)object3);
    }
}

