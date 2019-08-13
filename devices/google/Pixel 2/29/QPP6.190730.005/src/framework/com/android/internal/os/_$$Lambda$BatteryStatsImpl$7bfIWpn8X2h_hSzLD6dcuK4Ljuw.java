/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.util.SparseLongArray;
import com.android.internal.os.BatteryStatsImpl;
import com.android.internal.os.KernelCpuUidTimeReader;

public final class _$$Lambda$BatteryStatsImpl$7bfIWpn8X2h_hSzLD6dcuK4Ljuw
implements KernelCpuUidTimeReader.Callback {
    private final /* synthetic */ BatteryStatsImpl f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ boolean f$2;
    private final /* synthetic */ SparseLongArray f$3;

    public /* synthetic */ _$$Lambda$BatteryStatsImpl$7bfIWpn8X2h_hSzLD6dcuK4Ljuw(BatteryStatsImpl batteryStatsImpl, int n, boolean bl, SparseLongArray sparseLongArray) {
        this.f$0 = batteryStatsImpl;
        this.f$1 = n;
        this.f$2 = bl;
        this.f$3 = sparseLongArray;
    }

    public final void onUidCpuTime(int n, Object object) {
        this.f$0.lambda$readKernelUidCpuTimesLocked$0$BatteryStatsImpl(this.f$1, this.f$2, this.f$3, n, (long[])object);
    }
}

