/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.autofill.-$
 *  android.service.autofill.-$$Lambda
 *  android.service.autofill.-$$Lambda$eWz26esczusoIA84WEwFlxQuDGQ
 */
package android.service.autofill;

import android.service.autofill.-$;
import android.service.autofill.AutofillService;
import java.util.function.Consumer;

public final class _$$Lambda$eWz26esczusoIA84WEwFlxQuDGQ
implements Consumer {
    public static final /* synthetic */ -$.Lambda.eWz26esczusoIA84WEwFlxQuDGQ INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$eWz26esczusoIA84WEwFlxQuDGQ();
    }

    private /* synthetic */ _$$Lambda$eWz26esczusoIA84WEwFlxQuDGQ() {
    }

    public final void accept(Object object) {
        ((AutofillService)object).onDisconnected();
    }
}

