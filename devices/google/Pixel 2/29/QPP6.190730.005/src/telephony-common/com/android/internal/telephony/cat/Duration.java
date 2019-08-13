/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class Duration
implements Parcelable {
    public static final Parcelable.Creator<Duration> CREATOR = new Parcelable.Creator<Duration>(){

        public Duration createFromParcel(Parcel parcel) {
            return new Duration(parcel);
        }

        public Duration[] newArray(int n) {
            return new Duration[n];
        }
    };
    @UnsupportedAppUsage
    public int timeInterval;
    @UnsupportedAppUsage
    public TimeUnit timeUnit;

    public Duration(int n, TimeUnit timeUnit) {
        this.timeInterval = n;
        this.timeUnit = timeUnit;
    }

    private Duration(Parcel parcel) {
        this.timeInterval = parcel.readInt();
        this.timeUnit = TimeUnit.values()[parcel.readInt()];
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.timeInterval);
        parcel.writeInt(this.timeUnit.ordinal());
    }

    public static enum TimeUnit {
        MINUTE(0),
        SECOND(1),
        TENTH_SECOND(2);
        
        private int mValue;

        private TimeUnit(int n2) {
            this.mValue = n2;
        }

        @UnsupportedAppUsage
        public int value() {
            return this.mValue;
        }
    }

}

