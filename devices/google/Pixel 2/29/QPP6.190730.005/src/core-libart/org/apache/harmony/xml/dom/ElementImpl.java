/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.harmony.xml.dom.AttrImpl;
import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.InnerNodeImpl;
import org.apache.harmony.xml.dom.NodeImpl;
import org.apache.harmony.xml.dom.NodeListImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;

public class ElementImpl
extends InnerNodeImpl
implements Element {
    private List<AttrImpl> attributes = new ArrayList<AttrImpl>();
    @UnsupportedAppUsage
    String localName;
    boolean namespaceAware;
    String namespaceURI;
    String prefix;

    ElementImpl(DocumentImpl documentImpl, String string) {
        super(documentImpl);
        ElementImpl.setName(this, string);
    }

    ElementImpl(DocumentImpl documentImpl, String string, String string2) {
        super(documentImpl);
        ElementImpl.setNameNS(this, string, string2);
    }

    private int indexOfAttribute(String string) {
        for (int i = 0; i < this.attributes.size(); ++i) {
            if (!Objects.equals(string, this.attributes.get(i).getNodeName())) continue;
            return i;
        }
        return -1;
    }

    private int indexOfAttributeNS(String string, String string2) {
        for (int i = 0; i < this.attributes.size(); ++i) {
            AttrImpl attrImpl = this.attributes.get(i);
            if (!Objects.equals(string, attrImpl.getNamespaceURI()) || !Objects.equals(string2, attrImpl.getLocalName())) continue;
            return i;
        }
        return -1;
    }

    @Override
    public String getAttribute(String object) {
        if ((object = this.getAttributeNode((String)object)) == null) {
            return "";
        }
        return object.getValue();
    }

    @Override
    public String getAttributeNS(String object, String string) {
        if ((object = this.getAttributeNodeNS((String)object, string)) == null) {
            return "";
        }
        return object.getValue();
    }

    @Override
    public AttrImpl getAttributeNode(String string) {
        int n = this.indexOfAttribute(string);
        if (n == -1) {
            return null;
        }
        return this.attributes.get(n);
    }

    @Override
    public AttrImpl getAttributeNodeNS(String string, String string2) {
        int n = this.indexOfAttributeNS(string, string2);
        if (n == -1) {
            return null;
        }
        return this.attributes.get(n);
    }

    @Override
    public NamedNodeMap getAttributes() {
        return new ElementAttrNamedNodeMapImpl();
    }

    Element getElementById(String string) {
        for (Attr object : this.attributes) {
            if (!object.isId() || !string.equals(object.getValue())) continue;
            return this;
        }
        if (string.equals(this.getAttribute("id"))) {
            return this;
        }
        for (Object object : this.children) {
            if (((NodeImpl)object).getNodeType() != 1 || (object = ((ElementImpl)object).getElementById(string)) == null) continue;
            return object;
        }
        return null;
    }

    @Override
    public NodeList getElementsByTagName(String string) {
        NodeListImpl nodeListImpl = new NodeListImpl();
        this.getElementsByTagName(nodeListImpl, string);
        return nodeListImpl;
    }

    @Override
    public NodeList getElementsByTagNameNS(String string, String string2) {
        NodeListImpl nodeListImpl = new NodeListImpl();
        this.getElementsByTagNameNS(nodeListImpl, string, string2);
        return nodeListImpl;
    }

    @Override
    public String getLocalName() {
        String string = this.namespaceAware ? this.localName : null;
        return string;
    }

    @Override
    public String getNamespaceURI() {
        return this.namespaceURI;
    }

    @Override
    public String getNodeName() {
        return this.getTagName();
    }

    @Override
    public short getNodeType() {
        return 1;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return NULL_TYPE_INFO;
    }

    @Override
    public String getTagName() {
        CharSequence charSequence;
        if (this.prefix != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.prefix);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(this.localName);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = this.localName;
        }
        return charSequence;
    }

    @Override
    public boolean hasAttribute(String string) {
        boolean bl = this.indexOfAttribute(string) != -1;
        return bl;
    }

    @Override
    public boolean hasAttributeNS(String string, String string2) {
        boolean bl = this.indexOfAttributeNS(string, string2) != -1;
        return bl;
    }

    @Override
    public boolean hasAttributes() {
        return this.attributes.isEmpty() ^ true;
    }

    @Override
    public void removeAttribute(String string) throws DOMException {
        int n = this.indexOfAttribute(string);
        if (n != -1) {
            this.attributes.remove(n);
        }
    }

    @Override
    public void removeAttributeNS(String string, String string2) throws DOMException {
        int n = this.indexOfAttributeNS(string, string2);
        if (n != -1) {
            this.attributes.remove(n);
        }
    }

    @Override
    public Attr removeAttributeNode(Attr attr) throws DOMException {
        if (((AttrImpl)(attr = (AttrImpl)attr)).getOwnerElement() == this) {
            this.attributes.remove(attr);
            ((AttrImpl)attr).ownerElement = null;
            return attr;
        }
        throw new DOMException(8, null);
    }

    @Override
    public void setAttribute(String string, String string2) throws DOMException {
        AttrImpl attrImpl;
        AttrImpl attrImpl2 = attrImpl = this.getAttributeNode(string);
        if (attrImpl == null) {
            attrImpl2 = this.document.createAttribute(string);
            this.setAttributeNode(attrImpl2);
        }
        attrImpl2.setValue(string2);
    }

    @Override
    public void setAttributeNS(String string, String string2, String string3) throws DOMException {
        AttrImpl attrImpl;
        AttrImpl attrImpl2 = attrImpl = this.getAttributeNodeNS(string, string2);
        if (attrImpl == null) {
            attrImpl2 = this.document.createAttributeNS(string, string2);
            this.setAttributeNodeNS(attrImpl2);
        }
        attrImpl2.setValue(string3);
    }

    @Override
    public Attr setAttributeNode(Attr attr) throws DOMException {
        AttrImpl attrImpl = (AttrImpl)attr;
        if (attrImpl.document == this.document) {
            if (attrImpl.getOwnerElement() == null) {
                Object var3_3 = null;
                int n = this.indexOfAttribute(attr.getName());
                attr = var3_3;
                if (n != -1) {
                    attr = this.attributes.get(n);
                    this.attributes.remove(n);
                }
                this.attributes.add(attrImpl);
                attrImpl.ownerElement = this;
                return attr;
            }
            throw new DOMException(10, null);
        }
        throw new DOMException(4, null);
    }

    @Override
    public Attr setAttributeNodeNS(Attr attr) throws DOMException {
        AttrImpl attrImpl = (AttrImpl)attr;
        if (attrImpl.document == this.document) {
            if (attrImpl.getOwnerElement() == null) {
                Object var3_3 = null;
                int n = this.indexOfAttributeNS(attr.getNamespaceURI(), attr.getLocalName());
                attr = var3_3;
                if (n != -1) {
                    attr = this.attributes.get(n);
                    this.attributes.remove(n);
                }
                this.attributes.add(attrImpl);
                attrImpl.ownerElement = this;
                return attr;
            }
            throw new DOMException(10, null);
        }
        throw new DOMException(4, null);
    }

    @Override
    public void setIdAttribute(String string, boolean bl) throws DOMException {
        Object object = this.getAttributeNode(string);
        if (object != null) {
            ((AttrImpl)object).isId = bl;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No such attribute: ");
        ((StringBuilder)object).append(string);
        throw new DOMException(8, ((StringBuilder)object).toString());
    }

    @Override
    public void setIdAttributeNS(String string, String string2, boolean bl) throws DOMException {
        Object object = this.getAttributeNodeNS(string, string2);
        if (object != null) {
            ((AttrImpl)object).isId = bl;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No such attribute: ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" ");
        ((StringBuilder)object).append(string2);
        throw new DOMException(8, ((StringBuilder)object).toString());
    }

    @Override
    public void setIdAttributeNode(Attr attr, boolean bl) throws DOMException {
        ((AttrImpl)attr).isId = bl;
    }

    @Override
    public void setPrefix(String string) {
        this.prefix = ElementImpl.validatePrefix(string, this.namespaceAware, this.namespaceURI);
    }

    public class ElementAttrNamedNodeMapImpl
    implements NamedNodeMap {
        private int indexOfItem(String string) {
            return ElementImpl.this.indexOfAttribute(string);
        }

        private int indexOfItemNS(String string, String string2) {
            return ElementImpl.this.indexOfAttributeNS(string, string2);
        }

        @Override
        public int getLength() {
            return ElementImpl.this.attributes.size();
        }

        @Override
        public Node getNamedItem(String string) {
            return ElementImpl.this.getAttributeNode(string);
        }

        @Override
        public Node getNamedItemNS(String string, String string2) {
            return ElementImpl.this.getAttributeNodeNS(string, string2);
        }

        @Override
        public Node item(int n) {
            return (Node)ElementImpl.this.attributes.get(n);
        }

        @Override
        public Node removeNamedItem(String string) throws DOMException {
            int n = this.indexOfItem(string);
            if (n != -1) {
                return (Node)ElementImpl.this.attributes.remove(n);
            }
            throw new DOMException(8, null);
        }

        @Override
        public Node removeNamedItemNS(String string, String string2) throws DOMException {
            int n = this.indexOfItemNS(string, string2);
            if (n != -1) {
                return (Node)ElementImpl.this.attributes.remove(n);
            }
            throw new DOMException(8, null);
        }

        @Override
        public Node setNamedItem(Node node) throws DOMException {
            if (node instanceof Attr) {
                return ElementImpl.this.setAttributeNode((Attr)node);
            }
            throw new DOMException(3, null);
        }

        @Override
        public Node setNamedItemNS(Node node) throws DOMException {
            if (node instanceof Attr) {
                return ElementImpl.this.setAttributeNodeNS((Attr)node);
            }
            throw new DOMException(3, null);
        }
    }

}

