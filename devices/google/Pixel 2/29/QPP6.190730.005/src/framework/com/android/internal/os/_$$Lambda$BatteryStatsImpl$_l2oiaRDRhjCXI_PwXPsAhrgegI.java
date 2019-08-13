/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import com.android.internal.os.BatteryStatsImpl;
import com.android.internal.os.KernelCpuUidTimeReader;

public final class _$$Lambda$BatteryStatsImpl$_l2oiaRDRhjCXI_PwXPsAhrgegI
implements KernelCpuUidTimeReader.Callback {
    private final /* synthetic */ BatteryStatsImpl f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$BatteryStatsImpl$_l2oiaRDRhjCXI_PwXPsAhrgegI(BatteryStatsImpl batteryStatsImpl, boolean bl) {
        this.f$0 = batteryStatsImpl;
        this.f$1 = bl;
    }

    public final void onUidCpuTime(int n, Object object) {
        this.f$0.lambda$readKernelUidCpuActiveTimesLocked$2$BatteryStatsImpl(this.f$1, n, (Long)object);
    }
}

