/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.ext;

import org.xml.sax.SAXException;

public interface DeclHandler {
    public void attributeDecl(String var1, String var2, String var3, String var4, String var5) throws SAXException;

    public void elementDecl(String var1, String var2) throws SAXException;

    public void externalEntityDecl(String var1, String var2, String var3) throws SAXException;

    public void internalEntityDecl(String var1, String var2) throws SAXException;
}

