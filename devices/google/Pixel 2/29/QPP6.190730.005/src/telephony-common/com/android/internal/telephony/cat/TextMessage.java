/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.graphics.Bitmap
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.cat.Duration;

public class TextMessage
implements Parcelable {
    public static final Parcelable.Creator<TextMessage> CREATOR = new Parcelable.Creator<TextMessage>(){

        public TextMessage createFromParcel(Parcel parcel) {
            return new TextMessage(parcel);
        }

        public TextMessage[] newArray(int n) {
            return new TextMessage[n];
        }
    };
    public Duration duration;
    public Bitmap icon = null;
    @UnsupportedAppUsage
    public boolean iconSelfExplanatory;
    public boolean isHighPriority;
    public boolean responseNeeded;
    @UnsupportedAppUsage
    public String text = null;
    public String title = "";
    public boolean userClear;

    @UnsupportedAppUsage
    TextMessage() {
        this.iconSelfExplanatory = false;
        this.isHighPriority = false;
        this.responseNeeded = true;
        this.userClear = false;
        this.duration = null;
    }

    private TextMessage(Parcel parcel) {
        boolean bl = false;
        this.iconSelfExplanatory = false;
        this.isHighPriority = false;
        this.responseNeeded = true;
        this.userClear = false;
        this.duration = null;
        this.title = parcel.readString();
        this.text = parcel.readString();
        this.icon = (Bitmap)parcel.readParcelable(null);
        boolean bl2 = parcel.readInt() == 1;
        this.iconSelfExplanatory = bl2;
        bl2 = parcel.readInt() == 1;
        this.isHighPriority = bl2;
        bl2 = parcel.readInt() == 1;
        this.responseNeeded = bl2;
        bl2 = bl;
        if (parcel.readInt() == 1) {
            bl2 = true;
        }
        this.userClear = bl2;
        this.duration = (Duration)parcel.readParcelable(null);
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("title=");
        stringBuilder.append(this.title);
        stringBuilder.append(" text=");
        stringBuilder.append(this.text);
        stringBuilder.append(" icon=");
        stringBuilder.append((Object)this.icon);
        stringBuilder.append(" iconSelfExplanatory=");
        stringBuilder.append(this.iconSelfExplanatory);
        stringBuilder.append(" isHighPriority=");
        stringBuilder.append(this.isHighPriority);
        stringBuilder.append(" responseNeeded=");
        stringBuilder.append(this.responseNeeded);
        stringBuilder.append(" userClear=");
        stringBuilder.append(this.userClear);
        stringBuilder.append(" duration=");
        stringBuilder.append(this.duration);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.title);
        parcel.writeString(this.text);
        parcel.writeParcelable((Parcelable)this.icon, 0);
        parcel.writeInt((int)this.iconSelfExplanatory);
        parcel.writeInt((int)this.isHighPriority);
        parcel.writeInt((int)this.responseNeeded);
        parcel.writeInt((int)this.userClear);
        parcel.writeParcelable((Parcelable)this.duration, 0);
    }

}

