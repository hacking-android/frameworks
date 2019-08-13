/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.helpers;

import java.io.IOException;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DefaultHandler
implements EntityResolver,
DTDHandler,
ContentHandler,
ErrorHandler {
    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
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
    public void notationDecl(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
    }

    @Override
    public InputSource resolveEntity(String string, String string2) throws IOException, SAXException {
        return null;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
    }

    @Override
    public void unparsedEntityDecl(String string, String string2, String string3, String string4) throws SAXException {
    }

    @Override
    public void warning(SAXParseException sAXParseException) throws SAXException {
    }
}

