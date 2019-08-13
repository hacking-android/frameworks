/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.-$
 *  android.os.-$$Lambda
 *  android.os.-$$Lambda$IyvVQC-0mKtsfXbnO0kDL64hrk0
 */
package android.os;

import android.os.-$;
import android.os.BatteryStats;
import android.os.UserHandle;

public final class _$$Lambda$IyvVQC_0mKtsfXbnO0kDL64hrk0
implements BatteryStats.IntToString {
    public static final /* synthetic */ -$.Lambda.IyvVQC-0mKtsfXbnO0kDL64hrk0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$IyvVQC_0mKtsfXbnO0kDL64hrk0();
    }

    private /* synthetic */ _$$Lambda$IyvVQC_0mKtsfXbnO0kDL64hrk0() {
    }

    @Override
    public final String applyAsString(int n) {
        return UserHandle.formatUid(n);
    }
}

