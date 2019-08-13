/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset.spi;

import java.nio.charset.Charset;
import java.security.Permission;
import java.util.Iterator;

public abstract class CharsetProvider {
    protected CharsetProvider() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("charsetProvider"));
        }
    }

    public abstract Charset charsetForName(String var1);

    public abstract Iterator<Charset> charsets();
}

