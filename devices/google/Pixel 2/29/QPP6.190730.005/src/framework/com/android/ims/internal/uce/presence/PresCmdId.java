/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class PresCmdId
implements Parcelable {
    public static final Parcelable.Creator<PresCmdId> CREATOR = new Parcelable.Creator<PresCmdId>(){

        @Override
        public PresCmdId createFromParcel(Parcel parcel) {
            return new PresCmdId(parcel);
        }

        public PresCmdId[] newArray(int n) {
            return new PresCmdId[n];
        }
    };
    public static final int UCE_PRES_CMD_GETCONTACTCAP = 2;
    public static final int UCE_PRES_CMD_GETCONTACTLISTCAP = 3;
    public static final int UCE_PRES_CMD_GET_VERSION = 0;
    public static final int UCE_PRES_CMD_PUBLISHMYCAP = 1;
    public static final int UCE_PRES_CMD_REENABLE_SERVICE = 5;
    public static final int UCE_PRES_CMD_SETNEWFEATURETAG = 4;
    public static final int UCE_PRES_CMD_UNKNOWN = 6;
    private int mCmdId = 6;

    @UnsupportedAppUsage
    public PresCmdId() {
    }

    private PresCmdId(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCmdId() {
        return this.mCmdId;
    }

    public void readFromParcel(Parcel parcel) {
        this.mCmdId = parcel.readInt();
    }

    @UnsupportedAppUsage
    public void setCmdId(int n) {
        this.mCmdId = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCmdId);
    }

}

