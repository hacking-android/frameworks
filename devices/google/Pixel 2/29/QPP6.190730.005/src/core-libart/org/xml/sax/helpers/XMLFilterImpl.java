/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.helpers;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

public class XMLFilterImpl
implements XMLFilter,
EntityResolver,
DTDHandler,
ContentHandler,
ErrorHandler {
    @UnsupportedAppUsage
    private ContentHandler contentHandler = null;
    @UnsupportedAppUsage
    private DTDHandler dtdHandler = null;
    @UnsupportedAppUsage
    private EntityResolver entityResolver = null;
    @UnsupportedAppUsage
    private ErrorHandler errorHandler = null;
    @UnsupportedAppUsage
    private Locator locator = null;
    @UnsupportedAppUsage
    private XMLReader parent = null;

    public XMLFilterImpl() {
    }

    public XMLFilterImpl(XMLReader xMLReader) {
        this.setParent(xMLReader);
    }

    @UnsupportedAppUsage
    private void setupParse() {
        XMLReader xMLReader = this.parent;
        if (xMLReader != null) {
            xMLReader.setEntityResolver(this);
            this.parent.setDTDHandler(this);
            this.parent.setContentHandler(this);
            this.parent.setErrorHandler(this);
            return;
        }
        throw new NullPointerException("No parent for filter");
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.characters(arrc, n, n2);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.endDocument();
        }
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.endElement(string, string2, string3);
        }
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.endPrefixMapping(string);
        }
    }

    @Override
    public void error(SAXParseException sAXParseException) throws SAXException {
        ErrorHandler errorHandler = this.errorHandler;
        if (errorHandler != null) {
            errorHandler.error(sAXParseException);
        }
    }

    @Override
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        ErrorHandler errorHandler = this.errorHandler;
        if (errorHandler != null) {
            errorHandler.fatalError(sAXParseException);
        }
    }

    @Override
    public ContentHandler getContentHandler() {
        return this.contentHandler;
    }

    @Override
    public DTDHandler getDTDHandler() {
        return this.dtdHandler;
    }

    @Override
    public EntityResolver getEntityResolver() {
        return this.entityResolver;
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    @Override
    public boolean getFeature(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        Object object = this.parent;
        if (object != null) {
            return object.getFeature(string);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Feature: ");
        ((StringBuilder)object).append(string);
        throw new SAXNotRecognizedException(((StringBuilder)object).toString());
    }

    @Override
    public XMLReader getParent() {
        return this.parent;
    }

    @Override
    public Object getProperty(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        Object object = this.parent;
        if (object != null) {
            return object.getProperty(string);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Property: ");
        ((StringBuilder)object).append(string);
        throw new SAXNotRecognizedException(((StringBuilder)object).toString());
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.ignorableWhitespace(arrc, n, n2);
        }
    }

    @Override
    public void notationDecl(String string, String string2, String string3) throws SAXException {
        DTDHandler dTDHandler = this.dtdHandler;
        if (dTDHandler != null) {
            dTDHandler.notationDecl(string, string2, string3);
        }
    }

    @Override
    public void parse(String string) throws SAXException, IOException {
        this.parse(new InputSource(string));
    }

    @Override
    public void parse(InputSource inputSource) throws SAXException, IOException {
        this.setupParse();
        this.parent.parse(inputSource);
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.processingInstruction(string, string2);
        }
    }

    @Override
    public InputSource resolveEntity(String string, String string2) throws SAXException, IOException {
        EntityResolver entityResolver = this.entityResolver;
        if (entityResolver != null) {
            return entityResolver.resolveEntity(string, string2);
        }
        return null;
    }

    @Override
    public void setContentHandler(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    @Override
    public void setDTDHandler(DTDHandler dTDHandler) {
        this.dtdHandler = dTDHandler;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.setDocumentLocator(locator);
        }
    }

    @Override
    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public void setFeature(String string, boolean bl) throws SAXNotRecognizedException, SAXNotSupportedException {
        Object object = this.parent;
        if (object != null) {
            object.setFeature(string, bl);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Feature: ");
        ((StringBuilder)object).append(string);
        throw new SAXNotRecognizedException(((StringBuilder)object).toString());
    }

    @Override
    public void setParent(XMLReader xMLReader) {
        this.parent = xMLReader;
    }

    @Override
    public void setProperty(String string, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
        XMLReader xMLReader = this.parent;
        if (xMLReader != null) {
            xMLReader.setProperty(string, object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Property: ");
        ((StringBuilder)object).append(string);
        throw new SAXNotRecognizedException(((StringBuilder)object).toString());
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.skippedEntity(string);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.startDocument();
        }
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.startElement(string, string2, string3, attributes);
        }
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
        ContentHandler contentHandler = this.contentHandler;
        if (contentHandler != null) {
            contentHandler.startPrefixMapping(string, string2);
        }
    }

    @Override
    public void unparsedEntityDecl(String string, String string2, String string3, String string4) throws SAXException {
        DTDHandler dTDHandler = this.dtdHandler;
        if (dTDHandler != null) {
            dTDHandler.unparsedEntityDecl(string, string2, string3, string4);
        }
    }

    @Override
    public void warning(SAXParseException sAXParseException) throws SAXException {
        ErrorHandler errorHandler = this.errorHandler;
        if (errorHandler != null) {
            errorHandler.warning(sAXParseException);
        }
    }
}

