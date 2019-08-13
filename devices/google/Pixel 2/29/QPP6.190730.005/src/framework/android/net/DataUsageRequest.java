/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.NetworkTemplate;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class DataUsageRequest
implements Parcelable {
    public static final Parcelable.Creator<DataUsageRequest> CREATOR = new Parcelable.Creator<DataUsageRequest>(){

        @Override
        public DataUsageRequest createFromParcel(Parcel parcel) {
            return new DataUsageRequest(parcel.readInt(), (NetworkTemplate)parcel.readParcelable(null), parcel.readLong());
        }

        public DataUsageRequest[] newArray(int n) {
            return new DataUsageRequest[n];
        }
    };
    public static final String PARCELABLE_KEY = "DataUsageRequest";
    public static final int REQUEST_ID_UNSET = 0;
    public final int requestId;
    public final NetworkTemplate template;
    public final long thresholdInBytes;

    public DataUsageRequest(int n, NetworkTemplate networkTemplate, long l) {
        this.requestId = n;
        this.template = networkTemplate;
        this.thresholdInBytes = l;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof DataUsageRequest;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (DataUsageRequest)object;
            if (((DataUsageRequest)object).requestId != this.requestId || !Objects.equals(((DataUsageRequest)object).template, this.template) || ((DataUsageRequest)object).thresholdInBytes != this.thresholdInBytes) break block1;
            bl = true;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.requestId, this.template, this.thresholdInBytes);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataUsageRequest [ requestId=");
        stringBuilder.append(this.requestId);
        stringBuilder.append(", networkTemplate=");
        stringBuilder.append(this.template);
        stringBuilder.append(", thresholdInBytes=");
        stringBuilder.append(this.thresholdInBytes);
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.requestId);
        parcel.writeParcelable(this.template, n);
        parcel.writeLong(this.thresholdInBytes);
    }

}

