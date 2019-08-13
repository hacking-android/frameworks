/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.util.ArrayUtils;

public class TemporaryBuffer {
    private static char[] sTemp = null;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static char[] obtain(int n) {
        char[] arrc;
        synchronized (TemporaryBuffer.class) {
            arrc = sTemp;
            sTemp = null;
        }
        if (arrc == null) return ArrayUtils.newUnpaddedCharArray(n);
        char[] arrc2 = arrc;
        if (arrc.length >= n) return arrc2;
        return ArrayUtils.newUnpaddedCharArray(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void recycle(char[] arrc) {
        if (arrc.length > 1000) {
            return;
        }
        synchronized (TemporaryBuffer.class) {
            sTemp = arrc;
            return;
        }
    }
}

