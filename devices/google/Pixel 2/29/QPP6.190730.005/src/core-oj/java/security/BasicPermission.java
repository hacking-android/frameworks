/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.Serializable;
import java.security.Permission;

public abstract class BasicPermission
extends Permission
implements Serializable {
    public BasicPermission(String string) {
        super("");
    }

    public BasicPermission(String string, String string2) {
        super("");
    }

    @Override
    public String getActions() {
        return "";
    }

    @Override
    public boolean implies(Permission permission) {
        return true;
    }
}

