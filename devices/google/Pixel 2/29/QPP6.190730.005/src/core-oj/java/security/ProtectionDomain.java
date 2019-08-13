/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Principal;

public class ProtectionDomain {
    public ProtectionDomain(CodeSource codeSource, PermissionCollection permissionCollection) {
    }

    public ProtectionDomain(CodeSource codeSource, PermissionCollection permissionCollection, ClassLoader classLoader, Principal[] arrprincipal) {
    }

    public final ClassLoader getClassLoader() {
        return null;
    }

    public final CodeSource getCodeSource() {
        return null;
    }

    public final PermissionCollection getPermissions() {
        return null;
    }

    public final Principal[] getPrincipals() {
        return null;
    }

    public boolean implies(Permission permission) {
        return true;
    }
}

