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

public class Item
implements Parcelable {
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>(){

        public Item createFromParcel(Parcel parcel) {
            return new Item(parcel);
        }

        public Item[] newArray(int n) {
            return new Item[n];
        }
    };
    public Bitmap icon;
    public int id;
    public String text;

    public Item(int n, String string) {
        this(n, string, null);
    }

    public Item(int n, String string, Bitmap bitmap) {
        this.id = n;
        this.text = string;
        this.icon = bitmap;
    }

    public Item(Parcel parcel) {
        this.id = parcel.readInt();
        this.text = parcel.readString();
        this.icon = (Bitmap)parcel.readParcelable(null);
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return this.text;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.id);
        parcel.writeString(this.text);
        parcel.writeParcelable((Parcelable)this.icon, n);
    }

}

