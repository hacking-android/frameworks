/*
 * Decompiled with CFR 0.145.
 */
package java.lang.reflect;

import java.security.BasicPermission;

public final class ReflectPermission
extends BasicPermission {
    public ReflectPermission(String string) {
        super(string);
    }

    public ReflectPermission(String string, String string2) {
        super("", "");
    }
}

