/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.net.NetPermission;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URI;
import java.security.Permission;
import java.util.List;
import sun.security.util.SecurityConstants;

public abstract class ProxySelector {
    private static ProxySelector theProxySelector;

    static {
        block4 : {
            Class<?> class_ = Class.forName("sun.net.spi.DefaultProxySelector");
            if (class_ == null) break block4;
            try {
                if (ProxySelector.class.isAssignableFrom(class_)) {
                    theProxySelector = (ProxySelector)class_.newInstance();
                }
            }
            catch (Exception exception) {
                theProxySelector = null;
            }
        }
    }

    public static ProxySelector getDefault() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SecurityConstants.GET_PROXYSELECTOR_PERMISSION);
        }
        return theProxySelector;
    }

    public static void setDefault(ProxySelector proxySelector) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SecurityConstants.SET_PROXYSELECTOR_PERMISSION);
        }
        theProxySelector = proxySelector;
    }

    public abstract void connectFailed(URI var1, SocketAddress var2, IOException var3);

    public abstract List<Proxy> select(URI var1);
}

