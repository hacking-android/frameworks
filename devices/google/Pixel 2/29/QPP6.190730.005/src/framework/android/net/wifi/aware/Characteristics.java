/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public final class Characteristics
implements Parcelable {
    public static final Parcelable.Creator<Characteristics> CREATOR = new Parcelable.Creator<Characteristics>(){

        @Override
        public Characteristics createFromParcel(Parcel parcel) {
            return new Characteristics(parcel.readBundle());
        }

        public Characteristics[] newArray(int n) {
            return new Characteristics[n];
        }
    };
    public static final String KEY_MAX_MATCH_FILTER_LENGTH = "key_max_match_filter_length";
    public static final String KEY_MAX_SERVICE_NAME_LENGTH = "key_max_service_name_length";
    public static final String KEY_MAX_SERVICE_SPECIFIC_INFO_LENGTH = "key_max_service_specific_info_length";
    private Bundle mCharacteristics = new Bundle();

    public Characteristics(Bundle bundle) {
        this.mCharacteristics = bundle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getMaxMatchFilterLength() {
        return this.mCharacteristics.getInt(KEY_MAX_MATCH_FILTER_LENGTH);
    }

    public int getMaxServiceNameLength() {
        return this.mCharacteristics.getInt(KEY_MAX_SERVICE_NAME_LENGTH);
    }

    public int getMaxServiceSpecificInfoLength() {
        return this.mCharacteristics.getInt(KEY_MAX_SERVICE_SPECIFIC_INFO_LENGTH);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.mCharacteristics);
    }

}

