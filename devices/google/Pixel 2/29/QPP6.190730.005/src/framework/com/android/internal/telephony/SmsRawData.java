/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class SmsRawData
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<SmsRawData> CREATOR = new Parcelable.Creator<SmsRawData>(){

        @Override
        public SmsRawData createFromParcel(Parcel parcel) {
            byte[] arrby = new byte[parcel.readInt()];
            parcel.readByteArray(arrby);
            return new SmsRawData(arrby);
        }

        public SmsRawData[] newArray(int n) {
            return new SmsRawData[n];
        }
    };
    byte[] data;

    @UnsupportedAppUsage
    public SmsRawData(byte[] arrby) {
        this.data = arrby;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public byte[] getBytes() {
        return this.data;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.data.length);
        parcel.writeByteArray(this.data);
    }

}

