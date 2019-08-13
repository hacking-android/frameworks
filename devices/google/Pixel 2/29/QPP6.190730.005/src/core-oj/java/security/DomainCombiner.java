/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.ProtectionDomain;

public interface DomainCombiner {
    public ProtectionDomain[] combine(ProtectionDomain[] var1, ProtectionDomain[] var2);
}

