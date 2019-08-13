/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlContext;

final class FactoryURLClassLoader
extends URLClassLoader {
    static {
        ClassLoader.registerAsParallelCapable();
    }

    FactoryURLClassLoader(URL[] arruRL, ClassLoader classLoader, AccessControlContext accessControlContext) {
        super(arruRL, classLoader, accessControlContext);
    }

    FactoryURLClassLoader(URL[] arruRL, AccessControlContext accessControlContext) {
        super(arruRL, accessControlContext);
    }

    @Override
    public final Class<?> loadClass(String string, boolean bl) throws ClassNotFoundException {
        int n;
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null && (n = string.lastIndexOf(46)) != -1) {
            securityManager.checkPackageAccess(string.substring(0, n));
        }
        return super.loadClass(string, bl);
    }
}

