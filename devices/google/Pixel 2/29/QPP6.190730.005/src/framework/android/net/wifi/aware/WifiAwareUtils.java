/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class WifiAwareUtils {
    public static boolean isLegacyVersion(Context context, int n) {
        try {
            int n2 = context.getPackageManager().getApplicationInfo((String)context.getOpPackageName(), (int)0).targetSdkVersion;
            if (n2 < n) {
                return true;
            }
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            // empty catch block
        }
        return false;
    }

    public static boolean validatePassphrase(String string2) {
        return string2 != null && string2.length() >= 8 && string2.length() <= 63;
        {
        }
    }

    public static boolean validatePmk(byte[] arrby) {
        return arrby != null && arrby.length == 32;
        {
        }
    }

    public static void validateServiceName(byte[] arrby) throws IllegalArgumentException {
        if (arrby != null) {
            if (arrby.length >= 1 && arrby.length <= 255) {
                for (int i = 0; i < arrby.length; ++i) {
                    byte by = arrby[i];
                    if ((by & 128) != 0 || by >= 48 && by <= 57 || by >= 97 && by <= 122 || by >= 65 && by <= 90 || by == 45 || by == 46) continue;
                    throw new IllegalArgumentException("Invalid service name - illegal characters, allowed = (0-9, a-z,A-Z, -, .)");
                }
                return;
            }
            throw new IllegalArgumentException("Invalid service name length - must be between 1 and 255 bytes (UTF-8 encoding)");
        }
        throw new IllegalArgumentException("Invalid service name - null");
    }
}

