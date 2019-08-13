/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.PackageUserState;

public final class SELinuxUtil {
    public static final String COMPLETE_STR = ":complete";
    private static final String INSTANT_APP_STR = ":ephemeralapp";

    public static String assignSeinfoUser(PackageUserState packageUserState) {
        if (packageUserState.instantApp) {
            return ":ephemeralapp:complete";
        }
        return COMPLETE_STR;
    }
}

