/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.SyncStats;
import android.os.Parcel;
import android.os.Parcelable;

public final class SyncResult
implements Parcelable {
    public static final SyncResult ALREADY_IN_PROGRESS = new SyncResult(true);
    public static final Parcelable.Creator<SyncResult> CREATOR = new Parcelable.Creator<SyncResult>(){

        @Override
        public SyncResult createFromParcel(Parcel parcel) {
            return new SyncResult(parcel);
        }

        public SyncResult[] newArray(int n) {
            return new SyncResult[n];
        }
    };
    public boolean databaseError;
    public long delayUntil;
    public boolean fullSyncRequested;
    public boolean moreRecordsToGet;
    public boolean partialSyncUnavailable;
    public final SyncStats stats;
    public final boolean syncAlreadyInProgress;
    public boolean tooManyDeletions;
    public boolean tooManyRetries;

    public SyncResult() {
        this(false);
    }

    private SyncResult(Parcel parcel) {
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.syncAlreadyInProgress = bl2;
        bl2 = parcel.readInt() != 0;
        this.tooManyDeletions = bl2;
        bl2 = parcel.readInt() != 0;
        this.tooManyRetries = bl2;
        bl2 = parcel.readInt() != 0;
        this.databaseError = bl2;
        bl2 = parcel.readInt() != 0;
        this.fullSyncRequested = bl2;
        bl2 = parcel.readInt() != 0;
        this.partialSyncUnavailable = bl2;
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.moreRecordsToGet = bl2;
        this.delayUntil = parcel.readLong();
        this.stats = new SyncStats(parcel);
    }

    private SyncResult(boolean bl) {
        this.syncAlreadyInProgress = bl;
        this.tooManyDeletions = false;
        this.tooManyRetries = false;
        this.fullSyncRequested = false;
        this.partialSyncUnavailable = false;
        this.moreRecordsToGet = false;
        this.delayUntil = 0L;
        this.stats = new SyncStats();
    }

    public void clear() {
        if (!this.syncAlreadyInProgress) {
            this.tooManyDeletions = false;
            this.tooManyRetries = false;
            this.databaseError = false;
            this.fullSyncRequested = false;
            this.partialSyncUnavailable = false;
            this.moreRecordsToGet = false;
            this.delayUntil = 0L;
            this.stats.clear();
            return;
        }
        throw new UnsupportedOperationException("you are not allowed to clear the ALREADY_IN_PROGRESS SyncStats");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean hasError() {
        boolean bl = this.hasSoftError() || this.hasHardError();
        return bl;
    }

    public boolean hasHardError() {
        boolean bl = this.stats.numParseExceptions > 0L || this.stats.numConflictDetectedExceptions > 0L || this.stats.numAuthExceptions > 0L || this.tooManyDeletions || this.tooManyRetries || this.databaseError;
        return bl;
    }

    public boolean hasSoftError() {
        boolean bl = this.syncAlreadyInProgress || this.stats.numIoExceptions > 0L;
        return bl;
    }

    public boolean madeSomeProgress() {
        boolean bl = this.stats.numDeletes > 0L && !this.tooManyDeletions || this.stats.numInserts > 0L || this.stats.numUpdates > 0L;
        return bl;
    }

    public String toDebugString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.fullSyncRequested) {
            stringBuffer.append("f1");
        }
        if (this.partialSyncUnavailable) {
            stringBuffer.append("r1");
        }
        if (this.hasHardError()) {
            stringBuffer.append("X1");
        }
        if (this.stats.numParseExceptions > 0L) {
            stringBuffer.append("e");
            stringBuffer.append(this.stats.numParseExceptions);
        }
        if (this.stats.numConflictDetectedExceptions > 0L) {
            stringBuffer.append("c");
            stringBuffer.append(this.stats.numConflictDetectedExceptions);
        }
        if (this.stats.numAuthExceptions > 0L) {
            stringBuffer.append("a");
            stringBuffer.append(this.stats.numAuthExceptions);
        }
        if (this.tooManyDeletions) {
            stringBuffer.append("D1");
        }
        if (this.tooManyRetries) {
            stringBuffer.append("R1");
        }
        if (this.databaseError) {
            stringBuffer.append("b1");
        }
        if (this.hasSoftError()) {
            stringBuffer.append("x1");
        }
        if (this.syncAlreadyInProgress) {
            stringBuffer.append("l1");
        }
        if (this.stats.numIoExceptions > 0L) {
            stringBuffer.append("I");
            stringBuffer.append(this.stats.numIoExceptions);
        }
        return stringBuffer.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SyncResult:");
        if (this.syncAlreadyInProgress) {
            stringBuilder.append(" syncAlreadyInProgress: ");
            stringBuilder.append(this.syncAlreadyInProgress);
        }
        if (this.tooManyDeletions) {
            stringBuilder.append(" tooManyDeletions: ");
            stringBuilder.append(this.tooManyDeletions);
        }
        if (this.tooManyRetries) {
            stringBuilder.append(" tooManyRetries: ");
            stringBuilder.append(this.tooManyRetries);
        }
        if (this.databaseError) {
            stringBuilder.append(" databaseError: ");
            stringBuilder.append(this.databaseError);
        }
        if (this.fullSyncRequested) {
            stringBuilder.append(" fullSyncRequested: ");
            stringBuilder.append(this.fullSyncRequested);
        }
        if (this.partialSyncUnavailable) {
            stringBuilder.append(" partialSyncUnavailable: ");
            stringBuilder.append(this.partialSyncUnavailable);
        }
        if (this.moreRecordsToGet) {
            stringBuilder.append(" moreRecordsToGet: ");
            stringBuilder.append(this.moreRecordsToGet);
        }
        if (this.delayUntil > 0L) {
            stringBuilder.append(" delayUntil: ");
            stringBuilder.append(this.delayUntil);
        }
        stringBuilder.append(this.stats);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.syncAlreadyInProgress);
        parcel.writeInt((int)this.tooManyDeletions);
        parcel.writeInt((int)this.tooManyRetries);
        parcel.writeInt((int)this.databaseError);
        parcel.writeInt((int)this.fullSyncRequested);
        parcel.writeInt((int)this.partialSyncUnavailable);
        parcel.writeInt((int)this.moreRecordsToGet);
        parcel.writeLong(this.delayUntil);
        this.stats.writeToParcel(parcel, n);
    }

}

