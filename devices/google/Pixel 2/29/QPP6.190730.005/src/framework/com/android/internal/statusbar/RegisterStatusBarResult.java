/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.statusbar;

import android.graphics.Rect;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import com.android.internal.statusbar.StatusBarIcon;

public final class RegisterStatusBarResult
implements Parcelable {
    public static final Parcelable.Creator<RegisterStatusBarResult> CREATOR = new Parcelable.Creator<RegisterStatusBarResult>(){

        @Override
        public RegisterStatusBarResult createFromParcel(Parcel parcel) {
            return new RegisterStatusBarResult(parcel.createTypedArrayMap(StatusBarIcon.CREATOR), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readStrongBinder(), parcel.readTypedObject(Rect.CREATOR), parcel.readTypedObject(Rect.CREATOR), parcel.readBoolean());
        }

        public RegisterStatusBarResult[] newArray(int n) {
            return new RegisterStatusBarResult[n];
        }
    };
    public final int mDisabledFlags1;
    public final int mDisabledFlags2;
    public final Rect mDockedStackBounds;
    public final int mDockedStackSysUiVisibility;
    public final Rect mFullscreenStackBounds;
    public final int mFullscreenStackSysUiVisibility;
    public final ArrayMap<String, StatusBarIcon> mIcons;
    public final int mImeBackDisposition;
    public final IBinder mImeToken;
    public final int mImeWindowVis;
    public final boolean mMenuVisible;
    public final boolean mNavbarColorManagedByIme;
    public final boolean mShowImeSwitcher;
    public final int mSystemUiVisibility;

    public RegisterStatusBarResult(ArrayMap<String, StatusBarIcon> arrayMap, int n, int n2, boolean bl, int n3, int n4, boolean bl2, int n5, int n6, int n7, IBinder iBinder, Rect rect, Rect rect2, boolean bl3) {
        this.mIcons = new ArrayMap<String, StatusBarIcon>(arrayMap);
        this.mDisabledFlags1 = n;
        this.mSystemUiVisibility = n2;
        this.mMenuVisible = bl;
        this.mImeWindowVis = n3;
        this.mImeBackDisposition = n4;
        this.mShowImeSwitcher = bl2;
        this.mDisabledFlags2 = n5;
        this.mFullscreenStackSysUiVisibility = n6;
        this.mDockedStackSysUiVisibility = n7;
        this.mImeToken = iBinder;
        this.mFullscreenStackBounds = rect;
        this.mDockedStackBounds = rect2;
        this.mNavbarColorManagedByIme = bl3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedArrayMap(this.mIcons, n);
        parcel.writeInt(this.mDisabledFlags1);
        parcel.writeInt(this.mSystemUiVisibility);
        parcel.writeBoolean(this.mMenuVisible);
        parcel.writeInt(this.mImeWindowVis);
        parcel.writeInt(this.mImeBackDisposition);
        parcel.writeBoolean(this.mShowImeSwitcher);
        parcel.writeInt(this.mDisabledFlags2);
        parcel.writeInt(this.mFullscreenStackSysUiVisibility);
        parcel.writeInt(this.mDockedStackSysUiVisibility);
        parcel.writeStrongBinder(this.mImeToken);
        parcel.writeTypedObject(this.mFullscreenStackBounds, n);
        parcel.writeTypedObject(this.mDockedStackBounds, n);
        parcel.writeBoolean(this.mNavbarColorManagedByIme);
    }

}

