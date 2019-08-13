/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.Serializable;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;

public final class Permissions
extends PermissionCollection
implements Serializable {
    @Override
    public void add(Permission permission) {
    }

    @Override
    public Enumeration<Permission> elements() {
        return null;
    }

    @Override
    public boolean implies(Permission permission) {
        return true;
    }
}

