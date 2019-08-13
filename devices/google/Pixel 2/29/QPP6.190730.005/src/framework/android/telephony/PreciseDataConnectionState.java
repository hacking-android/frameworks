/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.LinkProperties;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.DataFailCause;
import android.telephony.data.ApnSetting;
import java.util.Objects;

@SystemApi
public final class PreciseDataConnectionState
implements Parcelable {
    public static final Parcelable.Creator<PreciseDataConnectionState> CREATOR = new Parcelable.Creator<PreciseDataConnectionState>(){

        @Override
        public PreciseDataConnectionState createFromParcel(Parcel parcel) {
            return new PreciseDataConnectionState(parcel);
        }

        public PreciseDataConnectionState[] newArray(int n) {
            return new PreciseDataConnectionState[n];
        }
    };
    private String mAPN = "";
    private int mAPNTypes = 0;
    private int mFailCause = 0;
    private LinkProperties mLinkProperties = null;
    private int mNetworkType = 0;
    private int mState = -1;

    public PreciseDataConnectionState() {
    }

    @UnsupportedAppUsage
    public PreciseDataConnectionState(int n, int n2, int n3, String string2, LinkProperties linkProperties, int n4) {
        this.mState = n;
        this.mNetworkType = n2;
        this.mAPNTypes = n3;
        this.mAPN = string2;
        this.mLinkProperties = linkProperties;
        this.mFailCause = n4;
    }

    private PreciseDataConnectionState(Parcel parcel) {
        this.mState = parcel.readInt();
        this.mNetworkType = parcel.readInt();
        this.mAPNTypes = parcel.readInt();
        this.mAPN = parcel.readString();
        this.mLinkProperties = (LinkProperties)parcel.readParcelable(null);
        this.mFailCause = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof PreciseDataConnectionState;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (PreciseDataConnectionState)object;
            if (!Objects.equals(this.mAPN, ((PreciseDataConnectionState)object).mAPN) || this.mAPNTypes != ((PreciseDataConnectionState)object).mAPNTypes || this.mFailCause != ((PreciseDataConnectionState)object).mFailCause || !Objects.equals(this.mLinkProperties, ((PreciseDataConnectionState)object).mLinkProperties) || this.mNetworkType != ((PreciseDataConnectionState)object).mNetworkType || this.mState != ((PreciseDataConnectionState)object).mState) break block1;
            bl = true;
        }
        return bl;
    }

    public String getDataConnectionApn() {
        return this.mAPN;
    }

    public int getDataConnectionApnTypeBitMask() {
        return this.mAPNTypes;
    }

    public int getDataConnectionFailCause() {
        return this.mFailCause;
    }

    @UnsupportedAppUsage
    public LinkProperties getDataConnectionLinkProperties() {
        return this.mLinkProperties;
    }

    public int getDataConnectionNetworkType() {
        return this.mNetworkType;
    }

    public int getDataConnectionState() {
        return this.mState;
    }

    public int hashCode() {
        return Objects.hash(this.mState, this.mNetworkType, this.mAPNTypes, this.mAPN, this.mLinkProperties, this.mFailCause);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Data Connection state: ");
        stringBuilder2.append(this.mState);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", Network type: ");
        stringBuilder2.append(this.mNetworkType);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", APN types: ");
        stringBuilder2.append(ApnSetting.getApnTypesStringFromBitmask(this.mAPNTypes));
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", APN: ");
        stringBuilder2.append(this.mAPN);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", Link properties: ");
        stringBuilder2.append(this.mLinkProperties);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", Fail cause: ");
        stringBuilder2.append(DataFailCause.toString(this.mFailCause));
        stringBuilder.append(stringBuilder2.toString());
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mNetworkType);
        parcel.writeInt(this.mAPNTypes);
        parcel.writeString(this.mAPN);
        parcel.writeParcelable(this.mLinkProperties, n);
        parcel.writeInt(this.mFailCause);
    }

}

