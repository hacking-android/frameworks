/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.Serializable;
import java.security.Permission;

public final class FilePermission
extends Permission
implements Serializable {
    public FilePermission(String string, String string2) {
        super(string);
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

