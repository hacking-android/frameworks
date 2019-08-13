/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import java.util.Objects;

public class HalVersion
implements Comparable<HalVersion> {
    public static final HalVersion UNKNOWN = new HalVersion(-1, -1);
    public final int major;
    public final int minor;

    public HalVersion(int n, int n2) {
        this.major = n;
        this.minor = n2;
    }

    @Override
    public int compareTo(HalVersion halVersion) {
        if (halVersion == null) {
            return 1;
        }
        int n = this.major;
        int n2 = halVersion.major;
        if (n > n2) {
            return 1;
        }
        if (n < n2) {
            return -1;
        }
        n = this.minor;
        n2 = halVersion.minor;
        if (n > n2) {
            return 1;
        }
        if (n < n2) {
            return -1;
        }
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof HalVersion && (object == this || this.compareTo((HalVersion)object) == 0);
        return bl;
    }

    public boolean greater(HalVersion halVersion) {
        boolean bl = this.compareTo(halVersion) > 0;
        return bl;
    }

    public boolean greaterOrEqual(HalVersion halVersion) {
        boolean bl = this.greater(halVersion) || this.equals(halVersion);
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.major, this.minor);
    }

    public boolean less(HalVersion halVersion) {
        boolean bl = this.compareTo(halVersion) < 0;
        return bl;
    }

    public boolean lessOrEqual(HalVersion halVersion) {
        boolean bl = this.less(halVersion) || this.equals(halVersion);
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.major);
        stringBuilder.append(".");
        stringBuilder.append(this.minor);
        return stringBuilder.toString();
    }
}

