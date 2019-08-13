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
import com.android.internal.telephony.cat.Item;
import com.android.internal.telephony.cat.PresentationType;
import com.android.internal.telephony.cat.TextAttribute;
import java.util.ArrayList;
import java.util.List;

public class Menu
implements Parcelable {
    public static final Parcelable.Creator<Menu> CREATOR = new Parcelable.Creator<Menu>(){

        public Menu createFromParcel(Parcel parcel) {
            return new Menu(parcel);
        }

        public Menu[] newArray(int n) {
            return new Menu[n];
        }
    };
    public int defaultItem;
    public boolean helpAvailable;
    public List<Item> items;
    public boolean itemsIconSelfExplanatory;
    public PresentationType presentationType;
    public boolean softKeyPreferred;
    public String title;
    @UnsupportedAppUsage
    public List<TextAttribute> titleAttrs;
    public Bitmap titleIcon;
    public boolean titleIconSelfExplanatory;

    public Menu() {
        this.items = new ArrayList<Item>();
        this.title = null;
        this.titleAttrs = null;
        this.defaultItem = 0;
        this.softKeyPreferred = false;
        this.helpAvailable = false;
        this.titleIconSelfExplanatory = false;
        this.itemsIconSelfExplanatory = false;
        this.titleIcon = null;
        this.presentationType = PresentationType.NAVIGATION_OPTIONS;
    }

    private Menu(Parcel parcel) {
        int n;
        this.title = parcel.readString();
        this.titleIcon = (Bitmap)parcel.readParcelable(null);
        this.items = new ArrayList<Item>();
        int n2 = parcel.readInt();
        for (n = 0; n < n2; ++n) {
            Item item = (Item)parcel.readParcelable(null);
            this.items.add(item);
        }
        this.defaultItem = parcel.readInt();
        n = parcel.readInt();
        boolean bl = false;
        boolean bl2 = n == 1;
        this.softKeyPreferred = bl2;
        bl2 = parcel.readInt() == 1;
        this.helpAvailable = bl2;
        bl2 = parcel.readInt() == 1;
        this.titleIconSelfExplanatory = bl2;
        bl2 = bl;
        if (parcel.readInt() == 1) {
            bl2 = true;
        }
        this.itemsIconSelfExplanatory = bl2;
        this.presentationType = PresentationType.values()[parcel.readInt()];
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.title);
        parcel.writeParcelable((Parcelable)this.titleIcon, n);
        int n2 = this.items.size();
        parcel.writeInt(n2);
        for (int i = 0; i < n2; ++i) {
            parcel.writeParcelable((Parcelable)this.items.get(i), n);
        }
        parcel.writeInt(this.defaultItem);
        parcel.writeInt((int)this.softKeyPreferred);
        parcel.writeInt((int)this.helpAvailable);
        parcel.writeInt((int)this.titleIconSelfExplanatory);
        parcel.writeInt((int)this.itemsIconSelfExplanatory);
        parcel.writeInt(this.presentationType.ordinal());
    }

}

