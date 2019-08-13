/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.location.Criteria;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.WorkSource;
import android.util.TimeUtils;

@SystemApi
public final class LocationRequest
implements Parcelable {
    public static final int ACCURACY_BLOCK = 102;
    public static final int ACCURACY_CITY = 104;
    public static final int ACCURACY_FINE = 100;
    public static final Parcelable.Creator<LocationRequest> CREATOR = new Parcelable.Creator<LocationRequest>(){

        @Override
        public LocationRequest createFromParcel(Parcel object) {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setQuality(((Parcel)object).readInt());
            locationRequest.setFastestInterval(((Parcel)object).readLong());
            locationRequest.setInterval(((Parcel)object).readLong());
            locationRequest.setExpireAt(((Parcel)object).readLong());
            locationRequest.setNumUpdates(((Parcel)object).readInt());
            locationRequest.setSmallestDisplacement(((Parcel)object).readFloat());
            int n = ((Parcel)object).readInt();
            boolean bl = true;
            boolean bl2 = n != 0;
            locationRequest.setHideFromAppOps(bl2);
            bl2 = ((Parcel)object).readInt() != 0;
            locationRequest.setLowPowerMode(bl2);
            bl2 = ((Parcel)object).readInt() != 0 ? bl : false;
            locationRequest.setLocationSettingsIgnored(bl2);
            String string2 = ((Parcel)object).readString();
            if (string2 != null) {
                locationRequest.setProvider(string2);
            }
            if ((object = (WorkSource)((Parcel)object).readParcelable(null)) != null) {
                locationRequest.setWorkSource((WorkSource)object);
            }
            return locationRequest;
        }

        public LocationRequest[] newArray(int n) {
            return new LocationRequest[n];
        }
    };
    private static final double FASTEST_INTERVAL_FACTOR = 6.0;
    public static final int POWER_HIGH = 203;
    public static final int POWER_LOW = 201;
    public static final int POWER_NONE = 200;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private long mExpireAt = Long.MAX_VALUE;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private boolean mExplicitFastestInterval = false;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private long mFastestInterval = (long)((double)this.mInterval / 6.0);
    @UnsupportedAppUsage
    private boolean mHideFromAppOps = false;
    @UnsupportedAppUsage
    private long mInterval = 3600000L;
    private boolean mLocationSettingsIgnored = false;
    private boolean mLowPowerMode = false;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mNumUpdates = Integer.MAX_VALUE;
    @UnsupportedAppUsage
    private String mProvider = "fused";
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mQuality = 201;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private float mSmallestDisplacement = 0.0f;
    @UnsupportedAppUsage
    private WorkSource mWorkSource = null;

    public LocationRequest() {
    }

    public LocationRequest(LocationRequest locationRequest) {
        this.mQuality = locationRequest.mQuality;
        this.mInterval = locationRequest.mInterval;
        this.mFastestInterval = locationRequest.mFastestInterval;
        this.mExplicitFastestInterval = locationRequest.mExplicitFastestInterval;
        this.mExpireAt = locationRequest.mExpireAt;
        this.mNumUpdates = locationRequest.mNumUpdates;
        this.mSmallestDisplacement = locationRequest.mSmallestDisplacement;
        this.mProvider = locationRequest.mProvider;
        this.mWorkSource = locationRequest.mWorkSource;
        this.mHideFromAppOps = locationRequest.mHideFromAppOps;
        this.mLowPowerMode = locationRequest.mLowPowerMode;
        this.mLocationSettingsIgnored = locationRequest.mLocationSettingsIgnored;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static void checkDisplacement(float f) {
        if (!(f < 0.0f)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid displacement: ");
        stringBuilder.append(f);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static void checkInterval(long l) {
        if (l >= 0L) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid interval: ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static void checkProvider(String string2) {
        if (string2 != null) {
            return;
        }
        throw new IllegalArgumentException("invalid provider: null");
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static void checkQuality(int n) {
        if (n != 100 && n != 102 && n != 104 && n != 203 && n != 200 && n != 201) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid quality: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static LocationRequest create() {
        return new LocationRequest();
    }

    @SystemApi
    public static LocationRequest createFromDeprecatedCriteria(Criteria parcelable, long l, float f, boolean bl) {
        int n;
        long l2 = l;
        if (l < 0L) {
            l2 = 0L;
        }
        float f2 = f;
        if (f < 0.0f) {
            f2 = 0.0f;
        }
        n = (n = ((Criteria)parcelable).getAccuracy()) != 1 ? (n != 2 ? (((Criteria)parcelable).getPowerRequirement() == 3 ? 203 : 201) : 102) : 100;
        parcelable = new LocationRequest().setQuality(n).setInterval(l2).setFastestInterval(l2).setSmallestDisplacement(f2);
        if (bl) {
            ((LocationRequest)parcelable).setNumUpdates(1);
        }
        return parcelable;
    }

    @SystemApi
    public static LocationRequest createFromDeprecatedProvider(String object, long l, float f, boolean bl) {
        long l2 = l;
        if (l < 0L) {
            l2 = 0L;
        }
        float f2 = f;
        if (f < 0.0f) {
            f2 = 0.0f;
        }
        int n = "passive".equals(object) ? 200 : ("gps".equals(object) ? 100 : 201);
        object = new LocationRequest().setProvider((String)object).setQuality(n).setInterval(l2).setFastestInterval(l2).setSmallestDisplacement(f2);
        if (bl) {
            ((LocationRequest)object).setNumUpdates(1);
        }
        return object;
    }

    public static String qualityToString(int n) {
        if (n != 100) {
            if (n != 102) {
                if (n != 104) {
                    if (n != 203) {
                        if (n != 200) {
                            if (n != 201) {
                                return "???";
                            }
                            return "POWER_LOW";
                        }
                        return "POWER_NONE";
                    }
                    return "POWER_HIGH";
                }
                return "ACCURACY_CITY";
            }
            return "ACCURACY_BLOCK";
        }
        return "ACCURACY_FINE";
    }

    public void decrementNumUpdates() {
        int n = this.mNumUpdates;
        if (n != Integer.MAX_VALUE) {
            this.mNumUpdates = n - 1;
        }
        if (this.mNumUpdates < 0) {
            this.mNumUpdates = 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getExpireAt() {
        return this.mExpireAt;
    }

    public long getFastestInterval() {
        return this.mFastestInterval;
    }

    @SystemApi
    public boolean getHideFromAppOps() {
        return this.mHideFromAppOps;
    }

    public long getInterval() {
        return this.mInterval;
    }

    public int getNumUpdates() {
        return this.mNumUpdates;
    }

    @SystemApi
    public String getProvider() {
        return this.mProvider;
    }

    public int getQuality() {
        return this.mQuality;
    }

    @SystemApi
    public float getSmallestDisplacement() {
        return this.mSmallestDisplacement;
    }

    @SystemApi
    public WorkSource getWorkSource() {
        return this.mWorkSource;
    }

    public boolean isLocationSettingsIgnored() {
        return this.mLocationSettingsIgnored;
    }

    @SystemApi
    public boolean isLowPowerMode() {
        return this.mLowPowerMode;
    }

    public LocationRequest setExpireAt(long l) {
        this.mExpireAt = l;
        if (this.mExpireAt < 0L) {
            this.mExpireAt = 0L;
        }
        return this;
    }

    public LocationRequest setExpireIn(long l) {
        long l2 = SystemClock.elapsedRealtime();
        this.mExpireAt = l > Long.MAX_VALUE - l2 ? Long.MAX_VALUE : l + l2;
        if (this.mExpireAt < 0L) {
            this.mExpireAt = 0L;
        }
        return this;
    }

    public LocationRequest setFastestInterval(long l) {
        LocationRequest.checkInterval(l);
        this.mExplicitFastestInterval = true;
        this.mFastestInterval = l;
        return this;
    }

    @SystemApi
    public void setHideFromAppOps(boolean bl) {
        this.mHideFromAppOps = bl;
    }

    public LocationRequest setInterval(long l) {
        LocationRequest.checkInterval(l);
        this.mInterval = l;
        if (!this.mExplicitFastestInterval) {
            this.mFastestInterval = (long)((double)this.mInterval / 6.0);
        }
        return this;
    }

    public LocationRequest setLocationSettingsIgnored(boolean bl) {
        this.mLocationSettingsIgnored = bl;
        return this;
    }

    @SystemApi
    public LocationRequest setLowPowerMode(boolean bl) {
        this.mLowPowerMode = bl;
        return this;
    }

    public LocationRequest setNumUpdates(int n) {
        if (n > 0) {
            this.mNumUpdates = n;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid numUpdates: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public LocationRequest setProvider(String string2) {
        LocationRequest.checkProvider(string2);
        this.mProvider = string2;
        return this;
    }

    public LocationRequest setQuality(int n) {
        LocationRequest.checkQuality(n);
        this.mQuality = n;
        return this;
    }

    @SystemApi
    public LocationRequest setSmallestDisplacement(float f) {
        LocationRequest.checkDisplacement(f);
        this.mSmallestDisplacement = f;
        return this;
    }

    @SystemApi
    public void setWorkSource(WorkSource workSource) {
        this.mWorkSource = workSource;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Request[");
        stringBuilder.append(LocationRequest.qualityToString(this.mQuality));
        if (this.mProvider != null) {
            stringBuilder.append(' ');
            stringBuilder.append(this.mProvider);
        }
        if (this.mQuality != 200) {
            stringBuilder.append(" requested=");
            TimeUtils.formatDuration(this.mInterval, stringBuilder);
        }
        stringBuilder.append(" fastest=");
        TimeUtils.formatDuration(this.mFastestInterval, stringBuilder);
        long l = this.mExpireAt;
        if (l != Long.MAX_VALUE) {
            long l2 = SystemClock.elapsedRealtime();
            stringBuilder.append(" expireIn=");
            TimeUtils.formatDuration(l - l2, stringBuilder);
        }
        if (this.mNumUpdates != Integer.MAX_VALUE) {
            stringBuilder.append(" num=");
            stringBuilder.append(this.mNumUpdates);
        }
        if (this.mLowPowerMode) {
            stringBuilder.append(" lowPowerMode");
        }
        if (this.mLocationSettingsIgnored) {
            stringBuilder.append(" locationSettingsIgnored");
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mQuality);
        parcel.writeLong(this.mFastestInterval);
        parcel.writeLong(this.mInterval);
        parcel.writeLong(this.mExpireAt);
        parcel.writeInt(this.mNumUpdates);
        parcel.writeFloat(this.mSmallestDisplacement);
        parcel.writeInt((int)this.mHideFromAppOps);
        parcel.writeInt((int)this.mLowPowerMode);
        parcel.writeInt((int)this.mLocationSettingsIgnored);
        parcel.writeString(this.mProvider);
        parcel.writeParcelable(this.mWorkSource, 0);
    }

}

