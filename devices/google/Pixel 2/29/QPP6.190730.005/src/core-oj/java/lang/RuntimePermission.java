/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.security.BasicPermission;

public final class RuntimePermission
extends BasicPermission {
    private static final long serialVersionUID = 7399184964622342223L;

    public RuntimePermission(String string) {
        super(string);
    }

    public RuntimePermission(String string, String string2) {
        super(string, string2);
    }
}

