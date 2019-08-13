/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Range;
import android.util.RecurrenceRule;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Iterator;
import java.util.Objects;

public final class SubscriptionPlan
implements Parcelable {
    public static final long BYTES_UNKNOWN = -1L;
    public static final long BYTES_UNLIMITED = Long.MAX_VALUE;
    public static final Parcelable.Creator<SubscriptionPlan> CREATOR = new Parcelable.Creator<SubscriptionPlan>(){

        @Override
        public SubscriptionPlan createFromParcel(Parcel parcel) {
            return new SubscriptionPlan(parcel);
        }

        public SubscriptionPlan[] newArray(int n) {
            return new SubscriptionPlan[n];
        }
    };
    public static final int LIMIT_BEHAVIOR_BILLED = 1;
    public static final int LIMIT_BEHAVIOR_DISABLED = 0;
    public static final int LIMIT_BEHAVIOR_THROTTLED = 2;
    public static final int LIMIT_BEHAVIOR_UNKNOWN = -1;
    public static final long TIME_UNKNOWN = -1L;
    private final RecurrenceRule cycleRule;
    private int dataLimitBehavior = -1;
    private long dataLimitBytes = -1L;
    private long dataUsageBytes = -1L;
    private long dataUsageTime = -1L;
    private CharSequence summary;
    private CharSequence title;

    private SubscriptionPlan(Parcel parcel) {
        this.cycleRule = (RecurrenceRule)parcel.readParcelable(null);
        this.title = parcel.readCharSequence();
        this.summary = parcel.readCharSequence();
        this.dataLimitBytes = parcel.readLong();
        this.dataLimitBehavior = parcel.readInt();
        this.dataUsageBytes = parcel.readLong();
        this.dataUsageTime = parcel.readLong();
    }

    private SubscriptionPlan(RecurrenceRule recurrenceRule) {
        this.cycleRule = Preconditions.checkNotNull(recurrenceRule);
    }

    public Iterator<Range<ZonedDateTime>> cycleIterator() {
        return this.cycleRule.cycleIterator();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof SubscriptionPlan;
        boolean bl2 = false;
        if (bl) {
            object = (SubscriptionPlan)object;
            if (Objects.equals(this.cycleRule, ((SubscriptionPlan)object).cycleRule) && Objects.equals(this.title, ((SubscriptionPlan)object).title) && Objects.equals(this.summary, ((SubscriptionPlan)object).summary) && this.dataLimitBytes == ((SubscriptionPlan)object).dataLimitBytes && this.dataLimitBehavior == ((SubscriptionPlan)object).dataLimitBehavior && this.dataUsageBytes == ((SubscriptionPlan)object).dataUsageBytes && this.dataUsageTime == ((SubscriptionPlan)object).dataUsageTime) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public RecurrenceRule getCycleRule() {
        return this.cycleRule;
    }

    public int getDataLimitBehavior() {
        return this.dataLimitBehavior;
    }

    public long getDataLimitBytes() {
        return this.dataLimitBytes;
    }

    public long getDataUsageBytes() {
        return this.dataUsageBytes;
    }

    public long getDataUsageTime() {
        return this.dataUsageTime;
    }

    public CharSequence getSummary() {
        return this.summary;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.cycleRule, this.title, this.summary, this.dataLimitBytes, this.dataLimitBehavior, this.dataUsageBytes, this.dataUsageTime);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("SubscriptionPlan{");
        stringBuilder.append("cycleRule=");
        stringBuilder.append(this.cycleRule);
        stringBuilder.append(" title=");
        stringBuilder.append(this.title);
        stringBuilder.append(" summary=");
        stringBuilder.append(this.summary);
        stringBuilder.append(" dataLimitBytes=");
        stringBuilder.append(this.dataLimitBytes);
        stringBuilder.append(" dataLimitBehavior=");
        stringBuilder.append(this.dataLimitBehavior);
        stringBuilder.append(" dataUsageBytes=");
        stringBuilder.append(this.dataUsageBytes);
        stringBuilder.append(" dataUsageTime=");
        stringBuilder.append(this.dataUsageTime);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.cycleRule, n);
        parcel.writeCharSequence(this.title);
        parcel.writeCharSequence(this.summary);
        parcel.writeLong(this.dataLimitBytes);
        parcel.writeInt(this.dataLimitBehavior);
        parcel.writeLong(this.dataUsageBytes);
        parcel.writeLong(this.dataUsageTime);
    }

    public static class Builder {
        private final SubscriptionPlan plan;

        public Builder(ZonedDateTime zonedDateTime, ZonedDateTime zonedDateTime2, Period period) {
            this.plan = new SubscriptionPlan(new RecurrenceRule(zonedDateTime, zonedDateTime2, period));
        }

        public static Builder createNonrecurring(ZonedDateTime zonedDateTime, ZonedDateTime zonedDateTime2) {
            if (zonedDateTime2.isAfter(zonedDateTime)) {
                return new Builder(zonedDateTime, zonedDateTime2, null);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("End ");
            stringBuilder.append(zonedDateTime2);
            stringBuilder.append(" isn't after start ");
            stringBuilder.append(zonedDateTime);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public static Builder createRecurring(ZonedDateTime serializable, Period period) {
            if (!period.isZero() && !period.isNegative()) {
                return new Builder((ZonedDateTime)serializable, null, period);
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Period ");
            ((StringBuilder)serializable).append(period);
            ((StringBuilder)serializable).append(" must be positive");
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }

        @SystemApi
        @Deprecated
        public static Builder createRecurringDaily(ZonedDateTime zonedDateTime) {
            return new Builder(zonedDateTime, null, Period.ofDays(1));
        }

        @SystemApi
        @Deprecated
        public static Builder createRecurringMonthly(ZonedDateTime zonedDateTime) {
            return new Builder(zonedDateTime, null, Period.ofMonths(1));
        }

        @SystemApi
        @Deprecated
        public static Builder createRecurringWeekly(ZonedDateTime zonedDateTime) {
            return new Builder(zonedDateTime, null, Period.ofDays(7));
        }

        public SubscriptionPlan build() {
            return this.plan;
        }

        public Builder setDataLimit(long l, int n) {
            if (l >= 0L) {
                if (n >= 0) {
                    this.plan.dataLimitBytes = l;
                    this.plan.dataLimitBehavior = n;
                    return this;
                }
                throw new IllegalArgumentException("Limit behavior must be defined");
            }
            throw new IllegalArgumentException("Limit bytes must be positive");
        }

        public Builder setDataUsage(long l, long l2) {
            if (l >= 0L) {
                if (l2 >= 0L) {
                    this.plan.dataUsageBytes = l;
                    this.plan.dataUsageTime = l2;
                    return this;
                }
                throw new IllegalArgumentException("Usage time must be positive");
            }
            throw new IllegalArgumentException("Usage bytes must be positive");
        }

        public Builder setSummary(CharSequence charSequence) {
            this.plan.summary = charSequence;
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.plan.title = charSequence;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LimitBehavior {
    }

}

