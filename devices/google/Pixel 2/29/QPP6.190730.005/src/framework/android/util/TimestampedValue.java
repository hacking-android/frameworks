/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.os.Parcel;
import java.util.Objects;

public final class TimestampedValue<T> {
    private final long mReferenceTimeMillis;
    private final T mValue;

    public TimestampedValue(long l, T t) {
        this.mReferenceTimeMillis = l;
        this.mValue = t;
    }

    public static <T> TimestampedValue<T> readFromParcel(Parcel object, ClassLoader object2, Class<? extends T> class_) {
        long l = ((Parcel)object).readLong();
        if ((object2 = ((Parcel)object).readValue((ClassLoader)object2)) != null && !class_.isAssignableFrom(object2.getClass())) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Value was of type ");
            ((StringBuilder)object).append(object2.getClass());
            ((StringBuilder)object).append(" is not assignable to ");
            ((StringBuilder)object).append(class_);
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        return new TimestampedValue<Object>(l, object2);
    }

    public static long referenceTimeDifference(TimestampedValue<?> timestampedValue, TimestampedValue<?> timestampedValue2) {
        return timestampedValue.mReferenceTimeMillis - timestampedValue2.mReferenceTimeMillis;
    }

    public static void writeToParcel(Parcel parcel, TimestampedValue<?> timestampedValue) {
        parcel.writeLong(timestampedValue.mReferenceTimeMillis);
        parcel.writeValue(timestampedValue.mValue);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (TimestampedValue)object;
            if (this.mReferenceTimeMillis != ((TimestampedValue)object).mReferenceTimeMillis || !Objects.equals(this.mValue, ((TimestampedValue)object).mValue)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public long getReferenceTimeMillis() {
        return this.mReferenceTimeMillis;
    }

    public T getValue() {
        return this.mValue;
    }

    public int hashCode() {
        return Objects.hash(this.mReferenceTimeMillis, this.mValue);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TimestampedValue{mReferenceTimeMillis=");
        stringBuilder.append(this.mReferenceTimeMillis);
        stringBuilder.append(", mValue=");
        stringBuilder.append(this.mValue);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

