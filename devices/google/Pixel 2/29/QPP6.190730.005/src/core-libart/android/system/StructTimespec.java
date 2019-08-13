/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import libcore.util.Objects;

public final class StructTimespec
implements Comparable<StructTimespec> {
    public final long tv_nsec;
    public final long tv_sec;

    public StructTimespec(long l, long l2) {
        this.tv_sec = l;
        this.tv_nsec = l2;
        if (l2 >= 0L && l2 <= 999999999L) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tv_nsec value ");
        stringBuilder.append(l2);
        stringBuilder.append(" is not in [0, 999999999]");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int compareTo(StructTimespec structTimespec) {
        long l = this.tv_sec;
        long l2 = structTimespec.tv_sec;
        if (l > l2) {
            return 1;
        }
        if (l < l2) {
            return -1;
        }
        l2 = this.tv_nsec;
        l = structTimespec.tv_nsec;
        if (l2 > l) {
            return 1;
        }
        if (l2 < l) {
            return -1;
        }
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (StructTimespec)object;
            if (this.tv_sec != ((StructTimespec)object).tv_sec) {
                return false;
            }
            if (this.tv_nsec != ((StructTimespec)object).tv_nsec) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        long l = this.tv_sec;
        int n = (int)(l ^ l >>> 32);
        l = this.tv_nsec;
        return n * 31 + (int)(l ^ l >>> 32);
    }

    public String toString() {
        return Objects.toString(this);
    }
}

