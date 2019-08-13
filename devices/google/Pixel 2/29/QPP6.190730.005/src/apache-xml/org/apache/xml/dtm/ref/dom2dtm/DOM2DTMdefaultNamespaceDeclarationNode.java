/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref.dom2dtm;

import org.apache.xml.dtm.DTMException;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

public class DOM2DTMdefaultNamespaceDeclarationNode
implements Attr,
TypeInfo {
    final String NOT_SUPPORTED_ERR;
    int handle;
    String nodename;
    String prefix;
    Element pseudoparent;
    String uri;

    DOM2DTMdefaultNamespaceDeclarationNode(Element object, String string, String string2, int n) {
        this.NOT_SUPPORTED_ERR = "Unsupported operation on pseudonode";
        this.pseudoparent = object;
        this.prefix = string;
        this.uri = string2;
        this.handle = n;
        object = new StringBuilder();
        ((StringBuilder)object).append("xmlns:");
        ((StringBuilder)object).append(string);
        this.nodename = ((StringBuilder)object).toString();
    }

    @Override
    public Node appendChild(Node node) {
        throw new DTMException("Unsupported operation on pseudonode");
    }

    @Override
    public Node cloneNode(boolean bl) {
        throw new DTMException("Unsupported operation on pseudonode");
    }

    @Override
    public short compareDocumentPosition(Node node) throws DOMException {
        return 0;
    }

    @Override
    public NamedNodeMap getAttributes() {
        return null;
    }

    @Override
    public String getBaseURI() {
        return null;
    }

    @Override
    public NodeList getChildNodes() {
        return null;
    }

    @Override
    public Object getFeature(String object, String string) {
        object = this.isSupported((String)object, string) ? this : null;
        return object;
    }

    @Override
    public Node getFirstChild() {
        return null;
    }

    public int getHandleOfNode() {
        return this.handle;
    }

    @Override
    public Node getLastChild() {
        return null;
    }

    @Override
    public String getLocalName() {
        return this.prefix;
    }

    @Override
    public String getName() {
        return this.nodename;
    }

    @Override
    public String getNamespaceURI() {
        return "http://www.w3.org/2000/xmlns/";
    }

    @Override
    public Node getNextSibling() {
        return null;
    }

    @Override
    public String getNodeName() {
        return this.nodename;
    }

    @Override
    public short getNodeType() {
        return 2;
    }

    @Override
    public String getNodeValue() {
        return this.uri;
    }

    @Override
    public Document getOwnerDocument() {
        return this.pseudoparent.getOwnerDocument();
    }

    @Override
    public Element getOwnerElement() {
        return this.pseudoparent;
    }

    @Override
    public Node getParentNode() {
        return null;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public Node getPreviousSibling() {
        return null;
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return this;
    }

    @Override
    public boolean getSpecified() {
        return false;
    }

    @Override
    public String getTextContent() throws DOMException {
        return this.getNodeValue();
    }

    @Override
    public String getTypeName() {
        return null;
    }

    @Override
    public String getTypeNamespace() {
        return null;
    }

    @Override
    public Object getUserData(String string) {
        return this.getOwnerDocument().getUserData(string);
    }

    @Override
    public String getValue() {
        return this.uri;
    }

    @Override
    public boolean hasAttributes() {
        return false;
    }

    @Override
    public boolean hasChildNodes() {
        return false;
    }

    @Override
    public Node insertBefore(Node node, Node node2) {
        throw new DTMException("Unsupported operation on pseudonode");
    }

    @Override
    public boolean isDefaultNamespace(String string) {
        return false;
    }

    @Override
    public boolean isDerivedFrom(String string, String string2, int n) {
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
    public boolean isSupported(String string, String string2) {
        return false;
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
            NamedNodeMap namedNodeMap = this.getAttributes();
            int n2 = namedNodeMap.getLength();
            for (n = 0; n < n2; ++n) {
                object = namedNodeMap.item(n);
                string2 = object.getPrefix();
                String string3 = object.getNodeValue();
                String string4 = object.getNamespaceURI();
                if (string4 == null || !string4.equals("http://www.w3.org/2000/xmlns/")) continue;
                if (string == null && object.getNodeName().equals("xmlns")) {
                    return string3;
                }
                if (string2 == null || !string2.equals("xmlns") || !object.getLocalName().equals(string)) continue;
                return string3;
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
    }

    @Override
    public Node removeChild(Node node) {
        throw new DTMException("Unsupported operation on pseudonode");
    }

    @Override
    public Node replaceChild(Node node, Node node2) {
        throw new DTMException("Unsupported operation on pseudonode");
    }

    @Override
    public void setNodeValue(String string) {
        throw new DTMException("Unsupported operation on pseudonode");
    }

    @Override
    public void setPrefix(String string) {
        throw new DTMException("Unsupported operation on pseudonode");
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
    public void setValue(String string) {
        throw new DTMException("Unsupported operation on pseudonode");
    }
}

