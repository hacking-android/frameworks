/*
 * Decompiled with CFR 0.145.
 */
package android.os.health;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.health.HealthStats;
import android.os.health.HealthStatsParceler;
import com.android.internal.app.IBatteryStats;

public class SystemHealthManager {
    private final IBatteryStats mBatteryStats;

    @UnsupportedAppUsage
    public SystemHealthManager() {
        this(IBatteryStats.Stub.asInterface(ServiceManager.getService("batterystats")));
    }

    public SystemHealthManager(IBatteryStats iBatteryStats) {
        this.mBatteryStats = iBatteryStats;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static SystemHealthManager from(Context context) {
        return (SystemHealthManager)context.getSystemService("systemhealth");
    }

    public HealthStats takeMyUidSnapshot() {
        return this.takeUidSnapshot(Process.myUid());
    }

    public HealthStats takeUidSnapshot(int n) {
        try {
            HealthStats healthStats = this.mBatteryStats.takeUidSnapshot(n).getHealthStats();
            return healthStats;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public HealthStats[] takeUidSnapshots(int[] arrn) {
        HealthStats[] arrhealthStats;
        HealthStatsParceler[] arrhealthStatsParceler;
        int n;
        try {
            arrhealthStatsParceler = this.mBatteryStats.takeUidSnapshots(arrn);
            arrhealthStats = new HealthStats[arrn.length];
            n = arrn.length;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
        for (int i = 0; i < n; ++i) {
            arrhealthStats[i] = arrhealthStatsParceler[i].getHealthStats();
            continue;
        }
        return arrhealthStats;
    }
}

