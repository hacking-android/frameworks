/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

import org.xml.sax.AttributeList;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

@Deprecated
public interface DocumentHandler {
    public void characters(char[] var1, int var2, int var3) throws SAXException;

    public void endDocument() throws SAXException;

    public void endElement(String var1) throws SAXException;

    public void ignorableWhitespace(char[] var1, int var2, int var3) throws SAXException;

    public void processingInstruction(String var1, String var2) throws SAXException;

    public void setDocumentLocator(Locator var1);

    public void startDocument() throws SAXException;

    public void startElement(String var1, AttributeList var2) throws SAXException;
}

