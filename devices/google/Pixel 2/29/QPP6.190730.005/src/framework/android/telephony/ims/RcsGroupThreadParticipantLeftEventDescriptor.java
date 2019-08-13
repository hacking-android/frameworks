/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsEvent;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.RcsGroupThreadEventDescriptor;
import android.telephony.ims.RcsGroupThreadParticipantLeftEvent;
import android.telephony.ims.RcsParticipant;
import com.android.internal.annotations.VisibleForTesting;

public class RcsGroupThreadParticipantLeftEventDescriptor
extends RcsGroupThreadEventDescriptor {
    public static final Parcelable.Creator<RcsGroupThreadParticipantLeftEventDescriptor> CREATOR = new Parcelable.Creator<RcsGroupThreadParticipantLeftEventDescriptor>(){

        @Override
        public RcsGroupThreadParticipantLeftEventDescriptor createFromParcel(Parcel parcel) {
            return new RcsGroupThreadParticipantLeftEventDescriptor(parcel);
        }

        public RcsGroupThreadParticipantLeftEventDescriptor[] newArray(int n) {
            return new RcsGroupThreadParticipantLeftEventDescriptor[n];
        }
    };
    private int mLeavingParticipantId;

    public RcsGroupThreadParticipantLeftEventDescriptor(long l, int n, int n2, int n3) {
        super(l, n, n2);
        this.mLeavingParticipantId = n3;
    }

    protected RcsGroupThreadParticipantLeftEventDescriptor(Parcel parcel) {
        super(parcel);
        this.mLeavingParticipantId = parcel.readInt();
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PROTECTED)
    @Override
    public RcsGroupThreadParticipantLeftEvent createRcsEvent(RcsControllerCall rcsControllerCall) {
        return new RcsGroupThreadParticipantLeftEvent(this.mTimestamp, new RcsGroupThread(rcsControllerCall, this.mRcsGroupThreadId), new RcsParticipant(rcsControllerCall, this.mOriginatingParticipantId), new RcsParticipant(rcsControllerCall, this.mLeavingParticipantId));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.mLeavingParticipantId);
    }

}

