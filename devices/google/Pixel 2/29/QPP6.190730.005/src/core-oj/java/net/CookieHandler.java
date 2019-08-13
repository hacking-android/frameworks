/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.net.NetPermission;
import java.net.URI;
import java.security.Permission;
import java.util.List;
import java.util.Map;
import sun.security.util.SecurityConstants;

public abstract class CookieHandler {
    private static CookieHandler cookieHandler;

    public static CookieHandler getDefault() {
        synchronized (CookieHandler.class) {
            Object object = System.getSecurityManager();
            if (object != null) {
                ((SecurityManager)object).checkPermission(SecurityConstants.GET_COOKIEHANDLER_PERMISSION);
            }
            object = cookieHandler;
            return object;
        }
    }

    public static void setDefault(CookieHandler cookieHandler) {
        synchronized (CookieHandler.class) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(SecurityConstants.SET_COOKIEHANDLER_PERMISSION);
            }
            CookieHandler.cookieHandler = cookieHandler;
            return;
        }
    }

    public abstract Map<String, List<String>> get(URI var1, Map<String, List<String>> var2) throws IOException;

    public abstract void put(URI var1, Map<String, List<String>> var2) throws IOException;
}

