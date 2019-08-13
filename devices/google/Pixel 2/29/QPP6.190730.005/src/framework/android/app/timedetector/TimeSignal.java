/*
 * Decompiled with CFR 0.145.
 */
package android.app.timedetector;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.TimestampedValue;
import java.util.Objects;

public final class TimeSignal
implements Parcelable {
    public static final Parcelable.Creator<TimeSignal> CREATOR = new Parcelable.Creator<TimeSignal>(){

        @Override
        public TimeSignal createFromParcel(Parcel parcel) {
            return TimeSignal.createFromParcel(parcel);
        }

        public TimeSignal[] newArray(int n) {
            return new TimeSignal[n];
        }
    };
    public static final String SOURCE_ID_NITZ = "nitz";
    private final String mSourceId;
    private final TimestampedValue<Long> mUtcTime;

    public TimeSignal(String string2, TimestampedValue<Long> timestampedValue) {
        this.mSourceId = Objects.requireNonNull(string2);
        this.mUtcTime = Objects.requireNonNull(timestampedValue);
    }

    private static TimeSignal createFromParcel(Parcel parcel) {
        return new TimeSignal(parcel.readString(), TimestampedValue.readFromParcel(parcel, null, Long.class));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (TimeSignal)object;
            if (!Objects.equals(this.mSourceId, ((TimeSignal)object).mSourceId) || !Objects.equals(this.mUtcTime, ((TimeSignal)object).mUtcTime)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public String getSourceId() {
        return this.mSourceId;
    }

    public TimestampedValue<Long> getUtcTime() {
        return this.mUtcTime;
    }

    public int hashCode() {
        return Objects.hash(this.mSourceId, this.mUtcTime);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TimeSignal{mSourceId='");
        stringBuilder.append(this.mSourceId);
        stringBuilder.append('\'');
        stringBuilder.append(", mUtcTime=");
        stringBuilder.append(this.mUtcTime);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mSourceId);
        TimestampedValue.writeToParcel(parcel, this.mUtcTime);
    }

}

