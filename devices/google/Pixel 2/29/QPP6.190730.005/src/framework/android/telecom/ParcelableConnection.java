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
import android.telecom.DisconnectCause;
import android.telecom.PhoneAccountHandle;
import android.telecom.StatusHints;
import com.android.internal.telecom.IVideoProvider;
import java.util.ArrayList;
import java.util.List;

public final class ParcelableConnection
implements Parcelable {
    public static final Parcelable.Creator<ParcelableConnection> CREATOR = new Parcelable.Creator<ParcelableConnection>(){

        @Override
        public ParcelableConnection createFromParcel(Parcel parcel) {
            Object object = ParcelableConnection.class.getClassLoader();
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle)parcel.readParcelable((ClassLoader)object);
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            Uri uri = (Uri)parcel.readParcelable((ClassLoader)object);
            int n3 = parcel.readInt();
            String string2 = parcel.readString();
            int n4 = parcel.readInt();
            IVideoProvider iVideoProvider = IVideoProvider.Stub.asInterface(parcel.readStrongBinder());
            int n5 = parcel.readInt();
            boolean bl = parcel.readByte() == 1;
            boolean bl2 = parcel.readByte() == 1;
            long l = parcel.readLong();
            StatusHints statusHints = (StatusHints)parcel.readParcelable((ClassLoader)object);
            DisconnectCause disconnectCause = (DisconnectCause)parcel.readParcelable((ClassLoader)object);
            ArrayList<String> arrayList = new ArrayList<String>();
            parcel.readStringList(arrayList);
            Bundle bundle = Bundle.setDefusable(parcel.readBundle((ClassLoader)object), true);
            int n6 = parcel.readInt();
            int n7 = parcel.readInt();
            object = parcel.readString();
            return new ParcelableConnection(phoneAccountHandle, n, n2, n6, n7, uri, n3, string2, n4, iVideoProvider, n5, bl, bl2, l, parcel.readLong(), statusHints, disconnectCause, arrayList, bundle, (String)object, parcel.readInt());
        }

        public ParcelableConnection[] newArray(int n) {
            return new ParcelableConnection[n];
        }
    };
    private final Uri mAddress;
    private final int mAddressPresentation;
    private int mCallDirection;
    private final String mCallerDisplayName;
    private final int mCallerDisplayNamePresentation;
    private final List<String> mConferenceableConnectionIds;
    private final long mConnectElapsedTimeMillis;
    private final long mConnectTimeMillis;
    private final int mConnectionCapabilities;
    private final int mConnectionProperties;
    private final DisconnectCause mDisconnectCause;
    private final Bundle mExtras;
    private final boolean mIsVoipAudioMode;
    private String mParentCallId;
    private final PhoneAccountHandle mPhoneAccount;
    private final boolean mRingbackRequested;
    private final int mState;
    private final StatusHints mStatusHints;
    private final int mSupportedAudioRoutes;
    private final IVideoProvider mVideoProvider;
    private final int mVideoState;

    public ParcelableConnection(PhoneAccountHandle phoneAccountHandle, int n, int n2, int n3, int n4, Uri uri, int n5, String string2, int n6, IVideoProvider iVideoProvider, int n7, boolean bl, boolean bl2, long l, long l2, StatusHints statusHints, DisconnectCause disconnectCause, List<String> list, Bundle bundle) {
        this.mPhoneAccount = phoneAccountHandle;
        this.mState = n;
        this.mConnectionCapabilities = n2;
        this.mConnectionProperties = n3;
        this.mSupportedAudioRoutes = n4;
        this.mAddress = uri;
        this.mAddressPresentation = n5;
        this.mCallerDisplayName = string2;
        this.mCallerDisplayNamePresentation = n6;
        this.mVideoProvider = iVideoProvider;
        this.mVideoState = n7;
        this.mRingbackRequested = bl;
        this.mIsVoipAudioMode = bl2;
        this.mConnectTimeMillis = l;
        this.mConnectElapsedTimeMillis = l2;
        this.mStatusHints = statusHints;
        this.mDisconnectCause = disconnectCause;
        this.mConferenceableConnectionIds = list;
        this.mExtras = bundle;
        this.mParentCallId = null;
        this.mCallDirection = -1;
    }

    public ParcelableConnection(PhoneAccountHandle phoneAccountHandle, int n, int n2, int n3, int n4, Uri uri, int n5, String string2, int n6, IVideoProvider iVideoProvider, int n7, boolean bl, boolean bl2, long l, long l2, StatusHints statusHints, DisconnectCause disconnectCause, List<String> list, Bundle bundle, String string3, int n8) {
        this(phoneAccountHandle, n, n2, n3, n4, uri, n5, string2, n6, iVideoProvider, n7, bl, bl2, l, l2, statusHints, disconnectCause, list, bundle);
        this.mParentCallId = string3;
        this.mCallDirection = n8;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCallDirection() {
        return this.mCallDirection;
    }

    public String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public final List<String> getConferenceableConnectionIds() {
        return this.mConferenceableConnectionIds;
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

    public int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public final DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public final Bundle getExtras() {
        return this.mExtras;
    }

    public Uri getHandle() {
        return this.mAddress;
    }

    public int getHandlePresentation() {
        return this.mAddressPresentation;
    }

    public boolean getIsVoipAudioMode() {
        return this.mIsVoipAudioMode;
    }

    public final String getParentCallId() {
        return this.mParentCallId;
    }

    public PhoneAccountHandle getPhoneAccount() {
        return this.mPhoneAccount;
    }

    public int getState() {
        return this.mState;
    }

    public final StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public IVideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ParcelableConnection [act:");
        stringBuilder.append(this.mPhoneAccount);
        stringBuilder.append("], state:");
        stringBuilder.append(this.mState);
        stringBuilder.append(", capabilities:");
        stringBuilder.append(Connection.capabilitiesToString(this.mConnectionCapabilities));
        stringBuilder.append(", properties:");
        stringBuilder.append(Connection.propertiesToString(this.mConnectionProperties));
        stringBuilder.append(", extras:");
        stringBuilder.append(this.mExtras);
        stringBuilder.append(", parent:");
        stringBuilder.append(this.mParentCallId);
        stringBuilder.append(", callDirection:");
        stringBuilder.append(this.mCallDirection);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mPhoneAccount, 0);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mConnectionCapabilities);
        parcel.writeParcelable(this.mAddress, 0);
        parcel.writeInt(this.mAddressPresentation);
        parcel.writeString(this.mCallerDisplayName);
        parcel.writeInt(this.mCallerDisplayNamePresentation);
        Object object = this.mVideoProvider;
        object = object != null ? object.asBinder() : null;
        parcel.writeStrongBinder((IBinder)object);
        parcel.writeInt(this.mVideoState);
        parcel.writeByte((byte)(this.mRingbackRequested ? 1 : 0));
        parcel.writeByte((byte)(this.mIsVoipAudioMode ? 1 : 0));
        parcel.writeLong(this.mConnectTimeMillis);
        parcel.writeParcelable(this.mStatusHints, 0);
        parcel.writeParcelable(this.mDisconnectCause, 0);
        parcel.writeStringList(this.mConferenceableConnectionIds);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mConnectionProperties);
        parcel.writeInt(this.mSupportedAudioRoutes);
        parcel.writeString(this.mParentCallId);
        parcel.writeLong(this.mConnectElapsedTimeMillis);
        parcel.writeInt(this.mCallDirection);
    }

}

