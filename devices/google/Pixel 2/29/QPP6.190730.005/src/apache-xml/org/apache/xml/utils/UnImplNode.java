/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.io.PrintStream;
import org.apache.xml.res.XMLMessages;
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

public class UnImplNode
implements Node,
Element,
NodeList,
Document {
    protected String actualEncoding;
    protected String fDocumentURI;
    private String xmlEncoding;
    private boolean xmlStandalone;
    private String xmlVersion;

    @Override
    public Node adoptNode(Node node) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Node appendChild(Node node) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    public void appendData(String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    @Override
    public Node cloneNode(boolean bl) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public short compareDocumentPosition(Node node) throws DOMException {
        return 0;
    }

    @Override
    public Attr createAttribute(String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Attr createAttributeNS(String string, String string2) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public CDATASection createCDATASection(String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Comment createComment(String string) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public DocumentFragment createDocumentFragment() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Element createElement(String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Element createElementNS(String string, String string2) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public EntityReference createEntityReference(String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public ProcessingInstruction createProcessingInstruction(String string, String string2) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Text createTextNode(String string) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    public void deleteData(int n, int n2) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    public void error(String string) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DOM ERROR! class: ");
        stringBuilder.append(this.getClass().getName());
        printStream.println(stringBuilder.toString());
        throw new RuntimeException(XMLMessages.createXMLMessage(string, null));
    }

    public void error(String string, Object[] arrobject) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DOM ERROR! class: ");
        stringBuilder.append(this.getClass().getName());
        printStream.println(stringBuilder.toString());
        throw new RuntimeException(XMLMessages.createXMLMessage(string, arrobject));
    }

    public String getActualEncoding() {
        return this.actualEncoding;
    }

    @Override
    public String getAttribute(String string) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public String getAttributeNS(String string, String string2) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Attr getAttributeNode(String string) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Attr getAttributeNodeNS(String string, String string2) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public NamedNodeMap getAttributes() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public String getBaseURI() {
        return null;
    }

    @Override
    public NodeList getChildNodes() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public DocumentType getDoctype() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Element getDocumentElement() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
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
    public Element getElementById(String string) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public NodeList getElementsByTagName(String string) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public NodeList getElementsByTagNameNS(String string, String string2) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Object getFeature(String object, String string) {
        object = this.isSupported((String)object, string) ? this : null;
        return object;
    }

    @Override
    public Node getFirstChild() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public DOMImplementation getImplementation() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public String getInputEncoding() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Node getLastChild() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public int getLength() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return 0;
    }

    @Override
    public String getLocalName() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public String getNamespaceURI() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Node getNextSibling() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public String getNodeName() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public short getNodeType() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return 0;
    }

    @Override
    public String getNodeValue() throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Document getOwnerDocument() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    public Element getOwnerElement() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Node getParentNode() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public String getPrefix() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Node getPreviousSibling() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return null;
    }

    public boolean getSpecified() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return false;
    }

    @Override
    public boolean getStrictErrorChecking() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return false;
    }

    @Override
    public String getTagName() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public String getTextContent() throws DOMException {
        return this.getNodeValue();
    }

    @Override
    public Object getUserData(String string) {
        return this.getOwnerDocument().getUserData(string);
    }

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
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return false;
    }

    @Override
    public boolean hasAttributeNS(String string, String string2) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return false;
    }

    @Override
    public boolean hasAttributes() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return false;
    }

    @Override
    public boolean hasChildNodes() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return false;
    }

    @Override
    public Node importNode(Node node, boolean bl) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Node insertBefore(Node node, Node node2) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    public void insertData(int n, String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    @Override
    public boolean isDefaultNamespace(String string) {
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

    public boolean isId() {
        return false;
    }

    @Override
    public boolean isSameNode(Node node) {
        boolean bl = this == node;
        return bl;
    }

    @Override
    public boolean isSupported(String string, String string2) {
        return false;
    }

    public boolean isWhitespaceInElementContent() {
        return false;
    }

    @Override
    public Node item(int n) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
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
        Object object = this.getNamespaceURI();
        String string2 = this.getPrefix();
        if (object != null) {
            if (string == null && string2 == string) {
                return object;
            }
            if (string2 != null && string2.equals(string)) {
                return object;
            }
        }
        if (this.hasAttributes()) {
            object = this.getAttributes();
            int n2 = object.getLength();
            for (n = 0; n < n2; ++n) {
                Node node = object.item(n);
                String string3 = node.getPrefix();
                string2 = node.getNodeValue();
                String string4 = node.getNamespaceURI();
                if (string4 == null || !string4.equals("http://www.w3.org/2000/xmlns/")) continue;
                if (string == null && node.getNodeName().equals("xmlns")) {
                    return string2;
                }
                if (string3 == null || !string3.equals("xmlns") || !node.getLocalName().equals(string)) continue;
                return string2;
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
    public void normalize() {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    @Override
    public void normalizeDocument() {
    }

    @Override
    public void removeAttribute(String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    @Override
    public void removeAttributeNS(String string, String string2) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    @Override
    public Attr removeAttributeNode(Attr attr) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Node removeChild(Node node) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Node renameNode(Node node, String string, String string2) throws DOMException {
        return node;
    }

    @Override
    public Node replaceChild(Node node, Node node2) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    public void replaceData(int n, int n2, String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    public Text replaceWholeText(String string) throws DOMException {
        return null;
    }

    public void setActualEncoding(String string) {
        this.actualEncoding = string;
    }

    @Override
    public void setAttribute(String string, String string2) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    @Override
    public void setAttributeNS(String string, String string2, String string3) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    @Override
    public Attr setAttributeNode(Attr attr) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    @Override
    public Attr setAttributeNodeNS(Attr attr) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    public void setData(String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
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

    public void setInputEncoding(String string) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    @Override
    public void setNodeValue(String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    @Override
    public void setPrefix(String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    @Override
    public void setStrictErrorChecking(boolean bl) {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
    }

    @Override
    public void setTextContent(String string) throws DOMException {
        this.setNodeValue(string);
    }

    @Override
    public Object setUserData(String string, Object object, UserDataHandler userDataHandler) {
        return this.getOwnerDocument().setUserData(string, object, userDataHandler);
    }

    public void setValue(String string) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
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

    public Text splitText(int n) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }

    public String substringData(int n, int n2) throws DOMException {
        this.error("ER_FUNCTION_NOT_SUPPORTED");
        return null;
    }
}

