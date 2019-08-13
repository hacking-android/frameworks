/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.Permission;

public final class AllPermission
extends Permission {
    public AllPermission() {
        super("");
    }

    public AllPermission(String string, String string2) {
        super("");
    }

    @Override
    public String getActions() {
        return null;
    }

    @Override
    public boolean implies(Permission permission) {
        return true;
    }
}

