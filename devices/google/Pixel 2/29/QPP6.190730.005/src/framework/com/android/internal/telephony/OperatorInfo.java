/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public class OperatorInfo
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<OperatorInfo> CREATOR = new Parcelable.Creator<OperatorInfo>(){

        @Override
        public OperatorInfo createFromParcel(Parcel parcel) {
            return new OperatorInfo(parcel.readString(), parcel.readString(), parcel.readString(), (State)((Object)parcel.readSerializable()));
        }

        public OperatorInfo[] newArray(int n) {
            return new OperatorInfo[n];
        }
    };
    @UnsupportedAppUsage
    private String mOperatorAlphaLong;
    @UnsupportedAppUsage
    private String mOperatorAlphaShort;
    @UnsupportedAppUsage
    private String mOperatorNumeric;
    @UnsupportedAppUsage
    private State mState = State.UNKNOWN;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public OperatorInfo(String string2, String string3, String string4) {
        this(string2, string3, string4, State.UNKNOWN);
    }

    @UnsupportedAppUsage
    OperatorInfo(String string2, String string3, String string4, State state) {
        this.mOperatorAlphaLong = string2;
        this.mOperatorAlphaShort = string3;
        this.mOperatorNumeric = string4;
        this.mState = state;
    }

    @UnsupportedAppUsage
    public OperatorInfo(String string2, String string3, String string4, String string5) {
        this(string2, string3, string4, OperatorInfo.rilStateToState(string5));
    }

    @UnsupportedAppUsage
    private static State rilStateToState(String string2) {
        if (string2.equals("unknown")) {
            return State.UNKNOWN;
        }
        if (string2.equals("available")) {
            return State.AVAILABLE;
        }
        if (string2.equals("current")) {
            return State.CURRENT;
        }
        if (string2.equals("forbidden")) {
            return State.FORBIDDEN;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RIL impl error: Invalid network state '");
        stringBuilder.append(string2);
        stringBuilder.append("'");
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public String getOperatorAlphaLong() {
        return this.mOperatorAlphaLong;
    }

    @UnsupportedAppUsage
    public String getOperatorAlphaShort() {
        return this.mOperatorAlphaShort;
    }

    @UnsupportedAppUsage
    public String getOperatorNumeric() {
        return this.mOperatorNumeric;
    }

    @UnsupportedAppUsage
    public State getState() {
        return this.mState;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("OperatorInfo ");
        stringBuilder.append(this.mOperatorAlphaLong);
        stringBuilder.append("/");
        stringBuilder.append(this.mOperatorAlphaShort);
        stringBuilder.append("/");
        stringBuilder.append(this.mOperatorNumeric);
        stringBuilder.append("/");
        stringBuilder.append((Object)this.mState);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mOperatorAlphaLong);
        parcel.writeString(this.mOperatorAlphaShort);
        parcel.writeString(this.mOperatorNumeric);
        parcel.writeSerializable((Serializable)((Object)this.mState));
    }

    public static enum State {
        UNKNOWN,
        AVAILABLE,
        CURRENT,
        FORBIDDEN;
        
    }

}

