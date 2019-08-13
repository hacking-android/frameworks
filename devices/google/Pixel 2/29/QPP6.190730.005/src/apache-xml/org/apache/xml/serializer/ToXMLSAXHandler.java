/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;
import org.apache.xml.serializer.AttributesImplSerializer;
import org.apache.xml.serializer.ElemContext;
import org.apache.xml.serializer.NamespaceMappings;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.ToSAXHandler;
import org.apache.xml.serializer.TransformStateSetter;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public final class ToXMLSAXHandler
extends ToSAXHandler {
    protected boolean m_escapeSetting = true;

    public ToXMLSAXHandler() {
        this.m_prefixMap = new NamespaceMappings();
        this.initCDATA();
    }

    public ToXMLSAXHandler(ContentHandler contentHandler, String string) {
        super(contentHandler, string);
        this.initCDATA();
        this.m_prefixMap = new NamespaceMappings();
    }

    public ToXMLSAXHandler(ContentHandler contentHandler, LexicalHandler lexicalHandler, String string) {
        super(contentHandler, lexicalHandler, string);
        this.initCDATA();
        this.m_prefixMap = new NamespaceMappings();
    }

    private void ensurePrefixIsDeclared(String string, String string2) throws SAXException {
        if (string != null && string.length() > 0) {
            String string3;
            int n = string2.indexOf(":");
            boolean bl = n < 0;
            string2 = bl ? "" : string2.substring(0, n);
            if (!(string2 == null || (string3 = this.m_prefixMap.lookupNamespace(string2)) != null && string3.equals(string))) {
                this.startPrefixMapping(string2, string, false);
                if (this.getShouldOutputNSAttr()) {
                    CharSequence charSequence = "xmlns";
                    string3 = bl ? "xmlns" : string2;
                    if (bl) {
                        string2 = charSequence;
                    } else {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("xmlns:");
                        ((StringBuilder)charSequence).append(string2);
                        string2 = ((StringBuilder)charSequence).toString();
                    }
                    this.addAttributeAlways("http://www.w3.org/2000/xmlns/", string3, string2, "CDATA", string, false);
                }
            }
        }
    }

    private void resetToXMLSAXHandler() {
        this.m_escapeSetting = true;
    }

    @Override
    public void addAttribute(String string, String string2, String string3, String string4, String string5, boolean bl) throws SAXException {
        if (this.m_elemContext.m_startTagOpen) {
            this.ensurePrefixIsDeclared(string, string3);
            this.addAttributeAlways(string, string2, string3, string4, string5, false);
        }
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
        this.characters(this.m_charsBuff, 0, n);
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_needToCallStartDocument) {
            this.startDocumentInternal();
            this.m_needToCallStartDocument = false;
        }
        if (this.m_elemContext.m_startTagOpen) {
            this.closeStartTag();
            this.m_elemContext.m_startTagOpen = false;
        }
        if (this.m_elemContext.m_isCdataSection && !this.m_cdataTagOpen && this.m_lexHandler != null) {
            this.m_lexHandler.startCDATA();
            this.m_cdataTagOpen = true;
        }
        this.m_saxHandler.characters(arrc, n, n2);
        if (this.m_tracer != null) {
            this.fireCharEvent(arrc, n, n2);
        }
    }

    @Override
    public void closeCDATA() throws SAXException {
        if (this.m_lexHandler != null && this.m_cdataTagOpen) {
            this.m_lexHandler.endCDATA();
        }
        this.m_cdataTagOpen = false;
    }

    @Override
    protected void closeStartTag() throws SAXException {
        this.m_elemContext.m_startTagOpen = false;
        String string = ToXMLSAXHandler.getLocalName(this.m_elemContext.m_elementName);
        String string2 = this.getNamespaceURI(this.m_elemContext.m_elementName, true);
        if (this.m_needToCallStartDocument) {
            this.startDocumentInternal();
        }
        this.m_saxHandler.startElement(string2, string, this.m_elemContext.m_elementName, this.m_attributes);
        this.m_attributes.clear();
        if (this.m_state != null) {
            this.m_state.setCurrentNode(null);
        }
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        this.flushPending();
        if (this.m_lexHandler != null) {
            this.m_lexHandler.comment(arrc, n, n2);
        }
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
        if (this.m_lexHandler != null) {
            this.m_lexHandler.endDTD();
        }
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
        this.endElement(null, null, string);
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        this.flushPending();
        String string4 = string;
        if (string == null) {
            string4 = this.m_elemContext.m_elementURI != null ? this.m_elemContext.m_elementURI : this.getNamespaceURI(string3, true);
        }
        string = string2;
        if (string2 == null) {
            string = this.m_elemContext.m_elementLocalName != null ? this.m_elemContext.m_elementLocalName : ToXMLSAXHandler.getLocalName(string3);
        }
        this.m_saxHandler.endElement(string4, string, string3);
        if (this.m_tracer != null) {
            super.fireEndElem(string3);
        }
        this.m_prefixMap.popNamespaces(this.m_elemContext.m_currentElemDepth, this.m_saxHandler);
        this.m_elemContext = this.m_elemContext.m_prev;
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
        this.m_saxHandler.ignorableWhitespace(arrc, n, n2);
    }

    public void indent(int n) throws SAXException {
    }

    @Override
    public void internalEntityDecl(String string, String string2) throws SAXException {
    }

    @Override
    public void namespaceAfterStartElement(String string, String string2) throws SAXException {
        this.startPrefixMapping(string, string2, false);
    }

    protected boolean popNamespace(String string) {
        try {
            if (this.m_prefixMap.popNamespace(string)) {
                this.m_saxHandler.endPrefixMapping(string);
                return true;
            }
        }
        catch (SAXException sAXException) {
            // empty catch block
        }
        return false;
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        this.flushPending();
        this.m_saxHandler.processingInstruction(string, string2);
        if (this.m_tracer != null) {
            super.fireEscapingEvent(string, string2);
        }
    }

    @Override
    public boolean reset() {
        boolean bl = false;
        if (super.reset()) {
            this.resetToXMLSAXHandler();
            bl = true;
        }
        return bl;
    }

    @Override
    public void serialize(Node node) throws IOException {
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        this.m_saxHandler.setDocumentLocator(locator);
    }

    @Override
    public boolean setEscaping(boolean bl) throws SAXException {
        boolean bl2 = this.m_escapeSetting;
        this.m_escapeSetting = bl;
        if (bl) {
            this.processingInstruction("javax.xml.transform.enable-output-escaping", "");
        } else {
            this.processingInstruction("javax.xml.transform.disable-output-escaping", "");
        }
        return bl2;
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
        this.m_saxHandler.skippedEntity(string);
    }

    @Override
    public void startCDATA() throws SAXException {
        if (!this.m_cdataTagOpen) {
            this.flushPending();
            if (this.m_lexHandler != null) {
                this.m_lexHandler.startCDATA();
                this.m_cdataTagOpen = true;
            }
        }
    }

    @Override
    public void startElement(String string) throws SAXException {
        this.startElement(null, null, string, null);
    }

    @Override
    public void startElement(String string, String string2, String string3) throws SAXException {
        this.startElement(string, string2, string3, null);
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        this.flushPending();
        super.startElement(string, string2, string3, attributes);
        if (this.m_needToOutputDocTypeDecl) {
            String string4 = this.getDoctypeSystem();
            if (string4 != null && this.m_lexHandler != null) {
                String string5 = this.getDoctypePublic();
                this.m_lexHandler.startDTD(string3, string5, string4);
            }
            this.m_needToOutputDocTypeDecl = false;
        }
        this.m_elemContext = this.m_elemContext.push(string, string2, string3);
        if (string != null) {
            this.ensurePrefixIsDeclared(string, string3);
        }
        if (attributes != null) {
            this.addAttributes(attributes);
        }
        this.m_elemContext.m_isCdataSection = this.isCdataSection();
    }

    @Override
    public void startEntity(String string) throws SAXException {
        if (this.m_lexHandler != null) {
            this.m_lexHandler.startEntity(string);
        }
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
        this.startPrefixMapping(string, string2, true);
    }

    @Override
    public boolean startPrefixMapping(String string, String string2, boolean bl) throws SAXException {
        int n;
        if (bl) {
            this.flushPending();
            n = this.m_elemContext.m_currentElemDepth + 1;
        } else {
            n = this.m_elemContext.m_currentElemDepth;
        }
        bl = this.m_prefixMap.pushNamespace(string, string2, n);
        if (bl) {
            this.m_saxHandler.startPrefixMapping(string, string2);
            if (this.getShouldOutputNSAttr()) {
                if ("".equals(string)) {
                    this.addAttributeAlways("http://www.w3.org/2000/xmlns/", "xmlns", "xmlns", "CDATA", string2, false);
                } else if (!"".equals(string2)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("xmlns:");
                    stringBuilder.append(string);
                    this.addAttributeAlways("http://www.w3.org/2000/xmlns/", string, stringBuilder.toString(), "CDATA", string2, false);
                }
            }
        }
        return bl;
    }
}

