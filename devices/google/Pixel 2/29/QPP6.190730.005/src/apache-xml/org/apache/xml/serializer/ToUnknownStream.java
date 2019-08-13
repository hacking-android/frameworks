/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;
import java.util.Vector;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Transformer;
import org.apache.xml.serializer.AttributesImplSerializer;
import org.apache.xml.serializer.DOMSerializer;
import org.apache.xml.serializer.NamespaceMappings;
import org.apache.xml.serializer.OutputPropertiesFactory;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.serializer.SerializerBase;
import org.apache.xml.serializer.SerializerFactory;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.ToXMLStream;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public final class ToUnknownStream
extends SerializerBase {
    private static final String EMPTYSTRING = "";
    private String m_firstElementLocalName = null;
    private String m_firstElementName;
    private String m_firstElementPrefix;
    private String m_firstElementURI;
    private boolean m_firstTagNotEmitted = true;
    private SerializationHandler m_handler = new ToXMLStream();
    private Vector m_namespacePrefix = null;
    private Vector m_namespaceURI = null;
    private boolean m_needToCallStartDocument = false;
    private boolean m_setDoctypePublic_called = false;
    private boolean m_setDoctypeSystem_called = false;
    private boolean m_setMediaType_called = false;
    private boolean m_setVersion_called = false;
    private boolean m_wrapped_handler_not_initialized = false;

    private void emitFirstTag() throws SAXException {
        if (this.m_firstElementName != null) {
            if (this.m_wrapped_handler_not_initialized) {
                this.initStreamOutput();
                this.m_wrapped_handler_not_initialized = false;
            }
            this.m_handler.startElement(this.m_firstElementURI, null, this.m_firstElementName, this.m_attributes);
            this.m_attributes = null;
            Object object = this.m_namespacePrefix;
            if (object != null) {
                int n = ((Vector)object).size();
                for (int i = 0; i < n; ++i) {
                    String string = (String)this.m_namespacePrefix.elementAt(i);
                    object = (String)this.m_namespaceURI.elementAt(i);
                    this.m_handler.startPrefixMapping(string, (String)object, false);
                }
                this.m_namespacePrefix = null;
                this.m_namespaceURI = null;
            }
            this.m_firstTagNotEmitted = false;
        }
    }

    private void flush() {
        try {
            if (this.m_firstTagNotEmitted) {
                this.emitFirstTag();
            }
            if (this.m_needToCallStartDocument) {
                this.m_handler.startDocument();
                this.m_needToCallStartDocument = false;
            }
            return;
        }
        catch (SAXException sAXException) {
            throw new RuntimeException(sAXException.toString());
        }
    }

    private String getLocalNameUnknown(String string) {
        int n = string.lastIndexOf(58);
        String string2 = string;
        if (n >= 0) {
            string2 = string.substring(n + 1);
        }
        n = string2.lastIndexOf(64);
        string = string2;
        if (n >= 0) {
            string = string2.substring(n + 1);
        }
        return string;
    }

    private String getPrefixPartUnknown(String string) {
        int n = string.indexOf(58);
        string = n > 0 ? string.substring(0, n) : EMPTYSTRING;
        return string;
    }

    private void initStreamOutput() throws SAXException {
        if (this.isFirstElemHTML()) {
            SerializationHandler serializationHandler = this.m_handler;
            Object object = OutputPropertiesFactory.getDefaultMethodProperties("html");
            this.m_handler = (SerializationHandler)SerializerFactory.getSerializer((Properties)object);
            object = serializationHandler.getWriter();
            if (object != null) {
                this.m_handler.setWriter((Writer)object);
            } else {
                object = serializationHandler.getOutputStream();
                if (object != null) {
                    this.m_handler.setOutputStream((OutputStream)object);
                }
            }
            this.m_handler.setVersion(serializationHandler.getVersion());
            this.m_handler.setDoctypeSystem(serializationHandler.getDoctypeSystem());
            this.m_handler.setDoctypePublic(serializationHandler.getDoctypePublic());
            this.m_handler.setMediaType(serializationHandler.getMediaType());
            this.m_handler.setTransformer(serializationHandler.getTransformer());
        }
        if (this.m_needToCallStartDocument) {
            this.m_handler.startDocument();
            this.m_needToCallStartDocument = false;
        }
        this.m_wrapped_handler_not_initialized = false;
    }

    private boolean isFirstElemHTML() {
        Object object;
        boolean bl;
        boolean bl2 = bl = this.getLocalNameUnknown(this.m_firstElementName).equalsIgnoreCase("html");
        if (bl) {
            object = this.m_firstElementURI;
            bl2 = bl;
            if (object != null) {
                bl2 = bl;
                if (!EMPTYSTRING.equals(object)) {
                    bl2 = false;
                }
            }
        }
        bl = bl2;
        if (bl2) {
            object = this.m_namespacePrefix;
            bl = bl2;
            if (object != null) {
                int n = ((Vector)object).size();
                int n2 = 0;
                do {
                    bl = bl2;
                    if (n2 >= n) break;
                    String string = (String)this.m_namespacePrefix.elementAt(n2);
                    object = (String)this.m_namespaceURI.elementAt(n2);
                    String string2 = this.m_firstElementPrefix;
                    if (string2 != null && string2.equals(string) && !EMPTYSTRING.equals(object)) {
                        bl = false;
                        break;
                    }
                    ++n2;
                } while (true);
            }
        }
        return bl;
    }

    @Override
    public void addAttribute(String string, String string2) {
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.addAttribute(string, string2);
    }

    @Override
    public void addAttribute(String string, String string2, String string3, String string4, String string5, boolean bl) throws SAXException {
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.addAttribute(string, string2, string3, string4, string5, bl);
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
        this.m_handler.addAttributes(attributes);
    }

    @Override
    public void addUniqueAttribute(String string, String string2, int n) throws SAXException {
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.addUniqueAttribute(string, string2, n);
    }

    @Override
    public ContentHandler asContentHandler() throws IOException {
        return this;
    }

    @Override
    public Object asDOM3Serializer() throws IOException {
        return this.m_handler.asDOM3Serializer();
    }

    @Override
    public DOMSerializer asDOMSerializer() throws IOException {
        return this.m_handler.asDOMSerializer();
    }

    @Override
    public void attributeDecl(String string, String string2, String string3, String string4, String string5) throws SAXException {
        this.m_handler.attributeDecl(string, string2, string3, string4, string5);
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
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.characters(arrc, n, n2);
    }

    @Override
    public void close() {
        this.m_handler.close();
    }

    @Override
    public void comment(String string) throws SAXException {
        if (this.m_firstTagNotEmitted && this.m_firstElementName != null) {
            this.emitFirstTag();
        } else if (this.m_needToCallStartDocument) {
            this.m_handler.startDocument();
            this.m_needToCallStartDocument = false;
        }
        this.m_handler.comment(string);
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.comment(arrc, n, n2);
    }

    @Override
    public void elementDecl(String string, String string2) throws SAXException {
        if (this.m_firstTagNotEmitted) {
            this.emitFirstTag();
        }
        this.m_handler.elementDecl(string, string2);
    }

    @Override
    public void endCDATA() throws SAXException {
        this.m_handler.endCDATA();
    }

    @Override
    public void endDTD() throws SAXException {
        this.m_handler.endDTD();
    }

    @Override
    public void endDocument() throws SAXException {
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.endDocument();
    }

    @Override
    public void endElement(String string) throws SAXException {
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.endElement(string);
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        String string4 = string;
        String string5 = string2;
        if (this.m_firstTagNotEmitted) {
            this.flush();
            String string6 = string;
            if (string == null) {
                string6 = string;
                if (this.m_firstElementURI != null) {
                    string6 = this.m_firstElementURI;
                }
            }
            string4 = string6;
            string5 = string2;
            if (string2 == null) {
                string4 = string6;
                string5 = string2;
                if (this.m_firstElementLocalName != null) {
                    string5 = this.m_firstElementLocalName;
                    string4 = string6;
                }
            }
        }
        this.m_handler.endElement(string4, string5, string3);
    }

    @Override
    public void endEntity(String string) throws SAXException {
        if (this.m_firstTagNotEmitted) {
            this.emitFirstTag();
        }
        this.m_handler.endEntity(string);
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
        this.m_handler.endPrefixMapping(string);
    }

    @Override
    public void entityReference(String string) throws SAXException {
        this.m_handler.entityReference(string);
    }

    @Override
    public void externalEntityDecl(String string, String string2, String string3) throws SAXException {
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.externalEntityDecl(string, string2, string3);
    }

    protected void firePseudoElement(String arrc) {
        if (this.m_tracer != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append('<');
            stringBuffer.append((String)arrc);
            arrc = stringBuffer.toString().toCharArray();
            this.m_tracer.fireGenerateEvent(11, arrc, 0, arrc.length);
        }
    }

    @Override
    public void flushPending() throws SAXException {
        this.flush();
        this.m_handler.flushPending();
    }

    @Override
    public String getDoctypePublic() {
        return this.m_handler.getDoctypePublic();
    }

    @Override
    public String getDoctypeSystem() {
        return this.m_handler.getDoctypeSystem();
    }

    @Override
    public String getEncoding() {
        return this.m_handler.getEncoding();
    }

    @Override
    public boolean getIndent() {
        return this.m_handler.getIndent();
    }

    @Override
    public int getIndentAmount() {
        return this.m_handler.getIndentAmount();
    }

    @Override
    public String getMediaType() {
        return this.m_handler.getMediaType();
    }

    @Override
    public NamespaceMappings getNamespaceMappings() {
        NamespaceMappings namespaceMappings = null;
        SerializationHandler serializationHandler = this.m_handler;
        if (serializationHandler != null) {
            namespaceMappings = serializationHandler.getNamespaceMappings();
        }
        return namespaceMappings;
    }

    @Override
    public String getNamespaceURI(String string, boolean bl) {
        return this.m_handler.getNamespaceURI(string, bl);
    }

    @Override
    public String getNamespaceURIFromPrefix(String string) {
        return this.m_handler.getNamespaceURIFromPrefix(string);
    }

    @Override
    public boolean getOmitXMLDeclaration() {
        return this.m_handler.getOmitXMLDeclaration();
    }

    @Override
    public Properties getOutputFormat() {
        return this.m_handler.getOutputFormat();
    }

    @Override
    public OutputStream getOutputStream() {
        return this.m_handler.getOutputStream();
    }

    @Override
    public String getPrefix(String string) {
        return this.m_handler.getPrefix(string);
    }

    @Override
    public String getStandalone() {
        return this.m_handler.getStandalone();
    }

    @Override
    public Transformer getTransformer() {
        return this.m_handler.getTransformer();
    }

    @Override
    public String getVersion() {
        return this.m_handler.getVersion();
    }

    @Override
    public Writer getWriter() {
        return this.m_handler.getWriter();
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.ignorableWhitespace(arrc, n, n2);
    }

    @Override
    public void internalEntityDecl(String string, String string2) throws SAXException {
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.internalEntityDecl(string, string2);
    }

    @Override
    public void namespaceAfterStartElement(String string, String string2) throws SAXException {
        String string3;
        if (this.m_firstTagNotEmitted && this.m_firstElementURI == null && (string3 = this.m_firstElementName) != null && ToUnknownStream.getPrefixPart(string3) == null && EMPTYSTRING.equals(string)) {
            this.m_firstElementURI = string2;
        }
        this.startPrefixMapping(string, string2, false);
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.processingInstruction(string, string2);
    }

    @Override
    public boolean reset() {
        return this.m_handler.reset();
    }

    @Override
    public void serialize(Node node) throws IOException {
        if (this.m_firstTagNotEmitted) {
            this.flush();
        }
        this.m_handler.serialize(node);
    }

    @Override
    public void setCdataSectionElements(Vector vector) {
        this.m_handler.setCdataSectionElements(vector);
    }

    @Override
    public void setContentHandler(ContentHandler contentHandler) {
        this.m_handler.setContentHandler(contentHandler);
    }

    @Override
    public void setDoctype(String string, String string2) {
        this.m_handler.setDoctypePublic(string2);
        this.m_handler.setDoctypeSystem(string);
    }

    @Override
    public void setDoctypePublic(String string) {
        this.m_handler.setDoctypePublic(string);
        this.m_setDoctypePublic_called = true;
    }

    @Override
    public void setDoctypeSystem(String string) {
        this.m_handler.setDoctypeSystem(string);
        this.m_setDoctypeSystem_called = true;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        this.m_handler.setDocumentLocator(locator);
    }

    @Override
    public void setEncoding(String string) {
        this.m_handler.setEncoding(string);
    }

    @Override
    public boolean setEscaping(boolean bl) throws SAXException {
        return this.m_handler.setEscaping(bl);
    }

    @Override
    public void setIndent(boolean bl) {
        this.m_handler.setIndent(bl);
    }

    @Override
    public void setIndentAmount(int n) {
        this.m_handler.setIndentAmount(n);
    }

    @Override
    public void setMediaType(String string) {
        this.m_handler.setMediaType(string);
        this.m_setMediaType_called = true;
    }

    @Override
    public void setOmitXMLDeclaration(boolean bl) {
        this.m_handler.setOmitXMLDeclaration(bl);
    }

    @Override
    public void setOutputFormat(Properties properties) {
        this.m_handler.setOutputFormat(properties);
    }

    @Override
    public void setOutputStream(OutputStream outputStream) {
        this.m_handler.setOutputStream(outputStream);
    }

    @Override
    public void setSourceLocator(SourceLocator sourceLocator) {
        this.m_handler.setSourceLocator(sourceLocator);
    }

    @Override
    public void setStandalone(String string) {
        this.m_handler.setStandalone(string);
    }

    @Override
    public void setTransformer(Transformer transformer) {
        this.m_handler.setTransformer(transformer);
        this.m_tracer = transformer instanceof SerializerTrace && ((SerializerTrace)((Object)transformer)).hasTraceListeners() ? (SerializerTrace)((Object)transformer) : null;
    }

    @Override
    public void setVersion(String string) {
        this.m_handler.setVersion(string);
        this.m_setVersion_called = true;
    }

    @Override
    public void setWriter(Writer writer) {
        this.m_handler.setWriter(writer);
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
        this.m_handler.skippedEntity(string);
    }

    @Override
    public void startCDATA() throws SAXException {
        this.m_handler.startCDATA();
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
        this.m_handler.startDTD(string, string2, string3);
    }

    @Override
    public void startDocument() throws SAXException {
        this.m_needToCallStartDocument = true;
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
        if (this.m_firstTagNotEmitted) {
            if (this.m_firstElementName != null) {
                this.flush();
                this.m_handler.startElement(string, string2, string3, attributes);
            } else {
                this.m_wrapped_handler_not_initialized = true;
                this.m_firstElementName = string3;
                this.m_firstElementPrefix = this.getPrefixPartUnknown(string3);
                this.m_firstElementURI = string;
                this.m_firstElementLocalName = string2;
                if (this.m_tracer != null) {
                    this.firePseudoElement(string3);
                }
                if (attributes != null) {
                    super.addAttributes(attributes);
                }
                if (attributes != null) {
                    this.flush();
                }
            }
        } else {
            this.m_handler.startElement(string, string2, string3, attributes);
        }
    }

    @Override
    public void startEntity(String string) throws SAXException {
        this.m_handler.startEntity(string);
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
        this.startPrefixMapping(string, string2, true);
    }

    @Override
    public boolean startPrefixMapping(String string, String string2, boolean bl) throws SAXException {
        boolean bl2 = false;
        if (this.m_firstTagNotEmitted) {
            if (this.m_firstElementName != null && bl) {
                this.flush();
                bl = this.m_handler.startPrefixMapping(string, string2, bl);
            } else {
                if (this.m_namespacePrefix == null) {
                    this.m_namespacePrefix = new Vector();
                    this.m_namespaceURI = new Vector();
                }
                this.m_namespacePrefix.addElement(string);
                this.m_namespaceURI.addElement(string2);
                bl = bl2;
                if (this.m_firstElementURI == null) {
                    bl = bl2;
                    if (string.equals(this.m_firstElementPrefix)) {
                        this.m_firstElementURI = string2;
                        bl = bl2;
                    }
                }
            }
        } else {
            bl = this.m_handler.startPrefixMapping(string, string2, bl);
        }
        return bl;
    }
}

