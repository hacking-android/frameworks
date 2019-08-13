/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax;

import org.xml.sax.AttributeList;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@Deprecated
public class HandlerBase
implements EntityResolver,
DTDHandler,
DocumentHandler,
ErrorHandler {
    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void endElement(String string) throws SAXException {
    }

    @Override
    public void error(SAXParseException sAXParseException) throws SAXException {
    }

    @Override
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        throw sAXParseException;
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void notationDecl(String string, String string2, String string3) {
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
    }

    @Override
    public InputSource resolveEntity(String string, String string2) throws SAXException {
        return null;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startElement(String string, AttributeList attributeList) throws SAXException {
    }

    @Override
    public void unparsedEntityDecl(String string, String string2, String string3, String string4) {
    }

    @Override
    public void warning(SAXParseException sAXParseException) throws SAXException {
    }
}

