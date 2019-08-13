/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;

public class EasyEditSpan
implements ParcelableSpan {
    public static final String EXTRA_TEXT_CHANGED_TYPE = "android.text.style.EXTRA_TEXT_CHANGED_TYPE";
    public static final int TEXT_DELETED = 1;
    public static final int TEXT_MODIFIED = 2;
    private boolean mDeleteEnabled;
    private final PendingIntent mPendingIntent;

    public EasyEditSpan() {
        this.mPendingIntent = null;
        this.mDeleteEnabled = true;
    }

    public EasyEditSpan(PendingIntent pendingIntent) {
        this.mPendingIntent = pendingIntent;
        this.mDeleteEnabled = true;
    }

    public EasyEditSpan(Parcel parcel) {
        this.mPendingIntent = (PendingIntent)parcel.readParcelable(null);
        byte by = parcel.readByte();
        boolean bl = true;
        if (by != 1) {
            bl = false;
        }
        this.mDeleteEnabled = bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 22;
    }

    @UnsupportedAppUsage
    public boolean isDeleteEnabled() {
        return this.mDeleteEnabled;
    }

    @UnsupportedAppUsage
    public void setDeleteEnabled(boolean bl) {
        this.mDeleteEnabled = bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeParcelable(this.mPendingIntent, 0);
        parcel.writeByte((byte)(this.mDeleteEnabled ? 1 : 0));
    }
}

