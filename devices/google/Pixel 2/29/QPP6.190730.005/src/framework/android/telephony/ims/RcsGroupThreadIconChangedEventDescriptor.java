/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsEvent;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.RcsGroupThreadEventDescriptor;
import android.telephony.ims.RcsGroupThreadIconChangedEvent;
import android.telephony.ims.RcsParticipant;
import com.android.internal.annotations.VisibleForTesting;

public class RcsGroupThreadIconChangedEventDescriptor
extends RcsGroupThreadEventDescriptor {
    public static final Parcelable.Creator<RcsGroupThreadIconChangedEventDescriptor> CREATOR = new Parcelable.Creator<RcsGroupThreadIconChangedEventDescriptor>(){

        @Override
        public RcsGroupThreadIconChangedEventDescriptor createFromParcel(Parcel parcel) {
            return new RcsGroupThreadIconChangedEventDescriptor(parcel);
        }

        public RcsGroupThreadIconChangedEventDescriptor[] newArray(int n) {
            return new RcsGroupThreadIconChangedEventDescriptor[n];
        }
    };
    private final Uri mNewIcon;

    public RcsGroupThreadIconChangedEventDescriptor(long l, int n, int n2, Uri uri) {
        super(l, n, n2);
        this.mNewIcon = uri;
    }

    protected RcsGroupThreadIconChangedEventDescriptor(Parcel parcel) {
        super(parcel);
        this.mNewIcon = (Uri)parcel.readParcelable(Uri.class.getClassLoader());
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PROTECTED)
    @Override
    public RcsGroupThreadIconChangedEvent createRcsEvent(RcsControllerCall rcsControllerCall) {
        return new RcsGroupThreadIconChangedEvent(this.mTimestamp, new RcsGroupThread(rcsControllerCall, this.mRcsGroupThreadId), new RcsParticipant(rcsControllerCall, this.mOriginatingParticipantId), this.mNewIcon);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeParcelable(this.mNewIcon, n);
    }

}

