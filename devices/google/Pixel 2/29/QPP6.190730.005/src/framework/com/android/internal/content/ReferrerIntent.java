/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.content;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class ReferrerIntent
extends Intent {
    public static final Parcelable.Creator<ReferrerIntent> CREATOR = new Parcelable.Creator<ReferrerIntent>(){

        @Override
        public ReferrerIntent createFromParcel(Parcel parcel) {
            return new ReferrerIntent(parcel);
        }

        public ReferrerIntent[] newArray(int n) {
            return new ReferrerIntent[n];
        }
    };
    @UnsupportedAppUsage
    public final String mReferrer;

    @UnsupportedAppUsage
    public ReferrerIntent(Intent intent, String string2) {
        super(intent);
        this.mReferrer = string2;
    }

    ReferrerIntent(Parcel parcel) {
        this.readFromParcel(parcel);
        this.mReferrer = parcel.readString();
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof ReferrerIntent) {
            object = (ReferrerIntent)object;
            boolean bl2 = bl;
            if (this.filterEquals((Intent)object)) {
                bl2 = bl;
                if (Objects.equals(this.mReferrer, ((ReferrerIntent)object).mReferrer)) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public int hashCode() {
        return (17 * 31 + this.filterHashCode()) * 31 + Objects.hashCode(this.mReferrer);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.mReferrer);
    }

}

