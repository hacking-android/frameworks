/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb;

import android.annotation.SystemApi;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPortStatus;
import com.android.internal.util.Preconditions;

@SystemApi
public final class UsbPort {
    private static final int NUM_DATA_ROLES = 3;
    private static final int POWER_ROLE_OFFSET = 0;
    private final String mId;
    private final int mSupportedContaminantProtectionModes;
    private final int mSupportedModes;
    private final boolean mSupportsEnableContaminantPresenceDetection;
    private final boolean mSupportsEnableContaminantPresenceProtection;
    private final UsbManager mUsbManager;

    public UsbPort(UsbManager usbManager, String string2, int n, int n2, boolean bl, boolean bl2) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkFlagsArgument(n, 15);
        this.mUsbManager = usbManager;
        this.mId = string2;
        this.mSupportedModes = n;
        this.mSupportedContaminantProtectionModes = n2;
        this.mSupportsEnableContaminantPresenceProtection = bl;
        this.mSupportsEnableContaminantPresenceDetection = bl2;
    }

    public static void checkDataRole(int n) {
        Preconditions.checkArgumentInRange(n, 0, 2, "powerRole");
    }

    public static void checkMode(int n) {
        Preconditions.checkArgumentInRange(n, 0, 3, "portMode");
    }

    public static void checkPowerRole(int n) {
        Preconditions.checkArgumentInRange(n, 0, 2, "powerRole");
    }

    public static void checkRoles(int n, int n2) {
        Preconditions.checkArgumentInRange(n, 0, 2, "powerRole");
        Preconditions.checkArgumentInRange(n2, 0, 2, "dataRole");
    }

    public static int combineRolesAsBit(int n, int n2) {
        UsbPort.checkRoles(n, n2);
        return 1 << (n + 0) * 3 + n2;
    }

    public static String contaminantPresenceStatusToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return Integer.toString(n);
                    }
                    return "detected";
                }
                return "not detected";
            }
            return "disabled";
        }
        return "not-supported";
    }

    public static String dataRoleToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return Integer.toString(n);
                }
                return "device";
            }
            return "host";
        }
        return "no-data";
    }

    public static String modeToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        if (n == 0) {
            return "none";
        }
        if ((n & 3) == 3) {
            stringBuilder.append("dual, ");
        } else if ((n & 2) == 2) {
            stringBuilder.append("dfp, ");
        } else if ((n & 1) == 1) {
            stringBuilder.append("ufp, ");
        }
        if ((n & 4) == 4) {
            stringBuilder.append("audio_acc, ");
        }
        if ((n & 8) == 8) {
            stringBuilder.append("debug_acc, ");
        }
        if (stringBuilder.length() == 0) {
            return Integer.toString(n);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }

    public static String powerRoleToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return Integer.toString(n);
                }
                return "sink";
            }
            return "source";
        }
        return "no-power";
    }

    public static String roleCombinationsToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        int n2 = 1;
        int n3 = n;
        n = n2;
        while (n3 != 0) {
            n2 = Integer.numberOfTrailingZeros(n3);
            n3 &= 1 << n2;
            int n4 = n2 / 3;
            if (n != 0) {
                n = 0;
            } else {
                stringBuilder.append(", ");
            }
            stringBuilder.append(UsbPort.powerRoleToString(n4 + 0));
            stringBuilder.append(':');
            stringBuilder.append(UsbPort.dataRoleToString(n2 % 3));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void enableContaminantDetection(boolean bl) {
        this.mUsbManager.enableContaminantDetection(this, bl);
    }

    public String getId() {
        return this.mId;
    }

    public UsbPortStatus getStatus() {
        return this.mUsbManager.getPortStatus(this);
    }

    public int getSupportedContaminantProtectionModes() {
        return this.mSupportedContaminantProtectionModes;
    }

    public int getSupportedModes() {
        return this.mSupportedModes;
    }

    public boolean isModeSupported(int n) {
        return (this.mSupportedModes & n) == n;
    }

    public void setRoles(int n, int n2) {
        UsbPort.checkRoles(n, n2);
        this.mUsbManager.setPortRoles(this, n, n2);
    }

    public boolean supportsEnableContaminantPresenceDetection() {
        return this.mSupportsEnableContaminantPresenceDetection;
    }

    public boolean supportsEnableContaminantPresenceProtection() {
        return this.mSupportsEnableContaminantPresenceProtection;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UsbPort{id=");
        stringBuilder.append(this.mId);
        stringBuilder.append(", supportedModes=");
        stringBuilder.append(UsbPort.modeToString(this.mSupportedModes));
        stringBuilder.append("supportedContaminantProtectionModes=");
        stringBuilder.append(this.mSupportedContaminantProtectionModes);
        stringBuilder.append("supportsEnableContaminantPresenceProtection=");
        stringBuilder.append(this.mSupportsEnableContaminantPresenceProtection);
        stringBuilder.append("supportsEnableContaminantPresenceDetection=");
        stringBuilder.append(this.mSupportsEnableContaminantPresenceDetection);
        return stringBuilder.toString();
    }
}

