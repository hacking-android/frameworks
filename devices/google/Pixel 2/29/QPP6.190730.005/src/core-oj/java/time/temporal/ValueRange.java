/*
 * Decompiled with CFR 0.145.
 */
package java.time.temporal;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.temporal.TemporalField;

public final class ValueRange
implements Serializable {
    private static final long serialVersionUID = -7317881728594519368L;
    private final long maxLargest;
    private final long maxSmallest;
    private final long minLargest;
    private final long minSmallest;

    private ValueRange(long l, long l2, long l3, long l4) {
        this.minSmallest = l;
        this.minLargest = l2;
        this.maxSmallest = l3;
        this.maxLargest = l4;
    }

    private String genInvalidFieldMessage(TemporalField object, long l) {
        if (object != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid value for ");
            stringBuilder.append(object);
            stringBuilder.append(" (valid values ");
            stringBuilder.append(this);
            stringBuilder.append("): ");
            stringBuilder.append(l);
            return stringBuilder.toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid value (valid values ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append("): ");
        ((StringBuilder)object).append(l);
        return ((StringBuilder)object).toString();
    }

    public static ValueRange of(long l, long l2) {
        if (l <= l2) {
            return new ValueRange(l, l, l2, l2);
        }
        throw new IllegalArgumentException("Minimum value must be less than maximum value");
    }

    public static ValueRange of(long l, long l2, long l3) {
        return ValueRange.of(l, l, l2, l3);
    }

    public static ValueRange of(long l, long l2, long l3, long l4) {
        if (l <= l2) {
            if (l3 <= l4) {
                if (l2 <= l4) {
                    return new ValueRange(l, l2, l3, l4);
                }
                throw new IllegalArgumentException("Minimum value must be less than maximum value");
            }
            throw new IllegalArgumentException("Smallest maximum value must be less than largest maximum value");
        }
        throw new IllegalArgumentException("Smallest minimum value must be less than largest minimum value");
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException, InvalidObjectException {
        objectInputStream.defaultReadObject();
        long l = this.minSmallest;
        long l2 = this.minLargest;
        if (l <= l2) {
            l = this.maxSmallest;
            long l3 = this.maxLargest;
            if (l <= l3) {
                if (l2 <= l3) {
                    return;
                }
                throw new InvalidObjectException("Minimum value must be less than maximum value");
            }
            throw new InvalidObjectException("Smallest maximum value must be less than largest maximum value");
        }
        throw new InvalidObjectException("Smallest minimum value must be less than largest minimum value");
    }

    public int checkValidIntValue(long l, TemporalField temporalField) {
        if (this.isValidIntValue(l)) {
            return (int)l;
        }
        throw new DateTimeException(this.genInvalidFieldMessage(temporalField, l));
    }

    public long checkValidValue(long l, TemporalField temporalField) {
        if (this.isValidValue(l)) {
            return l;
        }
        throw new DateTimeException(this.genInvalidFieldMessage(temporalField, l));
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof ValueRange) {
            object = (ValueRange)object;
            if (this.minSmallest != ((ValueRange)object).minSmallest || this.minLargest != ((ValueRange)object).minLargest || this.maxSmallest != ((ValueRange)object).maxSmallest || this.maxLargest != ((ValueRange)object).maxLargest) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public long getLargestMinimum() {
        return this.minLargest;
    }

    public long getMaximum() {
        return this.maxLargest;
    }

    public long getMinimum() {
        return this.minSmallest;
    }

    public long getSmallestMaximum() {
        return this.maxSmallest;
    }

    public int hashCode() {
        long l = this.minSmallest;
        long l2 = this.minLargest;
        int n = (int)(l2 + 16L);
        long l3 = this.maxSmallest;
        int n2 = (int)(l3 + 48L);
        int n3 = (int)(l3 + 32L);
        l3 = this.maxLargest;
        l2 = l + l2 << n >> n2 << n3 >> (int)(32L + l3) << (int)(l3 + 48L) >> 16;
        return (int)(l2 >>> 32 ^ l2);
    }

    public boolean isFixed() {
        boolean bl = this.minSmallest == this.minLargest && this.maxSmallest == this.maxLargest;
        return bl;
    }

    public boolean isIntValue() {
        boolean bl = this.getMinimum() >= Integer.MIN_VALUE && this.getMaximum() <= Integer.MAX_VALUE;
        return bl;
    }

    public boolean isValidIntValue(long l) {
        boolean bl = this.isIntValue() && this.isValidValue(l);
        return bl;
    }

    public boolean isValidValue(long l) {
        boolean bl = l >= this.getMinimum() && l <= this.getMaximum();
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.minSmallest);
        if (this.minSmallest != this.minLargest) {
            stringBuilder.append('/');
            stringBuilder.append(this.minLargest);
        }
        stringBuilder.append(" - ");
        stringBuilder.append(this.maxSmallest);
        if (this.maxSmallest != this.maxLargest) {
            stringBuilder.append('/');
            stringBuilder.append(this.maxLargest);
        }
        return stringBuilder.toString();
    }
}

