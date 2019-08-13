/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public final class ConfigRequest
implements Parcelable {
    public static final int CLUSTER_ID_MAX = 65535;
    public static final int CLUSTER_ID_MIN = 0;
    public static final Parcelable.Creator<ConfigRequest> CREATOR = new Parcelable.Creator<ConfigRequest>(){

        @Override
        public ConfigRequest createFromParcel(Parcel parcel) {
            boolean bl = parcel.readInt() != 0;
            return new ConfigRequest(bl, parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.createIntArray());
        }

        public ConfigRequest[] newArray(int n) {
            return new ConfigRequest[n];
        }
    };
    public static final int DW_DISABLE = 0;
    public static final int DW_INTERVAL_NOT_INIT = -1;
    public static final int NAN_BAND_24GHZ = 0;
    public static final int NAN_BAND_5GHZ = 1;
    public final int mClusterHigh;
    public final int mClusterLow;
    public final int[] mDiscoveryWindowInterval;
    public final int mMasterPreference;
    public final boolean mSupport5gBand;

    private ConfigRequest(boolean bl, int n, int n2, int n3, int[] arrn) {
        this.mSupport5gBand = bl;
        this.mMasterPreference = n;
        this.mClusterLow = n2;
        this.mClusterHigh = n3;
        this.mDiscoveryWindowInterval = arrn;
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
        if (!(object instanceof ConfigRequest)) {
            return false;
        }
        object = (ConfigRequest)object;
        if (this.mSupport5gBand != ((ConfigRequest)object).mSupport5gBand || this.mMasterPreference != ((ConfigRequest)object).mMasterPreference || this.mClusterLow != ((ConfigRequest)object).mClusterLow || this.mClusterHigh != ((ConfigRequest)object).mClusterHigh || !Arrays.equals(this.mDiscoveryWindowInterval, ((ConfigRequest)object).mDiscoveryWindowInterval)) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return ((((17 * 31 + this.mSupport5gBand) * 31 + this.mMasterPreference) * 31 + this.mClusterLow) * 31 + this.mClusterHigh) * 31 + Arrays.hashCode(this.mDiscoveryWindowInterval);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ConfigRequest [mSupport5gBand=");
        stringBuilder.append(this.mSupport5gBand);
        stringBuilder.append(", mMasterPreference=");
        stringBuilder.append(this.mMasterPreference);
        stringBuilder.append(", mClusterLow=");
        stringBuilder.append(this.mClusterLow);
        stringBuilder.append(", mClusterHigh=");
        stringBuilder.append(this.mClusterHigh);
        stringBuilder.append(", mDiscoveryWindowInterval=");
        stringBuilder.append(Arrays.toString(this.mDiscoveryWindowInterval));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void validate() throws IllegalArgumentException {
        int n = this.mMasterPreference;
        if (n >= 0) {
            if (n != 1 && n != 255 && n <= 255) {
                n = this.mClusterLow;
                if (n >= 0) {
                    if (n <= 65535) {
                        int n2 = this.mClusterHigh;
                        if (n2 >= 0) {
                            if (n2 <= 65535) {
                                if (n <= n2) {
                                    int[] arrn = this.mDiscoveryWindowInterval;
                                    if (arrn.length == 2) {
                                        if (arrn[0] != -1 && (arrn[0] < 1 || arrn[0] > 5)) {
                                            throw new IllegalArgumentException("Invalid discovery window interval for 2.4GHz: valid is UNSET or [1,5]");
                                        }
                                        arrn = this.mDiscoveryWindowInterval;
                                        if (arrn[1] != -1 && (arrn[1] < 0 || arrn[1] > 5)) {
                                            throw new IllegalArgumentException("Invalid discovery window interval for 5GHz: valid is UNSET or [0,5]");
                                        }
                                        return;
                                    }
                                    throw new IllegalArgumentException("Invalid discovery window interval: must have 2 elements (2.4 & 5");
                                }
                                throw new IllegalArgumentException("Invalid argument combination - must have Cluster Low <= Cluster High");
                            }
                            throw new IllegalArgumentException("Cluster specification must not exceed 0xFFFF");
                        }
                        throw new IllegalArgumentException("Cluster specification must be non-negative");
                    }
                    throw new IllegalArgumentException("Cluster specification must not exceed 0xFFFF");
                }
                throw new IllegalArgumentException("Cluster specification must be non-negative");
            }
            throw new IllegalArgumentException("Master Preference specification must not exceed 255 or use 1 or 255 (reserved values)");
        }
        throw new IllegalArgumentException("Master Preference specification must be non-negative");
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.mSupport5gBand);
        parcel.writeInt(this.mMasterPreference);
        parcel.writeInt(this.mClusterLow);
        parcel.writeInt(this.mClusterHigh);
        parcel.writeIntArray(this.mDiscoveryWindowInterval);
    }

    public static final class Builder {
        private int mClusterHigh = 65535;
        private int mClusterLow = 0;
        private int[] mDiscoveryWindowInterval = new int[]{-1, -1};
        private int mMasterPreference = 0;
        private boolean mSupport5gBand = true;

        public ConfigRequest build() {
            int n = this.mClusterLow;
            int n2 = this.mClusterHigh;
            if (n <= n2) {
                return new ConfigRequest(this.mSupport5gBand, this.mMasterPreference, n, n2, this.mDiscoveryWindowInterval);
            }
            throw new IllegalArgumentException("Invalid argument combination - must have Cluster Low <= Cluster High");
        }

        public Builder setClusterHigh(int n) {
            if (n >= 0) {
                if (n <= 65535) {
                    this.mClusterHigh = n;
                    return this;
                }
                throw new IllegalArgumentException("Cluster specification must not exceed 0xFFFF");
            }
            throw new IllegalArgumentException("Cluster specification must be non-negative");
        }

        public Builder setClusterLow(int n) {
            if (n >= 0) {
                if (n <= 65535) {
                    this.mClusterLow = n;
                    return this;
                }
                throw new IllegalArgumentException("Cluster specification must not exceed 0xFFFF");
            }
            throw new IllegalArgumentException("Cluster specification must be non-negative");
        }

        public Builder setDiscoveryWindowInterval(int n, int n2) {
            if (n != 0 && n != 1) {
                throw new IllegalArgumentException("Invalid band value");
            }
            if (n == 0 && (n2 < 1 || n2 > 5) || n == 1 && (n2 < 0 || n2 > 5)) {
                throw new IllegalArgumentException("Invalid interval value: 2.4 GHz [1,5] or 5GHz [0,5]");
            }
            this.mDiscoveryWindowInterval[n] = n2;
            return this;
        }

        public Builder setMasterPreference(int n) {
            if (n >= 0) {
                if (n != 1 && n != 255 && n <= 255) {
                    this.mMasterPreference = n;
                    return this;
                }
                throw new IllegalArgumentException("Master Preference specification must not exceed 255 or use 1 or 255 (reserved values)");
            }
            throw new IllegalArgumentException("Master Preference specification must be non-negative");
        }

        public Builder setSupport5gBand(boolean bl) {
            this.mSupport5gBand = bl;
            return this;
        }
    }

}

