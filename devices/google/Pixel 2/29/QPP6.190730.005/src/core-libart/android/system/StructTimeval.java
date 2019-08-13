/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import libcore.util.Objects;

public final class StructTimeval {
    public final long tv_sec;
    public final long tv_usec;

    private StructTimeval(long l, long l2) {
        this.tv_sec = l;
        this.tv_usec = l2;
        if (l2 >= 0L && l2 <= 999999L) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tv_usec value ");
        stringBuilder.append(l2);
        stringBuilder.append(" is not in [0, 999999]");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static StructTimeval fromMillis(long l) {
        long l2 = l / 1000L;
        long l3 = (l - l2 * 1000L) * 1000L;
        long l4 = l2;
        long l5 = l3;
        if (l < 0L) {
            l4 = l2 - 1L;
            l5 = l3 + 1000000L;
        }
        return new StructTimeval(l4, l5);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (StructTimeval)object;
            if (this.tv_sec != ((StructTimeval)object).tv_sec || this.tv_usec != ((StructTimeval)object).tv_usec) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.tv_sec, this.tv_usec);
    }

    public long toMillis() {
        return this.tv_sec * 1000L + this.tv_usec / 1000L;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

