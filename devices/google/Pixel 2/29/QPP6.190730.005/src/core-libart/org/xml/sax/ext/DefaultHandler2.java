/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.ext;

import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.EntityResolver2;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class DefaultHandler2
extends DefaultHandler
implements LexicalHandler,
DeclHandler,
EntityResolver2 {
    @Override
    public void attributeDecl(String string, String string2, String string3, String string4, String string5) throws SAXException {
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void elementDecl(String string, String string2) throws SAXException {
    }

    @Override
    public void endCDATA() throws SAXException {
    }

    @Override
    public void endDTD() throws SAXException {
    }

    @Override
    public void endEntity(String string) throws SAXException {
    }

    @Override
    public void externalEntityDecl(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public InputSource getExternalSubset(String string, String string2) throws SAXException, IOException {
        return null;
    }

    @Override
    public void internalEntityDecl(String string, String string2) throws SAXException {
    }

    @Override
    public InputSource resolveEntity(String string, String string2) throws SAXException, IOException {
        return this.resolveEntity(null, string, null, string2);
    }

    @Override
    public InputSource resolveEntity(String string, String string2, String string3, String string4) throws SAXException, IOException {
        return null;
    }

    @Override
    public void startCDATA() throws SAXException {
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public void startEntity(String string) throws SAXException {
    }
}

