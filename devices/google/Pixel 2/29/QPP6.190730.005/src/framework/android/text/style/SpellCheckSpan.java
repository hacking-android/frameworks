/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.text.ParcelableSpan;

public class SpellCheckSpan
implements ParcelableSpan {
    private boolean mSpellCheckInProgress;

    @UnsupportedAppUsage
    public SpellCheckSpan() {
        this.mSpellCheckInProgress = false;
    }

    @UnsupportedAppUsage
    public SpellCheckSpan(Parcel parcel) {
        boolean bl = parcel.readInt() != 0;
        this.mSpellCheckInProgress = bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 20;
    }

    @UnsupportedAppUsage
    public boolean isSpellCheckInProgress() {
        return this.mSpellCheckInProgress;
    }

    @UnsupportedAppUsage
    public void setSpellCheckInProgress(boolean bl) {
        this.mSpellCheckInProgress = bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeInt((int)this.mSpellCheckInProgress);
    }
}

