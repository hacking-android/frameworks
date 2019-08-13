/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Transformer;
import org.apache.xml.serializer.AttributesImplSerializer;
import org.apache.xml.serializer.DOMSerializer;
import org.apache.xml.serializer.ElemContext;
import org.apache.xml.serializer.NamespaceMappings;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.serializer.SerializerConstants;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.dom3.DOM3SerializerImpl;
import org.apache.xml.serializer.utils.Messages;
import org.apache.xml.serializer.utils.Utils;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public abstract class SerializerBase
implements SerializationHandler,
SerializerConstants {
    public static final String PKG_NAME;
    public static final String PKG_PATH;
    protected Hashtable m_CdataElems = null;
    private HashMap m_OutputProps;
    private HashMap m_OutputPropsDefault;
    protected String m_StringOfCDATASections = null;
    protected char[] m_attrBuff = new char[30];
    protected AttributesImplSerializer m_attributes = new AttributesImplSerializer();
    protected boolean m_cdataTagOpen = false;
    protected char[] m_charsBuff = new char[60];
    protected boolean m_doIndent = false;
    boolean m_docIsEmpty = true;
    protected String m_doctypePublic;
    protected String m_doctypeSystem;
    protected ElemContext m_elemContext = new ElemContext();
    protected boolean m_inEntityRef = false;
    protected boolean m_inExternalDTD = false;
    protected int m_indentAmount = 0;
    protected String m_mediatype;
    protected boolean m_needToCallStartDocument = true;
    boolean m_needToOutputDocTypeDecl = true;
    protected NamespaceMappings m_prefixMap;
    protected boolean m_shouldNotWriteXMLHeader = false;
    protected SourceLocator m_sourceLocator;
    private String m_standalone;
    protected boolean m_standaloneWasSpecified = false;
    protected SerializerTrace m_tracer;
    private Transformer m_transformer;
    protected String m_version = null;
    protected Writer m_writer = null;

    static {
        CharSequence charSequence = SerializerBase.class.getName();
        int n = ((String)charSequence).lastIndexOf(46);
        PKG_NAME = n < 0 ? "" : ((String)charSequence).substring(0, n);
        charSequence = new StringBuffer();
        for (n = 0; n < PKG_NAME.length(); ++n) {
            char c = PKG_NAME.charAt(n);
            if (c == '.') {
                ((StringBuffer)charSequence).append('/');
                continue;
            }
            ((StringBuffer)charSequence).append(c);
        }
        PKG_PATH = ((StringBuffer)charSequence).toString();
    }

    SerializerBase() {
    }

    private void addCDATAElement(String string, String string2) {
        Hashtable<String, String> hashtable;
        if (this.m_CdataElems == null) {
            this.m_CdataElems = new Hashtable();
        }
        Hashtable<String, String> hashtable2 = hashtable = (Hashtable<String, String>)this.m_CdataElems.get(string2);
        if (hashtable == null) {
            hashtable2 = new Hashtable<String, String>();
            this.m_CdataElems.put(string2, hashtable2);
        }
        hashtable2.put(string, string);
    }

    private void flushMyWriter() {
        Writer writer = this.m_writer;
        if (writer != null) {
            try {
                writer.flush();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    private String getElementURI() {
        String string = SerializerBase.getPrefixPart(this.m_elemContext.m_elementName);
        string = string == null ? this.m_prefixMap.lookupNamespace("") : this.m_prefixMap.lookupNamespace(string);
        String string2 = string;
        if (string == null) {
            string2 = "";
        }
        return string2;
    }

    static char getFirstCharLocName(String string) {
        int n = string.indexOf(125);
        int n2 = n < 0 ? (n = (int)string.charAt(0)) : (n = (int)string.charAt(n + 1));
        return (char)n2;
    }

    protected static String getLocalName(String string) {
        block0 : {
            int n = string.lastIndexOf(58);
            if (n <= 0) break block0;
            string = string.substring(n + 1);
        }
        return string;
    }

    protected static final String getPrefixPart(String string) {
        int n = string.indexOf(58);
        string = n > 0 ? string.substring(0, n) : null;
        return string;
    }

    private String getProp(String string, boolean bl) {
        if (this.m_OutputProps == null) {
            this.m_OutputProps = new HashMap();
            this.m_OutputPropsDefault = new HashMap();
        }
        string = bl ? (String)this.m_OutputPropsDefault.get(string) : (String)this.m_OutputProps.get(string);
        return string;
    }

    private void resetSerializerBase() {
        this.m_attributes.clear();
        this.m_CdataElems = null;
        this.m_cdataTagOpen = false;
        this.m_docIsEmpty = true;
        this.m_doctypePublic = null;
        this.m_doctypeSystem = null;
        this.m_doIndent = false;
        this.m_elemContext = new ElemContext();
        this.m_indentAmount = 0;
        this.m_inEntityRef = false;
        this.m_inExternalDTD = false;
        this.m_mediatype = null;
        this.m_needToCallStartDocument = true;
        this.m_needToOutputDocTypeDecl = false;
        Object object = this.m_OutputProps;
        if (object != null) {
            ((HashMap)object).clear();
        }
        if ((object = this.m_OutputPropsDefault) != null) {
            ((HashMap)object).clear();
        }
        if ((object = this.m_prefixMap) != null) {
            ((NamespaceMappings)object).reset();
        }
        this.m_shouldNotWriteXMLHeader = false;
        this.m_sourceLocator = null;
        this.m_standalone = null;
        this.m_standaloneWasSpecified = false;
        this.m_StringOfCDATASections = null;
        this.m_tracer = null;
        this.m_transformer = null;
        this.m_version = null;
    }

    private static final boolean subPartMatch(String string, String string2) {
        boolean bl = string == string2 || string != null && string.equals(string2);
        return bl;
    }

    @Override
    public void addAttribute(String string, String string2) {
        if (this.m_elemContext.m_startTagOpen) {
            string = this.patchName(string);
            String string3 = SerializerBase.getLocalName(string);
            this.addAttributeAlways(this.getNamespaceURI(string, false), string3, string, "CDATA", string2, false);
        }
    }

    @Override
    public void addAttribute(String string, String string2, String string3, String string4, String string5) throws SAXException {
        if (this.m_elemContext.m_startTagOpen) {
            this.addAttributeAlways(string, string2, string3, string4, string5, false);
        }
    }

    @Override
    public void addAttribute(String string, String string2, String string3, String string4, String string5, boolean bl) throws SAXException {
        if (this.m_elemContext.m_startTagOpen) {
            this.addAttributeAlways(string, string2, string3, string4, string5, bl);
        }
    }

    public boolean addAttributeAlways(String string, String string2, String string3, String string4, String string5, boolean bl) {
        int n = string2 != null && string != null && string.length() != 0 ? this.m_attributes.getIndex(string, string2) : this.m_attributes.getIndex(string3);
        if (n >= 0) {
            this.m_attributes.setValue(n, string5);
            bl = false;
        } else {
            this.m_attributes.addAttribute(string, string2, string3, string4, string5);
            bl = true;
        }
        return bl;
    }

    @Override
    public void addAttributes(Attributes attributes) throws SAXException {
        int n = attributes.getLength();
        for (int i = 0; i < n; ++i) {
            String string;
            String string2 = string = attributes.getURI(i);
            if (string == null) {
                string2 = "";
            }
            this.addAttributeAlways(string2, attributes.getLocalName(i), attributes.getQName(i), attributes.getType(i), attributes.getValue(i), false);
        }
    }

    @Override
    public void addXSLAttribute(String string, String string2, String string3) {
        if (this.m_elemContext.m_startTagOpen) {
            string = this.patchName(string);
            this.addAttributeAlways(string3, SerializerBase.getLocalName(string), string, "CDATA", string2, true);
        }
    }

    @Override
    public ContentHandler asContentHandler() throws IOException {
        return this;
    }

    @Override
    public Object asDOM3Serializer() throws IOException {
        return new DOM3SerializerImpl(this);
    }

    @Override
    public DOMSerializer asDOMSerializer() throws IOException {
        return this;
    }

    @Override
    public void characters(Node object) throws SAXException {
        this.flushPending();
        object = object.getNodeValue();
        if (object != null) {
            int n = ((String)object).length();
            if (n > this.m_charsBuff.length) {
                this.m_charsBuff = new char[n * 2 + 1];
            }
            ((String)object).getChars(0, n, this.m_charsBuff, 0);
            this.characters(this.m_charsBuff, 0, n);
        }
    }

    @Override
    public void close() {
    }

    @Override
    public void comment(String string) throws SAXException {
        this.m_docIsEmpty = false;
        int n = string.length();
        if (n > this.m_charsBuff.length) {
            this.m_charsBuff = new char[n * 2 + 1];
        }
        string.getChars(0, n, this.m_charsBuff, 0);
        this.comment(this.m_charsBuff, 0, n);
    }

    public boolean documentIsEmpty() {
        boolean bl = this.m_docIsEmpty && this.m_elemContext.m_currentElemDepth == 0;
        return bl;
    }

    @Override
    public void endEntity(String string) throws SAXException {
        if (string.equals("[dtd]")) {
            this.m_inExternalDTD = false;
        }
        this.m_inEntityRef = false;
        if (this.m_tracer != null) {
            this.fireEndEntity(string);
        }
    }

    @Override
    public void entityReference(String string) throws SAXException {
        this.flushPending();
        this.startEntity(string);
        this.endEntity(string);
        if (this.m_tracer != null) {
            this.fireEntityReference(string);
        }
    }

    @Override
    public void error(SAXParseException sAXParseException) throws SAXException {
    }

    @Override
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        this.m_elemContext.m_startTagOpen = false;
    }

    protected void fireCDATAEvent(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_tracer != null) {
            this.flushMyWriter();
            this.m_tracer.fireGenerateEvent(10, arrc, n, n2);
        }
    }

    protected void fireCharEvent(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_tracer != null) {
            this.flushMyWriter();
            this.m_tracer.fireGenerateEvent(5, arrc, n, n2);
        }
    }

    protected void fireCommentEvent(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_tracer != null) {
            this.flushMyWriter();
            this.m_tracer.fireGenerateEvent(8, new String(arrc, n, n2));
        }
    }

    protected void fireEndDoc() throws SAXException {
        if (this.m_tracer != null) {
            this.flushMyWriter();
            this.m_tracer.fireGenerateEvent(2);
        }
    }

    protected void fireEndElem(String string) throws SAXException {
        if (this.m_tracer != null) {
            this.flushMyWriter();
            this.m_tracer.fireGenerateEvent(4, string, (Attributes)null);
        }
    }

    public void fireEndEntity(String string) throws SAXException {
        if (this.m_tracer != null) {
            this.flushMyWriter();
        }
    }

    protected void fireEntityReference(String string) throws SAXException {
        if (this.m_tracer != null) {
            this.flushMyWriter();
            this.m_tracer.fireGenerateEvent(9, string, (Attributes)null);
        }
    }

    protected void fireEscapingEvent(String string, String string2) throws SAXException {
        if (this.m_tracer != null) {
            this.flushMyWriter();
            this.m_tracer.fireGenerateEvent(7, string, string2);
        }
    }

    protected void fireStartDoc() throws SAXException {
        if (this.m_tracer != null) {
            this.flushMyWriter();
            this.m_tracer.fireGenerateEvent(1);
        }
    }

    protected void fireStartElem(String string) throws SAXException {
        if (this.m_tracer != null) {
            this.flushMyWriter();
            this.m_tracer.fireGenerateEvent(3, string, this.m_attributes);
        }
    }

    protected void fireStartEntity(String string) throws SAXException {
        if (this.m_tracer != null) {
            this.flushMyWriter();
            this.m_tracer.fireGenerateEvent(9, string);
        }
    }

    @Override
    public String getDoctypePublic() {
        return this.m_doctypePublic;
    }

    @Override
    public String getDoctypeSystem() {
        return this.m_doctypeSystem;
    }

    @Override
    public String getEncoding() {
        return this.getOutputProperty("encoding");
    }

    @Override
    public boolean getIndent() {
        return this.m_doIndent;
    }

    @Override
    public int getIndentAmount() {
        return this.m_indentAmount;
    }

    @Override
    public String getMediaType() {
        return this.m_mediatype;
    }

    @Override
    public NamespaceMappings getNamespaceMappings() {
        return this.m_prefixMap;
    }

    @Override
    public String getNamespaceURI(String string, boolean bl) {
        String string2;
        block8 : {
            String string3;
            int n;
            String string4;
            block7 : {
                string3 = "";
                n = string.lastIndexOf(58);
                string4 = n > 0 ? string.substring(0, n) : "";
                if (!"".equals(string4)) break block7;
                string2 = string3;
                if (!bl) break block8;
            }
            NamespaceMappings namespaceMappings = this.m_prefixMap;
            string2 = string3;
            if (namespaceMappings != null) {
                string2 = string3 = namespaceMappings.lookupNamespace(string4);
                if (string3 == null) {
                    if (string4.equals("xmlns")) {
                        string2 = string3;
                    } else {
                        throw new RuntimeException(Utils.messages.createMessage("ER_NAMESPACE_PREFIX", new Object[]{string.substring(0, n)}));
                    }
                }
            }
        }
        return string2;
    }

    @Override
    public String getNamespaceURIFromPrefix(String string) {
        String string2 = null;
        NamespaceMappings namespaceMappings = this.m_prefixMap;
        if (namespaceMappings != null) {
            string2 = namespaceMappings.lookupNamespace(string);
        }
        return string2;
    }

    @Override
    public boolean getOmitXMLDeclaration() {
        return this.m_shouldNotWriteXMLHeader;
    }

    Set getOutputPropDefaultKeys() {
        return this.m_OutputPropsDefault.keySet();
    }

    Set getOutputPropKeys() {
        return this.m_OutputProps.keySet();
    }

    @Override
    public String getOutputProperty(String string) {
        String string2;
        String string3 = string2 = this.getOutputPropertyNonDefault(string);
        if (string2 == null) {
            string3 = this.getOutputPropertyDefault(string);
        }
        return string3;
    }

    @Override
    public String getOutputPropertyDefault(String string) {
        return this.getProp(string, true);
    }

    public String getOutputPropertyNonDefault(String string) {
        return this.getProp(string, false);
    }

    @Override
    public String getPrefix(String string) {
        return this.m_prefixMap.lookupPrefix(string);
    }

    @Override
    public String getStandalone() {
        return this.m_standalone;
    }

    @Override
    public Transformer getTransformer() {
        return this.m_transformer;
    }

    @Override
    public String getVersion() {
        return this.m_version;
    }

    final boolean inTemporaryOutputState() {
        boolean bl = this.getEncoding() == null;
        return bl;
    }

    protected void initCDATA() {
    }

    void initCdataElems(String string) {
        if (string != null) {
            int n = string.length();
            boolean bl = false;
            boolean bl2 = false;
            StringBuffer stringBuffer = new StringBuffer();
            String string2 = null;
            for (int i = 0; i < n; ++i) {
                boolean bl3;
                boolean bl4;
                String string3;
                char c = string.charAt(i);
                if (Character.isWhitespace(c)) {
                    if (!bl) {
                        bl3 = bl;
                        bl4 = bl2;
                        string3 = string2;
                        if (stringBuffer.length() > 0) {
                            string3 = stringBuffer.toString();
                            if (!bl2) {
                                string2 = "";
                            }
                            this.addCDATAElement(string2, string3);
                            stringBuffer.setLength(0);
                            bl4 = false;
                            bl3 = bl;
                            string3 = string2;
                        }
                    } else {
                        stringBuffer.append(c);
                        bl3 = bl;
                        bl4 = bl2;
                        string3 = string2;
                    }
                } else if ('{' == c) {
                    bl3 = true;
                    bl4 = bl2;
                    string3 = string2;
                } else if ('}' == c) {
                    bl4 = true;
                    string3 = stringBuffer.toString();
                    stringBuffer.setLength(0);
                    bl3 = false;
                } else {
                    stringBuffer.append(c);
                    string3 = string2;
                    bl4 = bl2;
                    bl3 = bl;
                }
                bl = bl3;
                bl2 = bl4;
                string2 = string3;
            }
            if (stringBuffer.length() > 0) {
                string = stringBuffer.toString();
                if (!bl2) {
                    string2 = "";
                }
                this.addCDATAElement(string2, string);
            }
        }
    }

    protected boolean isCdataSection() {
        boolean bl;
        boolean bl2 = bl = false;
        if (this.m_StringOfCDATASections != null) {
            Object object;
            if (this.m_elemContext.m_elementLocalName == null) {
                object = SerializerBase.getLocalName(this.m_elemContext.m_elementName);
                this.m_elemContext.m_elementLocalName = object;
            }
            if (this.m_elemContext.m_elementURI == null) {
                this.m_elemContext.m_elementURI = this.getElementURI();
            } else if (this.m_elemContext.m_elementURI.length() == 0) {
                if (this.m_elemContext.m_elementName == null) {
                    object = this.m_elemContext;
                    ((ElemContext)object).m_elementName = ((ElemContext)object).m_elementLocalName;
                } else if (this.m_elemContext.m_elementLocalName.length() < this.m_elemContext.m_elementName.length()) {
                    this.m_elemContext.m_elementURI = this.getElementURI();
                }
            }
            object = (Hashtable)this.m_CdataElems.get(this.m_elemContext.m_elementLocalName);
            bl2 = bl;
            if (object != null) {
                bl2 = bl;
                if (((Hashtable)object).get(this.m_elemContext.m_elementURI) != null) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    @Override
    public void namespaceAfterStartElement(String string, String string2) throws SAXException {
    }

    @Override
    public void notationDecl(String string, String string2, String string3) throws SAXException {
    }

    protected String patchName(String charSequence) {
        int n = ((String)charSequence).lastIndexOf(58);
        if (n > 0) {
            int n2 = ((String)charSequence).indexOf(58);
            String string = ((String)charSequence).substring(0, n2);
            String string2 = ((String)charSequence).substring(n + 1);
            String string3 = this.m_prefixMap.lookupNamespace(string);
            if (string3 != null && string3.length() == 0) {
                return string2;
            }
            if (n2 != n) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(':');
                ((StringBuilder)charSequence).append(string2);
                return ((StringBuilder)charSequence).toString();
            }
        }
        return charSequence;
    }

    @Override
    public boolean reset() {
        this.resetSerializerBase();
        return true;
    }

    @Override
    public void setDTDEntityExpansion(boolean bl) {
    }

    @Override
    public void setDoctype(String string, String string2) {
        this.setOutputProperty("doctype-system", string);
        this.setOutputProperty("doctype-public", string2);
    }

    @Override
    public void setDoctypePublic(String string) {
        this.setOutputProperty("doctype-public", string);
    }

    @Override
    public void setDoctypeSystem(String string) {
        this.setOutputProperty("doctype-system", string);
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void setEncoding(String string) {
        this.setOutputProperty("encoding", string);
    }

    @Override
    public void setIndent(boolean bl) {
        String string = bl ? "yes" : "no";
        this.setOutputProperty("indent", string);
    }

    @Override
    public void setIndentAmount(int n) {
        this.m_indentAmount = n;
    }

    @Override
    public void setMediaType(String string) {
        this.setOutputProperty("media-type", string);
    }

    @Override
    public void setNamespaceMappings(NamespaceMappings namespaceMappings) {
        this.m_prefixMap = namespaceMappings;
    }

    @Override
    public void setOmitXMLDeclaration(boolean bl) {
        String string = bl ? "yes" : "no";
        this.setOutputProperty("omit-xml-declaration", string);
    }

    @Override
    public void setOutputProperty(String string, String string2) {
        this.setProp(string, string2, false);
    }

    @Override
    public void setOutputPropertyDefault(String string, String string2) {
        this.setProp(string, string2, true);
    }

    void setProp(String string, String string2, boolean bl) {
        if (this.m_OutputProps == null) {
            this.m_OutputProps = new HashMap();
            this.m_OutputPropsDefault = new HashMap();
        }
        if (bl) {
            this.m_OutputPropsDefault.put(string, string2);
        } else if ("cdata-section-elements".equals(string) && string2 != null) {
            this.initCdataElems(string2);
            String string3 = (String)this.m_OutputProps.get(string);
            if (string3 == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string3);
                stringBuilder.append(' ');
                stringBuilder.append(string2);
                string2 = stringBuilder.toString();
            }
            this.m_OutputProps.put(string, string2);
        } else {
            this.m_OutputProps.put(string, string2);
        }
    }

    @Override
    public void setSourceLocator(SourceLocator sourceLocator) {
        this.m_sourceLocator = sourceLocator;
    }

    @Override
    public void setStandalone(String string) {
        this.setOutputProperty("standalone", string);
    }

    protected void setStandaloneInternal(String string) {
        this.m_standalone = "yes".equals(string) ? "yes" : "no";
    }

    @Override
    public void setTransformer(Transformer transformer) {
        this.m_transformer = transformer;
        this.m_tracer = (transformer = this.m_transformer) instanceof SerializerTrace && ((SerializerTrace)((Object)transformer)).hasTraceListeners() ? (SerializerTrace)((Object)this.m_transformer) : null;
    }

    @Override
    public void setVersion(String string) {
        this.setOutputProperty("version", string);
    }

    @Override
    public void startDocument() throws SAXException {
        this.startDocumentInternal();
        this.m_needToCallStartDocument = false;
    }

    protected void startDocumentInternal() throws SAXException {
        if (this.m_tracer != null) {
            this.fireStartDoc();
        }
    }

    @Override
    public void unparsedEntityDecl(String string, String string2, String string3, String string4) throws SAXException {
    }

    @Override
    public void warning(SAXParseException sAXParseException) throws SAXException {
    }
}

