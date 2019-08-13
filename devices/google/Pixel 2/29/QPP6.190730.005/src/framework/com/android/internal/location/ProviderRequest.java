/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.location;

import android.annotation.UnsupportedAppUsage;
import android.location.LocationRequest;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.TimeUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ProviderRequest
implements Parcelable {
    public static final Parcelable.Creator<ProviderRequest> CREATOR = new Parcelable.Creator<ProviderRequest>(){

        @Override
        public ProviderRequest createFromParcel(Parcel parcel) {
            ProviderRequest providerRequest = new ProviderRequest();
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            providerRequest.reportLocation = bl;
            providerRequest.interval = parcel.readLong();
            providerRequest.lowPowerMode = parcel.readBoolean();
            providerRequest.locationSettingsIgnored = parcel.readBoolean();
            int n2 = parcel.readInt();
            for (n = 0; n < n2; ++n) {
                providerRequest.locationRequests.add(LocationRequest.CREATOR.createFromParcel(parcel));
            }
            return providerRequest;
        }

        public ProviderRequest[] newArray(int n) {
            return new ProviderRequest[n];
        }
    };
    @UnsupportedAppUsage
    public long interval = Long.MAX_VALUE;
    @UnsupportedAppUsage
    public final List<LocationRequest> locationRequests = new ArrayList<LocationRequest>();
    public boolean locationSettingsIgnored = false;
    public boolean lowPowerMode = false;
    @UnsupportedAppUsage
    public boolean reportLocation = false;

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ProviderRequest[");
        if (this.reportLocation) {
            stringBuilder.append("ON");
            stringBuilder.append(" interval=");
            TimeUtils.formatDuration(this.interval, stringBuilder);
            if (this.lowPowerMode) {
                stringBuilder.append(" lowPowerMode");
            }
            if (this.locationSettingsIgnored) {
                stringBuilder.append(" locationSettingsIgnored");
            }
        } else {
            stringBuilder.append("OFF");
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.reportLocation);
        parcel.writeLong(this.interval);
        parcel.writeBoolean(this.lowPowerMode);
        parcel.writeBoolean(this.locationSettingsIgnored);
        parcel.writeInt(this.locationRequests.size());
        Iterator<LocationRequest> iterator = this.locationRequests.iterator();
        while (iterator.hasNext()) {
            iterator.next().writeToParcel(parcel, n);
        }
    }

}

