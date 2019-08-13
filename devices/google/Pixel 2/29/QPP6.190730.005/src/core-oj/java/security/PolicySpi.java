/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.ProtectionDomain;

public abstract class PolicySpi {
    protected PermissionCollection engineGetPermissions(CodeSource codeSource) {
        return Policy.UNSUPPORTED_EMPTY_COLLECTION;
    }

    protected PermissionCollection engineGetPermissions(ProtectionDomain protectionDomain) {
        return Policy.UNSUPPORTED_EMPTY_COLLECTION;
    }

    protected abstract boolean engineImplies(ProtectionDomain var1, Permission var2);

    protected void engineRefresh() {
    }
}

