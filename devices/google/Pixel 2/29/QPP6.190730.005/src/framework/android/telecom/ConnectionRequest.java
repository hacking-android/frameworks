/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.telecom.Connection;
import android.telecom.Log;
import android.telecom.PhoneAccountHandle;
import java.util.Set;

public final class ConnectionRequest
implements Parcelable {
    public static final Parcelable.Creator<ConnectionRequest> CREATOR = new Parcelable.Creator<ConnectionRequest>(){

        @Override
        public ConnectionRequest createFromParcel(Parcel parcel) {
            return new ConnectionRequest(parcel);
        }

        public ConnectionRequest[] newArray(int n) {
            return new ConnectionRequest[n];
        }
    };
    private final PhoneAccountHandle mAccountHandle;
    private final Uri mAddress;
    private final Bundle mExtras;
    private final ParcelFileDescriptor mRttPipeFromInCall;
    private final ParcelFileDescriptor mRttPipeToInCall;
    private Connection.RttTextStream mRttTextStream;
    private final boolean mShouldShowIncomingCallUi;
    private final String mTelecomCallId;
    private final int mVideoState;

    private ConnectionRequest(Parcel parcel) {
        this.mAccountHandle = (PhoneAccountHandle)parcel.readParcelable(this.getClass().getClassLoader());
        this.mAddress = (Uri)parcel.readParcelable(this.getClass().getClassLoader());
        this.mExtras = (Bundle)parcel.readParcelable(this.getClass().getClassLoader());
        this.mVideoState = parcel.readInt();
        this.mTelecomCallId = parcel.readString();
        int n = parcel.readInt();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        this.mShouldShowIncomingCallUi = bl;
        this.mRttPipeFromInCall = (ParcelFileDescriptor)parcel.readParcelable(this.getClass().getClassLoader());
        this.mRttPipeToInCall = (ParcelFileDescriptor)parcel.readParcelable(this.getClass().getClassLoader());
    }

    public ConnectionRequest(PhoneAccountHandle phoneAccountHandle, Uri uri, Bundle bundle) {
        this(phoneAccountHandle, uri, bundle, 0, null, false, null, null);
    }

    public ConnectionRequest(PhoneAccountHandle phoneAccountHandle, Uri uri, Bundle bundle, int n) {
        this(phoneAccountHandle, uri, bundle, n, null, false, null, null);
    }

    public ConnectionRequest(PhoneAccountHandle phoneAccountHandle, Uri uri, Bundle bundle, int n, String string2, boolean bl) {
        this(phoneAccountHandle, uri, bundle, n, string2, bl, null, null);
    }

    private ConnectionRequest(PhoneAccountHandle phoneAccountHandle, Uri uri, Bundle bundle, int n, String string2, boolean bl, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) {
        this.mAccountHandle = phoneAccountHandle;
        this.mAddress = uri;
        this.mExtras = bundle;
        this.mVideoState = n;
        this.mTelecomCallId = string2;
        this.mShouldShowIncomingCallUi = bl;
        this.mRttPipeFromInCall = parcelFileDescriptor;
        this.mRttPipeToInCall = parcelFileDescriptor2;
    }

    private static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bundle[");
        for (String string2 : bundle.keySet()) {
            stringBuilder.append(string2);
            stringBuilder.append("=");
            int n = -1;
            int n2 = string2.hashCode();
            if (n2 != -1582002592) {
                if (n2 == -1513984200 && string2.equals("android.telecom.extra.INCOMING_CALL_ADDRESS")) {
                    n = 0;
                }
            } else if (string2.equals("android.telecom.extra.UNKNOWN_CALL_HANDLE")) {
                n = 1;
            }
            if (n != 0 && n != 1) {
                stringBuilder.append(bundle.get(string2));
            } else {
                stringBuilder.append(Log.pii(bundle.get(string2)));
            }
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PhoneAccountHandle getAccountHandle() {
        return this.mAccountHandle;
    }

    public Uri getAddress() {
        return this.mAddress;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public ParcelFileDescriptor getRttPipeFromInCall() {
        return this.mRttPipeFromInCall;
    }

    public ParcelFileDescriptor getRttPipeToInCall() {
        return this.mRttPipeToInCall;
    }

    public Connection.RttTextStream getRttTextStream() {
        if (this.isRequestingRtt()) {
            if (this.mRttTextStream == null) {
                this.mRttTextStream = new Connection.RttTextStream(this.mRttPipeToInCall, this.mRttPipeFromInCall);
            }
            return this.mRttTextStream;
        }
        return null;
    }

    public String getTelecomCallId() {
        return this.mTelecomCallId;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public boolean isRequestingRtt() {
        boolean bl = this.mRttPipeFromInCall != null && this.mRttPipeToInCall != null;
        return bl;
    }

    public boolean shouldShowIncomingCallUi() {
        return this.mShouldShowIncomingCallUi;
    }

    public String toString() {
        Object object = this.mAddress;
        object = object == null ? Uri.EMPTY : Connection.toLogSafePhoneNumber(((Uri)object).toString());
        return String.format("ConnectionRequest %s %s", object, ConnectionRequest.bundleToString(this.mExtras));
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mAccountHandle, 0);
        parcel.writeParcelable(this.mAddress, 0);
        parcel.writeParcelable(this.mExtras, 0);
        parcel.writeInt(this.mVideoState);
        parcel.writeString(this.mTelecomCallId);
        parcel.writeInt((int)this.mShouldShowIncomingCallUi);
        parcel.writeParcelable(this.mRttPipeFromInCall, 0);
        parcel.writeParcelable(this.mRttPipeToInCall, 0);
    }

    public static final class Builder {
        private PhoneAccountHandle mAccountHandle;
        private Uri mAddress;
        private Bundle mExtras;
        private ParcelFileDescriptor mRttPipeFromInCall;
        private ParcelFileDescriptor mRttPipeToInCall;
        private boolean mShouldShowIncomingCallUi = false;
        private String mTelecomCallId;
        private int mVideoState = 0;

        public ConnectionRequest build() {
            return new ConnectionRequest(this.mAccountHandle, this.mAddress, this.mExtras, this.mVideoState, this.mTelecomCallId, this.mShouldShowIncomingCallUi, this.mRttPipeFromInCall, this.mRttPipeToInCall);
        }

        public Builder setAccountHandle(PhoneAccountHandle phoneAccountHandle) {
            this.mAccountHandle = phoneAccountHandle;
            return this;
        }

        public Builder setAddress(Uri uri) {
            this.mAddress = uri;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setRttPipeFromInCall(ParcelFileDescriptor parcelFileDescriptor) {
            this.mRttPipeFromInCall = parcelFileDescriptor;
            return this;
        }

        public Builder setRttPipeToInCall(ParcelFileDescriptor parcelFileDescriptor) {
            this.mRttPipeToInCall = parcelFileDescriptor;
            return this;
        }

        public Builder setShouldShowIncomingCallUi(boolean bl) {
            this.mShouldShowIncomingCallUi = bl;
            return this;
        }

        public Builder setTelecomCallId(String string2) {
            this.mTelecomCallId = string2;
            return this;
        }

        public Builder setVideoState(int n) {
            this.mVideoState = n;
            return this;
        }
    }

}

