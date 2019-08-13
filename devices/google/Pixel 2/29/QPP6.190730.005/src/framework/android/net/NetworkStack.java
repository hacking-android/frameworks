/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.content.Context;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@SystemApi
public class NetworkStack {
    @SystemApi
    public static final String PERMISSION_MAINLINE_NETWORK_STACK = "android.permission.MAINLINE_NETWORK_STACK";

    private NetworkStack() {
    }

    private static boolean checkAnyPermissionOf(Context context, String ... arrstring) {
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (context.checkCallingOrSelfPermission(arrstring[i]) != 0) continue;
            return true;
        }
        return false;
    }

    public static void checkNetworkStackPermission(Context context) {
        NetworkStack.checkNetworkStackPermissionOr(context, new String[0]);
    }

    public static void checkNetworkStackPermissionOr(Context context, String ... object) {
        object = new ArrayList<String>(Arrays.asList(object));
        ((ArrayList)object).add("android.permission.NETWORK_STACK");
        ((ArrayList)object).add(PERMISSION_MAINLINE_NETWORK_STACK);
        NetworkStack.enforceAnyPermissionOf(context, ((ArrayList)object).toArray(new String[0]));
    }

    private static void enforceAnyPermissionOf(Context object, String ... arrstring) {
        if (NetworkStack.checkAnyPermissionOf((Context)object, arrstring)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Requires one of the following permissions: ");
        ((StringBuilder)object).append(String.join((CharSequence)", ", arrstring));
        ((StringBuilder)object).append(".");
        throw new SecurityException(((StringBuilder)object).toString());
    }
}

