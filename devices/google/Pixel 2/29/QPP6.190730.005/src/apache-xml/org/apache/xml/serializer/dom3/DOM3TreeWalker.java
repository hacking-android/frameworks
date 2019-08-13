/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.dom3;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.serializer.dom3.DOMErrorImpl;
import org.apache.xml.serializer.dom3.DOMLocatorImpl;
import org.apache.xml.serializer.dom3.NamespaceSupport;
import org.apache.xml.serializer.utils.Messages;
import org.apache.xml.serializer.utils.Utils;
import org.apache.xml.serializer.utils.XML11Char;
import org.apache.xml.serializer.utils.XMLChar;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.ls.LSSerializerFilter;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.LocatorImpl;

final class DOM3TreeWalker {
    private static final int CANONICAL = 1;
    private static final int CDATA = 2;
    private static final int CHARNORMALIZE = 4;
    private static final int COMMENTS = 8;
    private static final int DISCARDDEFAULT = 32768;
    private static final int DTNORMALIZE = 16;
    private static final int ELEM_CONTENT_WHITESPACE = 32;
    private static final int ENTITIES = 64;
    private static final int IGNORE_CHAR_DENORMALIZE = 131072;
    private static final int INFOSET = 128;
    private static final int NAMESPACEDECLS = 512;
    private static final int NAMESPACES = 256;
    private static final int NORMALIZECHARS = 1024;
    private static final int PRETTY_PRINT = 65536;
    private static final int SCHEMAVALIDATE = 8192;
    private static final int SPLITCDATA = 2048;
    private static final int VALIDATE = 4096;
    private static final int WELLFORMED = 16384;
    private static final int XMLDECL = 262144;
    private static final String XMLNS_PREFIX = "xmlns";
    private static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
    private static final String XML_PREFIX = "xml";
    private static final String XML_URI = "http://www.w3.org/XML/1998/namespace";
    private static final Hashtable s_propKeys = new Hashtable();
    private Properties fDOMConfigProperties = null;
    private int fElementDepth = 0;
    private DOMErrorHandler fErrorHandler = null;
    private int fFeatures = 0;
    private LSSerializerFilter fFilter = null;
    private boolean fInEntityRef = false;
    private boolean fIsLevel3DOM = false;
    private boolean fIsXMLVersion11 = false;
    private LexicalHandler fLexicalHandler = null;
    protected NamespaceSupport fLocalNSBinder;
    private LocatorImpl fLocator = new LocatorImpl();
    protected NamespaceSupport fNSBinder;
    private String fNewLine = null;
    boolean fNextIsRaw = false;
    private SerializationHandler fSerializer = null;
    private int fWhatToShowFilter;
    private String fXMLVersion = null;

