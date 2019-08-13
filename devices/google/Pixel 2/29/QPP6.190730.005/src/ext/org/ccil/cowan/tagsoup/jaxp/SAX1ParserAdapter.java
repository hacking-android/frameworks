/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup.jaxp;

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

public class SAX1ParserAdapter
implements Parser {
    final XMLReader xmlReader;

    public SAX1ParserAdapter(XMLReader xMLReader) {
        this.xmlReader = xMLReader;
    }

    @Override
    public void parse(String string) throws SAXException {
        try {
            this.xmlReader.parse(string);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public void parse(InputSource inputSource) throws SAXException {
        try {
            this.xmlReader.parse(inputSource);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public void setDTDHandler(DTDHandler dTDHandler) {
        this.xmlReader.setDTDHandler(dTDHandler);
    }

    @Override
    public void setDocumentHandler(DocumentHandler documentHandler) {
        this.xmlReader.setContentHandler(new DocHandlerWrapper(documentHandler));
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
        throw new SAXNotSupportedException("TagSoup does not implement setLocale() method");
    }

    static final class AttributesWrapper
    implements AttributeList {
        Attributes attrs;

        @Override
        public int getLength() {
            return this.attrs.getLength();
        }

        @Override
        public String getName(int n) {
            String string;
            block0 : {
                string = this.attrs.getQName(n);
                if (string != null) break block0;
                string = this.attrs.getLocalName(n);
            }
            return string;
        }

        @Override
        public String getType(int n) {
            return this.attrs.getType(n);
        }

        @Override
        public String getType(String string) {
            return this.attrs.getType(string);
        }

        @Override
        public String getValue(int n) {
            return this.attrs.getValue(n);
        }

        @Override
        public String getValue(String string) {
            return this.attrs.getValue(string);
        }

        public void setAttributes(Attributes attributes) {
            this.attrs = attributes;
        }
    }

    static final class DocHandlerWrapper
    implements ContentHandler {
        final DocumentHandler docHandler;
        final AttributesWrapper mAttrWrapper = new AttributesWrapper();

        DocHandlerWrapper(DocumentHandler documentHandler) {
            this.docHandler = documentHandler;
        }

        @Override
        public void characters(char[] arrc, int n, int n2) throws SAXException {
            this.docHandler.characters(arrc, n, n2);
        }

        @Override
        public void endDocument() throws SAXException {
            this.docHandler.endDocument();
        }

        @Override
        public void endElement(String string, String string2, String string3) throws SAXException {
            string = string3;
            if (string3 == null) {
                string = string2;
            }
            this.docHandler.endElement(string);
        }

        @Override
        public void endPrefixMapping(String string) {
        }

        @Override
        public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
            this.docHandler.ignorableWhitespace(arrc, n, n2);
        }

        @Override
        public void processingInstruction(String string, String string2) throws SAXException {
            this.docHandler.processingInstruction(string, string2);
        }

        @Override
        public void setDocumentLocator(Locator locator) {
            this.docHandler.setDocumentLocator(locator);
        }

        @Override
        public void skippedEntity(String string) {
        }

        @Override
        public void startDocument() throws SAXException {
            this.docHandler.startDocument();
        }

        @Override
        public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
            string = string3;
            if (string3 == null) {
                string = string2;
            }
            this.mAttrWrapper.setAttributes(attributes);
            this.docHandler.startElement(string, this.mAttrWrapper);
        }

        @Override
        public void startPrefixMapping(String string, String string2) {
        }
    }

}

