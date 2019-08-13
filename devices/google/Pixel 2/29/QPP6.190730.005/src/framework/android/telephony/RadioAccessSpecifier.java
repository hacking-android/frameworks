/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public final class RadioAccessSpecifier
implements Parcelable {
    public static final Parcelable.Creator<RadioAccessSpecifier> CREATOR = new Parcelable.Creator<RadioAccessSpecifier>(){

        @Override
        public RadioAccessSpecifier createFromParcel(Parcel parcel) {
            return new RadioAccessSpecifier(parcel);
        }

        public RadioAccessSpecifier[] newArray(int n) {
            return new RadioAccessSpecifier[n];
        }
    };
    private int[] mBands;
    private int[] mChannels;
    private int mRadioAccessNetwork;

    public RadioAccessSpecifier(int n, int[] arrn, int[] arrn2) {
        this.mRadioAccessNetwork = n;
        this.mBands = arrn != null ? (int[])arrn.clone() : null;
        this.mChannels = arrn2 != null ? (int[])arrn2.clone() : null;
    }

    private RadioAccessSpecifier(Parcel parcel) {
        this.mRadioAccessNetwork = parcel.readInt();
        this.mBands = parcel.createIntArray();
        this.mChannels = parcel.createIntArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        RadioAccessSpecifier radioAccessSpecifier;
        boolean bl;
        block3 : {
            bl = false;
            try {
                radioAccessSpecifier = (RadioAccessSpecifier)object;
                if (object != null) break block3;
                return false;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }
        if (this.mRadioAccessNetwork == radioAccessSpecifier.mRadioAccessNetwork && Arrays.equals(this.mBands, radioAccessSpecifier.mBands) && Arrays.equals(this.mChannels, radioAccessSpecifier.mChannels)) {
            bl = true;
        }
        return bl;
    }

    public int[] getBands() {
        Object object = this.mBands;
        object = object == null ? null : (int[])object.clone();
        return object;
    }

    public int[] getChannels() {
        Object object = this.mChannels;
        object = object == null ? null : (int[])object.clone();
        return object;
    }

    public int getRadioAccessNetwork() {
        return this.mRadioAccessNetwork;
    }

    public int hashCode() {
        return this.mRadioAccessNetwork * 31 + Arrays.hashCode(this.mBands) * 37 + Arrays.hashCode(this.mChannels) * 39;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRadioAccessNetwork);
        parcel.writeIntArray(this.mBands);
        parcel.writeIntArray(this.mChannels);
    }

}

