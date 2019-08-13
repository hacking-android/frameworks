/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.os.Parcel;
import android.os.Parcelable;

public class SyncStats
implements Parcelable {
    public static final Parcelable.Creator<SyncStats> CREATOR = new Parcelable.Creator<SyncStats>(){

        @Override
        public SyncStats createFromParcel(Parcel parcel) {
            return new SyncStats(parcel);
        }

        public SyncStats[] newArray(int n) {
            return new SyncStats[n];
        }
    };
    public long numAuthExceptions;
    public long numConflictDetectedExceptions;
    public long numDeletes;
    public long numEntries;
    public long numInserts;
    public long numIoExceptions;
    public long numParseExceptions;
    public long numSkippedEntries;
    public long numUpdates;

    public SyncStats() {
        this.numAuthExceptions = 0L;
        this.numIoExceptions = 0L;
        this.numParseExceptions = 0L;
        this.numConflictDetectedExceptions = 0L;
        this.numInserts = 0L;
        this.numUpdates = 0L;
        this.numDeletes = 0L;
        this.numEntries = 0L;
        this.numSkippedEntries = 0L;
    }

    public SyncStats(Parcel parcel) {
        this.numAuthExceptions = parcel.readLong();
        this.numIoExceptions = parcel.readLong();
        this.numParseExceptions = parcel.readLong();
        this.numConflictDetectedExceptions = parcel.readLong();
        this.numInserts = parcel.readLong();
        this.numUpdates = parcel.readLong();
        this.numDeletes = parcel.readLong();
        this.numEntries = parcel.readLong();
        this.numSkippedEntries = parcel.readLong();
    }

    public void clear() {
        this.numAuthExceptions = 0L;
        this.numIoExceptions = 0L;
        this.numParseExceptions = 0L;
        this.numConflictDetectedExceptions = 0L;
        this.numInserts = 0L;
        this.numUpdates = 0L;
        this.numDeletes = 0L;
        this.numEntries = 0L;
        this.numSkippedEntries = 0L;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" stats [");
        if (this.numAuthExceptions > 0L) {
            stringBuilder.append(" numAuthExceptions: ");
            stringBuilder.append(this.numAuthExceptions);
        }
        if (this.numIoExceptions > 0L) {
            stringBuilder.append(" numIoExceptions: ");
            stringBuilder.append(this.numIoExceptions);
        }
        if (this.numParseExceptions > 0L) {
            stringBuilder.append(" numParseExceptions: ");
            stringBuilder.append(this.numParseExceptions);
        }
        if (this.numConflictDetectedExceptions > 0L) {
            stringBuilder.append(" numConflictDetectedExceptions: ");
            stringBuilder.append(this.numConflictDetectedExceptions);
        }
        if (this.numInserts > 0L) {
            stringBuilder.append(" numInserts: ");
            stringBuilder.append(this.numInserts);
        }
        if (this.numUpdates > 0L) {
            stringBuilder.append(" numUpdates: ");
            stringBuilder.append(this.numUpdates);
        }
        if (this.numDeletes > 0L) {
            stringBuilder.append(" numDeletes: ");
            stringBuilder.append(this.numDeletes);
        }
        if (this.numEntries > 0L) {
            stringBuilder.append(" numEntries: ");
            stringBuilder.append(this.numEntries);
        }
        if (this.numSkippedEntries > 0L) {
            stringBuilder.append(" numSkippedEntries: ");
            stringBuilder.append(this.numSkippedEntries);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.numAuthExceptions);
        parcel.writeLong(this.numIoExceptions);
        parcel.writeLong(this.numParseExceptions);
        parcel.writeLong(this.numConflictDetectedExceptions);
        parcel.writeLong(this.numInserts);
        parcel.writeLong(this.numUpdates);
        parcel.writeLong(this.numDeletes);
        parcel.writeLong(this.numEntries);
        parcel.writeLong(this.numSkippedEntries);
    }

}

