/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.util.ArrayMap;
import android.util.Slog;
import java.util.Map;

public final class RailStats {
    private static final String CELLULAR_SUBSYSTEM = "cellular";
    private static final String TAG = "RailStats";
    private static final String WIFI_SUBSYSTEM = "wifi";
    private long mCellularTotalEnergyUseduWs = 0L;
    private Map<Long, RailInfoData> mRailInfoData = new ArrayMap<Long, RailInfoData>();
    private boolean mRailStatsAvailability = true;
    private long mWifiTotalEnergyUseduWs = 0L;

    public long getCellularTotalEnergyUseduWs() {
        return this.mCellularTotalEnergyUseduWs;
    }

    public RailStats getRailStats() {
        return this;
    }

    public long getWifiTotalEnergyUseduWs() {
        return this.mWifiTotalEnergyUseduWs;
    }

    public boolean isRailStatsAvailable() {
        return this.mRailStatsAvailability;
    }

    public void reset() {
        this.mCellularTotalEnergyUseduWs = 0L;
        this.mWifiTotalEnergyUseduWs = 0L;
    }

    public void resetCellularTotalEnergyUsed() {
        this.mCellularTotalEnergyUseduWs = 0L;
    }

    public void resetWifiTotalEnergyUsed() {
        this.mWifiTotalEnergyUseduWs = 0L;
    }

    public void setRailStatsAvailability(boolean bl) {
        this.mRailStatsAvailability = bl;
    }

    public void updateRailData(long l, String string2, String string3, long l2, long l3) {
        block10 : {
            RailInfoData railInfoData;
            block9 : {
                if (!string3.equals(WIFI_SUBSYSTEM) && !string3.equals(CELLULAR_SUBSYSTEM)) {
                    return;
                }
                railInfoData = this.mRailInfoData.get(l);
                if (railInfoData == null) {
                    this.mRailInfoData.put(l, new RailInfoData(l, string2, string3, l2, l3));
                    if (string3.equals(WIFI_SUBSYSTEM)) {
                        this.mWifiTotalEnergyUseduWs += l3;
                        return;
                    }
                    if (string3.equals(CELLULAR_SUBSYSTEM)) {
                        this.mCellularTotalEnergyUseduWs += l3;
                    }
                    return;
                }
                l = railInfoData.timestampSinceBootMs;
                long l4 = l3 - railInfoData.energyUsedSinceBootuWs;
                if (l2 - l < 0L) break block9;
                l = l4;
                if (l4 >= 0L) break block10;
            }
            l = railInfoData.energyUsedSinceBootuWs;
        }
        railInfoData.timestampSinceBootMs = l2;
        railInfoData.energyUsedSinceBootuWs = l3;
        if (string3.equals(WIFI_SUBSYSTEM)) {
            this.mWifiTotalEnergyUseduWs += l;
            return;
        }
        if (string3.equals(CELLULAR_SUBSYSTEM)) {
            this.mCellularTotalEnergyUseduWs += l;
        }
    }

    public static class RailInfoData {
        private static final String TAG = "RailInfoData";
        public long energyUsedSinceBootuWs;
        public long index;
        public String railName;
        public String subSystemName;
        public long timestampSinceBootMs;

        private RailInfoData(long l, String string2, String string3, long l2, long l3) {
            this.index = l;
            this.railName = string2;
            this.subSystemName = string3;
            this.timestampSinceBootMs = l2;
            this.energyUsedSinceBootuWs = l3;
        }

        public void printData() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index = ");
            stringBuilder.append(this.index);
            Slog.d(TAG, stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("RailName = ");
            stringBuilder.append(this.railName);
            Slog.d(TAG, stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("SubSystemName = ");
            stringBuilder.append(this.subSystemName);
            Slog.d(TAG, stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("TimestampSinceBootMs = ");
            stringBuilder.append(this.timestampSinceBootMs);
            Slog.d(TAG, stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("EnergyUsedSinceBootuWs = ");
            stringBuilder.append(this.energyUsedSinceBootuWs);
            Slog.d(TAG, stringBuilder.toString());
        }
    }

}

