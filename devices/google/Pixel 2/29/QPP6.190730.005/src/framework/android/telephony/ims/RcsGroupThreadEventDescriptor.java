/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.telephony.ims.RcsEventDescriptor;

public abstract class RcsGroupThreadEventDescriptor
extends RcsEventDescriptor {
    protected final int mOriginatingParticipantId;
    protected final int mRcsGroupThreadId;

    RcsGroupThreadEventDescriptor(long l, int n, int n2) {
        super(l);
        this.mRcsGroupThreadId = n;
        this.mOriginatingParticipantId = n2;
    }

    RcsGroupThreadEventDescriptor(Parcel parcel) {
        super(parcel);
        this.mRcsGroupThreadId = parcel.readInt();
        this.mOriginatingParticipantId = parcel.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.mRcsGroupThreadId);
        parcel.writeInt(this.mOriginatingParticipantId);
    }
}

