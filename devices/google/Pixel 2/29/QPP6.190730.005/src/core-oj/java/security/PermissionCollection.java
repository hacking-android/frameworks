/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.Serializable;
import java.security.Permission;
import java.util.Enumeration;

public abstract class PermissionCollection
implements Serializable {
    public abstract void add(Permission var1);

    public abstract Enumeration<Permission> elements();

    public abstract boolean implies(Permission var1);

    public boolean isReadOnly() {
        return true;
    }

    public void setReadOnly() {
    }
}

