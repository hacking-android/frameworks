/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.android.internal.telephony.cat;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.cat.Duration;
import com.android.internal.telephony.cat.Tone;

public class ToneSettings
implements Parcelable {
    public static final Parcelable.Creator<ToneSettings> CREATOR = new Parcelable.Creator<ToneSettings>(){

        public ToneSettings createFromParcel(Parcel parcel) {
            return new ToneSettings(parcel);
        }

        public ToneSettings[] newArray(int n) {
            return new ToneSettings[n];
        }
    };
    public Duration duration;
    public Tone tone;
    public boolean vibrate;

    private ToneSettings(Parcel parcel) {
        this.duration = (Duration)parcel.readParcelable(null);
        this.tone = (Tone)parcel.readParcelable(null);
        int n = parcel.readInt();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        this.vibrate = bl;
    }

    public ToneSettings(Duration duration, Tone tone, boolean bl) {
        this.duration = duration;
        this.tone = tone;
        this.vibrate = bl;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable((Parcelable)this.duration, 0);
        parcel.writeParcelable((Parcelable)this.tone, 0);
        parcel.writeInt((int)this.vibrate);
    }

}

