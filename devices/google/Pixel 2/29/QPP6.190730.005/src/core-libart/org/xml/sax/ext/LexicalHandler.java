/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.ext;

import org.xml.sax.SAXException;

public interface LexicalHandler {
    public void comment(char[] var1, int var2, int var3) throws SAXException;

    public void endCDATA() throws SAXException;

    public void endDTD() throws SAXException;

    public void endEntity(String var1) throws SAXException;

    public void startCDATA() throws SAXException;

    public void startDTD(String var1, String var2, String var3) throws SAXException;

    public void startEntity(String var1) throws SAXException;
}

