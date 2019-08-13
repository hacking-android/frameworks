/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.Serializable;
import java.security.Guard;
import java.security.PermissionCollection;
import java.security.Permissions;

public abstract class Permission
implements Guard,
Serializable {
    private String name;

    public Permission(String string) {
        this.name = string;
    }

    @Override
    public void checkGuard(Object object) throws SecurityException {
    }

    public abstract String getActions();

    public final String getName() {
        return this.name;
    }

    public abstract boolean implies(Permission var1);

    public PermissionCollection newPermissionCollection() {
        return new Permissions();
    }
}

