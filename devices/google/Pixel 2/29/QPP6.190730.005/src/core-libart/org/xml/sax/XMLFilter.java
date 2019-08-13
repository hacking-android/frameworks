/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

import org.xml.sax.XMLReader;

public interface XMLFilter
extends XMLReader {
    public XMLReader getParent();

    public void setParent(XMLReader var1);
}

