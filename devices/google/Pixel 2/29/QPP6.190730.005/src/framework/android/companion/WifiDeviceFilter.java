/*
 * Decompiled with CFR 0.145.
 */
package android.companion;

import android.annotation.SuppressLint;
import android.companion.BluetoothDeviceFilterUtils;
import android.companion.DeviceFilter;
import android.net.wifi.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.OneTimeUseBuilder;
import java.util.Objects;
import java.util.regex.Pattern;

public final class WifiDeviceFilter
implements DeviceFilter<ScanResult> {
    public static final Parcelable.Creator<WifiDeviceFilter> CREATOR = new Parcelable.Creator<WifiDeviceFilter>(){

        @Override
        public WifiDeviceFilter createFromParcel(Parcel parcel) {
            return new WifiDeviceFilter(parcel);
        }

        public WifiDeviceFilter[] newArray(int n) {
            return new WifiDeviceFilter[n];
        }
    };
    private final Pattern mNamePattern;

    @SuppressLint(value={"ParcelClassLoader"})
    private WifiDeviceFilter(Parcel parcel) {
        this(BluetoothDeviceFilterUtils.patternFromString(parcel.readString()));
    }

    private WifiDeviceFilter(Pattern pattern) {
        this.mNamePattern = pattern;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (WifiDeviceFilter)object;
            return Objects.equals(this.mNamePattern, ((WifiDeviceFilter)object).mNamePattern);
        }
        return false;
    }

    @Override
    public String getDeviceDisplayName(ScanResult scanResult) {
        return BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal(scanResult);
    }

    @Override
    public int getMediumType() {
        return 2;
    }

    public Pattern getNamePattern() {
        return this.mNamePattern;
    }

    public int hashCode() {
        return Objects.hash(this.mNamePattern);
    }

    @Override
    public boolean matches(ScanResult scanResult) {
        return BluetoothDeviceFilterUtils.matchesName(this.getNamePattern(), scanResult);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(BluetoothDeviceFilterUtils.patternToString(this.getNamePattern()));
    }

    public static final class Builder
    extends OneTimeUseBuilder<WifiDeviceFilter> {
        private Pattern mNamePattern;

        @Override
        public WifiDeviceFilter build() {
            this.markUsed();
            return new WifiDeviceFilter(this.mNamePattern);
        }

        public Builder setNamePattern(Pattern pattern) {
            this.checkNotUsed();
            this.mNamePattern = pattern;
            return this;
        }
    }

}

