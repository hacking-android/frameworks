/*
 * Decompiled with CFR 0.145.
 */
package android.net.metrics;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.metrics.IpConnectivityLog;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class ApfStats
implements IpConnectivityLog.Event {
    public static final Parcelable.Creator<ApfStats> CREATOR = new Parcelable.Creator<ApfStats>(){

        @Override
        public ApfStats createFromParcel(Parcel parcel) {
            return new ApfStats(parcel);
        }

        public ApfStats[] newArray(int n) {
            return new ApfStats[n];
        }
    };
    @UnsupportedAppUsage
    public final int droppedRas;
    @UnsupportedAppUsage
    public final long durationMs;
    @UnsupportedAppUsage
    public final int matchingRas;
    @UnsupportedAppUsage
    public final int maxProgramSize;
    @UnsupportedAppUsage
    public final int parseErrors;
    @UnsupportedAppUsage
    public final int programUpdates;
    @UnsupportedAppUsage
    public final int programUpdatesAll;
    @UnsupportedAppUsage
    public final int programUpdatesAllowingMulticast;
    @UnsupportedAppUsage
    public final int receivedRas;
    @UnsupportedAppUsage
    public final int zeroLifetimeRas;

    private ApfStats(long l, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        this.durationMs = l;
        this.receivedRas = n;
        this.matchingRas = n2;
        this.droppedRas = n3;
        this.zeroLifetimeRas = n4;
        this.parseErrors = n5;
        this.programUpdates = n6;
        this.programUpdatesAll = n7;
        this.programUpdatesAllowingMulticast = n8;
        this.maxProgramSize = n9;
    }

    private ApfStats(Parcel parcel) {
        this.durationMs = parcel.readLong();
        this.receivedRas = parcel.readInt();
        this.matchingRas = parcel.readInt();
        this.droppedRas = parcel.readInt();
        this.zeroLifetimeRas = parcel.readInt();
        this.parseErrors = parcel.readInt();
        this.programUpdates = parcel.readInt();
        this.programUpdatesAll = parcel.readInt();
        this.programUpdatesAllowingMulticast = parcel.readInt();
        this.maxProgramSize = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object.getClass().equals(ApfStats.class)) {
            object = (ApfStats)object;
            boolean bl2 = bl;
            if (this.durationMs == ((ApfStats)object).durationMs) {
                bl2 = bl;
                if (this.receivedRas == ((ApfStats)object).receivedRas) {
                    bl2 = bl;
                    if (this.matchingRas == ((ApfStats)object).matchingRas) {
                        bl2 = bl;
                        if (this.droppedRas == ((ApfStats)object).droppedRas) {
                            bl2 = bl;
                            if (this.zeroLifetimeRas == ((ApfStats)object).zeroLifetimeRas) {
                                bl2 = bl;
                                if (this.parseErrors == ((ApfStats)object).parseErrors) {
                                    bl2 = bl;
                                    if (this.programUpdates == ((ApfStats)object).programUpdates) {
                                        bl2 = bl;
                                        if (this.programUpdatesAll == ((ApfStats)object).programUpdatesAll) {
                                            bl2 = bl;
                                            if (this.programUpdatesAllowingMulticast == ((ApfStats)object).programUpdatesAllowingMulticast) {
                                                bl2 = bl;
                                                if (this.maxProgramSize == ((ApfStats)object).maxProgramSize) {
                                                    bl2 = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ApfStats(");
        stringBuilder.append(String.format("%dms ", this.durationMs));
        stringBuilder.append(String.format("%dB RA: {", this.maxProgramSize));
        stringBuilder.append(String.format("%d received, ", this.receivedRas));
        stringBuilder.append(String.format("%d matching, ", this.matchingRas));
        stringBuilder.append(String.format("%d dropped, ", this.droppedRas));
        stringBuilder.append(String.format("%d zero lifetime, ", this.zeroLifetimeRas));
        stringBuilder.append(String.format("%d parse errors}, ", this.parseErrors));
        stringBuilder.append(String.format("updates: {all: %d, RAs: %d, allow multicast: %d})", this.programUpdatesAll, this.programUpdates, this.programUpdatesAllowingMulticast));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.durationMs);
        parcel.writeInt(this.receivedRas);
        parcel.writeInt(this.matchingRas);
        parcel.writeInt(this.droppedRas);
        parcel.writeInt(this.zeroLifetimeRas);
        parcel.writeInt(this.parseErrors);
        parcel.writeInt(this.programUpdates);
        parcel.writeInt(this.programUpdatesAll);
        parcel.writeInt(this.programUpdatesAllowingMulticast);
        parcel.writeInt(this.maxProgramSize);
    }

    @SystemApi
    public static final class Builder {
        private int mDroppedRas;
        private long mDurationMs;
        private int mMatchingRas;
        private int mMaxProgramSize;
        private int mParseErrors;
        private int mProgramUpdates;
        private int mProgramUpdatesAll;
        private int mProgramUpdatesAllowingMulticast;
        private int mReceivedRas;
        private int mZeroLifetimeRas;

        public ApfStats build() {
            return new ApfStats(this.mDurationMs, this.mReceivedRas, this.mMatchingRas, this.mDroppedRas, this.mZeroLifetimeRas, this.mParseErrors, this.mProgramUpdates, this.mProgramUpdatesAll, this.mProgramUpdatesAllowingMulticast, this.mMaxProgramSize);
        }

        public Builder setDroppedRas(int n) {
            this.mDroppedRas = n;
            return this;
        }

        public Builder setDurationMs(long l) {
            this.mDurationMs = l;
            return this;
        }

        public Builder setMatchingRas(int n) {
            this.mMatchingRas = n;
            return this;
        }

        public Builder setMaxProgramSize(int n) {
            this.mMaxProgramSize = n;
            return this;
        }

        public Builder setParseErrors(int n) {
            this.mParseErrors = n;
            return this;
        }

        public Builder setProgramUpdates(int n) {
            this.mProgramUpdates = n;
            return this;
        }

        public Builder setProgramUpdatesAll(int n) {
            this.mProgramUpdatesAll = n;
            return this;
        }

        public Builder setProgramUpdatesAllowingMulticast(int n) {
            this.mProgramUpdatesAllowingMulticast = n;
            return this;
        }

        public Builder setReceivedRas(int n) {
            this.mReceivedRas = n;
            return this;
        }

        public Builder setZeroLifetimeRas(int n) {
            this.mZeroLifetimeRas = n;
            return this;
        }
    }

}

