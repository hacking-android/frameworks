/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keymaster.KeymasterArguments;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class KeyCharacteristics
implements Parcelable {
    public static final Parcelable.Creator<KeyCharacteristics> CREATOR = new Parcelable.Creator<KeyCharacteristics>(){

        @Override
        public KeyCharacteristics createFromParcel(Parcel parcel) {
            return new KeyCharacteristics(parcel);
        }

        public KeyCharacteristics[] newArray(int n) {
            return new KeyCharacteristics[n];
        }
    };
    public KeymasterArguments hwEnforced;
    public KeymasterArguments swEnforced;

    @UnsupportedAppUsage
    public KeyCharacteristics() {
    }

    protected KeyCharacteristics(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean getBoolean(int n) {
        if (this.hwEnforced.containsTag(n)) {
            return this.hwEnforced.getBoolean(n);
        }
        return this.swEnforced.getBoolean(n);
    }

    public Date getDate(int n) {
        Date date = this.swEnforced.getDate(n, null);
        if (date != null) {
            return date;
        }
        return this.hwEnforced.getDate(n, null);
    }

    public Integer getEnum(int n) {
        if (this.hwEnforced.containsTag(n)) {
            return this.hwEnforced.getEnum(n, -1);
        }
        if (this.swEnforced.containsTag(n)) {
            return this.swEnforced.getEnum(n, -1);
        }
        return null;
    }

    public List<Integer> getEnums(int n) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.addAll(this.hwEnforced.getEnums(n));
        arrayList.addAll(this.swEnforced.getEnums(n));
        return arrayList;
    }

    public long getUnsignedInt(int n, long l) {
        if (this.hwEnforced.containsTag(n)) {
            return this.hwEnforced.getUnsignedInt(n, l);
        }
        return this.swEnforced.getUnsignedInt(n, l);
    }

    public List<BigInteger> getUnsignedLongs(int n) {
        ArrayList<BigInteger> arrayList = new ArrayList<BigInteger>();
        arrayList.addAll(this.hwEnforced.getUnsignedLongs(n));
        arrayList.addAll(this.swEnforced.getUnsignedLongs(n));
        return arrayList;
    }

    @UnsupportedAppUsage
    public void readFromParcel(Parcel parcel) {
        this.swEnforced = KeymasterArguments.CREATOR.createFromParcel(parcel);
        this.hwEnforced = KeymasterArguments.CREATOR.createFromParcel(parcel);
    }

    public void shallowCopyFrom(KeyCharacteristics keyCharacteristics) {
        this.swEnforced = keyCharacteristics.swEnforced;
        this.hwEnforced = keyCharacteristics.hwEnforced;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.swEnforced.writeToParcel(parcel, n);
        this.hwEnforced.writeToParcel(parcel, n);
    }

}

