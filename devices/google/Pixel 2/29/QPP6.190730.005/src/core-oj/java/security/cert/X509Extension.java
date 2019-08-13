/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.util.Set;

public interface X509Extension {
    public Set<String> getCriticalExtensionOIDs();

    public byte[] getExtensionValue(String var1);

    public Set<String> getNonCriticalExtensionOIDs();

    public boolean hasUnsupportedCriticalExtension();
}

