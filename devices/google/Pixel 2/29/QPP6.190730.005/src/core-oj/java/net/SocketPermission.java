/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.Serializable;
import java.security.Permission;

public final class SocketPermission
extends Permission
implements Serializable {
    public SocketPermission(String string, String string2) {
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

