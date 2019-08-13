/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.BackupUtils;
import android.util.Log;
import android.util.Range;
import com.android.internal.annotations.VisibleForTesting;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.ProtocolException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Iterator;
import java.util.Objects;

public class RecurrenceRule
implements Parcelable {
    public static final Parcelable.Creator<RecurrenceRule> CREATOR;
    private static final boolean LOGD;
    private static final String TAG = "RecurrenceRule";
    private static final int VERSION_INIT = 0;
    @VisibleForTesting
    public static Clock sClock;
    public final ZonedDateTime end;
    public final Period period;
    @UnsupportedAppUsage
    public final ZonedDateTime start;

    static {
        LOGD = Log.isLoggable(TAG, 3);
        sClock = Clock.systemDefaultZone();
        CREATOR = new Parcelable.Creator<RecurrenceRule>(){

            @Override
            public RecurrenceRule createFromParcel(Parcel parcel) {
                return new RecurrenceRule(parcel);
            }

            public RecurrenceRule[] newArray(int n) {
                return new RecurrenceRule[n];
            }
        };
    }

    private RecurrenceRule(Parcel parcel) {
        this.start = RecurrenceRule.convertZonedDateTime(parcel.readString());
        this.end = RecurrenceRule.convertZonedDateTime(parcel.readString());
        this.period = RecurrenceRule.convertPeriod(parcel.readString());
    }

    public RecurrenceRule(DataInputStream object) throws IOException {
        int n = ((DataInputStream)object).readInt();
        if (n == 0) {
            this.start = RecurrenceRule.convertZonedDateTime(BackupUtils.readString((DataInputStream)object));
            this.end = RecurrenceRule.convertZonedDateTime(BackupUtils.readString((DataInputStream)object));
            this.period = RecurrenceRule.convertPeriod(BackupUtils.readString((DataInputStream)object));
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown version ");
        ((StringBuilder)object).append(n);
        throw new ProtocolException(((StringBuilder)object).toString());
    }

    public RecurrenceRule(ZonedDateTime zonedDateTime, ZonedDateTime zonedDateTime2, Period period) {
        this.start = zonedDateTime;
        this.end = zonedDateTime2;
        this.period = period;
    }

    @Deprecated
    public static RecurrenceRule buildNever() {
        return new RecurrenceRule(null, null, null);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static RecurrenceRule buildRecurringMonthly(int n, ZoneId zoneId) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(sClock).withZoneSameInstant(zoneId);
        return new RecurrenceRule(ZonedDateTime.of(zonedDateTime.toLocalDate().minusYears(1L).withMonth(1).withDayOfMonth(n), LocalTime.MIDNIGHT, zoneId), null, Period.ofMonths(1));
    }

    public static String convertPeriod(Period object) {
        object = object != null ? ((Period)object).toString() : null;
        return object;
    }

    public static Period convertPeriod(String object) {
        object = object != null ? Period.parse((CharSequence)object) : null;
        return object;
    }

    public static String convertZonedDateTime(ZonedDateTime object) {
        object = object != null ? ((ZonedDateTime)object).toString() : null;
        return object;
    }

    public static ZonedDateTime convertZonedDateTime(String object) {
        object = object != null ? ZonedDateTime.parse((CharSequence)object) : null;
        return object;
    }

    public Iterator<Range<ZonedDateTime>> cycleIterator() {
        if (this.period != null) {
            return new RecurringIterator();
        }
        return new NonrecurringIterator();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof RecurrenceRule;
        boolean bl2 = false;
        if (bl) {
            object = (RecurrenceRule)object;
            if (Objects.equals(this.start, ((RecurrenceRule)object).start) && Objects.equals(this.end, ((RecurrenceRule)object).end) && Objects.equals(this.period, ((RecurrenceRule)object).period)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.start, this.end, this.period);
    }

    @Deprecated
    public boolean isMonthly() {
        Serializable serializable = this.start;
        boolean bl = true;
        if (serializable == null || (serializable = this.period) == null || ((Period)serializable).getYears() != 0 || this.period.getMonths() != 1 || this.period.getDays() != 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isRecurring() {
        boolean bl = this.period != null;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("RecurrenceRule{");
        stringBuilder.append("start=");
        stringBuilder.append(this.start);
        stringBuilder.append(" end=");
        stringBuilder.append(this.end);
        stringBuilder.append(" period=");
        stringBuilder.append(this.period);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(RecurrenceRule.convertZonedDateTime(this.start));
        parcel.writeString(RecurrenceRule.convertZonedDateTime(this.end));
        parcel.writeString(RecurrenceRule.convertPeriod(this.period));
    }

    public void writeToStream(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(0);
        BackupUtils.writeString(dataOutputStream, RecurrenceRule.convertZonedDateTime(this.start));
        BackupUtils.writeString(dataOutputStream, RecurrenceRule.convertZonedDateTime(this.end));
        BackupUtils.writeString(dataOutputStream, RecurrenceRule.convertPeriod(this.period));
    }

    private class NonrecurringIterator
    implements Iterator<Range<ZonedDateTime>> {
        boolean hasNext;

        public NonrecurringIterator() {
            boolean bl = RecurrenceRule.this.start != null && RecurrenceRule.this.end != null;
            this.hasNext = bl;
        }

        @Override
        public boolean hasNext() {
            return this.hasNext;
        }

        @Override
        public Range<ZonedDateTime> next() {
            this.hasNext = false;
            return new Range<ZonedDateTime>(RecurrenceRule.this.start, RecurrenceRule.this.end);
        }
    }

    private class RecurringIterator
    implements Iterator<Range<ZonedDateTime>> {
        ZonedDateTime cycleEnd;
        ZonedDateTime cycleStart;
        int i;

        public RecurringIterator() {
            RecurrenceRule.this = ((RecurrenceRule)RecurrenceRule.this).end != null ? ((RecurrenceRule)RecurrenceRule.this).end : ZonedDateTime.now(sClock).withZoneSameInstant(((RecurrenceRule)RecurrenceRule.this).start.getZone());
            if (LOGD) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Resolving using anchor ");
                stringBuilder.append(RecurrenceRule.this);
                Log.d(RecurrenceRule.TAG, stringBuilder.toString());
            }
            this.updateCycle();
            while (RecurrenceRule.this.toEpochSecond() > this.cycleEnd.toEpochSecond()) {
                ++this.i;
                this.updateCycle();
            }
            while (RecurrenceRule.this.toEpochSecond() <= this.cycleStart.toEpochSecond()) {
                --this.i;
                this.updateCycle();
            }
        }

        private ZonedDateTime roundBoundaryTime(ZonedDateTime zonedDateTime) {
            if (RecurrenceRule.this.isMonthly() && zonedDateTime.getDayOfMonth() < RecurrenceRule.this.start.getDayOfMonth()) {
                return ZonedDateTime.of(zonedDateTime.toLocalDate(), LocalTime.MAX, RecurrenceRule.this.start.getZone());
            }
            return zonedDateTime;
        }

        private void updateCycle() {
            this.cycleStart = this.roundBoundaryTime(RecurrenceRule.this.start.plus(RecurrenceRule.this.period.multipliedBy(this.i)));
            this.cycleEnd = this.roundBoundaryTime(RecurrenceRule.this.start.plus(RecurrenceRule.this.period.multipliedBy(this.i + 1)));
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.cycleStart.toEpochSecond() >= RecurrenceRule.this.start.toEpochSecond();
            return bl;
        }

        @Override
        public Range<ZonedDateTime> next() {
            Object object;
            if (LOGD) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cycle ");
                ((StringBuilder)object).append(this.i);
                ((StringBuilder)object).append(" from ");
                ((StringBuilder)object).append(this.cycleStart);
                ((StringBuilder)object).append(" to ");
                ((StringBuilder)object).append(this.cycleEnd);
                Log.d(RecurrenceRule.TAG, ((StringBuilder)object).toString());
            }
            object = new Range<ZonedDateTime>(this.cycleStart, this.cycleEnd);
            --this.i;
            this.updateCycle();
            return object;
        }
    }

}

