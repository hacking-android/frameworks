/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.util.Vector;
import javax.xml.transform.Transformer;
import org.apache.xml.serializer.ElemContext;
import org.apache.xml.serializer.SerializerBase;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.TransformStateSetter;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;

public abstract class ToSAXHandler
extends SerializerBase {
    protected LexicalHandler m_lexHandler;
    protected ContentHandler m_saxHandler;
    private boolean m_shouldGenerateNSAttribute = true;
    protected TransformStateSetter m_state = null;

    public ToSAXHandler() {
    }

    public ToSAXHandler(ContentHandler contentHandler, String string) {
        this.setContentHandler(contentHandler);
        this.setEncoding(string);
    }

    public ToSAXHandler(ContentHandler contentHandler, LexicalHandler lexicalHandler, String string) {
        this.setContentHandler(contentHandler);
        this.setLexHandler(lexicalHandler);
        this.setEncoding(string);
    }

    private void resetToSAXHandler() {
        this.m_lexHandler = null;
        this.m_saxHandler = null;
        this.m_state = null;
        this.m_shouldGenerateNSAttribute = false;
    }

    @Override
    public void addUniqueAttribute(String string, String string2, int n) throws SAXException {
        this.addAttribute(string, string2);
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
    public void characters(Node object) throws SAXException {
        TransformStateSetter transformStateSetter = this.m_state;
        if (transformStateSetter != null) {
            transformStateSetter.setCurrentNode((Node)object);
        }
        if ((object = object.getNodeValue()) != null) {
            this.characters((String)object);
        }
    }

    protected void closeCDATA() throws SAXException {
    }

    protected void closeStartTag() throws SAXException {
    }

    @Override
    public void comment(String string) throws SAXException {
        this.flushPending();
        if (this.m_lexHandler != null) {
            int n = string.length();
            if (n > this.m_charsBuff.length) {
                this.m_charsBuff = new char[n * 2 + 1];
            }
            string.getChars(0, n, this.m_charsBuff, 0);
            this.m_lexHandler.comment(this.m_charsBuff, 0, n);
            if (this.m_tracer != null) {
                super.fireCommentEvent(this.m_charsBuff, 0, n);
            }
        }
    }

    @Override
    public void error(SAXParseException sAXParseException) throws SAXException {
        super.error(sAXParseException);
        ContentHandler contentHandler = this.m_saxHandler;
        if (contentHandler instanceof ErrorHandler) {
            ((ErrorHandler)((Object)contentHandler)).error(sAXParseException);
        }
    }

    @Override
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        super.fatalError(sAXParseException);
        this.m_needToCallStartDocument = false;
        ContentHandler contentHandler = this.m_saxHandler;
        if (contentHandler instanceof ErrorHandler) {
            ((ErrorHandler)((Object)contentHandler)).fatalError(sAXParseException);
        }
    }

    @Override
    public void flushPending() throws SAXException {
        if (this.m_needToCallStartDocument) {
            this.startDocumentInternal();
            this.m_needToCallStartDocument = false;
        }
        if (this.m_elemContext.m_startTagOpen) {
            this.closeStartTag();
            this.m_elemContext.m_startTagOpen = false;
        }
        if (this.m_cdataTagOpen) {
            this.closeCDATA();
            this.m_cdataTagOpen = false;
        }
    }

    boolean getShouldOutputNSAttr() {
        return this.m_shouldGenerateNSAttribute;
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
    }

    @Override
    public boolean reset() {
        boolean bl = false;
        if (super.reset()) {
            this.resetToSAXHandler();
            bl = true;
        }
        return bl;
    }

    @Override
    public void setCdataSectionElements(Vector vector) {
    }

    @Override
    public void setContentHandler(ContentHandler contentHandler) {
        this.m_saxHandler = contentHandler;
        if (this.m_lexHandler == null && contentHandler instanceof LexicalHandler) {
            this.m_lexHandler = (LexicalHandler)((Object)contentHandler);
        }
    }

    public void setLexHandler(LexicalHandler lexicalHandler) {
        this.m_lexHandler = lexicalHandler;
    }

    public void setShouldOutputNSAttr(boolean bl) {
        this.m_shouldGenerateNSAttribute = bl;
    }

    public void setTransformState(TransformStateSetter transformStateSetter) {
        this.m_state = transformStateSetter;
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
    }

    @Override
    protected void startDocumentInternal() throws SAXException {
        if (this.m_needToCallStartDocument) {
            super.startDocumentInternal();
            this.m_saxHandler.startDocument();
            this.m_needToCallStartDocument = false;
        }
    }

    @Override
    public void startElement(String string) throws SAXException {
        TransformStateSetter transformStateSetter = this.m_state;
        if (transformStateSetter != null) {
            transformStateSetter.resetState(this.getTransformer());
        }
        if (this.m_tracer != null) {
            super.fireStartElem(string);
        }
    }

    @Override
    public void startElement(String object, String string, String string2) throws SAXException {
        object = this.m_state;
        if (object != null) {
            object.resetState(this.getTransformer());
        }
        if (this.m_tracer != null) {
            super.fireStartElem(string2);
        }
    }

    @Override
    public void startElement(String object, String string, String string2, Attributes attributes) throws SAXException {
        object = this.m_state;
        if (object != null) {
            object.resetState(this.getTransformer());
        }
        if (this.m_tracer != null) {
            super.fireStartElem(string2);
        }
    }

    @Override
    public void warning(SAXParseException sAXParseException) throws SAXException {
        super.warning(sAXParseException);
        ContentHandler contentHandler = this.m_saxHandler;
        if (contentHandler instanceof ErrorHandler) {
            ((ErrorHandler)((Object)contentHandler)).warning(sAXParseException);
        }
    }
}

