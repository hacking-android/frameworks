/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.util.Collection;
import java.util.Set;

@SystemApi
public final class ImsFeatureConfiguration
implements Parcelable {
    public static final Parcelable.Creator<ImsFeatureConfiguration> CREATOR = new Parcelable.Creator<ImsFeatureConfiguration>(){

        @Override
        public ImsFeatureConfiguration createFromParcel(Parcel parcel) {
            return new ImsFeatureConfiguration(parcel);
        }

        public ImsFeatureConfiguration[] newArray(int n) {
            return new ImsFeatureConfiguration[n];
        }
    };
    private final Set<FeatureSlotPair> mFeatures;

    public ImsFeatureConfiguration() {
        this.mFeatures = new ArraySet<FeatureSlotPair>();
    }

    protected ImsFeatureConfiguration(Parcel parcel) {
        int n = parcel.readInt();
        this.mFeatures = new ArraySet<FeatureSlotPair>(n);
        for (int i = 0; i < n; ++i) {
            this.mFeatures.add(new FeatureSlotPair(parcel.readInt(), parcel.readInt()));
        }
    }

    public ImsFeatureConfiguration(Set<FeatureSlotPair> set) {
        this.mFeatures = new ArraySet<FeatureSlotPair>();
        if (set != null) {
            this.mFeatures.addAll(set);
        }
    }

    void addFeature(int n, int n2) {
        this.mFeatures.add(new FeatureSlotPair(n, n2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ImsFeatureConfiguration)) {
            return false;
        }
        object = (ImsFeatureConfiguration)object;
        return this.mFeatures.equals(((ImsFeatureConfiguration)object).mFeatures);
    }

    public Set<FeatureSlotPair> getServiceFeatures() {
        return new ArraySet<FeatureSlotPair>(this.mFeatures);
    }

    public int hashCode() {
        return this.mFeatures.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        FeatureSlotPair[] arrfeatureSlotPair = new FeatureSlotPair[this.mFeatures.size()];
        this.mFeatures.toArray(arrfeatureSlotPair);
        parcel.writeInt(arrfeatureSlotPair.length);
        for (FeatureSlotPair featureSlotPair : arrfeatureSlotPair) {
            parcel.writeInt(featureSlotPair.slotId);
            parcel.writeInt(featureSlotPair.featureType);
        }
    }

    public static class Builder {
        ImsFeatureConfiguration mConfig = new ImsFeatureConfiguration();

        public Builder addFeature(int n, int n2) {
            this.mConfig.addFeature(n, n2);
            return this;
        }

        public ImsFeatureConfiguration build() {
            return this.mConfig;
        }
    }

    public static final class FeatureSlotPair {
        public final int featureType;
        public final int slotId;

        public FeatureSlotPair(int n, int n2) {
            this.slotId = n;
            this.featureType = n2;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (FeatureSlotPair)object;
                if (this.slotId != ((FeatureSlotPair)object).slotId) {
                    return false;
                }
                if (this.featureType != ((FeatureSlotPair)object).featureType) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return this.slotId * 31 + this.featureType;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{s=");
            stringBuilder.append(this.slotId);
            stringBuilder.append(", f=");
            stringBuilder.append(this.featureType);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}

