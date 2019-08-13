/*
 * Decompiled with CFR 0.145.
 */
package java.security.acl;

import java.security.Principal;
import java.security.acl.Permission;
import java.util.Enumeration;

public interface AclEntry
extends Cloneable {
    public boolean addPermission(Permission var1);

    public boolean checkPermission(Permission var1);

    public Object clone();

    public Principal getPrincipal();

    public boolean isNegative();

    public Enumeration<Permission> permissions();

    public boolean removePermission(Permission var1);

    public void setNegativePermissions();

    public boolean setPrincipal(Principal var1);

    public String toString();
}

