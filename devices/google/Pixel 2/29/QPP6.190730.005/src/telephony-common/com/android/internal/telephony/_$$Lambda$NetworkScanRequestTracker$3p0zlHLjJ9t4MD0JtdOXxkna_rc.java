/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.CellInfo
 */
package com.android.internal.telephony;

import android.telephony.CellInfo;
import com.android.internal.telephony.NetworkScanRequestTracker;
import java.util.Set;
import java.util.function.Predicate;

public final class _$$Lambda$NetworkScanRequestTracker$3p0zlHLjJ9t4MD0JtdOXxkna_rc
implements Predicate {
    private final /* synthetic */ Set f$0;

    public /* synthetic */ _$$Lambda$NetworkScanRequestTracker$3p0zlHLjJ9t4MD0JtdOXxkna_rc(Set set) {
        this.f$0 = set;
    }

    public final boolean test(Object object) {
        return NetworkScanRequestTracker.lambda$notifyMessenger$1(this.f$0, (CellInfo)object);
    }
}

