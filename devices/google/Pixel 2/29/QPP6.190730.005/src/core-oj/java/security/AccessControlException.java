/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.Permission;

public class AccessControlException
extends SecurityException {
    private static final long serialVersionUID = 5138225684096988535L;
    private Permission perm;

    public AccessControlException(String string) {
        super(string);
    }

    public AccessControlException(String string, Permission permission) {
        super(string);
        this.perm = permission;
    }

    public Permission getPermission() {
        return this.perm;
    }
}

