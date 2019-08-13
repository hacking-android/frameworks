/*
 * Decompiled with CFR 0.145.
 */
package java.security.acl;

import java.security.Principal;
import java.security.acl.AclEntry;
import java.security.acl.NotOwnerException;
import java.security.acl.Owner;
import java.security.acl.Permission;
import java.util.Enumeration;

public interface Acl
extends Owner {
    public boolean addEntry(Principal var1, AclEntry var2) throws NotOwnerException;

    public boolean checkPermission(Principal var1, Permission var2);

    public Enumeration<AclEntry> entries();

    public String getName();

    public Enumeration<Permission> getPermissions(Principal var1);

    public boolean removeEntry(Principal var1, AclEntry var2) throws NotOwnerException;

    public void setName(Principal var1, String var2) throws NotOwnerException;

    public String toString();
}

