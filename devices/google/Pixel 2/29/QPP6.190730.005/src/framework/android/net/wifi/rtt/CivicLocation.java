/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.rtt;

import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

public final class CivicLocation
implements Parcelable {
    private static final int ADDRESS_LINE_0_ROOM_DESK_FLOOR = 0;
    private static final int ADDRESS_LINE_1_NUMBER_ROAD_SUFFIX_APT = 1;
    private static final int ADDRESS_LINE_2_CITY = 2;
    private static final int ADDRESS_LINE_3_STATE_POSTAL_CODE = 3;
    private static final int ADDRESS_LINE_4_COUNTRY = 4;
    private static final int BYTE_MASK = 255;
    private static final int COUNTRY_CODE_LENGTH = 2;
    public static final Parcelable.Creator<CivicLocation> CREATOR = new Parcelable.Creator<CivicLocation>(){

        @Override
        public CivicLocation createFromParcel(Parcel parcel) {
            return new CivicLocation(parcel);
        }

        public CivicLocation[] newArray(int n) {
            return new CivicLocation[n];
        }
    };
    private static final int MAX_CIVIC_BUFFER_SIZE = 256;
    private static final int MIN_CIVIC_BUFFER_SIZE = 3;
    private static final int TLV_LENGTH_INDEX = 1;
    private static final int TLV_TYPE_INDEX = 0;
    private static final int TLV_VALUE_INDEX = 2;
    private SparseArray<String> mCivicAddressElements = new SparseArray(3);
    private final String mCountryCode;
    private final boolean mIsValid;

    private CivicLocation(Parcel parcel) {
        boolean bl = parcel.readByte() != 0;
        this.mIsValid = bl;
        this.mCountryCode = parcel.readString();
        this.mCivicAddressElements = parcel.readSparseArray(this.getClass().getClassLoader());
    }

    public CivicLocation(byte[] arrby, String string2) {
        this.mCountryCode = string2;
        if (string2 != null && string2.length() == 2) {
            boolean bl;
            boolean bl2 = bl = false;
            if (arrby != null) {
                bl2 = bl;
                if (arrby.length >= 3) {
                    bl2 = bl;
                    if (arrby.length < 256) {
                        bl2 = this.parseCivicTLVs(arrby);
                    }
                }
            }
            this.mIsValid = bl2;
            return;
        }
        this.mIsValid = false;
    }

    private String formatAddressElement(String string2, String string3) {
        if (string3 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(string3);
            return stringBuilder.toString();
        }
        return "";
    }

    private int[] getSparseArrayKeys(SparseArray<String> sparseArray) {
        int n = sparseArray.size();
        int[] arrn = new int[n];
        for (int i = 0; i < n; ++i) {
            arrn[i] = sparseArray.keyAt(i);
        }
        return arrn;
    }

    private String[] getSparseArrayValues(SparseArray<String> sparseArray) {
        int n = sparseArray.size();
        String[] arrstring = new String[n];
        for (int i = 0; i < n; ++i) {
            arrstring[i] = sparseArray.valueAt(i);
        }
        return arrstring;
    }

    private boolean isSparseArrayStringEqual(SparseArray<String> sparseArray, SparseArray<String> sparseArray2) {
        int n = sparseArray.size();
        if (n != sparseArray2.size()) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (sparseArray.valueAt(i).equals(sparseArray2.valueAt(i))) continue;
            return false;
        }
        return true;
    }

    private boolean parseCivicTLVs(byte[] arrby) {
        byte by;
        int n = arrby.length;
        for (int i = 0; i < n; i += by + 2) {
            byte by2 = arrby[i + 0];
            by = arrby[i + 1];
            if (by == 0) continue;
            if (i + 2 + by > n) {
                return false;
            }
            this.mCivicAddressElements.put(by2 & 255, new String(arrby, i + 2, (int)by, StandardCharsets.UTF_8));
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof CivicLocation)) {
            return false;
        }
        object = (CivicLocation)object;
        if (this.mIsValid != ((CivicLocation)object).mIsValid || !Objects.equals(this.mCountryCode, ((CivicLocation)object).mCountryCode) || !this.isSparseArrayStringEqual(this.mCivicAddressElements, ((CivicLocation)object).mCivicAddressElements)) {
            bl = false;
        }
        return bl;
    }

    public String getCivicElementValue(int n) {
        return this.mCivicAddressElements.get(n);
    }

    public int hashCode() {
        int[] arrn = this.getSparseArrayKeys(this.mCivicAddressElements);
        String[] arrstring = this.getSparseArrayValues(this.mCivicAddressElements);
        return Objects.hash(this.mIsValid, this.mCountryCode, arrn, arrstring);
    }

    public boolean isValid() {
        return this.mIsValid;
    }

    public Address toAddress() {
        if (!this.mIsValid) {
            return null;
        }
        Address address = new Address(Locale.US);
        String string2 = this.formatAddressElement("Room: ", this.getCivicElementValue(28));
        String string3 = this.formatAddressElement(" Desk: ", this.getCivicElementValue(33));
        CharSequence charSequence = this.formatAddressElement(", Flr: ", this.getCivicElementValue(27));
        String string4 = this.formatAddressElement("", this.getCivicElementValue(19));
        String string5 = this.formatAddressElement("", this.getCivicElementValue(20));
        String string6 = this.formatAddressElement(" ", this.getCivicElementValue(34));
        CharSequence charSequence2 = this.formatAddressElement(" ", this.getCivicElementValue(18));
        String string7 = this.formatAddressElement(", Apt: ", this.getCivicElementValue(26));
        String string8 = this.formatAddressElement("", this.getCivicElementValue(3));
        String string9 = this.formatAddressElement("", this.getCivicElementValue(1));
        String string10 = this.formatAddressElement(" ", this.getCivicElementValue(24));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(string3);
        stringBuilder.append((String)charSequence);
        string3 = stringBuilder.toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string4);
        ((StringBuilder)charSequence).append(string5);
        ((StringBuilder)charSequence).append(string6);
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(string7);
        string6 = ((StringBuilder)charSequence).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append(string9);
        ((StringBuilder)charSequence2).append(string10);
        string9 = ((StringBuilder)charSequence2).toString();
        string10 = this.mCountryCode;
        address.setAddressLine(0, string3);
        address.setAddressLine(1, string6);
        address.setAddressLine(2, string8);
        address.setAddressLine(3, string9);
        address.setAddressLine(4, string10);
        address.setFeatureName(this.getCivicElementValue(23));
        address.setSubThoroughfare(this.getCivicElementValue(19));
        address.setThoroughfare(this.getCivicElementValue(34));
        address.setSubLocality(this.getCivicElementValue(5));
        address.setSubAdminArea(this.getCivicElementValue(2));
        address.setAdminArea(this.getCivicElementValue(1));
        address.setPostalCode(this.getCivicElementValue(24));
        address.setCountryCode(this.mCountryCode);
        return address;
    }

    public SparseArray<String> toSparseArray() {
        return this.mCivicAddressElements;
    }

    public String toString() {
        return this.mCivicAddressElements.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByte((byte)(this.mIsValid ? 1 : 0));
        parcel.writeString(this.mCountryCode);
        parcel.writeSparseArray(this.mCivicAddressElements);
    }

}

