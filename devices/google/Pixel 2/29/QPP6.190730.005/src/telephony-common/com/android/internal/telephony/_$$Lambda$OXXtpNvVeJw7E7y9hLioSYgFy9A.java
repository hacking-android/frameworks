/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.CellInfo
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$OXXtpNvVeJw7E7y9hLioSYgFy9A
 */
package com.android.internal.telephony;

import android.telephony.CellInfo;
import com.android.internal.telephony.-$;
import java.util.function.Function;

public final class _$$Lambda$OXXtpNvVeJw7E7y9hLioSYgFy9A
implements Function {
    public static final /* synthetic */ -$.Lambda.OXXtpNvVeJw7E7y9hLioSYgFy9A INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$OXXtpNvVeJw7E7y9hLioSYgFy9A();
    }

    private /* synthetic */ _$$Lambda$OXXtpNvVeJw7E7y9hLioSYgFy9A() {
    }

    public final Object apply(Object object) {
        return ((CellInfo)object).sanitizeLocationInfo();
    }
}

