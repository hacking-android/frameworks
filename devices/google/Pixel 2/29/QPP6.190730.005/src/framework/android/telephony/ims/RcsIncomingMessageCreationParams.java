/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsMessageCreationParams;
import android.telephony.ims.RcsParticipant;

public final class RcsIncomingMessageCreationParams
extends RcsMessageCreationParams
implements Parcelable {
    public static final Parcelable.Creator<RcsIncomingMessageCreationParams> CREATOR = new Parcelable.Creator<RcsIncomingMessageCreationParams>(){

        @Override
        public RcsIncomingMessageCreationParams createFromParcel(Parcel parcel) {
            return new RcsIncomingMessageCreationParams(parcel);
        }

        public RcsIncomingMessageCreationParams[] newArray(int n) {
            return new RcsIncomingMessageCreationParams[n];
        }
    };
    private final long mArrivalTimestamp;
    private final long mSeenTimestamp;
    private final int mSenderParticipantId;

    private RcsIncomingMessageCreationParams(Parcel parcel) {
        super(parcel);
        this.mArrivalTimestamp = parcel.readLong();
        this.mSeenTimestamp = parcel.readLong();
        this.mSenderParticipantId = parcel.readInt();
    }

    private RcsIncomingMessageCreationParams(Builder builder) {
        super(builder);
        this.mArrivalTimestamp = builder.mArrivalTimestamp;
        this.mSeenTimestamp = builder.mSeenTimestamp;
        this.mSenderParticipantId = builder.mSenderParticipant.getId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getArrivalTimestamp() {
        return this.mArrivalTimestamp;
    }

    public long getSeenTimestamp() {
        return this.mSeenTimestamp;
    }

    public int getSenderParticipantId() {
        return this.mSenderParticipantId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel);
        parcel.writeLong(this.mArrivalTimestamp);
        parcel.writeLong(this.mSeenTimestamp);
        parcel.writeInt(this.mSenderParticipantId);
    }

    public static class Builder
    extends RcsMessageCreationParams.Builder {
        private long mArrivalTimestamp;
        private long mSeenTimestamp;
        private RcsParticipant mSenderParticipant;

        public Builder(long l, long l2, int n) {
            super(l, n);
            this.mArrivalTimestamp = l2;
        }

        @Override
        public RcsIncomingMessageCreationParams build() {
            return new RcsIncomingMessageCreationParams(this);
        }

        public Builder setArrivalTimestamp(long l) {
            this.mArrivalTimestamp = l;
            return this;
        }

        public Builder setSeenTimestamp(long l) {
            this.mSeenTimestamp = l;
            return this;
        }

        public Builder setSenderParticipant(RcsParticipant rcsParticipant) {
            this.mSenderParticipant = rcsParticipant;
            return this;
        }
    }

}

