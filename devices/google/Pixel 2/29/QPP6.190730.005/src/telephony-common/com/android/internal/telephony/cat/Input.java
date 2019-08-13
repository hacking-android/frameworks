/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.android.internal.telephony.cat;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.cat.Duration;

public class Input
implements Parcelable {
    public static final Parcelable.Creator<Input> CREATOR = new Parcelable.Creator<Input>(){

        public Input createFromParcel(Parcel parcel) {
            return new Input(parcel);
        }

        public Input[] newArray(int n) {
            return new Input[n];
        }
    };
    public String defaultText;
    public boolean digitOnly;
    public Duration duration;
    public boolean echo;
    public boolean helpAvailable;
    public Bitmap icon;
    public boolean iconSelfExplanatory;
    public int maxLen;
    public int minLen;
    public boolean packed;
    public String text;
    public boolean ucs2;
    public boolean yesNo;

    Input() {
        this.text = "";
        this.defaultText = null;
        this.icon = null;
        this.minLen = 0;
        this.maxLen = 1;
        this.ucs2 = false;
        this.packed = false;
        this.digitOnly = false;
        this.echo = false;
        this.yesNo = false;
        this.helpAvailable = false;
        this.duration = null;
        this.iconSelfExplanatory = false;
    }

    private Input(Parcel parcel) {
        this.text = parcel.readString();
        this.defaultText = parcel.readString();
        this.icon = (Bitmap)parcel.readParcelable(null);
        this.minLen = parcel.readInt();
        this.maxLen = parcel.readInt();
        int n = parcel.readInt();
        boolean bl = false;
        boolean bl2 = n == 1;
        this.ucs2 = bl2;
        bl2 = parcel.readInt() == 1;
        this.packed = bl2;
        bl2 = parcel.readInt() == 1;
        this.digitOnly = bl2;
        bl2 = parcel.readInt() == 1;
        this.echo = bl2;
        bl2 = parcel.readInt() == 1;
        this.yesNo = bl2;
        bl2 = parcel.readInt() == 1;
        this.helpAvailable = bl2;
        this.duration = (Duration)parcel.readParcelable(null);
        bl2 = bl;
        if (parcel.readInt() == 1) {
            bl2 = true;
        }
        this.iconSelfExplanatory = bl2;
    }

    public int describeContents() {
        return 0;
    }

    boolean setIcon(Bitmap bitmap) {
        return true;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.text);
        parcel.writeString(this.defaultText);
        parcel.writeParcelable((Parcelable)this.icon, 0);
        parcel.writeInt(this.minLen);
        parcel.writeInt(this.maxLen);
        parcel.writeInt((int)this.ucs2);
        parcel.writeInt((int)this.packed);
        parcel.writeInt((int)this.digitOnly);
        parcel.writeInt((int)this.echo);
        parcel.writeInt((int)this.yesNo);
        parcel.writeInt((int)this.helpAvailable);
        parcel.writeParcelable((Parcelable)this.duration, 0);
        parcel.writeInt((int)this.iconSelfExplanatory);
    }

}

