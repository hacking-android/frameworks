/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.os.Parcel;
import android.os.Parcelable;

public final class EventStats
implements Parcelable {
    public static final Parcelable.Creator<EventStats> CREATOR = new Parcelable.Creator<EventStats>(){

        @Override
        public EventStats createFromParcel(Parcel parcel) {
            EventStats eventStats = new EventStats();
            eventStats.mEventType = parcel.readInt();
            eventStats.mBeginTimeStamp = parcel.readLong();
            eventStats.mEndTimeStamp = parcel.readLong();
            eventStats.mLastEventTime = parcel.readLong();
            eventStats.mTotalTime = parcel.readLong();
            eventStats.mCount = parcel.readInt();
            return eventStats;
        }

        public EventStats[] newArray(int n) {
            return new EventStats[n];
        }
    };
    public long mBeginTimeStamp;
    public int mCount;
    public long mEndTimeStamp;
    public int mEventType;
    public long mLastEventTime;
    public long mTotalTime;

    public EventStats() {
    }

    public EventStats(EventStats eventStats) {
        this.mEventType = eventStats.mEventType;
        this.mBeginTimeStamp = eventStats.mBeginTimeStamp;
        this.mEndTimeStamp = eventStats.mEndTimeStamp;
        this.mLastEventTime = eventStats.mLastEventTime;
        this.mTotalTime = eventStats.mTotalTime;
        this.mCount = eventStats.mCount;
    }

    public void add(EventStats eventStats) {
        if (this.mEventType == eventStats.mEventType) {
            if (eventStats.mBeginTimeStamp > this.mBeginTimeStamp) {
                this.mLastEventTime = Math.max(this.mLastEventTime, eventStats.mLastEventTime);
            }
            this.mBeginTimeStamp = Math.min(this.mBeginTimeStamp, eventStats.mBeginTimeStamp);
            this.mEndTimeStamp = Math.max(this.mEndTimeStamp, eventStats.mEndTimeStamp);
            this.mTotalTime += eventStats.mTotalTime;
            this.mCount += eventStats.mCount;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't merge EventStats for event #");
        stringBuilder.append(this.mEventType);
        stringBuilder.append(" with EventStats for event #");
        stringBuilder.append(eventStats.mEventType);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCount() {
        return this.mCount;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public long getFirstTimeStamp() {
        return this.mBeginTimeStamp;
    }

    public long getLastEventTime() {
        return this.mLastEventTime;
    }

    public long getLastTimeStamp() {
        return this.mEndTimeStamp;
    }

    public long getTotalTime() {
        return this.mTotalTime;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mEventType);
        parcel.writeLong(this.mBeginTimeStamp);
        parcel.writeLong(this.mEndTimeStamp);
        parcel.writeLong(this.mLastEventTime);
        parcel.writeLong(this.mTotalTime);
        parcel.writeInt(this.mCount);
    }

}

