/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Connection;
import android.telecom.PhoneAccountHandle;
import android.telecom.StatusHints;
import com.android.internal.telecom.IVideoProvider;
import java.util.ArrayList;
import java.util.List;

public final class ParcelableConference
implements Parcelable {
    public static final Parcelable.Creator<ParcelableConference> CREATOR = new Parcelable.Creator<ParcelableConference>(){

        @Override
        public ParcelableConference createFromParcel(Parcel parcel) {
            ClassLoader classLoader = ParcelableConference.class.getClassLoader();
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle)parcel.readParcelable(classLoader);
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            ArrayList<String> arrayList = new ArrayList<String>(2);
            parcel.readList(arrayList, classLoader);
            long l = parcel.readLong();
            IVideoProvider iVideoProvider = IVideoProvider.Stub.asInterface(parcel.readStrongBinder());
            int n3 = parcel.readInt();
            StatusHints statusHints = (StatusHints)parcel.readParcelable(classLoader);
            Bundle bundle = parcel.readBundle(classLoader);
            return new ParcelableConference(phoneAccountHandle, n, n2, parcel.readInt(), arrayList, iVideoProvider, n3, l, parcel.readLong(), statusHints, bundle, (Uri)parcel.readParcelable(classLoader), parcel.readInt(), parcel.readString(), parcel.readInt());
        }

        public ParcelableConference[] newArray(int n) {
            return new ParcelableConference[n];
        }
    };
    private final Uri mAddress;
    private final int mAddressPresentation;
    private final String mCallerDisplayName;
    private final int mCallerDisplayNamePresentation;
    private long mConnectElapsedTimeMillis = 0L;
    private long mConnectTimeMillis = 0L;
    private int mConnectionCapabilities;
    private List<String> mConnectionIds;
    private int mConnectionProperties;
    private Bundle mExtras;
    private PhoneAccountHandle mPhoneAccount;
    private int mState;
    private StatusHints mStatusHints;
    private final IVideoProvider mVideoProvider;
    private final int mVideoState;

    public ParcelableConference(PhoneAccountHandle phoneAccountHandle, int n, int n2, int n3, List<String> list, IVideoProvider iVideoProvider, int n4, long l, long l2, StatusHints statusHints, Bundle bundle, Uri uri, int n5, String string2, int n6) {
        this.mPhoneAccount = phoneAccountHandle;
        this.mState = n;
        this.mConnectionCapabilities = n2;
        this.mConnectionProperties = n3;
        this.mConnectionIds = list;
        this.mVideoProvider = iVideoProvider;
        this.mVideoState = n4;
        this.mConnectTimeMillis = l;
        this.mStatusHints = statusHints;
        this.mExtras = bundle;
        this.mConnectElapsedTimeMillis = l2;
        this.mAddress = uri;
        this.mAddressPresentation = n5;
        this.mCallerDisplayName = string2;
        this.mCallerDisplayNamePresentation = n6;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getConnectElapsedTimeMillis() {
        return this.mConnectElapsedTimeMillis;
    }

    public long getConnectTimeMillis() {
        return this.mConnectTimeMillis;
    }

    public int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public List<String> getConnectionIds() {
        return this.mConnectionIds;
    }

    public int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public Uri getHandle() {
        return this.mAddress;
    }

    public int getHandlePresentation() {
        return this.mAddressPresentation;
    }

    public PhoneAccountHandle getPhoneAccount() {
        return this.mPhoneAccount;
    }

    public int getState() {
        return this.mState;
    }

    public StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public IVideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("account: ");
        stringBuffer.append(this.mPhoneAccount);
        stringBuffer.append(", state: ");
        stringBuffer.append(Connection.stateToString(this.mState));
        stringBuffer.append(", capabilities: ");
        stringBuffer.append(Connection.capabilitiesToString(this.mConnectionCapabilities));
        stringBuffer.append(", properties: ");
        stringBuffer.append(Connection.propertiesToString(this.mConnectionProperties));
        stringBuffer.append(", connectTime: ");
        stringBuffer.append(this.mConnectTimeMillis);
        stringBuffer.append(", children: ");
        stringBuffer.append(this.mConnectionIds);
        stringBuffer.append(", VideoState: ");
        stringBuffer.append(this.mVideoState);
        stringBuffer.append(", VideoProvider: ");
        stringBuffer.append(this.mVideoProvider);
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mPhoneAccount, 0);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mConnectionCapabilities);
        parcel.writeList(this.mConnectionIds);
        parcel.writeLong(this.mConnectTimeMillis);
        Object object = this.mVideoProvider;
        object = object != null ? object.asBinder() : null;
        parcel.writeStrongBinder((IBinder)object);
        parcel.writeInt(this.mVideoState);
        parcel.writeParcelable(this.mStatusHints, 0);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mConnectionProperties);
        parcel.writeLong(this.mConnectElapsedTimeMillis);
        parcel.writeParcelable(this.mAddress, 0);
        parcel.writeInt(this.mAddressPresentation);
        parcel.writeString(this.mCallerDisplayName);
        parcel.writeInt(this.mCallerDisplayNamePresentation);
    }

}

