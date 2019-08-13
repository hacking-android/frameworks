/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb;

import android.annotation.SystemApi;
import android.hardware.usb.UsbPort;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.Immutable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
@Immutable
public final class UsbPortStatus
implements Parcelable {
    public static final int CONTAMINANT_DETECTION_DETECTED = 3;
    public static final int CONTAMINANT_DETECTION_DISABLED = 1;
    public static final int CONTAMINANT_DETECTION_NOT_DETECTED = 2;
    public static final int CONTAMINANT_DETECTION_NOT_SUPPORTED = 0;
    public static final int CONTAMINANT_PROTECTION_DISABLED = 8;
    public static final int CONTAMINANT_PROTECTION_FORCE_DISABLE = 4;
    public static final int CONTAMINANT_PROTECTION_NONE = 0;
    public static final int CONTAMINANT_PROTECTION_SINK = 1;
    public static final int CONTAMINANT_PROTECTION_SOURCE = 2;
    public static final Parcelable.Creator<UsbPortStatus> CREATOR = new Parcelable.Creator<UsbPortStatus>(){

        @Override
        public UsbPortStatus createFromParcel(Parcel parcel) {
            return new UsbPortStatus(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public UsbPortStatus[] newArray(int n) {
            return new UsbPortStatus[n];
        }
    };
    public static final int DATA_ROLE_DEVICE = 2;
    public static final int DATA_ROLE_HOST = 1;
    public static final int DATA_ROLE_NONE = 0;
    public static final int MODE_AUDIO_ACCESSORY = 4;
    public static final int MODE_DEBUG_ACCESSORY = 8;
    public static final int MODE_DFP = 2;
    public static final int MODE_DUAL = 3;
    public static final int MODE_NONE = 0;
    public static final int MODE_UFP = 1;
    public static final int POWER_ROLE_NONE = 0;
    public static final int POWER_ROLE_SINK = 2;
    public static final int POWER_ROLE_SOURCE = 1;
    private final int mContaminantDetectionStatus;
    private final int mContaminantProtectionStatus;
    private final int mCurrentDataRole;
    private final int mCurrentMode;
    private final int mCurrentPowerRole;
    private final int mSupportedRoleCombinations;

    public UsbPortStatus(int n, int n2, int n3, int n4, int n5, int n6) {
        this.mCurrentMode = n;
        this.mCurrentPowerRole = n2;
        this.mCurrentDataRole = n3;
        this.mSupportedRoleCombinations = n4;
        this.mContaminantProtectionStatus = n5;
        this.mContaminantDetectionStatus = n6;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getContaminantDetectionStatus() {
        return this.mContaminantDetectionStatus;
    }

    public int getContaminantProtectionStatus() {
        return this.mContaminantProtectionStatus;
    }

    public int getCurrentDataRole() {
        return this.mCurrentDataRole;
    }

    public int getCurrentMode() {
        return this.mCurrentMode;
    }

    public int getCurrentPowerRole() {
        return this.mCurrentPowerRole;
    }

    public int getSupportedRoleCombinations() {
        return this.mSupportedRoleCombinations;
    }

    public boolean isConnected() {
        boolean bl = this.mCurrentMode != 0;
        return bl;
    }

    public boolean isRoleCombinationSupported(int n, int n2) {
        boolean bl = (this.mSupportedRoleCombinations & UsbPort.combineRolesAsBit(n, n2)) != 0;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UsbPortStatus{connected=");
        stringBuilder.append(this.isConnected());
        stringBuilder.append(", currentMode=");
        stringBuilder.append(UsbPort.modeToString(this.mCurrentMode));
        stringBuilder.append(", currentPowerRole=");
        stringBuilder.append(UsbPort.powerRoleToString(this.mCurrentPowerRole));
        stringBuilder.append(", currentDataRole=");
        stringBuilder.append(UsbPort.dataRoleToString(this.mCurrentDataRole));
        stringBuilder.append(", supportedRoleCombinations=");
        stringBuilder.append(UsbPort.roleCombinationsToString(this.mSupportedRoleCombinations));
        stringBuilder.append(", contaminantDetectionStatus=");
        stringBuilder.append(this.getContaminantDetectionStatus());
        stringBuilder.append(", contaminantProtectionStatus=");
        stringBuilder.append(this.getContaminantProtectionStatus());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCurrentMode);
        parcel.writeInt(this.mCurrentPowerRole);
        parcel.writeInt(this.mCurrentDataRole);
        parcel.writeInt(this.mSupportedRoleCombinations);
        parcel.writeInt(this.mContaminantProtectionStatus);
        parcel.writeInt(this.mContaminantDetectionStatus);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface ContaminantDetectionStatus {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface ContaminantProtectionStatus {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface UsbDataRole {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface UsbPortMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface UsbPowerRole {
    }

}

