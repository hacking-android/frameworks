/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.CallQuality;
import android.telephony.PreciseCallState;
import java.util.Objects;

@SystemApi
public final class CallAttributes
implements Parcelable {
    public static final Parcelable.Creator<CallAttributes> CREATOR = new Parcelable.Creator(){

        public CallAttributes createFromParcel(Parcel parcel) {
            return new CallAttributes(parcel);
        }

        public CallAttributes[] newArray(int n) {
            return new CallAttributes[n];
        }
    };
    private CallQuality mCallQuality;
    private int mNetworkType;
    private PreciseCallState mPreciseCallState;

    private CallAttributes(Parcel parcel) {
        this.mPreciseCallState = (PreciseCallState)parcel.readParcelable(PreciseCallState.class.getClassLoader());
        this.mNetworkType = parcel.readInt();
        this.mCallQuality = (CallQuality)parcel.readParcelable(CallQuality.class.getClassLoader());
    }

    public CallAttributes(PreciseCallState preciseCallState, int n, CallQuality callQuality) {
        this.mPreciseCallState = preciseCallState;
        this.mNetworkType = n;
        this.mCallQuality = callQuality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof CallAttributes && this.hashCode() == object.hashCode()) {
            if (this == object) {
                return true;
            }
            object = (CallAttributes)object;
            if (Objects.equals(this.mPreciseCallState, ((CallAttributes)object).mPreciseCallState) && this.mNetworkType == ((CallAttributes)object).mNetworkType && Objects.equals(this.mCallQuality, ((CallAttributes)object).mCallQuality)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public CallQuality getCallQuality() {
        return this.mCallQuality;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public PreciseCallState getPreciseCallState() {
        return this.mPreciseCallState;
    }

    public int hashCode() {
        return Objects.hash(this.mPreciseCallState, this.mNetworkType, this.mCallQuality);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mPreciseCallState=");
        stringBuilder.append(this.mPreciseCallState);
        stringBuilder.append(" mNetworkType=");
        stringBuilder.append(this.mNetworkType);
        stringBuilder.append(" mCallQuality=");
        stringBuilder.append(this.mCallQuality);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mPreciseCallState, n);
        parcel.writeInt(this.mNetworkType);
        parcel.writeParcelable(this.mCallQuality, n);
    }

}

