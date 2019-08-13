/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class WifiDisplay
implements Parcelable {
    public static final Parcelable.Creator<WifiDisplay> CREATOR;
    public static final WifiDisplay[] EMPTY_ARRAY;
    private final boolean mCanConnect;
    private final String mDeviceAddress;
    private final String mDeviceAlias;
    private final String mDeviceName;
    private final boolean mIsAvailable;
    private final boolean mIsRemembered;

    static {
        EMPTY_ARRAY = new WifiDisplay[0];
        CREATOR = new Parcelable.Creator<WifiDisplay>(){

            @Override
            public WifiDisplay createFromParcel(Parcel parcel) {
                String string2 = parcel.readString();
                String string3 = parcel.readString();
                String string4 = parcel.readString();
                boolean bl = parcel.readInt() != 0;
                boolean bl2 = parcel.readInt() != 0;
                boolean bl3 = parcel.readInt() != 0;
                return new WifiDisplay(string2, string3, string4, bl, bl2, bl3);
            }

            public WifiDisplay[] newArray(int n) {
                WifiDisplay[] arrwifiDisplay = n == 0 ? EMPTY_ARRAY : new WifiDisplay[n];
                return arrwifiDisplay;
            }
        };
    }

    public WifiDisplay(String string2, String string3, String string4, boolean bl, boolean bl2, boolean bl3) {
        if (string2 != null) {
            if (string3 != null) {
                this.mDeviceAddress = string2;
                this.mDeviceName = string3;
                this.mDeviceAlias = string4;
                this.mIsAvailable = bl;
                this.mCanConnect = bl2;
                this.mIsRemembered = bl3;
                return;
            }
            throw new IllegalArgumentException("deviceName must not be null");
        }
        throw new IllegalArgumentException("deviceAddress must not be null");
    }

    @UnsupportedAppUsage
    public boolean canConnect() {
        return this.mCanConnect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public boolean equals(WifiDisplay wifiDisplay) {
        boolean bl = wifiDisplay != null && this.mDeviceAddress.equals(wifiDisplay.mDeviceAddress) && this.mDeviceName.equals(wifiDisplay.mDeviceName) && Objects.equals(this.mDeviceAlias, wifiDisplay.mDeviceAlias);
        return bl;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof WifiDisplay && this.equals((WifiDisplay)object);
        return bl;
    }

    @UnsupportedAppUsage
    public String getDeviceAddress() {
        return this.mDeviceAddress;
    }

    @UnsupportedAppUsage
    public String getDeviceAlias() {
        return this.mDeviceAlias;
    }

    @UnsupportedAppUsage
    public String getDeviceName() {
        return this.mDeviceName;
    }

    public String getFriendlyDisplayName() {
        String string2 = this.mDeviceAlias;
        if (string2 == null) {
            string2 = this.mDeviceName;
        }
        return string2;
    }

    public boolean hasSameAddress(WifiDisplay wifiDisplay) {
        boolean bl = wifiDisplay != null && this.mDeviceAddress.equals(wifiDisplay.mDeviceAddress);
        return bl;
    }

    public int hashCode() {
        return this.mDeviceAddress.hashCode();
    }

    @UnsupportedAppUsage
    public boolean isAvailable() {
        return this.mIsAvailable;
    }

    @UnsupportedAppUsage
    public boolean isRemembered() {
        return this.mIsRemembered;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.mDeviceName);
        ((StringBuilder)charSequence).append(" (");
        ((StringBuilder)charSequence).append(this.mDeviceAddress);
        ((StringBuilder)charSequence).append(")");
        CharSequence charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = charSequence2;
        if (this.mDeviceAlias != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(", alias ");
            ((StringBuilder)charSequence).append(this.mDeviceAlias);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(", isAvailable ");
        ((StringBuilder)charSequence2).append(this.mIsAvailable);
        ((StringBuilder)charSequence2).append(", canConnect ");
        ((StringBuilder)charSequence2).append(this.mCanConnect);
        ((StringBuilder)charSequence2).append(", isRemembered ");
        ((StringBuilder)charSequence2).append(this.mIsRemembered);
        return ((StringBuilder)charSequence2).toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mDeviceAddress);
        parcel.writeString(this.mDeviceName);
        parcel.writeString(this.mDeviceAlias);
        parcel.writeInt((int)this.mIsAvailable);
        parcel.writeInt((int)this.mCanConnect);
        parcel.writeInt((int)this.mIsRemembered);
    }

}

