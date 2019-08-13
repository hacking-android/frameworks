/*
 * Decompiled with CFR 0.145.
 */
package javax.security.auth;

import java.security.Permission;
import java.security.Principal;
import java.util.Set;

public final class PrivateCredentialPermission
extends Permission {
    public PrivateCredentialPermission(String string, String string2) {
        super("");
    }

    PrivateCredentialPermission(String string, Set<Principal> set) {
        super("");
    }

    @Override
    public String getActions() {
        return null;
    }

    public String getCredentialClass() {
        return null;
    }

    public String[][] getPrincipals() {
        return null;
    }

    @Override
    public boolean implies(Permission permission) {
        return true;
    }
}

