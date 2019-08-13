/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.AccessControlException;
import java.security.DomainCombiner;
import java.security.Permission;
import java.security.ProtectionDomain;

public final class AccessControlContext {
    public AccessControlContext(AccessControlContext accessControlContext, DomainCombiner domainCombiner) {
    }

    public AccessControlContext(ProtectionDomain[] arrprotectionDomain) {
    }

    public void checkPermission(Permission permission) throws AccessControlException {
    }

    public DomainCombiner getDomainCombiner() {
        return null;
    }
}

