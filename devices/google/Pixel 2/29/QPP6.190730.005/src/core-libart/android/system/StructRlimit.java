/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import libcore.util.Objects;

public final class StructRlimit {
    public final long rlim_cur;
    public final long rlim_max;

    public StructRlimit(long l, long l2) {
        this.rlim_cur = l;
        this.rlim_max = l2;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

