/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import java.io.Serializable;

public final class DateInterval
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final long fromDate;
    private final long toDate;

    public DateInterval(long l, long l2) {
        this.fromDate = l;
        this.toDate = l2;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof DateInterval;
        boolean bl2 = false;
        if (bl) {
            object = (DateInterval)object;
            bl = bl2;
            if (this.fromDate == ((DateInterval)object).fromDate) {
                bl = bl2;
                if (this.toDate == ((DateInterval)object).toDate) {
                    bl = true;
                }
            }
            return bl;
        }
        return false;
    }

    public long getFromDate() {
        return this.fromDate;
    }

    public long getToDate() {
        return this.toDate;
    }

    public int hashCode() {
        return (int)(this.fromDate + this.toDate);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(this.fromDate));
        stringBuilder.append(" ");
        stringBuilder.append(String.valueOf(this.toDate));
        return stringBuilder.toString();
    }
}

