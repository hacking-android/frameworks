/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

public interface NameList {
    public boolean contains(String var1);

    public boolean containsNS(String var1, String var2);

    public int getLength();

    public String getName(int var1);

    public String getNamespaceURI(int var1);
}

