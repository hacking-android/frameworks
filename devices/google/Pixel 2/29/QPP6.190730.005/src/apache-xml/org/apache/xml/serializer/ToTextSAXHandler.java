/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.ToSAXHandler;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public final class ToTextSAXHandler
extends ToSAXHandler {
    public ToTextSAXHandler(ContentHandler contentHandler, String string) {
        super(contentHandler, string);
    }

    public ToTextSAXHandler(ContentHandler contentHandler, LexicalHandler lexicalHandler, String string) {
        super(contentHandler, lexicalHandler, string);
    }

    @Override
    public void addAttribute(String string, String string2) {
    }

    @Override
    public void addAttribute(String string, String string2, String string3, String string4, String string5, boolean bl) {
    }

    @Override
    public void attributeDecl(String string, String string2, String string3, String string4, String string5) throws SAXException {
    }

    @Override
    public void characters(String string) throws SAXException {
        int n = string.length();
        if (n > this.m_charsBuff.length) {
            this.m_charsBuff = new char[n * 2 + 1];
        }
        string.getChars(0, n, this.m_charsBuff, 0);
        this.m_saxHandler.characters(this.m_charsBuff, 0, n);
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        this.m_saxHandler.characters(arrc, n, n2);
        if (this.m_tracer != null) {
            super.fireCharEvent(arrc, n, n2);
        }
    }

    @Override
    public void comment(String string) throws SAXException {
        int n = string.length();
        if (n > this.m_charsBuff.length) {
            this.m_charsBuff = new char[n * 2 + 1];
        }
        string.getChars(0, n, this.m_charsBuff, 0);
        this.comment(this.m_charsBuff, 0, n);
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_tracer != null) {
            super.fireCommentEvent(arrc, n, n2);
        }
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
    public void endDocument() throws SAXException {
        this.flushPending();
        this.m_saxHandler.endDocument();
        if (this.m_tracer != null) {
            super.fireEndDoc();
        }
    }

    @Override
    public void endElement(String string) throws SAXException {
        if (this.m_tracer != null) {
            super.fireEndElem(string);
        }
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        if (this.m_tracer != null) {
            super.fireEndElem(string3);
        }
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
    }

    @Override
    public void externalEntityDecl(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public Properties getOutputFormat() {
        return null;
    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public Writer getWriter() {
        return null;
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
    }

    public void indent(int n) throws SAXException {
    }

    @Override
    public void internalEntityDecl(String string, String string2) throws SAXException {
    }

    @Override
    public void namespaceAfterStartElement(String string, String string2) throws SAXException {
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        if (this.m_tracer != null) {
            super.fireEscapingEvent(string, string2);
        }
    }

    @Override
    public boolean reset() {
        return false;
    }

    @Override
    public void serialize(Node node) throws IOException {
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public boolean setEscaping(boolean bl) {
        return false;
    }

    @Override
    public void setIndent(boolean bl) {
    }

    @Override
    public void setOutputFormat(Properties properties) {
    }

    @Override
    public void setOutputStream(OutputStream outputStream) {
    }

    @Override
    public void setWriter(Writer writer) {
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
    }

    @Override
    public void startCDATA() throws SAXException {
    }

    @Override
    public void startElement(String string) throws SAXException {
        super.startElement(string);
    }

    @Override
    public void startElement(String string, String string2, String string3) throws SAXException {
        super.startElement(string, string2, string3);
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        this.flushPending();
        super.startElement(string, string2, string3, attributes);
    }

    @Override
    public void startEntity(String string) throws SAXException {
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
    }

    @Override
    public boolean startPrefixMapping(String string, String string2, boolean bl) throws SAXException {
        return false;
    }
}

