/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

public interface TypeInfo {
    public static final int DERIVATION_EXTENSION = 2;
    public static final int DERIVATION_LIST = 8;
    public static final int DERIVATION_RESTRICTION = 1;
    public static final int DERIVATION_UNION = 4;

    public String getTypeName();

    public String getTypeNamespace();

    public boolean isDerivedFrom(String var1, String var2, int var3);
}

