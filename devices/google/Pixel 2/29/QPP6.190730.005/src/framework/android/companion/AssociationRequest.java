/*
 * Decompiled with CFR 0.145.
 */
package android.companion;

import android.annotation.UnsupportedAppUsage;
import android.companion.DeviceFilter;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.OneTimeUseBuilder;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class AssociationRequest
implements Parcelable {
    public static final Parcelable.Creator<AssociationRequest> CREATOR = new Parcelable.Creator<AssociationRequest>(){

        @Override
        public AssociationRequest createFromParcel(Parcel parcel) {
            return new AssociationRequest(parcel);
        }

        public AssociationRequest[] newArray(int n) {
            return new AssociationRequest[n];
        }
    };
    private final List<DeviceFilter<?>> mDeviceFilters;
    private final boolean mSingleDevice;

    private AssociationRequest(Parcel parcel) {
        boolean bl = parcel.readByte() != 0;
        this(bl, parcel.readParcelableList(new ArrayList(), AssociationRequest.class.getClassLoader()));
    }

    private AssociationRequest(boolean bl, List<DeviceFilter<?>> list) {
        this.mSingleDevice = bl;
        this.mDeviceFilters = CollectionUtils.emptyIfNull(list);
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
            object = (AssociationRequest)object;
            if (this.mSingleDevice != ((AssociationRequest)object).mSingleDevice || !Objects.equals(this.mDeviceFilters, ((AssociationRequest)object).mDeviceFilters)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @UnsupportedAppUsage
    public List<DeviceFilter<?>> getDeviceFilters() {
        return this.mDeviceFilters;
    }

    public int hashCode() {
        return Objects.hash(this.mSingleDevice, this.mDeviceFilters);
    }

    @UnsupportedAppUsage
    public boolean isSingleDevice() {
        return this.mSingleDevice;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AssociationRequest{mSingleDevice=");
        stringBuilder.append(this.mSingleDevice);
        stringBuilder.append(", mDeviceFilters=");
        stringBuilder.append(this.mDeviceFilters);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByte((byte)(this.mSingleDevice ? 1 : 0));
        parcel.writeParcelableList(this.mDeviceFilters, n);
    }

    public static final class Builder
    extends OneTimeUseBuilder<AssociationRequest> {
        private ArrayList<DeviceFilter<?>> mDeviceFilters = null;
        private boolean mSingleDevice = false;

        public Builder addDeviceFilter(DeviceFilter<?> deviceFilter) {
            this.checkNotUsed();
            if (deviceFilter != null) {
                this.mDeviceFilters = ArrayUtils.add(this.mDeviceFilters, deviceFilter);
            }
            return this;
        }

        @Override
        public AssociationRequest build() {
            this.markUsed();
            return new AssociationRequest(this.mSingleDevice, this.mDeviceFilters);
        }

        public Builder setSingleDevice(boolean bl) {
            this.checkNotUsed();
            this.mSingleDevice = bl;
            return this;
        }
    }

}

