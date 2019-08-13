/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.options;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class OptionsCmdId
implements Parcelable {
    public static final Parcelable.Creator<OptionsCmdId> CREATOR = new Parcelable.Creator<OptionsCmdId>(){

        @Override
        public OptionsCmdId createFromParcel(Parcel parcel) {
            return new OptionsCmdId(parcel);
        }

        public OptionsCmdId[] newArray(int n) {
            return new OptionsCmdId[n];
        }
    };
    public static final int UCE_OPTIONS_CMD_GETCONTACTCAP = 2;
    public static final int UCE_OPTIONS_CMD_GETCONTACTLISTCAP = 3;
    public static final int UCE_OPTIONS_CMD_GETMYCDINFO = 0;
    public static final int UCE_OPTIONS_CMD_GET_VERSION = 5;
    public static final int UCE_OPTIONS_CMD_RESPONSEINCOMINGOPTIONS = 4;
    public static final int UCE_OPTIONS_CMD_SETMYCDINFO = 1;
    public static final int UCE_OPTIONS_CMD_UNKNOWN = 6;
    private int mCmdId = 6;

    @UnsupportedAppUsage
    public OptionsCmdId() {
    }

    private OptionsCmdId(Parcel parcel) {
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

