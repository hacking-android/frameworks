/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.UnsupportedAppUsage;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.DisconnectCause;
import android.telecom.GatewayInfo;
import android.telecom.ParcelableRttCall;
import android.telecom.PhoneAccountHandle;
import android.telecom.StatusHints;
import android.telecom.VideoCallImpl;
import com.android.internal.telecom.IVideoProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ParcelableCall
implements Parcelable {
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final Parcelable.Creator<ParcelableCall> CREATOR = new Parcelable.Creator<ParcelableCall>(){

        @Override
        public ParcelableCall createFromParcel(Parcel parcel) {
            ClassLoader classLoader = ParcelableCall.class.getClassLoader();
            String string2 = parcel.readString();
            int n = parcel.readInt();
            DisconnectCause disconnectCause = (DisconnectCause)parcel.readParcelable(classLoader);
            ArrayList<String> arrayList = new ArrayList<String>();
            parcel.readList(arrayList, classLoader);
            int n2 = parcel.readInt();
            int n3 = parcel.readInt();
            long l = parcel.readLong();
            Uri uri = (Uri)parcel.readParcelable(classLoader);
            int n4 = parcel.readInt();
            String string3 = parcel.readString();
            int n5 = parcel.readInt();
            GatewayInfo gatewayInfo = (GatewayInfo)parcel.readParcelable(classLoader);
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle)parcel.readParcelable(classLoader);
            boolean bl = parcel.readByte() == 1;
            IVideoProvider iVideoProvider = IVideoProvider.Stub.asInterface(parcel.readStrongBinder());
            String string4 = parcel.readString();
            ArrayList<String> arrayList2 = new ArrayList<String>();
            parcel.readList(arrayList2, classLoader);
            StatusHints statusHints = (StatusHints)parcel.readParcelable(classLoader);
            int n6 = parcel.readInt();
            ArrayList<String> arrayList3 = new ArrayList<String>();
            parcel.readList(arrayList3, classLoader);
            Bundle bundle = parcel.readBundle(classLoader);
            Bundle bundle2 = parcel.readBundle(classLoader);
            int n7 = parcel.readInt();
            boolean bl2 = parcel.readByte() == 1;
            return new ParcelableCall(string2, n, disconnectCause, arrayList, n2, n3, n7, l, uri, n4, string3, n5, gatewayInfo, phoneAccountHandle, bl, iVideoProvider, bl2, (ParcelableRttCall)parcel.readParcelable(classLoader), string4, arrayList2, statusHints, n6, arrayList3, bundle, bundle2, parcel.readLong(), parcel.readInt());
        }

        public ParcelableCall[] newArray(int n) {
            return new ParcelableCall[n];
        }
    };
    private final PhoneAccountHandle mAccountHandle;
    private final int mCallDirection;
    private final String mCallerDisplayName;
    private final int mCallerDisplayNamePresentation;
    private final List<String> mCannedSmsResponses;
    private final int mCapabilities;
    private final List<String> mChildCallIds;
    private final List<String> mConferenceableCallIds;
    private final long mConnectTimeMillis;
    private final long mCreationTimeMillis;
    private final DisconnectCause mDisconnectCause;
    private final Bundle mExtras;
    private final GatewayInfo mGatewayInfo;
    private final Uri mHandle;
    private final int mHandlePresentation;
    private final String mId;
    private final Bundle mIntentExtras;
    private final boolean mIsRttCallChanged;
    private final boolean mIsVideoCallProviderChanged;
    private final String mParentCallId;
    private final int mProperties;
    private final ParcelableRttCall mRttCall;
    private final int mState;
    private final StatusHints mStatusHints;
    private final int mSupportedAudioRoutes;
    private VideoCallImpl mVideoCall;
    private final IVideoProvider mVideoCallProvider;
    private final int mVideoState;

    public ParcelableCall(String string2, int n, DisconnectCause disconnectCause, List<String> list, int n2, int n3, int n4, long l, Uri uri, int n5, String string3, int n6, GatewayInfo gatewayInfo, PhoneAccountHandle phoneAccountHandle, boolean bl, IVideoProvider iVideoProvider, boolean bl2, ParcelableRttCall parcelableRttCall, String string4, List<String> list2, StatusHints statusHints, int n7, List<String> list3, Bundle bundle, Bundle bundle2, long l2, int n8) {
        this.mId = string2;
        this.mState = n;
        this.mDisconnectCause = disconnectCause;
        this.mCannedSmsResponses = list;
        this.mCapabilities = n2;
        this.mProperties = n3;
        this.mSupportedAudioRoutes = n4;
        this.mConnectTimeMillis = l;
        this.mHandle = uri;
        this.mHandlePresentation = n5;
        this.mCallerDisplayName = string3;
        this.mCallerDisplayNamePresentation = n6;
        this.mGatewayInfo = gatewayInfo;
        this.mAccountHandle = phoneAccountHandle;
        this.mIsVideoCallProviderChanged = bl;
        this.mVideoCallProvider = iVideoProvider;
        this.mIsRttCallChanged = bl2;
        this.mRttCall = parcelableRttCall;
        this.mParentCallId = string4;
        this.mChildCallIds = list2;
        this.mStatusHints = statusHints;
        this.mVideoState = n7;
        this.mConferenceableCallIds = Collections.unmodifiableList(list3);
        this.mIntentExtras = bundle;
        this.mExtras = bundle2;
        this.mCreationTimeMillis = l2;
        this.mCallDirection = n8;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PhoneAccountHandle getAccountHandle() {
        return this.mAccountHandle;
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

    public List<String> getCannedSmsResponses() {
        return this.mCannedSmsResponses;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public List<String> getChildCallIds() {
        return this.mChildCallIds;
    }

    public List<String> getConferenceableCallIds() {
        return this.mConferenceableCallIds;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public long getConnectTimeMillis() {
        return this.mConnectTimeMillis;
    }

    public long getCreationTimeMillis() {
        return this.mCreationTimeMillis;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public GatewayInfo getGatewayInfo() {
        return this.mGatewayInfo;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public Uri getHandle() {
        return this.mHandle;
    }

    public int getHandlePresentation() {
        return this.mHandlePresentation;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public String getId() {
        return this.mId;
    }

    public Bundle getIntentExtras() {
        return this.mIntentExtras;
    }

    public boolean getIsRttCallChanged() {
        return this.mIsRttCallChanged;
    }

    public ParcelableRttCall getParcelableRttCall() {
        return this.mRttCall;
    }

    public String getParentCallId() {
        return this.mParentCallId;
    }

    public int getProperties() {
        return this.mProperties;
    }

    public int getState() {
        return this.mState;
    }

    public StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public VideoCallImpl getVideoCallImpl(String string2, int n) {
        IVideoProvider iVideoProvider;
        if (this.mVideoCall == null && (iVideoProvider = this.mVideoCallProvider) != null) {
            try {
                VideoCallImpl videoCallImpl;
                this.mVideoCall = videoCallImpl = new VideoCallImpl(iVideoProvider, string2, n);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return this.mVideoCall;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public boolean isVideoCallProviderChanged() {
        return this.mIsVideoCallProviderChanged;
    }

    public String toString() {
        return String.format("[%s, parent:%s, children:%s]", this.mId, this.mParentCallId, this.mChildCallIds);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
        parcel.writeInt(this.mState);
        parcel.writeParcelable(this.mDisconnectCause, 0);
        parcel.writeList(this.mCannedSmsResponses);
        parcel.writeInt(this.mCapabilities);
        parcel.writeInt(this.mProperties);
        parcel.writeLong(this.mConnectTimeMillis);
        parcel.writeParcelable(this.mHandle, 0);
        parcel.writeInt(this.mHandlePresentation);
        parcel.writeString(this.mCallerDisplayName);
        parcel.writeInt(this.mCallerDisplayNamePresentation);
        parcel.writeParcelable(this.mGatewayInfo, 0);
        parcel.writeParcelable(this.mAccountHandle, 0);
        parcel.writeByte((byte)(this.mIsVideoCallProviderChanged ? 1 : 0));
        Object object = this.mVideoCallProvider;
        object = object != null ? object.asBinder() : null;
        parcel.writeStrongBinder((IBinder)object);
        parcel.writeString(this.mParentCallId);
        parcel.writeList(this.mChildCallIds);
        parcel.writeParcelable(this.mStatusHints, 0);
        parcel.writeInt(this.mVideoState);
        parcel.writeList(this.mConferenceableCallIds);
        parcel.writeBundle(this.mIntentExtras);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mSupportedAudioRoutes);
        parcel.writeByte((byte)(this.mIsRttCallChanged ? 1 : 0));
        parcel.writeParcelable(this.mRttCall, 0);
        parcel.writeLong(this.mCreationTimeMillis);
        parcel.writeInt(this.mCallDirection);
    }

}

