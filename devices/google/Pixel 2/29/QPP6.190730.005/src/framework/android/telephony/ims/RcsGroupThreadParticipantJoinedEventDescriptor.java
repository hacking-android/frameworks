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
import android.telephony.ims.RcsGroupThreadParticipantJoinedEvent;
import android.telephony.ims.RcsParticipant;
import com.android.internal.annotations.VisibleForTesting;

public class RcsGroupThreadParticipantJoinedEventDescriptor
extends RcsGroupThreadEventDescriptor {
    public static final Parcelable.Creator<RcsGroupThreadParticipantJoinedEventDescriptor> CREATOR = new Parcelable.Creator<RcsGroupThreadParticipantJoinedEventDescriptor>(){

        @Override
        public RcsGroupThreadParticipantJoinedEventDescriptor createFromParcel(Parcel parcel) {
            return new RcsGroupThreadParticipantJoinedEventDescriptor(parcel);
        }

        public RcsGroupThreadParticipantJoinedEventDescriptor[] newArray(int n) {
            return new RcsGroupThreadParticipantJoinedEventDescriptor[n];
        }
    };
    private final int mJoinedParticipantId;

    public RcsGroupThreadParticipantJoinedEventDescriptor(long l, int n, int n2, int n3) {
        super(l, n, n2);
        this.mJoinedParticipantId = n3;
    }

    protected RcsGroupThreadParticipantJoinedEventDescriptor(Parcel parcel) {
        super(parcel);
        this.mJoinedParticipantId = parcel.readInt();
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PROTECTED)
    @Override
    public RcsGroupThreadParticipantJoinedEvent createRcsEvent(RcsControllerCall rcsControllerCall) {
        return new RcsGroupThreadParticipantJoinedEvent(this.mTimestamp, new RcsGroupThread(rcsControllerCall, this.mRcsGroupThreadId), new RcsParticipant(rcsControllerCall, this.mOriginatingParticipantId), new RcsParticipant(rcsControllerCall, this.mJoinedParticipantId));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.mJoinedParticipantId);
    }

}

