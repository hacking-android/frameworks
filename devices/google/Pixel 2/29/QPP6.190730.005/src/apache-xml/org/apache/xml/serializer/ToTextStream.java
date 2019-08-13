/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import org.apache.xml.serializer.EncodingInfo;
import org.apache.xml.serializer.Encodings;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.ToStream;
import org.apache.xml.serializer.utils.Messages;
import org.apache.xml.serializer.utils.Utils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ToTextStream
extends ToStream {
    @Override
    public void addAttribute(String string, String string2) {
    }

    @Override
    public void addAttribute(String string, String string2, String string3, String string4, String string5, boolean bl) {
    }

    @Override
    public void addUniqueAttribute(String string, String string2, int n) throws SAXException {
    }

    @Override
    public void cdata(char[] arrc, int n, int n2) throws SAXException {
        try {
            this.writeNormalizedChars(arrc, n, n2, this.m_lineSepUse);
            if (this.m_tracer != null) {
                super.fireCDATAEvent(arrc, n, n2);
            }
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
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
        this.flushPending();
        try {
            if (this.inTemporaryOutputState()) {
                this.m_writer.write(arrc, n, n2);
            } else {
                this.writeNormalizedChars(arrc, n, n2, this.m_lineSepUse);
            }
            if (this.m_tracer != null) {
                super.fireCharEvent(arrc, n, n2);
            }
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public void charactersRaw(char[] arrc, int n, int n2) throws SAXException {
        try {
            this.writeNormalizedChars(arrc, n, n2, this.m_lineSepUse);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
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
        this.flushPending();
        if (this.m_tracer != null) {
            super.fireCommentEvent(arrc, n, n2);
        }
    }

    @Override
    public void endCDATA() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
        this.flushPending();
        this.flushWriter();
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
    public void entityReference(String string) throws SAXException {
        if (this.m_tracer != null) {
            super.fireEntityReference(string);
        }
    }

    @Override
    public void flushPending() throws SAXException {
        if (this.m_needToCallStartDocument) {
            this.startDocumentInternal();
            this.m_needToCallStartDocument = false;
        }
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        try {
            this.writeNormalizedChars(arrc, n, n2, this.m_lineSepUse);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public void namespaceAfterStartElement(String string, String string2) throws SAXException {
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        this.flushPending();
        if (this.m_tracer != null) {
            super.fireEscapingEvent(string, string2);
        }
    }

    @Override
    protected void startDocumentInternal() throws SAXException {
        super.startDocumentInternal();
        this.m_needToCallStartDocument = false;
    }

    @Override
    public void startElement(String string, String string2, String string3) throws SAXException {
        if (this.m_needToCallStartDocument) {
            this.startDocumentInternal();
        }
        if (this.m_tracer != null) {
            super.fireStartElem(string3);
            this.firePseudoAttributes();
        }
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        if (this.m_tracer != null) {
            super.fireStartElem(string3);
            this.firePseudoAttributes();
        }
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
    }

    @Override
    public boolean startPrefixMapping(String string, String string2, boolean bl) throws SAXException {
        return false;
    }

    void writeNormalizedChars(char[] arrc, int n, int n2, boolean bl) throws IOException, SAXException {
        String string = this.getEncoding();
        Writer writer = this.m_writer;
        n2 = n + n2;
        while (n < n2) {
            String string2;
            char c = arrc[n];
            if ('\n' == c && bl) {
                writer.write(this.m_lineSep, 0, this.m_lineSepLen);
            } else if (this.m_encodingInfo.isInEncoding(c)) {
                writer.write(c);
            } else if (Encodings.isHighUTF16Surrogate(c)) {
                int n3 = this.writeUTF16Surrogate(c, arrc, n, n2);
                if (n3 != 0) {
                    string2 = Integer.toString(n3);
                    string2 = Utils.messages.createMessage("ER_ILLEGAL_CHARACTER", new Object[]{string2, string});
                    System.err.println(string2);
                }
                ++n;
            } else if (string != null) {
                writer.write(38);
                writer.write(35);
                writer.write(Integer.toString(c));
                writer.write(59);
                string2 = Integer.toString(c);
                string2 = Utils.messages.createMessage("ER_ILLEGAL_CHARACTER", new Object[]{string2, string});
                System.err.println(string2);
            } else {
                writer.write(c);
            }
            ++n;
        }
    }
}

