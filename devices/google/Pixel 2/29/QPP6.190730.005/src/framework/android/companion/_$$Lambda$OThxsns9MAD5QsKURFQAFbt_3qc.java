/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.companion.-$
 *  android.companion.-$$Lambda
 *  android.companion.-$$Lambda$OThxsns9MAD5QsKURFQAFbt-3qc
 */
package android.companion;

import android.companion.-$;
import android.companion.CompanionDeviceManager;
import android.content.IntentSender;
import java.util.function.BiConsumer;

public final class _$$Lambda$OThxsns9MAD5QsKURFQAFbt_3qc
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.OThxsns9MAD5QsKURFQAFbt-3qc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$OThxsns9MAD5QsKURFQAFbt_3qc();
    }

    private /* synthetic */ _$$Lambda$OThxsns9MAD5QsKURFQAFbt_3qc() {
    }

    public final void accept(Object object, Object object2) {
        ((CompanionDeviceManager.Callback)object).onDeviceFound((IntentSender)object2);
    }
}

