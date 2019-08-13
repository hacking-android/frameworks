/*
 * Decompiled with CFR 0.145.
 */
package java.security.acl;

import java.security.Principal;
import java.security.acl.LastOwnerException;
import java.security.acl.NotOwnerException;

public interface Owner {
    public boolean addOwner(Principal var1, Principal var2) throws NotOwnerException;

    public boolean deleteOwner(Principal var1, Principal var2) throws NotOwnerException, LastOwnerException;

    public boolean isOwner(Principal var1);
}

