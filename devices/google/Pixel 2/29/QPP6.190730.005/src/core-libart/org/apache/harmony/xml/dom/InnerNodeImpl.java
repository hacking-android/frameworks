/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.ElementImpl;
import org.apache.harmony.xml.dom.LeafNodeImpl;
import org.apache.harmony.xml.dom.NodeImpl;
import org.apache.harmony.xml.dom.NodeListImpl;
import org.apache.harmony.xml.dom.TextImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class InnerNodeImpl
extends LeafNodeImpl {
    List<LeafNodeImpl> children = new ArrayList<LeafNodeImpl>();

    protected InnerNodeImpl(DocumentImpl documentImpl) {
        super(documentImpl);
    }

    private static boolean matchesNameOrWildcard(String string, String string2) {
        boolean bl = "*".equals(string) || Objects.equals(string, string2);
        return bl;
    }

    private void refreshIndices(int n) {
        while (n < this.children.size()) {
            this.children.get((int)n).index = n;
            ++n;
        }
    }

    @Override
    public Node appendChild(Node node) throws DOMException {
        return this.insertChildAt(node, this.children.size());
    }

    @Override
    public NodeList getChildNodes() {
        NodeListImpl nodeListImpl = new NodeListImpl();
        Iterator<LeafNodeImpl> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            nodeListImpl.add(iterator.next());
        }
        return nodeListImpl;
    }

    void getElementsByTagName(NodeListImpl nodeListImpl, String string) {
        for (NodeImpl nodeImpl : this.children) {
            if (nodeImpl.getNodeType() != 1) continue;
            ElementImpl elementImpl = (ElementImpl)nodeImpl;
            if (InnerNodeImpl.matchesNameOrWildcard(string, elementImpl.getNodeName())) {
                nodeListImpl.add(elementImpl);
            }
            elementImpl.getElementsByTagName(nodeListImpl, string);
        }
    }

    void getElementsByTagNameNS(NodeListImpl nodeListImpl, String string, String string2) {
        for (NodeImpl nodeImpl : this.children) {
            if (nodeImpl.getNodeType() != 1) continue;
            ElementImpl elementImpl = (ElementImpl)nodeImpl;
            if (InnerNodeImpl.matchesNameOrWildcard(string, elementImpl.getNamespaceURI()) && InnerNodeImpl.matchesNameOrWildcard(string2, elementImpl.getLocalName())) {
                nodeListImpl.add(elementImpl);
            }
            elementImpl.getElementsByTagNameNS(nodeListImpl, string, string2);
        }
    }

    @Override
    public Node getFirstChild() {
        Node node = !this.children.isEmpty() ? (Node)this.children.get(0) : null;
        return node;
    }

    @Override
    public Node getLastChild() {
        Object object;
        if (!this.children.isEmpty()) {
            object = this.children;
            object = object.get(object.size() - 1);
        } else {
            object = null;
        }
        return object;
    }

    @Override
    public Node getNextSibling() {
        if (this.parent != null && this.index + 1 < this.parent.children.size()) {
            return this.parent.children.get(this.index + 1);
        }
        return null;
    }

    @Override
    public String getTextContent() throws DOMException {
        Node node = this.getFirstChild();
        CharSequence charSequence = "";
        if (node == null) {
            return "";
        }
        if (node.getNextSibling() == null) {
            if (this.hasTextContent(node)) {
                charSequence = node.getTextContent();
            }
            return charSequence;
        }
        charSequence = new StringBuilder();
        this.getTextContent((StringBuilder)charSequence);
        return ((StringBuilder)charSequence).toString();
    }

    @Override
    void getTextContent(StringBuilder stringBuilder) throws DOMException {
        for (Node node = this.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (!this.hasTextContent(node)) continue;
            ((NodeImpl)node).getTextContent(stringBuilder);
        }
    }

    @Override
    public boolean hasChildNodes() {
        boolean bl = this.children.size() != 0;
        return bl;
    }

    final boolean hasTextContent(Node node) {
        boolean bl = node.getNodeType() != 8 && node.getNodeType() != 7;
        return bl;
    }

    @Override
    public Node insertBefore(Node node, Node node2) throws DOMException {
        if ((node2 = (LeafNodeImpl)node2) == null) {
            return this.appendChild(node);
        }
        if (((LeafNodeImpl)node2).document == this.document) {
            if (((LeafNodeImpl)node2).parent == this) {
                return this.insertChildAt(node, ((LeafNodeImpl)node2).index);
            }
            throw new DOMException(3, null);
        }
        throw new DOMException(4, null);
    }

    Node insertChildAt(Node node, int n) throws DOMException {
        if (node instanceof DocumentFragment) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); ++i) {
                this.insertChildAt(nodeList.item(i), n + i);
            }
            return node;
        }
        LeafNodeImpl leafNodeImpl = (LeafNodeImpl)node;
        if (leafNodeImpl.document != null && this.document != null && leafNodeImpl.document != this.document) {
            throw new DOMException(4, null);
        }
        if (!leafNodeImpl.isParentOf(this)) {
            if (leafNodeImpl.parent != null) {
                int n2 = leafNodeImpl.index;
                leafNodeImpl.parent.children.remove(n2);
                leafNodeImpl.parent.refreshIndices(n2);
            }
            this.children.add(n, leafNodeImpl);
            leafNodeImpl.parent = this;
            this.refreshIndices(n);
            return node;
        }
        throw new DOMException(3, null);
    }

    @Override
    public boolean isParentOf(Node node) {
        node = (LeafNodeImpl)node;
        while (node != null) {
            if (node == this) {
                return true;
            }
            node = ((LeafNodeImpl)node).parent;
        }
        return false;
    }

    @Override
    public final void normalize() {
        Node node = this.getFirstChild();
        while (node != null) {
            Node node2 = node.getNextSibling();
            node.normalize();
            if (node.getNodeType() == 3) {
                ((TextImpl)node).minimize();
            }
            node = node2;
        }
    }

    @Override
    public Node removeChild(Node node) throws DOMException {
        LeafNodeImpl leafNodeImpl = (LeafNodeImpl)node;
        if (leafNodeImpl.document == this.document) {
            if (leafNodeImpl.parent == this) {
                int n = leafNodeImpl.index;
                this.children.remove(n);
                leafNodeImpl.parent = null;
                this.refreshIndices(n);
                return node;
            }
            throw new DOMException(3, null);
        }
        throw new DOMException(4, null);
    }

    @Override
    public Node replaceChild(Node node, Node node2) throws DOMException {
        int n = ((LeafNodeImpl)node2).index;
        this.removeChild(node2);
        this.insertChildAt(node, n);
        return node2;
    }
}

