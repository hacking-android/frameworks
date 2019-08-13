/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsEvent;
import com.android.internal.annotations.VisibleForTesting;

public abstract class RcsEventDescriptor
implements Parcelable {
    protected final long mTimestamp;

    RcsEventDescriptor(long l) {
        this.mTimestamp = l;
    }

    RcsEventDescriptor(Parcel parcel) {
        this.mTimestamp = parcel.readLong();
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PROTECTED)
    public abstract RcsEvent createRcsEvent(RcsControllerCall var1);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mTimestamp);
    }
}

