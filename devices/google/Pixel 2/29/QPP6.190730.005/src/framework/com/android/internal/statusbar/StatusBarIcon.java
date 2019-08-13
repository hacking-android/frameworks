/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.statusbar;

import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;

public class StatusBarIcon
implements Parcelable {
    public static final Parcelable.Creator<StatusBarIcon> CREATOR = new Parcelable.Creator<StatusBarIcon>(){

        @Override
        public StatusBarIcon createFromParcel(Parcel parcel) {
            return new StatusBarIcon(parcel);
        }

        public StatusBarIcon[] newArray(int n) {
            return new StatusBarIcon[n];
        }
    };
    public CharSequence contentDescription;
    public Icon icon;
    public int iconLevel;
    public int number;
    public String pkg;
    public UserHandle user;
    public boolean visible = true;

    public StatusBarIcon(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public StatusBarIcon(UserHandle userHandle, String string2, Icon icon, int n, int n2, CharSequence charSequence) {
        Icon icon2 = icon;
        if (icon.getType() == 2) {
            icon2 = icon;
            if (TextUtils.isEmpty(icon.getResPackage())) {
                icon2 = Icon.createWithResource(string2, icon.getResId());
            }
        }
        this.pkg = string2;
        this.user = userHandle;
        this.icon = icon2;
        this.iconLevel = n;
        this.number = n2;
        this.contentDescription = charSequence;
    }

    public StatusBarIcon(String string2, UserHandle userHandle, int n, int n2, int n3, CharSequence charSequence) {
        this(userHandle, string2, Icon.createWithResource(string2, n), n2, n3, charSequence);
    }

    public StatusBarIcon clone() {
        StatusBarIcon statusBarIcon = new StatusBarIcon(this.user, this.pkg, this.icon, this.iconLevel, this.number, this.contentDescription);
        statusBarIcon.visible = this.visible;
        return statusBarIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.icon = (Icon)parcel.readParcelable(null);
        this.pkg = parcel.readString();
        this.user = (UserHandle)parcel.readParcelable(null);
        this.iconLevel = parcel.readInt();
        boolean bl = parcel.readInt() != 0;
        this.visible = bl;
        this.number = parcel.readInt();
        this.contentDescription = parcel.readCharSequence();
    }

    public String toString() {
        CharSequence charSequence;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("StatusBarIcon(icon=");
        stringBuilder.append(this.icon);
        int n = this.iconLevel;
        String string2 = "";
        if (n != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" level=");
            ((StringBuilder)charSequence).append(this.iconLevel);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        charSequence = this.visible ? " visible" : "";
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" user=");
        stringBuilder.append(this.user.getIdentifier());
        charSequence = string2;
        if (this.number != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" num=");
            ((StringBuilder)charSequence).append(this.number);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" )");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.icon, 0);
        parcel.writeString(this.pkg);
        parcel.writeParcelable(this.user, 0);
        parcel.writeInt(this.iconLevel);
        parcel.writeInt((int)this.visible);
        parcel.writeInt(this.number);
        parcel.writeCharSequence(this.contentDescription);
    }

}

