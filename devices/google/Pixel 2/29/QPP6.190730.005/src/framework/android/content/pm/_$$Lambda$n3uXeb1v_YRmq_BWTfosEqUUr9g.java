/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.pm.-$
 *  android.content.pm.-$$Lambda
 *  android.content.pm.-$$Lambda$n3uXeb1v-YRmq_BWTfosEqUUr9g
 */
package android.content.pm;

import android.content.pm.-$;
import android.content.pm.PackageInstaller;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$n3uXeb1v_YRmq_BWTfosEqUUr9g
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.n3uXeb1v-YRmq_BWTfosEqUUr9g INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$n3uXeb1v_YRmq_BWTfosEqUUr9g();
    }

    private /* synthetic */ _$$Lambda$n3uXeb1v_YRmq_BWTfosEqUUr9g() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((PackageInstaller.SessionCallback)object).onProgressChanged((Integer)object2, ((Float)object3).floatValue());
    }
}

