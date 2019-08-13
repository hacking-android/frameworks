/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.Objects;
import java.util.regex.Pattern;

@SystemApi
public final class PhoneNumberRange
implements Parcelable {
    public static final Parcelable.Creator<PhoneNumberRange> CREATOR = new Parcelable.Creator<PhoneNumberRange>(){

        @Override
        public PhoneNumberRange createFromParcel(Parcel parcel) {
            return new PhoneNumberRange(parcel);
        }

        public PhoneNumberRange[] newArray(int n) {
            return new PhoneNumberRange[n];
        }
    };
    private final String mCountryCode;
    private final String mLowerBound;
    private final String mPrefix;
    private final String mUpperBound;

    private PhoneNumberRange(Parcel parcel) {
        this.mCountryCode = parcel.readStringNoHelper();
        this.mPrefix = parcel.readStringNoHelper();
        this.mLowerBound = parcel.readStringNoHelper();
        this.mUpperBound = parcel.readStringNoHelper();
    }

    public PhoneNumberRange(String string2, String string3, String string4, String string5) {
        this.validateLowerAndUpperBounds(string4, string5);
        if (Pattern.matches("[0-9]*", string2)) {
            if (Pattern.matches("[0-9]*", string3)) {
                this.mCountryCode = string2;
                this.mPrefix = string3;
                this.mLowerBound = string4;
                this.mUpperBound = string5;
                return;
            }
            throw new IllegalArgumentException("Prefix must be all numeric");
        }
        throw new IllegalArgumentException("Country code must be all numeric");
    }

    private void validateLowerAndUpperBounds(String string2, String string3) {
        if (string2.length() == string3.length()) {
            if (Pattern.matches("[0-9]*", string2)) {
                if (Pattern.matches("[0-9]*", string3)) {
                    if (Integer.parseInt(string2) <= Integer.parseInt(string3)) {
                        return;
                    }
                    throw new IllegalArgumentException("Lower bound must be lower than upper bound");
                }
                throw new IllegalArgumentException("Upper bound must be all numeric");
            }
            throw new IllegalArgumentException("Lower bound must be all numeric");
        }
        throw new IllegalArgumentException("Lower and upper bounds must have the same length");
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
        if (object != null && this.getClass() == object.getClass()) {
            object = (PhoneNumberRange)object;
            if (!(Objects.equals(this.mCountryCode, ((PhoneNumberRange)object).mCountryCode) && Objects.equals(this.mPrefix, ((PhoneNumberRange)object).mPrefix) && Objects.equals(this.mLowerBound, ((PhoneNumberRange)object).mLowerBound) && Objects.equals(this.mUpperBound, ((PhoneNumberRange)object).mUpperBound))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mCountryCode, this.mPrefix, this.mLowerBound, this.mUpperBound);
    }

    public boolean matches(String string2) {
        block9 : {
            boolean bl;
            boolean bl2;
            block8 : {
                block7 : {
                    string2 = string2.replaceAll("[^0-9]", "");
                    CharSequence charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(this.mCountryCode);
                    ((StringBuilder)charSequence).append(this.mPrefix);
                    charSequence = ((StringBuilder)charSequence).toString();
                    bl2 = string2.startsWith((String)charSequence);
                    bl = false;
                    if (!bl2) break block7;
                    string2 = string2.substring(((String)charSequence).length());
                    break block8;
                }
                if (!string2.startsWith(this.mPrefix)) break block9;
                string2 = string2.substring(this.mPrefix.length());
            }
            try {
                int n = Integer.parseInt(this.mLowerBound);
                int n2 = Integer.parseInt(this.mUpperBound);
                int n3 = Integer.parseInt(string2);
                bl2 = bl;
                if (n3 <= n2) {
                    bl2 = bl;
                    if (n3 >= n) {
                        bl2 = true;
                    }
                }
                return bl2;
            }
            catch (NumberFormatException numberFormatException) {
                Log.e(PhoneNumberRange.class.getSimpleName(), "Invalid bounds or number.", numberFormatException);
                return false;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PhoneNumberRange{mCountryCode='");
        stringBuilder.append(this.mCountryCode);
        stringBuilder.append('\'');
        stringBuilder.append(", mPrefix='");
        stringBuilder.append(this.mPrefix);
        stringBuilder.append('\'');
        stringBuilder.append(", mLowerBound='");
        stringBuilder.append(this.mLowerBound);
        stringBuilder.append('\'');
        stringBuilder.append(", mUpperBound='");
        stringBuilder.append(this.mUpperBound);
        stringBuilder.append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStringNoHelper(this.mCountryCode);
        parcel.writeStringNoHelper(this.mPrefix);
        parcel.writeStringNoHelper(this.mLowerBound);
        parcel.writeStringNoHelper(this.mUpperBound);
    }

}

