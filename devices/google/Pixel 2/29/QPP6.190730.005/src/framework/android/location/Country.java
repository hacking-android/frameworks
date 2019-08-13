/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import java.util.Locale;

public class Country
implements Parcelable {
    public static final int COUNTRY_SOURCE_LOCALE = 3;
    public static final int COUNTRY_SOURCE_LOCATION = 1;
    public static final int COUNTRY_SOURCE_NETWORK = 0;
    public static final int COUNTRY_SOURCE_SIM = 2;
    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>(){

        @Override
        public Country createFromParcel(Parcel parcel) {
            return new Country(parcel.readString(), parcel.readInt(), parcel.readLong());
        }

        public Country[] newArray(int n) {
            return new Country[n];
        }
    };
    private final String mCountryIso;
    private int mHashCode;
    private final int mSource;
    private final long mTimestamp;

    public Country(Country country) {
        this.mCountryIso = country.mCountryIso;
        this.mSource = country.mSource;
        this.mTimestamp = country.mTimestamp;
    }

    @UnsupportedAppUsage
    public Country(String string2, int n) {
        if (string2 != null && n >= 0 && n <= 3) {
            this.mCountryIso = string2.toUpperCase(Locale.US);
            this.mSource = n;
            this.mTimestamp = SystemClock.elapsedRealtime();
            return;
        }
        throw new IllegalArgumentException();
    }

    private Country(String string2, int n, long l) {
        if (string2 != null && n >= 0 && n <= 3) {
            this.mCountryIso = string2.toUpperCase(Locale.US);
            this.mSource = n;
            this.mTimestamp = l;
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof Country) {
            if (!this.mCountryIso.equals(((Country)(object = (Country)object)).getCountryIso()) || this.mSource != ((Country)object).getSource()) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public boolean equalsIgnoreSource(Country country) {
        boolean bl = country != null && this.mCountryIso.equals(country.getCountryIso());
        return bl;
    }

    @UnsupportedAppUsage
    public final String getCountryIso() {
        return this.mCountryIso;
    }

    @UnsupportedAppUsage
    public final int getSource() {
        return this.mSource;
    }

    public final long getTimestamp() {
        return this.mTimestamp;
    }

    public int hashCode() {
        if (this.mHashCode == 0) {
            this.mHashCode = (17 * 13 + this.mCountryIso.hashCode()) * 13 + this.mSource;
        }
        return this.mHashCode;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Country {ISO=");
        stringBuilder.append(this.mCountryIso);
        stringBuilder.append(", source=");
        stringBuilder.append(this.mSource);
        stringBuilder.append(", time=");
        stringBuilder.append(this.mTimestamp);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mCountryIso);
        parcel.writeInt(this.mSource);
        parcel.writeLong(this.mTimestamp);
    }

}

