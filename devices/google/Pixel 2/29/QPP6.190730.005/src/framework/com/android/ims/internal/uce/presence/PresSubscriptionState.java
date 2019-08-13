/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class PresSubscriptionState
implements Parcelable {
    public static final Parcelable.Creator<PresSubscriptionState> CREATOR = new Parcelable.Creator<PresSubscriptionState>(){

        @Override
        public PresSubscriptionState createFromParcel(Parcel parcel) {
            return new PresSubscriptionState(parcel);
        }

        public PresSubscriptionState[] newArray(int n) {
            return new PresSubscriptionState[n];
        }
    };
    public static final int UCE_PRES_SUBSCRIPTION_STATE_ACTIVE = 0;
    public static final int UCE_PRES_SUBSCRIPTION_STATE_PENDING = 1;
    public static final int UCE_PRES_SUBSCRIPTION_STATE_TERMINATED = 2;
    public static final int UCE_PRES_SUBSCRIPTION_STATE_UNKNOWN = 3;
    private int mPresSubscriptionState = 3;

    @UnsupportedAppUsage
    public PresSubscriptionState() {
    }

    private PresSubscriptionState(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getPresSubscriptionStateValue() {
        return this.mPresSubscriptionState;
    }

    public void readFromParcel(Parcel parcel) {
        this.mPresSubscriptionState = parcel.readInt();
    }

    @UnsupportedAppUsage
    public void setPresSubscriptionState(int n) {
        this.mPresSubscriptionState = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mPresSubscriptionState);
    }

}

