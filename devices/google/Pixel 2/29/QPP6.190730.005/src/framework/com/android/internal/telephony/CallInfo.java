/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.os.Parcel;
import android.os.Parcelable;

public class CallInfo
implements Parcelable {
    public static final Parcelable.Creator<CallInfo> CREATOR = new Parcelable.Creator<CallInfo>(){

        @Override
        public CallInfo createFromParcel(Parcel parcel) {
            return new CallInfo(parcel.readString());
        }

        public CallInfo[] newArray(int n) {
            return new CallInfo[n];
        }
    };
    private String handle;

    public CallInfo(String string2) {
        this.handle = string2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getHandle() {
        return this.handle;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.handle);
    }

}

