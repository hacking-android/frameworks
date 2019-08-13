/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.Serializable;
import java.security.Permission;
import java.security.cert.Certificate;

public final class UnresolvedPermission
extends Permission
implements Serializable {
    public UnresolvedPermission(String string, String string2, String string3, Certificate[] arrcertificate) {
        super("");
    }

    @Override
    public String getActions() {
        return null;
    }

    public String getUnresolvedActions() {
        return null;
    }

    public Certificate[] getUnresolvedCerts() {
        return null;
    }

    public String getUnresolvedName() {
        return null;
    }

    public String getUnresolvedType() {
        return null;
    }

    @Override
    public boolean implies(Permission permission) {
        return false;
    }
}

