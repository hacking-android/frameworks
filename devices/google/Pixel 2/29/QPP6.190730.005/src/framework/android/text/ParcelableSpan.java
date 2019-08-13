/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.os.Parcel;
import android.os.Parcelable;

public interface ParcelableSpan
extends Parcelable {
    public int getSpanTypeId();

    public int getSpanTypeIdInternal();

    public void writeToParcelInternal(Parcel var1, int var2);
}

