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
import android.telephony.ims.RcsGroupThreadNameChangedEvent;
import android.telephony.ims.RcsParticipant;
import com.android.internal.annotations.VisibleForTesting;

public class RcsGroupThreadNameChangedEventDescriptor
extends RcsGroupThreadEventDescriptor {
    public static final Parcelable.Creator<RcsGroupThreadNameChangedEventDescriptor> CREATOR = new Parcelable.Creator<RcsGroupThreadNameChangedEventDescriptor>(){

        @Override
        public RcsGroupThreadNameChangedEventDescriptor createFromParcel(Parcel parcel) {
            return new RcsGroupThreadNameChangedEventDescriptor(parcel);
        }

        public RcsGroupThreadNameChangedEventDescriptor[] newArray(int n) {
            return new RcsGroupThreadNameChangedEventDescriptor[n];
        }
    };
    private final String mNewName;

    public RcsGroupThreadNameChangedEventDescriptor(long l, int n, int n2, String string2) {
        super(l, n, n2);
        this.mNewName = string2;
    }

    protected RcsGroupThreadNameChangedEventDescriptor(Parcel parcel) {
        super(parcel);
        this.mNewName = parcel.readString();
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PROTECTED)
    @Override
    public RcsGroupThreadNameChangedEvent createRcsEvent(RcsControllerCall rcsControllerCall) {
        return new RcsGroupThreadNameChangedEvent(this.mTimestamp, new RcsGroupThread(rcsControllerCall, this.mRcsGroupThreadId), new RcsParticipant(rcsControllerCall, this.mOriginatingParticipantId), this.mNewName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.mNewName);
    }

}

