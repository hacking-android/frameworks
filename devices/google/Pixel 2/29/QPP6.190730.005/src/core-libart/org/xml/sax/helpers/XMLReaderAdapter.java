/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.helpers;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.util.Locale;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XMLReaderAdapter
implements Parser,
ContentHandler {
    @UnsupportedAppUsage
    DocumentHandler documentHandler;
    @UnsupportedAppUsage
    AttributesAdapter qAtts;
    @UnsupportedAppUsage
    XMLReader xmlReader;

    public XMLReaderAdapter() throws SAXException {
        this.setup(XMLReaderFactory.createXMLReader());
    }

    public XMLReaderAdapter(XMLReader xMLReader) {
        this.setup(xMLReader);
    }

    @UnsupportedAppUsage
    private void setup(XMLReader xMLReader) {
        if (xMLReader != null) {
            this.xmlReader = xMLReader;
            this.qAtts = new AttributesAdapter();
            return;
        }
        throw new NullPointerException("XMLReader must not be null");
    }

    @UnsupportedAppUsage
    private void setupXMLReader() throws SAXException {
        this.xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
        try {
            this.xmlReader.setFeature("http://xml.org/sax/features/namespaces", false);
        }
        catch (SAXException sAXException) {
            // empty catch block
        }
        this.xmlReader.setContentHandler(this);
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        DocumentHandler documentHandler = this.documentHandler;
        if (documentHandler != null) {
            documentHandler.characters(arrc, n, n2);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        DocumentHandler documentHandler = this.documentHandler;
        if (documentHandler != null) {
            documentHandler.endDocument();
        }
    }

    @Override
    public void endElement(String object, String string, String string2) throws SAXException {
        object = this.documentHandler;
        if (object != null) {
            object.endElement(string2);
        }
    }

    @Override
    public void endPrefixMapping(String string) {
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        DocumentHandler documentHandler = this.documentHandler;
        if (documentHandler != null) {
            documentHandler.ignorableWhitespace(arrc, n, n2);
        }
    }

    @Override
    public void parse(String string) throws IOException, SAXException {
        this.parse(new InputSource(string));
    }

    @Override
    public void parse(InputSource inputSource) throws IOException, SAXException {
        this.setupXMLReader();
        this.xmlReader.parse(inputSource);
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        DocumentHandler documentHandler = this.documentHandler;
        if (documentHandler != null) {
            documentHandler.processingInstruction(string, string2);
        }
    }

    @Override
    public void setDTDHandler(DTDHandler dTDHandler) {
        this.xmlReader.setDTDHandler(dTDHandler);
    }

    @Override
    public void setDocumentHandler(DocumentHandler documentHandler) {
        this.documentHandler = documentHandler;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        DocumentHandler documentHandler = this.documentHandler;
        if (documentHandler != null) {
            documentHandler.setDocumentLocator(locator);
        }
    }

    @Override
    public void setEntityResolver(EntityResolver entityResolver) {
        this.xmlReader.setEntityResolver(entityResolver);
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.xmlReader.setErrorHandler(errorHandler);
    }

    @Override
    public void setLocale(Locale locale) throws SAXException {
        throw new SAXNotSupportedException("setLocale not supported");
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
    }

    @Override
    public void startDocument() throws SAXException {
        DocumentHandler documentHandler = this.documentHandler;
        if (documentHandler != null) {
            documentHandler.startDocument();
        }
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        if (this.documentHandler != null) {
            this.qAtts.setAttributes(attributes);
            this.documentHandler.startElement(string3, this.qAtts);
        }
    }

    @Override
    public void startPrefixMapping(String string, String string2) {
    }

    static final class AttributesAdapter
    implements AttributeList {
        private Attributes attributes;

        AttributesAdapter() {
        }

        @Override
        public int getLength() {
            return this.attributes.getLength();
        }

        @Override
        public String getName(int n) {
            return this.attributes.getQName(n);
        }

        @Override
        public String getType(int n) {
            return this.attributes.getType(n);
        }

        @Override
        public String getType(String string) {
            return this.attributes.getType(string);
        }

        @Override
        public String getValue(int n) {
            return this.attributes.getValue(n);
        }

        @Override
        public String getValue(String string) {
            return this.attributes.getValue(string);
        }

        void setAttributes(Attributes attributes) {
            this.attributes = attributes;
        }
    }

}

