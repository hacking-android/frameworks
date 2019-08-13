/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsEvent;
import android.telephony.ims.RcsEventDescriptor;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims.RcsParticipantAliasChangedEvent;
import com.android.internal.annotations.VisibleForTesting;

public class RcsParticipantAliasChangedEventDescriptor
extends RcsEventDescriptor {
    public static final Parcelable.Creator<RcsParticipantAliasChangedEventDescriptor> CREATOR = new Parcelable.Creator<RcsParticipantAliasChangedEventDescriptor>(){

        @Override
        public RcsParticipantAliasChangedEventDescriptor createFromParcel(Parcel parcel) {
            return new RcsParticipantAliasChangedEventDescriptor(parcel);
        }

        public RcsParticipantAliasChangedEventDescriptor[] newArray(int n) {
            return new RcsParticipantAliasChangedEventDescriptor[n];
        }
    };
    protected String mNewAlias;
    protected int mParticipantId;

    public RcsParticipantAliasChangedEventDescriptor(long l, int n, String string2) {
        super(l);
        this.mParticipantId = n;
        this.mNewAlias = string2;
    }

    protected RcsParticipantAliasChangedEventDescriptor(Parcel parcel) {
        super(parcel);
        this.mNewAlias = parcel.readString();
        this.mParticipantId = parcel.readInt();
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PROTECTED)
    @Override
    public RcsParticipantAliasChangedEvent createRcsEvent(RcsControllerCall rcsControllerCall) {
        return new RcsParticipantAliasChangedEvent(this.mTimestamp, new RcsParticipant(rcsControllerCall, this.mParticipantId), this.mNewAlias);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.mNewAlias);
        parcel.writeInt(this.mParticipantId);
    }

}

