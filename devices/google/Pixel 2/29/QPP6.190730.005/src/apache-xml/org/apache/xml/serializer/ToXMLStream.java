/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import org.apache.xml.serializer.CharInfo;
import org.apache.xml.serializer.ElemContext;
import org.apache.xml.serializer.EncodingInfo;
import org.apache.xml.serializer.Encodings;
import org.apache.xml.serializer.NamespaceMappings;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.ToStream;
import org.apache.xml.serializer.utils.Messages;
import org.apache.xml.serializer.utils.Utils;
import org.xml.sax.SAXException;

public class ToXMLStream
extends ToStream {
    private CharInfo m_xmlcharInfo;

    public ToXMLStream() {
        this.m_charInfo = this.m_xmlcharInfo = CharInfo.getCharInfo(CharInfo.XML_ENTITIES_RESOURCE, "xml");
        this.initCDATA();
        this.m_prefixMap = new NamespaceMappings();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getXMLVersion() {
        String string = this.getVersion();
        if (string == null) return "1.0";
        if (string.equals("1.0")) return "1.0";
        if (string.equals("1.1")) {
            return "1.1";
        }
        string = Utils.messages.createMessage("ER_XML_VERSION_NOT_SUPPORTED", new Object[]{string});
        try {
            ErrorListener errorListener = super.getTransformer().getErrorListener();
            if (errorListener != null && this.m_sourceLocator != null) {
                TransformerException transformerException = new TransformerException(string, this.m_sourceLocator);
                errorListener.warning(transformerException);
                return "1.0";
            } else {
                System.out.println(string);
            }
            return "1.0";
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "1.0";
    }

    private void resetToXMLStream() {
    }

    public void CopyFrom(ToXMLStream toXMLStream) {
        this.setWriter(toXMLStream.m_writer);
        this.setEncoding(toXMLStream.getEncoding());
        this.setOmitXMLDeclaration(toXMLStream.getOmitXMLDeclaration());
        this.m_ispreserve = toXMLStream.m_ispreserve;
        this.m_preserves = toXMLStream.m_preserves;
        this.m_isprevtext = toXMLStream.m_isprevtext;
        this.m_doIndent = toXMLStream.m_doIndent;
        this.setIndentAmount(toXMLStream.getIndentAmount());
        this.m_startNewLine = toXMLStream.m_startNewLine;
        this.m_needToOutputDocTypeDecl = toXMLStream.m_needToOutputDocTypeDecl;
        this.setDoctypeSystem(toXMLStream.getDoctypeSystem());
        this.setDoctypePublic(toXMLStream.getDoctypePublic());
        this.setStandalone(toXMLStream.getStandalone());
        this.setMediaType(toXMLStream.getMediaType());
        this.m_encodingInfo = toXMLStream.m_encodingInfo;
        this.m_spaceBeforeClose = toXMLStream.m_spaceBeforeClose;
        this.m_cdataStartCalled = toXMLStream.m_cdataStartCalled;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addAttribute(String object, String object2, String charSequence, String string, String string2, boolean bl) throws SAXException {
        if (this.m_elemContext.m_startTagOpen) {
            CharSequence charSequence2 = charSequence;
            if (this.addAttributeAlways((String)object, (String)object2, (String)charSequence, string, string2, bl)) {
                charSequence2 = charSequence;
                if (!bl) {
                    charSequence2 = charSequence;
                    if (!((String)charSequence).startsWith("xmlns")) {
                        String string3 = this.ensureAttributesNamespaceIsDeclared((String)object, (String)object2, (String)charSequence);
                        charSequence2 = charSequence;
                        if (string3 != null) {
                            charSequence2 = charSequence;
                            if (!((String)charSequence).startsWith(string3)) {
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append(string3);
                                ((StringBuilder)charSequence).append(":");
                                ((StringBuilder)charSequence).append((String)object2);
                                charSequence2 = ((StringBuilder)charSequence).toString();
                            }
                        }
                    }
                }
            }
            this.addAttributeAlways((String)object, (String)object2, (String)charSequence2, string, string2, bl);
            return;
        }
        charSequence = Utils.messages.createMessage("ER_ILLEGAL_ATTRIBUTE_POSITION", new Object[]{object2});
        try {
            object = super.getTransformer().getErrorListener();
            if (object != null && this.m_sourceLocator != null) {
                object2 = new TransformerException((String)charSequence, this.m_sourceLocator);
                object.warning((TransformerException)object2);
                return;
            }
            System.out.println((String)charSequence);
            return;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addUniqueAttribute(String object, String string, int n) throws SAXException {
        if (!this.m_elemContext.m_startTagOpen) return;
        try {
            String string2 = this.patchName((String)object);
            object = this.m_writer;
            if ((n & 1) > 0 && this.m_xmlcharInfo.onlyQuotAmpLtGt) {
                ((Writer)object).write(32);
                ((Writer)object).write(string2);
                ((Writer)object).write("=\"");
                ((Writer)object).write(string);
                ((Writer)object).write(34);
                return;
            }
            ((Writer)object).write(32);
            ((Writer)object).write(string2);
            ((Writer)object).write("=\"");
            this.writeAttrString((Writer)object, string, this.getEncoding());
            ((Writer)object).write(34);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        this.flushPending();
        if (this.m_doIndent && !this.m_isprevtext) {
            try {
                this.outputLineSep();
            }
            catch (IOException iOException) {
                throw new SAXException(iOException);
            }
        }
        this.flushWriter();
        if (this.m_tracer != null) {
            super.fireEndDoc();
        }
    }

    @Override
    public void endElement(String string) throws SAXException {
        this.endElement(null, null, string);
    }

    public void endPreserving() throws SAXException {
        boolean bl = this.m_preserves.isEmpty() ? false : this.m_preserves.pop();
        this.m_ispreserve = bl;
    }

    @Override
    public void entityReference(String string) throws SAXException {
        block4 : {
            if (this.m_elemContext.m_startTagOpen) {
                this.closeStartTag();
                this.m_elemContext.m_startTagOpen = false;
            }
            try {
                if (this.shouldIndent()) {
                    this.indent();
                }
                Writer writer = this.m_writer;
                writer.write(38);
                writer.write(string);
                writer.write(59);
                if (this.m_tracer == null) break block4;
            }
            catch (IOException iOException) {
                throw new SAXException(iOException);
            }
            super.fireEntityReference(string);
        }
    }

    @Override
    public void namespaceAfterStartElement(String string, String string2) throws SAXException {
        if (this.m_elemContext.m_elementURI == null && ToXMLStream.getPrefixPart(this.m_elemContext.m_elementName) == null && "".equals(string)) {
            this.m_elemContext.m_elementURI = string2;
        }
        this.startPrefixMapping(string, string2, false);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        if (this.m_inEntityRef) {
            return;
        }
        this.flushPending();
        if (string.equals("javax.xml.transform.disable-output-escaping")) {
            this.startNonEscaping();
        } else if (string.equals("javax.xml.transform.enable-output-escaping")) {
            this.endNonEscaping();
        } else {
            int n;
            if (this.m_elemContext.m_startTagOpen) {
                this.closeStartTag();
                this.m_elemContext.m_startTagOpen = false;
            } else if (this.m_needToCallStartDocument) {
                this.startDocumentInternal();
            }
            if (this.shouldIndent()) {
                this.indent();
            }
            Writer writer = this.m_writer;
            writer.write("<?");
            writer.write(string);
            if (string2.length() > 0 && !Character.isSpaceChar(string2.charAt(0))) {
                writer.write(32);
            }
            if ((n = string2.indexOf("?>")) >= 0) {
                if (n > 0) {
                    writer.write(string2.substring(0, n));
                }
                writer.write("? >");
                if (n + 2 < string2.length()) {
                    writer.write(string2.substring(n + 2));
                }
            } else {
                writer.write(string2);
            }
            writer.write(63);
            writer.write(62);
            this.m_startNewLine = true;
        }
        if (this.m_tracer == null) return;
        super.fireEscapingEvent(string, string2);
        return;
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    protected boolean pushNamespace(String string, String string2) {
        try {
            if (this.m_prefixMap.pushNamespace(string, string2, this.m_elemContext.m_currentElemDepth)) {
                this.startPrefixMapping(string, string2);
                return true;
            }
        }
        catch (SAXException sAXException) {
            // empty catch block
        }
        return false;
    }

    @Override
    public boolean reset() {
        boolean bl = false;
        if (super.reset()) {
            bl = true;
        }
        return bl;
    }

    @Override
    public void startDocumentInternal() throws SAXException {
        if (this.m_needToCallStartDocument) {
            super.startDocumentInternal();
            this.m_needToCallStartDocument = false;
            if (this.m_inEntityRef) {
                return;
            }
            this.m_needToOutputDocTypeDecl = true;
            this.m_startNewLine = false;
            String string = this.getXMLVersion();
            if (!this.getOmitXMLDeclaration()) {
                CharSequence charSequence;
                String string2 = Encodings.getMimeEncoding(this.getEncoding());
                if (this.m_standaloneWasSpecified) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(" standalone=\"");
                    ((StringBuilder)charSequence).append(this.getStandalone());
                    ((StringBuilder)charSequence).append("\"");
                    charSequence = ((StringBuilder)charSequence).toString();
                } else {
                    charSequence = "";
                }
                try {
                    Writer writer = this.m_writer;
                    writer.write("<?xml version=\"");
                    writer.write(string);
                    writer.write("\" encoding=\"");
                    writer.write(string2);
                    writer.write(34);
                    writer.write((String)charSequence);
                    writer.write("?>");
                    if (this.m_doIndent && (this.m_standaloneWasSpecified || this.getDoctypePublic() != null || this.getDoctypeSystem() != null)) {
                        writer.write(this.m_lineSep, 0, this.m_lineSepLen);
                    }
                }
                catch (IOException iOException) {
                    throw new SAXException(iOException);
                }
            }
        }
    }

    public void startPreserving() throws SAXException {
        this.m_preserves.push(true);
        this.m_ispreserve = true;
    }
}

