/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

import org.xml.sax.SAXException;

public interface DTDHandler {
    public void notationDecl(String var1, String var2, String var3) throws SAXException;

    public void unparsedEntityDecl(String var1, String var2, String var3, String var4) throws SAXException;
}

