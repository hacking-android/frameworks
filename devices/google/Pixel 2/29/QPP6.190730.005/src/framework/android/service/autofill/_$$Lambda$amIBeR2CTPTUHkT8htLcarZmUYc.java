/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.autofill.-$
 *  android.service.autofill.-$$Lambda
 *  android.service.autofill.-$$Lambda$amIBeR2CTPTUHkT8htLcarZmUYc
 */
package android.service.autofill;

import android.service.autofill.-$;
import android.service.autofill.AutofillService;
import java.util.function.Consumer;

public final class _$$Lambda$amIBeR2CTPTUHkT8htLcarZmUYc
implements Consumer {
    public static final /* synthetic */ -$.Lambda.amIBeR2CTPTUHkT8htLcarZmUYc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$amIBeR2CTPTUHkT8htLcarZmUYc();
    }

    private /* synthetic */ _$$Lambda$amIBeR2CTPTUHkT8htLcarZmUYc() {
    }

    public final void accept(Object object) {
        ((AutofillService)object).onConnected();
    }
}

