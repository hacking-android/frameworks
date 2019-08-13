/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.-$
 *  android.telephony.-$$Lambda
 *  android.telephony.-$$Lambda$MLKtmRGKP3e0WU7x_KyS5-Vg8q4
 */
package android.telephony;

import android.telephony.-$;
import android.telephony.NetworkRegistrationInfo;
import java.util.function.Function;

public final class _$$Lambda$MLKtmRGKP3e0WU7x_KyS5_Vg8q4
implements Function {
    public static final /* synthetic */ -$.Lambda.MLKtmRGKP3e0WU7x_KyS5-Vg8q4 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$MLKtmRGKP3e0WU7x_KyS5_Vg8q4();
    }

    private /* synthetic */ _$$Lambda$MLKtmRGKP3e0WU7x_KyS5_Vg8q4() {
    }

    public final Object apply(Object object) {
        return ((NetworkRegistrationInfo)object).sanitizeLocationInfo();
    }
}

