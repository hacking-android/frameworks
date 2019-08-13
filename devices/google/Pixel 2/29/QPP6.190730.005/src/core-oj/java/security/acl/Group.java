/*
 * Decompiled with CFR 0.145.
 */
package java.security.acl;

import java.security.Principal;
import java.util.Enumeration;

public interface Group
extends Principal {
    public boolean addMember(Principal var1);

    public boolean isMember(Principal var1);

    public Enumeration<? extends Principal> members();

    public boolean removeMember(Principal var1);
}

