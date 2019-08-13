/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import java.util.Vector;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMDOMException;
import org.apache.xml.dtm.ref.DTMChildIterNodeList;
import org.apache.xml.dtm.ref.DTMNamedNodeMap;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.NodeSet;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public class DTMNodeProxy
implements Node,
Document,
Text,
Element,
Attr,
ProcessingInstruction,
Comment,
DocumentFragment {
    private static final String EMPTYSTRING = "";
    static final DOMImplementation implementation = new DTMNodeProxyImplementation();
    protected String actualEncoding;
    public DTM dtm;
    protected String fDocumentURI;
    int node;
    private String xmlEncoding;
    private boolean xmlStandalone;
    private String xmlVersion;

    public DTMNodeProxy(DTM dTM, int n) {
        this.dtm = dTM;
        this.node = n;
    }

    private final void traverseChildren(Vector vector, Node object, String string, String string2, boolean bl, boolean bl2) {
        if (object == null) {
            return;
        }
        if (object.getNodeType() == 1 && (bl2 || object.getLocalName().equals(string2))) {
            String string3 = object.getNamespaceURI();
            if (string == null && string3 == null || bl || string != null && string.equals(string3)) {
                vector.add(object);
            }
        }
        if (object.hasChildNodes()) {
            object = object.getChildNodes();
            for (int i = 0; i < object.getLength(); ++i) {
                this.traverseChildren(vector, object.item(i), string, string2, bl, bl2);
            }
        }
    }

    private final void traverseChildren(Vector vector, Node object, String string, boolean bl) {
        if (object == null) {
            return;
        }
        if (object.getNodeType() == 1 && (bl || object.getNodeName().equals(string))) {
            vector.add(object);
        }
        if (object.hasChildNodes()) {
            object = object.getChildNodes();
            for (int i = 0; i < object.getLength(); ++i) {
                this.traverseChildren(vector, object.item(i), string, bl);
            }
        }
    }

    @Override
    public Node adoptNode(Node node) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final Node appendChild(Node node) throws DOMException {
        throw new DTMDOMException(7);
    }

    @Override
    public final void appendData(String string) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final Node cloneNode(boolean bl) {
        throw new DTMDOMException(9);
    }

    @Override
    public short compareDocumentPosition(Node node) throws DOMException {
        return 0;
    }

    @Override
    public final Attr createAttribute(String string) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final Attr createAttributeNS(String string, String string2) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final CDATASection createCDATASection(String string) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final Comment createComment(String string) {
        throw new DTMDOMException(9);
    }

    @Override
    public final DocumentFragment createDocumentFragment() {
        throw new DTMDOMException(9);
    }

    @Override
    public final Element createElement(String string) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final Element createElementNS(String string, String string2) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final EntityReference createEntityReference(String string) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final ProcessingInstruction createProcessingInstruction(String string, String string2) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final Text createTextNode(String string) {
        throw new DTMDOMException(9);
    }

    @Override
    public final void deleteData(int n, int n2) throws DOMException {
        throw new DTMDOMException(9);
    }

    public final boolean equals(Object object) {
        try {
            boolean bl = this.equals((Node)object);
            return bl;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public final boolean equals(Node object) {
        boolean bl;
        boolean bl2 = false;
        try {
            object = (DTMNodeProxy)object;
            bl = bl2;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
        if (((DTMNodeProxy)object).node == this.node) {
            object = ((DTMNodeProxy)object).dtm;
            DTM dTM = this.dtm;
            bl = bl2;
            if (object == dTM) {
                bl = true;
            }
        }
        return bl;
    }

    public String getActualEncoding() {
        return this.actualEncoding;
    }

    @Override
    public final String getAttribute(String object) {
        object = (object = new DTMNamedNodeMap(this.dtm, this.node).getNamedItem((String)object)) == null ? EMPTYSTRING : object.getNodeValue();
        return object;
    }

    @Override
    public final String getAttributeNS(String object, String string) {
        Object var3_3 = null;
        int n = this.dtm.getAttributeNode(this.node, (String)object, string);
        object = var3_3;
        if (n != -1) {
            object = this.dtm.getNode(n);
        }
        object = object == null ? EMPTYSTRING : object.getNodeValue();
        return object;
    }

    @Override
    public final Attr getAttributeNode(String string) {
        return (Attr)new DTMNamedNodeMap(this.dtm, this.node).getNamedItem(string);
    }

    @Override
    public final Attr getAttributeNodeNS(String object, String string) {
        Object var3_3 = null;
        int n = this.dtm.getAttributeNode(this.node, (String)object, string);
        object = var3_3;
        if (n != -1) {
            object = (Attr)this.dtm.getNode(n);
        }
        return object;
    }

    @Override
    public final NamedNodeMap getAttributes() {
        return new DTMNamedNodeMap(this.dtm, this.node);
    }

    @Override
    public String getBaseURI() {
        return null;
    }

    @Override
    public final NodeList getChildNodes() {
        return new DTMChildIterNodeList(this.dtm, this.node);
    }

    public final DTM getDTM() {
        return this.dtm;
    }

    public final int getDTMNodeNumber() {
        return this.node;
    }

    @Override
    public final String getData() throws DOMException {
        return this.dtm.getNodeValue(this.node);
    }

    @Override
    public final DocumentType getDoctype() {
        return null;
    }

    @Override
    public final Element getDocumentElement() {
        int n = this.dtm.getDocument();
        int n2 = -1;
        int n3 = this.dtm.getFirstChild(n);
        while (n3 != -1) {
            short s = this.dtm.getNodeType(n3);
            if (s != 1) {
                if (s != 10 && s != 7 && s != 8) {
                    n2 = -1;
                    n3 = this.dtm.getLastChild(n);
                }
            } else if (n2 != -1) {
                n2 = -1;
                n3 = this.dtm.getLastChild(n);
            } else {
                n2 = n3;
            }
            n3 = this.dtm.getNextSibling(n3);
        }
        if (n2 != -1) {
            return (Element)this.dtm.getNode(n2);
        }
        throw new DTMDOMException(9);
    }

    @Override
    public String getDocumentURI() {
        return this.fDocumentURI;
    }

    @Override
    public DOMConfiguration getDomConfig() {
        return null;
    }

    @Override
    public final Element getElementById(String string) {
        DTM dTM = this.dtm;
        return (Element)dTM.getNode(dTM.getElementById(string));
    }

    @Override
    public final NodeList getElementsByTagName(String object) {
        int n;
        Vector vector = new Vector();
        Object object2 = this.dtm.getNode(this.node);
        if (object2 != null) {
            boolean bl = "*".equals(object);
            if (1 == object2.getNodeType()) {
                object2 = object2.getChildNodes();
                for (n = 0; n < object2.getLength(); ++n) {
                    this.traverseChildren(vector, object2.item(n), (String)object, bl);
                }
            } else if (9 == object2.getNodeType()) {
                this.traverseChildren(vector, this.dtm.getNode(this.node), (String)object, bl);
            }
        }
        int n2 = vector.size();
        object = new NodeSet(n2);
        for (n = 0; n < n2; ++n) {
            ((NodeSet)object).addNode((Node)vector.elementAt(n));
        }
        return object;
    }

    @Override
    public final NodeList getElementsByTagNameNS(String object, String string) {
        int n;
        Vector vector = new Vector();
        Object object2 = this.dtm.getNode(this.node);
        if (object2 != null) {
            boolean bl = "*".equals(object);
            boolean bl2 = "*".equals(string);
            if (1 == object2.getNodeType()) {
                object2 = object2.getChildNodes();
                for (n = 0; n < object2.getLength(); ++n) {
                    this.traverseChildren(vector, object2.item(n), (String)object, string, bl, bl2);
                }
            } else if (9 == object2.getNodeType()) {
                this.traverseChildren(vector, this.dtm.getNode(this.node), (String)object, string, bl, bl2);
            }
        }
        int n2 = vector.size();
        object = new NodeSet(n2);
        for (n = 0; n < n2; ++n) {
            ((NodeSet)object).addNode((Node)vector.elementAt(n));
        }
        return object;
    }

    @Override
    public Object getFeature(String object, String string) {
        object = this.isSupported((String)object, string) ? this : null;
        return object;
    }

    @Override
    public final Node getFirstChild() {
        int n = this.dtm.getFirstChild(this.node);
        Node node = n == -1 ? null : this.dtm.getNode(n);
        return node;
    }

    @Override
    public final DOMImplementation getImplementation() {
        return implementation;
    }

    @Override
    public String getInputEncoding() {
        throw new DTMDOMException(9);
    }

    @Override
    public final Node getLastChild() {
        int n = this.dtm.getLastChild(this.node);
        Node node = n == -1 ? null : this.dtm.getNode(n);
        return node;
    }

    @Override
    public final int getLength() {
        return this.dtm.getNodeValue(this.node).length();
    }

    @Override
    public final String getLocalName() {
        return this.dtm.getLocalName(this.node);
    }

    @Override
    public final String getName() {
        return this.dtm.getNodeName(this.node);
    }

    @Override
    public final String getNamespaceURI() {
        return this.dtm.getNamespaceURI(this.node);
    }

    @Override
    public final Node getNextSibling() {
        int n = this.dtm.getNodeType(this.node);
        Node node = null;
        if (n == 2) {
            return null;
        }
        n = this.dtm.getNextSibling(this.node);
        if (n != -1) {
            node = this.dtm.getNode(n);
        }
        return node;
    }

    @Override
    public final String getNodeName() {
        return this.dtm.getNodeName(this.node);
    }

    @Override
    public final short getNodeType() {
        return this.dtm.getNodeType(this.node);
    }

    @Override
    public final String getNodeValue() throws DOMException {
        return this.dtm.getNodeValue(this.node);
    }

    @Override
    public final Document getOwnerDocument() {
        DTM dTM = this.dtm;
        return (Document)dTM.getNode(dTM.getOwnerDocument(this.node));
    }

    @Override
    public final Element getOwnerElement() {
        int n = this.getNodeType();
        Element element = null;
        if (n != 2) {
            return null;
        }
        n = this.dtm.getParent(this.node);
        if (n != -1) {
            element = (Element)this.dtm.getNode(n);
        }
        return element;
    }

    public final Node getOwnerNode() {
        int n = this.dtm.getParent(this.node);
        Node node = n == -1 ? null : this.dtm.getNode(n);
        return node;
    }

    @Override
    public final Node getParentNode() {
        int n = this.getNodeType();
        Node node = null;
        if (n == 2) {
            return null;
        }
        n = this.dtm.getParent(this.node);
        if (n != -1) {
            node = this.dtm.getNode(n);
        }
        return node;
    }

    @Override
    public final String getPrefix() {
        return this.dtm.getPrefix(this.node);
    }

    @Override
    public final Node getPreviousSibling() {
        int n = this.dtm.getPreviousSibling(this.node);
        Node node = n == -1 ? null : this.dtm.getNode(n);
        return node;
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return null;
    }

    @Override
    public final boolean getSpecified() {
        return true;
    }

    @Override
    public boolean getStrictErrorChecking() {
        throw new DTMDOMException(9);
    }

    public final String getStringValue() throws DOMException {
        return this.dtm.getStringValue(this.node).toString();
    }

    @Override
    public final String getTagName() {
        return this.dtm.getNodeName(this.node);
    }

    @Override
    public final String getTarget() {
        return this.dtm.getNodeName(this.node);
    }

    @Override
    public String getTextContent() throws DOMException {
        return this.dtm.getStringValue(this.node).toString();
    }

    @Override
    public Object getUserData(String string) {
        return this.getOwnerDocument().getUserData(string);
    }

    @Override
    public final String getValue() {
        return this.dtm.getNodeValue(this.node);
    }

    @Override
    public String getWholeText() {
        return null;
    }

    @Override
    public String getXmlEncoding() {
        return this.xmlEncoding;
    }

    @Override
    public boolean getXmlStandalone() {
        return this.xmlStandalone;
    }

    @Override
    public String getXmlVersion() {
        return this.xmlVersion;
    }

    @Override
    public boolean hasAttribute(String string) {
        boolean bl = -1 != this.dtm.getAttributeNode(this.node, null, string);
        return bl;
    }

    @Override
    public boolean hasAttributeNS(String string, String string2) {
        boolean bl = -1 != this.dtm.getAttributeNode(this.node, string, string2);
        return bl;
    }

    @Override
    public boolean hasAttributes() {
        boolean bl = -1 != this.dtm.getFirstAttribute(this.node);
        return bl;
    }

    @Override
    public final boolean hasChildNodes() {
        boolean bl = -1 != this.dtm.getFirstChild(this.node);
        return bl;
    }

    @Override
    public final Node importNode(Node node, boolean bl) throws DOMException {
        throw new DTMDOMException(7);
    }

    @Override
    public final Node insertBefore(Node node, Node node2) throws DOMException {
        throw new DTMDOMException(7);
    }

    @Override
    public final void insertData(int n, String string) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public boolean isDefaultNamespace(String string) {
        return false;
    }

    @Override
    public boolean isElementContentWhitespace() {
        return false;
    }

    @Override
    public boolean isEqualNode(Node node) {
        if (node == this) {
            return true;
        }
        if (node.getNodeType() != this.getNodeType()) {
            return false;
        }
        if (this.getNodeName() == null ? node.getNodeName() != null : !this.getNodeName().equals(node.getNodeName())) {
            return false;
        }
        if (this.getLocalName() == null ? node.getLocalName() != null : !this.getLocalName().equals(node.getLocalName())) {
            return false;
        }
        if (this.getNamespaceURI() == null ? node.getNamespaceURI() != null : !this.getNamespaceURI().equals(node.getNamespaceURI())) {
            return false;
        }
        if (this.getPrefix() == null ? node.getPrefix() != null : !this.getPrefix().equals(node.getPrefix())) {
            return false;
        }
        return !(this.getNodeValue() == null ? node.getNodeValue() != null : !this.getNodeValue().equals(node.getNodeValue()));
    }

    @Override
    public boolean isId() {
        return false;
    }

    @Override
    public boolean isSameNode(Node node) {
        boolean bl = this == node;
        return bl;
    }

    @Override
    public final boolean isSupported(String string, String string2) {
        return implementation.hasFeature(string, string2);
    }

    @Override
    public String lookupNamespaceURI(String string) {
        int n = this.getNodeType();
        if (n != 1) {
            if (n != 2) {
                if (n != 6) {
                    switch (n) {
                        default: {
                            return null;
                        }
                        case 10: 
                        case 11: 
                        case 12: 
                    }
                }
                return null;
            }
            if (this.getOwnerElement().getNodeType() == 1) {
                return this.getOwnerElement().lookupNamespaceURI(string);
            }
            return null;
        }
        String string2 = this.getNamespaceURI();
        String string3 = this.getPrefix();
        if (string2 != null) {
            if (string == null && string3 == string) {
                return string2;
            }
            if (string3 != null && string3.equals(string)) {
                return string2;
            }
        }
        if (this.hasAttributes()) {
            NamedNodeMap namedNodeMap = this.getAttributes();
            int n2 = namedNodeMap.getLength();
            for (n = 0; n < n2; ++n) {
                Node node = namedNodeMap.item(n);
                string2 = node.getPrefix();
                String string4 = node.getNodeValue();
                string3 = node.getNamespaceURI();
                if (string3 == null || !string3.equals("http://www.w3.org/2000/xmlns/")) continue;
                if (string == null && node.getNodeName().equals("xmlns")) {
                    return string4;
                }
                if (string2 == null || !string2.equals("xmlns") || !node.getLocalName().equals(string)) continue;
                return string4;
            }
        }
        return null;
    }

    @Override
    public String lookupPrefix(String string) {
        if (string == null) {
            return null;
        }
        short s = this.getNodeType();
        if (s != 2) {
            if (s != 6) {
                switch (s) {
                    default: {
                        return null;
                    }
                    case 10: 
                    case 11: 
                    case 12: 
                }
            }
            return null;
        }
        if (this.getOwnerElement().getNodeType() == 1) {
            return this.getOwnerElement().lookupPrefix(string);
        }
        return null;
    }

    @Override
    public final void normalize() {
        throw new DTMDOMException(9);
    }

    @Override
    public void normalizeDocument() {
    }

    @Override
    public final void removeAttribute(String string) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final void removeAttributeNS(String string, String string2) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final Attr removeAttributeNode(Attr attr) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final Node removeChild(Node node) throws DOMException {
        throw new DTMDOMException(7);
    }

    @Override
    public Node renameNode(Node node, String string, String string2) throws DOMException {
        return node;
    }

    @Override
    public final Node replaceChild(Node node, Node node2) throws DOMException {
        throw new DTMDOMException(7);
    }

    @Override
    public final void replaceData(int n, int n2, String string) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public Text replaceWholeText(String string) throws DOMException {
        return null;
    }

    public final boolean sameNodeAs(Node node) {
        boolean bl = node instanceof DTMNodeProxy;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        node = (DTMNodeProxy)node;
        bl = bl2;
        if (this.dtm == ((DTMNodeProxy)node).dtm) {
            bl = bl2;
            if (this.node == ((DTMNodeProxy)node).node) {
                bl = true;
            }
        }
        return bl;
    }

    public void setActualEncoding(String string) {
        this.actualEncoding = string;
    }

    @Override
    public final void setAttribute(String string, String string2) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final void setAttributeNS(String string, String string2, String string3) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final Attr setAttributeNode(Attr attr) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final Attr setAttributeNodeNS(Attr attr) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final void setData(String string) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public void setDocumentURI(String string) {
        this.fDocumentURI = string;
    }

    @Override
    public void setIdAttribute(String string, boolean bl) {
    }

    public void setIdAttribute(boolean bl) {
    }

    @Override
    public void setIdAttributeNS(String string, String string2, boolean bl) {
    }

    @Override
    public void setIdAttributeNode(Attr attr, boolean bl) {
    }

    @Override
    public final void setNodeValue(String string) throws DOMException {
        throw new DTMDOMException(7);
    }

    @Override
    public final void setPrefix(String string) throws DOMException {
        throw new DTMDOMException(7);
    }

    @Override
    public void setStrictErrorChecking(boolean bl) {
        throw new DTMDOMException(9);
    }

    @Override
    public void setTextContent(String string) throws DOMException {
        this.setNodeValue(string);
    }

    @Override
    public Object setUserData(String string, Object object, UserDataHandler userDataHandler) {
        return this.getOwnerDocument().setUserData(string, object, userDataHandler);
    }

    @Override
    public final void setValue(String string) {
        throw new DTMDOMException(9);
    }

    public void setXmlEncoding(String string) {
        this.xmlEncoding = string;
    }

    @Override
    public void setXmlStandalone(boolean bl) throws DOMException {
        this.xmlStandalone = bl;
    }

    @Override
    public void setXmlVersion(String string) throws DOMException {
        this.xmlVersion = string;
    }

    @Override
    public final Text splitText(int n) throws DOMException {
        throw new DTMDOMException(9);
    }

    @Override
    public final String substringData(int n, int n2) throws DOMException {
        return this.getData().substring(n, n + n2);
    }

    public final boolean supports(String string, String string2) {
        return implementation.hasFeature(string, string2);
    }

    static class DTMNodeProxyImplementation
    implements DOMImplementation {
        DTMNodeProxyImplementation() {
        }

        @Override
        public Document createDocument(String string, String string2, DocumentType documentType) {
            throw new DTMDOMException(9);
        }

        @Override
        public DocumentType createDocumentType(String string, String string2, String string3) {
            throw new DTMDOMException(9);
        }

        @Override
        public Object getFeature(String string, String string2) {
            return null;
        }

        @Override
        public boolean hasFeature(String string, String string2) {
            return !(!"CORE".equals(string.toUpperCase()) && !"XML".equals(string.toUpperCase()) || !"1.0".equals(string2) && !"2.0".equals(string2));
            {
            }
        }
    }

}