    static {
        Integer n = new Integer(2);
        s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", n);
        n = new Integer(8);
        s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}comments", n);
        n = new Integer(32);
        s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", n);
        n = new Integer(64);
        s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}entities", n);
        n = new Integer(256);
        s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", n);
        n = new Integer(512);
        s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", n);
        n = new Integer(2048);
        s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}split-cdata-sections", n);
        n = new Integer(16384);
        s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", n);
        n = new Integer(32768);
        s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}discard-default-content", n);
        s_propKeys.put("{http://www.w3.org/TR/DOM-Level-3-LS}format-pretty-print", "");
        s_propKeys.put("omit-xml-declaration", "");
        s_propKeys.put("{http://xml.apache.org/xerces-2j}xml-version", "");
        s_propKeys.put("encoding", "");
        s_propKeys.put("{http://xml.apache.org/xerces-2j}entities", "");
    }

    DOM3TreeWalker(SerializationHandler object, DOMErrorHandler object2, LSSerializerFilter lSSerializerFilter, String string) {
        this.fSerializer = object;
        this.fErrorHandler = object2;
        this.fFilter = lSSerializerFilter;
        this.fLexicalHandler = null;
        this.fNewLine = string;
        this.fNSBinder = new NamespaceSupport();
        this.fLocalNSBinder = new NamespaceSupport();
        this.fDOMConfigProperties = this.fSerializer.getOutputFormat();
        this.fSerializer.setDocumentLocator(this.fLocator);
        this.initProperties(this.fDOMConfigProperties);
        try {
            object = this.fLocator;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(System.getProperty("user.dir"));
            ((StringBuilder)object2).append(File.separator);
            ((StringBuilder)object2).append("dummy.xsl");
            ((LocatorImpl)object).setSystemId(((StringBuilder)object2).toString());
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
    }

    private final void dispatachChars(Node object) throws SAXException {
        SerializationHandler serializationHandler = this.fSerializer;
        if (serializationHandler != null) {
            serializationHandler.characters((Node)object);
        } else {
            object = ((Text)object).getData();
            this.fSerializer.characters(((String)object).toCharArray(), 0, ((String)object).length());
        }
    }

    protected boolean applyFilter(Node node, int n) {
        LSSerializerFilter lSSerializerFilter = this.fFilter;
        return lSSerializerFilter == null || (this.fWhatToShowFilter & n) == 0 || (n = (int)lSSerializerFilter.acceptNode(node)) != 2 && n != 3;
        {
        }
    }

    protected void checkUnboundPrefixInEntRef(Node node) {
        Node node2 = node.getFirstChild();
        while (node2 != null) {
            Node node3 = node2.getNextSibling();
            if (node2.getNodeType() == 1) {
                Object object;
                Object object2 = node2.getPrefix();
                if (object2 != null && this.fNSBinder.getURI((String)object2) == null) {
                    object = Utils.messages.createMessage("unbound-prefix-in-entity-reference", new Object[]{node.getNodeName(), node2.getNodeName(), object2});
                    object2 = this.fErrorHandler;
                    if (object2 != null) {
                        object2.handleError(new DOMErrorImpl(3, (String)object, "unbound-prefix-in-entity-reference", null, null, null));
                    }
                }
                object2 = node2.getAttributes();
                for (int i = 0; i < object2.getLength(); ++i) {
                    object = object2.item(i).getPrefix();
                    if (object == null || this.fNSBinder.getURI((String)object) != null) continue;
                    String string = Utils.messages.createMessage("unbound-prefix-in-entity-reference", new Object[]{node.getNodeName(), node2.getNodeName(), object2.item(i)});
                    object = this.fErrorHandler;
                    if (object == null) continue;
                    object.handleError(new DOMErrorImpl(3, string, "unbound-prefix-in-entity-reference", null, null, null));
                }
            }
            if (node2.hasChildNodes()) {
                this.checkUnboundPrefixInEntRef(node2);
            }
            node2 = node3;
        }
    }

    protected void endNode(Node node) throws SAXException {
        short s = node.getNodeType();
        if (s != 1) {
            if (s != 4) {
                if (s != 5) {
                    if (s != 9 && s == 10) {
                        this.serializeDocType((DocumentType)node, false);
                    }
                } else {
                    this.serializeEntityReference((EntityReference)node, false);
                }
            }
        } else {
            this.serializeElement((Element)node, false);
        }
    }

    protected void fixupElementNS(Node object) throws SAXException {
        String string = ((Element)object).getNamespaceURI();
        Object object2 = ((Element)object).getPrefix();
        CharSequence charSequence = ((Element)object).getLocalName();
        if (string != null) {
            if (object2 == null) {
                object2 = "";
            }
            if ((charSequence = this.fNSBinder.getURI((String)object2)) == null || !((String)charSequence).equals(string)) {
                if ((this.fFeatures & 512) != 0) {
                    if (!"".equals(object2) && !"".equals(string)) {
                        object = (Element)object;
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("xmlns:");
                        ((StringBuilder)charSequence).append((String)object2);
                        object.setAttributeNS(XMLNS_URI, ((StringBuilder)charSequence).toString(), string);
                    } else {
                        ((Element)object).setAttributeNS(XMLNS_URI, XMLNS_PREFIX, string);
                    }
                }
                this.fLocalNSBinder.declarePrefix((String)object2, string);
                this.fNSBinder.declarePrefix((String)object2, string);
            }
        } else if (charSequence != null && !"".equals(charSequence)) {
            object2 = this.fNSBinder.getURI("");
            if (object2 != null && ((String)object2).length() > 0) {
                ((Element)object).setAttributeNS(XMLNS_URI, XMLNS_PREFIX, "");
                this.fLocalNSBinder.declarePrefix("", "");
                this.fNSBinder.declarePrefix("", "");
            }
        } else {
            object = Utils.messages.createMessage("ER_NULL_LOCAL_ELEMENT_NAME", new Object[]{object.getNodeName()});
            object2 = this.fErrorHandler;
            if (object2 != null) {
                object2.handleError(new DOMErrorImpl(2, (String)object, "ER_NULL_LOCAL_ELEMENT_NAME", null, null, null));
            }
        }
    }

    protected void initProperties(Properties object) {
        Enumeration enumeration = ((Hashtable)object).keys();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            Object object2 = s_propKeys.get(string);
            if (object2 == null) continue;
            if (object2 instanceof Integer) {
                int n = (Integer)object2;
                if (((Properties)object).getProperty(string).endsWith("yes")) {
                    this.fFeatures |= n;
                    continue;
                }
                this.fFeatures &= n;
                continue;
            }
            if ("{http://www.w3.org/TR/DOM-Level-3-LS}format-pretty-print".equals(string)) {
                if (((Properties)object).getProperty(string).endsWith("yes")) {
                    this.fSerializer.setIndent(true);
                    this.fSerializer.setIndentAmount(3);
                    continue;
                }
                this.fSerializer.setIndent(false);
                continue;
            }
            if ("omit-xml-declaration".equals(string)) {
                if (((Properties)object).getProperty(string).endsWith("yes")) {
                    this.fSerializer.setOmitXMLDeclaration(true);
                    continue;
                }
                this.fSerializer.setOmitXMLDeclaration(false);
                continue;
            }
            if ("{http://xml.apache.org/xerces-2j}xml-version".equals(string)) {
                object2 = ((Properties)object).getProperty(string);
                if ("1.1".equals(object2)) {
                    this.fIsXMLVersion11 = true;
                    this.fSerializer.setVersion((String)object2);
                    continue;
                }
                this.fSerializer.setVersion("1.0");
                continue;
            }
            if ("encoding".equals(string)) {
                object2 = ((Properties)object).getProperty(string);
                if (object2 == null) continue;
                this.fSerializer.setEncoding((String)object2);
                continue;
            }
            if (!"{http://xml.apache.org/xerces-2j}entities".equals(string)) continue;
            if (((Properties)object).getProperty(string).endsWith("yes")) {
                this.fSerializer.setDTDEntityExpansion(false);
                continue;
            }
            this.fSerializer.setDTDEntityExpansion(true);
        }
        object = this.fNewLine;
        if (object != null) {
            this.fSerializer.setOutputProperty("{http://xml.apache.org/xalan}line-separator", (String)object);
        }
    }

    protected void isAttributeWellFormed(Node object) {
        Object object2;
        Object object3;
        boolean bl = (this.fFeatures & 256) != 0 ? this.isValidQName(object.getPrefix(), object.getLocalName(), this.fIsXMLVersion11) : this.isXMLName(object.getNodeName(), this.fIsXMLVersion11);
        if (!bl) {
            object3 = Utils.messages.createMessage("wf-invalid-character-in-node-name", new Object[]{"Attr", object.getNodeName()});
            object2 = this.fErrorHandler;
            if (object2 != null) {
                object2.handleError(new DOMErrorImpl(3, (String)object3, "wf-invalid-character-in-node-name", null, null, null));
            }
        }
        if (object.getNodeValue().indexOf(60) >= 0) {
            object2 = Utils.messages.createMessage("ER_WF_LT_IN_ATTVAL", new Object[]{((Attr)object).getOwnerElement().getNodeName(), object.getNodeName()});
            object3 = this.fErrorHandler;
            if (object3 != null) {
                object3.handleError(new DOMErrorImpl(3, (String)object2, "ER_WF_LT_IN_ATTVAL", null, null, null));
            }
        }
        object = object.getChildNodes();
        for (int i = 0; i < object.getLength(); ++i) {
            object3 = object.item(i);
            if (object3 == null) continue;
            short s = object3.getNodeType();
            if (s != 3) {
                if (s != 5) continue;
                this.isEntityReferneceWellFormed((EntityReference)object3);
                continue;
            }
            this.isTextWellFormed((Text)object3);
        }
    }

    protected void isCDATASectionWellFormed(CDATASection object) {
        if ((object = this.isWFXMLChar(object.getData())) != null) {
            String string = Utils.messages.createMessage("ER_WF_INVALID_CHARACTER_IN_CDATA", new Object[]{Integer.toHexString(Character.getNumericValue(((Character)object).charValue()))});
            object = this.fErrorHandler;
            if (object != null) {
                object.handleError(new DOMErrorImpl(3, string, "wf-invalid-character", null, null, null));
            }
        }
    }

    protected void isCommentWellFormed(String arrc) {
        if (arrc != null && arrc.length() != 0) {
            arrc = arrc.toCharArray();
            int n = arrc.length;
            if (this.fIsXMLVersion11) {
                int n2 = 0;
                while (n2 < n) {
                    Object object;
                    Object object2;
                    int n3 = n2 + 1;
                    char c = arrc[n2];
                    if (XML11Char.isXML11Invalid(c)) {
                        n2 = n3;
                        if (XMLChar.isHighSurrogate(c)) {
                            n2 = n3;
                            if (n3 < n) {
                                n2 = n3 + 1;
                                char c2 = arrc[n3];
                                if (XMLChar.isLowSurrogate(c2) && XMLChar.isSupplemental(XMLChar.supplemental(c, c2))) continue;
                            }
                        }
                        object2 = Utils.messages.createMessage("ER_WF_INVALID_CHARACTER_IN_COMMENT", new Object[]{new Character(c)});
                        object = this.fErrorHandler;
                        if (object == null) continue;
                        object.handleError(new DOMErrorImpl(3, (String)object2, "wf-invalid-character", null, null, null));
                        continue;
                    }
                    if (c == '-' && n3 < n && arrc[n3] == '-') {
                        object = Utils.messages.createMessage("ER_WF_DASH_IN_COMMENT", null);
                        object2 = this.fErrorHandler;
                        if (object2 != null) {
                            object2.handleError(new DOMErrorImpl(3, (String)object, "wf-invalid-character", null, null, null));
                        }
                    }
                    n2 = n3;
                }
            } else {
                int n4 = 0;
                while (n4 < n) {
                    Object object;
                    Object object3;
                    int n5 = n4 + 1;
                    char c = arrc[n4];
                    if (XMLChar.isInvalid(c)) {
                        n4 = n5;
                        if (XMLChar.isHighSurrogate(c)) {
                            n4 = n5;
                            if (n5 < n) {
                                n4 = n5 + 1;
                                char c3 = arrc[n5];
                                if (XMLChar.isLowSurrogate(c3) && XMLChar.isSupplemental(XMLChar.supplemental(c, c3))) continue;
                            }
                        }
                        object3 = Utils.messages.createMessage("ER_WF_INVALID_CHARACTER_IN_COMMENT", new Object[]{new Character(c)});
                        object = this.fErrorHandler;
                        if (object == null) continue;
                        object.handleError(new DOMErrorImpl(3, (String)object3, "wf-invalid-character", null, null, null));
                        continue;
                    }
                    if (c == '-' && n5 < n && arrc[n5] == '-') {
                        object = Utils.messages.createMessage("ER_WF_DASH_IN_COMMENT", null);
                        object3 = this.fErrorHandler;
                        if (object3 != null) {
                            object3.handleError(new DOMErrorImpl(3, (String)object, "wf-invalid-character", null, null, null));
                        }
                    }
                    n4 = n5;
                }
            }
            return;
        }
    }

    protected void isElementWellFormed(Node object) {
        boolean bl = (this.fFeatures & 256) != 0 ? this.isValidQName(object.getPrefix(), object.getLocalName(), this.fIsXMLVersion11) : this.isXMLName(object.getNodeName(), this.fIsXMLVersion11);
        if (!bl) {
            object = Utils.messages.createMessage("wf-invalid-character-in-node-name", new Object[]{"Element", object.getNodeName()});
            DOMErrorHandler dOMErrorHandler = this.fErrorHandler;
            if (dOMErrorHandler != null) {
                dOMErrorHandler.handleError(new DOMErrorImpl(3, (String)object, "wf-invalid-character-in-node-name", null, null, null));
            }
        }
    }

    protected void isEntityReferneceWellFormed(EntityReference entityReference) {
        Object object;
        Object object2;
        if (!this.isXMLName(entityReference.getNodeName(), this.fIsXMLVersion11)) {
            object = Utils.messages.createMessage("wf-invalid-character-in-node-name", new Object[]{"EntityReference", entityReference.getNodeName()});
            object2 = this.fErrorHandler;
            if (object2 != null) {
                object2.handleError(new DOMErrorImpl(3, (String)object, "wf-invalid-character-in-node-name", null, null, null));
            }
        }
        Node node = entityReference.getParentNode();
        object = entityReference.getOwnerDocument().getDoctype();
        if (object != null) {
            NamedNodeMap namedNodeMap = object.getEntities();
            for (int i = 0; i < namedNodeMap.getLength(); ++i) {
                Entity entity = (Entity)namedNodeMap.item(i);
                object = entityReference.getNodeName();
                String string = "";
                object = object == null ? "" : entityReference.getNodeName();
                object2 = entityReference.getNamespaceURI() == null ? "" : entityReference.getNamespaceURI();
                String string2 = entity.getNodeName() == null ? "" : entity.getNodeName();
                if (entity.getNamespaceURI() != null) {
                    string = entity.getNamespaceURI();
                }
                if (node.getNodeType() == 1 && string.equals(object2) && string2.equals(object) && entity.getNotationName() != null) {
                    String string3 = Utils.messages.createMessage("ER_WF_REF_TO_UNPARSED_ENT", new Object[]{entityReference.getNodeName()});
                    DOMErrorHandler dOMErrorHandler = this.fErrorHandler;
                    if (dOMErrorHandler != null) {
                        dOMErrorHandler.handleError(new DOMErrorImpl(3, string3, "ER_WF_REF_TO_UNPARSED_ENT", null, null, null));
                    }
                }
                if (node.getNodeType() != 2 || !string.equals(object2) || !string2.equals(object) || entity.getPublicId() == null && entity.getSystemId() == null && entity.getNotationName() == null) continue;
                object2 = Utils.messages.createMessage("ER_WF_REF_TO_EXTERNAL_ENT", new Object[]{entityReference.getNodeName()});
                object = this.fErrorHandler;
                if (object == null) continue;
                object.handleError(new DOMErrorImpl(3, (String)object2, "ER_WF_REF_TO_EXTERNAL_ENT", null, null, null));
            }
        }
    }

    protected void isPIWellFormed(ProcessingInstruction object) {
        String string;
        if (!this.isXMLName(object.getNodeName(), this.fIsXMLVersion11)) {
            string = Utils.messages.createMessage("wf-invalid-character-in-node-name", new Object[]{"ProcessingInstruction", object.getTarget()});
            DOMErrorHandler dOMErrorHandler = this.fErrorHandler;
            if (dOMErrorHandler != null) {
                dOMErrorHandler.handleError(new DOMErrorImpl(3, string, "wf-invalid-character-in-node-name", null, null, null));
            }
        }
        if ((object = this.isWFXMLChar(object.getData())) != null) {
            string = Utils.messages.createMessage("ER_WF_INVALID_CHARACTER_IN_PI", new Object[]{Integer.toHexString(Character.getNumericValue(((Character)object).charValue()))});
            object = this.fErrorHandler;
            if (object != null) {
                object.handleError(new DOMErrorImpl(3, string, "wf-invalid-character", null, null, null));
            }
        }
    }

    protected void isTextWellFormed(Text object) {
        if ((object = this.isWFXMLChar(object.getData())) != null) {
            String string = Utils.messages.createMessage("ER_WF_INVALID_CHARACTER_IN_TEXT", new Object[]{Integer.toHexString(Character.getNumericValue(((Character)object).charValue()))});
            object = this.fErrorHandler;
            if (object != null) {
                object.handleError(new DOMErrorImpl(3, string, "wf-invalid-character", null, null, null));
            }
        }
    }

    protected boolean isValidQName(String string, String string2, boolean bl) {
        boolean bl2 = false;
        boolean bl3 = false;
        if (string2 == null) {
            return false;
        }
        bl = !bl ? ((string == null || XMLChar.isValidNCName(string)) && XMLChar.isValidNCName(string2) ? true : bl3) : ((string == null || XML11Char.isXML11ValidNCName(string)) && XML11Char.isXML11ValidNCName(string2) ? true : bl2);
        return bl;
    }

    protected Character isWFXMLChar(String arrc) {
        if (arrc != null && arrc.length() != 0) {
            arrc = arrc.toCharArray();
            int n = arrc.length;
            if (this.fIsXMLVersion11) {
                int n2 = 0;
                while (n2 < n) {
                    int n3 = n2 + 1;
                    if (XML11Char.isXML11Invalid(arrc[n2])) {
                        char c;
                        char c2 = arrc[n3 - 1];
                        if (XMLChar.isHighSurrogate(c2) && n3 < n && XMLChar.isLowSurrogate(c = arrc[n3]) && XMLChar.isSupplemental(XMLChar.supplemental(c2, c))) {
                            n2 = n3 + 1;
                            continue;
                        }
                        return new Character(c2);
                    }
                    n2 = n3;
                }
            } else {
                int n4 = 0;
                while (n4 < n) {
                    int n5 = n4 + 1;
                    if (XMLChar.isInvalid(arrc[n4])) {
                        char c;
                        char c3 = arrc[n5 - 1];
                        if (XMLChar.isHighSurrogate(c3) && n5 < n && XMLChar.isLowSurrogate(c = arrc[n5]) && XMLChar.isSupplemental(XMLChar.supplemental(c3, c))) {
                            n4 = n5 + 1;
                            continue;
                        }
                        return new Character(c3);
                    }
                    n4 = n5;
                }
            }
            return null;
        }
        return null;
    }

    protected boolean isWFXMLChar(String arrc, Character c) {
        if (arrc != null && arrc.length() != 0) {
            arrc = arrc.toCharArray();
            int n = arrc.length;
            if (this.fIsXMLVersion11) {
                int n2 = 0;
                while (n2 < n) {
                    int n3 = n2 + 1;
                    if (XML11Char.isXML11Invalid(arrc[n2])) {
                        char c2;
                        char c3 = arrc[n3 - 1];
                        if (XMLChar.isHighSurrogate(c3) && n3 < n && XMLChar.isLowSurrogate(c2 = arrc[n3]) && XMLChar.isSupplemental(XMLChar.supplemental(c3, c2))) {
                            n2 = n3 + 1;
                            continue;
                        }
                        new Character(c3);
                        return false;
                    }
                    n2 = n3;
                }
            } else {
                int n4 = 0;
                while (n4 < n) {
                    int n5 = n4 + 1;
                    if (XMLChar.isInvalid(arrc[n4])) {
                        char c4;
                        char c5 = arrc[n5 - 1];
                        if (XMLChar.isHighSurrogate(c5) && n5 < n && XMLChar.isLowSurrogate(c4 = arrc[n5]) && XMLChar.isSupplemental(XMLChar.supplemental(c5, c4))) {
                            n4 = n5 + 1;
                            continue;
                        }
                        new Character(c5);
                        return false;
                    }
                    n4 = n5;
                }
            }
            return true;
        }
        return true;
    }

    protected boolean isXMLName(String string, boolean bl) {
        if (string == null) {
            return false;
        }
        if (!bl) {
            return XMLChar.isValidName(string);
        }
        return XML11Char.isXML11ValidName(string);
    }

    protected void recordLocalNSDecl(Node object) {
        NamedNodeMap namedNodeMap = ((Element)object).getAttributes();
        int n = namedNodeMap.getLength();
        for (int i = 0; i < n; ++i) {
            Object object2 = namedNodeMap.item(i);
            object = object2.getLocalName();
            Object object3 = object2.getPrefix();
            String string = object2.getNodeValue();
            object2 = object2.getNamespaceURI();
            if (object == null || XMLNS_PREFIX.equals(object)) {
                object = "";
            }
            if (object3 == null) {
                object3 = "";
            }
            if (string == null) {
                string = "";
            }
            if (object2 == null) {
                object2 = "";
            }
            if (!XMLNS_URI.equals(object2)) continue;
            if (XMLNS_URI.equals(string)) {
                object = Utils.messages.createMessage("ER_NS_PREFIX_CANNOT_BE_BOUND", new Object[]{object3, XMLNS_URI});
                object3 = this.fErrorHandler;
                if (object3 == null) continue;
                object3.handleError(new DOMErrorImpl(2, (String)object, "ER_NS_PREFIX_CANNOT_BE_BOUND", null, null, null));
                continue;
            }
            if (XMLNS_PREFIX.equals(object3)) {
                if (string.length() == 0) continue;
                this.fNSBinder.declarePrefix((String)object, string);
                continue;
            }
            this.fNSBinder.declarePrefix("", string);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void serializeAttList(Element var1_1) throws SAXException {
        var2_2 = var1_1.getAttributes();
        var3_3 = var2_2.getLength();
        var4_4 = 0;
        while (var4_4 < var3_3) {
            block29 : {
                block27 : {
                    block28 : {
                        var5_5 = var2_2.item(var4_4);
                        var6_6 = var5_5.getLocalName();
                        var7_7 = var5_5.getNodeName();
                        var8_8 = var5_5.getPrefix() == null ? "" : var5_5.getPrefix();
                        var9_9 = var5_5.getNodeValue();
                        var1_1 = null;
                        if (this.fIsLevel3DOM) {
                            var1_1 = ((Attr)var5_5).getSchemaTypeInfo().getTypeName();
                        }
                        var10_10 = var1_1 == null ? "CDATA" : var1_1;
                        var11_11 = var5_5.getNamespaceURI();
                        if (var11_11 != null && var11_11.length() == 0) {
                            var1_1 = var5_5.getLocalName();
                            var11_11 = null;
                        } else {
                            var1_1 = var7_7;
                        }
                        var12_12 = ((Attr)var5_5).getSpecified();
                        var13_13 = 0;
                        var14_14 = var1_1.equals("xmlns") || var1_1.startsWith("xmlns:");
                        if ((this.fFeatures & 16384) != 0) {
                            this.isAttributeWellFormed(var5_5);
                        }
                        if ((this.fFeatures & 256) == 0 || var14_14) break block27;
                        if (var11_11 == null) break block28;
                        if (var8_8 == null) {
                            var8_8 = "";
                        }
                        var7_7 = this.fNSBinder.getPrefix(var11_11);
                        var15_15 = this.fNSBinder.getURI((String)var8_8);
                        if (!"".equals(var8_8) && !"".equals(var7_7) && var8_8.equals(var7_7)) ** GOTO lbl61
                        if (var7_7 != null && !"".equals(var7_7)) {
                            var8_8 = var7_7;
                            if (var7_7.length() > 0) {
                                var1_1 = new StringBuilder();
                                var1_1.append((String)var7_7);
                                var1_1.append(":");
                                var1_1.append(var6_6);
                                var1_1 = var1_1.toString();
                                var13_13 = 0;
                            } else {
                                var1_1 = var6_6;
                                var13_13 = 0;
                            }
                        } else if (!"".equals(var8_8) && var15_15 == null) {
                            if ((this.fFeatures & 512) != 0) {
                                var15_15 = this.fSerializer;
                                var7_7 = new StringBuilder();
                                var7_7.append("xmlns:");
                                var7_7.append((String)var8_8);
                                var15_15.addAttribute("http://www.w3.org/2000/xmlns/", (String)var8_8, var7_7.toString(), "CDATA", var11_11);
                                this.fNSBinder.declarePrefix((String)var8_8, var11_11);
                                this.fLocalNSBinder.declarePrefix((String)var8_8, var11_11);
                            }
lbl61: // 4 sources:
                            var13_13 = 0;
                        } else {
                            var1_1 = new StringBuilder();
                            var1_1.append("NS");
                            var1_1.append(1);
                            var1_1 = var1_1.toString();
                            var16_16 = 1 + 1;
                            while (this.fLocalNSBinder.getURI((String)var1_1) != null) {
                                var1_1 = new StringBuilder();
                                var1_1.append("NS");
                                var1_1.append(var16_16);
                                var1_1 = var1_1.toString();
                                ++var16_16;
                            }
                            var8_8 = new StringBuilder();
                            var8_8.append((String)var1_1);
                            var8_8.append(":");
                            var8_8.append(var6_6);
                            var7_7 = var8_8.toString();
                            if ((this.fFeatures & 512) != 0) {
                                var8_8 = this.fSerializer;
                                var15_15 = new StringBuilder();
                                var15_15.append("xmlns:");
                                var15_15.append((String)var1_1);
                                var8_8.addAttribute("http://www.w3.org/2000/xmlns/", (String)var1_1, var15_15.toString(), "CDATA", var11_11);
                                this.fNSBinder.declarePrefix((String)var1_1, var11_11);
                                this.fLocalNSBinder.declarePrefix((String)var1_1, var11_11);
                            }
                            var8_8 = var1_1;
                            var1_1 = var7_7;
                        }
                        break block29;
                    }
                    if (var6_6 == null) {
                        var7_7 = Utils.messages.createMessage("ER_NULL_LOCAL_ELEMENT_NAME", new Object[]{var1_1});
                        var8_8 = this.fErrorHandler;
                        if (var8_8 != null) {
                            var8_8.handleError(new DOMErrorImpl(2, (String)var7_7, "ER_NULL_LOCAL_ELEMENT_NAME", null, null, null));
                        }
                    }
                }
                var13_13 = 0;
            }
            if ((this.fFeatures & 32768) != 0 && var12_12 || (this.fFeatures & 32768) == 0) {
                var16_16 = 1;
                var13_13 = 1;
            } else {
                var17_17 = 0;
                var16_16 = var13_13;
                var13_13 = var17_17;
            }
            if (!(var16_16 == 0 || (var8_8 = this.fFilter) == null || (var8_8.getWhatToShow() & 2) == 0 || var14_14 || (var16_16 = (int)this.fFilter.acceptNode(var5_5)) != 2 && var16_16 != 3)) {
                var13_13 = 0;
            }
            if (var13_13 != 0 && var14_14) {
                if ((this.fFeatures & 512) != 0 && var6_6 != null && !"".equals(var6_6)) {
                    this.fSerializer.addAttribute(var11_11, var6_6, (String)var1_1, (String)var10_10, var9_9);
                }
            } else if (var13_13 != 0 && !var14_14) {
                if ((this.fFeatures & 512) != 0 && var11_11 != null) {
                    this.fSerializer.addAttribute(var11_11, var6_6, (String)var1_1, (String)var10_10, var9_9);
                } else {
                    this.fSerializer.addAttribute("", var6_6, (String)var1_1, (String)var10_10, var9_9);
                }
            }
            if (var14_14 && (this.fFeatures & 512) != 0 && !"".equals(var1_1 = (var13_13 = var1_1.indexOf(":")) < 0 ? "" : var1_1.substring(var13_13 + 1))) {
                this.fSerializer.namespaceAfterStartElement((String)var1_1, var9_9);
            }
            ++var4_4;
        }
    }

    protected void serializeCDATASection(CDATASection object) throws SAXException {
        if ((this.fFeatures & 16384) != 0) {
            this.isCDATASectionWellFormed((CDATASection)object);
        }
        if ((this.fFeatures & 2) != 0) {
            Object object2 = object.getNodeValue();
            int n = ((String)object2).indexOf("]]>");
            if ((this.fFeatures & 2048) != 0) {
                if (n >= 0) {
                    String string = ((String)object2).substring(0, n + 2);
                    object2 = Utils.messages.createMessage("cdata-sections-splitted", null);
                    DOMErrorHandler dOMErrorHandler = this.fErrorHandler;
                    if (dOMErrorHandler != null) {
                        dOMErrorHandler.handleError(new DOMErrorImpl(1, (String)object2, "cdata-sections-splitted", null, string, null));
                    }
                }
            } else if (n >= 0) {
                ((String)object2).substring(0, n + 2);
                object = Utils.messages.createMessage("cdata-sections-splitted", null);
                object2 = this.fErrorHandler;
                if (object2 != null) {
                    object2.handleError(new DOMErrorImpl(2, (String)object, "cdata-sections-splitted"));
                }
                return;
            }
            if (!this.applyFilter((Node)object, 8)) {
                return;
            }
            object2 = this.fLexicalHandler;
            if (object2 != null) {
                object2.startCDATA();
            }
            this.dispatachChars((Node)object);
            object = this.fLexicalHandler;
            if (object != null) {
                object.endCDATA();
            }
        } else {
            this.dispatachChars((Node)object);
        }
    }

    protected void serializeComment(Comment comment) throws SAXException {
        if ((this.fFeatures & 8) != 0) {
            String string = comment.getData();
            if ((this.fFeatures & 16384) != 0) {
                this.isCommentWellFormed(string);
            }
            if (this.fLexicalHandler != null) {
                if (!this.applyFilter(comment, 128)) {
                    return;
                }
                this.fLexicalHandler.comment(string.toCharArray(), 0, string.length());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void serializeDocType(DocumentType object, boolean bl) throws SAXException {
        String string = object.getNodeName();
        String string2 = object.getPublicId();
        String string3 = object.getSystemId();
        String string4 = object.getInternalSubset();
        if (string4 != null && !"".equals(string4)) {
            if (!bl) return;
            try {
                Writer writer = this.fSerializer.getWriter();
                object = new StringBuffer();
                ((StringBuffer)object).append("<!DOCTYPE ");
                ((StringBuffer)object).append(string);
                if (string2 != null) {
                    ((StringBuffer)object).append(" PUBLIC \"");
                    ((StringBuffer)object).append(string2);
                    ((StringBuffer)object).append('\"');
                }
                if (string3 != null) {
                    if (string2 == null) {
                        ((StringBuffer)object).append(" SYSTEM \"");
                    } else {
                        ((StringBuffer)object).append(" \"");
                    }
                    ((StringBuffer)object).append(string3);
                    ((StringBuffer)object).append('\"');
                }
                ((StringBuffer)object).append(" [ ");
                ((StringBuffer)object).append(this.fNewLine);
                ((StringBuffer)object).append(string4);
                ((StringBuffer)object).append("]>");
                string3 = new String(this.fNewLine);
                ((StringBuffer)object).append(string3);
                writer.write(((StringBuffer)object).toString());
                writer.flush();
                return;
            }
            catch (IOException iOException) {
                throw new SAXException(Utils.messages.createMessage("ER_WRITING_INTERNAL_SUBSET", null), iOException);
            }
        }
        if (bl) {
            object = this.fLexicalHandler;
            if (object == null) return;
            object.startDTD(string, string2, string3);
            return;
        }
        object = this.fLexicalHandler;
        if (object == null) return;
        object.endDTD();
    }

    protected void serializeElement(Element element, boolean bl) throws SAXException {
        if (bl) {
            ++this.fElementDepth;
            if ((this.fFeatures & 16384) != 0) {
                this.isElementWellFormed(element);
            }
            if (!this.applyFilter(element, 1)) {
                return;
            }
            if ((this.fFeatures & 256) != 0) {
                this.fNSBinder.pushContext();
                this.fLocalNSBinder.reset();
                this.recordLocalNSDecl(element);
                this.fixupElementNS(element);
            }
            this.fSerializer.startElement(element.getNamespaceURI(), element.getLocalName(), element.getNodeName());
            this.serializeAttList(element);
        } else {
            --this.fElementDepth;
            if (!this.applyFilter(element, 1)) {
                return;
            }
            this.fSerializer.endElement(element.getNamespaceURI(), element.getLocalName(), element.getNodeName());
            if ((this.fFeatures & 256) != 0) {
                this.fNSBinder.popContext();
            }
        }
    }

    protected void serializeEntityReference(EntityReference entityReference, boolean bl) throws SAXException {
        if (bl) {
            LexicalHandler lexicalHandler;
            int n = this.fFeatures;
            if ((n & 64) != 0) {
                if ((n & 16384) != 0) {
                    this.isEntityReferneceWellFormed(entityReference);
                }
                if ((this.fFeatures & 256) != 0) {
                    this.checkUnboundPrefixInEntRef(entityReference);
                }
            }
            if ((lexicalHandler = this.fLexicalHandler) != null) {
                lexicalHandler.startEntity(entityReference.getNodeName());
            }
        } else {
            LexicalHandler lexicalHandler = this.fLexicalHandler;
            if (lexicalHandler != null) {
                lexicalHandler.endEntity(entityReference.getNodeName());
            }
        }
    }

    protected void serializePI(ProcessingInstruction processingInstruction) throws SAXException {
        String string = processingInstruction.getNodeName();
        if ((this.fFeatures & 16384) != 0) {
            this.isPIWellFormed(processingInstruction);
        }
        if (!this.applyFilter(processingInstruction, 64)) {
            return;
        }
        if (string.equals("xslt-next-is-raw")) {
            this.fNextIsRaw = true;
        } else {
            this.fSerializer.processingInstruction(string, processingInstruction.getData());
        }
    }

    protected void serializeText(Text text) throws SAXException {
        if (this.fNextIsRaw) {
            this.fNextIsRaw = false;
            this.fSerializer.processingInstruction("javax.xml.transform.disable-output-escaping", "");
            this.dispatachChars(text);
            this.fSerializer.processingInstruction("javax.xml.transform.enable-output-escaping", "");
        } else {
            boolean bl = false;
            if ((this.fFeatures & 16384) != 0) {
                this.isTextWellFormed(text);
            }
            boolean bl2 = false;
            if (this.fIsLevel3DOM) {
                bl2 = text.isElementContentWhitespace();
            }
            if (bl2) {
                if ((this.fFeatures & 32) != 0) {
                    bl = true;
                }
            } else {
                bl = true;
            }
            if (!this.applyFilter(text, 4)) {
                return;
            }
            if (bl) {
                this.dispatachChars(text);
            }
        }
    }

    protected void startNode(Node node) throws SAXException {
        if (node instanceof Locator) {
            Locator locator = (Locator)((Object)node);
            this.fLocator.setColumnNumber(locator.getColumnNumber());
            this.fLocator.setLineNumber(locator.getLineNumber());
            this.fLocator.setPublicId(locator.getPublicId());
            this.fLocator.setSystemId(locator.getSystemId());
        } else {
            this.fLocator.setColumnNumber(0);
            this.fLocator.setLineNumber(0);
        }
        switch (node.getNodeType()) {
            default: {
                break;
            }
            case 11: {
                break;
            }
            case 10: {
                this.serializeDocType((DocumentType)node, true);
                break;
            }
            case 9: {
                break;
            }
            case 8: {
                this.serializeComment((Comment)node);
                break;
            }
            case 7: {
                this.serializePI((ProcessingInstruction)node);
                break;
            }
            case 5: {
                this.serializeEntityReference((EntityReference)node, true);
                break;
            }
            case 4: {
                this.serializeCDATASection((CDATASection)node);
                break;
            }
            case 3: {
                this.serializeText((Text)node);
                break;
            }
            case 1: {
                this.serializeElement((Element)node, true);
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void traverse(Node var1_1) throws SAXException {
        this.fSerializer.startDocument();
        if (var1_1.getNodeType() != 9) {
            var2_2 = var1_1.getOwnerDocument();
            if (var2_2 != null && var2_2.getImplementation().hasFeature("Core", "3.0")) {
                this.fIsLevel3DOM = true;
            }
        } else if (((Document)var1_1).getImplementation().hasFeature("Core", "3.0")) {
            this.fIsLevel3DOM = true;
        }
        var2_2 = this.fSerializer;
        if (var2_2 instanceof LexicalHandler) {
            this.fLexicalHandler = var2_2;
        }
        if ((var2_2 = this.fFilter) != null) {
            this.fWhatToShowFilter = var2_2.getWhatToShow();
        }
        var2_2 = var1_1;
        do {
            block11 : {
                if (var2_2 == null) {
                    this.fSerializer.endDocument();
                    return;
                }
                this.startNode((Node)var2_2);
                var3_3 = var2_2.getFirstChild();
                var4_4 = var2_2;
                var2_2 = var3_3;
                do lbl-1000: // 3 sources:
                {
                    var3_3 = var2_2;
                    if (var2_2 != null) break block11;
                    this.endNode((Node)var4_4);
                    if (var1_1.equals(var4_4)) {
                        var3_3 = var2_2;
                        break block11;
                    }
                    var5_5 = var4_4.getNextSibling();
                    var2_2 = var5_5;
                    if (var5_5 != null) ** GOTO lbl-1000
                    var3_3 = var4_4.getParentNode();
                    if (var3_3 == null) break;
                    var2_2 = var5_5;
                    var4_4 = var3_3;
                } while (!var1_1.equals(var3_3));
                if (var3_3 != null) {
                    this.endNode((Node)var3_3);
                }
                var3_3 = null;
            }
            var2_2 = var3_3;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void traverse(Node var1_1, Node var2_2) throws SAXException {
        this.fSerializer.startDocument();
        if (var1_1.getNodeType() != 9) {
            var3_3 = var1_1.getOwnerDocument();
            if (var3_3 != null && var3_3.getImplementation().hasFeature("Core", "3.0")) {
                this.fIsLevel3DOM = true;
            }
        } else if (((Document)var1_1).getImplementation().hasFeature("Core", "3.0")) {
            this.fIsLevel3DOM = true;
        }
        var3_3 = this.fSerializer;
        if (var3_3 instanceof LexicalHandler) {
            this.fLexicalHandler = var3_3;
        }
        var4_4 = this.fFilter;
        var3_3 = var1_1;
        if (var4_4 != null) {
            this.fWhatToShowFilter = var4_4.getWhatToShow();
            var3_3 = var1_1;
        }
        block0 : do {
            if (var3_3 == null) {
                this.fSerializer.endDocument();
                return;
            }
            this.startNode((Node)var3_3);
            var1_1 = var3_3.getFirstChild();
            var4_4 = var3_3;
            do lbl-1000: // 4 sources:
            {
                var3_3 = var1_1;
                if (var1_1 != null) continue block0;
                this.endNode((Node)var4_4);
                if (var2_2 != null && var2_2.equals(var4_4)) {
                    var3_3 = var1_1;
                    continue block0;
                }
                var1_1 = var5_5 = var4_4.getNextSibling();
                if (var5_5 != null) ** GOTO lbl-1000
                var3_3 = var4_4.getParentNode();
                if (var3_3 == null) break;
                var1_1 = var5_5;
                var4_4 = var3_3;
                if (var2_2 == null) ** GOTO lbl-1000
                var1_1 = var5_5;
                var4_4 = var3_3;
            } while (!var2_2.equals(var3_3));
            var3_3 = null;
        } while (true);
    }
}

