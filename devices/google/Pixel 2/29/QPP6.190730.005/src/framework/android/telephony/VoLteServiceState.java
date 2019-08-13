/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.Rlog;

@Deprecated
public final class VoLteServiceState
implements Parcelable {
    public static final Parcelable.Creator<VoLteServiceState> CREATOR = new Parcelable.Creator(){

        public VoLteServiceState createFromParcel(Parcel parcel) {
            return new VoLteServiceState(parcel);
        }

        public VoLteServiceState[] newArray(int n) {
            return new VoLteServiceState[n];
        }
    };
    private static final boolean DBG = false;
    public static final int HANDOVER_CANCELED = 3;
    public static final int HANDOVER_COMPLETED = 1;
    public static final int HANDOVER_FAILED = 2;
    public static final int HANDOVER_STARTED = 0;
    public static final int INVALID = Integer.MAX_VALUE;
    private static final String LOG_TAG = "VoLteServiceState";
    public static final int NOT_SUPPORTED = 0;
    public static final int SUPPORTED = 1;
    private int mSrvccState;

    public VoLteServiceState() {
        this.initialize();
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public VoLteServiceState(int n) {
        this.initialize();
        this.mSrvccState = n;
    }

    public VoLteServiceState(Parcel parcel) {
        this.mSrvccState = parcel.readInt();
    }

    public VoLteServiceState(VoLteServiceState voLteServiceState) {
        this.copyFrom(voLteServiceState);
    }

    private void initialize() {
        this.mSrvccState = Integer.MAX_VALUE;
    }

    private static void log(String string2) {
        Rlog.w(LOG_TAG, string2);
    }

    public static VoLteServiceState newFromBundle(Bundle bundle) {
        VoLteServiceState voLteServiceState = new VoLteServiceState();
        voLteServiceState.setFromNotifierBundle(bundle);
        return voLteServiceState;
    }

    private void setFromNotifierBundle(Bundle bundle) {
        this.mSrvccState = bundle.getInt("mSrvccState");
    }

    protected void copyFrom(VoLteServiceState voLteServiceState) {
        this.mSrvccState = voLteServiceState.mSrvccState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        try {
            VoLteServiceState voLteServiceState = (VoLteServiceState)object;
            if (object == null) {
                return false;
            }
            if (this.mSrvccState == voLteServiceState.mSrvccState) {
                bl = true;
            }
            return bl;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public void fillInNotifierBundle(Bundle bundle) {
        bundle.putInt("mSrvccState", this.mSrvccState);
    }

    public int getSrvccState() {
        return this.mSrvccState;
    }

    public int hashCode() {
        return this.mSrvccState * 31;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VoLteServiceState: ");
        stringBuilder.append(this.mSrvccState);
        return stringBuilder.toString();
    }

    public void validateInput() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSrvccState);
    }

}

