/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

public interface Locator {
    public int getColumnNumber();

    public int getLineNumber();

    public String getPublicId();

    public String getSystemId();
}

