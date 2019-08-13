/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.feature;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SystemApi
public final class CapabilityChangeRequest
implements Parcelable {
    public static final Parcelable.Creator<CapabilityChangeRequest> CREATOR = new Parcelable.Creator<CapabilityChangeRequest>(){

        @Override
        public CapabilityChangeRequest createFromParcel(Parcel parcel) {
            return new CapabilityChangeRequest(parcel);
        }

        public CapabilityChangeRequest[] newArray(int n) {
            return new CapabilityChangeRequest[n];
        }
    };
    private final Set<CapabilityPair> mCapabilitiesToDisable;
    private final Set<CapabilityPair> mCapabilitiesToEnable;

    public CapabilityChangeRequest() {
        this.mCapabilitiesToEnable = new ArraySet<CapabilityPair>();
        this.mCapabilitiesToDisable = new ArraySet<CapabilityPair>();
    }

    protected CapabilityChangeRequest(Parcel parcel) {
        int n;
        int n2 = parcel.readInt();
        this.mCapabilitiesToEnable = new ArraySet<CapabilityPair>(n2);
        for (n = 0; n < n2; ++n) {
            this.mCapabilitiesToEnable.add(new CapabilityPair(parcel.readInt(), parcel.readInt()));
        }
        n2 = parcel.readInt();
        this.mCapabilitiesToDisable = new ArraySet<CapabilityPair>(n2);
        for (n = 0; n < n2; ++n) {
            this.mCapabilitiesToDisable.add(new CapabilityPair(parcel.readInt(), parcel.readInt()));
        }
    }

    private void addAllCapabilities(Set<CapabilityPair> set, int n, int n2) {
        long l = Long.highestOneBit(n);
        int n3 = 1;
        while ((long)n3 <= l) {
            if ((n3 & n) > 0) {
                set.add(new CapabilityPair(n3, n2));
            }
            n3 *= 2;
        }
    }

    public void addCapabilitiesToDisableForTech(int n, int n2) {
        this.addAllCapabilities(this.mCapabilitiesToDisable, n, n2);
    }

    public void addCapabilitiesToEnableForTech(int n, int n2) {
        this.addAllCapabilities(this.mCapabilitiesToEnable, n, n2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CapabilityChangeRequest)) {
            return false;
        }
        object = (CapabilityChangeRequest)object;
        if (!this.mCapabilitiesToEnable.equals(((CapabilityChangeRequest)object).mCapabilitiesToEnable)) {
            return false;
        }
        return this.mCapabilitiesToDisable.equals(((CapabilityChangeRequest)object).mCapabilitiesToDisable);
    }

    public List<CapabilityPair> getCapabilitiesToDisable() {
        return new ArrayList<CapabilityPair>(this.mCapabilitiesToDisable);
    }

    public List<CapabilityPair> getCapabilitiesToEnable() {
        return new ArrayList<CapabilityPair>(this.mCapabilitiesToEnable);
    }

    public int hashCode() {
        return this.mCapabilitiesToEnable.hashCode() * 31 + this.mCapabilitiesToDisable.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CapabilityChangeRequest{mCapabilitiesToEnable=");
        stringBuilder.append(this.mCapabilitiesToEnable);
        stringBuilder.append(", mCapabilitiesToDisable=");
        stringBuilder.append(this.mCapabilitiesToDisable);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCapabilitiesToEnable.size());
        for (CapabilityPair capabilityPair : this.mCapabilitiesToEnable) {
            parcel.writeInt(capabilityPair.getCapability());
            parcel.writeInt(capabilityPair.getRadioTech());
        }
        parcel.writeInt(this.mCapabilitiesToDisable.size());
        for (CapabilityPair capabilityPair : this.mCapabilitiesToDisable) {
            parcel.writeInt(capabilityPair.getCapability());
            parcel.writeInt(capabilityPair.getRadioTech());
        }
    }

    public static class CapabilityPair {
        private final int mCapability;
        private final int radioTech;

        public CapabilityPair(int n, int n2) {
            this.mCapability = n;
            this.radioTech = n2;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof CapabilityPair)) {
                return false;
            }
            object = (CapabilityPair)object;
            if (this.getCapability() != ((CapabilityPair)object).getCapability()) {
                return false;
            }
            if (this.getRadioTech() != ((CapabilityPair)object).getRadioTech()) {
                bl = false;
            }
            return bl;
        }

        public int getCapability() {
            return this.mCapability;
        }

        public int getRadioTech() {
            return this.radioTech;
        }

        public int hashCode() {
            return this.getCapability() * 31 + this.getRadioTech();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CapabilityPair{mCapability=");
            stringBuilder.append(this.mCapability);
            stringBuilder.append(", radioTech=");
            stringBuilder.append(this.radioTech);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

}

