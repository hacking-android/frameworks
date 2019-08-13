/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.mbms.ServiceInfo;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class StreamingServiceInfo
extends ServiceInfo
implements Parcelable {
    public static final Parcelable.Creator<StreamingServiceInfo> CREATOR = new Parcelable.Creator<StreamingServiceInfo>(){

        @Override
        public StreamingServiceInfo createFromParcel(Parcel parcel) {
            return new StreamingServiceInfo(parcel);
        }

        public StreamingServiceInfo[] newArray(int n) {
            return new StreamingServiceInfo[n];
        }
    };

    private StreamingServiceInfo(Parcel parcel) {
        super(parcel);
    }

    @SystemApi
    public StreamingServiceInfo(Map<Locale, String> map, String string2, List<Locale> list, String string3, Date date, Date date2) {
        super(map, string2, list, string3, date, date2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
    }

}

