/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import com.android.internal.os.BatteryStatsImpl;
import com.android.internal.os.KernelCpuUidTimeReader;

public final class _$$Lambda$BatteryStatsImpl$B_TmZhQb712ePnuJTxvMe7P_YwQ
implements KernelCpuUidTimeReader.Callback {
    private final /* synthetic */ BatteryStatsImpl f$0;
    private final /* synthetic */ boolean f$1;
    private final /* synthetic */ boolean f$2;
    private final /* synthetic */ boolean f$3;
    private final /* synthetic */ int f$4;
    private final /* synthetic */ int f$5;

    public /* synthetic */ _$$Lambda$BatteryStatsImpl$B_TmZhQb712ePnuJTxvMe7P_YwQ(BatteryStatsImpl batteryStatsImpl, boolean bl, boolean bl2, boolean bl3, int n, int n2) {
        this.f$0 = batteryStatsImpl;
        this.f$1 = bl;
        this.f$2 = bl2;
        this.f$3 = bl3;
        this.f$4 = n;
        this.f$5 = n2;
    }

    public final void onUidCpuTime(int n, Object object) {
        this.f$0.lambda$readKernelUidCpuFreqTimesLocked$1$BatteryStatsImpl(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, n, (long[])object);
    }
}

