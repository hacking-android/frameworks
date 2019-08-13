/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

public interface Attributes {
    public int getIndex(String var1);

    public int getIndex(String var1, String var2);

    public int getLength();

    public String getLocalName(int var1);

    public String getQName(int var1);

    public String getType(int var1);

    public String getType(String var1);

    public String getType(String var1, String var2);

    public String getURI(int var1);

    public String getValue(int var1);

    public String getValue(String var1);

    public String getValue(String var1, String var2);
}

