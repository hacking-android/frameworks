/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.PersistableBundle;
import android.telephony.ServiceState;

public abstract class CellSignalStrength {
    public static final int NUM_SIGNAL_STRENGTH_BINS = 5;
    protected static final int NUM_SIGNAL_STRENGTH_THRESHOLDS = 4;
    public static final int SIGNAL_STRENGTH_GOOD = 3;
    public static final int SIGNAL_STRENGTH_GREAT = 4;
    public static final int SIGNAL_STRENGTH_MODERATE = 2;
    public static final String[] SIGNAL_STRENGTH_NAMES = new String[]{"none", "poor", "moderate", "good", "great"};
    public static final int SIGNAL_STRENGTH_NONE_OR_UNKNOWN = 0;
    public static final int SIGNAL_STRENGTH_POOR = 1;

    protected CellSignalStrength() {
    }

    protected static final int getAsuFromRscpDbm(int n) {
        if (n == Integer.MAX_VALUE) {
            return 255;
        }
        return n + 120;
    }

    protected static final int getAsuFromRssiDbm(int n) {
        if (n == Integer.MAX_VALUE) {
            return 99;
        }
        return (n + 113) / 2;
    }

    protected static final int getEcNoDbFromAsu(int n) {
        if (n <= 49 && n >= 0) {
            return n / 2 - 24;
        }
        return Integer.MAX_VALUE;
    }

    protected static final int getRscpDbmFromAsu(int n) {
        if (n <= 96 && n >= 0) {
            return n - 120;
        }
        return Integer.MAX_VALUE;
    }

    protected static final int getRssiDbmFromAsu(int n) {
        if (n <= 31 && n >= 0) {
            return n * 2 - 113;
        }
        return Integer.MAX_VALUE;
    }

    protected static final int inRangeOrUnavailable(int n, int n2, int n3) {
        if (n >= n2 && n <= n3) {
            return n;
        }
        return Integer.MAX_VALUE;
    }

    protected static final int inRangeOrUnavailable(int n, int n2, int n3, int n4) {
        if ((n < n2 || n > n3) && n != n4) {
            return Integer.MAX_VALUE;
        }
        return n;
    }

    public abstract CellSignalStrength copy();

    public abstract boolean equals(Object var1);

    public abstract int getAsuLevel();

    public abstract int getDbm();

    public abstract int getLevel();

    public abstract int hashCode();

    public abstract boolean isValid();

    public abstract void setDefaultValues();

    public abstract void updateLevel(PersistableBundle var1, ServiceState var2);
}

