/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce.presence;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class PresPublishTriggerType
implements Parcelable {
    public static final Parcelable.Creator<PresPublishTriggerType> CREATOR = new Parcelable.Creator<PresPublishTriggerType>(){

        @Override
        public PresPublishTriggerType createFromParcel(Parcel parcel) {
            return new PresPublishTriggerType(parcel);
        }

        public PresPublishTriggerType[] newArray(int n) {
            return new PresPublishTriggerType[n];
        }
    };
    public static final int UCE_PRES_PUBLISH_TRIGGER_ETAG_EXPIRED = 0;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_2G = 6;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_3G = 5;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_EHRPD = 3;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_HSPAPLUS = 4;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_IWLAN = 8;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_LTE_VOPS_DISABLED = 1;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_LTE_VOPS_ENABLED = 2;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_WLAN = 7;
    public static final int UCE_PRES_PUBLISH_TRIGGER_UNKNOWN = 9;
    private int mPublishTriggerType = 9;

    @UnsupportedAppUsage
    public PresPublishTriggerType() {
    }

    private PresPublishTriggerType(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getPublishTrigeerType() {
        return this.mPublishTriggerType;
    }

    public void readFromParcel(Parcel parcel) {
        this.mPublishTriggerType = parcel.readInt();
    }

    @UnsupportedAppUsage
    public void setPublishTrigeerType(int n) {
        this.mPublishTriggerType = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mPublishTriggerType);
    }

}

