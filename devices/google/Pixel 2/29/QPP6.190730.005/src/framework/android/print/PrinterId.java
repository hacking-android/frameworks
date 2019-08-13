/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

public final class PrinterId
implements Parcelable {
    public static final Parcelable.Creator<PrinterId> CREATOR = new Parcelable.Creator<PrinterId>(){

        @Override
        public PrinterId createFromParcel(Parcel parcel) {
            return new PrinterId(parcel);
        }

        public PrinterId[] newArray(int n) {
            return new PrinterId[n];
        }
    };
    private final String mLocalId;
    private final ComponentName mServiceName;

    public PrinterId(ComponentName componentName, String string2) {
        this.mServiceName = componentName;
        this.mLocalId = string2;
    }

    private PrinterId(Parcel parcel) {
        this.mServiceName = Preconditions.checkNotNull((ComponentName)parcel.readParcelable(null));
        this.mLocalId = Preconditions.checkNotNull(parcel.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (PrinterId)object;
        if (!this.mServiceName.equals(((PrinterId)object).mServiceName)) {
            return false;
        }
        return this.mLocalId.equals(((PrinterId)object).mLocalId);
    }

    public String getLocalId() {
        return this.mLocalId;
    }

    @UnsupportedAppUsage
    public ComponentName getServiceName() {
        return this.mServiceName;
    }

    public int hashCode() {
        return (1 * 31 + this.mServiceName.hashCode()) * 31 + this.mLocalId.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PrinterId{");
        stringBuilder.append("serviceName=");
        stringBuilder.append(this.mServiceName.flattenToString());
        stringBuilder.append(", localId=");
        stringBuilder.append(this.mLocalId);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mServiceName, n);
        parcel.writeString(this.mLocalId);
    }

}

