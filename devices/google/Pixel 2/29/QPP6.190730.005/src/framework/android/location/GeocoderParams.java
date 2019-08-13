/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;

public class GeocoderParams
implements Parcelable {
    public static final Parcelable.Creator<GeocoderParams> CREATOR = new Parcelable.Creator<GeocoderParams>(){

        @Override
        public GeocoderParams createFromParcel(Parcel parcel) {
            GeocoderParams geocoderParams = new GeocoderParams();
            geocoderParams.mLocale = new Locale(parcel.readString(), parcel.readString(), parcel.readString());
            geocoderParams.mPackageName = parcel.readString();
            return geocoderParams;
        }

        public GeocoderParams[] newArray(int n) {
            return new GeocoderParams[n];
        }
    };
    private Locale mLocale;
    private String mPackageName;

    private GeocoderParams() {
    }

    public GeocoderParams(Context context, Locale locale) {
        this.mLocale = locale;
        this.mPackageName = context.getPackageName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public String getClientPackage() {
        return this.mPackageName;
    }

    @UnsupportedAppUsage
    public Locale getLocale() {
        return this.mLocale;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mLocale.getLanguage());
        parcel.writeString(this.mLocale.getCountry());
        parcel.writeString(this.mLocale.getVariant());
        parcel.writeString(this.mPackageName);
    }

}

